package Packg_Events;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
public class Create_Graph {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Response response;
		String input = "client1/o+client2/o,client3/o,client4/o,client5/o";
		Client cl1 = ClientBuilder.newClient();
		WebTarget tar1 = cl1.target("http://localhost:8080/273_Proj_Server/boot/Graph");
		response = tar1.request(MediaType.TEXT_PLAIN).post(Entity.text(input));

	}

}
