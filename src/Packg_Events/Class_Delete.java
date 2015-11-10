package Packg_Events;

import java.util.Scanner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Class_Delete {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Client cl1 = ClientBuilder.newClient();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Client ID");
		String client_id = scan.next();
		System.out.println("Enter Object ID");
		String object_id = scan.next();
		String str = client_id+ ","+object_id;
	    WebTarget tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/delete");
		Response response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		System.out.println("Entry deleted");

	}

}
