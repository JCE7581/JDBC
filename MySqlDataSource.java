package com.mycompany.tennis;

// Importation des classes nécessaires pour la base de données
import com.mysql.cj.jdbc.MysqlDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlDataSource {

    // Déclare une variable statique pour contenir l'objet DataSource.
    // Elle sera partagée par toutes les méthodes de cette classe.
    private static DataSource dataSource;

    // Ce bloc statique s'exécute une seule fois au chargement de la classe.
    // Il est utilisé pour configurer la connexion à la base de données.
    static {
        try {
            // Crée une instance de MysqlDataSource, l'implémentation du DataSource pour MySQL.
            MysqlDataSource mysqlDataSource = new MysqlDataSource();

            // Configure les paramètres de connexion de manière propre.
            mysqlDataSource.setServerName("localhost");
            mysqlDataSource.setPortNumber(3306);
            mysqlDataSource.setDatabaseName("tennis");
            mysqlDataSource.setUseSSL(false);
            mysqlDataSource.setServerTimezone("Europe/Paris"); // Définit le fuseau horaire du serveur
            mysqlDataSource.setUser("JDBC");
            mysqlDataSource.setPassword("javacore");

            // Affecte l'objet configuré à la variable statique.
            dataSource = mysqlDataSource;

        } catch (SQLException e) {
            // En cas d'erreur lors de la configuration initiale (mauvais mot de passe, base de données inexistante, etc.),
            // on affiche la trace et on arrête le programme en lançant une RuntimeException.
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'initialisation du DataSource", e);
        }
    }

    public static void main(String... args) {
        // La méthode main sert de point d'entrée pour tester les différentes opérations.
        // On appelle chaque méthode pour ajouter, modifier et supprimer des joueurs.

        // Ajout d'un nouveau joueur (l'ID 44L est utilisé comme exemple).
        ajouterJoueur("poinas", "Yannick", 44L, 'H');

        // Modification d'un joueur existant (l'ID 20L est utilisé comme exemple).
        modifierJoueur("Federer", "Roger", 20L);

        // Suppression de joueurs (les IDs 45L et 46L sont utilisés comme exemples).
        supprimerJoueur(45L);
        supprimerJoueur(46L);
    }

    /**
     * Méthode utilitaire pour obtenir une connexion à la base de données.
     * Le DataSource gère un pool de connexions, donc cette méthode
     * retourne une connexion existante du pool ou en crée une nouvelle si nécessaire.
     * @return un objet Connection.
     * @throws SQLException si une erreur de connexion survient.
     */
    private static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Ajoute un nouveau joueur dans la table JOUEUR.
     * @param nom Nom du joueur.
     * @param prenom Prénom du joueur.
     * @param identifiant ID unique du joueur.
     * @param sexe Sexe du joueur ('H' ou 'F').
     */
    private static void ajouterJoueur(String nom, String prenom, long identifiant, char sexe) {
        // Le bloc try-with-resources garantit que la connexion sera fermée automatiquement
        // à la fin de l'exécution, même en cas d'exception.
        try (Connection conn = getConnection()) {
            // Prépare une requête SQL d'insertion. Les '?' sont des placeholders pour les valeurs.
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO JOUEUR (ID, NOM, PRENOM, SEXE) VALUES (?, ?, ?, ?)");

            // Attribue les valeurs aux placeholders de la requête.
            preparedStatement.setLong(1, identifiant); // 1er '?'
            preparedStatement.setString(2, nom);       // 2e '?'
            preparedStatement.setString(3, prenom);    // 3e '?'
            preparedStatement.setString(4, String.valueOf(sexe)); // 4e '?'

            // Exécute la requête et récupère le nombre de lignes ajoutées.
            int nbEnregistrementsAjoutes = preparedStatement.executeUpdate();
            System.out.println("Ajout : " + nbEnregistrementsAjoutes + " ligne(s) ajoutée(s).");

        } catch (SQLException e) {
            // Affiche la trace de l'exception si une erreur SQL se produit (ex: ID déjà existant).
            e.printStackTrace();
        }
    }

    /**
     * Modifie le nom et le prénom d'un joueur existant.
     * @param nouveauNom Le nouveau nom du joueur.
     * @param nouveauPrenom Le nouveau prénom du joueur.
     * @param identifiant L'ID du joueur à modifier.
     */
    private static void modifierJoueur(String nouveauNom, String nouveauPrenom, long identifiant) {
        try (Connection conn = getConnection()) {
            // Prépare une requête SQL de mise à jour.
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE JOUEUR SET NOM=?, PRENOM=? WHERE ID=?");

            // Attribue les nouvelles valeurs au joueur ciblé par son ID.
            preparedStatement.setString(1, nouveauNom);
            preparedStatement.setString(2, nouveauPrenom);
            preparedStatement.setLong(3, identifiant);

            // Exécute la requête et affiche le nombre de lignes modifiées.
            int nbEnregistrementsModifies = preparedStatement.executeUpdate();
            System.out.println("Modification : " + nbEnregistrementsModifies + " ligne(s) modifiée(s).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime une ligne de la table JOUEUR en fonction de son ID.
     * @param identifiant L'ID du joueur à supprimer.
     */
    private static void supprimerJoueur(long identifiant) {
        try (Connection conn = getConnection()) {
            // Prépare une requête SQL de suppression.
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM JOUEUR WHERE ID=?");

            // Définit l'ID du joueur à supprimer.
            preparedStatement.setLong(1, identifiant);

            // Exécute la requête et affiche le nombre de lignes supprimées.
            int nbLignesSupprimees = preparedStatement.executeUpdate();
            System.out.println("Suppression : " + nbLignesSupprimees + " ligne(s) supprimée(s).");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}