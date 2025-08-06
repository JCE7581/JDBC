package com.mycompany.tennis;

// Importe les classes nécessaires pour gérer les connexions SQL.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestDeConnection {
    public static void main(String... args){
        // Déclare une variable de type Connection, initialisée à null.
        // Cela permet de s'assurer que la connexion peut être fermée même si son établissement échoue.
        Connection conn = null;
        try {
            // Tente d'établir une connexion à la base de données.
            // L'URL de connexion contient toutes les informations nécessaires :
            // - Le type de base de données (jdbc:mysql)
            // - L'adresse du serveur (localhost:3306)
            // - Le nom de la base de données (tennis)
            // - Les paramètres de connexion (useSSL, useLegacyDatetimeCode, serverTimezone)
            // Les deux derniers arguments sont le nom d'utilisateur ("JDBC") et le mot de passe ("javacore").
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=Europe/Paris","JDBC","javacore");

            // Si la connexion réussit, ce message est affiché.
            System.out.println("success");
        } catch (SQLException e) {
            // Si une erreur de connexion survient (par exemple, mauvais mot de passe ou serveur non disponible),
            // une exception de type SQLException est capturée.
            // La méthode printStackTrace() affiche les détails de l'erreur, ce qui est utile pour le débogage.
            e.printStackTrace();
        }
        finally {
            // Le bloc 'finally' s'exécute toujours, qu'une exception ait été levée ou non.
            // Son rôle principal est de s'assurer que les ressources sont correctement libérées.
            try {
                // Vérifie si la connexion est bien établie (n'est pas null) avant de la fermer.
                if (conn!=null) {
                    conn.close();
                }
            } catch (SQLException e) {
                // Gère les exceptions qui pourraient survenir lors de la fermeture de la connexion.
                e.printStackTrace();
            }
        }
    }
}