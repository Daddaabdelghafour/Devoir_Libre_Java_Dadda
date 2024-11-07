package myDL.DL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

public class Client {
	public int NumClient;
	public String Nom;
	public String prenom;
	public String adresse;
	public String phone;
	public String email;
	public List<Compte> comptes;
	
	
	public Client(int num,String nom,String prenom,String add , String ph,String em) {
		this.NumClient=num;
		this.Nom=nom;
		this.prenom=prenom;
		this.adresse=add;
		this.phone=ph;
		this.email=em;
	}
		
	@Override
	public String toString() {
	    return "Client{" +
	           "NumClient=" + NumClient +
	           ", Nom='" + Nom + '\'' +
	           ", prenom='" + prenom + '\'' +
	           ", adresse='" + adresse + '\'' +
	           ", phone='" + phone + '\'' +
	           ", email='" + email + '\'' +
	           ", comptes=" + (comptes != null ? comptes : "No accounts") +
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
	
	
	
	 public static Client fromJson(String json) {
	        ObjectMapper objectMapper = new ObjectMapper();
	        try {
	            return objectMapper.readValue(json, Client.class);
	        } catch (JsonProcessingException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	
	
	
	
}
