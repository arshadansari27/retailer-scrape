package com.mkyong;
import java.io.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class productprint {
	Document doc = null;
	public productprint(String url){
	String x=url;
	try{
		doc =Jsoup.connect(x).get();
		}
	catch(IOException e){
		e.printStackTrace();
		}
	}

		void check(){
			Element check =doc.getElementById("Product_description_long");
			String x=check.text();
		}
		
		void printtitle(){
			String title =doc.body().getElementsByTag("h1").text();
			str d=new str();
			String title2=d.getproductname(title);
			System.out.println("Title : "+title2+"\n");
			}
		
		void printdescription(){
			Element c =doc.getElementById("Product_description_long");
			System.out.println("\ndescription : "+c.text()+"\n");
			}
		
		void printprice(){// to display product price
			Element price =doc.getElementById("Product_FormattedPrice");
			System.out.println("Price : "+price.text()+"\n");
			}
		
		void printimgurl(){
			Elements img =doc.select("img[src$=.jpg]");
			for(Element k:img){
				if(k.tagName().equals("img")){
					System.out.println("image url :" +k.attr("src"));
					}}}
		
		void printcategory(){
			Element cat =doc.getElementById("breadcrumbs");
			System.out.println("\nCATEGORY : "+cat.text()+"\n");
		}
		
		void printcolour(){
			System.out.println("\nColour : one colour\n");
		}
		
		void printsize(){
			Elements size =doc.select("select[id=SelectSize");
			if(size.isEmpty()==true){
				System.out.println("size : one size");
				}
			for(Element s:size){
					Elements dt=s.select("option[value]");
					for(Element s2:dt){
						System.out.println("Size : "+s2.text());
						}
					}}
}	

