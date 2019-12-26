import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

class Currency {
	public static double currencyMethod(String currency) {
		// inline will store the JSON data streamed in string format
		String inline = "";
		double amount=1;
		
         
		try {
			URL url = new URL("https://api.exchangeratesapi.io/latest");
			
			System.setProperty("http.agent", "Chrome");

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			
			conn.connect();
			
			int responsecode = conn.getResponseCode();
			

			
			if (responsecode != 200)
				throw new RuntimeException("HttpResponseCode: " + responsecode);
			else {
				
				Scanner sc = new Scanner(url.openStream());
				while (sc.hasNext()) {
					inline += sc.nextLine();
				}
				
				sc.close();
			}

			
			JSONParser parse = new JSONParser();
			
			/*JSONArray jsonArray = (JSONArray) parse.parse(inline);*/
			
			JSONObject jsonObject=(JSONObject) parse.parse(inline);
			String base=(String) jsonObject.get("base");
			
			
					
			JSONObject rates=(JSONObject) jsonObject.get("rates");
			
			
			
			if(base.equals(currency)) {		
				
				amount= (double) rates.get("INR");
				
				}
			else {
				double inr,currencyint;
				 inr=(double) rates.get("INR");
			     currencyint=(double) rates.get(currency);
			     amount=inr/currencyint;
				
			}
			
			/*Gson gson = new Gson();

			Product staff = gson.fromJson(jsonarr_1.toString(), Product.class);
            */
			
			
			
			

			conn.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return amount;
			}
	
	
	public static void main(String args[]) {
		
		System.out.println(Currency.currencyMethod("USD"));
	}
}