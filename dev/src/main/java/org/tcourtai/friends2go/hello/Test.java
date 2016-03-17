package org.tcourtai.friends2go.hello;

import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub


		ExtractorAmericanAirlines eu = new ExtractorAmericanAirlines(new FlightInfo("TEST","ORD", "DFW", "2016-05-01", FlightType.DEP_A));
		eu.start();
		//Extractor.extractCode("Dallas/Ft. Worth (DFW)");
		//Extractor.extractCode("AAA");
		//System.out.println(eu.flights.toString());
		
		//eu.generateAirportList();
		/*
		String url = "https://www.united.com/ual/fr/fr/flight-search/book-a-flight/results/rev?f=DFW&t=NYC&d=2016-03-13&tt=1&sc=7&px=1&taxng=1&idx=1";
		HttpResponse<String> response;
		try {
			response = Unirest.get(url).asString();
	
				
			//System.out.println(response.getBody().toString());
			String html = response.getBody().toString();	
		
			List<String> lst = new ArrayList<>();
			lst.add(html);
			
			Path file = Paths.get("C:\\Users\\tcour\\Documents\\unitedv2.html");
			try {
				Files.write(file, lst, Charset.forName("UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	*/
	}
	

}
