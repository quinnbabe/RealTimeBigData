package twitter;


// cc MaxTemperatureReducer Reducer for maximum temperature example
// vv MaxTemperatureReducer
import java.io.IOException;

import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class FilterDatasetReducer
  extends Reducer<Text, IntWritable, Text, Text> {
  
  @Override
  /**
   * calculate the average score of each restaurant
   */
  public void reduce(Text key, Iterable<IntWritable> values,
      Context context)
      throws IOException, InterruptedException {
    
    int sum = 0;
    for (IntWritable value : values) {
      sum += value.get();

    }
    context.write(key, new Text(String.valueOf(sum)));
  }
}

