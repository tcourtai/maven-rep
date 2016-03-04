package hello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	
	public ExtractorUnited(FlightInfo fi) {
		super(fi);
		// TODO Auto-generated constructor stub
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
			response = Unirest.post("https://mobile.united.com/Booking/OneWaySearch")
					  .header("accept", "text/html, */*; q=0.01")
					  .header("origin", "https://mobile.united.com")
					  .header("upgrade-insecure-requests", "1")
					  .header("user-agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/48.0.2564.116 Safari/537.36")
					  .header("content-type", "application/x-www-form-urlencoded; charset=UTF-8")
					  .header("accept-encoding", "gzip, deflate")
					  .header("accept-language", "fr,en-US;q=0.8,en;q=0.6")
					  .body("Cabin=Coach&DepartDate=" + flightInfo.getDate() + "&DepartTime=0000&FromCode=DFW&NonstopOnly=true&NumberOfAdults=1&SearchBy=P&SearchType=OW&ToCode=CHI&From=Dallas%2FFort+Worth%2C+TX+(DFW)&To=Chicago, IL, US (CHI - All Airports)")
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
			
			details = eDetails.get(1).html();
			detailsTab = details.split(">");
			f.setArrival(detailsTab[1].replaceAll("</span", ""));
			f.setTo(detailsTab[4]);			
			
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
}
