package hw2;
import java.io.IOException;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

public class SearchTweetMapper
	extends Mapper<LongWritable, Text, Text, IntWritable> {
	
	private static final int MISSING = 9999;

	@Override
	public void map(LongWritable key, Text value, Context context)
		throws IOException, InterruptedException {
		
		String line = value.toString();
		int index;
		if ((index = line.indexOf("hackathon")) != -1
				|| (index = line.indexOf("Hackthon")) != -1){
			context.write(new Text("hackathon"), new IntWritable(1));
		} else {
			context.write(new Text("hackathon"), new IntWritable(0));
		}

		if ((index = line.indexOf("Dec")) != -1
				|| (index = line.indexOf("dec")) != -1){
			context.write(new Text("Dec"), new IntWritable(1));
		} else {
			context.write(new Text("Dec"), new IntWritable(0));
		}

		if ((index = line.indexOf("Chicago")) != -1
				|| (index = line.indexOf("chicago")) != -1){
			context.write(new Text("Chicago"), new IntWritable(1));
		} else {
			context.write(new Text("Chicago"), new IntWritable(0));
		}

		if ((index = line.indexOf("Java")) != -1
				|| (index = line.indexOf("java")) != -1){
			context.write(new Text("Java"), new IntWritable(1));
		} else {
			context.write(new Text("Java"), new IntWritable(0));
		}
	}
}