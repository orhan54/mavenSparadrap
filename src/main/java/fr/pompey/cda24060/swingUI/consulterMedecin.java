package fr.pompey.cda24060.swingUI;

import fr.pompey.cda24060.model.Medecin;
import fr.pompey.cda24060.model.Ordonnance;
import fr.pompey.cda24060.model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.stream.Collectors;

public class consulterMedecin extends JFrame {
    private JPanel contentPane;
    private JLabel titreMenu;
    private JTable tableMedecin;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JButton quitterButton;
    private JButton retourButton;
    private JComboBox<String> comboBoxMedecin;
    private JButton creerUnMedecinButton;
    private JComboBox<String> comboBoxInformation;
    private JTable tableFiltreInformation;
    private JLabel titreFiltreInfo;
    private String selectedValue;
    private JFrame previousFrame;

    private DefaultTableModel tableModelMedecin;

    private String[] HEADER_PATIENT = new String[]{
            "id", "Nom", "Prenom", "Adresse", "Code postal", "Ville",
            "Téléphone", "Email", "Numero sécurité social", "Date de naissance",
            "Mutuelle", "Medecin"
    };

    private String[] HEADER_ORDONNANCE = new String[]{
            "Date", "Nom médecin", "Nom patient", "Liste des médicaments"
    };

    public consulterMedecin(JFrame previousFrame) {
        this.previousFrame = previousFrame;

        ImageIcon imageIcon = new ImageIcon("C:\\Users\\User\\Desktop\\ECF-CPP1_CICEK_Orhan\\ECF-CPP-1\\sparadrap\\src\\sparadrap\\afpa\\image\\miniLogo.png");
        Dimension dimension = new Dimension(1600, 1000);

        // Attributs JFrame
        this.setTitle("Sparadrap");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(dimension);
        this.setResizable(false);
        this.setContentPane(contentPane);

        // Table médecin
        String[] colonnes = {"Nom", "Prénom", "Adresse", "Code postal", "Ville", "Téléphone", "Email", "Numéro d'agrément"};
        tableModelMedecin = new DefaultTableModel(colonnes, 0);
        tableMedecin.setModel(tableModelMedecin);

        // ComboBox Information
        comboBoxInformation.addItem("Choisir le filtre...");
        comboBoxInformation.addItem("Liste des patients du médecin");
        comboBoxInformation.addItem("Liste des ordonnances du médecin");
        comboBoxInformation.setSelectedIndex(0);

        // Listeners
        remplirComboBox();

        comboBoxInformation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayInformation();
            }
        });

        retourButton.addActionListener(e -> retour());
        quitterButton.addActionListener(e -> quitter());
        creerUnMedecinButton.addActionListener(e -> creerMedecin());
        modifierButton.addActionListener(e -> updateMedecin());
        supprimerButton.addActionListener(e -> deleteMedecin());

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

    // Affichage dynamique Patients / Ordonnances
    public void displayInformation() {
        String selectedMedecin = (String) comboBoxMedecin.getSelectedItem();
        int selectedInfo = comboBoxInformation.getSelectedIndex();

        if (selectedInfo == 0 || selectedMedecin == null || selectedMedecin.equals("Choisir un médecin")) {
            titreFiltreInfo.setText("Choisir un filtre...");
            return;
        }

        Medecin medecinChoisi = Medecin.getMedecins().stream().filter(m -> (m.getNom() + " " + m.getPrenom()).equals(selectedMedecin)).findFirst().orElse(null);

        if (medecinChoisi == null) {
            return;
        }

        if (selectedInfo == 1) {
            // Patients du médecin
            titreFiltreInfo.setText("Liste des patients du médecin : " + selectedMedecin);

            List<Patient> patientsMedecin = Patient.getPatients().stream()
                    .filter(p -> p.getMedecin() != null && p.getMedecin().equals(medecinChoisi))
                    .collect(Collectors.toList());

            configureTable(HEADER_PATIENT);
            constructDataTable(patientsMedecin);

        } else if (selectedInfo == 2) {
            // Ordonnances du médecin
            titreFiltreInfo.setText("Liste des ordonnances du médecin : " + selectedMedecin);

            List<Ordonnance> ordonnancesMedecin = Ordonnance.getOrdonnances().stream()
                    .filter(o -> o.getNomMedecin().equals(medecinChoisi.getNom() + " " + medecinChoisi.getPrenom()))
                    .collect(Collectors.toList());

            configureTable(HEADER_ORDONNANCE);
            constructDataTable(ordonnancesMedecin);
        }
    }


    // Préparer le modèle de table
    private void configureTable(String[] header) {
        TableModel model = new DefaultTableModel(header, 0);
        this.tableFiltreInformation.setModel(model);
        this.tableFiltreInformation.revalidate();
        this.tableFiltreInformation.repaint();
    }

    // Remplir le tableau avec les données
    private <T> void constructDataTable(List<T> dataListe) {
        DefaultTableModel model = (DefaultTableModel) this.tableFiltreInformation.getModel();
        model.setRowCount(0);

        for (T obj : dataListe) {
            if (obj instanceof Patient) {
                Patient p = (Patient) obj;
                model.addRow(new Object[]{
                        p.getId(),
                        p.getNom(),
                        p.getPrenom(),
                        p.getLieu().getAdresse(),
                        p.getLieu().getCodePostal(),
                        p.getLieu().getVille(),
                        p.getLieu().getTelephone(),
                        p.getLieu().getEmail(),
                        p.getNumeroSecuriteSociale(),
                        p.getDateNaissance(),
                        p.getMutuelle() != null ? p.getMutuelle().getNom() : "",
                        p.getMedecin() != null ? p.getMedecin().getNom() + " " + p.getMedecin().getPrenom() : ""
                });
            } else if (obj instanceof Ordonnance) {
                Ordonnance o = (Ordonnance) obj;

                // Construire une chaîne "NomMedicament (quantité)"
                String medicamentsStr = o.getMedicaments().stream()
                        .map(m -> m.getNom() + " (" + m.getQuantite() + ")")
                        .collect(Collectors.joining(", "));

                model.addRow(new Object[]{
                        o.getDate(),
                        o.getNomMedecin(),
                        o.getNomPatient(),
                        medicamentsStr   // noms et quantités
                });
            }
        }
    }


    // Remplir la comboBox médecin
    private void remplirComboBox() {
        comboBoxMedecin.removeAllItems();
        comboBoxMedecin.addItem("Choisir un médecin");
        comboBoxMedecin.setSelectedIndex(0);

        for (Medecin m : Medecin.getMedecins()) {
            comboBoxMedecin.addItem(m.getNom() + " " + m.getPrenom());
        }

        comboBoxMedecin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selected = (String) comboBoxMedecin.getSelectedItem();
                selectedValue = selected;
                tableModelMedecin.setRowCount(0);

                for (Medecin m : Medecin.getMedecins()) {
                    if (selectedValue.equals(m.getNom() + " " + m.getPrenom())) {
                        tableModelMedecin.addRow(new Object[]{
                                m.getNom(),
                                m.getPrenom(),
                                m.getLieu().getAdresse(),
                                m.getLieu().getCodePostal(),
                                m.getLieu().getVille(),
                                m.getLieu().getTelephone(),
                                m.getLieu().getEmail(),
                                m.getNumeroAgreement()
                        });
                    }
                }
            }
        });
    }

    // Appel de la view pour créer un médecin
    private void creerMedecin() {
        registerMedecin registerMedecin = new registerMedecin(this);
        registerMedecin.setVisible(true);
        this.setVisible(false); // Cache la fenêtre consulterMedecin
    }

    // Update un médecin
    private void updateMedecin() {
        try {
            String selected = (String) comboBoxMedecin.getSelectedItem();
            for (Medecin m : Medecin.getMedecins()) {
                if (selectedValue.equals(m.getNom() + " " + m.getPrenom())) {
                    registerMedecin updateMedecin = new registerMedecin(m, this);
                    updateMedecin.setVisible(true);
                    this.setVisible(false); // Cache la fenêtre consulterMedecin
                }
            }
        } catch (Exception e) {
            System.out.println("Error updateMedecin: " + e.getMessage());
        }
    }

    // Delete un médecin
    private void deleteMedecin() {
        int selectedRow = tableMedecin.getSelectedRow();

        if (selectedRow >= 0 && selectedValue != null) {
            Medecin medecinToRemove = null;
            for (Medecin m : Medecin.getMedecins()) {
                if (selectedValue.equals(m.getNom() + " " + m.getPrenom())) {
                    medecinToRemove = m;
                    break;
                }
            }
            if (medecinToRemove != null) {
                Medecin.getMedecins().remove(medecinToRemove);
                comboBoxMedecin.removeItem(selectedValue);
                tableModelMedecin.setRowCount(0);
                selectedValue = null;
                comboBoxMedecin.setSelectedIndex(0);
            }
        }
    }

    // Retour de la page
    private void retour() {
        if (previousFrame != null) {
            previousFrame.setVisible(true); // réaffiche la fenêtre précédente
        }
        this.dispose(); // ferme la fenêtre actuelle
    }

    // Quitter l'application
    private void quitter() {
        int reponse = JOptionPane.showConfirmDialog(consulterMedecin.this, "Voulez-vous quitter l'application ?", "Quitter", JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}