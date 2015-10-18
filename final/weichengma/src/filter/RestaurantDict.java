package filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class RestaurantDict {
	private static Vector<String> RestaurantList=null;
	private static RestaurantDict dict=null;
	
	private RestaurantDict() {
		RestaurantList=new Vector<String>();
	}
	
	public static RestaurantDict getInstance() {
		if(dict==null) {
			dict=new RestaurantDict();
		}
		return dict;
	}
	
	public void load(String dictPath) throws IOException {
		RestaurantList=new Vector<String>();
		if(!new File(dictPath).exists()) {
			return;
		}
		BufferedReader fin=new BufferedReader(new FileReader(new File(dictPath)));
		String line;
		while((line=fin.readLine())!=null) {
			line=line.toLowerCase();
			if(!RestaurantList.contains(line)) {
				RestaurantList.add(line.toLowerCase());
			}
		}
		fin.close();
	}
	
	public void add(String dictPath) throws IOException {
		if(!new File(dictPath).exists()) {
			return;
		}
		BufferedReader fin=new BufferedReader(new FileReader(new File(dictPath)));
		String line;
		while((line=fin.readLine())!=null) {
			line=line.toLowerCase();
			if(!RestaurantList.contains(line)) {
				RestaurantList.add(line.toLowerCase());
			}
		}
		fin.close();
	}
	
	public int search(String restName) {
		restName=restName.toLowerCase();
		return RestaurantList.indexOf(restName);
	}
	
	public String search(int index) {
		if(index >= RestaurantList.size()) {
			return null;
		}
		return RestaurantList.get(index);
	}
	
	public int getDictSize() {
		return RestaurantList.size();
	}
}