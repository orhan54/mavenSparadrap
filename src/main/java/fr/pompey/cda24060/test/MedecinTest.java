package fr.pompey.cda24060.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.pompey.cda24060.exception.SaisieException;
import fr.pompey.cda24060.model.Lieu;
import fr.pompey.cda24060.model.Medecin;

import static org.junit.jupiter.api.Assertions.*;

class MedecinTest {

    private Medecin medecin;
    private Lieu lieu;

    @BeforeEach
    void setUp() throws SaisieException {
        // Création d'un lieu valide pour le médecin
        lieu = new Lieu(
                "10 rue de Paris",
                "testmedecin@example.com",
                "+33123456789",
                "Paris",
                75001
        );

        // Création d'un médecin valide
        medecin = new Medecin("Dupont", "Jean", "1234567890", lieu);
    }

    @Test
    void testGetMedecins() {
        // La liste statique doit être accessible
        assertNotNull(Medecin.getMedecins());
    }

    @Test
    void testGetNumeroAgreement() {
        assertEquals("12345", medecin.getNumeroAgreement());
    }

    @Test
    void testSetNumeroAgreement() throws SaisieException {
        medecin.setNumeroAgreement("67890");
        assertEquals("67890", medecin.getNumeroAgreement());
    }

    @Test
    void testSetNumeroAgreementInvalide() {
        Exception exception = assertThrows(SaisieException.class, () -> {
            medecin.setNumeroAgreement("ABC!"); // numéro invalide
        });
        assertTrue(exception.getMessage().contains("Error sur numéro agreement"));
    }

    @Test
    void testToString() {
        String str = medecin.toString();
        assertTrue(str.contains("Dupont"));
        assertTrue(str.contains("Jean"));
        assertTrue(str.contains("12345"));
        assertTrue(str.contains("Paris")); // contenu du lieu
    }
}