import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

//import com.sun.org.apache.xpath.internal.operations.Bool;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Test {

    //input file
    private static final String filePath =
            "/Users/Quinnbabe/Desktop/yelp_dataset_challenge_academic_dataset/yelp_academic_dataset_business.json";

    //15 attributes considered in the score
    private static final String[] attributes = {
            "Take-out",
            "Takes Reservations",
            "Caters","Delivery",
            "Parking",
            "Has Tv",
            "Music",
            "Outdoor Seating",
            "Accepts Credit Cards",
            "Waiter Service",
            "Good For Kids",
            "Good For Groups",
            "Wheelchair Accessible",
            "Happy Hour",
            "By Appointment Only"
    };

    final static String[] nonBinaryAttributes =
            {
                    "Alcohol",
                    "Attire",
                    "Price Range",
                    "Ages Allowed",
                    "Smoking",
                    "Noise Level",
                    "BYOB/Corkage",
                    "Wi-Fi"
            };

    final static String[] basicFeatures =
            {
                    "business_id",
                    "city",
                    "review_count",
                    "longitude",
                    "latitude",
            };

    //output file
    private static final String outPutPath = "/Users/Quinnbabe/Desktop/yelpOutput.txt";

    public static void main(String[] args) {

        File writefile=new  File(outPutPath);

        try{
            boolean b = writefile.createNewFile();
            FileWriter outputfile = new FileWriter(writefile);
            BufferedWriter bw = new BufferedWriter(outputfile);

            // read the json file
            //to transform the json file into right format
            BufferedReader br=new BufferedReader(new FileReader(filePath));
            String line="";
            StringBuffer  buffer = new StringBuffer();

            while((line=br.readLine())!=null){
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(line);

                // get an array from the JSON object
                JSONArray category= (JSONArray) jsonObject.get("categories");
                JSONObject categoryObject = new JSONObject();
                boolean flagF = false;
                boolean flagR = false;
                // take the elements of the json array
                for(int i=0; i<category.size(); i++){
                    if(category.get(i).equals("French")){
                        flagF = true;
                    }
                    //categoryObject.put(i, category.get(i));
                    //System.out.println("The " + i + " element of the array: "+category.get(i));
                }
                //JSONArray CUISINE = new JSONArray();
                //CUISINE.add(categoryObject);
                if(flagF){
                    // get a String from the JSON object
                    //get the restaurant name
                    String name = (String) jsonObject.get("name");
                    //System.out.println("The name of the restaurant is: " + name);

                    Double star =  (Double) jsonObject.get("stars");
                    if (jsonObject.get("stars") == null ){
                        star = 0.0;
                    }
                    //System.out.println("The star is: " + star);

                    int attributesMark = getAttributesMark(jsonObject);
                    //System.out.println("The attribute’s mark is: " + attributesMark);

                    String state = (String) jsonObject.get("state");
                    String city = (String) jsonObject.get("city");
                    String address = (String) jsonObject.get("full_address");
                    address = address.replaceAll(",", "");
                    //System.out.println("The address is: " + address);

                    long count = (Long)jsonObject.get("review_count");
                    //System.out.println("The count is: " + count);
                    
                    /*
                    long cost =  (Long) jsonObject.get("Price Range");
                    System.out.println("The Price Range is: " + cost);
                    */
                    //Criterion
                    //Both the full mark of the star and the attributesMark is 100.
                    // star counts for 85 percentage， attributesMark counts for 15 percentage
                    Double finalScore = star * 20 * 0.85 + attributesMark *100/15* 0.15;
                    String output = name.replaceAll(",", "")+", "+"French"+", "+finalScore+", "+address.replaceAll("\n", " ");
                    String finalOutput = "";
                    for(int j=0;j<count;j++){
                        finalOutput = finalOutput + output + "\n";
                    }

                    bw.write(finalOutput);
                    bw.flush();
                    System.out.print(finalOutput);
                }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }

    }

    private static int getAttributesMark(JSONObject jsonObject) {
        int mark = 0;

        JSONObject tempObject = (JSONObject) jsonObject.get("attributes");

        for(int i=0;i<attributes.length;i++){
            if(tempObject.containsKey(attributes[i])){
                String temp = tempObject.get(attributes[i]).toString();
                if(!temp.equals("false")){
                    mark++;
                }
            }
        }
        return mark;
    }


}