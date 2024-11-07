package myDL.DL;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.sql.*;
import java.util.Arrays;

public class DbmanagerTest {

    private Dbmanager dbManager;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private ResultSet mockResultSet;

    @Before
    public void setUp() throws SQLException {
        // Initialize mock objects
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        // Initialize the Dbmanager instance with a mock connection
        dbManager = new Dbmanager();
        // Mock PreparedStatement behavior
        when(mockConnection.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS)))
                .thenReturn(mockPreparedStatement);
    }

    @Test
    public void testCreateClientTable() {
        String expectedSQL = "CREATE TABLE IF NOT EXISTS Client (" +
                             "NumClient INT AUTO_INCREMENT PRIMARY KEY, " +
                             "Nom VARCHAR(255) NOT NULL, " +
                             "prenom VARCHAR(255) NOT NULL, " +
                             "adresse VARCHAR(255), " +
                             "phone VARCHAR(15), " +
                             "email VARCHAR(255) UNIQUE " +
                             ");";
        assertEquals(expectedSQL, dbManager.generateClientTableSQL());
    }

    @Test
    public void testAddClientToDatabase() throws SQLException {
        // Prepare test client
        Client client = new Client(0, "rawya", "hamdi", "123 Iziki, Marrakech", "06688745", "rawyah@gmail.com");

        // Mock result set for generated keys
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1); // Mock generated NumClient to be 1

        // Execute addClientToDatabase method
        dbManager.addClientToDatabase(client);

        // Assert the client ID has been set correctly
        assertEquals(1, client.NumClient);
    }

    @Test
    public void testAddBanque() throws SQLException {
        // Prepare test banque
        Banque banque = new Banque(0, "Maroc");

        // Mock result set for generated keys
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1); // Mock generated ID to be 1

        // Execute addBanque method
        boolean result = dbManager.addBanque(banque);

        // Assert the return value and Banque ID
        assertTrue(result);
        assertEquals(1, banque.id);
    }

    @Test
    public void testAddCompte() throws SQLException {
        // Prepare test compte
        Client client = new Client(1, "rawya", "hamdi", "123 Iziki, Marrakech", "06688745", "rawyah@gmail.com");
        Banque banque = new Banque(1, "Maroc");
        Compte compte = new Compte(0, Date.valueOf("2024-01-01"), Date.valueOf("2024-01-01"), "EUR");
        compte.myclient=client;
        compte.mybanque=banque;

        // Mock result set for generated keys
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);
        when(mockPreparedStatement.getGeneratedKeys()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt(1)).thenReturn(1); // Mock generated numcompte to be 1

        // Execute addCompte method
        boolean result = dbManager.addCompte(compte);

        // Assert the return value and Compte numcompte
        assertTrue(result);
        assertEquals(1, compte.numcompte);
    }

    @Test
    public void testSearchClient() throws SQLException {
        // Prepare test client
        Client client = new Client(1, "rawya", "hamdi", "123 Iziki, Marrakech", "06688745", "rawyah@gmail.com");

        // Mock the result set for the query
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true);
        when(mockResultSet.getInt("NumClient")).thenReturn(1);
        when(mockResultSet.getString("Nom")).thenReturn("rawya");
        when(mockResultSet.getString("prenom")).thenReturn("hamdi");
        when(mockResultSet.getString("adresse")).thenReturn("123 Iziki, Marrakech");
        when(mockResultSet.getString("phone")).thenReturn("06688745");
        when(mockResultSet.getString("email")).thenReturn("rawyah@gmail.com");

        // Execute searchClient method
        Client foundClient = dbManager.searchClient(1);

        // Assert the client data
        assertNotNull(foundClient);
        assertEquals("rawya", foundClient.Nom);
        assertEquals("hamdi", foundClient.prenom);
    }

    @Test
    public void testAddTransaction() throws SQLException {
        // Prepare test accounts
        Client clientSrc = new Client(1, "rawya", "hamdi", "123 Iziki, Marrakech", "06688745", "rawyah@gmail.com");
        Banque banqueSrc = new Banque(1, "Maroc");
        Compte compteSrc = new Compte(1, Date.valueOf("2024-01-01"), Date.valueOf("2024-01-01"), "EUR");
        compteSrc.myclient=clientSrc;
        compteSrc.mybanque=banqueSrc;

        Client clientDest = new Client(2, "sanae", "himani", "123 Sokoma, Marrakech", "0668874", "sanaef@gmail.com");
        Banque banqueDest = new Banque(2, "Maroc");
        Compte compteDest = new Compte(2, Date.valueOf("2024-01-01"), Date.valueOf("2024-01-01"), "USD");
        compteDest.myclient=clientDest;
        compteDest.mybanque=banqueDest;

        // Prepare a transaction
        Transaction transaction = new Transaction(
            Timestamp.valueOf("2024-01-01 10:00:00"),
            compteSrc,
            Arrays.asList(compteDest)
        );

        // Mock PreparedStatement behavior
        when(mockPreparedStatement.executeUpdate()).thenReturn(1);

        // Execute addTransaction method
        dbManager.addTransaction(transaction);

        // Assert that the transaction has been processed
        // Transaction doesn't have ID, but you can assert other checks, such as success
        assertTrue(transaction.getCompterc() != null);
        assertTrue(transaction.getComptedest().size() > 0);
    }

    @Test
    public void testCreateAllTables() throws SQLException {
        // Execute createAllTables method
        dbManager.createAllTables();

        // Assert that the executeSQL method was called for each table creation
        verify(mockConnection, times(5)).prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS));
    }

    @Test
    public void testCloseConnection() throws SQLException {
        // Close the connection
        dbManager.closeConnection();

        // Verify that the close() method was called on the connection
        verify(mockConnection, times(1)).close();
    }
}
