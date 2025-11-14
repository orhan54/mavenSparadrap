package fr.pompey.cda24060.model;

import fr.pompey.cda24060.exception.SaisieException;

import static fr.pompey.cda24060.utility.RegexUtility.regexAlpha;

public class Pharmacie {
    // Attribut de la classe Pharmacie
    private int Id_Pharmacie;
    private String nom, prenom;

    /**
     * Instantiates a new Pharmacie.
     *
     * @param pNom              the nom pharmacie
     * @throws SaisieException  the saisie exception
     */
    // Constructeur de la classe Pharmacie
    public Pharmacie(String pNom, String pPrenom) throws SaisieException {
        this.setNom(pNom);
        this.setPrenom(pPrenom);
    }

    // Setters et Getters de la classe Pharmacie


    /**
     * Gets id pharmacie.
     *
     * @return the id pharmacie
     */
    public int getId_Pharmacie() {
        return Id_Pharmacie;
    }

    /**
     * Sets id pharmacie.
     *
     * @param id_Pharmacie the id pharmacie
     */
    public void setId_Pharmacie(int id_Pharmacie) {
        Id_Pharmacie = id_Pharmacie;
    }

    /**
     * Gets prenom.
     *
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * Sets prenom.
     *
     * @param pPrenom the p prenom
     * @throws SaisieException the saisie exception
     */
    public void setPrenom(String pPrenom) throws SaisieException {
        if(!regexAlpha(pPrenom) && pPrenom.isEmpty()){
            throw new SaisieException("Error sur le nom de la pharmacie : " + pPrenom);
        }else{
            this.nom = pPrenom;
        }
    }

    /**
     * Gets nom.
     *
     * @return the nom pharmacie
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Sets nom.
     *
     * @param pNom              the nom pharmacie
     * @throws SaisieException  the saisie exception
     */
    public void setNom(String pNom) throws SaisieException {
        if(!regexAlpha(pNom) && pNom.isEmpty()){
            throw new SaisieException("Error sur le nom de la pharmacie : " + pNom);
        }else{
            this.nom = pNom;
        }
    }

    // StringBuilder pour afficher le toString de Pharmacie
    @Override
    public String toString() {
        StringBuilder sbo = new StringBuilder();
        sbo.append("Le prenom du pharmacien est ").append(this.nom).append("\n");
        sbo.append("Le nom du pharmacien est ").append(this.nom).append("\n");

        return sbo.toString();
    }
}
