package Pkg_Bootstrap;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

@Path("read_sensors")
public class Read_Sensors {
	
	@GET 
	
	@Produces(MediaType.APPLICATION_JSON)
	public Response readS(){
		MongoClient cl1;
		cl1 = new MongoClient("localhost" ,27017);
		DB db = cl1.getDB("database_name");
		BasicDBObject dbo = new BasicDBObject();
		dbo.put("_id", 1);
		DBCollection tab = db.getCollection("273_Reg_Data");
		DBCursor result = tab.find(null,dbo);
		
		JSON json = new JSON();
		String json_op= json.serialize(result);
	//	while (result.hasNext()){
		//	 json_op = json_op + json.serialize(result.next());//serialize(result);
		//}
		System.out.println(json_op);
		return Response.ok(json_op, MediaType.APPLICATION_JSON).build();
		
		
	}
	

}
