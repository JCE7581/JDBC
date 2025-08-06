package com.mycompany.tennis;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MySqlDataSource {

    private static DataSource dataSource;

    // Bloc statique pour initialiser le DataSource une seule fois
    static {
        try {
            MysqlDataSource mysqlDataSource = new MysqlDataSource();
            mysqlDataSource.setServerName("localhost");
            mysqlDataSource.setPortNumber(3306);
            mysqlDataSource.setDatabaseName("tennis");
            mysqlDataSource.setUseSSL(false);
            mysqlDataSource.setServerTimezone("Europe/Paris");
            mysqlDataSource.setUser("JDBC");
            mysqlDataSource.setPassword("javacore");

            dataSource = mysqlDataSource;
        } catch (SQLException e) {
            // En cas d'erreur de configuration (par exemple, un mauvais nom de base de données),
            // le programme se termine avec une erreur.
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de l'initialisation du DataSource", e);
        }
    }

    public static void main(String... args) {
        // Le code dans main reste le même, car la logique d'obtention de la connexion est encapsulée
        ajouterJoueur("poinas", "Yannick", 44L, 'H');
        modifierJoueur("Federer", "Roger", 20L);
        supprimerJoueur(45L);
        supprimerJoueur(46L);
    }

    private static Connection getConnection() throws SQLException {
        // Méthode utilitaire pour obtenir une connexion du pool
        return dataSource.getConnection();
    }

    private static void ajouterJoueur(String nom, String prenom, long identifiant, char sexe) {
        try (Connection conn = getConnection()) { // Utilisation d'un try-with-resources pour fermer automatiquement la connexion
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO JOUEUR (ID, NOM, PRENOM, SEXE) VALUES (?, ?, ?, ?)");
            preparedStatement.setLong(1, identifiant);
            preparedStatement.setString(2, nom);
            preparedStatement.setString(3, prenom);
            preparedStatement.setString(4, String.valueOf(sexe));
            int nbEnregistrementsAjoutes = preparedStatement.executeUpdate();
            System.out.println("Ajout : " + nbEnregistrementsAjoutes + " ligne(s) ajoutée(s).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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