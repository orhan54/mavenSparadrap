package fr.pompey.cda24060.model;

import fr.pompey.cda24060.exception.SaisieException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static fr.pompey.cda24060.utility.RegexUtility.dateValide;
import static fr.pompey.cda24060.utility.RegexUtility.positifInt;

public class Patient extends Personne {

    private static int nextId = 1;
    private int id;

    // Attribut de la classe Client
    private String numeroSecuriteSociale, dateNaissance;

    // List des patients enregistrer
    private static List<Patient> patients = new ArrayList<Patient>();

    /**
     * Instantiates a new Patient.
     *
     * @param pNom           the nom patient
     * @param pPrenom        the prenom patient
     * @param pDateNaissance the date naissance
     * @param lieu           the lieu patient
     * @param mutuelle       the mutuelle
     * @param medecin        the medecin
     * @throws SaisieException the saisie exception
     */
    // Constructeur qui extends de la classe Personne et Obj Lieu
    public Patient(String pNom, String pPrenom, String pDateNaissance, Lieu lieu, Mutuelle mutuelle, Medecin medecin) throws SaisieException {
        super(pNom, pPrenom, lieu, mutuelle, medecin);
        this.setId(id);
        this.setDateNaissance(String.valueOf(pDateNaissance));
        this.numeroSecuriteSociale = generateNumSecu();
        this.getMutuelle().getNom();
        this.getMedecin().getNom();
    }

    /**
     * Gets patients.
     *
     * @return the patients
     */
    // Afficher la list des patients
    public static List<Patient> getPatients() {
        return patients;
    }

    /**
     *
     * @return the toString random numero securite social
     */
    // Création du numéro de la sécurité social
    private static String generateNumSecu() {
        Random random = new Random();
        StringBuilder numSecu = new StringBuilder();

        // la premiere boucle for pour choisir un chiffre entre 1 et 2 pour le numero de securité social
        for(int i = 0; i < 1; i++) {
            numSecu.append(random.nextInt(2) + 1);
            // le deuxieme for pour les 14 chiffres suivent pour faire une longueur de 15 chiffre total
            for (int j = 0; j < 14; j++) {
                numSecu.append(random.nextInt(10)); // Ajoute un chiffre entre 0 et 9
            }
        }

        if(numSecu.length()!=15){
            System.out.println("Error sur le numero de sécurité social : ");
        }
        return numSecu.toString();
    }

    // Getters et Setters

    /**
     * Sets id.
     *
     * @param id the id
     * @throws SaisieException the saisie exception
     */
    public void setId(int id) throws SaisieException {
        if(!positifInt(String.valueOf(id))){
            throw new SaisieException("Error sur id Patient : ");
        }else{
            this.id = nextId++;
        }
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets date naissance.
     *
     * @return the date naissance
     */
    public String getDateNaissance() {
        return dateNaissance;
    }

    /**
     * Sets date naissance.
     *
     * @param pDateNaissance the date naissance
     */
    public void setDateNaissance(String pDateNaissance) throws SaisieException {
        if(!dateValide(String.valueOf(pDateNaissance))){
            throw new SaisieException("Error sur le date de naissance : " + pDateNaissance);
        }else{
            this.dateNaissance = pDateNaissance;
        }
    }

    /**
     * Gets numero securite sociale.
     *
     * @return the numero securite sociale
     */
    public String getNumeroSecuriteSociale() {
        return this.numeroSecuriteSociale;
    }

    /**
     * Sets numero securite sociale.
     *
     * @param pNumeroSecuriteSociale    the numero securite sociale
     * @throws SaisieException          the saisie exception
     */
    public void setNumeroSecuriteSociale(String pNumeroSecuriteSociale) throws  SaisieException {
        if (numeroSecuriteSociale.length()!=15) {
            System.out.println("Error sur le numero de sécurité social : " + pNumeroSecuriteSociale);
        }else {
            this.numeroSecuriteSociale = pNumeroSecuriteSociale;
        }
    }

    // StringBuilder pour afficher le toString de Patient
    @Override
    public String toString() {
        StringBuilder sbpat = new StringBuilder();
        sbpat.append("Patient :\n");
        sbpat.append("- Nom : ").append(getNom()).append("\n");
        sbpat.append("- Prénom : ").append(getPrenom()).append("\n");
        sbpat.append("- Numéro de sécurité sociale : ").append(numeroSecuriteSociale).append("\n");
        sbpat.append("- Date de naissance : ").append(dateNaissance.toString()).append("\n");
        if (getLieu() != null) {
            sbpat.append(getLieu().toString());
        }
        if (getMutuelle() != null) {
            sbpat.append("- Mutuelle : ").append(getMutuelle().getNom()).append("\n");
        }
        sbpat.append("- Nom du medecin : ").append(getMedecin().getNom()).append("\n");

        return sbpat.toString();
    }

}
