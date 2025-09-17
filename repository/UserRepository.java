// 📦 Déclaration du package dans lequel se trouve cette classe
// Cela permet d'organiser ton projet en dossiers logiques
package repository;

// 📦 Importation de la classe Utilisateur
// Elle représente un utilisateur dans ton application Java
import model.Utilisateur;

// 📦 Importation de l'interface DataSource
// Elle permet de gérer les connexions à la base de données
import javax.sql.DataSource;

// 📦 Importation des classes JDBC nécessaires pour interagir avec MySQL
// Connection : pour se connecter à la base
// PreparedStatement : pour exécuter des requêtes SQL avec paramètres
// ResultSet : pour lire les résultats des requêtes
// SQLException : pour gérer les erreurs SQL
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// 📦 Importation des classes utilitaires Java
// List : pour stocker plusieurs utilisateurs
// ArrayList : une implémentation concrète de List
import java.util.ArrayList;
import java.util.List;

// 🧱 Déclaration de la classe UserRepository
// Cette classe contient toutes les méthodes pour accéder à la table "utilisateurs"
public class UserRepository {

    // 🔐 Déclaration d'un attribut privé de type DataSource
    // Il permet d'obtenir des connexions à la base de données
    private final DataSource dataSource;

    // 🏗️ Constructeur de la classe
    // Il reçoit une DataSource en paramètre et l'assigne à l'attribut
    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Déclaration de la méthode publique 'findAll'.
// Elle ne prend aucun paramètre et retourne une liste d'objets 'Utilisateur'.
    public List<Utilisateur> findAll() {

        // Crée une nouvelle instance de la classe 'ArrayList' pour stocker les utilisateurs.
        // Cette liste est initialement vide.
        List<Utilisateur> utilisateurs = new ArrayList<>();

        // Déclare une chaîne de caractères (String) contenant la requête SQL.
        // La requête sélectionne toutes les colonnes ('*') de la table 'utilisateurs'.
        String sql = "SELECT * FROM utilisateurs";

        // Débute un bloc 'try-with-resources'. C'est une fonctionnalité de Java qui assure que
        // les ressources créées entre les parenthèses (ici, la connexion, le statement et le result set)
        // seront automatiquement fermées à la fin du bloc, qu'il y ait une erreur ou non.
        try (
                // Obtient une connexion à la base de données à partir du pool de connexions 'dataSource'.
                Connection conn = dataSource.getConnection();

                // Crée un 'PreparedStatement' à partir de la connexion et de la requête SQL.
                // Cela prépare la requête pour l'exécution.
                PreparedStatement stmt = conn.prepareStatement(sql);

                // Exécute la requête SQL et stocke les résultats dans un objet 'ResultSet'.
                // Le 'ResultSet' agit comme un curseur qui pointe vers les lignes de résultats.
                ResultSet rs = stmt.executeQuery()
        ) {

            // Une boucle 'while' qui s'exécute tant qu'il y a des lignes dans le 'ResultSet'.
            // La méthode 'rs.next()' déplace le curseur à la ligne suivante.
            while (rs.next()) {

                // Appelle la méthode 'mapResultSetToUtilisateur()' pour convertir la ligne
                // actuelle du 'ResultSet' en un objet 'Utilisateur'.
                // L'objet 'Utilisateur' est ensuite ajouté à la liste 'utilisateurs'.
                utilisateurs.add(mapResultSetToUtilisateur(rs));
            }

            // Un bloc 'catch' qui capture les erreurs de type 'SQLException'.
            // Si une erreur liée à la base de données se produit, le code dans ce bloc est exécuté.
        } catch (SQLException e) {

            // Affiche un message d'erreur sur la console, y compris le message
            // spécifique de l'exception.
            System.out.println("❌ Erreur dans findAll : " + e.getMessage());
        }

        // Retourne la liste 'utilisateurs' qui contient tous les utilisateurs trouvés dans la base de données.
        return utilisateurs;
    }

    // 📦 Déclaration de la méthode publique 'findById'.
// Elle prend un entier (int) 'id' en paramètre et renvoie un objet 'Utilisateur'.
    public Utilisateur findById(int id) {
        // 🧾 Déclaration de la requête SQL. Le '?' est un espace réservé (paramètre).
        // Cela protège le code contre les injections SQL.
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";

        // 🧱 Début du bloc 'try-with-resources'. Les ressources (connexion et statement)
        // sont automatiquement fermées à la fin du bloc, même en cas d'erreur.
        try (
                // 🔌 Obtient une connexion à la base de données à partir du pool de connexions.
                Connection conn = dataSource.getConnection();

                // 🧠 Prépare la requête SQL pour être exécutée.
                // C'est un 'PreparedStatement' car il a des paramètres.
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // 🧩 Remplace le premier paramètre '?' par la valeur de l'ID reçue.
            // L'indice 1 correspond au premier '?'.
            stmt.setInt(1, id);

            // 🧱 Un second 'try-with-resources' pour le 'ResultSet'.
            try (ResultSet rs = stmt.executeQuery()) {

                // ✅ Vérifie si la requête a renvoyé au moins une ligne.
                // Si c'est le cas, cela signifie que l'utilisateur a été trouvé.
                if (rs.next()) {

                    // ➡️ Appelle la méthode 'mapResultSetToUtilisateur' pour transformer la ligne
                    // du 'ResultSet' en un objet 'Utilisateur' complet.
                    return mapResultSetToUtilisateur(rs);
                }
            }
        } catch (SQLException e) {
            // ❌ Si une erreur SQL se produit (par exemple, problème de connexion),
            // ce bloc la capture et affiche le message d'erreur.
            System.out.println("❌ Erreur dans findById : " + e.getMessage());
        }

        // ⛔ Si la requête n'a renvoyé aucune ligne (l'utilisateur n'existe pas)
        // ou si une erreur a été capturée, la méthode renvoie 'null'.
        return null;
    }

    // ➕ Méthode pour ajouter un nouvel utilisateur dans la base
    // Elle retourne true si l'ajout a réussi, false sinon
    public boolean save(Utilisateur u) {
        // 🧾 Requête SQL d'insertion avec deux paramètres
        String sql = "INSERT INTO utilisateurs (nom, email) VALUES (?, ?)";

        // 🔁 Bloc try-with-resources
        try (
                // 🔌 Connexion à la base
                Connection conn = dataSource.getConnection();

                // 🧠 Préparation de la requête avec récupération des clés générées
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            // 🧩 Remplacement des ? par les valeurs de l'objet Utilisateur
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getEmail());

            // ▶️ Exécution de la requête
            int affectedRows = stmt.executeUpdate();

            // ✅ Si au moins une ligne a été insérée
            if (affectedRows > 0) {
                // 🔁 Récupération de l'ID généré automatiquement
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // 🧱 Mise à jour de l'objet Java avec l'ID généré
                        u.setId(generatedKeys.getInt(1));
                    }
                }
                return true; // ✅ Insertion réussie
            }
        } catch (SQLException e) {
            // ⚠️ Affichage de l'erreur SQL
            System.out.println("❌ Erreur dans save : " + e.getMessage());
        }

        // ❌ Échec de l'insertion
        return false;
    }

    // ✏️ Méthode pour modifier un utilisateur existant
    // Elle retourne true si la mise à jour a réussi
    public boolean update(Utilisateur u) {
        // 🧾 Requête SQL de mise à jour avec trois paramètres
        String sql = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";

        // 🔁 Bloc try-with-resources
        try (
                // 🔌 Connexion à la base
                Connection conn = dataSource.getConnection();

                // 🧠 Préparation de la requête SQL
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // 🧩 Remplacement des ? par les nouvelles valeurs
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getEmail());
            stmt.setInt(3, u.getId());

            // ▶️ Exécution de la requête
            return stmt.executeUpdate() > 0; // ✅ true si au moins une ligne modifiée
        } catch (SQLException e) {
            // ⚠️ Affichage de l'erreur SQL
            System.out.println("❌ Erreur dans update : " + e.getMessage());
        }

        // ❌ Échec de la mise à jour
        return false;
    }

    // ❌ Méthode pour supprimer un utilisateur par son ID
    // Elle retourne true si la suppression a réussi
    public boolean delete(int id) {
        // 🧾 Requête SQL de suppression avec un paramètre
        String sql = "DELETE FROM utilisateurs WHERE id = ?";

        // 🔁 Bloc try-with-resources
        try (
                // 🔌 Connexion à la base
                Connection conn = dataSource.getConnection();

                // 🧠 Préparation de la requête SQL
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            // 🧩 Remplacement du ? par l'ID à supprimer
            stmt.setInt(1, id);

            // ▶️ Exécution de la requête
            return stmt.executeUpdate() > 0; // ✅ true si une ligne supprimée
        } catch (SQLException e) {
            // ⚠️ Affichage de l'erreur SQL
            System.out.println("❌ Erreur dans delete : " + e.getMessage());
        }

        // ❌ Échec de la suppression
        return false;

    }

    // Déclaration de la méthode publique 'saveAll'.
    // Elle retourne 'true' si l'opération a réussi, 'false' sinon.
    // Elle prend en paramètre une liste d'objets 'Utilisateur' à sauvegarder.
    public boolean saveAll(List<Utilisateur> utilisateurs) {
        // Déclaration d'une chaîne de caractères (String) qui contient la requête SQL.
        // '?' est un espace réservé pour les valeurs qui seront ajoutées plus tard.
        String sql = "INSERT INTO utilisateurs (nom, email) VALUES (?, ?)";

        // Déclaration d'une variable de type 'Connection'.
        // Elle est initialisée à 'null' (vide).
        // On la déclare ici pour qu'elle soit accessible dans tous les blocs (try, catch, finally).
        Connection conn = null;

        // Début d'un bloc 'try'. Le code à l'intérieur de ce bloc va être exécuté.
        // Si une erreur (une exception) se produit, l'exécution est transférée au bloc 'catch'.
        try {
            // Obtient une connexion à la base de données depuis le pool de connexions (dataSource).
            // Cette connexion est assignée à la variable 'conn'.
            conn = dataSource.getConnection();

            // Désactive le mode d'auto-validation (auto-commit).
            // Par défaut, chaque requête SQL est automatiquement validée.
            // Ici, nous voulons valider toutes les requêtes ensemble, à la fin, pour la transaction.
            conn.setAutoCommit(false);

            // Crée un objet 'PreparedStatement' pour exécuter la requête SQL.
            // Un 'PreparedStatement' est plus sûr et plus rapide qu'un 'Statement' simple.
            // 'Statement.RETURN_GENERATED_KEYS' permet de récupérer les clés (ID) générées.
            PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            // Début d'une boucle 'for' qui parcourt chaque 'Utilisateur' de la liste 'utilisateurs'.
            for (Utilisateur u : utilisateurs) {
                // Associe le nom de l'utilisateur ('u.getNom()') au premier '?' de la requête SQL.
                // C'est une méthode sécurisée pour éviter les injections SQL.
                stmt.setString(1, u.getNom());
                // Associe l'email de l'utilisateur ('u.getEmail()') au deuxième '?' de la requête SQL.
                stmt.setString(2, u.getEmail());

                // Ajoute la requête préparée au "batch", une sorte de "lot" de requêtes.
                // Au lieu d'envoyer une requête à la base de données à chaque tour de boucle,
                // on les regroupe pour les envoyer toutes en une seule fois.
                stmt.addBatch();
            }

            // Exécute toutes les requêtes qui ont été ajoutées au batch.
            // 'executeBatch()' est beaucoup plus performant que 'executeUpdate()' dans une boucle.
            // Le résultat est un tableau d'entiers qui indique le nombre de lignes affectées par chaque requête.
            int[] result = stmt.executeBatch();

            // Valide la transaction.
            // Toutes les modifications qui ont été regroupées depuis 'setAutoCommit(false)' sont maintenant
            // rendues permanentes dans la base de données.
            conn.commit();

            // Affiche un message de succès sur la console.
            // 'result.length' donne le nombre d'utilisateurs qui ont été insérés.
            System.out.println("✅ Transaction réussie. " + result.length + " utilisateurs insérés.");

            // Retourne 'true' pour indiquer que l'opération a réussi.
            return true;

            // Début du bloc 'catch', qui sera exécuté si une 'SQLException' se produit dans le bloc 'try'.
        } catch (SQLException e) {
            // Affiche un message d'erreur sur la console, avec les détails de l'erreur.
            System.out.println("❌ Erreur de transaction : " + e.getMessage());

            // Vérifie si la connexion a été établie avec succès (n'est pas 'null').
            if (conn != null) {
                // Début d'un nouveau bloc 'try' pour le 'rollback'.
                try {
                    // Annule toutes les modifications qui ont été effectuées
                    // depuis le début de la transaction.
                    // La base de données est remise dans son état initial.
                    conn.rollback();
                    // Affiche un message pour indiquer que la transaction a été annulée.
                    System.out.println("🔄 Transaction annulée (rollback).");
                    // Un bloc 'catch' interne pour gérer les erreurs qui pourraient survenir lors du 'rollback'.
                } catch (SQLException rollbackEx) {
                    System.out.println("⚠️ Erreur lors de l'annulation de la transaction : " + rollbackEx.getMessage());
                }
            }
            // Retourne 'false' pour indiquer que l'opération a échoué.
            return false;

            // Début du bloc 'finally'. Ce bloc est TOUJOURS exécuté,
            // que le code ait réussi ('try') ou qu'une erreur ait été gérée ('catch').
        } finally {
            // Vérifie si la connexion est valide.
            if (conn != null) {
                // Tente de fermer la connexion à la base de données.
                // Cela libère les ressources et remet la connexion dans le pool HikariCP.
                try {
                    conn.close();
                    // Gère les erreurs qui pourraient survenir lors de la fermeture de la connexion.
                } catch (SQLException closeEx) {
                    System.out.println("⚠️ Erreur lors de la fermeture de la connexion : " + closeEx.getMessage());
                }
            }
        }
    }

    // Déclaration de la méthode. Elle est 'private', ce qui signifie qu'elle ne peut être appelée
    // que depuis l'intérieur de la classe 'UserRepository'.
    // Elle retourne un objet de type 'Utilisateur'.
    // Elle prend en paramètre un objet 'ResultSet', qui contient les résultats de la requête SQL.
    // Le mot-clé 'throws SQLException' indique que la méthode peut générer une erreur liée à la base de données.
    private Utilisateur mapResultSetToUtilisateur(ResultSet rs) throws SQLException {

        // Crée une nouvelle instance de la classe 'Utilisateur'. C'est l'objet
        // que nous allons remplir avec les données de la base de données.
        Utilisateur u = new Utilisateur();

        // Appelle la méthode 'setId()' de l'objet 'u'.
        // 'rs.getInt("id")' récupère la valeur entière de la colonne 'id' de la ligne actuelle
        // du 'ResultSet'. La colonne est identifiée par son nom.
        u.setId(rs.getInt("id"));

        // Appelle la méthode 'setNom()' de l'objet 'u'.
        // 'rs.getString("nom")' récupère la valeur de type chaîne de caractères de la
        // colonne 'nom' de la ligne actuelle du 'ResultSet'.
        u.setNom(rs.getString("nom"));

        // Appelle la méthode 'setEmail()' de l'objet 'u'.
        // 'rs.getString("email")' récupère la valeur de la colonne 'email'.
        u.setEmail(rs.getString("email"));

        // Retourne l'objet 'Utilisateur' qui est maintenant entièrement
        // rempli avec les données de la base de données.
        return u;
    }
}