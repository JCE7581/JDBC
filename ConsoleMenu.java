// On importe la classe Utilisateur, notre modèle de données
import config.HikariDataSourceExample;
import model.Utilisateur;
import repository.UserRepository;

// On importe le repository qui contient les méthodes d'accès à la base de données

// On importe les classes nécessaires pour lire les entrées clavier et manipuler des listes
import java.util.List;
import java.util.Scanner;

// Classe principale qui affiche un menu console pour tester le UserRepository
public class ConsoleMenu {

    // Méthode main : point d'entrée du programme
    public static void main(String[] args) {

        // On crée une instance du repository en lui passant la DataSource HikariCP
        UserRepository repo = new UserRepository(HikariDataSourceExample.getDataSource());

        // Scanner permet de lire les entrées clavier de l'utilisateur
        Scanner scanner = new Scanner(System.in);

        // Boucle infinie pour afficher le menu jusqu'à ce que l'utilisateur choisisse de quitter
        while (true) {
            // Affichage du menu principal
            System.out.println("\n=== MENU UTILISATEURS ===");
            System.out.println("1. Afficher tous les utilisateurs");
            System.out.println("2. Rechercher un utilisateur par ID");
            System.out.println("3. Ajouter un utilisateur");
            System.out.println("4. Modifier un utilisateur");
            System.out.println("5. Supprimer un utilisateur");
            System.out.println("0. Quitter");
            System.out.print("Choix : ");

            // Lecture du choix de l'utilisateur
            int choix = scanner.nextInt();
            scanner.nextLine(); // Consomme le retour à la ligne après nextInt()

            // On utilise un switch pour exécuter l'action correspondant au choix
            switch (choix) {

                case 1:
                    // Cas 1 : afficher tous les utilisateurs
                    List<Utilisateur> utilisateurs = repo.findAll(); // appel à la méthode du repository
                    for (Utilisateur u : utilisateurs) {
                        // Affichage de chaque utilisateur
                        System.out.println("👤 " + u.getId() + " - " + u.getNom() + " - " + u.getEmail());
                    }
                    break;

                case 2:
                    // Cas 2 : rechercher un utilisateur par son ID
                    System.out.print("ID à rechercher : ");
                    int idRecherche = scanner.nextInt();
                    scanner.nextLine(); // consomme le retour à la ligne
                    Utilisateur u1 = repo.findById(idRecherche); // appel à la méthode du repository
                    if (u1 != null) {
                        // Si trouvé, on affiche ses infos
                        System.out.println("✅ Trouvé : " + u1.getNom() + " - " + u1.getEmail());
                    } else {
                        // Sinon, message d'erreur
                        System.out.println("❌ Aucun utilisateur trouvé avec cet ID.");
                    }
                    break;

                case 3:
                    // Cas 3 : ajouter un nouvel utilisateur
                    System.out.print("Nom : ");
                    String nom = scanner.nextLine(); // lecture du nom
                    System.out.print("Email : ");
                    String email = scanner.nextLine(); // lecture de l'email

                    // Création d'un nouvel objet Utilisateur
                    Utilisateur nouveau = new Utilisateur();
                    nouveau.setNom(nom);
                    nouveau.setEmail(email);

                    // Appel à la méthode save du repository
                    if (repo.save(nouveau)) {
                        System.out.println("✅ Utilisateur ajouté avec ID : " + nouveau.getId());
                    } else {
                        System.out.println("❌ Échec de l'ajout.");
                    }
                    break;

                case 4:
                    // Cas 4 : modifier un utilisateur existant
                    System.out.print("ID à modifier : ");
                    int idModif = scanner.nextInt();
                    scanner.nextLine();

                    // On récupère l'utilisateur existant
                    Utilisateur existant = repo.findById(idModif);
                    if (existant != null) {
                        // Si trouvé, on demande les nouvelles infos
                        System.out.print("Nouveau nom : ");
                        String nouveauNom = scanner.nextLine();
                        System.out.print("Nouvel email : ");
                        String nouvelEmail = scanner.nextLine();

                        // Mise à jour de l'objet
                        existant.setNom(nouveauNom);
                        existant.setEmail(nouvelEmail);

                        // Appel à la méthode update
                        if (repo.update(existant)) {
                            System.out.println("✅ Utilisateur mis à jour.");
                        } else {
                            System.out.println("❌ Échec de la mise à jour.");
                        }
                    } else {
                        System.out.println("❌ Utilisateur introuvable.");
                    }
                    break;

                case 5:
                    // Cas 5 : supprimer un utilisateur
                    System.out.print("ID à supprimer : ");
                    int idSuppr = scanner.nextInt();
                    scanner.nextLine();

                    // Appel à la méthode delete
                    if (repo.delete(idSuppr)) {
                        System.out.println("✅ Utilisateur supprimé.");
                    } else {
                        System.out.println("❌ Échec de la suppression.");
                    }
                    break;

                case 0:
                    // Cas 0 : quitter le programme
                    System.out.println("👋 Au revoir !");
                    return; // on sort de la méthode main

                default:
                    // Si l'utilisateur entre un choix invalide
                    System.out.println("❌ Choix invalide.");
            }
        }
    }
}

//✅ Résumé pédagogique
//        Élément	Rôle
//        Scanner	Lit les entrées clavier
//switch	Gère les choix du menu
//        UserRepository	Exécute les requêtes SQL
//        Utilisateur	Représente les données d’un utilisateur
//System.out.println(...)	Affiche les résultats dans la console
