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
		WebTarget tar1;
		Response response;
		Client cl1 = ClientBuilder.newClient();
		String str;
		String client_id, object_id, new_value, value;
		String min_pressure = "100";
		String max_pressure = "5000";
		System.out.println("Enter");
		System.out.println("1. Read");
		System.out.println("2. Dicsover");
		System.out.println("3. Write");
		System.out.println("4. Write Attributes");
		System.out.println("5. Execute");
		System.out.println("6. Create");
		System.out.println("7. Delete");
		System.out.println("8. Observe");
	//	System.out.println("9. Notify");
		System.out.println("9. Cancel Observation");
		System.out.println("10. Read all clients");
		System.out.println("11. Read all alerts");
		
		Scanner scan = new Scanner(System.in);
		int operation = scan.nextInt();
		switch (operation ){
		case 1:
			System.out.println("Enter Client ID");
			client_id = scan.next();
			System.out.println("Enter Object ID");
			object_id = scan.next();
			str = client_id + "/" + object_id + "," + client_id + "," + object_id;
			tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/read");
			response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			Thread.sleep (1000);
			System.out.println("Value read and updated in DB");		
			break;
		case 2:
			System.out.println("Enter Client ID");
			 client_id = scan.next();
			 str = client_id;
			tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/discover");
			response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			Thread.sleep (5000);
		//	System.out.println("Value read and updated in DB");
			break;
		case 3:
			System.out.println("Enter Client ID");
			 client_id = scan.next();
			System.out.println("Enter Object ID");
			 object_id = scan.next();
			System.out.println("Enter New Value");
			new_value = scan.next();
			str = client_id+","+object_id+","+new_value;
			tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/update");
			response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			System.out.println("Updated");
			break;
		case 4:
			 System.out.println("Enter Client ID");
			 client_id = scan.next();
			 System.out.println("Enter Object ID");
			 object_id = scan.next();
			 System.out.println("Enter Minimum Pressure");
			 min_pressure = scan.next();
			 System.out.println("Enter Maximum Pressure");
			 max_pressure = scan.next();
			 str = client_id + "/" + object_id + "," + client_id + "," + object_id+","+ min_pressure+ ","+ max_pressure+ ","+ "30";
			 tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/write_attributes");
			 response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			 break;
			 
		case 5:
			str = "Execute";
			tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/execute");
			 response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			break;
		case 6:
			System.out.println("Enter Client ID");
			 client_id = scan.next();
			System.out.println("Enter Object ID");
			 object_id = scan.next();
			 System.out.println("Enter Value");
			value = scan.next();
			str = client_id+","+object_id+","+value; //"/urn:esn:CLIENTID3 "+ ",11004" + ",100";
			tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/create");
			response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			break;
		case 7:
			System.out.println("Enter Client ID");
			 client_id = scan.next();
			System.out.println("Enter Object ID");
			 object_id = scan.next();
			str = client_id+ ","+object_id;
		    tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/delete");
			response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			System.out.println("Entry deleted");
			break;
		case 8:
			 System.out.println("Write attributes for Observing");
			 System.out.println("Enter Client ID");
			 client_id = scan.next();
			 System.out.println("Enter Object ID");
			 object_id = scan.next();
			 System.out.println("Enter Minimum Pressure");
			 min_pressure = scan.next();
			 System.out.println("Enter Maximum Pressure");
			 max_pressure = scan.next();
			 str = client_id + "/" + object_id + "," + client_id + "," + object_id+","+ min_pressure+ ","+ max_pressure+ ","+ "30";
			 tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/write_attributes");
			 response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			 Thread.sleep(5000);
			 tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/observe");
			 response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			 System.out.println("Started Observation");
			 break;
		case 9:
			 System.out.println("Enter Client ID");
			 client_id = scan.next();
			 System.out.println("Enter Object ID");
			 object_id = scan.next();
			 str = client_id + "/" + object_id + "," + client_id + "," + object_id;
			 tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/cancel_observe");
			 response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
			 System.out.println("Cancelled Observation");
			 break;
		case 10:
			 tar1 = cl1.target("http://localhost:8080/273_Proj_Server/boot/read_sensors");
			 response = tar1.request(MediaType.APPLICATION_JSON).get();
			
			System.out.println(response.readEntity(String.class));
		    break;
		case 11:
			tar1 = cl1.target("http://localhost:8080/273_Proj_Server/boot/read_alerts");
			response = tar1.request(MediaType.APPLICATION_JSON).get();		
			System.out.println(response.readEntity(String.class));
			break;
		}
		
		
		
		
		
	
		
	/*	str = "/urn:esn:CLIENTID3 "+ ",11005" + ",100";
		tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/create");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		
		Thread.sleep(5000);
		
		str = "/urn:esn:CLIENTID3 "+ ",11006" + ",100";
		tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/create");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		
		Thread.sleep(5000);*/
	
				
		
		
		
	/*	str = "/urn:esn:CLIENTID3 "+ ",11005";
		tar1 = cl1.target("http://localhost:8080/273_Proj_Server/api/events/delete");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(str));
		System.out.println("Entry deleted");*/
		
		
	//	Thread.sleep(2000);
		
		
		
	}
}
