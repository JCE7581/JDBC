package com.mycompany.tennis;

// Importation des classes nécessaires pour la gestion du pool de connexions et des opérations SQL.
import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Cette classe gère le pool de connexions à une base de données MySQL
 * en utilisant la bibliothèque Apache Commons DBCP2.
 * Elle contient des méthodes pour effectuer des opérations CRUD (Créer, Lire, Mettre à jour, Supprimer)
 * sur une table 'JOUEUR'.
 */
public class Hibernate {

    // Déclaration de la variable statique dataSource.
    // 'static' signifie qu'elle est partagée par toutes les instances de la classe.
    // 'private' signifie qu'elle ne peut être accédée que dans cette classe.
    private static BasicDataSource dataSource;

    // Le bloc 'static' est exécuté une seule fois, au premier chargement de la classe.
    // C'est l'endroit idéal pour initialiser le pool de connexions.
    static {
        dataSource = new BasicDataSource();

        // --- Configuration du pool de connexions ---

        // 1. Spécification du pilote de connexion JDBC pour MySQL.
        //    C'est la classe que le pool va utiliser pour créer les connexions physiques.
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");

        // 2. L'URL de connexion à la base de données.
        //    Elle contient l'adresse du serveur, le port, le nom de la base de données
        //    et des paramètres additionnels comme le fuseau horaire.
        dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&serverTimezone=Europe/Paris");

        // 3. Le nom d'utilisateur et le mot de passe pour la connexion à la base de données.
        //    Il est très important de les remplacer par tes propres identifiants.
        dataSource.setUsername("JDBC");
        dataSource.setPassword("javacore");

        // 4. Configuration de la taille du pool pour de meilleures performances.
        //    'InitialSize' définit le nombre de connexions créées au démarrage.
        //    'MaxTotal' définit le nombre maximum de connexions actives simultanément.
        dataSource.setInitialSize(5);
        dataSource.setMaxTotal(10);
    }

    /**
     * Point d'entrée principal de l'application.
     * Cette méthode appelle les fonctions pour tester les opérations de base de données.
     * @param args Arguments de la ligne de commande (non utilisés ici).
     */
    public static void main(String... args) {
        // Appels aux méthodes CRUD pour l'exemple.
        // Assurez-vous que les identifiants utilisés sont pertinents pour vos données.
        ajouterJoueur("poinas", "Yannick", 44L, 'H');
        modifierJoueur("Federer", "Roger", 20L);
        supprimerJoueur(45L);
    }

    /**
     * Méthode pour obtenir une connexion depuis le pool.
     * Le pool gère la création et la réutilisation des connexions pour optimiser les performances.
     * @return Une connexion prête à l'emploi.
     * @throws SQLException Si une erreur de base de données se produit lors de l'obtention de la connexion.
     */
    private static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Ajoute un nouveau joueur à la base de données.
     * @param nom Nom du joueur.
     * @param prenom Prénom du joueur.
     * @param identifiant Identifiant unique du joueur.
     * @param sexe Sexe du joueur ('H' pour Homme, 'F' pour Femme).
     */
    private static void ajouterJoueur(String nom, String prenom, long identifiant, char sexe) {
        // Utilisation d'un 'try-with-resources' pour s'assurer que la connexion est fermée automatiquement
        // à la fin du bloc, même en cas d'erreur.
        try (Connection conn = getConnection()) {
            // Création d'une requête préparée pour éviter les injections SQL et améliorer les performances.
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO JOUEUR (ID, NOM, PRENOM, SEXE) VALUES (?, ?, ?, ?)");

            // Attribution des valeurs aux paramètres de la requête.
            preparedStatement.setLong(1, identifiant);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, String.valueOf(sexe));

            // Exécution de la requête de mise à jour (insertion, modification, suppression).
            int nbEnregistrementsAjoutes = preparedStatement.executeUpdate();
            System.out.println("Ajout : " + nbEnregistrementsAjoutes + " ligne(s) ajoutée(s).");
        } catch (SQLException e) {
            // Affichage de la trace de l'erreur en cas de problème de base de données.
            e.printStackTrace();
        }
    }

    /**
     * Modifie le nom et le prénom d'un joueur existant.
     * @param nouveauNom Nouveau nom du joueur.
     * @param nouveauPrenom Nouveau prénom du joueur.
     * @param identifiant Identifiant du joueur à modifier.
     */
    private static void modifierJoueur(String nouveauNom, String nouveauPrenom, long identifiant) {
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE JOUEUR SET NOM=?, PRENOM=? WHERE ID=?");

            preparedStatement.setString(1, nouveauNom);
            preparedStatement.setString(2, nouveauPrenom);
            preparedStatement.setLong(3, identifiant);

            int nbEnregistrementsModifies = preparedStatement.executeUpdate();
            System.out.println("Modification : " + nbEnregistrementsModifies + " ligne(s) modifiée(s).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Supprime un joueur de la base de données.
     * @param identifiant Identifiant du joueur à supprimer.
     */
    private static void supprimerJoueur(long identifiant) {
        try (Connection conn = getConnection()) {
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM JOUEUR WHERE ID=?");

            preparedStatement.setLong(1, identifiant);

            int nbLignesSupprimees = preparedStatement.executeUpdate();
            System.out.println("Suppression : " + nbLignesSupprimees + " ligne(s) supprimée(s).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}