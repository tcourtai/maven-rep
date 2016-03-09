package org.tcourtai.friends2go.hello;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ExtractorSpirit extends Extractor {
	private String html;
	private final Company company = Company.SPIRIT;
	
	public ExtractorSpirit(FlightInfo fi) {
		super(fi);
	}
	
	public void start(){
		queryToHtml();
		parse();
	};
	
	public void queryToHtml() {
		html = "";
		System.out.println("Searching for ...." + flightInfo.toString());
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
					  .body("ADT=1&CHD=0&INF=0&awardFSNumber=&birthdates=03%2F02%2F2016&bookingType=F&departDate="+ getDateToHtml(flightInfo.getDate()) +"&departDateDisplay="+ getDateToHtml(flightInfo.getDate()) +"&from="+ flightInfo.getFromCode() + "&promoCode=&returnDate=03%2F16%2F2016&returnDateDisplay=03%2F16%2F2016&to=" + flightInfo.getToCode() + "&tripType=oneWay")
					  .asString();
	
				
			response = Unirest.get("https://www.spirit.com/DPPCalendarMarket.aspx").asString();
			//System.out.println(response.getBody().toString());
			html = response.getBody().toString();			
			
		} catch (UnirestException e) {
			e.printStackTrace();
			System.out.println(msgNoResult(company));
		}
				
	}
	
	
public boolean parse(){
		
		Document doc = Jsoup.parse(html);

		//from	= doc.select("tr.departureInfo1").first().text();
		//to		= doc.select("tr.arrivalInfo1").first().text();
		//fligthDate	= doc.select("div.date").first().text();
		
		try {
		Element tbody = doc.select("tbody.sortThisTable").first();
		Elements lstflights = tbody.select("tr.rowsMarket1");

		for (Element flight : lstflights){
			Flight f = new Flight(flightInfo);

			String dep = flight.select("td.depart").first().text();
			f.setDeparture(dep.substring(18, dep.length()));
			String arr = flight.select("td.arrive").first().text();
			f.setArrival(arr.substring(18, arr.length()));			
			
			f.setPrice(flight.select("em.emPrice").first().text());

			f.setFrom(doc.select("tr.departureInfo1").first().text());
			f.setTo(doc.select("tr.arrivalInfo1").first().text());
			f.setDate(flightInfo.getDate());
			
			f.setCompany(company);
			//f.setFlightType(flightInfo.getFlightType());
			
			flights.add(f);
			//System.out.println(f.toString());
			
		}
		   		
		return true;
				
		}
		
		catch (Exception ex) {
			System.out.println(msgNoResult(company));
		}
		return false;
	}
}
