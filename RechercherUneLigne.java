package com.mycompany.tennis;

// Importe les classes nécessaires pour interagir avec la base de données
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RechercherUneLigne {

    public static void main(String... args) {

        // Déclare une variable pour la connexion à la base de données, initialisée à null.
        // Cela permettra de la fermer dans le bloc 'finally' même si la connexion échoue.
        Connection conn = null;
        try {
            // Étape 1 : Établir la connexion à la base de données.
            // On utilise la classe DriverManager pour se connecter à la base de données MySQL.
            // L'URL spécifie l'adresse, le port, le nom de la base ('tennis') et le fuseau horaire.
            // "JDBC" et "javacore" sont les identifiants et le mot de passe de l'utilisateur.
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tennis?useSSL=false&serverTimezone=Europe/Paris", "JDBC", "javacore");

            // Étape 2 : Préparer la requête SQL.
            // On utilise un PreparedStatement pour se prémunir des injections SQL.
            // Les points d'interrogation '?' sont des placeholders pour les valeurs qui seront fournies plus tard.
            // La clause 'WHERE' permet de filtrer les résultats.
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT ID, NOM, PRENOM FROM JOUEUR WHERE NOM=? AND PRENOM=?");

            // Étape 3 : Définir les valeurs pour les placeholders.
            // On définit les critères de recherche.
            String nomRecherche = "POINAS";
            String prenomRecherche = "Yannick";

            // On attribue les valeurs aux placeholders de la requête.
            // La méthode setString(index, valeur) est utilisée. Les index commencent à 1.
            preparedStatement.setString(1, nomRecherche);  // Le 1er '?' est remplacé par 'POINAS'.
            preparedStatement.setString(2, prenomRecherche); // Le 2e '?' est remplacé par 'Yannick'.

            // Étape 4 : Exécuter la requête.
            // La méthode executeQuery() est utilisée pour les requêtes SELECT qui retournent des données.
            // Le résultat est stocké dans un objet ResultSet.
            ResultSet rs = preparedStatement.executeQuery();

            // Étape 5 : Traiter le résultat.
            // La méthode rs.next() déplace le curseur sur la première ligne du résultat.
            // Elle retourne 'true' s'il y a une ligne, 'false' sinon.
            if (rs.next()) {
                // Si une ligne a été trouvée, on récupère les données des colonnes par leur nom.
                final long id = rs.getLong("ID");
                final String nom = rs.getString("NOM");
                final String prenom = rs.getString("PRENOM");

                // On affiche les informations du joueur trouvé.
                System.out.println("Joueur trouvé !");
                System.out.println("ID : " + id + ", Nom : " + nom + ", Prénom : " + prenom);
            } else {
                // Si aucune ligne n'a été trouvée (rs.next() a retourné false), on affiche un message.
                System.out.println("Aucun joueur trouvé avec ce nom et prénom.");
            }

        } catch (SQLException e) {
            // Si une erreur SQL se produit (connexion impossible, requête invalide...),
            // on affiche la trace de l'erreur dans la console.
            e.printStackTrace();
        } finally {
            // Le bloc 'finally' est toujours exécuté, qu'il y ait eu une erreur ou non.
            // Il sert à s'assurer que la connexion à la base de données est fermée pour libérer les ressources.
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