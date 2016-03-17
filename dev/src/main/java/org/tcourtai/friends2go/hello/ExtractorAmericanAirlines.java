package org.tcourtai.friends2go.hello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ExtractorAmericanAirlines extends Extractor{
	
	public ExtractorAmericanAirlines(FlightInfo fi) {
		super(fi);
	}
	
	public void start() {
		queryToHtml();
		parse();
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
	
	public boolean parse(){

		Document doc = Jsoup.parse(html);

		Elements lstflights = doc.select("[class~=^lmb[0-9]*bl$]");
		System.out.println("nb flights "+ lstflights.size());

		for (Element flight : lstflights){
			Flight f = new Flight(flightInfo);
			Elements eDetails = flight.select("div.bord");
			
			if (eDetails.size() == 0) break;

			Elements items = eDetails.first().select("div.un_fs12");
			for (Element it : items) {
				if (it.text().contains("Departing")) {
					Pattern p = Pattern.compile("Departing : ([A-Z]{3} [A-Za-z/,\\s]+)\\D+([0-9]+:[0-9]+.*)");
					//Pattern p = Pattern.compile("Departing : ([A-Z]{3}.+)(\\d+:\\d+ [A-Z]+)");
					Matcher m = p.matcher(it.text());
					while (m.find()) {
						f.setFrom(m.group(1));
						f.setDeparture(m.group(2));
					}
				}
				
			}
			
			for (Element detail : eDetails) {
				items = detail.select("div.un_fs12");
				for (Element it : items) {
				if (it.text().contains("Arriving")) {
					Pattern p = Pattern.compile("Arriving : ([A-Z]{3} [A-Za-z/,\\s]+)\\D+([0-9]+:[0-9]+.*)");
					//Pattern p = Pattern.compile("Departing : ([A-Z]{3}.+)(\\d+:\\d+ [A-Z]+)");
					Matcher m = p.matcher(it.text());
					while (m.find()) {
						f.setTo(m.group(1));
						f.setArrival(m.group(2));
					}
				}
				}
			}
			
			Element ePrice = flight.select("div.un_fs13").first();
			Pattern p = Pattern.compile("\\D*([0-9]+[.]?[0-9]+)\\D*");
			Matcher m = p.matcher(ePrice.text());
			while (m.find()) {
				f.setPrice(m.group(1));
			}
			
			f.setCompany(Company.AmericanAirlines);
			flights.add(f);
		}

		return true;
	}


}
