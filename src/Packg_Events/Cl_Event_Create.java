package Packg_Events;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import java.io.IOException;

import java.util.Date;
import java.util.Properties;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Singleton;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.ws.rs.Consumes;
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
import org.glassfish.jersey.media.sse.SseBroadcaster;

@Path("events/{eventtype}")
public class Cl_Event_Create {
	static String operation = "empty";
	static String operation_data = "empty"; 
	static client_objects[] clients_ls = new client_objects[] 
			{
					new client_objects(), 
					new client_objects(),
					new client_objects(),
					new client_objects()
			};
	static boolean readx_flag = false;
/*	
	@Singleton
	@Path("broadcast")
	public static class BroadcasterResource {
	 
	    private SseBroadcaster broadcaster = new SseBroadcaster();
	 
	    @POST
	    @Produces(MediaType.TEXT_PLAIN)
	    @Consumes(MediaType.TEXT_PLAIN)
	    public String broadcastMessage(String message) {
	        OutboundEvent.Builder eventBuilder = new OutboundEvent.Builder();
	        OutboundEvent event = eventBuilder.name("message")
	            .mediaType(MediaType.TEXT_PLAIN_TYPE)
	            .data(String.class, message)
	            .build();
	 
	        broadcaster.broadcast(event);
	 
	        return "Message '" + message + "' has been broadcast.";
	    }
	 
	    @GET
	    @Produces(SseFeature.SERVER_SENT_EVENTS)
	    public EventOutput listenToBroadcast() {
	        final EventOutput eventOutput = new EventOutput();
	        this.broadcaster.add(eventOutput);
	        return eventOutput;
	    }
	}*/
	 
  /* Below code will send events to the client operation holds the operation that is to be performed 
   * on the client side like read, observer etc. operation_data holds the operation details. like client id, values to write etc.
   *  */
	@GET
     @Produces(SseFeature.SERVER_SENT_EVENTS)
     public EventOutput allEvents(@PathParam("eventtype") String eventtype) {
        final EventOutput eventOutput = new EventOutput();
       // System.out.println("Get event received " + eventtype);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                	// long threadId = Thread.currentThread().getId();
                	// System.out.println(threadId+" thread");
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
    	 long threadId = Thread.currentThread().getId();
    	 System.out.println(threadId+" thread");
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

    	 } else if(eventtype.equals("write_attributes")) {
    		 operation_data = input;

    	 } else if(eventtype.equals("observe")) {
    		  threadId = Thread.currentThread().getId();
        	 System.out.println(threadId+" thread");
    		 operation_data = (input);

    	 } else if(eventtype.equals("execute")) {
    		 operation_data = (input);

    	 } else if(eventtype.equals("discover")) {
    		 operation_data = (input);

    	 }
    	 else if(eventtype.equals("cancel_observe")) {
    		 operation_data = (input);    		 
    	 }
    	 operation = eventtype;
    	 return Response.ok("OK", MediaType.TEXT_PLAIN).build();
     }
     
     @PUT
     @Produces(MediaType.TEXT_PLAIN)
     public Response putEvents(@PathParam("eventtype") String eventtype, String input) throws InterruptedException {
    	 if (eventtype.equals("read")) {
    		String[] arr_input;
    		String curr_date;
    		arr_input = input.split(",");
    		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		Date date = new Date();
    		curr_date = dateFormat.format(date).toString();
     		MongoClient db_cl = new MongoClient("localhost", 27017);
     		BasicDBObject object = new BasicDBObject();
     		object.put("id", arr_input[0]);
     		object.put("client_id", arr_input[1]);
     		object.put("object_id", arr_input[2]);
     		object.put("value", arr_input[3]);
     		object.put("time", curr_date);
     		DBCollection object_val = db_cl.getDB("database_name").getCollection("273_Object_Transaction_Values");	
     		object_val.insert(object);
     		System.out.println("Current Value "+arr_input[3]);
    	 } else if (eventtype.equals("notify")) {
    		// long threadId = Thread.currentThread().getId();
        	// System.out.println(threadId+" thread");
    		 String[] arr_input;
    		 String curr_date;
     		 arr_input = input.split(",");
     		 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    		 Date date = new Date();
    		 curr_date = dateFormat.format(date).toString();
     		 MongoClient db_cl = new MongoClient("localhost", 27017);
     		 BasicDBObject object = new BasicDBObject();
     		 object.put("id", arr_input[0]);
     		 object.put("client_id", arr_input[1]);
     		 object.put("object_id", arr_input[2]);
     		 object.put("value", arr_input[3]);
     		 object.put("time", curr_date);
     		 DBCollection object_val = db_cl.getDB("database_name").getCollection("273_Object_Transaction_Values");	
     		 object_val.insert(object);
     		// System.out.println(input);
     		 notify_Action(input);
    	 } else if (eventtype.equals("discover")){
    		 System.out.println("List of objects "+input);
    	 } else if (eventtype.equals("execute")){
    		 System.out.println(input);
    	 } else if (eventtype.equals("readx")){
    		 System.out.println("inside readx client return");
    		 String[] arr_input;
    		 arr_input = input.split(",");
    		 for (int i = 0; i<4; i++){
    			 System.out.println("a"+clients_ls[i].object_id +" curr client"+"new"+arr_input[0]);
    			 if ( clients_ls[i].object_id.equals(arr_input[0])){
    				 System.out.println("Help");
    				 clients_ls[i].object_value = arr_input[1];
    				 readx_flag = true;
    			 }
    		 }
    		// object.put("id", arr_input[0]);
    		// object.put("id", arr_input[0]);
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
     public void notify_Action(String input) throws InterruptedException{
    	 String[] arr_input;
    	 String[] clients;
    	 String[] clients_list;
    	 String curr_date;
    	 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date date = new Date();
		 curr_date = dateFormat.format(date).toString();
    	 arr_input = input.split(",");
    	 MongoClient db_cl = new MongoClient("localhost", 27017);
  		 DBCollection object_val = db_cl.getDB("database_name").getCollection("273_Alerts");	
  		 BasicDBObject values = new BasicDBObject();
  		 
  		 if ( arr_input.length > 1){
    		 switch (arr_input[4]){
    			 case "-1":
    				 values.put("id", arr_input[0]);
    		  		 values.put("pressure_value", arr_input[3]);
    		  		 values.put("pressure_status",arr_input[4]);
    		  		 values.put("time", curr_date);
    		  		object_val.insert(values);
    		  		 
    				 System.out.println("ALERT: Pressure is less than minimum. Current pressure is "+arr_input[3]);
				try {
				
					send_email("ALERT: Pressure is less than minimum. Current pressure is "+arr_input[3]);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    				 break;
    			 case "1":
    				 values.put("id", arr_input[0]);
			  		 values.put("pressure_value", arr_input[3]);
			  		 values.put("pressure_status",arr_input[4]);
			  		 values.put("time", curr_date);
			  		object_val.insert(values);
    				 System.out.println("ALERT: Pressure is greater than maximum. Current pressure is "+arr_input[3]);
				try {
					send_email("ALERT: Pressure is greater than maximum. Current pressure is "+arr_input[3]);
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    				 break;
    			 case "100":
    			//	 MongoClient db_cl =  new MongoClient("localhost", 27017);
    				 DBCollection tab = db_cl.getDB("database_name").getCollection("273_Mapped_Objects");
    				 BasicDBObject srch = new BasicDBObject();
    				// System.out.println(arr_input[0]+"array");
    				 srch.put("_id", arr_input[0]);
    				 BasicDBObject fld = new BasicDBObject();
    				 fld.put("conn_nodes", 1);
    				 fld.put("_id", 0);
    				 DBObject result = tab.findOne(srch,fld);
    				 
    				 String res = result.toString();
    				 clients = res.split(":");
    				 clients[1] = clients[1].replaceAll("\"", "");
    				 clients[1] = clients[1].replaceAll("}", "");
    				 clients[1] = clients[1].replaceAll(" ", "");
    				 clients_list = clients[1].split(",");
    				 
    					
    			
    				 
    				 for(int i=0; i<4; i++){
    					  						
    					 operation_data = clients_list[i];
    					 operation = "readx";
    					 readx_flag = false;
    					 clients_ls[i].object_id = clients_list[i];
    					 System.out.println("inside for on clients");
    					 while(readx_flag == false){
    						 Thread.sleep(500);
    					 } 					 
    				 }
				try {
					compare_value(arr_input[0],arr_input[3]);
				} catch (AddressException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MessagingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    				 System.out.println(result);
    				 
    				// String json_res = JSON.serialize(result);
    			//	 ObjectMapper obj = new ObjectMapper();
    			//	 MapNodes nodes = new 
    				 
    				 System.out.println("ALERT: Pressure difference is too high. Current pressure is "+arr_input[3]);
    				 break;
    	 }
    	 }
     }
     
    // public String write_Attributes(String input){
    	/* String[] arr_input;
    	 arr_input = input.split(",");*/
    	 
    	 
    	 
  //   }

     public String Jsonstr(String key, String value) {
    	 String str = "";
    	 str = "\""+key+"\":"+"\""+value+"\"";
    	 return str;
     }
     public void compare_value(String client_main, String value_main) throws AddressException, MessagingException{
    	 int diff = 100000;
    	 int curr_diff = 0;
    	 int curr_value = Integer.valueOf(value_main);
    	 String alt_client = "";
    	 System.out.println("Inside compare");
    	 for(int i = 0; i < 4; i++){
    		curr_diff = Math.abs(curr_value - Integer.valueOf(clients_ls[i].object_value));
    		if(curr_diff <= diff){
    			diff = curr_diff; 
    			alt_client = clients_ls[i].object_id;
    		}
    		System.out.println("diff b/w: cl main " + client_main + "value "+ curr_value + " other cl "+clients_ls[i].object_id + "value : "+ clients_ls[i].object_value);
    	 }
    	 System.out.println("Alt client  : "+alt_client);
    	 String curr_date;
    	 DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		 Date date = new Date();
		 curr_date = dateFormat.format(date).toString();
    	
    	 MongoClient db_cl = new MongoClient("localhost", 27017);
  		 DBCollection object_val = db_cl.getDB("database_name").getCollection("273_Alerts");	
  		 BasicDBObject values = new BasicDBObject();
  		 values.put("id", client_main);
  		 values.put("pressure_value", curr_value);
  		 values.put("pressure_status","100");
  		 values.put("connected_node", alt_client);
  		 values.put("time", curr_date);
  		 object_val.insert(values);
  		 
    	  send_email("ALERT: There is a leakage detected between client:- "+client_main+" and client:- "+alt_client);
    	 
     }
     public void send_email(String input) throws AddressException, MessagingException{
    	
         String to = "273projectfall2015@gmail.com";
         String from = "273projectfall2015@gmail.com";
         String host = "smtp.gmail.com";
         System.out.println("Inside mail");

         // Get system properties
         Properties properties = System.getProperties();

         // Setup mail server
         properties.setProperty("mail.smtp.host", "smtp.gmail.com");
         properties.setProperty("mail.smtp.auth", "true");
         properties.setProperty("mail.smtp.starttls.enable", "true");
         properties.setProperty("mail.smtp.port", "587");
   //      properties.setProperty("mail.user", "273projectfall2015@gmail.com");
   //      properties.setProperty("mail.password", "273password");

         // Get the default Session object.
         //Session session = Session.getDefaultInstance(properties);
         Session session = Session.getInstance(properties,
       		  new javax.mail.Authenticator() {
       			protected PasswordAuthentication getPasswordAuthentication() {
       				return new PasswordAuthentication("273projectfall2015@gmail.com", "273password");
       			}
       		  });


         
            // Create a default MimeMessage object.
            MimeMessage message = new MimeMessage(session);

            // Set From: header field of the header.
            message.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message.setSubject("ALERT!");

            // Now set the actual message
            message.setText(input);

            // Send message
            Transport.send(message);
            System.out.println("Sent message successfully....");
         
     }
}
 class client_objects{
	public String object_id = "Savani";
	public String object_value = "Savani";
}
     


