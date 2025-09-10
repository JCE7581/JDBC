// Importation des classes nécessaires à la configuration du pool de connexions
import com.zaxxer.hikari.HikariConfig;       // Classe qui permet de définir les paramètres du pool (URL, utilisateur, mot de passe, etc.)
import com.zaxxer.hikari.HikariDataSource;   // Classe qui représente le pool de connexions lui-même (objet que l'on utilisera pour obtenir des connexions)
import javax.sql.DataSource;                 // Interface standard JDBC pour les sources de données (DataSource), utilisée pour l'abstraction

// Déclaration de la classe AppDataSource
public class AppDataSource {

    // Déclaration d'une variable statique (partagée par toutes les classes) qui contiendra le pool de connexions
    private static HikariDataSource dataSource;

    // Bloc static : ce bloc est exécuté automatiquement UNE SEULE FOIS, au moment où la classe est chargée en mémoire
    static {
        // Création d'un objet de configuration HikariCP
        HikariConfig config = new HikariConfig();

        // Définition de l'URL JDBC pour se connecter à la base de données MySQL
        // - localhost : le serveur MySQL est sur la même machine
        // - 3306 : port par défaut de MySQL
        // - jdbc_demo : nom de la base de données
        // - serverTimezone=Europe/Paris : pour éviter les erreurs de fuseau horaire
        // - useSSL=false : désactive SSL (sécurité inutile en local)
        config.setJdbcUrl("jdbc:mysql://localhost:3306/jdbc_demo?serverTimezone=Europe/Paris&useSSL=false");

        // Définition du nom d'utilisateur MySQL (doit correspondre à un utilisateur existant dans MySQL)
        config.setUsername("copilot");

        // Définition du mot de passe associé à l'utilisateur MySQL
        config.setPassword("copilot");

        // Configuration du pool de connexions :

        // Nombre maximum de connexions que le pool peut contenir en même temps
        config.setMaximumPoolSize(10); // Par exemple : 10 utilisateurs simultanés

        // Nombre minimum de connexions qui doivent rester ouvertes même si elles ne sont pas utilisées
        config.setMinimumIdle(2); // Cela évite d'avoir à recréer des connexions à chaque fois
        // Temps maximum (en millisecondes) pendant lequel une connexion inactive peut rester ouverte avant d'être fermée
        config.setIdleTimeout(30000); // 30 secondes

        // Temps maximum (en millisecondes) que le pool attend pour fournir une connexion avant de lever une erreur
        config.setConnectionTimeout(30000); // 30 secondes

        // Création effective du pool de connexions à partir de la configuration définie ci-dessus
        dataSource = new HikariDataSource(config);
    }

    // Méthode publique et statique qui permet à d'autres classes d'accéder au pool de connexions
    // Elle retourne un objet de type DataSource (interface standard JDBC)
    public static DataSource getDataSource() {
        return dataSource;
    }
}

//✅ Résumé pédagogique
//        Élément	Rôle
//        HikariConfig	Permet de définir les paramètres du pool
//        HikariDataSource	Est le pool de connexions lui-même
//getDataSource()	Permet à d'autres classes (comme les Repository) d'obtenir une connexion à la base
//        Bloc static	Initialise le pool une seule fois au démarrage de l'application
