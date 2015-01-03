package com.mkyong;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
//import java.util.Map.Entry;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;

import com.mkyong.productprint;
public class productinfo {
	public static void main(String[] args)throws IOException{
	
		HashMap<String,productprint> hash1 = new HashMap<String,productprint>();
		System.out.println("Enter the url to know product details :");
		BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
		String i =b.readLine();
		
		hash1.put("INFO", new productprint(i));
		
		try{
			productprint var=hash1.get("INFO");
			var.check();
			var.printtitle();
			var.printdescription();
			var.printprice();
			var.printimgurl();
			var.printcategory();
			var.printcolour();
			var.printsize();
			}
		catch(NullPointerException np){
			System.out.println("there is no product data");
		}
}
}