package hello;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;



public class Gizmo {

    private String departure;
    private String arrival;
	
    public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
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
						  .body("ADT=1&CHD=0&INF=0&awardFSNumber=&birthdates=03%2F02%2F2016&bookingType=F&departDate=03%2F17%2F2016&departDateDisplay=03%2F17%2F2016&from=DFW&promoCode=&returnDate=03%2F16%2F2016&returnDateDisplay=03%2F16%2F2016&to=LGA&tripType=oneWay")
						  .asString();
		
					
				response = Unirest.get("https://www.spirit.com/DPPCalendarMarket.aspx").asString();
				
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
   
}
