package Pkg_Bootstrap;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;

@Path ("Graph")	
public class Pipe_Graph {
	@POST
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.TEXT_PLAIN)
	public Response graphCreate(String input){
		String[] nodes;
		MongoClient db_cl;
		db_cl = new MongoClient("localhost", 27017);
		DB db = db_cl.getDB("database_name");
		DBCollection tab = db.getCollection("273_Mapped_Objects");
		BasicDBObject ls_map_obj = new BasicDBObject();
		System.out.println("input"+input);
		nodes = input.split("\\+");
		ls_map_obj.put("_id", nodes[0]); //client id + object id combination 
		ls_map_obj.put("conn_nodes", nodes[1]);
		tab.insert(ls_map_obj);
		return Response.ok("OK").build();
	}

}
