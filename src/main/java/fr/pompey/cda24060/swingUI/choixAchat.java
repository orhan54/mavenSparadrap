package fr.pompey.cda24060.swingUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Objects;

public class choixAchat extends JFrame {
    private JPanel contentPane;
    private JPanel logoChoixAchat;
    private JPanel logoMainAchat;
    private JPanel logoFooterAchat;
    private JButton achatDirect;
    private JButton achatOrdonnance;
    private JButton buttonQuitterChoix;
    private JButton retourChoixAchat;
    private JLabel titreChoixAchat;
    private JFrame previousFrame;

    /**
     * Instantiates a new Choix achat.
     */
    public choixAchat(JFrame previousFrame) {
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

        this.pack();
        this.setLocationRelativeTo(null);

        // Gestionnaire pour la croix (X)
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                retour();
            }
        });

        achatDirect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeAchatDirect();
            }
        });
        achatOrdonnance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                typeAchatOrdonnance();
            }
        });
        retourChoixAchat.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retour();
            }
        });
        buttonQuitterChoix.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitter();
            }
        });
    }

    private void typeAchatDirect() {
        validationAchat validationAchat = new validationAchat("direct", this);
        this.setVisible(false);
        validationAchat.setVisible(true);
    }

    private void typeAchatOrdonnance() {
        validationAchat validationAchat = new validationAchat("ordonnance", this);
        this.setVisible(false);
        validationAchat.setVisible(true);
    }

    private void retour() {
        if (previousFrame != null) {
            previousFrame.setVisible(true); // réaffiche la fenêtre précédente
        }
        this.dispose(); // ferme la fenêtre actuelle
    }

    private void quitter() {
        int reponse = JOptionPane.showConfirmDialog(choixAchat.this, "Voulez-vous quitter l'application ?", "Quitter", JOptionPane.YES_NO_OPTION);
        if (reponse == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}