package zagat_yelp_facebook;


// cc MaxTemperatureReducer Reducer for maximum temperature example
// vv MaxTemperatureReducer
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FilterDatasetReducer
  extends Reducer<Text, DoubleWritable, Text, Text> {
  
  @Override
  /**
   * calculate the average score of each restaurant
   */
  public void reduce(Text key, Iterable<DoubleWritable> values,
      Context context)
      throws IOException, InterruptedException {
    
    double sum = 0.;
    int count=0;
    for (DoubleWritable value : values) {
      sum += value.get();
      count++;

    }
    double avg = sum/count;
    String outputline = String.valueOf(avg)+","+String.valueOf(count);
    context.write(key, new Text(outputline));
  }
}

