
// On importe la classe MysqlDataSource fournie par le driver JDBC MySQL
import com.mysql.cj.jdbc.MysqlDataSource;

// On importe les classes nécessaires pour gérer la connexion
import java.sql.Connection;
import java.sql.SQLException;

public class DataSourceDemo {
    public static void main(String[] args) {

        // Étape 1 : Créer une instance de MysqlDataSource
        // Cette classe permet de configurer la connexion à la base de données
        MysqlDataSource dataSource = new MysqlDataSource();

        // Étape 2 : Définir l'URL JDBC complète
        // Elle contient le nom du serveur, le port, la base de données, le fuseau horaire et la désactivation du SSL
        dataSource.setURL("jdbc:mysql://localhost:3306/jdbc_demo?serverTimezone=Europe/Paris&useSSL=false");

        // Étape 3 : Définir les identifiants de connexion
        dataSource.setUser("copilot"); // Nom d'utilisateur MySQL
        dataSource.setPassword("copilot"); // Remplace par ton mot de passe MySQL

        try {
            // Étape 4 : Obtenir une connexion à partir de la DataSource
            // Cela remplace DriverManager.getConnection(...)
            Connection conn = dataSource.getConnection();

            // Étape 5 : Vérifier que la connexion fonctionne
            System.out.println("✅ Connexion réussie avec DataSource !");

            // Étape 6 : Fermer proprement la connexion
            conn.close();

        } catch (SQLException e) {
            // En cas d'erreur, afficher le message
            System.out.println("❌ Erreur de connexion : " + e.getMessage());
        }
    }
}
