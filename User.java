// Déclaration de la classe User
// Cette classe représente un "utilisateur" dans ton application Java
// Elle correspond directement à la table "users" dans ta base de données MySQL
public class User {

    // 🔸 Déclaration des variables (appelées "champs" ou "attributs") de la classe

    // Identifiant unique de l'utilisateur
    // Il correspond à la colonne "id" dans la table MySQL
    // Il est auto-incrémenté par MySQL, donc on ne le fournit pas lors de l'insertion
    private int id;

    // Nom de l'utilisateur
    // Correspond à la colonne "name" dans la table MySQL
    private String name;

    // Adresse email de l'utilisateur
    // Correspond à la colonne "email" dans la table MySQL
    private String email;

    // 🔸 Constructeur #1 : utilisé pour créer un nouvel utilisateur AVANT qu'il soit inséré dans la base
    // On ne connaît pas encore l'ID (car il sera généré automatiquement par MySQL)
    public User(String name, String email) {
        this.name = name;     // On initialise le champ "name" avec la valeur passée en paramètre
        this.email = email;   // On initialise le champ "email" avec la valeur passée en paramètre
    }

    // 🔸 Constructeur #2 : utilisé quand on lit un utilisateur DEPUIS la base de données
    // Dans ce cas, on connaît déjà l'ID, le nom et l'email
    public User(int id, String name, String email) {
        this.id = id;         // On initialise le champ "id"
        this.name = name;     // On initialise le champ "name"
        this.email = email;   // On initialise le champ "email"
    }

    // 🔸 Getters : méthodes qui permettent de LIRE les valeurs des champs
    // On les utilise pour accéder aux données de l'objet depuis l'extérieur

    public int getId() {
        return id;            // Retourne la valeur du champ "id"
    }

    public String getName() {
        return name;          // Retourne la valeur du champ "name"
    }

    public String getEmail() {
        return email;         // Retourne la valeur du champ "email"
    }

    // 🔸 Setters : méthodes qui permettent de MODIFIER les valeurs des champs
    // On les utilise pour mettre à jour les données de l'objet

    public void setId(int id) {
        this.id = id;         // Met à jour la valeur du champ "id"
    }

    public void setName(String name) {
        this.name = name;     // Met à jour la valeur du champ "name"
    }

    public void setEmail(String email) {
        this.email = email;   // Met à jour la valeur du champ "email"
    }
}

//✅ Résumé pédagogique
//Élément	Rôle
//private	Rend les champs inaccessibles directement depuis l’extérieur (sécurité)
//this.nomChamp	Fait référence au champ de la classe (utile quand le nom du paramètre est identique)
//getX()	Permet de lire la valeur d’un champ
//setX()	Permet de modifier la valeur d’un champ
//Constructeurs	Permettent de créer un objet avec des valeurs initiales
