package com.mycompany.tennis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LireTouteLaTable {

    public static void main(String... args){

        Connection conn = null;
        try {
            // Étape 1 : Établir la connexion à la base de données
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&serverTimezone=Europe/Paris", "JDBC", "javacore");

            // Étape 2 : Préparer la requête SQL
            // La requête n'a pas de paramètres, mais on utilise quand même PreparedStatement
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT ID, NOM, PRENOM FROM JOUEUR");

            // Étape 3 : Exécuter la requête
            ResultSet rs = preparedStatement.executeQuery();

            // Étape 4 : Parcourir les résultats
            System.out.println("--- Liste de tous les joueurs ---");
            while (rs.next()){
                final long id = rs.getLong("ID");
                final String nom = rs.getString("NOM");
                final String prenom = rs.getString("PRENOM");

                System.out.println("ID : " + id + ", Nom : " + nom + ", Prénom : " + prenom);
            }

            System.out.println("--- Fin de la liste ---");

        } catch (SQLException e){
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