package Pkg_Bootstrap;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;

@Path ("register")
public class Server_Op {
	@POST
    @Consumes(MediaType.APPLICATION_JSON)
	public Response register_obj(String reg_data){
		MongoClient mongo1;
		mongo1 = new MongoClient("localhost", 27017);
		DB db = mongo1.getDB("database_name");
		DBCollection table = db.getCollection("273_Reg_Data");
		BasicDBObject document = new BasicDBObject();
		DBObject query = (DBObject) JSON.parse(reg_data); 
		System.out.println(reg_data);
		System.out.println(query);
		table.insert(query);    //Insert current entry in the DB
		return Response.status(Response.Status.CREATED).entity("2.01").build();
	}

	

}


