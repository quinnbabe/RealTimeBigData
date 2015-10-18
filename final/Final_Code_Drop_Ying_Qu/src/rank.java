import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class rank {

    /**
     * @param args
     */
    private static final String filePath = "/Users/Quinnbabe/Desktop/final";
    private static final String outPutPath = "/Users/Quinnbabe/Desktop/outputRank.txt";

    static Double totalOccurrence = 0.0;
    static Double maxOccurrence = 0.0;
    static Map<String, Double> map = new HashMap<String, Double>();
    static Map<String, Double> map2 = new HashMap<String, Double>();

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        File writefile=new  File(outPutPath);
        try {
            BufferedReader br=new BufferedReader(new FileReader(filePath));
            String line="";

            while((line=br.readLine())!=null){
                String[] metaData = line.split(",");
                String name = metaData[0].replaceAll(" ", "");
                Double zagat = Double.parseDouble(metaData[1].replaceAll(" ", ""));
                Double yelp = Double.parseDouble(metaData[2].replaceAll(" ", ""));
                Double facebook = Double.parseDouble(metaData[3].replaceAll(" ", ""));
                Double occurrence = Double.parseDouble(metaData[4].replaceAll(" ", ""));
                Double tempScore = 0.0;

                if(maxOccurrence < occurrence ){
                    maxOccurrence = occurrence;
                }
                if(zagat==0.0 && yelp==0.0 && facebook>0.0){
                    tempScore = facebook * 0.9 + occurrence/maxOccurrence*100.0*0.1;
                } else if(yelp==0.0 && facebook==0.0 && zagat>0.0){
                    tempScore = zagat * 0.9 + occurrence/maxOccurrence*100.0*0.1;
                } else if(zagat==0.0 && facebook==0.0 && yelp>0.0){
                    tempScore = yelp * 0.9 + occurrence/maxOccurrence*100.0*0.1;
                }else if(zagat==0.0 && facebook>0.0 && yelp>0.0){
                    tempScore = facebook * 0.225 + yelp*0.675 + occurrence/maxOccurrence*100*0.1;
                }else if(yelp==0.0 && facebook>0.0 && zagat>0.0){
                    tempScore = facebook * 0.3 + zagat*0.6 + occurrence/maxOccurrence*100*0.1;
                }
                else if(facebook==0.0 && yelp>0.0 && zagat>0.0){
                    tempScore = yelp * 0.54 + zagat*0.36 + occurrence/maxOccurrence*100*0.1;
                }
                else if(facebook==0.0 && yelp==0.0 && zagat==0.0){
                    tempScore = occurrence/maxOccurrence*100*0.1;
                }
                else {
                    tempScore = zagat * 0.3 + yelp * 0.45+ facebook*0.15 + occurrence/maxOccurrence*100*0.1;
                    //zagat:yelp:facebook=2:3:1
                }
                totalOccurrence = totalOccurrence + occurrence;
                map.put(name, tempScore);
                map2.put(name, occurrence);
            }

            List<Map.Entry<String, Double>> infoIds =
                    new ArrayList<Map.Entry<String, Double>>(map.entrySet());

            Collections.sort(infoIds, new Comparator<Map.Entry<String, Double>>() {
                public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                    Double o2Value = o2.getValue()+(map2.get(o2.getKey())/maxOccurrence)*100.0*0.1;
                    Double o1Value = o1.getValue()+(map2.get(o1.getKey())/maxOccurrence)*100.0*0.1;
                    return o2.getValue().compareTo(o1.getValue());
                }
            });


            boolean b =writefile.createNewFile();
            FileWriter outputfile=new FileWriter(writefile);
            BufferedWriter bw = new BufferedWriter(outputfile);

            System.out.print("Rank     Name     Score\n");
            for (int i = 0; i < infoIds.size(); i++) {
                String name = infoIds.get(i).toString().split("=")[0];
                String finalScore = infoIds.get(i).toString().split("=")[1];

                bw.write((i+1)+" "+name+" "+finalScore+"\n");
                bw.flush();
                System.out.print((i+1)+" "+name+" "+finalScore+"\n");

            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
