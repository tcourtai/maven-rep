package hello;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;



public class Gizmo {

    private String from;
    private String to;
    private Flights lstFlight = new Flights();
    
	
    public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	
	public boolean start() {
		System.out.println("lets go!");

			HttpResponse<String> response;
			try {
				response = Unirest.post("https://www.spirit.com/Default.aspx?action=search")
						  .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						  .header("origin", "https//www.spirit.com")
						  .header("upgrade-insecure-requests", "1")
						  .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
						  .header("content-type", "application/x-www-form-urlencoded")
						  .header("referer", "https//www.spirit.com/Default.aspx")
						  .header("accept-encoding", "gzip, deflate")
						  .header("accept-language", "fr,en-US;q=0.8,en;q=0.6")
						  .header("cache-control", "no-cache")
						  .header("postman-token", "33364678-ae74-1ba4-f6db-ed50b04510eb")
						  .body("ADT=1&CHD=0&INF=0&awardFSNumber=&birthdates=03%2F02%2F2016&bookingType=F&departDate=03%2F17%2F2016&departDateDisplay=03%2F17%2F2016&from="+ from + "&promoCode=&returnDate=03%2F16%2F2016&returnDateDisplay=03%2F16%2F2016&to=" + to + "&tripType=oneWay")
						  .asString();
		
					
				response = Unirest.get("https://www.spirit.com/DPPCalendarMarket.aspx").asString();
				System.out.println(response.getBody().toString());
				parseResult(response.getBody().toString());
				
				try(  PrintWriter out = new PrintWriter( "C:\\Users\\tcour\\Documents\\filename.txt" )  ){
	            out.println( response.getBody().toString());
				} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
				
			} catch (UnirestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
					
		
		System.out.println("finished");
		return true;
	}
   
	public boolean parseResult(String html){
		
		String date = "";
		
		/*
		String html = "";
		try {
			html = readFile("C:\\Users\\tcour\\Documents\\filename.txt", Charset.defaultCharset());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
		Document doc = Jsoup.parse(html);

		//from	= doc.select("tr.departureInfo1").first().text();
		//to		= doc.select("tr.arrivalInfo1").first().text();
		date	= doc.select("div.date").first().text();
				
		Element tbody = doc.select("tbody.sortThisTable").first();
		Elements flights = tbody.select("tr.rowsMarket1");

		for (Element flight : flights){
			Flight f = new Flight();

			String dep = flight.select("td.depart").first().text();
			f.setDeparture(dep.substring(18, dep.length()));
			String arr = flight.select("td.arrive").first().text();
			f.setArrival(arr.substring(18, arr.length()));			
			
			f.setPrice(flight.select("em.emPrice").first().text());

			f.setFrom(from);
			f.setTo(to);
			f.setDate(date);
			
			lstFlight.add(f);
			System.out.println(f.toString());
			
		}
		   		
		return true;
	}
	
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
	
	public String toHtml() {
		return lstFlight.toHtml();
		
	}
}
