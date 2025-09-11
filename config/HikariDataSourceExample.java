// Déclaration du package dans lequel se trouve cette classe.
// Cela permet d'organiser ton code en dossiers logiques.
package config;

// Importation de la classe HikariConfig du framework HikariCP.
// Elle sert à configurer les paramètres du pool de connexions.
import com.zaxxer.hikari.HikariConfig;

// Importation de la classe HikariDataSource du framework HikariCP.
// Elle représente le pool de connexions lui-même.
import com.zaxxer.hikari.HikariDataSource;

// Importation de l'interface DataSource de JDBC.
// Elle définit un moyen standard d'obtenir des connexions à une base de données.
import javax.sql.DataSource;

// Importation de la classe Connection de JDBC.
// Elle représente une connexion active à une base de données.
import java.sql.Connection;

// Importation de la classe SQLException.
// Elle est utilisée pour gérer les erreurs liées à la base de données.
import java.sql.SQLException;

// Déclaration de la classe publique nommée HikariDataSourceExample.
// Elle contient toute la logique pour configurer et tester le pool de connexions.
public class HikariDataSourceExample {

    // Déclaration d'une variable statique et finale nommée dataSource.
    // "static" signifie qu'elle appartient à la classe et non à une instance.
    // "final" signifie qu'on ne pourra pas la modifier après l'initialisation.
    // Elle est de type DataSource, donc elle permet d'obtenir des connexions.
    private static final DataSource dataSource;

    // Bloc statique : ce bloc est exécuté une seule fois quand la classe est chargée.
    // Il sert ici à configurer et initialiser le pool de connexions HikariCP.
    static {
        // Création d'une instance de HikariConfig.
        // C'est l'objet qui va contenir tous les paramètres du pool.
        HikariConfig config = new HikariConfig();

        // Définition de l'URL JDBC pour se connecter à la base MySQL.
        // "localhost" = base sur ton ordinateur.
        // "3306" = port par défaut de MySQL.
        // "JDBC" = nom de ta base de données.
        // "serverTimezone=Europe/Paris" = fuseau horaire.
        // "useSSL=false" = désactivation du chiffrement SSL.
        config.setJdbcUrl("jdbc:mysql://localhost:3306/JDBC?serverTimezone=Europe/Paris&useSSL=false");

        // Définition du nom d'utilisateur pour se connecter à la base.
        config.setUsername("JDBC");

        // Définition du mot de passe associé à l'utilisateur.
        config.setPassword("copilot");

        // Définition du nombre maximum de connexions dans le pool.
        // Cela limite le nombre de connexions simultanées à la base.
        config.setMaximumPoolSize(10); // max 10 connexions en même temps

        // Définition du nombre minimum de connexions qui restent ouvertes.
        // Cela permet d'avoir toujours des connexions prêtes à l'emploi.
        config.setMinimumIdle(2); // au moins 2 connexions toujours disponibles
        // Définition du temps d'inactivité avant qu'une connexion soit fermée.
        // Ici, une connexion inactive plus de 60 secondes sera fermée.
        config.setIdleTimeout(60000); // 60 000 ms = 60 secondes

        // Définition du temps maximal pour obtenir une connexion du pool.
        // Si aucune connexion n'est disponible après 30 secondes, une erreur est levée.
        config.setConnectionTimeout(30000); // 30 000 ms = 30 secondes

        // Définition du nom du pool de connexions.
        // Utile pour identifier le pool dans les logs ou les outils de monitoring.
        config.setPoolName("MonPoolHikari");

        // Création de la DataSource Hikari à partir de la configuration.
        // Cela initialise le pool de connexions avec tous les paramètres définis.
        dataSource = new HikariDataSource(config);
    }

    // Méthode publique et statique pour récupérer la DataSource.
    // Elle permet à d'autres classes d'accéder au pool de connexions.
    public static DataSource getDataSource() {
        // On retourne simplement la DataSource créée dans le bloc statique.
        return dataSource;
    }

    // Méthode principale : point d'entrée de l'application.
    // Elle est utilisée ici pour tester la connexion à la base.
    public static void main(String[] args) {
        // Bloc try-with-resources : permet d'ouvrir une ressource (ici une connexion)
        // et de la fermer automatiquement à la fin du bloc.
        try (Connection conn = getDataSource().getConnection()) {
            // Si la connexion est réussie, on affiche un message de succès.
            System.out.println("✅ Connexion réussie avec HikariCP !");
        } catch (SQLException e) {
            // Si une erreur survient, on affiche le message d'erreur.
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
}
