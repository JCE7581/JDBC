// 📦 Importation de la classe qui fournit la DataSource configurée avec HikariCP
// Cette classe permet de gérer les connexions à la base de données de manière optimisée
import config.HikariDataSourceExample;

// 📦 Importation de la classe Utilisateur, qui représente une ligne de la table "utilisateurs"
import model.Utilisateur;

// 📦 Importation du UserRepository, qui contient toutes les méthodes pour interagir avec la base de données
import repository.UserRepository;

// 📦 Importation de la classe List, qui permet de stocker plusieurs objets dans une liste
import java.util.List;

// 📦 Importation de la classe Scanner, qui permet de lire les entrées clavier de l'utilisateur
import java.util.Scanner;

// 🧱 Déclaration de la classe principale qui contient le menu console
public class ConsoleMenu {

    // 🚪 Méthode main : point d'entrée du programme Java
    public static void main(String[] args) {

        // 🔌 Création d'une instance du UserRepository
        // On lui passe la DataSource HikariCP pour qu'il puisse accéder à la base de données
        UserRepository repo = new UserRepository(HikariDataSourceExample.getDataSource());

        // 🧾 Création d'un Scanner pour lire les entrées clavier de l'utilisateur
        Scanner scanner = new Scanner(System.in);

        // 🔁 Boucle infinie : le menu s'affiche tant que l'utilisateur ne choisit pas "0" pour quitter
        while (true) {

            // 🖥️ Affichage du menu principal avec les différentes options
            System.out.println("\n=== MENU UTILISATEURS ===");
            System.out.println("1. Afficher tous les utilisateurs");
            System.out.println("2. Rechercher un utilisateur par ID");
            System.out.println("3. Ajouter un utilisateur");
            System.out.println("4. Modifier un utilisateur");
            System.out.println("5. Supprimer un utilisateur");
            System.out.println("0. Quitter");

            // 🧭 Demande du choix à l'utilisateur
            System.out.print("Choix : ");

            // 📥 Lecture du choix (entier) saisi par l'utilisateur
            int choix = scanner.nextInt();

            // 🔄 Consommation du retour à la ligne (nécessaire après nextInt pour éviter des bugs)
            scanner.nextLine();

            // 🔀 Utilisation d'un switch pour exécuter l'action correspondant au choix
            switch (choix) {

                case 1:
                    // 📋 Cas 1 : afficher tous les utilisateurs
                    // Appel à la méthode findAll() du repository pour récupérer tous les utilisateurs
                    List<Utilisateur> utilisateurs = repo.findAll();

                    // 🔄 Parcours de la liste des utilisateurs
                    for (Utilisateur u : utilisateurs) {
                        // 🖨️ Affichage des informations de chaque utilisateur
                        System.out.println("👤 " + u.getId() + " - " + u.getNom() + " - " + u.getEmail());
                    }
                    break;

                case 2:
                    // 🔍 Cas 2 : rechercher un utilisateur par son ID
                    System.out.print("ID à rechercher : ");

                    // 📥 Lecture de l'ID saisi par l'utilisateur
                    int idRecherche = scanner.nextInt();
                    scanner.nextLine(); // 🔄 Consommation du retour à la ligne

                    // 🔍 Appel à la méthode findById() du repository
                    Utilisateur u1 = repo.findById(idRecherche);

                    // ✅ Si l'utilisateur est trouvé, on affiche ses infos
                    if (u1 != null) {
                        System.out.println("✅ Trouvé : " + u1.getNom() + " - " + u1.getEmail());
                    } else {
                        // ❌ Sinon, on affiche un message d'erreur
                        System.out.println("❌ Aucun utilisateur trouvé avec cet ID.");
                    }
                    break;

                case 3:
                    // ➕ Cas 3 : ajouter un nouvel utilisateur
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine(); // 📥 Lecture du nom

                    System.out.print("Email : ");
                    String email = scanner.nextLine(); // 📥 Lecture de l'email

                    // 🧱 Création d'un nouvel objet Utilisateur
                    Utilisateur nouveau = new Utilisateur();
                    nouveau.setNom(nom);       // 🧑 On lui attribue le nom saisi
                    nouveau.setEmail(email);   // 📧 On lui attribue l'email saisi

                    // 💾 Appel à la méthode save() du repository pour enregistrer l'utilisateur
                    if (repo.save(nouveau)) {
                        // ✅ Si l'ajout réussit, on affiche l'ID généré
                        System.out.println("✅ Utilisateur ajouté avec ID : " + nouveau.getId());
                    } else {
                        // ❌ Sinon, on affiche un message d'erreur
                        System.out.println("❌ Échec de l'ajout.");
                    }
                    break;

                case 4:
                    // ✏️ Cas 4 : modifier un utilisateur existant
                    System.out.print("ID à modifier : ");
                    int idModif = scanner.nextInt(); // 📥 Lecture de l'ID
                    scanner.nextLine(); // 🔄 Consommation du retour à la ligne

                    // 🔍 On récupère l'utilisateur existant avec findById()
                    Utilisateur existant = repo.findById(idModif);

                    // ✅ Si l'utilisateur existe, on demande les nouvelles infos
                    if (existant != null) {
                        System.out.print("Nouveau nom : ");
                        String nouveauNom = scanner.nextLine();

                        System.out.print("Nouvel email : ");
                        String nouvelEmail = scanner.nextLine();

                        // 🧱 Mise à jour de l'objet Utilisateur
                        existant.setNom(nouveauNom);
                        existant.setEmail(nouvelEmail);

                        // 💾 Appel à la méthode update() du repository
                        if (repo.update(existant)) {
                            System.out.println("✅ Utilisateur mis à jour.");
                        } else {
                            System.out.println("❌ Échec de la mise à jour.");
                        }
                    } else {
                        // ❌ Si l'utilisateur n'existe pas
                        System.out.println("❌ Utilisateur introuvable.");
                    }
                    break;

                case 5:
                    // 🗑️ Cas 5 : supprimer un utilisateur
                    System.out.print("ID à supprimer : ");
                    int idSuppr = scanner.nextInt(); // 📥 Lecture de l'ID
                    scanner.nextLine(); // 🔄 Consommation du retour à la ligne

                    // 🗑️ Appel à la méthode delete() du repository
                    if (repo.delete(idSuppr)) {
                        System.out.println("✅ Utilisateur supprimé.");
                    } else {
                        System.out.println("❌ Échec de la suppression.");
                    }
                    break;

                case 0:
                    // 🚪 Cas 0 : quitter le programme
                    System.out.println("👋 Au revoir !");
                    return; // 🔚 On sort de la méthode main → fin du programme

                default:
                    // ⚠️ Si l'utilisateur entre un choix invalide
                    System.out.println("❌ Choix invalide.");
            }
        }
    }
}
