package fr.pompey.cda24060.swingUI;

import fr.pompey.cda24060.exception.SaisieException;
import fr.pompey.cda24060.model.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class consulterClient extends JFrame {
    private JPanel contentPane;
    private JLabel titreMenu;
    private JTable tableClient;
    private JButton modifierButton;
    private JButton supprimerButton;
    private JButton retourButton;
    private JButton quitterButton;
    private JButton créerUnCompteButton;
    private JComboBox comboBoxClient;
    private String selectedValue;
    private JFrame previousFrame;

    private DefaultTableModel tableModelClient;

    public consulterClient(JFrame previousFrame) throws SaisieException {
        this.previousFrame = previousFrame;

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/miniLogo.png")));
        Dimension dimension = new Dimension(1600, 1000);

        //les attributs
        this.setTitle("Sparadrap");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(dimension);
        this.setResizable(false);
        this.setContentPane(contentPane);

        String[] colonnes = {"id", "Nom", "Prenom", "Adresse", "Code postal", "Ville", "Téléphone", "Email", "Numero sécurité social", "Date de naissance", "Mutuelle", "Medecin"};
        tableModelClient = new DefaultTableModel(colonnes, 0);
        tableClient.setModel(tableModelClient);

        // Ajout list item dans combobox
        remplirComboBox();

        this.pack();
        this.setLocationRelativeTo(null);

        // Gestionnaire pour la croix (X)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                retour();
            }
        });

        créerUnCompteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addClient();
            }
        });

        modifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateClient();
            }
        });

        supprimerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteClient();
            }
        });

        retourButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retour();
            }
        });

        quitterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitter();
            }
        });
    }

    private void remplirComboBox() throws SaisieException {
        comboBoxClient.removeAllItems();

        comboBoxClient.addItem("Choisir un client");
        comboBoxClient.setSelectedItem(0);

        for(Patient p : Patient.getPatients()) {
            comboBoxClient.addItem(p.getNom() + " " + p.getPrenom());
        }

        comboBoxClient.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.getSource();

                String selected = (String) comboBoxClient.getSelectedItem();
                //System.out.println("Vous avez séléctionné : " + selected);
                selectedValue = selected;

                if(selected.equals(comboBoxClient.getSelectedItem())) {
                    tableModelClient.setRowCount(0);

                    for(Patient p : Patient.getPatients()) {
                        if(selected.equals(p.getNom() + " " + p.getPrenom())) {
                            tableModelClient.addRow(new Object[]{
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
                                    p.getMutuelle().getNom(),
                                    p.getMedecin().getNom()
                            });
                        }
                    }
                }
                //System.out.println(selectedValue);
            }
        });
    }

    // Appel de la view pour créer un client
    private void addClient() {
        try {
            registerClient registerClient = new registerClient(this);
            registerClient.setVisible(true);
            this.setVisible(false); // Cache la fenêtre consulterClient
        }catch(Exception e){
            System.out.println("Erreur sur la vue créer un client" + e.getMessage());
        }
    }

    // Maj d'un client
    private void updateClient() {
        try {
            String selectedClient = comboBoxClient.getSelectedItem().toString();
            for (Patient p : Patient.getPatients()) {
                if (selectedClient.equals(p.getNom() + " " + p.getPrenom())) {
                    registerClient updateClient = new registerClient(p, this);
                    updateClient.setVisible(true);
                    this.setVisible(false); // Cache la fenêtre consulterClient
                    System.out.println(p);
                }
            }
        } catch (Exception e) {
            System.out.println("Error sur le lancement de la view update client : " + e.getMessage());
        }
    }

    // delete client
    private void deleteClient() {
        int selectedRow = tableClient.getSelectedRow();

        if (selectedRow >= 0 && selectedValue != null) {
            Patient patientToRemove = null;

            for (Patient p : Patient.getPatients()) {
                if (selectedValue.equals(p.getNom() + " " + p.getPrenom())) {
                    patientToRemove = p;
                    break;
                }
            }

            if (patientToRemove != null) {
                Patient.getPatients().remove(patientToRemove);

                // Mise à jour ComboBox
                comboBoxClient.removeItem(selectedValue);

                // Vider le tableau après suppression
                tableModelClient.setRowCount(0);

                // Réinitialiser la sélection
                selectedValue = null;
                comboBoxClient.setSelectedIndex(0); // revient sur "Choisir un client"
            }
        }
    }

    private void retour() {
        if (previousFrame != null) {
            previousFrame.setVisible(true); // réaffiche la fenêtre précédente
        }
        this.dispose(); // ferme la fenêtre actuelle
    }

    private void quitter() {
        int reponse = JOptionPane.showConfirmDialog(consulterClient.this, "Voulez-vous quitter l'application ?", "Quitter", JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}