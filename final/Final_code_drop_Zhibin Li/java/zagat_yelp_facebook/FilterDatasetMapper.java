package zagat_yelp_facebook;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FilterDatasetMapper
  extends Mapper<LongWritable, Text, Text, DoubleWritable> {

  private static final double MISSING = -9999;
  
  @Override
  /**
   * filter the data set, output key/value as name of restaurant/score
   */
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  
    String line = value.toString();
    String[] tokens = line.split(",");
    String name = tokens[0].trim();
    String cuisine = tokens[1].trim().toLowerCase();
    String scorestring = tokens[2].trim();
    double score = MISSING;
    if(!scorestring.isEmpty()){
    	try{
    		score = Double.parseDouble(scorestring);
    	}catch (Exception e){
    		score = MISSING;
    	}
    }


    if (cuisine.contains("french") && score != MISSING ) {
    	System.out.println(name);
      context.write(new Text(name), new DoubleWritable(score));
    }
  }
}

