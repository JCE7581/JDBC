package com.mycompany.tennis.repository;

import com.mycompany.tennis.entity.Joueur;
import java.util.List;

/**
 * C'est le "contrat" de notre repository. Il définit les opérations
 * possibles sur la table 'JOUEUR' de la base de données : créer, mettre à jour,
 * supprimer, et récupérer des joueurs.
 */
public interface JoueurRepository {

    /**
     * Méthode pour créer (ajouter) un nouveau joueur dans la base de données.
     * @param joueur L'objet Joueur à créer.
     */
    void create(Joueur joueur);

    /**
     * Méthode pour mettre à jour un joueur existant.
     * @param joueur L'objet Joueur avec les nouvelles informations (son ID doit être renseigné).
     */
    void update(Joueur joueur);

    /**
     * Méthode pour supprimer un joueur de la base de données.
     * @param id L'identifiant du joueur à supprimer.
     */
    void delete(Long id);

    /**
     * Méthode pour récupérer un joueur par son identifiant.
     * @param id L'identifiant du joueur à rechercher.
     * @return L'objet Joueur correspondant ou 'null' s'il n'est pas trouvé.
     */
    Joueur getById(Long id);

    /**
     * Méthode pour récupérer TOUS les joueurs de la base de données.
     * @return Une liste d'objets Joueur. La liste peut être vide si la table est vide.
     */
    List<Joueur> listAll();
}