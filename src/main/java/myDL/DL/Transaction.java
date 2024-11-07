package myDL.DL;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public final class Transaction {

    private Type type;  // The type of the transaction
    private final Timestamp timestamp; // The timestamp of the transaction
    private final String reference; // The unique reference of the transaction
    private final Compte compterc; // The source account
    private final List<Compte> comptedest; // The list of destination accounts

    public Transaction(Timestamp time, Compte compterc, List<Compte> comptedest) {
        this.timestamp = time;
        this.reference = "REF-" + UUID.randomUUID().toString();
        this.compterc = compterc;
        this.comptedest = comptedest == null ? Collections.emptyList() : Collections.unmodifiableList(comptedest);
        determineTransactionType();
    }

    public Transaction() {
		this.timestamp = null;
		this.reference = "";
		this.compterc = null;
		this.comptedest = null;
    	
    }
    
    
    // Public getters to provide access
    public Type getType() {
        return type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getReference() {
        return reference;
    }

    public Compte getCompterc() {
        return compterc;
    }

    public List<Compte> getComptedest() {
        return comptedest;
    }

    // Method to get all involved comptes (source and destination)
    public List<Compte> getComptes() {
        List<Compte> allComptes = new ArrayList<>();
        allComptes.add(compterc);
        allComptes.addAll(comptedest);
        return Collections.unmodifiableList(allComptes); // Return an unmodifiable list for immutability
    }

    // Method to get the source account ID
    public int getSourceCompteId() {
        return compterc.numcompte; // Assuming Compte has a method `getId()` that returns the account ID
    }

    // Method to get the destination account IDs
    public List<Integer> getDestinationCompteIds() {
        List<Integer> destinationIds = new ArrayList<>();
        for (Compte compte : comptedest) {
            destinationIds.add(compte.numcompte); // Assuming Compte has a method `getId()` that returns the account ID
        }
        return destinationIds;
    }

    @Override
    public String toString() {
        return "Transaction{" +
               "Type='" + type + '\'' +
               ", Timestamp=" + timestamp +
               ", Reference='" + reference + '\'' +
               ", Source Compte=" + compterc +
               ", Destination Comptes=" + comptedest +
               '}';
    }

    // Determine the transaction type based on source and destination accounts
    public void determineTransactionType() {
        if (comptedest.size() == 1) {
            // If there is only one destination account
            Compte compteDest = comptedest.get(0);

            // Check if it's the same country
            if (!compterc.mybanque.pays.equals(compteDest.mybanque.pays)) {
                // Different countries and different banks
                this.type = Type.Virchac;
            } else {
                // Same country, but check if same bank
                if (compterc.mybanque.equals(compteDest.mybanque)) {
                    // Same bank and same country
                    this.type = Type.Virin;
                } else {
                    // Different banks but same country
                    this.type = Type.Virest;
                }
            }
        } else {
            // Multiple destination accounts
            this.type = Type.VirMulta;
        }
    }
    
    
    public String toJson() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Static method to create a Transaction object from a JSON string
    public static Transaction fromJson(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, Transaction.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
    
    
    
    
    
    
    
    
}
