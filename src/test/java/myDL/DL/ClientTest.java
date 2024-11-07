package myDL.DL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;

public class ClientTest {

    private Client client;

    @Before
    public void setUp() {
        // Create a new Client object before each test
        client = new Client(1, "John", "Doe", "123 Main St", "1234567890", "johndoe@example.com");
    }

    @Test
    public void testClientConstructor() {
        // Test the client constructor to ensure correct initialization
        assertNotNull(client);
        assertEquals(1, client.NumClient);
        assertEquals("John", client.Nom);
        assertEquals("Doe", client.prenom);
        assertEquals("123 Main St", client.adresse);
        assertEquals("1234567890", client.phone);
        assertEquals("johndoe@example.com", client.email);
    }

    @Test
    public void testToStringWithNonNullComptes() {
        // Set comptes to a non-null value for the test
        Compte compte = new Compte(123, java.sql.Date.valueOf("2024-01-01"), java.sql.Date.valueOf("2024-01-01"), "USD");
        client.comptes = Arrays.asList(compte);

        String expectedToString = "Client{" +
                "NumClient=1" +
                ", Nom='John'" +
                ", prenom='Doe'" +
                ", adresse='123 Main St'" +
                ", phone='1234567890'" +
                ", email='johndoe@example.com'" +
                ", comptes=[Compte{numcompte='123', dateCreation=2024-01-01, dateUpdate=2024-01-01, Devise='USD', myclient=No client assigned, transactions=No transactions, mybanque=No bank assigned}]" +
                '}';

        assertEquals(expectedToString, client.toString());
    }

    @Test
    public void testToStringWithNullComptes() {
        // Set comptes to null for the test
        client.comptes = null;

        String expectedToString = "Client{" +
                "NumClient=1" +
                ", Nom='John'" +
                ", prenom='Doe'" +
                ", adresse='123 Main St'" +
                ", phone='1234567890'" +
                ", email='johndoe@example.com'" +
                ", comptes=No accounts" +
                '}';

        assertEquals(expectedToString, client.toString());
    }

    @Test
    public void testToJson() {
        // Create a sample Client object
        Client client = new Client(1, "John", "Doe", "123 Main St", "1234567890", "johndoe@example.com");

        // Convert the Client object to a JSON string
        String json = client.toJson();

        // Assert that the JSON string is not null or empty
        assertNotNull("JSON string should not be null", json);
        assertFalse("JSON string should not be empty", json.isEmpty());

        // Assert that the JSON string contains expected fields
        assertTrue("JSON string should contain the NumClient field", json.contains("\"NumClient\":1"));
        assertTrue("JSON string should contain the Nom field", json.contains("\"Nom\":\"John\""));
        assertTrue("JSON string should contain the prenom field", json.contains("\"prenom\":\"Doe\""));
        assertTrue("JSON string should contain the adresse field", json.contains("\"adresse\":\"123 Main St\""));
        assertTrue("JSON string should contain the phone field", json.contains("\"phone\":\"1234567890\""));
        assertTrue("JSON string should contain the email field", json.contains("\"email\":\"johndoe@example.com\""));
    }

    @Test
    public void testFromJson() {
        // Create a JSON string representing a Client object
        String json = "{\"NumClient\":1,\"Nom\":\"John\",\"prenom\":\"Doe\",\"adresse\":\"123 Main St\",\"phone\":\"1234567890\",\"email\":\"johndoe@example.com\"}";

        // Convert the JSON string to a Client object
        Client client = Client.fromJson(json);

        // Assert that the Client object is correctly created from the JSON string
        assertNotNull("Client object should not be null", client);
        assertEquals("NumClient should be 1", 1, client.NumClient);
        assertEquals("Nom should be 'John'", "John", client.Nom);
        assertEquals("prenom should be 'Doe'", "Doe", client.prenom);
        assertEquals("adresse should be '123 Main St'", "123 Main St", client.adresse);
        assertEquals("phone should be '1234567890'", "1234567890", client.phone);
        assertEquals("email should be 'johndoe@example.com'", "johndoe@example.com", client.email);
    }
    
    
    
    
    
    @Test
    public void testSetComptes() {
        // Set a comptes list for the client
        Compte compte = new Compte(123, java.sql.Date.valueOf("2024-01-01"), java.sql.Date.valueOf("2024-01-01"), "USD");
        client.comptes = Arrays.asList(compte);

        assertEquals(1, client.comptes.size());
        assertEquals(compte, client.comptes.get(0));
    }
}
