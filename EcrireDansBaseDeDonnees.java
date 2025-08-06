package com.mycompany.tennis;

// Importe les classes nécessaires pour interagir avec la base de données
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EcrireDansBaseDeDonnees {

    // Déclare les constantes pour les informations de connexion.
    // Cela rend le code plus propre et plus facile à maintenir.
    private static final String URL = "jdbc:mysql://localhost:3306/tennis?useSSL=false&serverTimezone=Europe/Paris";
    private static final String USER = "JDBC";
    private static final String PASSWORD = "javacore";

    public static void main(String... args) {
        // La méthode main sert de point d'entrée pour le programme.
        // Elle appelle les méthodes qui gèrent les opérations sur la base de données.

        // 1. Ajout d'un nouveau joueur dans la base de données.
        // L'ID 44L est utilisé comme exemple.
        ajouterJoueur("poinas", "Yannick", 44L, 'H');

        // 2. Modification d'un joueur existant.
        // L'ID 20L est utilisé pour cibler le joueur à modifier.
        modifierJoueur("Federer", "Roger", 20L);

        // 3. Suppression de joueurs.
        // Les IDs 45L et 46L sont utilisés pour cibler les lignes à supprimer.
        supprimerJoueur(45L);
        supprimerJoueur(46L);
    }

    /**
     * Ajoute un nouveau joueur dans la table JOUEUR.
     * @param nom Nom du joueur.
     * @param prenom Prénom du joueur.
     * @param identifiant ID unique du joueur.
     * @param sexe Sexe du joueur ('H' pour homme, 'F' pour femme).
     */
    private static void ajouterJoueur(String nom, String prenom, long identifiant, char sexe) {
        // Déclare une connexion, initialisée à null pour pouvoir la gérer dans le bloc 'finally'.
        Connection conn = null;
        try {
            // Établit une connexion à la base de données en utilisant les constantes définies.
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prépare une requête SQL d'insertion.
            // Les '?' sont des placeholders qui évitent les injections SQL.
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO JOUEUR (ID, NOM, PRENOM, SEXE) VALUES (?, ?, ?, ?)");

            // Attribue les valeurs aux placeholders de la requête préparée.
            // Les index des placeholders commencent à 1.
            preparedStatement.setLong(1, identifiant);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, String.valueOf(sexe));

            // Exécute la requête de mise à jour (insertion, modification, suppression).
            // La méthode executeUpdate() retourne le nombre de lignes affectées.
            int nbEnregistrementsAjoutes = preparedStatement.executeUpdate();
            System.out.println("Ajout : " + nbEnregistrementsAjoutes + " ligne(s) ajoutée(s).");

        } catch (SQLException e) {
            // Affiche la trace de l'erreur si une exception SQL se produit.
            e.printStackTrace();
        } finally {
            // Le bloc 'finally' s'exécute toujours. Il est utilisé ici pour garantir
            // que la connexion à la base de données est fermée pour libérer les ressources.
            fermerConnexion(conn);
        }
    }

    /**
     * Modifie le nom et le prénom d'un joueur existant.
     * @param nouveauNom Le nouveau nom du joueur.
     * @param nouveauPrenom Le nouveau prénom du joueur.
     * @param identifiant L'ID du joueur à modifier.
     */
    private static void modifierJoueur(String nouveauNom, String nouveauPrenom, long identifiant) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prépare une requête SQL de mise à jour.
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE JOUEUR SET NOM=?, PRENOM=? WHERE ID=?");

            // Attribue les nouvelles valeurs aux placeholders.
            preparedStatement.setString(1, nouveauNom);
            preparedStatement.setString(2, nouveauPrenom);
            preparedStatement.setLong(3, identifiant);

            int nbEnregistrementsModifies = preparedStatement.executeUpdate();
            System.out.println("Modification : " + nbEnregistrementsModifies + " ligne(s) modifiée(s).");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fermerConnexion(conn);
        }
    }

    /**
     * Supprime une ligne de la table JOUEUR en fonction de son ID.
     * @param identifiant L'ID du joueur à supprimer.
     */
    private static void supprimerJoueur(long identifiant) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Prépare une requête SQL de suppression.
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM JOUEUR WHERE ID=?");

            // Attribue l'ID du joueur à supprimer au placeholder.
            preparedStatement.setLong(1, identifiant);

            int nbLignesSupprimees = preparedStatement.executeUpdate();
            System.out.println("Suppression : " + nbLignesSupprimees + " ligne(s) supprimée(s).");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fermerConnexion(conn);
        }
    }

    /**
     * Ferme la connexion à la base de données si elle est ouverte.
     * Cette méthode est appelée dans le bloc 'finally' pour chaque opération.
     * @param conn La connexion à fermer.
     */
    private static void fermerConnexion(Connection conn) {
        try {
            // Vérifie si la connexion n'est pas nulle avant d'essayer de la fermer.
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}