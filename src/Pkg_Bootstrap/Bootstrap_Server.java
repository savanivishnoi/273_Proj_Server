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
//import Db_acc.ClientRecord;
@JsonIgnoreProperties(ignoreUnknown = true)
class ClientInput{
	public String client_name = "Savani";
	public String manufacturer_id = "Savani";
}
@Path ("Bootstrap")  
public class Bootstrap_Server {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response bootStrap(String input) throws JsonParseException, JsonMappingException, IOException
	{
		System.out.println("Inside bs server");
		ObjectMapper objMap = new ObjectMapper();
		objMap.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		ClientInput clRecord = objMap.readValue(input, ClientInput.class); //deserialize JSON
		MongoClient db_cl;
		db_cl = new MongoClient("localhost", 27017);
		DB db = db_cl.getDB("database_name");
		DBCollection tab = db.getCollection("273_BootStrap_Objects");
		BasicDBObject nQuery = new BasicDBObject();
		System.out.println("row 44");
		List<BasicDBObject> ls_srch = new ArrayList<BasicDBObject>();
		ls_srch.add(new BasicDBObject("client_name", clRecord.client_name));
		ls_srch.add(new BasicDBObject("manufacturer_id", clRecord.manufacturer_id));
		nQuery.put("$and", ls_srch);
		BasicDBObject fld = new BasicDBObject();
	  //  fld.put("DataInput", 1);
		DBObject cursor = tab.findOne(nQuery);	//Read previous data
	//	System.out.println("row 53");
		JSON json = new JSON();
		String json_op = json.serialize(cursor);
		return Response.ok(json_op, MediaType.APPLICATION_JSON).build(); //Response Set
		//return Response.ok("ww");
	}	

}

