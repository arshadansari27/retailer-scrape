import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class task2 {
    public static void main(String[] args) throws IOException {
        Document doc = null;
        Elements products = null;
        Elements prices;
        Elements category;
        String title;
        Elements co;
        Element frst;
        Elements scnd;
        Elements images = null;
        Elements size;
        Element sfrst;
        Elements sscnd;
        Elements present;
        Element cat;
        Element cat1;
        Elements desc;
        HashMap hm = new HashMap();
        try {
            System.out.println("Enter the url:: ");
            BufferedReader b = new BufferedReader(new InputStreamReader(System.in));
            String url = b.readLine();
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        present = doc.select("#attributeNames");


        // to check if Product details are present
        try {


            if (!present.isEmpty()) {
                for (Element p : present) {
                    System.out.println("Product details  present :\n");
                }
            }
            products = doc.select("h1");
            prices = doc.select("#price");
            co = doc.select("select[name*=att1]");
            size = doc.select("select[name*=att2]");
            frst = co.get(0);
            scnd = frst.select("option");
            sfrst = size.get(0);
            sscnd = sfrst.select("option");
            category = doc.select(".crumbtrail-anchor");
            cat = category.get(0);
            cat1 = category.get(1);
            desc = doc.select("p[class*=tab-invtdesc1]");
            System.out.println(desc.text());
            images = doc.getElementsByTag("img");
            title = doc.title();
            desc = doc.select("p[class=tab-invtdesc1]");
            System.out.println(desc.text());

            //Title
            hm.put("title", title);
            System.out.println("Title :" + hm.get("title"));

            // Category
            hm.put("category", cat.text() + " " + cat1.text());
            System.out.println("Category :" + hm.get("category"));


            // Product
            hm.put("product", products.text());
            System.out.println("Product Name :" + hm.get("product"));

            // Image Source
            System.out.print("Sizes :");
            for (Element sizeOP : sscnd) {
                hm.put("size", sizeOP.attr("value"));
                System.out.println(hm.get("size"));

            }
            System.out.print("Color Option :");
            for (Element colOp : scnd) {
                hm.put("color", colOp.attr("value"));
                System.out.println(hm.get("color"));
            }

            hm.put("price", prices.text());
            System.out.println("Price :" + hm.get("price"));

            System.out.println("Image Found!");
            for (Element el : images) {
                String src = el.absUrl("src");
                hm.put("imgurl", el.attr("src"));
                System.out.println("Image Link : " + hm.get("imgurl"));

            }


        } catch (Exception e1) {
            System.out.println("Product details are not present on this page");
            System.out.println("Exit");
        }


    }
}





