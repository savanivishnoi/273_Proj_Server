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
import com.mongodb.WriteResult;
import com.mongodb.util.JSON;
import java.util.ArrayList;
import java.util.List;

@Path ("deregister")
public class Deregister {
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	public Response dereg(String client_id){
		MongoClient db_cl;
		db_cl = new MongoClient("localhost", 27017);
		DB db = db_cl.getDB("database_name");
		DBCollection table = db.getCollection("273_Reg_Data");
		DBObject query = (DBObject) JSON.parse(client_id); 
		DBObject rs = table.findOne(query);
	  if (rs == null){
			return Response.status(Response.Status.BAD_REQUEST).entity("4.00").build();
		}
		table.remove(query);
		return Response.status(Response.Status.OK).entity("2.02").build();
	}
}
