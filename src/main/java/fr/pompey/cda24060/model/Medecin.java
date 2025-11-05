package fr.pompey.cda24060.model;

import fr.pompey.cda24060.exception.SaisieException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static fr.pompey.cda24060.utility.RegexUtility.numAgreementValide;
import static fr.pompey.cda24060.utility.RegexUtility.positifInt;

public class Medecin extends Personne {
    // Attribut de la classe Medecin
    private String numeroAgreement;

    // List des medecins enregistrer
    private static List<Medecin> medecins = new ArrayList<Medecin>();

    // List des patients du medecin
    public List<Patient> patient = new ArrayList<>();

    /**
     * Instantiates a new Medecin.
     *
     * @param pNom             the nom
     * @param pPrenom          the prenom
     * @param pNumeroAgreement the numero agreement
     * @param lieu             the Lieu lieu
     * @throws SaisieException the saisie exception
     */
    // Constructeur qui extends de la classe Personne et Obj Lieu
    public Medecin(String pNom, String pPrenom, String pNumeroAgreement, Lieu lieu) throws SaisieException {
        super(pNom, pPrenom, lieu);
        this.setNumeroAgreement(pNumeroAgreement);
    }

    // Afficher la List des medecins
    /**
     * Gets medecins.
     *
     * @return the medecins
     */
    public static List<Medecin> getMedecins() {
        return medecins;
    }

    // Getters et Setters
    /**
     * Gets numero agreement.
     *
     * @return the numero agreement
     */
    public String getNumeroAgreement() {
        return this.numeroAgreement;
    }

    /**
     * Sets numero agreement.
     *
     * @param pNumeroAgreement the numero agreement
     * @throws SaisieException the saisie exception
     */
    public void setNumeroAgreement(String pNumeroAgreement) throws SaisieException {
        if (!numAgreementValide(pNumeroAgreement)) {
            throw new SaisieException("Error sur numéro agreement : " + pNumeroAgreement);
        }else{
            this.numeroAgreement = pNumeroAgreement;
        }
    }

    // StringBuilder pour afficher le toString de Medecin
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Médecin :\n");
        sb.append("- Nom : ").append(getNom()).append("\n");
        sb.append("- Prénom : ").append(getPrenom()).append("\n");
        sb.append("- Numero agréement : ").append(numeroAgreement).append("\n");
        if (getLieu() != null) {
            sb.append(getLieu().toString());
        }
        return sb.toString();
    }
}
