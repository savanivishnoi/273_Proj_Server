package Pkg_Bootstrap;
import javax.ws.rs.*;
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
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;
@Path("update_reg")
public class update_registeration {
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(String input) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper objMap = new ObjectMapper();
		objMap.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
		Json_Read clRecord = objMap.readValue(input, Json_Read.class); 
		MongoClient mongo1;
		mongo1 = new MongoClient("localhost", 27017);
		DB db = mongo1.getDB("database_name");
		DBCollection table = db.getCollection("273_Reg_Data");
		BasicDBObject document = new BasicDBObject();
		DBObject query = new BasicDBObject("_id",clRecord.client_id);
		DBObject update = new BasicDBObject();
        update.put("$set", new BasicDBObject("lifetime",clRecord.lifetime));
        WriteResult result = table.update(query, update);
        BasicDBObject nQuery = new BasicDBObject();
        query = null;
		nQuery.put("_id",clRecord.client_id);
		BasicDBObject fld = new BasicDBObject();
	    fld.put("lifetime", 1);
		DBObject cursor = table.findOne(nQuery, fld);	//Read previous data
		String json_op = JSON.serialize(cursor); 
		Json_Read clRecord_n = objMap.readValue(json_op, Json_Read.class); 
		if ( result.isUpdateOfExisting()  
        	&& clRecord_n.lifetime.equals(clRecord.lifetime)){
        	return Response.status(Response.Status.OK).entity("2.04").build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity("4.00").build();
      }
   
}
@JsonIgnoreProperties(ignoreUnknown = true)
class Json_Read{
	public String client_id = "SV";
	public String lifetime = "SV";
}
