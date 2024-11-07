package myDL.DL;
import java.sql.Timestamp;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;


public class Dbmanager {

    // Database connection details (adjust as per your database configuration)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/DevoirLibre"; // Adjust the database name
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    // Connection object
    private Connection connection;

    // Constructor to establish the connection
    public Dbmanager() {
        try {
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Connection established successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to establish connection to the database.");
        }
    }

    // Method to close the connection
    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection closed successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Failed to close the connection.");
            }
        }
    }

    // Generate SQL for creating the Transaction table
    public String generateTransactionTableSQL() {
        return "CREATE TABLE IF NOT EXISTS Transaction (" +
               "id INT AUTO_INCREMENT PRIMARY KEY, " + // AUTO_INCREMENT for primary key
               "type VARCHAR(255) NOT NULL, " +
               "timestamp TIMESTAMP NOT NULL, " +
               "reference VARCHAR(255) UNIQUE NOT NULL " +  // Removed unnecessary comma
               ");";
    }

    // Generate SQL for creating the Client table
    public String generateClientTableSQL() {
        return "CREATE TABLE IF NOT EXISTS Client (" +
               "NumClient INT AUTO_INCREMENT PRIMARY KEY, " +
               "Nom VARCHAR(255) NOT NULL, " +       // NOT NULL ensures that the field is mandatory
               "prenom VARCHAR(255) NOT NULL, " +
               "adresse VARCHAR(255), " +
               "phone VARCHAR(15), " +
               "email VARCHAR(255) UNIQUE " +       // Unique constraint for email
               ");";
    }

    // Generate SQL for creating the Banque table
    public String generateBanqueTableSQL() {
        return "CREATE TABLE IF NOT EXISTS Banque (" +
               "id INT AUTO_INCREMENT PRIMARY KEY, " + // AUTO_INCREMENT for primary key
               "pays VARCHAR(255) NOT NULL" +         // Make sure "pays" is NOT NULL
               ");";
    }

    // Generate SQL for creating the Compte table
    public String generateCompteTableSQL() {
        return "CREATE TABLE IF NOT EXISTS Compte (" +
               "numcompte INT AUTO_INCREMENT PRIMARY KEY, " + // AUTO_INCREMENT for numcompte
               "dateCreation DATE, " +
               "dateUpdate DATE, " +
               "Devise VARCHAR(50), " +
               "numclient INT, " +  // Foreign key reference to Client
               "banque_id INT, " +  // Foreign key reference to Banque
               "FOREIGN KEY (numclient) REFERENCES Client(NumClient), " +
               "FOREIGN KEY (banque_id) REFERENCES Banque(id)" +
               ");";
    }

    // Generate SQL for creating the Compte_Transaction table (many-to-many relationship)
    public String generateCompte_TransactionTableSQL() {
        return "CREATE TABLE IF NOT EXISTS Compte_Transaction (" +
               "compte_numcompte INT, " +
               "transaction_id INT, " +
               "PRIMARY KEY (compte_numcompte, transaction_id), " +
               "FOREIGN KEY (compte_numcompte) REFERENCES Compte(numcompte), " +
               "FOREIGN KEY (transaction_id) REFERENCES Transaction(id)" +
               ");";
    }

    // Method to execute an SQL statement
    public void executeSQL(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("SQL executed successfully: " + sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Failed to execute SQL: " + sql);
        }
    }

    // Generate and execute all table creation statements
    public void createAllTables() {
        executeSQL(generateClientTableSQL());
        executeSQL(generateBanqueTableSQL());        
        executeSQL(generateTransactionTableSQL());
        executeSQL(generateCompteTableSQL());
        executeSQL(generateCompte_TransactionTableSQL());
    }
    
    
    
    public void addClientToDatabase(Client client) {
        String insertClientSQL = "INSERT INTO Client (Nom, prenom, adresse, phone, email) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(insertClientSQL, Statement.RETURN_GENERATED_KEYS)) {
            // Set the values for the client
            stmt.setString(1, client.Nom);
            stmt.setString(2, client.prenom);
            stmt.setString(3, client.adresse);
            stmt.setString(4, client.phone);
            stmt.setString(5, client.email);

            // Execute the insert
            int rowsAffected = stmt.executeUpdate();

            // Retrieve the generated client ID (NumClient) if insertion is successful
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedClientId = generatedKeys.getInt(1); // Get the generated NumClient
                        client.NumClient = generatedClientId; // Set the generated NumClient back in the client object
                        System.out.println("Client added successfully with ID: " + generatedClientId);
                    }
                }
            } else {
                System.err.println("Failed to add client to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error adding client to the database.");
        }
    }

    
    public Client searchClient(int numClient) {
        // SQL query to search for a client by NumClient
        String searchSQL = "SELECT * FROM Client WHERE NumClient = ?";
        Client client = null;

        try (PreparedStatement stmt = connection.prepareStatement(searchSQL)) {
            // Set the NumClient in the query
            stmt.setInt(1, numClient);

            // Execute the query
            try (ResultSet rs = stmt.executeQuery()) {
                // If a result is found, map it to a Client object
                if (rs.next()) {
                    client = new Client(
                        rs.getInt("NumClient"),
                        rs.getString("Nom"),
                        rs.getString("prenom"),
                        rs.getString("adresse"),
                        rs.getString("phone"),
                        rs.getString("email")
                    );
                    System.out.println("Client found: " + client);
                } else {
                    System.out.println("Client with NumClient " + numClient + " not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while searching for client.");
        }

        return client;  // Returns the client or null if not found
    }

    
    
    
    public Banque searchBanqueById(int id) {
        String searchSQL = "SELECT * FROM Banque WHERE id = ?";
        Banque banque = null;

        try (PreparedStatement stmt = connection.prepareStatement(searchSQL)) {
            stmt.setInt(1, id);  // Set the ID for the search query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    banque = new Banque(
                        rs.getInt("id"),
                        rs.getString("pays")
                    );
                    System.out.println("Banque found: " + banque);
                } else {
                    System.out.println("No banque found with id: " + id);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while searching for banque by ID.");
        }

        return banque;
    }

    
    
    public boolean addBanque(Banque banque) {
        String insertSQL = "INSERT INTO Banque (pays) VALUES (?)";  // Omit the id in the insert statement since it's auto-incremented

        try (PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, banque.pays);  // Set the pays of the banque

            // Execute the insert
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                // Retrieve the generated id for the banque
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int generatedBanqueId = generatedKeys.getInt(1);  // Get the generated id
                        banque.id = generatedBanqueId;  // Set the generated id back in the banque object
                        System.out.println("Banque added successfully with ID: " + generatedBanqueId);
                    }
                }
                return true;  // Successfully added
            } else {
                System.out.println("Failed to add banque.");
                return false;  // Failed to add
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while adding banque to the database.");
            return false;
        }
    }


    
    public boolean addCompte(Compte compte) {
        // SQL query without numcompte since it's auto-incremented in the database
        String insertSQL = "INSERT INTO Compte (dateCreation, dateUpdate, Devise, numclient, banque_id) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
            // Set the values for the other fields (not numcompte)
            stmt.setDate(1, compte.dateCreation);  // Set the dateCreation
            stmt.setDate(2, compte.dateUpdate);    // Set the dateUpdate
            stmt.setString(3, compte.Devise);     // Set the Devise
            stmt.setInt(4, compte.myclient.NumClient);  // Set the numclient (Foreign Key)
            stmt.setInt(5, compte.mybanque.id);    // Set the banque_id (Foreign Key)

            // Execute the update
            int rowsAffected = stmt.executeUpdate();

            // Check if the insertion was successful
            if (rowsAffected > 0) {
                // Retrieve the generated keys (the generated numcompte)
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        // Get the generated numcompte value
                        int generatedNumcompte = generatedKeys.getInt(1);  // The first column is the generated ID
                        compte.numcompte = generatedNumcompte;  // Set the generated numcompte back to the Compte object
                        System.out.println("Compte added successfully with numcompte: " + generatedNumcompte);
                        return true;  // Successfully added
                    }
                }
            } else {
                System.out.println("Failed to add compte.");
                return false;  // Failed to add
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while adding compte to the database.");
            return false;
        }
		return false;
    }

    
    
    
    public Compte searchCompteByNumcompte(int numcompte) {
        String searchSQL = "SELECT * FROM Compte WHERE numcompte = ?";
        Compte compte = null;

        try (PreparedStatement stmt = connection.prepareStatement(searchSQL)) {
            stmt.setInt(1, numcompte);  // Set the numcompte (int) for the search query

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Create a Compte object with the fetched data
                    compte = new Compte(
                        rs.getInt("numcompte"), // Assuming numcompte is VARCHAR, if not, use rs.getInt()
                        rs.getDate("dateCreation"),
                        rs.getDate("dateUpdate"),
                        rs.getString("Devise")
                    );
                    System.out.println("Compte found: " + compte);
                } else {
                    System.out.println("No compte found with numcompte: " + numcompte);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while searching for compte by numcompte.");
        }

        return compte;
    }


    public void addTransaction(Transaction transaction) {
        String insertTransactionSQL = "INSERT INTO Transaction (type, timestamp, reference) VALUES (?, ?, ?)";
        String insertCompteTransactionSQL = "INSERT INTO Compte_Transaction (compte_numcompte, transaction_id) VALUES (?, ?)";

        try (PreparedStatement transactionStmt = connection.prepareStatement(insertTransactionSQL, Statement.RETURN_GENERATED_KEYS)) {

            // Automatically determine the transaction type
            transaction.determineTransactionType();

            // Insert into Transaction table
            transactionStmt.setString(1, transaction.getType().name());  // Set the type as a String
            transactionStmt.setTimestamp(2, transaction.getTimestamp());  // Convert LocalDateTime to Timestamp
            transactionStmt.setString(3, transaction.getReference()); // Set the reference

            int rowsAffected = transactionStmt.executeUpdate();
            if (rowsAffected > 0) {
                // Retrieve the generated transaction ID
                try (ResultSet generatedKeys = transactionStmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int transactionId = generatedKeys.getInt(1);

                        // Insert the source account (compterc) into Compte_Transaction table
                        try (PreparedStatement compteTransactionStmt = connection.prepareStatement(insertCompteTransactionSQL)) {
                            // Insert source account
                            compteTransactionStmt.setInt(1, transaction.getCompterc().numcompte); // Source account number
                            compteTransactionStmt.setInt(2, transactionId);  // Transaction ID
                            compteTransactionStmt.executeUpdate();  // Execute insert for source account
                        }

                        // Insert the destination accounts (comptedest) into Compte_Transaction table
                        try (PreparedStatement compteTransactionStmt = connection.prepareStatement(insertCompteTransactionSQL)) {
                            for (Compte compte : transaction.getComptedest()) {
                                compteTransactionStmt.setInt(1, compte.numcompte); // Destination account number
                                compteTransactionStmt.setInt(2, transactionId);  // Transaction ID
                                compteTransactionStmt.addBatch();  // Add to batch for efficiency
                            }
                            compteTransactionStmt.executeBatch();  // Execute the batch insert for all destination accounts
                        }

                        System.out.println("Transaction and associated comptes added successfully.");
                    }
                }
            } else {
                System.out.println("Failed to add transaction.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error while adding the transaction.");
        }
    }

   

    
   


    
    

    // Main method for testing
    public static void main(String[] args) {
    	Dbmanager dbManager = new Dbmanager();
    	Client client = new Client(0,"clientest","testeur1","123 Iziki, Marrakech","06688745","clienttest1@gmail.com");
    	Client client2 = new Client(0,"clienttest2","testeur2","123 Sokoma, Marrakech","0668874","clienttest2@gmail.com");
    	//Client client3 = new Client(0,"mounir","testeur3","123 massira, Marrakech","0688741","mounir@gmail.com");

    	dbManager.addClientToDatabase(client);
    	dbManager.addClientToDatabase(client2);
    	//dbManager.addClientToDatabase(client3);
    	
    	Banque banque = new Banque(0,"Maroc");
    	//Banque banque2 = new Banque(0,"Maroc");
    	//Banque banque3 = new Banque(0,"Nigeria");
    	
    	dbManager.addBanque(banque);
    	//dbManager.addBanque(banque2);
    	//dbManager.addBanque(banque3);

    	Compte compteSrc = new Compte(0, Date.valueOf("2024-01-01"), Date.valueOf("2024-01-01"), "EUR");
    	compteSrc.myclient=client;
    	compteSrc.mybanque=banque;
        
    	
    	Compte compteDest1 = new Compte(0, Date.valueOf("2024-01-01"), Date.valueOf("2024-01-01"), "USD");
    	compteDest1.myclient=client2;
    	compteDest1.mybanque=banque;
    	
    	//Compte compteDest2 = new Compte(0, Date.valueOf("2024-01-01"), Date.valueOf("2024-01-01"), "USD");
    	//compteDest2.myclient=client3;
    	//compteDest2.mybanque=banque3;
    	
    	
    	 dbManager.addCompte(compteSrc);
    	 dbManager.addCompte(compteDest1);
    	 //dbManager.addCompte(compteDest2);
    	 
    	 
    	  Transaction transaction = new Transaction(
    		        Timestamp.from(Instant.now()),  // Timestamp now
    		        compteSrc,  // Source account
    		        Arrays.asList(compteDest1)  // List of destination accounts
    		    );
    	 
    	 dbManager.addTransaction(transaction);
    	 dbManager.closeConnection();
    	 
       
    }
}
