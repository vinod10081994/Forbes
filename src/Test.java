import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

class Test {
	public static void main(String[] args) {
		// inline will store the JSON data streamed in string format
		String inline = "";
		DecimalFormat df2 = new DecimalFormat("#.##");
		try {
			URL url = new URL("https://api.jsonbin.io/b/5d31a1c4536bb970455172ca/latest");
			// Parse URL into HttpURLConnection in order to open the connection in order to
			// get the JSON data
			System.setProperty("http.agent", "Chrome");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// Set the request to GET or POST as per the requirements
			conn.setRequestMethod("GET");
			// Use the connect method to create the connection bridge
			conn.connect();
			// Get the response status of the Rest API
			int responsecode = conn.getResponseCode();
			//System.out.println("Response code is: " + responsecode);

			// Iterating condition to if response code is not 200 then throw a runtime
			// exception
			// else continue the actual process of getting the JSON data
			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				// Scanner functionality will read the JSON data from the stream
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				//System.out.println("\nJSON Response in String format");
				//System.out.println(inline);
				// Close the stream when reading the data has been finished
				sc.close();
			}

			// JSONParser reads the data from string object and break each data into key
			// value pairs
			JSONParser parse = new JSONParser();
			// Type caste the parsed json data in json object
			JSONArray jobj = (JSONArray) parse.parse(inline);
			//System.out.println(jobj);
			// Store the JSON object in JSON array as objects (For level 1 array element i.e
			// Results)
			JSONObject jsonarr_1 = (JSONObject) jobj.get(5);

			//System.out.println(jsonarr_1);
			Gson gson = new Gson();

			// 1. JSON file to Java object
			Product product = gson.fromJson(jsonarr_1.toString(), Product.class);
			System.out.println(product.toString());
            
			if(product.getCurrency()!="INR") {		     		
			double currency=Currency.currencyMethod(product.getCurrency());
			currency*=product.getPrice();
			currency=Double.valueOf(df2.format(currency));
			product.setPrice(currency);
			product.setCurrency("INR");
			}
			PromotionSetA promotion=new PromotionSetA(product);
			System.out.println(promotion.finalDiscount().discountName);
			
		System.out.println(product.toString());
			
			
			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}