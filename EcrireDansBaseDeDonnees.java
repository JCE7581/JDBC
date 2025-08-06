package com.mycompany.tennis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EcrireDansBaseDeDonnees {

    // URL de connexion à la base de données
    private static final String URL = "jdbc:mysql://localhost:3306/tennis?useSSL=false&serverTimezone=Europe/Paris";
    private static final String USER = "JDBC";
    private static final String PASSWORD = "javacore";

    public static void main(String... args) {

        // Exemple d'utilisation des méthodes

        // 1. Ajout d'un nouveau joueur
        ajouterJoueur("poinas", "Yannick", 44L, 'H');

        // 2. Modification d'un joueur existant
        modifierJoueur("Federer", "Roger", 20L);

        // 3. Suppression du joueur
        supprimerJoueur(45L);
        supprimerJoueur(46L);
    }

    private static void ajouterJoueur(String nom, String prenom, long identifiant, char sexe) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO JOUEUR (ID, NOM, PRENOM, SEXE) VALUES (?, ?, ?, ?)");

            preparedStatement.setLong(1, identifiant);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, String.valueOf(sexe));

            int nbEnregistrementsAjoutes = preparedStatement.executeUpdate();
            System.out.println("Ajout : " + nbEnregistrementsAjoutes + " ligne(s) ajoutée(s).");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fermerConnexion(conn);
        }
    }

    private static void modifierJoueur(String nouveauNom, String nouveauPrenom, long identifiant) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE JOUEUR SET NOM=?, PRENOM=? WHERE ID=?");

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

    private static void supprimerJoueur(long identifiant) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM JOUEUR WHERE ID=?");

            preparedStatement.setLong(1, identifiant);

            int nbLignesSupprimees = preparedStatement.executeUpdate();
            System.out.println("Suppression : " + nbLignesSupprimees + " ligne(s) supprimée(s).");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            fermerConnexion(conn);
        }
    }

    private static void fermerConnexion(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}