package myDL.DL;

import org.junit.Before;
import org.junit.Test;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;

import static org.junit.Assert.*;

public class CompteTest {

    private Compte compte;
    private Client client;
    private Banque banque;

    @Before
    public void setUp() {
        // Create mock objects for Client and Banque
        client = new Client(1, "John", "Doe", "123 Main St", "1234567890", "johndoe@example.com");
        banque = new Banque(1, "Banque de Paris");

        // Create a Compte object
        compte = new Compte(123, Date.valueOf("2024-01-01"), Date.valueOf("2024-01-01"), "USD");
        compte.myclient = client;
        compte.mybanque = banque;
    }

    @Test
    public void testCompteConstructor() {
        assertNotNull(compte);
        assertEquals(123, compte.numcompte);
        assertEquals("USD", compte.Devise);
        assertEquals(Date.valueOf("2024-01-01"), compte.dateCreation);
        assertEquals(Date.valueOf("2024-01-01"), compte.dateUpdate);
    }

    @Test
    public void testToStringWithNonNullValues() {
        // Setting up some transactions for testing
        Transaction transaction = new Transaction(Timestamp.valueOf("2024-01-01"), compte, Arrays.asList(compte));
        compte.transactions = Arrays.asList(transaction);

        // Verify that the toString method works with non-null fields
        String expectedToString = "Compte{" +
                "numcompte='123'" +
                ", dateCreation=2024-01-01" +
                ", dateUpdate=2024-01-01" +
                ", Devise='USD'" +
                ", myclient=Client{id=1, name=John Doe}" +
                ", transactions=[Transaction{source=123, dest=123}]" +
                ", mybanque=Banque{id=1, name=Banque de Paris}" +
                '}';

        assertEquals(expectedToString, compte.toString());
    }

    @Test
    public void testToStringWithNullValues() {
        // Set client, transactions, and banque to null to test the fallback string
        compte.myclient = null;
        compte.transactions = null;
        compte.mybanque = null;

        String expectedToString = "Compte{" +
                "numcompte='123'" +
                ", dateCreation=2024-01-01" +
                ", dateUpdate=2024-01-01" +
                ", Devise='USD'" +
                ", myclient=No client assigned" +
                ", transactions=No transactions" +
                ", mybanque=No bank assigned" +
                '}';

        assertEquals(expectedToString, compte.toString());
    }

    @Test
    public void testSetClient() {
        // Set a client for the compte
        compte.myclient = client;

        assertEquals(client, compte.myclient);
    }

    @Test
    public void testSetTransactions() {
        // Set transactions for the compte
        Transaction transaction = new Transaction(Timestamp.valueOf("2024-01-01"), compte, Arrays.asList(compte));
        compte.transactions = Arrays.asList(transaction);

        assertEquals(1, compte.transactions.size());
        assertEquals(transaction, compte.transactions.get(0));
    }

    @Test
    public void testToJson() {
        // Arrange
        Compte compte = new Compte(123, Date.valueOf("2024-01-01"), Date.valueOf("2024-11-07"), "USD");
        
        // Act
        String jsonResult = compte.toJson();

        // Assert
        assertNotNull("The JSON string should not be null", jsonResult);
        assertTrue("The JSON string should contain numcompte", jsonResult.contains("\"numcompte\":123"));
        assertTrue("The JSON string should contain dateCreation", jsonResult.contains("\"dateCreation\":\"2024-01-01\""));
        assertTrue("The JSON string should contain Devise", jsonResult.contains("\"Devise\":\"USD\""));
    }

    @Test
    public void testFromJson() {
        // Arrange
        String jsonInput = "{ \"numcompte\": 123, \"dateCreation\": \"2024-01-01\", \"dateUpdate\": \"2024-11-07\", \"Devise\": \"USD\" }";

        // Act
        Compte resultCompte = Compte.fromJson(jsonInput);

        // Assert
        assertNotNull("The Compte object should not be null", resultCompte);
        assertEquals("The numcompte should be 123", 123, resultCompte.numcompte);
        assertEquals("The dateCreation should be 2024-01-01", Date.valueOf("2024-01-01"), resultCompte.dateCreation);
        assertEquals("The Devise should be USD", "USD", resultCompte.Devise);
    }
    
    
    
    @Test
    public void testSetBanque() {
        // Set a banque for the compte
        compte.mybanque = banque;

        assertEquals(banque, compte.mybanque);
    }
}
