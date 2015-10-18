package test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Vector;

import filter.DictBuild;
import filter.RestaurantDict;

public class TestMain {
	public static void main(String[] args) throws IOException {
		String inputDataRoot="src/input/";
		String dictPath="src/dict/rest.dict";
		String sourcePath="src/source/twitter.txt";
		String destPath="src/dest/twitter.txt";
		RestaurantDict dict=RestaurantDict.getInstance();
		dict.load("");
		File[] files=new File(inputDataRoot).listFiles();
		for(File file : files) {
			System.out.println(file.getAbsolutePath());
			DictBuild.build(file.getAbsolutePath(), dictPath);
			dict.add(dictPath);
		}
		BufferedReader fin=new BufferedReader(new FileReader(new File(sourcePath)));
		BufferedWriter fout=new BufferedWriter(new FileWriter(new File(destPath)));
		String line;
		while((line=fin.readLine())!=null) {
			line=fin.readLine();
			if(line==null) {
				break;
			}
			String[] tokens=line.split("\\s+");
			for(int i=0;i<tokens.length;i++) {
				if(dict.search(tokens[i])!=-1) {
					fout.write(tokens[i]+"&"+line);
					fout.newLine();
					break;
				}
			}
		}
		fout.close();
		fin.close();
		System.out.println(dict.getDictSize());
		Vector<Double> YelpScore=new Vector<Double>(dict.getDictSize());
		Vector<Double> ZagatScore=new Vector<Double>(dict.getDictSize());
		Vector<Double> FBScore=new Vector<Double>(dict.getDictSize());
		Vector<Integer> YelpCount=new Vector<Integer>(dict.getDictSize());
		Vector<Integer> ZagatCount=new Vector<Integer>(dict.getDictSize());
		Vector<Integer> FBCount=new Vector<Integer>(dict.getDictSize());
		for(int i=0;i<dict.getDictSize();i++) {
			YelpScore.add(0.0);
			ZagatScore.add(0.0);
			FBScore.add(0.0);
			YelpCount.add(0);
			ZagatCount.add(0);
			FBCount.add(0);
		}
		BufferedReader YelpIn=new BufferedReader(new FileReader(new File("src/input/yelp")));
		BufferedReader ZagatIn=new BufferedReader(new FileReader(new File("src/input/zagat")));
		BufferedReader FBIn=new BufferedReader(new FileReader(new File("src/input/facebook_out")));
		while((line=YelpIn.readLine())!=null) {
			String[] tokens=line.split(",");
			int ind;
			if((ind=dict.search(tokens[0]))==-1) {
				continue;
			}
			YelpScore.set(ind,Double.parseDouble(tokens[1]));
			YelpCount.set(ind,Integer.parseInt(tokens[2]));
		}
		while((line=ZagatIn.readLine())!=null) {
			String[] tokens=line.split(",");
			int ind;
			if((ind=dict.search(tokens[0]))==-1) {
				continue;
			}
			ZagatScore.set(ind,Double.parseDouble(tokens[1]));
			ZagatCount.set(ind,Integer.parseInt(tokens[2]));
		}
		while((line=FBIn.readLine())!=null) {
			String[] tokens=line.split(",");
			int ind;
			if((ind=dict.search(tokens[0]))==-1) {
				continue;
			}
			FBScore.set(ind,Double.parseDouble(tokens[1]));
			FBCount.set(ind,Integer.parseInt(tokens[2]));
		}
		FBIn.close();
		ZagatIn.close();
		YelpIn.close();
		BufferedWriter finalOut=new BufferedWriter(new FileWriter(new File("src/dest/final")));
		for(int i=0;i<dict.getDictSize();i++) {
			finalOut.write(dict.search(i)+","
		+YelpScore.get(i)+","
		+ZagatScore.get(i)+","
		+FBScore.get(i)+","
		+(YelpCount.get(i)+ZagatCount.get(i)+FBCount.get(i)));
			finalOut.newLine();
		}
		finalOut.close();
	}
}