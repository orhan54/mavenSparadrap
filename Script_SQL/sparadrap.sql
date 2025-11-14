-- Créer la base si elle n'existe pas
CREATE DATABASE IF NOT EXISTS mavensparadrap CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;

-- Sélectionner la base
USE mavensparadrap;

-- Table Lieu
CREATE TABLE IF NOT EXISTS Lieu(
    Id_Lieu INT AUTO_INCREMENT,
    lieu_adresse VARCHAR(100) NOT NULL,
    lieu_email VARCHAR(50),
    lieu_telephone VARCHAR(12),
    lieu_ville VARCHAR(70) NOT NULL,
    lieu_cp INT NOT NULL,
    PRIMARY KEY(Id_Lieu),
    UNIQUE(lieu_email),
    UNIQUE(lieu_telephone)
    );

-- Table Pharmacie
CREATE TABLE IF NOT EXISTS Pharmacie(
    Id_Pharmacie INT AUTO_INCREMENT,
    pha_nom VARCHAR(30) NOT NULL,
    pha_prenom VARCHAR(50) NOT NULL,
    Id_Lieu INT NOT NULL,
    PRIMARY KEY(Id_Pharmacie),
    FOREIGN KEY(Id_Lieu) REFERENCES Lieu(Id_Lieu) ON DELETE CASCADE
    );

-- Table Medecin
CREATE TABLE IF NOT EXISTS Medecin(
    Id_Medecin INT AUTO_INCREMENT,
    med_nom VARCHAR(30) NOT NULL,
    med_prenom VARCHAR(50) NOT NULL,
    med_numero_agreement CHAR(11) NOT NULL,
    Id_Lieu INT NOT NULL,
    PRIMARY KEY(Id_Medecin),
    UNIQUE(med_numero_agreement),
    FOREIGN KEY(Id_Lieu) REFERENCES Lieu(Id_Lieu) ON DELETE CASCADE
    );

-- Table Stock_Medicament
CREATE TABLE IF NOT EXISTS Stock_Medicament(
    Id_Stock_Medicament INT AUTO_INCREMENT,
    medic_nom VARCHAR(50) NOT NULL,
    medic_categorie VARCHAR(50) NOT NULL,
    medic_quantite INT NOT NULL,
    medic_date_mise_en_service DATE NOT NULL,
    date_entree_stock VARCHAR(50),
    prix_unitaire DOUBLE,
    Id_Pharmacie INT NOT NULL,
    PRIMARY KEY(Id_Stock_Medicament),
    FOREIGN KEY(Id_Pharmacie) REFERENCES Pharmacie(Id_Pharmacie) ON DELETE CASCADE
    );

-- Table Mutuelle
CREATE TABLE IF NOT EXISTS Mutuelle(
    Id_Mutuelle INT AUTO_INCREMENT,
    mut_nom VARCHAR(70) NOT NULL,
    mut_taux_prise_en_charge INT NOT NULL,
    mut_num_departement INT NOT NULL,
    Id_Lieu INT,
    PRIMARY KEY(Id_Mutuelle),
    FOREIGN KEY(Id_Lieu) REFERENCES Lieu(Id_Lieu) ON DELETE SET NULL
    );

-- Table Patient
CREATE TABLE IF NOT EXISTS Patient(
    Id_Patient INT AUTO_INCREMENT,
    pat_nom VARCHAR(30) NOT NULL,
    pat_prenom VARCHAR(50) NOT NULL,
    pat_num_secu INT NOT NULL,
    pat_date_naissance DATE NOT NULL,
    Id_Lieu INT NOT NULL,
    Id_Mutuelle INT NOT NULL,
    PRIMARY KEY(Id_Patient),
    FOREIGN KEY(Id_Lieu) REFERENCES Lieu(Id_Lieu) ON DELETE CASCADE,
    FOREIGN KEY(Id_Mutuelle) REFERENCES Mutuelle(Id_Mutuelle) ON DELETE CASCADE
    );

-- Table Ordonnance
CREATE TABLE IF NOT EXISTS Ordonnance(
    Id_Ordonnance INT AUTO_INCREMENT,
    ordo_date DATE NOT NULL,
    ordo_nom_medecin VARCHAR(50) NOT NULL,
    ordo_nom_patient VARCHAR(50) NOT NULL,
    Id_Medecin INT NOT NULL,
    Id_Patient INT NOT NULL,
    PRIMARY KEY(Id_Ordonnance),
    FOREIGN KEY(Id_Medecin) REFERENCES Medecin(Id_Medecin) ON DELETE CASCADE,
    FOREIGN KEY(Id_Patient) REFERENCES Patient(Id_Patient) ON DELETE CASCADE
    );

-- Table Commande
CREATE TABLE IF NOT EXISTS Commande(
    Id_Commande INT AUTO_INCREMENT,
    com_date_commande DATE NOT NULL,
    com_nom_medecin VARCHAR(50) NOT NULL,
    com_nom_patient VARCHAR(50) NOT NULL,
    com_quantite INT NOT NULL,
    com_prix DECIMAL(6,2) NOT NULL,
    com_prise_en_charge BOOLEAN NOT NULL,
    Id_Patient INT,
    Id_Ordonnance INT,
    Id_Pharmacie INT NOT NULL,
    PRIMARY KEY(Id_Commande),
    FOREIGN KEY(Id_Patient) REFERENCES Patient(Id_Patient) ON DELETE SET NULL,
    FOREIGN KEY(Id_Ordonnance) REFERENCES Ordonnance(Id_Ordonnance) ON DELETE SET NULL,
    FOREIGN KEY(Id_Pharmacie) REFERENCES Pharmacie(Id_Pharmacie) ON DELETE CASCADE
    );

-- Table contenir
CREATE TABLE IF NOT EXISTS contenir(
    Id_Commande INT,
    Id_Stock_Medicament INT,
    total_achete INT NOT NULL,
    prix_achat INT NOT NULL,
    PRIMARY KEY(Id_Commande, Id_Stock_Medicament),
    FOREIGN KEY(Id_Commande) REFERENCES Commande(Id_Commande) ON DELETE CASCADE,
    FOREIGN KEY(Id_Stock_Medicament) REFERENCES Stock_Medicament(Id_Stock_Medicament) ON DELETE CASCADE
    );
