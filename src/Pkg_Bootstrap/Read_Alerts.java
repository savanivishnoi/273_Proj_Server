package Pkg_Bootstrap;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;


@Path("read_alerts")
public class Read_Alerts{
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response sendAlerts(){
		MongoClient cl1;
		cl1 = new MongoClient("localhost" ,27017);
		DB db = cl1.getDB("database_name");
		BasicDBObject dbo = new BasicDBObject();
		DBCollection tab = db.getCollection("273_Alerts");
		DBCursor result = tab.find();
		JSON json = new JSON();
		String json_op= json.serialize(result);
		System.out.println("ss"+json_op);
		return Response.ok(json_op, MediaType.APPLICATION_JSON).build();
		
	}
}
