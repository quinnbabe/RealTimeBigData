import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class PageRank {

	public static void main(String[] args) throws Exception {
		if (args.length != 2) {
			System.err.println("Usage: PageRank <input path> <output path>");
			System.exit(-1);
		}
		
		String[] ins = new String[3];
		String[] outs = new String[3];
		ins[0] = args[0];
		outs[0] = "/user/cloudera/input/i1";
		ins[1] = "/user/cloudera/input/i1/part-r-00000";
		outs[1] = "/user/cloudera/input/i2";
		ins[2] = "/user/cloudera/input/i2/part-r-00000";
		outs[2] = args[1];
		
		for (int i = 0; i < 3; i++){
			Job job = new Job();
			job.setJarByClass(PageRank.class);
			job.setJobName("PageRank"+i);
	
			FileInputFormat.addInputPath(job, new Path(ins[i]));
			FileOutputFormat.setOutputPath(job, new Path(outs[i]));
	
			job.setMapperClass(PageRankMapper.class);
			job.setReducerClass(PageRankReducer.class);
			
			job.setOutputKeyClass(Text.class);
			job.setOutputValueClass(Text.class);
	
			if ( !job.waitForCompletion(true))
				System.exit(1);
		}
	}
}
