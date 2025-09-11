// Déclaration du package dans lequel se trouve cette classe
// Cela permet d'organiser ton code dans des dossiers logiques (ici : "model")
package model;

// Déclaration de la classe Utilisateur
// Elle représente un utilisateur de ta base de données
public class Utilisateur {

    // Attribut privé : identifiant unique de l'utilisateur
    // Correspond à la colonne "id" dans la table MySQL
    private int id;

    // Attribut privé : nom de l'utilisateur
    // Correspond à la colonne "nom" dans la table
    private String nom;

    // Attribut privé : email de l'utilisateur
    // Correspond à la colonne "email" dans la table
    private String email;

    // Constructeur vide (sans paramètres)
    // Obligatoire pour certains frameworks ou bibliothèques Java
    public Utilisateur() {}

    // Constructeur avec tous les champs
    // Permet de créer un objet Utilisateur directement avec ses valeurs
    public Utilisateur(int id, String nom, String email) {
        this.id = id;         // on affecte la valeur reçue à l'attribut id
        this.nom = nom;       // idem pour nom
        this.email = email;   // idem pour email
    }

    // Getter pour l'attribut id
    // Permet de lire la valeur de id depuis l'extérieur de la classe
    public int getId() {
        return id;
    }

    // Setter pour l'attribut id
    // Permet de modifier la valeur de id depuis l'extérieur
    public void setId(int id) {
        this.id = id;
    }

    // Getter pour l'attribut nom
    public String getNom() {
        return nom;
    }

    // Setter pour l'attribut nom
    public void setNom(String nom) {
        this.nom = nom;
    }

    // Getter pour l'attribut email
    public String getEmail() {
        return email;
    }

    // Setter pour l'attribut email
    public void setEmail(String email) {
        this.email = email;
    }
}
