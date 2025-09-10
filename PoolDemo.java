// Importation des classes nécessaires pour gérer la connexion à la base de données
import java.sql.Connection;     // Classe qui représente une connexion à la base de données
import java.sql.SQLException;   // Classe qui gère les erreurs liées à SQL (ex : mauvaise requête, problème de connexion)

// Déclaration de la classe principale qui va tester le pool de connexions
public class PoolDemo {

    // Méthode principale : c'est le point d'entrée de l'application Java
    public static void main(String[] args) {

        // Bloc try-catch : permet de gérer les erreurs qui peuvent survenir pendant l'exécution
        try {
            // 🔄 Récupération d'une connexion depuis le pool HikariCP
            // AppDataSource.getDataSource() : méthode statique qui retourne le pool de connexions
            // .getConnection() : demande une connexion disponible dans le pool
            Connection conn = AppDataSource.getDataSource().getConnection();

            // ✅ Si la connexion est réussie, on affiche un message dans la console
            System.out.println("Connexion réussie via le pool HikariCP !");

            // 🔒 Fermeture de la connexion
            // ATTENTION : ici, on ne détruit pas la connexion, on la rend au pool pour qu'elle soit réutilisée
            conn.close();

        } catch (SQLException e) {
            // ❌ Si une erreur survient (ex : base inaccessible, mauvais mot de passe...), elle est capturée ici
            // e.getMessage() : retourne le message d'erreur détaillé
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}

//✅ Résumé pédagogique
//        Élément	Rôle
//        Connection	Objet qui permet d’envoyer des requêtes SQL à la base
//AppDataSource.getDataSource()	Méthode qui retourne le pool HikariCP
//        getConnection()	Demande une connexion disponible dans le pool
//conn.close()	Rend la connexion au pool (elle n’est pas détruite)
//        try-catch	Permet de gérer les erreurs sans faire planter le programme
