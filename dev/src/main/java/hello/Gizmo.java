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
    private String depDate;
    private String retDate;
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
	
	public String getDepDate() {
		return depDate;
	}
	public void setDepDate(String date) {
		this.depDate = date;
	}
	
	public String getRetDate() {
		return retDate;
	}
	public void setRetDate(String date) {
		this.retDate = date;
	}
	
	public boolean start() {
		System.out.println("lets go!");
		FlightInfo fi = new FlightInfo(from, to, depDate);
		ExtractorSpirit eSpirit = new ExtractorSpirit(fi);
		eSpirit.start();		
		lstFlight.add(eSpirit.getFlights().getList());
		System.out.println("finished");
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
