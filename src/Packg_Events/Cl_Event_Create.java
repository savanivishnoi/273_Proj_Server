package Packg_Events;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;

@Path("events/{eventtype}")
public class Cl_Event_Create {
	static String operation = "empty";
	static String operation_data = "empty";
	 
     @GET
     @Produces(SseFeature.SERVER_SENT_EVENTS)
     public EventOutput allEvents(@PathParam("eventtype") String eventtype) {
        final EventOutput eventOutput = new EventOutput();
        System.out.println("Get event received " + eventtype);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                	final OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
                    eventBuilder.name(operation);
                    eventBuilder.data(String.class, operation_data);
                    final OutboundEvent event = eventBuilder.build();
                    eventOutput.write(event);
                	operation = "empty";
                	operation_data = "empty";
                } catch (IOException e) {
                    throw new RuntimeException(
                        "Error when writing the event.", e);
                } finally {
                    try {
                        eventOutput.close();
                    } catch (IOException ioClose) {
                        throw new RuntimeException(
                            "Error when closing the event output.", ioClose);
                    }
                }
            }
        }).start();
        
        return eventOutput;
     }
     
     @POST
     @Produces(MediaType.TEXT_PLAIN)
     public Response setEvents(@PathParam("eventtype") String eventtype, String input) {
    	 String[] obj_input;
    	 System.out.println(eventtype);
    	 // wait while the current operation has been processed
    	 
    	 if(eventtype.equals("create")) {
    		 obj_input = input.split(",");
    		 operation_data = create_Call(obj_input[0], obj_input[1], obj_input[2]);
    	 } else if (eventtype.equals("read")) {
    		 operation_data = input;

    	 } else if (eventtype.equals("update")) {
    		 obj_input = input.split(",");
    		operation_data = update_Call(obj_input[0], obj_input[1], obj_input[2]);

    	 } else if(eventtype.equals("delete")) {
    		 obj_input = input.split(",");
    		 operation_data = delete_Call(obj_input[0], obj_input[1]);

    	 }
    	 operation = eventtype;
    	 return Response.ok("OK", MediaType.TEXT_PLAIN).build();
     }
     
     @PUT
     @Produces(MediaType.TEXT_PLAIN)
     public Response putEvents(@PathParam("eventtype") String eventtype, String input) {
    	 if (eventtype.equals("read")) {
    		String[] arr_input;
    		String curr_date;
    		arr_input = input.split(",");
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		Date date = new Date();
    		curr_date = dateFormat.format(date).toString();
     		MongoClient db_cl = new MongoClient("localhost", 27017);
     		BasicDBObject object = new BasicDBObject();
     		object.put("_id", arr_input[0]);
     		object.put("client_id", arr_input[1]);
     		object.put("object_id", arr_input[2]);
     		object.put("value", arr_input[3]);
     		object.put("time", curr_date);
     		DBCollection object_val = db_cl.getDB("database_name").getCollection("273_Object_Transaction_Values");	
     		object_val.insert(object);
    	 }
    	 return Response.ok("OK", MediaType.TEXT_PLAIN).build();
     }
     
     public String create_Call(String sensor_id, String object_id, String pressure){
		String id;
		MongoClient db_cl = new MongoClient("localhost", 27017);
		BasicDBObject object = new BasicDBObject();
		//DBCollection tab = db.getCollection("273_Reg_Data");
		// in register db we have all the values.. loop on all the sensors or obj ids _ids
		object.put("_id", sensor_id+"/"+object_id);
		id = sensor_id+"/"+object_id;
		object.put("client_id", sensor_id);
		object.put("object_id", object_id);
		object.put("value", pressure);
		DBCollection object_val = db_cl.getDB("database_name").getCollection("273_Object_Values");	
		object_val.insert(object);
		
		return "{" + Jsonstr("_id", id) + "," + Jsonstr("client_id", sensor_id) +","+Jsonstr("object",object_id) + "," +Jsonstr("value",pressure)+"}"  ;
     }
     
     public String update_Call(String sensor_id, String object_id, String pressure){
 		String id;
 		id = sensor_id+"/"+object_id;
 		MongoClient db_cl = new MongoClient("localhost", 27017);
 		DBCollection object_val = db_cl.getDB("database_name").getCollection("273_Object_Values");	
 		BasicDBObject object = new BasicDBObject();
 		object.append("$set", new BasicDBObject().append("value", pressure));		
 		BasicDBObject searchQuery = new BasicDBObject().append("_id", id);
 		object_val.update(searchQuery, object );
 		id = id + "," + pressure;
 		return id;
 		
     }
     
     public String delete_Call(String sensor_id, String object_id){
    	 String id = sensor_id+"/"+object_id;
    	 MongoClient db_cl = new MongoClient("localhost", 27017);
  		 DBCollection object_val = db_cl.getDB("database_name").getCollection("273_Object_Values");	
  		 BasicDBObject query = new BasicDBObject();
  		 query.put("_id" , id );
  		 object_val.remove(query);
    	 return id;
     }

     public String Jsonstr(String key, String value) {
    	 String str = "";
    	 str = "\""+key+"\":"+"\""+value+"\"";
    	 return str;
     }
}
     


