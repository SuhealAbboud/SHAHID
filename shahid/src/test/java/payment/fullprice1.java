package payment;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;


public class fullprice1  {
	
	
	static FileWriter fileWriter;
	static FileWriter fileWriter1;
	static String env= "prod";
	static String paymentMode="ApplePay";//Credit Card,App Store Billing//ApplePay


	public static void main(String[] args) throws Exception {
		LocalDate currentDate = LocalDate.now();
		fileWriter = new FileWriter("/Users/admin/Downloads/"+ paymentMode+"_"+currentDate+ "_Failed.csv");
				
				
		BufferedWriter writer = new BufferedWriter(fileWriter);
		fileWriter.write(" message  ,"+"country ,"+"sku ,"+"EV price ,"+"sheet price ,"+"\n");
		fileWriter1 = new FileWriter("/Users/admin/Downloads/"+ paymentMode+"_"+currentDate+ "_passed_FullPrice.csv");
		
		
		BufferedWriter writer1 = new BufferedWriter(fileWriter1);
		fileWriter1.write(" message  ,"+"country ,"+"sku ,"+"EV price ,"+"sheet price ,"+"\n");
		
		
		//System.out.println(getapikey());
		//data.getapikey();

for (int i = 1; i < Country().length; i++) {

//int i=3;
	
		String url = "https://rest-"
				+ env
				+ "-shahid.evergent.com/shahid/getProductsAndPaymentModes";
    	
        String body = "{\"GetProductsAndPaymentModesRequestMessage\": {\"channelPartnerID\": \"SHAHID\",\"apiKey\": \""
        		+ getapikey()
        		+ "\",\"groupByParameter\": \"paymentType\",\"paymentMode\": \""
        		+ paymentMode
        		+ "\",\"country\": \""+ Country()[i][0]+ "\"}}";
        		
        		
        
       
        
       apivalues(url,body,Country()[i][2],Country()[i][3],Country()[i][4],Country()[i][5],Country()[i][6],Country()[i][7],Country()[i][8],Country()[i][9],Country()[i][10]);
     
         
         
}
fileWriter.close();
fileWriter1.close();




	}
		   
		 
	
	
	
	
	
public static JsonNode apivalues (String url, String body,String country,String vipm,String vipy,String geam,String geay,String sportm,String sporty,String ultimatem,String ultimatey) throws Exception {
	
		String jsonString = APIrequest(url,body);
		
		
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonString);
        JsonNode sku = null;
JsonNode promotionsResponseMessageArrayNode = rootNode.findPath("productDetailsList");

if (promotionsResponseMessageArrayNode.isEmpty()) {
	System.out.println("not configured by EV ,"+country+","+""+","+ ""+","+""+","+"\n");
	fileWriter.write("not configured by EV ,"+country+","+"\n");
	
}
        
        for(int i = 0;i<promotionsResponseMessageArrayNode.size();i++) {
        	 JsonNode promotionObjectNode = promotionsResponseMessageArrayNode.get(i);
             
             String skuapi = promotionObjectNode.get("serviceID").toString();
             if (skuapi.equals("\""
             		+ country
             		+ "_B2C_SHAHID_VIP_1M\"")) {
             
             	 sku=promotionObjectNode;
             	String vip1m = sku.get("price").toString();
             	String vipcurrency = sku.get("currency").toString().replace("\"", "");;
             	
             	String vippricecurrency=vip1m+" "+vipcurrency;
             	String vippricecurrency1=vipcurrency+" "+vip1m;
             	
             	if(vippricecurrency1.equals(vipm.replace("$", "USD"))||vippricecurrency.equals(vipm.replace("$", "USD"))) {
             		System.out.println("passed"+skuapi);
             		fileWriter1.write("passed ,"+country+","+skuapi+","+ vippricecurrency+","+vipm+","+"\n"); 
             	}
             	
             	else {
             		System.out.println("failed"+skuapi+vipcurrency+""+vipm);
             		fileWriter.write("wrong price ,"+country+","+skuapi+","+ vippricecurrency+","+vipm+","+"\n");             	}
             }
             
             if (skuapi.equals("\""
             		+ country
             		+ "_B2C_SHAHID_VIP_12M\"")) {
            
              	 sku=promotionObjectNode;
              	String vip12m = sku.get("price").toString();
                String vip12mcurrency = sku.get("currency").toString().replace("\"", "");;
             	
             	String vippricecurrency=vip12m+" "+vip12mcurrency;
             	String vippricecurrency1=vip12mcurrency+" "+vip12m;
              	if(vippricecurrency.equals(vipy.replace("$", "USD"))||vippricecurrency1.equals(vipy.replace("$", "USD"))) {
              		System.out.println("passed"+skuapi);
              		fileWriter1.write("passed ,"+country+","+skuapi+","+ vippricecurrency+","+vipy+","+"\n"); 
              	}
              	
              	else {
             		System.out.println("failed"+skuapi);
             		fileWriter.write("wrong price ,"+country+","+skuapi+","+ vippricecurrency+","+vipy+","+"\n");             	}
              }
             if (skuapi.equals("\""
             		+ country
             		+ "_B2C_SHAHID_VIP_GEA_1M\"")) {
              
               	 sku=promotionObjectNode;
               	String gea1m = sku.get("price").toString();
                 String gea1mcurrency = sku.get("currency").toString().replace("\"", "");;
             	
             	String vippricecurrency=gea1m+" "+gea1mcurrency;
             	String vippricecurrency1=gea1mcurrency+" "+gea1m;
               	if(vippricecurrency.equals(geam.replace("$", "USD"))||vippricecurrency1.equals(geam.replace("$", "USD"))) {
               		System.out.println("passed"+skuapi);
               		fileWriter1.write("passed ,"+country+","+skuapi+","+ vippricecurrency+","+geam+","+"\n");
               	}
               	else {
             		System.out.println("failed"+skuapi);
             		fileWriter.write("wrong price ,"+country+","+skuapi+","+ vippricecurrency+","+geam+","+"\n");
             	}
               
               }
             if (skuapi.equals("\""
             		+ country
             		+ "_B2C_SHAHID_VIP_GEA_12M\"")) {
                
                	 sku=promotionObjectNode;
                	String gea12m = sku.get("price").toString();
                	String gea12mcurrency = sku.get("currency").toString().replace("\"", "");;
                 	
                 	String geapricecurrency=gea12m+" "+gea12mcurrency;
                 	String gea1mcurrency1=gea12mcurrency+" "+gea12m;
                	if(geapricecurrency.equals(geay.replace("$", "USD"))||gea1mcurrency1.equals(geay.replace("$", "USD"))) {
                		System.out.println("passed"+skuapi);
                		fileWriter1.write("passed ,"+country+","+skuapi+","+ geapricecurrency+","+geay+","+"\n");
                	}
                	else {
                 		System.out.println("failed"+skuapi);
                 		fileWriter.write("wrong price ,"+country+","+skuapi+","+ geapricecurrency+","+geay+","+"\n");
                 	}
                	
                }
             if (skuapi.equals("\""
             		+ country
             		+ "_B2C_SHAHID_VIP_SPORT_1M\"")) {
                	
                	 sku=promotionObjectNode;
                	String sport1m = sku.get("price").toString();
                     String sport1mcurrency = sku.get("currency").toString().replace("\"", "");;
                 	
                 	String sportpricecurrency=sport1m+" "+sport1mcurrency;
                 	String sportpricecurrency1=sport1mcurrency+" "+sport1m;
                	if(sportpricecurrency.equals(sportm.replace("$", "USD"))||sportpricecurrency1.equals(sportm.replace("$", "USD"))) {
                		System.out.println("passed"+skuapi);
                		fileWriter1.write("passed ,"+country+","+skuapi+","+ sportpricecurrency+","+sportm+","+"\n");
                	}
                	else {
                 		System.out.println("failed"+skuapi);
                 		fileWriter.write("wrong price ,"+country+","+skuapi+","+ sportpricecurrency+","+sportm+","+"\n");
                 	}
                	
                }
              if (skuapi.equals("\""
              		+ country
              		+ "_B2C_SHAHID_VIP_SPORT_12M\"")) {
                
                 	 sku=promotionObjectNode;
                 	String sport12m = sku.get("price").toString();
                    String sport12mcurrency = sku.get("currency").toString().replace("\"", "");;
                 	
                 	String sportpricecurrency=sport12m+" "+sport12mcurrency;
                 	String sportpricecurrency1=sport12mcurrency+" "+sport12m;
                 	if(sportpricecurrency.equals(sporty.replace("$", "USD"))||sportpricecurrency1.equals(sporty.replace("$", "USD"))) {
                 		System.out.println("passed"+skuapi);
                 		fileWriter1.write("passed ,"+country+","+skuapi+","+ sportpricecurrency+","+sporty+","+"\n");
                 	}
                 	else {
                 		System.out.println("failed"+skuapi);
                 		fileWriter.write("wrong price ,"+country+","+skuapi+","+ sportpricecurrency+","+sporty+","+"\n");
                 	}
                 	
                 }
              if (skuapi.equals("\""
                		+ country
                		+ "_B2C_SHAHID_VIP_GEA_SPORT_1M\"")) {
                   
                   	 sku=promotionObjectNode;
                   	String ultimate1m = sku.get("price").toString();
                     String ultimate1mcurrency = sku.get("currency").toString().replace("\"", "");;
                 	
                 	String ultimatepricecurrency=ultimate1m+" "+ultimate1mcurrency;
                 	String ultimatepricecurrency1=ultimate1mcurrency+" "+ultimate1m;
                   	if(ultimatepricecurrency.equals(ultimatem.replace("$", "USD"))||ultimatepricecurrency1.equals(ultimatem.replace("$", "USD"))) {
                   		System.out.println("passed"+skuapi);
                   		fileWriter1.write("passed ,"+country+","+skuapi+","+ ultimatepricecurrency+","+ultimatem+","+"\n");
                   	}
                   	else {
                 		System.out.println("failed"+skuapi);
                 		fileWriter.write("wrong price ,"+country+","+skuapi+","+ ultimatepricecurrency+","+ultimatem+","+"\n");
                 	}
                   	
                   }
              if (skuapi.equals("\""
              		+ country
              		+ "_B2C_SHAHID_VIP_GEA_SPORT_12M\"")) {
               
                 	 sku=promotionObjectNode;
                 	String ultimate12m = sku.get("price").toString();
                   String ultimate12mcurrency = sku.get("currency").toString().replace("\"", "");;
                 	
                 	String ultimatepricecurrency=ultimate12m+" "+ultimate12mcurrency;
                 	String ultimatepricecurrency1=ultimate12mcurrency+" "+ultimate12m;
                 	if(ultimatepricecurrency.equals(ultimatey.replace("$", "USD"))||ultimatepricecurrency1.equals(ultimatey.replace("$", "USD"))) {
                 		System.out.println("passed"+skuapi);
                 		fileWriter1.write("passed ,"+country+","+skuapi+","+ultimatepricecurrency+","+ ultimatey+","+"\n");
                 	}
                 	else {
                 		System.out.println("failed"+skuapi);
                 		fileWriter.write("wrong price ,"+country+","+skuapi+","+ultimatepricecurrency+","+ ultimatey+","+"\n");
                 	}
                 	
                 }
        	
        }
        
     
		return sku;
    
		
		
	}
	
		    
	public static String[][] Country()throws Exception   {
		Reader reader = new FileReader("/Users/admin/Documents/pricetest/" + paymentMode + ".csv");
		CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

		List<CSVRecord> records = csvParser.getRecords();
		String[][] array = new String[records.size()][];
		int numRows = 0;

		for (int i = 0; i < records.size(); i++) {
		    CSVRecord record = records.get(i);
		    String[] row = new String[record.size()];
		    boolean isEmpty = true;
		    for (int j = 0; j < record.size(); j++) {
		        row[j] = record.get(j);
		        if (!row[j].isEmpty()) {
		            isEmpty = false;
		        }
		    }
		    if (!isEmpty) {
		        array[numRows++] = row;
		    }
		}

		return Arrays.copyOfRange(array, 0, numRows);
		

	}

		 
		        
	public static String APIrequest(String urlString, String body)throws Exception   {
        URL obj = new URL(urlString);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // Set request method
        con.setRequestMethod("POST");

        // Set request properties
        con.setRequestProperty("Content-Type", "application/json");

        // Set request body
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
	
	

  
		        


