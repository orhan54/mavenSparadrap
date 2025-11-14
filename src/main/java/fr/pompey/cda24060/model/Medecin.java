package fr.pompey.cda24060.model;

import fr.pompey.cda24060.exception.SaisieException;

import java.util.ArrayList;
import java.util.List;

import static fr.pompey.cda24060.utility.RegexUtility.numAgreementValide;

public class Medecin extends Personne {

    private int id;
    private String numeroAgreement;
    private static List<Medecin> medecins = new ArrayList<>();
    private List<Patient> patients = new ArrayList<>();

    // Constructeurs
    public Medecin() throws SaisieException {
        super();
    }

    public Medecin(String pNom, String pPrenom, String pNumeroAgreement, Lieu lieu) throws SaisieException {
        super(pNom, pPrenom, lieu);
        this.setNumeroAgreement(pNumeroAgreement);
    }

    // Getter & Setter

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(int id) {
        this.id = id;
    }


    /**
     * Gets numero agreement.
     *
     * @return the numero agreement
     */
    public String getNumeroAgreement() {
        return numeroAgreement;
    }

    /**
     * Sets numero agreement.
     *
     * @param pNumeroAgreement the p numero agreement
     * @throws SaisieException the saisie exception
     */
    public void setNumeroAgreement(String pNumeroAgreement) throws SaisieException {
        if (pNumeroAgreement == null || !numAgreementValide(pNumeroAgreement)) {
            throw new SaisieException("Erreur sur numéro d'agrément : " + pNumeroAgreement);
        }
        this.numeroAgreement = pNumeroAgreement;
    }

    /**
     * Gets medecins.
     *
     * @return the medecins
     */
    public static List<Medecin> getMedecins() {
        return medecins;
    }

    /**
     * Gets patients.
     *
     * @return the patients
     */
    public List<Patient> getPatients() {
        return patients;
    }

    /**
     * Sets patients.
     *
     * @param patients the patients
     */
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Médecin :\n");
        sb.append("- ID : ").append(id).append("\n");
        sb.append("- Nom : ").append(getNom()).append("\n");
        sb.append("- Prénom : ").append(getPrenom()).append("\n");
        sb.append("- Numéro d'agrément : ").append(numeroAgreement).append("\n");
        if (getLieu() != null) {
            sb.append(getLieu().toString());
        }
        return sb.toString();
    }
}
