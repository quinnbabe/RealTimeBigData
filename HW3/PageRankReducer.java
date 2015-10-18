package hw3;
import java.io.IOException;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

public class PageRankReducer
	extends Reducer<Text, Text, Text, Text> {

	@Override
	public void reduce(Text key, Iterable<Text> values, Context context)
		throws IOException, InterruptedException {

		double PR = 0;
		String outlinks="";
		for (Text value : values) {
			String line = value.toString();
			String[] tokens = line.split(" ");
			int len = tokens.length;
			if (!tokens[len-1].matches("[-+]?\\d*\\.?\\d+")){
				outlinks = line;
			} else {
				PR += Double.parseDouble(tokens[len - 1]);
			}
		}
		context.write(key, new Text(outlinks +String.valueOf(PR)));
	}
}