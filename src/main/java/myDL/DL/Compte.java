package myDL.DL;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Date;
import java.util.List;

public class Compte {
	public int numcompte;
	public Date dateCreation;
	public Date dateUpdate;
	public String Devise;
	public Client myclient;
	public List<Transaction> transactions;
	public Banque mybanque;
	
	
	public Compte(int numcompte,Date dateCreation,Date dateUpdate , String Devise ) {
		this.numcompte=numcompte;
		this.dateCreation=dateCreation;
		this.dateUpdate=dateUpdate;
		this.Devise=Devise;
		
	}
	
	@Override
	public String toString() {
	    return "Compte{" +
	           "numcompte='" + numcompte + '\'' +
	           ", dateCreation=" + dateCreation +
	           ", dateUpdate=" + dateUpdate +
	           ", Devise='" + Devise + '\'' +
	           ", myclient=" + (myclient != null ? myclient : "No client assigned") +
	           ", transactions=" + (transactions != null ? transactions : "No transactions") +
	           ", mybanque=" + (mybanque != null ? mybanque : "No bank assigned") +
	           '}';
	}

	
	public String toJson() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Converts a JSON string to a Compte object
    public static Compte fromJson(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonString, Compte.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
