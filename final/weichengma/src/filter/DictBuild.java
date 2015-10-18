package filter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class DictBuild {
	public static void build(String inputPath, String outputPath) throws IOException {
		BufferedReader fin=new BufferedReader(new FileReader(new File(inputPath)));
		BufferedWriter fout=new BufferedWriter(new FileWriter(new File(outputPath)));
		String line;
		while((line=fin.readLine())!=null) {
			String[] tokens=line.split(",");
			if(tokens.length<2) {
				continue;
			}
			fout.write(tokens[0]);
			fout.newLine();
		}
		fout.close();
		fin.close();
	}
}