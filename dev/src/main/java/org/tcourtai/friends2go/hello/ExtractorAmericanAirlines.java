package org.tcourtai.friends2go.hello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ExtractorAmericanAirlines extends Extractor{
	
	public ExtractorAmericanAirlines(FlightInfo fi) {
		super(fi);
	}
	
	public void start() {
		queryToHtml();
	}	
	public void queryToHtml() {
		html = "";
		HttpResponse<String> response;
		try {
			String body = "adultPassengerCount=1&adults=1&air_room_car=A&cabinOfServicePreference=matrix-lowest_fare%2Cmatrix-show_all&carrierPreference=T&childPassengerCount=0&classOfServicePreference=coach-restricted&countryPointOfSale=US&currentCalForm=dep" 
							+ "&destinationAirport=" + flightInfo.getToCode()
							+ "&originAirport="  + flightInfo.getFromCode()
							+ "&flightParams.flightDateParams.searchTime=120001&"
							+ "flightParams.flightDateParams.travelDay=" + flightInfo.getDateDay()
							+ "&flightParams.flightDateParams.travelMonth=" + flightInfo.getDateMonth()
							+ "&flightParams.flightDateParams.travelYear=" + flightInfo.getDateYear()
							+ "&flightSearch=revenue&fromSearchPage=true&infantPassengerCount=0&passengerCount=1&returnDate.searchTime=040001&returnDate.travelDay=-1000&returnDate.travelMonth=-1000&rooms=1&searchCategory=false&searchType=fare&seniorPassengerCount=0&serviceclass=coach&tripType=oneWay&un_form_encoding=utf-8&un_jtt_oneWay=true&youngAdultPassengerCount=0";
			System.out.println(body);
			
			response = Unirest.post("https://mobile.aa.com/mt/www.aa.com/reservation/tripSearchSubmit.do")
					.header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
					.header("origin", "https://mobile.aa.com")
					.header("upgrade-insecure-requests", "1")
					.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
					.header("content-type", "application/x-www-form-urlencoded")
					.header("accept-encoding", "gzip, deflate")
					.header("accept-language", "fr,en-US;q=0.8,en;q=0.6")
					.body(body)
					//.body("Cabin=Coach&DepartDate=2016-03-12&DepartTime=0000&FromCode=DFW&NonstopOnly=true&NumberOfAdults=1&SearchBy=P&SearchType=OW&ToCode=NYC")
					.asString();


			//System.out.println(response.getBody().toString());
			html = response.getBody().toString();	


			List<String> lst = new ArrayList<>();
			lst.add(html);

			Path file = Paths.get("C:\\Users\\tcour\\Documents\\AA.html");
			try {
				Files.write(file, lst, Charset.forName("UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (UnirestException e) {
			e.printStackTrace();
		}

	}


}
