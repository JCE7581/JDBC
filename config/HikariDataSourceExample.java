
package config;

// Importation des classes nécessaires
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class HikariDataSourceExample {

    // On déclare une DataSource statique pour la partager dans toute l'application
    private static final DataSource dataSource;

    // Bloc statique exécuté une seule fois au démarrage
    static {
        // Création de la configuration HikariCP
        HikariConfig config = new HikariConfig();

        // URL de connexion JDBC avec fuseau horaire et SSL désactivé
        config.setJdbcUrl("jdbc:mysql://localhost:3306/JDBC?serverTimezone=Europe/Paris&useSSL=false");

        // Identifiants de connexion
        config.setUsername("JDBC");
        config.setPassword("copilot");

        // Configuration optionnelle du pool
        config.setMaximumPoolSize(10); // max 10 connexions simultanées
        config.setMinimumIdle(2);      // au moins 2 connexions toujours prêtes
        config.setIdleTimeout(60000);  // 60 secondes avant de fermer une connexion inactive
        config.setConnectionTimeout(30000); // 30 secondes max pour obtenir une connexion
        config.setPoolName("MonPoolHikari"); // nom du pool (utile pour les logs)

        // Création de la DataSource Hikari à partir de la config
        dataSource = new HikariDataSource(config);
    }

    // Méthode pour récupérer la DataSource (réutilisable partout)
    public static DataSource getDataSource() {
        return dataSource;
    }

    // Méthode de test
    public static void main(String[] args) {
        // On récupère une connexion depuis le pool
        try (Connection conn = getDataSource().getConnection()) {
            System.out.println("✅ Connexion réussie avec HikariCP !");
        } catch (SQLException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
}
