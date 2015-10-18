import java.io.IOException;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class PageRankMapper
	extends Mapper<LongWritable, Text, Text, Text> {
	
	private static final int MISSING = 9999;

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		String line = value.toString();
		String[] tokens = line.split("[ \t]");
		String outlinks = "";
		
		int len = tokens.length;

		System.out.println(tokens[len-1]);

		double PR = Double.parseDouble(tokens[len - 1]) / (len - 2);
		for (int i = 1; i < len - 1; ++i){
		    outlinks += tokens[i]+" ";
		    context.write(new Text(tokens[i]), new Text(tokens[0]+" "+String.valueOf(PR)));
		}
		context.write(new Text(tokens[0]), new Text(outlinks));
	}
}
