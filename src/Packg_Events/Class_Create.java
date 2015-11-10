package Packg_Events;


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
import org.glassfish.jersey.media.sse.EventOutput;
import org.glassfish.jersey.media.sse.OutboundEvent;
import org.glassfish.jersey.media.sse.SseFeature;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.mongodb.MongoException;




import java.util.ArrayList;
//sensorId, timestamp, location, sensor type- source sink junction 
public class Class_Create {
	public void create_Call(String sensor_id, String pressure, String volume){
	String[] objs;// all objects wih complete data, comma seperated
	String[] singl_obj;//
	String client_input	= "" ;// = "{";
	String id;
	MongoClient db_cl;
	
	db_cl = new MongoClient("localhost", 27017);
	DB db = db_cl.getDB("database_name");
	DBCollection tab = db.getCollection("273_Reg_Data");
	// in register db we have all the values.. loop on all the sensors or obj ids _ids
	BasicDBObject obj = new BasicDBObject();
	obj.put("_id",sensor_id);
	BasicDBObject fld = new BasicDBObject();
	fld.put("objects", 1);
	fld.put("_id", 0);
	DBObject cursor = tab.findOne(obj,fld);
	String result = cursor.toString(); 
	result = result.replaceAll("<", "");
	result = result.replaceAll(">", "");
	result = result.replaceAll("\"", "");
	result = result.replaceAll("objects", "");
	result = result.replace("{", "");
	result = result.replace("}", "");
	result = result.replace(":", "");
	objs = result.split(",");
	System.out.println(result);
	BasicDBObject object = new BasicDBObject();
	for(int i = 0; i< objs.length; i++){
		//split, take 3rd element 
	//	ObjectMapper objMap = new ObjectMapper();
	//	objMap.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
//		Objects_List objl = objMap.readValue(objs[i], Objects_List.class);
		
		singl_obj = objs[i].split("/"); 
		System.out.println(objs[i]);
		object.put("_id", sensor_id+"/"+singl_obj[2]);
		id = sensor_id+"/"+singl_obj[2];
		object.put("object", singl_obj[2]);
	//	client_input = client_input  + "{" + Jsonstr("_id", id) + "," +Jsonstr("object",singl_obj[2])  ;
		switch (singl_obj[2]){
		 case "11004":
			 object.put("value", "pressure");
	//		 client_input = client_input +  "," + Jsonstr("value",pressure) +"}";
			 break;
		 case "abc"://put temperatur
			 object.put("value", volume);	
//			 client_input = client_input + "," + Jsonstr("value",volume) +"}";
			 break;
		 default: 
	//		 client_input = client_input + "}";
		}
	DBCollection object_val = db.getCollection("273_Object_Values");	
//	object_val.insert(object);
	System.out.println("DONE");
	
//	Client cl1 = ClientBuilder.newClient();
//	String 
//	WebTarget tar1 = cl1.target("http://localhost:8080/273_Assgn_1_Server/api/Bootstrap");
//	Response response = tar1.request(MediaType.APPLICATION_JSON_TYPE).post(Entity.text());

	
	
		
	// client_id obj_id value 
		
	}
//	client_input = client_input + "}";
	
	//line.split("\\|");
	Cl_GlobalVariables.setGv(1);
	System.out.println(client_input);
	

	}


	public String Jsonstr(String key, String value) {
	String str = "";
	str = "\""+key+"\":"+"\""+value+"\"";
	return str;
}


}
@JsonIgnoreProperties(ignoreUnknown = true)
class Objects_List{
		public String objects = "objects";
	}
enum ResourceId {
	MANUFACTURER ("Manufacturer", 11001),
	TEMPERATURE ("Temperature", 11002),
	FLOW_RATE ("Temperature", 11003),
	PRESSURE ("Temperature", 11004),
	HARDWARE_VERSION ("Hardware Version", 5);
	
	private final String name;
	private final Integer value;
	
	ResourceId(String name, Integer value) {
        this.name = name;
        this.value = value;
    }
	
    public String nameOf() { return name; }
    public Integer valueOf() { return value; }
};
