package myDL.DL;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

public class TransactionTest {

    private Transaction transaction;
    private Compte sourceCompte;
    private Compte destCompte;

    @BeforeEach
    public void setUp() {
        // Prepare test data
        sourceCompte = new Compte(1, Date.valueOf("2024-01-01 10:00:00"), Date.valueOf("2024-01-01 10:00:00"), "USD");
        destCompte = new Compte(2, Date.valueOf("2024-01-01 10:00:00"), Date.valueOf("2024-01-01 10:00:00"), "USD");

        // Create a Transaction with one source and one destination account
        transaction = new Transaction(Timestamp.valueOf("2024-01-01 10:00:00"), sourceCompte, Arrays.asList(destCompte));
    }

    @Test
    public void testTransactionConstructor() {
        assertNotNull(transaction.getTimestamp());
        assertNotNull(transaction.getReference());
        assertEquals("REF-", transaction.getReference().substring(0, 4));
        assertEquals(1, transaction.getSourceCompteId());
        assertEquals(1, transaction.getDestinationCompteIds().size());
        assertEquals(2, (int) transaction.getDestinationCompteIds().get(0));
    }

    @Test
    public void testTransactionTypeVirchac() {
        // Assuming that sourceCompte and destCompte belong to different countries or banks
        // Set different countries or different banks to force the Virchac type
        destCompte.mybanque.pays = "France";
        sourceCompte.mybanque.pays = "USA";  // Different countries, should trigger Virchac type

        transaction.determineTransactionType();
        assertEquals(Type.Virchac, transaction.getType());
    }

    @Test
    public void testTransactionTypeVirin() {
        // Assuming source and dest are in the same bank
        sourceCompte.mybanque.pays = "USA";
        destCompte.mybanque.pays = "USA";  // Same country, same bank, should trigger Virin type

        transaction.determineTransactionType();
        assertEquals(Type.Virin, transaction.getType());
    }

    @Test
    public void testTransactionTypeVirest() {
        // Same country, but different banks
        sourceCompte.mybanque.pays = "USA";
        destCompte.mybanque.pays = "USA";  // Same country, different banks, should trigger Virest type

        transaction.determineTransactionType();
        assertEquals(Type.Virest, transaction.getType());
    }

    @Test
    public void testTransactionTypeVirMulta() {
        // Multiple destination accounts
        Compte anotherDestCompte = new Compte(3, Date.valueOf("2024-01-01 10:00:00"), Date.valueOf("2024-01-01 10:00:00"), "USD");
        transaction = new Transaction(Timestamp.valueOf("2024-01-01 10:00:00"), sourceCompte, Arrays.asList(destCompte, anotherDestCompte));

        transaction.determineTransactionType();
        assertEquals(Type.VirMulta, transaction.getType());
    }

    @Test
    public void testGetComptes() {
        List<Compte> comptes = transaction.getComptes();
        assertNotNull(comptes);
        assertEquals(2, comptes.size());
        assertTrue(comptes.contains(sourceCompte));
        assertTrue(comptes.contains(destCompte));
    }

    // Test JSON Serialization/Deserialization
    @Test
    public void testJsonSerialization() throws Exception {
        // Create an ObjectMapper instance for JSON conversion
        ObjectMapper objectMapper = new ObjectMapper();

        // Serialize the transaction to JSON
        String json = objectMapper.writeValueAsString(transaction);
        assertNotNull(json);
        assertTrue(json.contains("REF-"));

        // Deserialize the JSON back to a Transaction object
        Transaction deserializedTransaction = objectMapper.readValue(json, Transaction.class);
        assertNotNull(deserializedTransaction);
        assertEquals(transaction.getReference(), deserializedTransaction.getReference());
        assertEquals(transaction.getTimestamp(), deserializedTransaction.getTimestamp());
        assertEquals(transaction.getSourceCompteId(), deserializedTransaction.getSourceCompteId());
    }

    @Test
    public void testJsonSerializationWithMultipleDestinations() throws Exception {
        // Add more destinations
        Compte anotherDestCompte = new Compte(3, Date.valueOf("2024-01-01 10:00:00"), Date.valueOf("2024-01-01 10:00:00"), "USD");
        transaction = new Transaction(Timestamp.valueOf("2024-01-01 10:00:00"), sourceCompte, Arrays.asList(destCompte, anotherDestCompte));

        // Create an ObjectMapper instance for JSON conversion
        ObjectMapper objectMapper = new ObjectMapper();

        // Serialize the transaction to JSON
        String json = objectMapper.writeValueAsString(transaction);
        assertNotNull(json);

        // Deserialize the JSON back to a Transaction object
        Transaction deserializedTransaction = objectMapper.readValue(json, Transaction.class);
        assertNotNull(deserializedTransaction);
        assertEquals(transaction.getReference(), deserializedTransaction.getReference());
        assertEquals(transaction.getTimestamp(), deserializedTransaction.getTimestamp());
        assertEquals(transaction.getSourceCompteId(), deserializedTransaction.getSourceCompteId());
        assertEquals(transaction.getDestinationCompteIds(), deserializedTransaction.getDestinationCompteIds());
    }
    
    @Test
    public void testToJson() {
        // Set up sample data
        Compte sourceCompte = new Compte(12345, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), "USD");
        Compte destinationCompte1 = new Compte(54321, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), "EUR");
        Compte destinationCompte2 = new Compte(67890, new java.sql.Date(System.currentTimeMillis()), new java.sql.Date(System.currentTimeMillis()), "GBP");

        Transaction transaction = new Transaction(new Timestamp(System.currentTimeMillis()), sourceCompte, Arrays.asList(destinationCompte1, destinationCompte2));

        // Convert to JSON
        String jsonOutput = transaction.toJson();

        // Assert that JSON output is not null or empty
        assertNotNull(jsonOutput);
        assertFalse(jsonOutput.isEmpty());

        // Print JSON for visual confirmation (optional)
        System.out.println("Serialized Transaction to JSON: " + jsonOutput);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
