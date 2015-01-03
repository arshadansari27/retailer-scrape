package com.mkyong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashMap;

public class Task2 {

	public static void main(String[] args){
			
		Document doc;
		HashMap<String, String> hm = new HashMap<String, String>(); 
		
		try{    //collections/shirts-b/products/patrolshirtteal
			    doc=Jsoup.connect("http://cadetusa.com/collections/shirts-b/products/patrolshirtteal").get();   // Enter urls related to cadetusa.com           
			    String title=doc.title();                                   
			    hm.put("title",title);
				System.out.println("Title of the page         : "+hm.get("title"));   //printing title of current web page
				try{
						Elements category = doc.select("h1[class]");						//extracting category of clothing selected
						hm.put("category",category.text());						
						System.out.println("\nCategory Selected         : "+hm.get("category")+"\n");
						}
				catch(NullPointerException ne){
						System.out.println("\nPlease select a category\n");
				}
			
			   try{
				   		Elements type = doc.select("h4.title");			// Extracting all the choices available 	   		
				   		System.out.println("Types available :\n");
				   		for(Element t : type){
				   			hm.put("type",t.text());
				   		System.out.println("-> "+hm.get("type"));     
				   		}				   		
				   		}
			   catch(NullPointerException ne){
				   		System.out.print("");	
				}
			
			   try{		        
				   		Element proName = doc.select("h1.title").first();    // Extracting selected product's name
				   		hm.put("name",proName.text());
				   		System.out.println("\t\t\tProduct Details\n\nProduct Name              : "+hm.get("name"));			    

				   		Elements size = doc.select("option[data-price]");       // Extracting sizes available
				   		System.out.print("Sizes Available           : ");
				   		for(Element s : size){
				   			hm.put("size", s.text());
				   			System.out.print(hm.get("size")+"\t");
				   		}	   		    

				   		Element desClass = doc.select("div.description").first();   // Extracting description of selected product
			   			hm.put("des",desClass.text());
			   			System.out.println("\nProduct Description       : "+hm.get("des"));
			    
			   			Elements media = doc.select("a[href].cloud-zoom").select("img[src]");      // Extracting url of product image
			   			for (Element src : media){	            
			   					hm.put("media",src.attr("abs:src"));
			   					System.out.println("Product image url         : <"+hm.get("media")+">");
		                }

			   			Elements price = doc.select("h2.price");               // Extracting the price of product selected
			   		    hm.put("price",price.text());
			   		    System.out.println("Price of the Product      : "+hm.get("price"));
			   }
			   catch(NullPointerException ne){
						System.out.print("\nPlease select a product");
			   }
			}
			catch(IOException e){
				System.out.print("Please enter correct url...");
			}
	}
}


