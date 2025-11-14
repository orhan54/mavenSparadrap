package fr.pompey.cda24060.model;

import fr.pompey.cda24060.exception.SaisieException;

import java.util.ArrayList;
import java.util.List;

import static fr.pompey.cda24060.utility.RegexUtility.positifInt;
import static fr.pompey.cda24060.utility.RegexUtility.regexAlpha;

public class Mutuelle {

    // Attribut de la classe Mutuelle
    private String nom;
    private double tauxPriseEnCharge;
    private int id ,departement;
    private Lieu lieu;

    // List des mutuelles
    private static List<Mutuelle> mutuelles = new ArrayList<Mutuelle>();

    /**
     * Instantiates a new Mutuelle.
     *
     * @param pNom               the nom mutuelle
     * @param pTauxPriseEnCharge the taux prise en charge
     * @param pDepartement       the departement
     * @param lieu               the Lieu lieu
     * @throws SaisieException   the saisie exception
     */
    // Constucteur qui extends de la classe Lieu
    public Mutuelle(String pNom, double pTauxPriseEnCharge, int pDepartement, Lieu lieu) throws SaisieException {
        this.setNom(pNom);
        this.setTauxPriseEnCharge(pTauxPriseEnCharge);
        this.setDepartement(pDepartement);
        this.setLieu(lieu);
    }

    // Getters et Setters

    /**
     * Gets mutuelles.
     *
     * @return the mutuelles
     */
    // Afficher la list des Mutuelles
    public static List<Mutuelle> getMutuelles() {
        return mutuelles;
    }

    /**
     * Gets nom.
     *
     * @return the nom mutuelle
     */
    public  String getNom() {
        return this.nom;
    }

    /**
     * Sets nom.
     *
     * @param pNom              the nom mutuelle
     * @throws SaisieException  the saisie exception
     */
    public void setNom(String pNom) throws SaisieException {
        if (!regexAlpha(pNom) && !pNom.isEmpty()) {
            throw new SaisieException("Error sur le nom de la mutuelle : " + pNom);
        }else{
            this.nom = pNom;
        }
    }

    /**
     * Gets taux prise en charge.
     *
     * @return the taux prise en charge
     */
    public double getTauxPriseEnCharge() {
        return this.tauxPriseEnCharge;
    }

    /**
     * Sets taux prise en charge.
     *
     * @param pTauxPriseEnCharge the taux prise en charge
     * @throws SaisieException the saisie exception
     */
    public void setTauxPriseEnCharge(double pTauxPriseEnCharge) throws SaisieException {
        if (!positifInt(String.valueOf(pTauxPriseEnCharge)) && pTauxPriseEnCharge < 0){
            throw new SaisieException("Error sur le taux de prise en charge : " + pTauxPriseEnCharge);
        }else{
            this.tauxPriseEnCharge = pTauxPriseEnCharge;
        }
    }

    /**
     * Gets departement.
     *
     * @return the departement
     */
    public int getDepartement() {
        return this.departement;
    }

    /**
     * Sets departement.
     *
     * @param pDepartement      the departement
     * @throws SaisieException  the saisie exception
     */
    public void setDepartement(int pDepartement) throws SaisieException {
        if (!positifInt(String.valueOf(pDepartement)) && pDepartement < 0){
            throw new SaisieException("Error sur le numéro de departement : " + pDepartement);
        }else{
            this.departement = pDepartement;
        }
    }

    /**
     * Gets lieu.
     *
     * @return the Lieu lieu
     */
    public Lieu getLieu() {
        return lieu;
    }

    /**
     * Sets lieu.
     *
     * @param lieu the Lieu lieu
     */
    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    // StringBuilder pour afficher le toString de Mutuelle
    @Override
    public String toString() {
        StringBuilder sbm = new StringBuilder();
        sbm.append("Mutuelle : ").append("\n");
        sbm.append("- Nom : ").append(this.getNom()).append("\n");
        if (getLieu() != null) {
            sbm.append(getLieu().toString());
        }
        sbm.append("- Departement : ").append(this.getDepartement()).append("\n");
        sbm.append("- Taux de prise en charge : ").append(this.getTauxPriseEnCharge()).append("\n");
        return sbm.toString();
    }
}
