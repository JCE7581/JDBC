import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConnexion {
    public static void main(String[] args) {
        // URL de connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/jdbc_demo?serverTimezone=Europe/Paris&useSSL=false";
        // Identifiants MySQL
        String user = "copilot";
        String password = "copilot"; // Remplace par ton vrai mot de passe

        try {
            // Tentative de connexion
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connexion réussie !");
            conn.close(); // On ferme la connexion proprement
        } catch (SQLException e) {
            // En cas d'erreur, on affiche le message
            System.out.println("Erreur de connexion : " + e.getMessage());
        }
    }
}
