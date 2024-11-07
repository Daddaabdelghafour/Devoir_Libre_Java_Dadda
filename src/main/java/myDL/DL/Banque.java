package myDL.DL;

import java.util.List;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Banque {
public int id;
public String pays;
public List<Compte> comptes;



public Banque() {
	
}

public Banque(int id,String pays) {
	this.id=id;
	this.pays=pays;
}
	
@Override
public String toString() {
    return "Banque{" +
           "id=" + id +
           ", pays='" + pays + '\'' +
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

// Static method to create a Banque object from a JSON string
public static Banque fromJson(String json) {
    ObjectMapper objectMapper = new ObjectMapper();
    try {
        return objectMapper.readValue(json, Banque.class);
    } catch (JsonProcessingException e) {
        e.printStackTrace();
        return null;
    }
}



}
