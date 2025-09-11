// On importe l'interface DataSource, qui permet de gérer les connexions à une base de données
import javax.sql.DataSource;

// On importe la classe Connection, qui représente une connexion active à la base
import java.sql.Connection;

// SQLException est l'exception levée si une erreur survient lors de l'accès à la base
import java.sql.SQLException;

// MysqlDataSource est une implémentation concrète de DataSource fournie par le driver MySQL
import com.mysql.cj.jdbc.MysqlDataSource;

// Déclaration de la classe principale
public class SimpleDataSource {

    // Constante contenant l'URL de connexion JDBC
    // jdbc:mysql:// → protocole JDBC pour MySQL
    // localhost:3306 → adresse du serveur MySQL (local) et port
    // JDBC → nom de la base de données
    // serverTimezone=Europe/Paris → corrige les erreurs de fuseau horaire
    // useSSL=false → désactive SSL (utile en local pour éviter les avertissements)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/JDBC?serverTimezone=Europe/Paris&useSSL=false";

    // Nom d'utilisateur pour se connecter à la base MySQL
    private static final String DB_USER = "JDBC";

    // Mot de passe associé à l'utilisateur
    private static final String DB_PASSWORD = "copilot";

    // Méthode statique qui retourne une DataSource configurée
    public static DataSource getDataSource() {
        // On crée une instance de MysqlDataSource (spécifique à MySQL)
        MysqlDataSource dataSource = new MysqlDataSource();

        // On configure l'URL de connexion
        dataSource.setURL(DB_URL);

        // On configure le nom d'utilisateur
        dataSource.setUser(DB_USER);

        // On configure le mot de passe
        dataSource.setPassword(DB_PASSWORD);

        // On retourne l'objet DataSource prêt à l'emploi
        return dataSource;
    }

    // Méthode principale pour tester la connexion à la base
    public static void main(String[] args) {
        // Bloc try-with-resources : la connexion sera automatiquement fermée à la fin du bloc
        try (Connection conn = getDataSource().getConnection()) {
            // Si la connexion réussit, on affiche un message de succès
            System.out.println("✅ Connexion réussie avec DataSource !");
        } catch (SQLException e) {
            // Si une erreur survient, on affiche le message d'erreur
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }
}
