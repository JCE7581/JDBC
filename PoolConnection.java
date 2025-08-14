// Le "package" est un moyen d'organiser vos classes en dossiers logiques.
// C'est comme le chemin d'accès à votre fichier.
package com.mycompany.tennis;

// Importation des classes nécessaires pour ce fichier. On les "importe" pour pouvoir les utiliser.
import com.mycompany.tennis.entity.Joueur; // On a besoin de la classe 'Joueur' pour créer des objets de ce type.
import com.mycompany.tennis.repository.JoueurRepositoryImpl; // On a besoin de la classe 'JoueurRepositoryImpl' pour interagir avec la base de données.
import org.apache.commons.dbcp2.BasicDataSource; // On importe l'outil qui gère notre pool de connexions.
import java.sql.Connection; // On importe la classe 'Connection' qui représente une connexion à la base de données.
import java.sql.SQLException; // On importe 'SQLException' pour pouvoir gérer les erreurs liées à la base de données.

// Déclaration de la classe principale 'PoolConnection'. C'est le nom de notre fichier.
public class PoolConnection {

    // Déclaration de la variable 'dataSource' qui va gérer notre pool de connexions.
    // 'private' : La variable n'est accessible que dans cette classe.
    // 'static' : Il n'y a qu'une seule instance de cette variable pour toute l'application.
    private static BasicDataSource dataSource;

    // Ce bloc 'static' est exécuté une seule fois, au tout début, lorsque la classe est chargée en mémoire.
    // C'est l'endroit parfait pour initialiser des choses qui ne changent pas, comme notre pool de connexions.
    static {
        dataSource = new BasicDataSource(); // Crée une nouvelle instance de notre gestionnaire de pool.
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver"); // Indique le pilote JDBC pour MySQL.
        dataSource.setUrl("jdbc:mysql://localhost:3306/tennis?useSSL=false&serverTimezone=Europe/Paris"); // Définit l'adresse et les paramètres de la base de données.
        dataSource.setUsername("JDBC"); // Nom d'utilisateur de la base de données.
        dataSource.setPassword("javacore"); // Mot de passe de la base de données.
        dataSource.setInitialSize(5); // Le nombre de connexions créées au démarrage.
        dataSource.setMaxTotal(10); // Le nombre maximum de connexions actives en même temps.
    }

    // Cette méthode publique permet à d'autres classes d'obtenir une connexion depuis notre pool.
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection(); // Renvoie une connexion disponible depuis le pool.
    }

    /**
     * Cette méthode 'main' est le point d'entrée de notre programme.
     * C'est par ici que l'exécution commence. Le code à l'intérieur sera exécuté.
     * @param args Les arguments passés à la ligne de commande (non utilisés ici).
     */
    public static void main(String... args) {
        // Crée une instance de notre classe 'JoueurRepositoryImpl' pour pouvoir utiliser ses méthodes.
        JoueurRepositoryImpl joueurDao = new JoueurRepositoryImpl();

        // Affiche un message dans la console.
        System.out.println("--- Création d'un joueur ---");
        // Crée un nouvel objet 'Joueur'.
        Joueur nouveauJoueur = new Joueur();
        // Renseigne les attributs du joueur que nous venons de créer.
        nouveauJoueur.setNom("Djokovic");
        nouveauJoueur.setPrenom("Novak");
        nouveauJoueur.setSexe('H');
        // Appelle la méthode 'create' de notre repository pour ajouter le joueur à la base de données.
        joueurDao.create(nouveauJoueur);

        // Affiche une ligne vide pour une meilleure lisibilité.
        System.out.println("\n--- Récupération d'un joueur ---");
        // Appelle la méthode 'getById' pour récupérer le joueur avec l'ID 1L.
        // La lettre 'L' indique que c'est un 'Long', le type d'ID que nous utilisons.
        Joueur joueurRecupere = joueurDao.getById(1L);
        // Vérifie si un joueur a été trouvé (l'objet n'est pas 'null').
        if (joueurRecupere != null) {
            System.out.println("Joueur trouvé : " + joueurRecupere.getNom() + " " + joueurRecupere.getPrenom());
        } else {
            System.out.println("Aucun joueur trouvé avec cet ID.");
        }

        // Affiche une ligne vide.
        System.out.println("\n--- Modification d'un joueur ---");
        // Vérifie si le joueur à modifier a bien été récupéré.
        if (joueurRecupere != null) {
            // Modifie le nom et le prénom de l'objet 'joueurRecupere'.
            joueurRecupere.setNom("Nadal");
            joueurRecupere.setPrenom("Rafael");
            // Appelle la méthode 'update' pour enregistrer les modifications dans la base de données.
            joueurDao.update(joueurRecupere);
        }

        // Affiche une ligne vide.
        System.out.println("\n--- Suppression d'un joueur ---");
        // Appelle la méthode 'delete' pour supprimer le joueur avec l'ID 2L.
        joueurDao.delete(2L);

        // Affiche une ligne vide.
        System.out.println("\n--- Liste de tous les joueurs ---");
        // Appelle la méthode 'listAll' qui renvoie une liste de tous les joueurs.
        // '.forEach' est une boucle qui parcourt chaque élément de la liste.
        // Pour chaque 'joueur' dans la liste, il exécute le code entre les accolades.
        joueurDao.listAll().forEach(joueur -> System.out.println(joueur.getNom() + " " + joueur.getPrenom()));
    }
}