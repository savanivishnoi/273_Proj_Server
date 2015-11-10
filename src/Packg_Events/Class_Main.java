package Packg_Events;


import java.io.IOException;
import java.util.Scanner;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class Class_Main {

	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, InterruptedException {
		// TODO Auto-generated method stub
		//urn:esn:CLIENTID3
	//	Class_Create obj = new Class_Create();
	//	obj.create_Call("urn:esn:CLIENTID3", "100", "1000" );
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter Client ID");
		String client_id = scan.next();
		System.out.println("Enter Object ID");
		String object_id = scan.next();
		System.out.println("Enter Value");
		String value = scan.next();
		
		
		Client cl1 = ClientBuilder.newClient();
		
		String str = client_id+","+object_id+","+value;//"/urn:esn:CLIENTID3 "+ ",11004" + ",100";
		WebTarget tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/create");
		Response response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		System.out.println("Created");
		//Thread.sleep(5000);
		
	/*	str = "/urn:esn:CLIENTID3 "+ ",11005" + ",100";
		tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/create");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		
		Thread.sleep(5000);
		
		str = "/urn:esn:CLIENTID3 "+ ",11006" + ",100";
		tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/create");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		
		Thread.sleep(5000);*/
		System.out.println("Enter New Value");
		String new_value = scan.next();
				
		str = client_id+","+object_id+","+new_value;
		tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/update");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		System.out.println("Updated");
		Thread.sleep(2000);
		
		System.out.println("Reading Object Value");
		Thread.sleep(1000);
		str = client_id + "/" + object_id + "," + client_id + "," + object_id;
	//	str = "/urn:esn:CLIENTID3"+ "/11006" + ",/urn:esn:CLIENTID3" + ",11006";
		tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/read");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		Thread.sleep(5000);
		System.out.println("Value read and updated in DB");		
		
	/*	str = "/urn:esn:CLIENTID3 "+ ",11005";
		tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/delete");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		System.out.println("Entry deleted");*/
		
		
		
	}
}
