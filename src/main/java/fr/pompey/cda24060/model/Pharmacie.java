package fr.pompey.cda24060.model;

import fr.pompey.cda24060.exception.SaisieException;

import static fr.pompey.cda24060.utility.RegexUtility.regexAlpha;

public class Pharmacie {
    // Attribut de la classe Pharmacie
    private String nom;

    /**
     * Instantiates a new Pharmacie.
     *
     * @param pNom              the nom pharmacie
     * @throws SaisieException  the saisie exception
     */
    // Constructeur de la classe Pharmacie
    public Pharmacie(String pNom) throws SaisieException {
        this.setNom(pNom);
    }

    // Setters et Getters de la classe Pharmacie

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
        sbo.append("Le nom de la pharmacie est ").append(this.nom).append("\n");

        return sbo.toString();
    }
}
