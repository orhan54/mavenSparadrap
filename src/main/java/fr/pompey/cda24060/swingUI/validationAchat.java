package fr.pompey.cda24060.swingUI;

import fr.pompey.cda24060.exception.SaisieException;
import fr.pompey.cda24060.model.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class validationAchat extends JFrame {
    private JPanel contentPane;
    private JButton btnRetourAchat;
    private JButton btnValiderAchat;
    private JButton buttonQuitterAchat;
    private JLabel titreValideAchat;
    private JTextField inputQuantiteMedic;
    private JButton btnAjouterMedicamentList;
    private JButton btnDelete;
    private JTable tableMedic;
    private JTable tableMedicDispo;
    private JLabel titreTypeLabel;
    private JComboBox<String> comboBoxPatient;
    private JComboBox<String> comboBoxMedicament;
    private JComboBox<String> comboBoxMedecin;
    private JTextField textFieldPrixTotalPayer;
    private JCheckBox checkBoxMutuelle;
    private JComboBox<String> comboBoxMutuelle;
    private JLabel labelPrixTotal;
    private JLabel labelDeductionMutuelle;
    private JLabel labelPrixAPayer;
    private JLabel labelTauxMutuelle;
    private JFrame previousFrame;

    private DefaultTableModel tableModelCommande;
    private DefaultTableModel tableModelMedicDispo;

    private List<Medicament> medicamentsCommande = new ArrayList<>();
    private List<Integer> quantitesMedicaments = new ArrayList<>();

    public validationAchat(String typeAchat, JFrame previousFrame) {
        this.previousFrame = previousFrame;

        ImageIcon imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/image/miniLogo.png")));
        Dimension dimension = new Dimension(1600, 1000);

        this.setTitle("Sparadrap");
        this.setIconImage(imageIcon.getImage());
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setPreferredSize(dimension);
        this.setResizable(false);
        this.setContentPane(contentPane);

        initializeNewComponents();

        // Afficher le type d'achat
        if (typeAchat.equalsIgnoreCase("direct")) {
            titreTypeLabel.setText("DIRECT");
            setMutuelleComponentsVisible(false);
        } else if (typeAchat.equalsIgnoreCase("ordonnance")) {
            titreTypeLabel.setText("ORDONNANCE");
            setMutuelleComponentsVisible(true);
            checkBoxMutuelle.setSelected(true); // auto-activer mutuelle pour ordonnance
        }

        // Tableaux pour les medicaments diponible
        String[] colonne = {"Quantité", "Date mise en service", "Prix", "Categorie", "Nom"};
        tableModelMedicDispo = new DefaultTableModel(colonne, 0);
        tableMedicDispo.setModel(tableModelMedicDispo);

        // Tableaux pour passer la commade des médicaments
        String[] colonnes = {"Nom medicament", "Quantite", "Prix unitaire", "Prix total"};
        tableModelCommande = new DefaultTableModel(colonnes, 0);
        tableMedic.setModel(tableModelCommande);

        afficherListeMedicDispo();
        remplirComboBoxMedecin();
        remplirComboBoxClient();
        remplirComboBoxMedicament();
        remplirComboBoxMutuelle();

        this.pack();
        this.setLocationRelativeTo(null);
        btnValiderAchat.setEnabled(false);

        // Gestionnaire pour la croix (X)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                retour();
            }
        });

        // Listeners
        btnRetourAchat.addActionListener(e -> retour());
        btnValiderAchat.addActionListener(e -> {
            try {
                valider();
            } catch (SaisieException ex) {
                throw new RuntimeException(ex);
            }
        });
        buttonQuitterAchat.addActionListener(e -> quitter());
        btnAjouterMedicamentList.addActionListener(e -> {
            try {
                ajouterMedicamentAuPanier();
            } catch (SaisieException ex) {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'ajout : " + ex.getMessage(),
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        btnDelete.addActionListener(e -> supprimerMedicamentDuPanier());

        // Mutuelle
        checkBoxMutuelle.addActionListener(e -> mettreAJourAffichagePrix());
        comboBoxMutuelle.addActionListener(e -> mettreAJourAffichagePrix());

        // Met à jour le prix initial
        mettreAJourAffichagePrix();
    }

    private void initializeNewComponents() {
        checkBoxMutuelle = new JCheckBox("Prise en charge mutuelle");
        checkBoxMutuelle.setSelected(false);

        comboBoxMutuelle = new JComboBox<>();
        comboBoxMutuelle.setEnabled(false);

        labelPrixTotal = new JLabel("Prix total : 0,00€");
        labelDeductionMutuelle = new JLabel("Déduction mutuelle : 0,00€");
        labelPrixAPayer = new JLabel("Prix à payer : 0,00€");
        labelTauxMutuelle = new JLabel("Taux : 0%");
    }

    private void setMutuelleComponentsVisible(boolean visible) {
        if (checkBoxMutuelle != null) checkBoxMutuelle.setVisible(visible);
        if (comboBoxMutuelle != null) comboBoxMutuelle.setVisible(visible);
        if (labelDeductionMutuelle != null) labelDeductionMutuelle.setVisible(visible);
        if (labelTauxMutuelle != null) labelTauxMutuelle.setVisible(visible);
    }

    private void remplirComboBoxMutuelle() {
        comboBoxMutuelle.removeAllItems();
        comboBoxMutuelle.addItem("-- Sélectionner une mutuelle --");
        for (Mutuelle mutuelle : Mutuelle.getMutuelles()) {
            String item = mutuelle.getNom() + " (" + String.format("%.1f", mutuelle.getTauxPriseEnCharge()) + "%)";
            comboBoxMutuelle.addItem(item);
        }
    }

    private void mettreAJourAffichagePrix() {
        double prixTotal = calculerPrixTotalPanier();
        double deduction = 0.0;
        double prixAPayer = prixTotal;
        String tauxText = "Taux : 0%";

        // Réduction mutuelle 30% si ordonnance
        if ("ORDONNANCE".equalsIgnoreCase(titreTypeLabel.getText()) && checkBoxMutuelle.isSelected()) {
            deduction = prixTotal * 0.30;
            prixAPayer = prixTotal - deduction;
            tauxText = "Taux : 30%";
        }

        labelPrixTotal.setText(String.format("Prix total : %.2f€", prixTotal));
        labelDeductionMutuelle.setText(String.format("Déduction mutuelle : %.2f€", deduction));
        labelPrixAPayer.setText(String.format("Prix à payer : %.2f€", prixAPayer));
        labelTauxMutuelle.setText(tauxText);

        textFieldPrixTotalPayer.setText(String.format("%.2f", prixAPayer));
    }

    private double calculerPrixTotalPanier() {
        double total = 0.0;
        for (int i = 0; i < medicamentsCommande.size(); i++) {
            total += medicamentsCommande.get(i).getPrix() * quantitesMedicaments.get(i);
        }
        return total;
    }

    private void remplirComboBoxMedecin() {
        comboBoxMedecin.removeAllItems();
        for (Medecin medecin : Medecin.getMedecins()) {
            comboBoxMedecin.addItem(medecin.getNom() + " " + medecin.getPrenom());
        }
    }

    private void remplirComboBoxClient() {
        comboBoxPatient.removeAllItems();
        for (Patient patient : Patient.getPatients()) {
            comboBoxPatient.addItem(patient.getNom() + " " + patient.getPrenom());
        }
    }

    private void remplirComboBoxMedicament() {
        comboBoxMedicament.removeAllItems();
        for (Medicament medicament : Medicament.getMedicaments()) {
            comboBoxMedicament.addItem(medicament.getNom());
        }
    }

    private void ajouterMedicamentAuPanier() throws SaisieException {
        String nomMedic = comboBoxMedicament.getSelectedItem().toString().trim();
        int quantite;
        try {
            quantite = Integer.parseInt(inputQuantiteMedic.getText().trim());
            if (quantite <= 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "La quantité doit être un nombre entier positif", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Medicament medicamentTrouve = null;
        for (Medicament medicament : Medicament.getMedicaments()) {
            if (medicament.getNom().equalsIgnoreCase(nomMedic)) {
                medicamentTrouve = medicament;
                break;
            }
        }
        if (medicamentTrouve == null) {
            JOptionPane.showMessageDialog(this, "Médicament introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérifier si le médicament est déjà dans le panier
        boolean dejaPresent = false;
        for (int i = 0; i < medicamentsCommande.size(); i++) {
            if (medicamentsCommande.get(i).getNom().equalsIgnoreCase(nomMedic)) {
                // Juste mettre à jour la quantité
                quantitesMedicaments.set(i, quantitesMedicaments.get(i) + quantite);
                dejaPresent = true;
                break;
            }
        }

        // Si pas déjà présent, ajouter le médicament
        if (!dejaPresent) {
            // Créer une référence au médicament original (sans quantité dedans)
            Medicament medicamentCommande = new Medicament(
                    0,
                    medicamentTrouve.getDateMiseEnService(),
                    medicamentTrouve.getPrix(),
                    medicamentTrouve.getCategorie(),
                    medicamentTrouve.getNom()
            );
            medicamentsCommande.add(medicamentCommande);
            quantitesMedicaments.add(quantite);
        }

        rafraichirTableauPanier();
        inputQuantiteMedic.setText("");
        btnValiderAchat.setEnabled(!medicamentsCommande.isEmpty());
        mettreAJourAffichagePrix();

        JOptionPane.showMessageDialog(this, "Médicament ajouté au panier !", "Succès", JOptionPane.INFORMATION_MESSAGE);
    }

    private void rafraichirTableauPanier() {
        tableModelCommande.setRowCount(0);
        for (int i = 0; i < medicamentsCommande.size(); i++) {
            Medicament med = medicamentsCommande.get(i);
            int qty = quantitesMedicaments.get(i);
            double prixTotal = med.getPrix() * qty;
            tableModelCommande.addRow(new Object[]{
                    med.getNom(),
                    qty,
                    String.format("%.2f€", med.getPrix()),
                    String.format("%.2f€", prixTotal)
            });
        }
    }

    private void afficherListeMedicDispo() {
        tableModelMedicDispo.setRowCount(0);
        if (Medicament.getMedicaments().isEmpty()) {
            tableModelMedicDispo.addRow(new Object[]{"-", "Aucun medicament", "", "", ""});
        } else {
            for (Medicament medicaments : Medicament.getMedicaments()) {
                tableModelMedicDispo.addRow(new Object[]{
                        medicaments.getQuantite(),
                        medicaments.getDateMiseEnService(),
                        String.format("%.2f€", medicaments.getPrix()),
                        medicaments.getCategorie(),
                        medicaments.getNom(),
                });
            }
        }
    }

    private void supprimerMedicamentDuPanier() {
        int selectedRow = tableMedic.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < medicamentsCommande.size()) {
            medicamentsCommande.remove(selectedRow);
            quantitesMedicaments.remove(selectedRow);
            rafraichirTableauPanier();
            btnValiderAchat.setEnabled(!medicamentsCommande.isEmpty());
            mettreAJourAffichagePrix();
            JOptionPane.showMessageDialog(this, "Médicament retiré du panier", "Information", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médicament à supprimer", "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void retour() {
        int reponse = JOptionPane.showConfirmDialog(this, "Des médicaments sont dans le panier. Voulez-vous vraiment annuler ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            // Réaffiche la fenêtre précédente si elle existe
            if (previousFrame != null) {
                previousFrame.setVisible(true);
            }
            // Ferme la fenêtre actuelle
            this.dispose();
        }
    }

    private void valider() throws SaisieException {
        if (medicamentsCommande.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucun médicament dans le panier", "Panier vide", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (comboBoxMedecin.getSelectedItem() == null || comboBoxPatient.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un médecin et un patient", "Information manquante", JOptionPane.WARNING_MESSAGE);
            return;
        }

        double prixTotal = calculerPrixTotalPanier();
        double deduction = 0.0;
        double prixAPayer = prixTotal;
        boolean priseEnCharge = false;

        // Vérifier si c'est une ordonnance avec mutuelle
        if ("ORDONNANCE".equalsIgnoreCase(titreTypeLabel.getText()) && checkBoxMutuelle.isSelected()) {
            deduction = prixTotal * 0.30;
            prixAPayer = prixTotal - deduction;
            priseEnCharge = true;
        }

        List<Medicament> medicamentsAvecQuantites = new ArrayList<>();
        for (int i = 0; i < medicamentsCommande.size(); i++) {
            Medicament medOriginal = medicamentsCommande.get(i);
            int quantite = quantitesMedicaments.get(i);

            // Créer un nouveau médicament avec la bonne quantité
            Medicament medAvecQte = new Medicament(
                    quantite,  // ← La vraie quantité
                    medOriginal.getDateMiseEnService(),
                    medOriginal.getPrix(),
                    medOriginal.getCategorie(),
                    medOriginal.getNom()
            );
            medicamentsAvecQuantites.add(medAvecQte);
        }

        // Créer la commande
        Commande.TypeAchat typeAchat = "DIRECT".equalsIgnoreCase(titreTypeLabel.getText())
                ? Commande.TypeAchat.DIRECT
                : Commande.TypeAchat.ORDONNANCE;

        int quantiteTotale = quantitesMedicaments.stream().mapToInt(Integer::intValue).sum();

        Commande commande = new Commande(
                new java.sql.Date(System.currentTimeMillis()),
                typeAchat,
                comboBoxMedecin.getSelectedItem().toString(),
                comboBoxPatient.getSelectedItem().toString(),
                medicamentsAvecQuantites,  // ← Liste avec les bonnes quantités
                quantiteTotale,
                prixTotal,
                null,
                priseEnCharge
        );

        // Message adapté selon le type d'achat
        String message = "Commande validée !\n" +
                "Prix total : " + String.format("%.2f€", prixTotal) + "\n";

        if (priseEnCharge) {
            message += "Prise en charge mutuelle : 30%\n";
        }

        message += "Prix à payer : " + String.format("%.2f€", prixAPayer);

        JOptionPane.showMessageDialog(this, message, "Succès", JOptionPane.INFORMATION_MESSAGE);

        Menu menu = new Menu();
        menu.setVisible(true);
        this.dispose();
    }

    private void quitter() {
        int reponse = JOptionPane.showConfirmDialog(this, "Voulez-vous quitter l'application ?", "Quitter", JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}