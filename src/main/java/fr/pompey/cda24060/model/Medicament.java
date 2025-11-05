package fr.pompey.cda24060.model;

import fr.pompey.cda24060.exception.SaisieException;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import static fr.pompey.cda24060.utility.RegexUtility.*;

public class Medicament {
    // Attibut pour la classe Medicament
    private int quantite;
    private String dateMiseEnService, categorie, nom;
    private double prix;

    // List des medicaments 
    private static List<Medicament> medicaments = new ArrayList<Medicament>();

    /**
     * Instantiates a new Medicament.
     *
     * @param pQuantite          the quantite
     * @param pDateMiseEnService the date mise en service
     * @param pPrix              the prix
     * @param pCategorie         the categorie
     * @param pNom               the nom
     * @throws SaisieException the saisie exception
     */
// Constucteur de la classe Medicament
    public Medicament(int pQuantite, String pDateMiseEnService, double pPrix, String pCategorie, String pNom) throws SaisieException {
        this.setQuantite(pQuantite);
        this.setDateMiseEnService(pDateMiseEnService);
        this.setPrix(pPrix);
        this.setCategorie(pCategorie);
        this.setNom(pNom);
    }

    // Getters et Setters

    /**
     * Gets medicaments.
     *
     * @return the medicaments
     */
    // Afficher la list des medicaments
    public static List<Medicament> getMedicaments() {
        return medicaments;
    }

    /**
     * Gets quantite.
     *
     * @return the quantite
     */
    public int getQuantite() {
        return this.quantite;
    }

    /**
     * Sets quantite.
     *
     * @param pQuantite         the quantite
     * @throws SaisieException  the saisie exception
     */
    public void setQuantite(int pQuantite) throws SaisieException {
        if (!positifInt(String.valueOf(pQuantite)) && pQuantite < 0) {
            throw new SaisieException("Error sur la quantité des médicaments : " + pQuantite);
        }else{
            this.quantite = pQuantite;
        }
    }

    /**
     * Gets date mise en service.
     *
     * @return the date mise en service
     */
    public String getDateMiseEnService() {
        return this.dateMiseEnService;
    }

    /**
     * Sets date mise en service.
     *
     * @param pDateMiseEnService    the date mise en service
     * @throws SaisieException      the saisie exception
     */
    public void setDateMiseEnService(String pDateMiseEnService) throws SaisieException {
        if(!dateValide(String.valueOf(pDateMiseEnService))){
            throw new SaisieException("Error sur la date de mise en service : " + pDateMiseEnService);
        }else{
            this.dateMiseEnService = pDateMiseEnService;
        }
    }

    /**
     * Gets prix.
     *
     * @return the prix
     */
    public double getPrix() {
        return this.prix;
    }

    /**
     * Sets prix.
     *
     * @param pPrix             the prix
     * @throws SaisieException  the saisie exception
     */
    public void setPrix(double pPrix) throws SaisieException {
        if (!positifInt(String.valueOf(pPrix)) && pPrix < 0) {
            throw new SaisieException("Error sur le prix : " + pPrix);
        }else{
            this.prix = pPrix;
        }
    }

    /**
     * Gets categorie.
     *
     * @return the categorie
     */
    public String getCategorie() {
        return this.categorie;
    }

    /**
     * Sets categorie.
     *
     * @param pCategorie        the categorie
     * @throws SaisieException  the saisie exception
     */
    public void setCategorie(String pCategorie) throws SaisieException {
        if (!regexAlpha(pCategorie) && pCategorie.isEmpty()) {
            throw new SaisieException("Error sur le categorie : " +  pCategorie);
        }else{
            this.categorie = pCategorie;
        }
    }

    /**
     * Gets nom.
     *
     * @return the nom medecin
     */
    public String getNom() {
        return this.nom;
    }

    /**
     * Sets nom.
     *
     * @param pNom              the nom medecin
     * @throws SaisieException  the saisie exception
     */
    public void setNom(String pNom) throws SaisieException {
        if (!regexAlpha(pNom) && pNom.isEmpty()) {
            throw new SaisieException("Error sur le nom : " + pNom);
        }else {
            this.nom = pNom;
        }
    }

    // StringBiulder dans mon toString de la classe Medicament
    public String toString() {
        StringBuilder m = new StringBuilder();
        m.append("- Quantité : ").append(this.quantite).append("\n");
        m.append("- Date mise en service : ").append(this.dateMiseEnService).append("\n");
        m.append("- Prix : ").append(this.prix).append("\n");
        m.append("- Categorie : ").append(this.categorie).append("\n");
        m.append("- Nom : ").append(this.nom).append("\n");
        m.append("\n");

        return m.toString();
    }
}
