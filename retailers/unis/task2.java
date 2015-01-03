import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashMap;

@SuppressWarnings("serial")
class ProductNotFound extends Exception{
	String name;
	public ProductNotFound(Elements article) {
		// TODO Auto-generated constructor stub
		printStackTrace();
	}
}




public class task2 {

	public static void main(String[] args) throws IOException,ProductNotFound {
		System.out.println("Enter the url :: ");
		BufferedReader b = new BufferedReader (new InputStreamReader(System.in));
	    String url = b.readLine();
	    HashMap hm = new HashMap();
	    try{
	    	Document doc = Jsoup.connect(url).get();
	    	Elements Article = doc.select("article[class=product-details]");
	    	if(Article.isEmpty()==true){
	    		throw new ProductNotFound(Article);
	    	}
	    	
	    	for(Element a : Article){
	    		Elements name = a.select("h1");
	    		Elements product_price = a.select("h2");
	    		Elements price_org = a.select("del");
	    		Elements price_offer = a.select(".new");
	    		Elements images = a.select("img[src]");
	    		Elements product_color = a.select("option[value]");
	    		Elements desc = a.select("div[class=description rte]");
	    		Elements desc1 = desc.select("p");
	    		Elements desc2 = desc.select("ul");
	    		
	    		//Printing Title
	    		hm.put("title",doc.title());
	    		System.out.println("Title of product :: " + hm.get("title"));
	    		//Name of product
	    		hm.put("name",name.text());
	    		System.out.println("Name of product :: " + hm.get("name"));
	    		
	    	
	    		//Price of product with if else to check product is in offer or not and print accordingly
	    		if(price_org.isEmpty() == false || price_offer.isEmpty() == false ){
	    			System.out.println("The product is in Offer");
	    			for(Element p0 : price_org){
	    				hm.put("Original_price", p0.text());
	    				System.out.println("Original price of product :: " + hm.get("Original_price"));
	    				
	    			}
	    			for(Element p1 : price_offer){
	    				hm.put("Price",p1.text());
	    				System.out.println("Offer price of product :: " + hm.get("Price"));
	    				
	    			}
	    		}
	    		else 
	    		{
	    			for(Element n : product_price){
	    				hm.put("Price",n.text());
	    				System.out.println("Price of the product :: " + hm.get("Price"));
	    				
	    			}
	    			}
	    		
	    		//color and Size of Product no saperate tags are used for storing size and color 
	    		System.out.print("Colors/Size of Product :: ");
	    		for(Element n : product_color ){
	    			hm.put("color/size",n.text());
	    			System.out.print(hm.get("color/size") + "  ");
	    			} 
	    			System.out.println("  ");
	    		
	    		for(Element el : images) {
	    			hm.put("img_url","http:" + el.attr("src"));
	                System.out.println("Image Url of Product :: " + hm.get("img_url"));
	              	}

	        //description
	    	
	    			hm.put("description", desc1.text() + "\n\t\t" + desc2.text() );
	    			System.out.println("Product Description : " + hm.get("description"));
	    		
	    	
	    		}	   	
	       }
	    catch (IOException e) {
            e.printStackTrace();
        }
	    
	    
	   
	    }

	}

