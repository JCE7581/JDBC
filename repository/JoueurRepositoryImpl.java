package com.mycompany.tennis.repository;

import com.mycompany.tennis.PoolConnection;
import com.mycompany.tennis.entity.Joueur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * C'est la classe qui implémente l'interface 'JoueurRepository'.
 * Elle contient toute la logique pour interagir avec la base de données.
 * On l'appelle souvent un 'DAO' (Data Access Object).
 */
public class JoueurRepositoryImpl implements JoueurRepository {

    @Override
    public void create(Joueur joueur) {
        // Le bloc 'try-with-resources' est une fonctionnalité de Java qui assure que
        // la connexion (conn) sera automatiquement fermée à la fin du bloc, même en cas d'erreur.
        try (Connection conn = PoolConnection.getConnection()) { // On demande une connexion au pool.

            // On prépare une requête SQL. Les '?' sont des marqueurs de position pour les valeurs.
            // L'utilisation de 'PreparedStatement' est une bonne pratique pour éviter les injections SQL.
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO JOUEUR (NOM, PRENOM, SEXE) VALUES (?, ?, ?)");

            // On remplit les marqueurs de position avec les données de l'objet 'joueur'.
            preparedStatement.setString(1, joueur.getNom()); // Le premier '?' prend la valeur du nom.
            preparedStatement.setString(2, joueur.getPrenom()); // Le deuxième '?' prend la valeur du prénom.
            preparedStatement.setString(3, String.valueOf(joueur.getSexe())); // Le troisième '?' prend la valeur du sexe.

            // 'executeUpdate()' est utilisée pour les requêtes qui modifient la base de données (INSERT, UPDATE, DELETE).
            int nbEnregistrementsAjoutes = preparedStatement.executeUpdate();
            System.out.println("Ajout : " + nbEnregistrementsAjoutes + " ligne(s) ajoutée(s).");
        } catch (SQLException e) {
            // Si une erreur liée à la base de données survient (ex: mauvais mot de passe),
            // on l'affiche pour comprendre l'origine du problème.
            e.printStackTrace();
        }
    }

    @Override
    public void update(Joueur joueur) {
        try (Connection conn = PoolConnection.getConnection()) {
            // Requête SQL pour mettre à jour les données.
            PreparedStatement preparedStatement = conn.prepareStatement("UPDATE JOUEUR SET NOM=?, PRENOM=? WHERE ID=?");

            // On remplit les marqueurs de position avec les nouvelles valeurs et l'ID du joueur à modifier.
            preparedStatement.setString(1, joueur.getNom());
            preparedStatement.setString(2, joueur.getPrenom());
            preparedStatement.setLong(3, joueur.getId()); // On utilise l'ID pour cibler le joueur à mettre à jour.

            int nbEnregistrementsModifies = preparedStatement.executeUpdate();
            System.out.println("Modification : " + nbEnregistrementsModifies + " ligne(s) modifiée(s).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        try (Connection conn = PoolConnection.getConnection()) {
            // Requête SQL pour supprimer une ligne.
            PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM JOUEUR WHERE ID=?");

            // On indique l'ID du joueur à supprimer.
            preparedStatement.setLong(1, id);

            int nbLignesSupprimees = preparedStatement.executeUpdate();
            System.out.println("Suppression : " + nbLignesSupprimees + " ligne(s) supprimée(s).");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Joueur getById(Long id) {
        Joueur joueur = null; // On initialise l'objet à 'null' au cas où aucun joueur ne serait trouvé.
        try (Connection conn = PoolConnection.getConnection()) {
            // Requête SQL de sélection (SELECT) pour récupérer un joueur par son ID.
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM JOUEUR WHERE ID=?");
            preparedStatement.setLong(1, id);

            // 'executeQuery()' est utilisée pour les requêtes qui renvoient des résultats (SELECT).
            // Le résultat est stocké dans un objet 'ResultSet'.
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) { // 'rs.next()' déplace le curseur sur la première ligne de résultat.
                // Elle renvoie 'true' s'il y a une ligne, 'false' sinon.
                joueur = new Joueur(); // Si une ligne est trouvée, on crée un nouvel objet Joueur.
                // On remplit cet objet avec les données des colonnes du ResultSet.
                joueur.setId(rs.getLong("ID"));
                joueur.setNom(rs.getString("NOM"));
                joueur.setPrenom(rs.getString("PRENOM"));
                joueur.setSexe(rs.getString("SEXE").charAt(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueur; // On retourne l'objet Joueur rempli ou 'null' si rien n'a été trouvé.
    }

    @Override
    public List<Joueur> listAll() {
        List<Joueur> joueurs = new ArrayList<>(); // On crée une liste vide qui va stocker tous les joueurs.
        try (Connection conn = PoolConnection.getConnection()) {
            // Requête SQL pour sélectionner tous les joueurs.
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM JOUEUR");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) { // La boucle 'while' continue tant qu'il y a des lignes dans le résultat.
                Joueur joueur = new Joueur(); // Pour chaque ligne, on crée un nouvel objet Joueur.
                joueur.setId(rs.getLong("ID"));
                joueur.setNom(rs.getString("NOM"));
                joueur.setPrenom(rs.getString("PRENOM"));
                joueur.setSexe(rs.getString("SEXE").charAt(0));
                joueurs.add(joueur); // On ajoute l'objet Joueur rempli à notre liste.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return joueurs; // On retourne la liste de tous les joueurs.
    }
}