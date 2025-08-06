package com.mycompany.tennis;

// Importe les classes nécessaires pour interagir avec la base de données.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LireTouteLaTable {

    public static void main(String... args){

        // Déclare une variable pour la connexion à la base de données, initialisée à null.
        Connection conn = null;
        try {
            // Étape 1 : Établir la connexion à la base de données MySQL.
            // La méthode getConnection() prend l'URL, le nom d'utilisateur et le mot de passe.
            // L'URL spécifie l'emplacement du serveur, le nom de la base de données et les paramètres de connexion.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&serverTimezone=Europe/Paris", "JDBC", "javacore");

            // Étape 2 : Préparer la requête SQL.
            // On utilise un PreparedStatement, même sans paramètres, pour une meilleure pratique et cohérence.
            // La requête "SELECT ID, NOM, PRENOM FROM JOUEUR" récupère toutes les lignes des colonnes spécifiées.
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT ID, NOM, PRENOM FROM JOUEUR");

            // Étape 3 : Exécuter la requête.
            // La méthode executeQuery() est utilisée pour les requêtes SELECT qui retournent un ensemble de résultats (ResultSet).
            ResultSet rs = preparedStatement.executeQuery();

            // Étape 4 : Parcourir les résultats.
            System.out.println("--- Liste de tous les joueurs ---");

            // La boucle while parcourt chaque ligne du ResultSet.
            // rs.next() déplace le curseur à la ligne suivante et retourne 'true' s'il y a une ligne, 'false' sinon.
            while (rs.next()){
                // On récupère les valeurs de chaque colonne de la ligne actuelle.
                // rs.getLong() et rs.getString() sont utilisés en fonction du type de données.
                final long id = rs.getLong("ID");
                final String nom = rs.getString("NOM");
                final String prenom = rs.getString("PRENOM");

                // On affiche les informations du joueur.
                System.out.println("ID : " + id + ", Nom : " + nom + ", Prénom : " + prenom);
            }

            System.out.println("--- Fin de la liste ---");

        } catch (SQLException e){
            // Si une erreur SQL se produit (par exemple, la connexion échoue),
            // on affiche la trace de l'erreur pour le débogage.
            e.printStackTrace();
        } finally {
            // Le bloc 'finally' s'exécute toujours.
            // Il est utilisé pour s'assurer que la connexion est fermée.
            try {
                // On vérifie que la connexion n'est pas nulle avant de la fermer.
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}