package test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.HttpClient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class limesurvey {
	
    public static String parse(String jsonLine) {
	 JsonElement jelement = new JsonParser().parse(jsonLine);
	 //System.out.println(jelement.toString());
	 JsonObject  jobject = jelement.getAsJsonObject();
	 String result = jobject.get("result").getAsString();
	 return result;
	}
	
	
    public static void main(String[] args) throws UnsupportedEncodingException {
    
    CloseableHttpClient client = HttpClientBuilder.create().build();
         
      HttpPost post = new HttpPost("https://localhost/limesurvey/index.php/admin/remotecontrol");
      post.setHeader("Content-type", "application/json");
      post.setEntity( new StringEntity("{\"method\": \"get_session_key\", \"params\": [\"admin\", \"password\"], \"id\": 1}"));
      try {
        HttpResponse response = client.execute(post);
        if(response.getStatusLine().getStatusCode() == 200){
        	
            HttpEntity entity = response.getEntity();
            String sessionKey = parse(EntityUtils.toString(entity)); 
            post.setEntity( new StringEntity("{\"method\": \"list_groups\", \"params\": [ \""+sessionKey+"\", \"135259\" ], \"id\": 1}"));
            response = client.execute(post);
            if(response.getStatusLine().getStatusCode() == 200){
                entity = response.getEntity();
                System.out.println(EntityUtils.toString(entity));
                }
           }
       
       
      } catch (IOException e) {
        e.printStackTrace();
      }
    }}