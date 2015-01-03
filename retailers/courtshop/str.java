package com.mkyong;

public class str {
	
	String getproductname(String heading){
		String t = heading;
		int x= t.indexOf("/");
		if (x>=0){
					return t.substring(0,x);
					}			
		else
		{
			return t;
		}
	}
}
		
	


