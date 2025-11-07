package fr.pompey.cda24060.swingUI;

import fr.pompey.cda24060.model.Commande;
import fr.pompey.cda24060.model.Stock_Medicament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

public class historiqueAchat extends JFrame {
    private JPanel contentPane;
    private JComboBox<String> comboBoxTypeHistoriqueAchat;
    private JTextField textFieldDate1; // Date début
    private JTextField textFieldDate2; // Date fin
    private JButton buttonRetourHistorique;
    private JButton buttonValiderHistorique;
    private JButton buttonQuitterHistorique;
    private JLabel titreHistorique;
    private JTable tableHistorique;
    private JButton rechercherButton;
    private JButton informationButton;
    private JFrame previousFrame;

    private DefaultTableModel tableModelHistorique;

    public historiqueAchat(JFrame previousFrame) {
        this.previousFrame = previousFrame;

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/miniLogo.png")));
        Dimension dimension = new Dimension(1600, 1000);

        this.setTitle("Sparadrap - Historique des Achats");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(dimension);
        this.setResizable(false);
        this.setContentPane(contentPane);

        // Colonnes du tableau
        String[] colonnes = {"Date", "Type achat", "Nom medecin", "Nom patient", "Médicaments", "Quantité totale", "Prix total"};
        tableModelHistorique = new DefaultTableModel(colonnes, 0);
        tableHistorique.setModel(tableModelHistorique);

        initialiserComboBox();
        afficherCommandes();

        // Champs input pour les dates
        textFieldDate1.setToolTipText("Format: dd/MM/yyyy"); // Debut
        textFieldDate2.setToolTipText("Format: dd/MM/yyyy"); // Fin

        this.pack();
        this.setLocationRelativeTo(null);

        // Gestionnaire pour la croix (X)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                retour();
            }
        });

        // Listeners boutons
        buttonRetourHistorique.addActionListener(e -> retour());
        buttonValiderHistorique.addActionListener(e -> valider());
        buttonQuitterHistorique.addActionListener(e -> quitter());

        // Filtre type
        comboBoxTypeHistoriqueAchat.addItemListener(e -> {
            if (e.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
                appliquerFiltres();
            }
        });

        // Bouton rechercher
        rechercherButton.addActionListener(e -> valider());

        //Button information qui va ouvrir une nouvelle JFrame
        informationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                informationCommande();
            }
        });
    }

    private void initialiserComboBox() {
        comboBoxTypeHistoriqueAchat.removeAllItems();
        comboBoxTypeHistoriqueAchat.addItem("Tous");
        comboBoxTypeHistoriqueAchat.addItem("DIRECT");
        comboBoxTypeHistoriqueAchat.addItem("ORDONNANCE");
    }

    // afficher les commandes
    private void afficherCommandes() {
        tableModelHistorique.setRowCount(0);
        if (Commande.getCommandes().isEmpty()) {
            tableModelHistorique.addRow(new Object[]{"Aucune commande", "", "", "", "", "", ""});
        } else {
            for (Commande commande : Commande.getCommandes()) {
                tableModelHistorique.addRow(new Object[]{
                        commande.getDateCommandeCreation(),
                        commande.getTypeAchat().toString(),
                        commande.getNomMedecin(),
                        commande.getNomPatient(),
                        construireChaineListeMedicaments(commande),
                        commande.getQuantite(),
                        String.format("%.2f€", commande.getPrix())
                });
            }
        }
    }

    private String construireChaineListeMedicaments(Commande commande) {
        if (commande.getMedicaments() == null || commande.getMedicaments().isEmpty()) return "Aucun médicament";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < commande.getMedicaments().size(); i++) {
            Stock_Medicament med = commande.getMedicaments().get(i);
            if (i > 0) sb.append(", ");
            sb.append(med.getNom());
        }
        String result = sb.toString();
        return result.length() > 50 ? result.substring(0, 47) + "..." : result;
    }

    // Filtre qui affiche les commandes entre 2 date
    private void appliquerFiltres() {
        tableModelHistorique.setRowCount(0);

        String typeFiltre = comboBoxTypeHistoriqueAchat.getSelectedItem().toString();
        LocalDate dateDebut = null, dateFin = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try {
            if (!textFieldDate1.getText().trim().isEmpty())
                dateDebut = LocalDate.parse(textFieldDate1.getText().trim(), formatter);
            if (!textFieldDate2.getText().trim().isEmpty())
                dateFin = LocalDate.parse(textFieldDate2.getText().trim(), formatter);
        } catch (DateTimeParseException ignored) {}

        for (Commande commande : Commande.getCommandes()) {
            boolean afficher = true;

            if (!"Tous".equals(typeFiltre) && !commande.getTypeAchat().toString().equals(typeFiltre)) afficher = false;

            if (afficher && (dateDebut != null || dateFin != null)) {
                LocalDate dateCommande = commande.getDateCommande().toLocalDate();
                if (dateDebut != null && dateCommande.isBefore(dateDebut)) afficher = false;
                if (dateFin != null && dateCommande.isAfter(dateFin)) afficher = false;
            }

            if (afficher) {
                tableModelHistorique.addRow(new Object[]{
                        commande.getDateCommandeCreation(),
                        commande.getTypeAchat().toString(),
                        commande.getNomMedecin(),
                        commande.getNomPatient(),
                        construireChaineListeMedicaments(commande),
                        commande.getQuantite(),
                        String.format("%.2f€", commande.getPrix())
                });
            }
        }

        if (tableModelHistorique.getRowCount() == 0)
            tableModelHistorique.addRow(new Object[]{"Aucun résultat", "pour les critères", "de recherche", "sélectionnés", "", "", ""});
    }

    // Validation des 2 champs input date et verification pattern
    private void valider() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        boolean dateValide = true;

        if (!textFieldDate1.getText().trim().isEmpty()) {
            try { LocalDate.parse(textFieldDate1.getText().trim(), formatter); }
            catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Format de date début invalide. Utilisez dd/MM/yyyy", "Erreur de date", JOptionPane.ERROR_MESSAGE);
                dateValide = false;
            }
        }

        if (!textFieldDate2.getText().trim().isEmpty() && dateValide) {
            try { LocalDate.parse(textFieldDate2.getText().trim(), formatter); }
            catch (DateTimeParseException e) {
                JOptionPane.showMessageDialog(this, "Format de date fin invalide. Utilisez dd/MM/yyyy", "Erreur de date", JOptionPane.ERROR_MESSAGE);
                dateValide = false;
            }
        }

        if (dateValide && !textFieldDate1.getText().trim().isEmpty() && !textFieldDate2.getText().trim().isEmpty()) {
            LocalDate debut = LocalDate.parse(textFieldDate1.getText().trim(), formatter);
            LocalDate fin = LocalDate.parse(textFieldDate2.getText().trim(), formatter);
            if (debut.isAfter(fin)) {
                JOptionPane.showMessageDialog(this, "La date de début ne peut pas être postérieure à la date de fin", "Erreur de période", JOptionPane.ERROR_MESSAGE);
                dateValide = false;
            }
        }

        if (dateValide) {
            appliquerFiltres();
        }
    }

    // Affichage commande avec détails
    private void informationCommande() {
        int selectedRow = tableHistorique.getSelectedRow();
        if (selectedRow == -1 || Commande.getCommandes().isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Veuillez sélectionner une commande dans le tableau.",
                    "Aucune sélection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Récupération de la commande correspondante (même index que dans la liste Commandes)
        Commande commande = Commande.getCommandes().get(selectedRow);

        // Création de la fenêtre popup
        JDialog dialog = new JDialog(this, "Informations sur la commande", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // Panel principal
        JPanel panelInfos = new JPanel();
        panelInfos.setLayout(new BoxLayout(panelInfos, BoxLayout.Y_AXIS));
        panelInfos.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Infos générales
        panelInfos.add(new JLabel("Date : " + commande.getDateCommandeCreation()));
        panelInfos.add(new JLabel("Type d'achat : " + commande.getTypeAchat()));
        panelInfos.add(new JLabel("Médecin : " + commande.getNomMedecin()));
        panelInfos.add(new JLabel("Patient : " + commande.getNomPatient()));
        panelInfos.add(new JLabel(""));

        // Tableau des médicaments
        String[] colonnes = {"Médicament", "Quantité"};
        DefaultTableModel modelMed = new DefaultTableModel(colonnes, 0);

        if (commande.getMedicaments() != null && !commande.getMedicaments().isEmpty()) {
            for (Stock_Medicament med : commande.getMedicaments()) {
                modelMed.addRow(new Object[]{med.getNom(), med.getQuantite()});
            }
        } else {
            modelMed.addRow(new Object[]{"Aucun médicament", ""});
        }

        JTable tableMeds = new JTable(modelMed);
        JScrollPane scrollPane = new JScrollPane(tableMeds);

        // Ajout au dialog
        dialog.add(panelInfos, BorderLayout.NORTH);
        dialog.add(scrollPane, BorderLayout.CENTER);

        // Bouton fermer
        JButton closeBtn = new JButton("Fermer");
        closeBtn.addActionListener(e -> dialog.dispose());
        JPanel panelBtn = new JPanel();
        panelBtn.add(closeBtn);

        dialog.add(panelBtn, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }


    private void retour() {
        if (previousFrame != null) {
            previousFrame.setVisible(true); // réaffiche la fenêtre précédente
        }
        this.dispose(); // ferme la fenêtre actuelle
    }

    private void quitter() {
        int reponse = JOptionPane.showConfirmDialog(this, "Voulez-vous quitter l'application ?", "Quitter", JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) System.exit(0);
    }
}