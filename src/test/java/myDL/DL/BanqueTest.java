package myDL.DL;

import org.junit.Before;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class BanqueTest {

    private Banque banque;

    @Before
    public void setUp() {
        // Create a new Banque object before each test
        banque = new Banque(1, "Morocco");
    }

    @Test
    public void testBanqueConstructor() {
        // Test the banque constructor to ensure correct initialization
        assertNotNull(banque);
        assertEquals(1, banque.id);
        assertEquals("Morocco", banque.pays);
    }

    @Test
    public void testToStringWithNonNullComptes() {
        // Set comptes to a non-null value for the test
        Compte compte = new Compte(123, java.sql.Date.valueOf("2024-01-01"), java.sql.Date.valueOf("2024-01-01"), "USD");
        banque.comptes = Arrays.asList(compte);

        String expectedToString = "Banque{" +
                "id=1" +
                ", pays='Morocco'" +
                ", comptes=[Compte{numcompte='123', dateCreation=2024-01-01, dateUpdate=2024-01-01, Devise='USD', myclient=No client assigned, transactions=No transactions, mybanque=No bank assigned}]" +
                '}';

        assertEquals(expectedToString, banque.toString());
    }

    @Test
    public void testToStringWithNullComptes() {
        // Set comptes to null for the test
        banque.comptes = null;

        String expectedToString = "Banque{" +
                "id=1" +
                ", pays='Morocco'" +
                ", comptes=No accounts" +
                '}';

        assertEquals(expectedToString, banque.toString());
    }

    @Test
    public void testToJson() {
        // Create a Banque object
        Banque banque = new Banque(1, "Morocco");
        Compte compte1 = new Compte(101, null, null, "MAD");
        Compte compte2 = new Compte(102, null, null, "USD");
        banque.comptes = Arrays.asList(compte1, compte2);

        // Convert to JSON
        String jsonResult = banque.toJson();

        // Verify the result
        assertNotNull("JSON string should not be null", jsonResult);
        assertTrue("JSON string should contain banque ID", jsonResult.contains("\"id\":1"));
        assertTrue("JSON string should contain country", jsonResult.contains("\"pays\":\"Morocco\""));
        assertTrue("JSON string should contain compte list", jsonResult.contains("\"comptes\""));
    }

    @Test
    public void testFromJson() {
        // JSON string representing a Banque object
        String jsonString = "{ \"id\": 2, \"pays\": \"France\", \"comptes\": [{\"numcompte\": 201, \"Devise\": \"EUR\"}, {\"numcompte\": 202, \"Devise\": \"GBP\"}] }";

        // Convert from JSON to Banque object
        Banque banque = Banque.fromJson(jsonString);

        // Verify the result
        assertNotNull("Banque object should not be null", banque);
        assertEquals("Banque ID should be 2", 2, banque.id);
        assertEquals("Banque pays should be 'France'", "France", banque.pays);
        assertNotNull("Comptes list should not be null", banque.comptes);
        assertEquals("There should be 2 comptes", 2, banque.comptes.size());
        assertEquals("First compte numcompte should be 201", 201, banque.comptes.get(0).numcompte);
        assertEquals("Second compte Devise should be 'GBP'", "GBP", banque.comptes.get(1).Devise);
    }
    
    
    
    
    
    
    
    
    
    
    @Test
    public void testSetComptes() {
        // Set a comptes list for the banque
        Compte compte = new Compte(123, java.sql.Date.valueOf("2024-01-01"), java.sql.Date.valueOf("2024-01-01"), "USD");
        banque.comptes = Arrays.asList(compte);

        assertNotNull(banque.comptes);
        assertEquals(1, banque.comptes.size());
        assertEquals(compte, banque.comptes.get(0));
    }
}
