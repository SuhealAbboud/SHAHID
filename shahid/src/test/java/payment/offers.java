package payment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;






public class offers {
	static FileWriter fileWriter;
	static FileWriter fileWriter1;
	static String promation="WB_SPORT_40P_";//IOS_Ramadan2023_VIP_"//"GW_Ramadan2023_VIP_"//Ramadan2023_VIP_
	//RE_50P_//iOS_RE_50P_//RE_40P_//GW_RE_40P_//WB_SPORT_50P_//GW_WB_SPORT_50P_//iOS_WB_SPORT_50P_
	static String env="prod";
	
	public static void main(String[] args) throws Exception {
		promation="BYG_SPORT_50P_";
		main();
		promation="BYG_SPORT_40P_";
		main();
		promation="BYG_SPORT_30P_";
		main();
		promation="BYG_30P_";
		main();
		promation="BYG_40P_";
		main();
		promation="BYG_50P_";
		main();



	}
	
	public static void main () throws Exception {
		// TODO Auto-generated method stub
				LocalDate currentDate = LocalDate.now();


				
				fileWriter = new FileWriter("/Users/admin/Desktop/Auto_excution/BYG/Auto_excution_"+promation+"/" +currentDate+env+"_Failed"+ ".csv");
						
				fileWriter1 = new FileWriter("/Users/admin/Desktop/Auto_excution/BYG/Auto_excution_"+promation+"/"+"passed" +currentDate+env+ "-.csv");
						
				BufferedWriter writer = new BufferedWriter(fileWriter);
				BufferedWriter writer1 = new BufferedWriter(fileWriter1);
				fileWriter.write(" message  ,"+"product,"+"promotionalPrice,"+"sheetprice"+ "\n");
				fileWriter1.write(" Result  ,"+"product,"+"promotionalPrice,"+"sheetprice"+ "\n");
				for (int i = 1; i < Country().length;i++) {
					String url = "https://rest-"
							+ env
							+ "-shahid.evergent.com/shahid/getPromotions";
					    	
					        String body = "{\"GetPromotionsRequestMessage\": {\"channelPartnerID\": \"SHAHID\",\"apiKey\": \""
					        		+ getapikey()+ "\",\"dmaID\": \""+ Country()[i][0]+ "\",\"promotionID\": \""+ promation+ Country()[i][1]+ "\"}}";
					        		
					        		
					     
					       try {
					    	   apivalues(url,body,Country()[i][2]);
					       }catch(java.lang.NullPointerException e) {
					    	   System.out.println(   "not configured by ev   ");
					    	   fileWriter.write("not configured by ev ,"+Country()[i][1]+","+""+","+ ""+","+"\n");
					    	   
					       }
					       
							}
							fileWriter.close();
							fileWriter1.close();
				}
		
	
		
	
	public static String[][] Country()throws Exception   {
		

	      
		Reader reader = new FileReader("/Users/admin/Documents/ramadan/"+ promation+ ".csv");
 		
 		
	    CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

	    List<CSVRecord> records = csvParser.getRecords();
	    String[][] array = new String[records.size()][];
	    
	    for (int i = 0; i < records.size(); i++) {
	        CSVRecord record = records.get(i);
	        String[] row = new String[record.size()];
	        for (int j = 0; j < record.size(); j++) {
	            row[j] = record.get(j);
	        }
	        array[i] = row;
	    
	    
	    }
	  //  System.out.println(   array);
	return array;
	}
	public static String APIrequest(String urlString, String body)throws Exception   {
		URL obj = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set request method
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(body);
        wr.flush();
        wr.close();

        // Send the request
        int responseCode = con.getResponseCode();
        

        // Get the response
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
       
		return response.toString();
    	
    }
public static JsonNode apivalues (String url, String body,String sheetprice) throws Exception {
	//JsonNode promotionObjectNode = null;
		String jsonString = APIrequest(url,body);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        
       
           JsonNode promotionsResponseMessageArrayNode = rootNode.path("GetPromotionsResponseMessage").path("promotionsResponseMessage");

           JsonNode   promotionObjectNode = promotionsResponseMessageArrayNode.get(0);
      
        
        String promotionalPrice1 = promotionObjectNode.get("promotionalPrice").toString();
        BigDecimal price = new BigDecimal(promotionalPrice1);
        String promotionalPrice = price.setScale(2, RoundingMode.HALF_UP).stripTrailingZeros().toPlainString();
        String sku = promotionObjectNode.get("product").toString();
        if (promotionalPrice.equals(sheetprice)) {
        	System.out.println(   "Passed   "+sku);
        	fileWriter1.write("Passed ,"+sku+","+promotionalPrice+","+ sheetprice+","+"\n");
        	
        }
        else {
        	System.out.println(   "Failed"+sku);
     		fileWriter.write("wrong price ,"+sku+","+promotionalPrice+","+ sheetprice+","+"\n");    
        }

        	
       

		
		return promotionObjectNode;
		
		
	}
public static String getapikey() {
	String apikey = null;
	
	
if(env.equals("dev")) {
 apikey="8uMMWK58TY2fGjJS";
 
}
if(env.equals("prod")) {
 apikey="ga9medLeRadFSwBa";
 
}
if (env.equals("uat")){
 apikey="szm7B2v9nkTjV2wxFJ26";
 
}
return apikey;
	
}
	
		
		
		
	
	
		
		
	}
	
	
		
	
		
		




