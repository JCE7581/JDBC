package com.mycompany.tennis.entity;

/**
 * C'est la classe modèle qui représente une ligne de la table 'JOUEUR'
 * dans la base de données. On l'appelle souvent une 'entité' ou un 'POJO'.
 * Son rôle unique est de stocker les informations d'un joueur.
 */
public class Joueur {

    // --- Les attributs : ce sont les informations que la classe va stocker. ---
    // Le mot-clé 'private' est crucial : il rend ces variables inaccessibles
    // directement depuis l'extérieur de cette classe. Pour les manipuler,
    // il faut obligatoirement passer par les getters et setters.
    private Long id;       // L'identifiant unique du joueur. Type 'Long' pour un nombre entier long.
    private String nom;    // Le nom du joueur, une chaîne de caractères.
    private String prenom; // Le prénom du joueur, une chaîne de caractères.
    private char sexe;     // Le sexe du joueur, un seul caractère ('H' ou 'F').

    // --- Le constructeur : la méthode qui permet de créer un objet Joueur. ---
    // Ce constructeur est vide. Il permet de créer un objet 'Joueur' sans lui
    // donner de valeurs initiales, par exemple : 'Joueur joueur = new Joueur();'.
    public Joueur() {
    }

    // --- Les Getters et Setters : ce sont les méthodes pour lire et écrire les attributs. ---
    // Les 'getters' servent à lire une valeur.
    // Les 'setters' servent à modifier une valeur.

    /**
     * Getter pour l'identifiant du joueur.
     * @return La valeur actuelle de l'attribut 'id'.
     */
    public Long getId() {
        return this.id; // 'this.id' fait référence à l'attribut 'id' de l'objet actuel.
    }

    /**
     * Setter pour l'identifiant du joueur.
     * @param id La nouvelle valeur que l'on veut donner à l'attribut 'id'.
     */
    public void setId(Long id) {
        this.id = id; // On affecte la valeur passée en paramètre à l'attribut 'id' de l'objet.
    }

    /**
     * Getter pour le nom du joueur.
     * @return La valeur actuelle de l'attribut 'nom'.
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Setter pour le nom du joueur.
     * @param nom Le nouveau nom à attribuer au joueur.
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Getter pour le prénom du joueur.
     * @return La valeur actuelle de l'attribut 'prenom'.
     */
    public String getPrenom() {
        return this.prenom;
    }

    /**
     * Setter pour le prénom du joueur.
     * @param prenom Le nouveau prénom à attribuer au joueur.
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * Getter pour le sexe du joueur.
     * @return La valeur actuelle de l'attribut 'sexe'.
     */
    public char getSexe() {
        return this.sexe;
    }

    /**
     * Setter pour le sexe du joueur.
     * @param sexe Le nouveau sexe à attribuer au joueur.
     */
    public void setSexe(char sexe) {
        this.sexe = sexe;
    }

    /**
     * Méthode spéciale `toString()` : c'est la "carte de visite" de l'objet.
     * Elle est automatiquement appelée quand on veut afficher l'objet, par exemple
     * dans un 'System.out.println()'. Elle permet d'afficher les informations
     * de l'objet de manière lisible.
     * @return Une chaîne de caractères décrivant l'objet Joueur.
     */
    @Override // L'annotation @Override indique que l'on redéfinit une méthode héritée.
    public String toString() {
        return "Joueur{" +
                "id=" + this.id +
                ", nom='" + this.nom + '\'' +
                ", prenom='" + this.prenom + '\'' +
                ", sexe=" + this.sexe +
                '}';
    }
}