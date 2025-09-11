// Le package "repository" contient les classes qui accèdent à la base de données
package repository;

// On importe la classe Utilisateur (notre modèle de données)
import model.Utilisateur;

// On importe l'interface DataSource (pour gérer les connexions)
import javax.sql.DataSource;

// On importe les classes JDBC nécessaires
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Déclaration de la classe UserRepository
// Elle contient toutes les méthodes pour interagir avec la table "utilisateurs"
public class UserRepository {

    // Attribut privé : la source de données (HikariCP dans notre cas)
    private final DataSource dataSource;

    // Constructeur : on injecte la DataSource au moment de créer le repository
    public UserRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // 1️⃣ Méthode pour récupérer tous les utilisateurs
    public List<Utilisateur> findAll() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery()
        ) {
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setEmail(rs.getString("email"));
                utilisateurs.add(u);
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur dans findAll : " + e.getMessage());
        }

        return utilisateurs;
    }

    // 2️⃣ Méthode pour récupérer un utilisateur par son ID
    public Utilisateur findById(int id) {
        String sql = "SELECT * FROM utilisateurs WHERE id = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id); // Remplace le ? par l'ID
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilisateur(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("email")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur dans findById : " + e.getMessage());
        }

        return null; // Aucun utilisateur trouvé
    }

    // 3️⃣ Méthode pour ajouter un nouvel utilisateur
    public boolean save(Utilisateur u) {
        String sql = "INSERT INTO utilisateurs (nom, email) VALUES (?, ?)";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getEmail());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        u.setId(generatedKeys.getInt(1)); // Récupère l'ID auto-généré
                    }
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("❌ Erreur dans save : " + e.getMessage());
        }

        return false;
    }

    // 4️⃣ Méthode pour mettre à jour un utilisateur existant
    public boolean update(Utilisateur u) {
        String sql = "UPDATE utilisateurs SET nom = ?, email = ? WHERE id = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setString(1, u.getNom());
            stmt.setString(2, u.getEmail());
            stmt.setInt(3, u.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Erreur dans update : " + e.getMessage());
        }

        return false;
    }

    // 5️⃣ Méthode pour supprimer un utilisateur par son ID
    public boolean delete(int id) {
        String sql = "DELETE FROM utilisateurs WHERE id = ?";

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)
        ) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("❌ Erreur dans delete : " + e.getMessage());
        }

        return false;
    }
}

//✅ Résumé pédagogique
//        Méthode	Action
//        findAll()	Lire tous les utilisateurs
//        findById(int id)	Lire un utilisateur par ID
//        save(Utilisateur u)	Ajouter un utilisateur
//        update(Utilisateur u)	Modifier un utilisateur
//        delete(int id)	Supprimer un utilisateur