import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;




/* Website url : www.unisnewyork.com
 * 5 methods are incomplete which returns null
 * maybe isAvailable will be un acceptable
 * 
 * */
public class Scrape {
	//Page Description
	static int getPageType(Document document){
		Document doc = document;		
		Elements Article = doc.select("article[class=product-details]");
		Elements Article_mul = doc.select("article[class=product]");
		if(!Article.isEmpty()){
			System.out.println(":::::::The url is a Product page:::::::");
			return 1;
		}
		else if(!Article_mul.isEmpty()){
			System.out.println("::::::::The url is a Listing page:::::::: ");
			return 0;
		}
		else{
			System.out.println("::::::::Dont care condition::::::::");
			return -1;
		}
	}

	//To fetch product urls from listing page
	public static ArrayList<String> getProductUrls(Document doc){
		Elements url = doc.select("article[class=product]").select("a[class=img-wrap]");
		//System.out.println(url);
		ArrayList<String> s = new ArrayList<String>();
		for(Element e : url){
			System.out.println("Product url is :: " +"http://unisnewyork.com" + e.attr("href"));
			s.add("http://unisnewyork.com" + e.attr("href"));
		}
		return s;
	}


	//To extract cateogry of the listing page
	public static ArrayList<String> extractCategory(Document doc){
		Elements cateogry = doc.select("h1[class=text-center]");
		ArrayList<String> c = new ArrayList<String>();
		System.out.println("Cateogry is :: " + cateogry.text());
		c.add(cateogry.text());
		return c;
	}


	//Reatiler name
	public static String getRetailerTitle(Document doc){
		Elements R_title = doc.select("div[class=mobileHeader]").select("span[class=logo-unis-dark]");
		System.out.println("Retailer title is :: " + R_title.text());
		return R_title.text();
	}

	//retailer description
	public static String getRetailerDescription(Document doc){
		Elements desc = doc.select("p[class=addr]");
		System.out.println("Retailer desc is :: "+desc.text());
		return desc.text();
	}


	//Product Gender
	public String getGender(){
		return null ;
	}

	//Products age group
	public String getAgeGroup(){
		return null ;
	}

	public String getProductType(){
		return null ;
	}

	public String getURL(){
		return null;
	}
	
	public String getProductId(){
		return null;
	}

	public static String getProductName(Document doc){
		Elements n = doc.select("h1");
		System.out.println("Name of product :: " + n.text());
		return n.text();
	}
	//products brand
	public static String getBrand(Document doc){
		Elements Article = doc.select("article[class=product-details]");
		String name = Article.select("h1").text();
		char hifen = '-';
		int dash = name.indexOf(hifen);
		String brand_prod =  name.substring(0,dash);
		System.out.println("Brand of Product :: " + brand_prod  );
		return brand_prod ;
	}

	//to get cateogry of the product in product page
	public static String getCateogry(String url,Document doc){
		Elements ul = doc.select("ul[class=primary col]").select("li").select("a");
		String cat = null;
		String Str = new String(url);

		for(Element c : ul){
			String cmp = c.attr("href");
			if(Str.matches("(.*)"+cmp+"(.*)") == true){
				cat = c.text();
			}
		}
		System.out.println("Cateogry is :: " +cat);
		return cat;

	}

	//to get the currency of the retailer.
	public static String getCurrency(Document doc){
		Elements c = doc.select("meta[itemprop=priceCurrency]");
		String currency = c.attr("content");
		System.out.println("Currency is ::" + currency);
		return currency;
	}


	public static String getSmallDescription(Document doc){
		Elements d = doc.select("meta[name=description]");
		String s_desc = d.attr("content");
		System.out.println("Short desc is ::" + s_desc);
		return s_desc;
	}

	public static String getLongDescription(Document doc){
		Elements desc = doc.select("article[class=product-details]").select("div[class=description rte]");
		Elements desc1 = desc.select("p");
		Elements desc2 = desc.select("ul");
		String description = desc1.text() + desc2.text();
		return description;
	}

	public static ArrayList<String> getImages(Document doc){
		Elements i = doc.select("article[class=mobile-product-details]").select("div[class=image]");
		Elements src1 =i.select("img");
		ArrayList<String> im = new ArrayList<String>();
		for(Element e : src1){
			System.out.println("Image url is :: " + e.attr("src"));
			String sk =e.attr("src");
			im.add(sk);
		}
		System.out.println("Images are" + im);
		return im;
	}

	public static String getTitle(Document doc){
		String title = doc.title();
		System.out.println("Product title is :: " + title);
		return title;
	}

	public static String getGlobalImage(Document doc){
		Elements gi = doc.select("div[class=image]").select("img");
		String im = gi.attr("src");
		System.out.println("Global image is : "+im);
	return im;
	}


	public static String getPrice(Document doc){
		Elements prod_price =doc.select("article[class=product-details]").select("h2");
		String price = prod_price.text();
		System.out.println("Price of Product :: " + price);
		return price;
	}

	public static String getDiscountPrice(Document doc){
		String disc =doc.select("article[class=product-details]").select(".new").text();
		if(!disc.isEmpty()){
			return disc;
		}
		else
			return "NO Discount for this Product";
	}

	public static boolean isAvailable(Document doc){
		Elements Article = doc.select("article[class=product-details]");
		Elements Article_mul = doc.select("article[class=product]");
		if(!Article.isEmpty() || !Article_mul.isEmpty()){
			return true;
		}
		else
			return false;
	}

	


	public static void main(String[] args) throws IOException {
		System.out.println("Enter the url :: ");
		BufferedReader b = new BufferedReader (new InputStreamReader(System.in));
		String url = b.readLine();

		try{
			Document doc = Jsoup.connect(url).get();
			int s = getPageType(doc);
			getRetailerTitle(doc);
			getRetailerDescription(doc);
			switch(s){
			case 0 :
				extractCategory(doc);
				getProductUrls(doc);
				break;
			case 1 :
				getProductName(doc);
				getPrice(doc);
				getDiscountPrice(doc);
				isAvailable(doc);
				getBrand(doc);
				getImages(doc);
				getCurrency(doc);
				getCateogry(url,doc);
				getSmallDescription(doc);
				break;
			case -1 :
				System.out.println("Sorry no product found");
				break;
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}
