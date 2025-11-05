package fr.pompey.cda24060.swingUI;

import fr.pompey.cda24060.model.*;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainSwingUI {
    public static void main(String[] args) throws Exception {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
            initialisation();
            Menu myMenu = new Menu();
            myMenu.setVisible(true);
        }catch (Exception e) {
            System.out.println("Error au lancement de la vue Swing " + e.getMessage());
        }
    }

    public static void initialisation() {
        try{
            // Création des lieux
            Lieu lieu1 = new Lieu("12 rue de le guerre", "dupontjean@live.fr.com", "+33312345678", "Nancy", 54000);
            Lieu lieu2 = new Lieu("2 rue de la paix", "martinpaul@gmail.com", "+33383813059", "PAM", 54700);
            Lieu lieu3 = new Lieu("55 avenue des malades", "macif@gmail.com", "+33383812010", "Dieulouard", 54380);
            Lieu lieu4 = new Lieu("205 avenue de la liberté", "francemutuelle@live.fr", "+33356843890", "Bordeaux", 33000);
            Lieu lieu5 = new Lieu("10 place Stanislas", "mutuelleverte@live.fr", "+33383391234", "Nancy", 54000);
            Lieu lieu6 = new Lieu("48 rue Nationale", "francemaladie@live.fr", "+33387225678", "Metz", 57000);
            Lieu lieu7 = new Lieu("3 rue Sainte Catherine", "mutuelleeurope@gmail.com", "+33556874532", "Strasbourg", 67000);

            // Création des medicaments
            Medicament medic1 = new Medicament(5, "10-05-2025", 10.50, "Cardiotoniques", "Digoxine");
            Medicament medic2 = new Medicament(2, "02-09-2025", 5.99, "Bêta-bloquants", "Bisoprolol");
            Medicament medic3 = new Medicament(6, "28-05-2025", 9.99, "Diurétiques", "Mannitol");
            Medicament medic4 = new Medicament(2, "22-01-2025", 25.99, "Hypnotiques", "Méthaqualone");
            Medicament medic5 = new Medicament(4, "02-03-2025", 15.99, "Triptans", "Élétriptan");

            // Médicaments supplémentaires pour les commandes
            Medicament medic6 = new Medicament(10, "15-04-2025", 8.50, "Antibiotiques", "Amoxicilline");

            // === Création de plusieurs ordonnances par médecin ===
            // Dr Martin (3 ordonnances)
            List<Medicament> ordoM1a = new ArrayList<>();
            ordoM1a.add(medic1); // Digoxine
            ordoM1a.add(medic2); // Bisoprolol
            Ordonnance ordoM1_1 = new Ordonnance("12/05/2025", "Dr Martin Paul", "Jean Dupont", ordoM1a);

            List<Medicament> ordoM1b = new ArrayList<>();
            ordoM1b.add(medic3); // Mannitol
            Ordonnance ordoM1_2 = new Ordonnance("18/06/2025", "Dr Martin Paul", "Claire Martin", ordoM1b);

            List<Medicament> ordoM1c = new ArrayList<>();
            ordoM1c.add(medic5); // Élétriptan
            ordoM1c.add(medic6); // Amoxicilline
            Ordonnance ordoM1_3 = new Ordonnance("02/07/2025", "Dr Martin Paul", "Paul Durand", ordoM1c);


            // Dr Robert (4 ordonnances)
            List<Medicament> ordoM2a = new ArrayList<>();
            ordoM2a.add(medic3); // Mannitol
            Ordonnance ordoM2_1 = new Ordonnance("20/06/2025", "Dr Robert François", "Claire Martin", ordoM2a);

            List<Medicament> ordoM2b = new ArrayList<>();
            ordoM2b.add(medic4); // Méthaqualone
            ordoM2b.add(medic2); // Bisoprolol
            Ordonnance ordoM2_2 = new Ordonnance("25/07/2025", "Dr Robert François", "Lucas Bernard", ordoM2b);

            List<Medicament> ordoM2c = new ArrayList<>();
            ordoM2c.add(medic1);
            ordoM2c.add(medic5);
            ordoM2c.add(medic6);
            Ordonnance ordoM2_3 = new Ordonnance("01/08/2025", "Dr Robert François", "Jean Dupont", ordoM2c);

            List<Medicament> ordoM2d = new ArrayList<>();
            ordoM2d.add(medic2);
            Ordonnance ordoM2_4 = new Ordonnance("10/08/2025", "Dr Robert François", "Paul Durand", ordoM2d);


            // Dr Dubois (3 ordonnances)
            List<Medicament> ordoM3a = new ArrayList<>();
            ordoM3a.add(medic1);
            ordoM3a.add(medic4);
            ordoM3a.add(medic5);
            Ordonnance ordoM3_1 = new Ordonnance("28/07/2025", "Dr Dubois Anne", "Paul Durand", ordoM3a);

            List<Medicament> ordoM3b = new ArrayList<>();
            ordoM3b.add(medic6);
            ordoM3b.add(medic3);
            Ordonnance ordoM3_2 = new Ordonnance("05/08/2025", "Dr Dubois Anne", "Sophie Petit", ordoM3b);

            List<Medicament> ordoM3c = new ArrayList<>();
            ordoM3c.add(medic2);
            ordoM3c.add(medic5);
            Ordonnance ordoM3_3 = new Ordonnance("18/08/2025", "Dr Dubois Anne", "Lucas Bernard", ordoM3c);


            // Dr Morel (3 ordonnances)
            List<Medicament> ordoM4a = new ArrayList<>();
            ordoM4a.add(medic2);
            ordoM4a.add(medic3);
            Ordonnance ordoM4_1 = new Ordonnance("29/07/2025", "Dr Morel Hélène", "Sophie Petit", ordoM4a);

            List<Medicament> ordoM4b = new ArrayList<>();
            ordoM4b.add(medic4);
            Ordonnance ordoM4_2 = new Ordonnance("03/08/2025", "Dr Morel Hélène", "Jean Dupont", ordoM4b);

            List<Medicament> ordoM4c = new ArrayList<>();
            ordoM4c.add(medic1);
            ordoM4c.add(medic5);
            ordoM4c.add(medic6);
            Ordonnance ordoM4_3 = new Ordonnance("15/08/2025", "Dr Morel Hélène", "Claire Martin", ordoM4c);


            // Dr Lefevre (3 ordonnances)
            List<Medicament> ordoM5a = new ArrayList<>();
            ordoM5a.add(medic5);
            Ordonnance ordoM5_1 = new Ordonnance("05/08/2025", "Dr Lefevre Julien", "Lucas Bernard", ordoM5a);

            List<Medicament> ordoM5b = new ArrayList<>();
            ordoM5b.add(medic2);
            ordoM5b.add(medic3);
            Ordonnance ordoM5_2 = new Ordonnance("12/08/2025", "Dr Lefevre Julien", "Paul Durand", ordoM5b);

            List<Medicament> ordoM5c = new ArrayList<>();
            ordoM5c.add(medic1);
            ordoM5c.add(medic6);
            Ordonnance ordoM5_3 = new Ordonnance("22/08/2025", "Dr Lefevre Julien", "Sophie Petit", ordoM5c);

            // Création des mutuelles
            Mutuelle m1 = new Mutuelle("Macif", 30, 54, lieu3);
            Mutuelle m2 = new Mutuelle("France Mutuelle", 30, 57, lieu4);
            Mutuelle m3 = new Mutuelle("Mutuelle Verte", 30, 88, lieu5);
            Mutuelle m4 = new Mutuelle("France Maladie", 30, 88, lieu6);
            Mutuelle m5 = new Mutuelle("Mutuelle Europe", 30, 88, lieu7);

            // Création des médecins
            Medecin med1 = new Medecin("Dr Martin", "Paul", "12345678912", lieu2);
            Medecin med2 = new Medecin("Dr Robert", "François", "22345678912", lieu3);
            Medecin med3 = new Medecin("Dr Dubois", "Anne", "32345678912", lieu4);
            Medecin med4 = new Medecin("Dr Morel", "Hélène", "42345678912", lieu5);
            Medecin med5 = new Medecin("Dr Lefevre", "Julien", "52345678912", lieu6);

            // Création des patients
            Patient p1 = new Patient("Dupont", "Jean","16-05-1985", lieu1, m1, med1);
            Patient p2 = new Patient("Martin", "Claire","20-06-1982", lieu2, m2, med2);
            Patient p3 = new Patient("Durand", "Paul","19-02-1975", lieu3, m3, med3);
            Patient p4 = new Patient("Petit", "Sophie", "25-10-1973", lieu4, m4, med4);
            Patient p5 = new Patient("Bernard", "Lucas", "22-09-1965", lieu5, m5, med5);

            // Création des commandes avec dates différentes
            // Commande 1 - Achat direct (pas de mutuelle)
            List<Medicament> medicamentsCmd1 = new ArrayList<>();
            medicamentsCmd1.add(medic1); // Digoxine
            Commande cmd1 = new Commande(Date.valueOf("2025-05-12"),
                    Commande.TypeAchat.DIRECT,
                    "Dr Martin",
                    "Jean Dupont",
                    medicamentsCmd1,
                    2,
                    21.0
            );

            // Commande 2 - Ordonnance avec mutuelle
            List<Medicament> medicamentsCmd2 = new ArrayList<>();
            medicamentsCmd2.add(medic6); // Amoxicilline
            Commande cmd2 = new Commande(Date.valueOf("2025-06-15"),
                    Commande.TypeAchat.ORDONNANCE,
                    "Dr Robert",
                    "Paul Durand",
                    medicamentsCmd2,
                    5,
                    42.5,
                    true // prise en charge mutuelle activée
            );

            // Commande 3 - Achat direct (pas de mutuelle)
            List<Medicament> medicamentsCmd3 = new ArrayList<>();
            medicamentsCmd3.add(medic3); // Mannitol
            medicamentsCmd3.add(medic2); // Bisoprolol
            Commande cmd3 = new Commande(Date.valueOf("2025-07-01"),
                    Commande.TypeAchat.DIRECT,
                    "Dr Bernard",
                    "Claire Martin",
                    medicamentsCmd3,
                    4,
                    63.92
            );

            // Commande 4 - Achat direct avec un seul médicament
            Commande cmd4 = new Commande(Date.valueOf("2025-07-20"),
                    Commande.TypeAchat.DIRECT,
                    "Dr Dubois",
                    "Sophie Petit",
                    medic4, // Méthaqualone
                    6,
                    155.94
            );

            // Commande 5 - Ordonnance avec mutuelle
            List<Medicament> medicamentsCmd5 = new ArrayList<>();
            medicamentsCmd5.add(medic5); // Élétriptan
            medicamentsCmd5.add(medic1); // Digoxine
            Commande cmd5 = new Commande(Date.valueOf("2025-08-05"),
                    Commande.TypeAchat.ORDONNANCE,
                    "Dr Morel",
                    "Lucas Bernard",
                    medicamentsCmd5,
                    8,
                    210.0,
                    true // prise en charge mutuelle activée
            );

            // Ajout des mutuelles dans la liste statique
            System.out.println("=== MUTUELLES CRÉÉES ===");
            Mutuelle.getMutuelles().add(m1);
            Mutuelle.getMutuelles().add(m2);
            Mutuelle.getMutuelles().add(m3);
            Mutuelle.getMutuelles().add(m4);
            Mutuelle.getMutuelles().add(m5);

            // Ajout des medecins dans la liste statique
            System.out.println("=== MEDECINS CRÉÉES ===");
            Medecin.getMedecins().add(med1);
            Medecin.getMedecins().add(med2);
            Medecin.getMedecins().add(med3);
            Medecin.getMedecins().add(med4);
            Medecin.getMedecins().add(med5);

            // Ajout des patients dans la liste statique
            System.out.println("=== PATIENTS CRÉÉES ===");
            Patient.getPatients().add(p1);
            Patient.getPatients().add(p2);
            Patient.getPatients().add(p3);
            Patient.getPatients().add(p4);
            Patient.getPatients().add(p5);

            // Ajout dans la liste des medicaments
            System.out.println("=== MEDICAMENTS CRÉÉES ===");
            Medicament.getMedicaments().add(medic1);
            Medicament.getMedicaments().add(medic2);
            Medicament.getMedicaments().add(medic3);
            Medicament.getMedicaments().add(medic4);
            Medicament.getMedicaments().add(medic5);
            Medicament.getMedicaments().add(medic6);

            // Ajout des commandes
            System.out.println("=== COMMANDES CRÉÉES ===");
            Commande.getCommandes().add(cmd1);
            Commande.getCommandes().add(cmd2);
            Commande.getCommandes().add(cmd3);
            Commande.getCommandes().add(cmd4);
            Commande.getCommandes().add(cmd5);

            // Affichage des informations
            System.out.println("=== INFORMATIONS CRÉÉES ===");
            System.out.println(m1);
            System.out.println(p1);
            System.out.println(p2);
            System.out.println(med1);

            // Affichage des commandes avec les nouvelles informations
            System.out.println("=== COMMANDES AVEC MÉDICAMENTS ===");
            System.out.println(cmd1);
            System.out.println(cmd2);
            System.out.println(cmd3);
            System.out.println(cmd4);
            System.out.println(cmd5);

            System.out.println("Medicaments disponibles : " + "\n" + Medicament.getMedicaments());

            // Affichage des ordonnances créées
            System.out.println("=== ORDONNANCES CRÉÉES ===");

            // Dr Martin
            System.out.println(ordoM1_1);
            System.out.println(ordoM1_2);
            System.out.println(ordoM1_3);

            // Dr Robert
            System.out.println(ordoM2_1);
            System.out.println(ordoM2_2);
            System.out.println(ordoM2_3);
            System.out.println(ordoM2_4);

            // Dr Dubois
            System.out.println(ordoM3_1);
            System.out.println(ordoM3_2);
            System.out.println(ordoM3_3);

            // Dr Morel
            System.out.println(ordoM4_1);
            System.out.println(ordoM4_2);
            System.out.println(ordoM4_3);

            // Dr Lefevre
            System.out.println(ordoM5_1);
            System.out.println(ordoM5_2);
            System.out.println(ordoM5_3);

        }catch(Exception e){
            System.out.println("Erreur de la vue initialisation menu swing " + e.getMessage());
            e.printStackTrace();
        }
    }
}