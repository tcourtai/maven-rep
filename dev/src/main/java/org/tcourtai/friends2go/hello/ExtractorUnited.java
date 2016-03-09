package org.tcourtai.friends2go.hello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ExtractorUnited extends Extractor {

	private String html;
	private final String unitedUrl = "https://mobile.united.com";
	private Map<String, String> mapAirport;
	private  final String pathAirportList = "C:\\Users\\tcour\\Documents\\UnitedAirports.txt";

	public ExtractorUnited(FlightInfo fi) {
		super(fi);
		// TODO Auto-generated constructor stub
	}


	public void start(){
		loadAirportList();
		queryToHtml();
		parse();
	};

	public void queryToHtml() {
		html = "";
		System.out.println("Searching for ...." + flightInfo.toString());
		HttpResponse<String> response;
		try {
			String fromToHtml = StringEscapeUtils.escapeHtml(resolveCode(flightInfo.getFromCode()));
			String toToHtml = StringEscapeUtils.escapeHtml(resolveCode(flightInfo.getToCode()));
			String body = "Cabin=Coach&DepartDate=" + flightInfo.getDate() 
					+ "&DepartTime=0000&FromCode=" + flightInfo.getFromCode() 
					+ "&NonstopOnly=true&NumberOfAdults=1&SearchBy=P&SearchType=OW&ToCode=" + flightInfo.getToCode() 
					+ "&From=" + fromToHtml 
					+ "&To=" + toToHtml;
			System.out.println(body);
			
			response = Unirest.post("https://mobile.united.com/Booking/OneWaySearch")
					.header("accept", "text/html, */*; q=0.01")
					.header("origin", "https://mobile.united.com")
					.header("upgrade-insecure-requests", "1")
					.header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
					.header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
					.header("accept-encoding", "gzip, deflate")
					.header("accept-language", "fr,en-US;q=0.8,en;q=0.6")
					.body(body)
					//.body("Cabin=Coach&DepartDate=2016-03-12&DepartTime=0000&FromCode=DFW&NonstopOnly=true&NumberOfAdults=1&SearchBy=P&SearchType=OW&ToCode=NYC")
					.asString();


			//System.out.println(response.getBody().toString());
			html = response.getBody().toString();	

			Document doc = Jsoup.parse(html);
			Element title = doc.select("title").first();
			if (title.text().equalsIgnoreCase("Object moved")) {
				Element link = doc.select("a[href]").first();
				String href = unitedUrl + link.attr("href");
				System.out.println("Object moved, connecting to..." + href);
				response = Unirest.get(href).asString();
				html = response.getBody().toString();	
			}


			List<String> lst = new ArrayList<>();
			lst.add(html);

			Path file = Paths.get("C:\\Users\\tcour\\Documents\\united.html");
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

		Elements lstflights = doc.select("ul[data-role]");

		for (Element flight : lstflights){
			Flight f = new Flight();

			Elements eDetails = flight.select("div.grid_7");

			if (eDetails.size() == 0) break;

			String details = eDetails.get(0).html();

			String[] detailsTab = details.split(">");

			f.setDeparture(detailsTab[1].replaceAll("</span", ""));
			f.setFrom(detailsTab[4]);
			/*for (String s : detailsTab) {
				System.out.println(s);
			}*/

			//get last flight in case of trip with stop(s)
			for (Element e : eDetails) {
				details = e.html(); 
				if (details.contains("</span")) {
						detailsTab = details.split(">");
						f.setArrival(detailsTab[1].replaceAll("</span", ""));
						f.setTo(detailsTab[4]);	}
			}

			Element ePrice = flight.select("input#btnPickTrip").first();

			Pattern p = Pattern.compile("([$]\\d+)");
			Matcher m = p.matcher(ePrice.attr("value"));

			while (m.find()) {
				f.setPrice(m.group(1));
			}

			f.setDate(flightInfo.getDate());

			f.setCompany(Company.UNITED);
			f.setFlightType(flightInfo.getFlightType());
			//System.out.println(f.toString());
			flights.add(f);
			//System.out.println(f.toString());

		}

		return true;
	}

	public String resolveCode(String code) {
		return mapAirport.get(code);
	}
	
	public void loadAirportList () {
		ArrayList<String> list = FileUtil.readFileByLine(pathAirportList);
		mapAirport = new HashMap<String, String>();
		for (String line : list) {
			mapAirport.put(line.split(FileUtil.csvSeparator)[0], line.split(FileUtil.csvSeparator)[1]);
		}			
	}

	public void generateAirportList() {
		html = "";
		ArrayList<UnitedAirport> listAirport = new ArrayList<>();
		ArrayList<String> listAirportCSV = new ArrayList<>();
		HttpResponse<String> response;	
		ArrayList<String> stateList = FileUtil.readFileByLine("C:\\Users\\tcour\\Documents\\state.txt");

		for (String state : stateList) {


			try {
				response = Unirest.get("https://www.united.com/ual/en/us/common/common/_airportlookupbystate?StateCode=" + state)
						.asString();
				html = response.getBody().toString();	
			} catch (UnirestException e) {
				e.printStackTrace();
			}
			JSONObject obj = new JSONObject(html);
			JSONArray arr = obj.getJSONArray("Airports");
			for (int i = 0; i < arr.length(); i++)
			{
				listAirport.add(new UnitedAirport(arr.getJSONObject(i).getString("IATACode"), 
						arr.getJSONObject(i).getString("ShortName"),
						arr.getJSONObject(i).getString("Name")));			
			}
		}

		for (UnitedAirport airport : listAirport) {
			listAirportCSV.add(airport.toCSV());
		}
		
		FileUtil.writeCSV(Paths.get(pathAirportList), listAirportCSV);

	}

	class UnitedAirport {
		public String code;
		public String shortName;
		public String name;

		public UnitedAirport(String code, String shortName, String name) {
			this.code = code;
			this.shortName = shortName;
			this.name = name;
		}

		public String toCSV () {return code + FileUtil.csvSeparator + shortName;} 
	}
}
