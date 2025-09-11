// Déclaration du package dans lequel se trouve cette classe.
// Un package est comme un dossier logique qui regroupe des classes liées.
// Ici, "model" signifie que cette classe fait partie du modèle de données.
package model;

// Déclaration de la classe publique nommée Utilisateur.
// Une classe est une structure qui permet de définir des objets avec des attributs et des comportements.
// Ici, cette classe représente un utilisateur dans ton application.
public class Utilisateur {

    // Déclaration d'un attribut privé de type entier (int) nommé "id".
    // "private" signifie que cet attribut n'est accessible que depuis cette classe.
    // Cet identifiant est unique pour chaque utilisateur, comme une clé primaire en base de données.
    private int id;

    // Déclaration d'un attribut privé de type chaîne de caractères (String) nommé "nom".
    // Il représente le nom de l'utilisateur.
    private String nom;

    // Déclaration d'un attribut privé de type chaîne de caractères (String) nommé "email".
    // Il représente l'adresse email de l'utilisateur.
    private String email;

    // Constructeur vide (sans paramètres).
    // Un constructeur est une méthode spéciale utilisée pour créer un objet.
    // Ce constructeur est nécessaire pour certains frameworks Java qui utilisent la réflexion.
    public Utilisateur() {}

    // Constructeur avec trois paramètres : id, nom et email.
    // Ce constructeur permet de créer un objet Utilisateur avec des valeurs initiales.
    public Utilisateur(int id, String nom, String email) {
        // "this.id" fait référence à l'attribut de la classe.
        // "id" est le paramètre reçu par le constructeur.
        // On affecte la valeur du paramètre à l'attribut de l'objet.
        this.id = id;

        // Même principe pour le nom.
        this.nom = nom;

        // Et pour l'email.
        this.email = email;
    }

    // Méthode publique nommée getId qui retourne un entier.
    // C'est un "getter" : il permet de lire la valeur de l'attribut privé "id".
    public int getId() {
        return id;
    }

    // Méthode publique nommée setId qui prend un entier en paramètre.
    // C'est un "setter" : il permet de modifier la valeur de l'attribut "id".
    public void setId(int id) {
        // On affecte la valeur reçue au champ "id" de l'objet.
        this.id = id;
    }

    // Getter pour l'attribut "nom".
    // Permet de lire la valeur du nom de l'utilisateur.
    public String getNom() {
        return nom;
    }

    // Setter pour l'attribut "nom".
    // Permet de modifier le nom de l'utilisateur.
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour l'attribut "email".
    // Permet de lire l'adresse email de l'utilisateur.
    public String getEmail() {
        return email;
    }

    // Setter pour l'attribut "email".
    // Permet de modifier l'adresse email de l'utilisateur.
    public void setEmail(String email) {
        this.email = email;
    }
}
