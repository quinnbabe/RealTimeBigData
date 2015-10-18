package twitter;
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class FilterDatasetMapper
  extends Mapper<LongWritable, Text, Text, IntWritable> {

  private static final double MISSING = -9999;
  
  @Override
  /**
   * filter the data set, output key/value as name of restaurant/score
   */
  public void map(LongWritable key, Text value, Context context)
      throws IOException, InterruptedException {
	  
    String line = value.toString();
    String[] tokens = line.split("&");
    String name = tokens[0].trim();


      context.write(new Text(name), new IntWritable(1));
    }
  
}

