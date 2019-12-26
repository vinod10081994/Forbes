import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JsonReader {

  private static String readAll(Reader rd) throws IOException {
    StringBuilder sb = new StringBuilder();
    int cp;
    while ((cp = rd.read()) != -1) {
      sb.append((char) cp);
    }
    return sb.toString();
  }

  public static JsonObject readJsonFromUrl(String urlString) throws IOException, JSONException {
   /* InputStream is = new URL(url).openStream();
    try {
      BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
      String jsonText = readAll(rd);
      JSONObject json = new JSONObject(jsonText);
      return json;
    } finally {      is.close();
    }*/
	  
	  URL url = new URL(urlString);
	  URLConnection request = url.openConnection();
	  request.connect();
	  
	// Convert to a JSON object to print data
	    JsonParser jp = new JsonParser(); //from gson
	    
//	    {
//	        "locations":
//	    }
	    JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
	   JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
	     return rootobj;
  }
  
  public static String getText(String url) throws IOException {
	    HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
	    //add headers to the connection, or check the status if desired..
	    
	    // handle error response code it occurs
	    int responseCode = connection.getResponseCode();
	    InputStream inputStream;
	    if (200 <= responseCode && responseCode <= 299) {
	        inputStream = connection.getInputStream();
	    } else {
	        inputStream = connection.getErrorStream();
	    }

	    BufferedReader in = new BufferedReader(
	        new InputStreamReader(
	            inputStream));

	    StringBuilder response = new StringBuilder();
	    String currentLine;

	    while ((currentLine = in.readLine()) != null) 
	        response.append(currentLine);

	    in.close();
	    
	    String finalStr = "{\"data\":" + response.toString() +"}";

	    return finalStr;
	}

  public static void main(String[] args) throws IOException, JSONException {
	  System.setProperty("http.agent", "Chrome");
//    JsonObject json = readJsonFromUrl("https://api.jsonbin.io/b/5d31a1c4536bb970455172ca/latest");
    String response = getText("https://api.jsonbin.io/b/5d31a1c4536bb970455172ca/latest");
    System.out.println(response);
//    System.out.println(json.toString());
//    System.out.println(json.get("id"));
  }
}