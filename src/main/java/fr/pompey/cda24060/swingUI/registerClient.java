package fr.pompey.cda24060.swingUI;

import fr.pompey.cda24060.exception.SaisieException;
import fr.pompey.cda24060.model.Lieu;
import fr.pompey.cda24060.model.Medecin;
import fr.pompey.cda24060.model.Mutuelle;
import fr.pompey.cda24060.model.Patient;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class registerClient extends JFrame {
    private int id;
    private JPanel contentPane;
    private JPanel logoRegisterClient;
    private JPanel mainRegisterClient;
    private JPanel footerRegisterClient;
    private JButton buttonRetourRegisterClient;
    private JButton buttonValideRegisterClient;
    private JTextField textFieldRegisterNom;
    private JTextField textFieldRegisterPrenom;
    private JTextField textFieldRegisterAdresse;
    private JTextField textFieldRegisterCodePostal;
    private JTextField textFieldRegisterVille;
    private JTextField textFieldRegisterTel;
    private JTextField textFieldRegisterEmail;
    private JTextField textFieldregisterNumSecu;
    private JTextField textFieldRegisterDateNaissance;
    private JLabel titreRegister;
    private JButton quitterButton;
    private JComboBox<String> comboBoxNomMedecin;
    private JComboBox<String> comboBoxMutuelle;
    private JFrame previousFrame;

    // Patient en cours (null = création, sinon update)
    private Patient currentPatient;

    /**
     * Constructeur pour la création d'un nouveau patient
     */
    public registerClient(JFrame previousFrame) {
        this.previousFrame = previousFrame;

        initUI();
        remplirComboBox();

        // Actions boutons
        buttonRetourRegisterClient.addActionListener(e -> retour());
        buttonValideRegisterClient.addActionListener(e -> {
            try {
                valider();
            } catch (SaisieException ex) {
                JOptionPane.showMessageDialog(this, "Erreur : " + ex.getMessage());
            }
        });
        quitterButton.addActionListener(e -> quitter());
    }

    /**
     * Constructeur pour l'édition d'un patient existant
     *
     * @param patient the patient
     * @param previousFrame la fenêtre précédente
     */
    public registerClient(Patient patient, JFrame previousFrame) {
        this.previousFrame = previousFrame;

        initUI();
        remplirComboBox();

        this.currentPatient = patient;

        if (patient != null) {
            textFieldRegisterNom.setText(patient.getNom());
            textFieldRegisterPrenom.setText(patient.getPrenom());
            textFieldRegisterAdresse.setText(patient.getLieu().getAdresse());
            textFieldRegisterCodePostal.setText(String.valueOf(patient.getLieu().getCodePostal()));
            textFieldRegisterVille.setText(patient.getLieu().getVille());
            textFieldRegisterTel.setText(patient.getLieu().getTelephone());
            textFieldRegisterEmail.setText(patient.getLieu().getEmail());
            textFieldregisterNumSecu.setText(patient.getNumeroSecuriteSociale());
            textFieldRegisterDateNaissance.setText(patient.getDateNaissance());
            comboBoxMutuelle.setSelectedItem(patient.getMutuelle().getNom());
            comboBoxNomMedecin.setSelectedItem(patient.getMedecin().getNom() + " " + patient.getMedecin().getPrenom());
        }

        // Actions boutons
        buttonRetourRegisterClient.addActionListener(e -> retour());
        buttonValideRegisterClient.addActionListener(e -> {
            try {
                valider();
            } catch (SaisieException ex) {
                JOptionPane.showMessageDialog(this, "Erreur sur la validation de la mise a jour patient : " + ex.getMessage());
            }
        });
        quitterButton.addActionListener(e -> quitter());
    }

    /**
     * Initialisation de la fenêtre
     */
    private void initUI() {
        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/miniLogo.png")));
        Dimension dimension = new Dimension(1600, 1000);

        this.setTitle("Sparadrap");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(dimension);
        this.setResizable(false);
        this.setContentPane(contentPane);

        this.pack();
        this.setLocationRelativeTo(null);

        // Gestionnaire pour la croix (X)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                retour();
            }
        });
    }

    /**
     * Remplir les JComboBox avec les listes de médecins et mutuelles
     */
    private void remplirComboBox() {
        comboBoxNomMedecin.removeAllItems();
        for (Medecin med : Medecin.getMedecins()) {
            comboBoxNomMedecin.addItem(med.getNom() + " " + med.getPrenom());
        }

        comboBoxMutuelle.removeAllItems();
        for (Mutuelle mut : Mutuelle.getMutuelles()) {
            comboBoxMutuelle.addItem(mut.getNom());
        }
    }

    /**
     * Retour à la fenêtre précédente
     */
    private void retour() {
        if (previousFrame != null) {
            previousFrame.setVisible(true); // réaffiche la fenêtre précédente
        }
        this.dispose(); // ferme la fenêtre actuelle
    }

    /**
     * Validation du formulaire
     */
    private void valider() throws SaisieException {
        try {
            // Champs saisis
            String nom = textFieldRegisterNom.getText();
            String prenom = textFieldRegisterPrenom.getText();
            String adresse = textFieldRegisterAdresse.getText();
            int codePostal = Integer.parseInt(textFieldRegisterCodePostal.getText());
            String ville = textFieldRegisterVille.getText();
            String tel = textFieldRegisterTel.getText();
            String email = textFieldRegisterEmail.getText();
            String numSecu = textFieldregisterNumSecu.getText();
            String dateNaissance = textFieldRegisterDateNaissance.getText();

            // Sélection mutuelle et médecin
            String mutuelleNom = (String) comboBoxMutuelle.getSelectedItem();
            String medecinNomComplet = (String) comboBoxNomMedecin.getSelectedItem();

            Mutuelle mutuelleChoisie = null;
            for (Mutuelle m : Mutuelle.getMutuelles()) {
                if (m.getNom().equals(mutuelleNom)) {
                    mutuelleChoisie = m;
                    break;
                }
            }

            Medecin medecinChoisi = null;
            for (Medecin med : Medecin.getMedecins()) {
                String nomComplet = med.getNom() + " " + med.getPrenom();
                if (nomComplet.equals(medecinNomComplet)) {
                    medecinChoisi = med;
                    break;
                }
            }

            if (currentPatient != null) {
                // -------- MODE UPDATE --------
                currentPatient.setNom(nom);
                currentPatient.setPrenom(prenom);
                currentPatient.setNumeroSecuriteSociale(numSecu);
                currentPatient.setDateNaissance(dateNaissance);

                Lieu lieu = currentPatient.getLieu();
                lieu.setAdresse(adresse);
                lieu.setEmail(email);
                lieu.setTelephone(tel);
                lieu.setVille(ville);
                lieu.setCodePostal(codePostal);

                currentPatient.setMutuelle(mutuelleChoisie);
                currentPatient.setMedecin(medecinChoisi);

                JOptionPane.showMessageDialog(this,
                        "Patient mis à jour avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);

            } else {
                // -------- MODE CREATION --------
                Lieu lieu = new Lieu(adresse, email, tel, ville, codePostal);
                Patient newPatient = new Patient(nom, prenom, dateNaissance, lieu, mutuelleChoisie, medecinChoisi);
                newPatient.setNumeroSecuriteSociale(numSecu);
                Patient.getPatients().add(newPatient);

                JOptionPane.showMessageDialog(this,
                        "Nouveau patient ajouté avec succès !",
                        "Succès",
                        JOptionPane.INFORMATION_MESSAGE);
            }

            // retour vers consulterClient
            consulterClient consulterClient = new consulterClient(this);
            consulterClient.setVisible(true);
            this.dispose();

        } catch (NumberFormatException e) {
            throw new SaisieException("Code postal ou Numéro de sécu invalide !");
        }
    }

    /**
     * Quitter l'application
     */
    private void quitter() {
        int reponse = JOptionPane.showConfirmDialog(registerClient.this,
                "Voulez-vous quitter l'application ?", "Quitter",
                JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}