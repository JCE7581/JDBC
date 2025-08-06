package com.mycompany.tennis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RechercherUneLigne {

    public static void main(String... args) {

        Connection conn = null;
        try {
            // Étape 1 : Établir la connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&serverTimezone=Europe/Paris", "JDBC", "javacore");

            // Étape 2 : Préparer la requête SQL avec des placeholders (?)
            // On utilise deux conditions dans la clause WHERE (NOM=? AND PRENOM=?)
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT ID, NOM, PRENOM FROM JOUEUR WHERE NOM=? AND PRENOM=?");

            // Étape 3 : Définir les valeurs pour les placeholders
            String nomRecherche = "POINAS";
            String prenomRecherche = "Yannick";

            preparedStatement.setString(1, nomRecherche);  // 1er '?' -> NOM
            preparedStatement.setString(2, prenomRecherche); // 2e '?' -> PRENOM

            // Étape 4 : Exécuter la requête
            ResultSet rs = preparedStatement.executeQuery();

            // Étape 5 : Traiter le résultat
            if (rs.next()) {
                // Si une ligne a été trouvée
                final long id = rs.getLong("ID");
                final String nom = rs.getString("NOM");
                final String prenom = rs.getString("PRENOM");

                System.out.println("Joueur trouvé !");
                System.out.println("ID : " + id + ", Nom : " + nom + ", Prénom : " + prenom);
            } else {
                // Si aucune ligne n'a été trouvée
                System.out.println("Aucun joueur trouvé avec ce nom et prénom.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}