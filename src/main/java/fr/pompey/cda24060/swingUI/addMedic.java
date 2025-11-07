package fr.pompey.cda24060.swingUI;

import fr.pompey.cda24060.model.Stock_Medicament;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class addMedic extends JFrame {

    private JPanel contentPane;
    private JLabel titreAddMedic;
    private JPanel footerAddMedic;
    private JPanel mainAddMedic;
    private JButton retourButton;
    private JButton quitterButton;
    private JComboBox comboBoxDetailsMedic;
    private JTable tableDetailsMedic;
    private JFrame previousFrame;

    private DefaultTableModel tableModel;

    public addMedic(JFrame previousFrame) {
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

        String[] colonnes = {"Nom médicament", "Catégorie médicament", "Prix médicament", "Date mise en service", "Quantité médicament", "Date enregistrement stock"};
        tableModel = new DefaultTableModel(colonnes, 0);
        tableDetailsMedic.setModel(tableModel);

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

    private void remplirComboBox() {
        comboBoxDetailsMedic.removeAllItems();
        comboBoxDetailsMedic.addItem("Voir le détails d'un médicament");
        comboBoxDetailsMedic.setSelectedIndex(0);

        for(Stock_Medicament medicament : Stock_Medicament.getMedicaments()) {
            comboBoxDetailsMedic.addItem(medicament.getNom());
        }

        comboBoxDetailsMedic.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                e.getSource();

                String selected = (String) comboBoxDetailsMedic.getSelectedItem();

                if(selected.equals(comboBoxDetailsMedic.getSelectedItem())) {
                    tableModel.setRowCount(0);

                    for(Stock_Medicament m : Stock_Medicament.getMedicaments()) {
                        if(m.getNom().equals(comboBoxDetailsMedic.getSelectedItem())) {
                            tableModel.addRow(new Object[] {
                                    m.getNom(),
                                    m.getCategorie(),
                                    m.getPrix() + "€",
                                    m.getDateMiseEnService(),
                                    m.getQuantite(),
                                    m.getDateEntreeStock()
                            });
                        }
                    }
                }

            }
        });
    }

    private void retour() {
        if (previousFrame != null) {
            previousFrame.setVisible(true); // réaffiche la fenêtre précédente
        }
        this.dispose(); // ferme la fenêtre actuelle
    }

    private void quitter() {
        int reponse = JOptionPane.showConfirmDialog(addMedic.this, "Voulez-vous quitter l'application ?", "Quitter", JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }


}