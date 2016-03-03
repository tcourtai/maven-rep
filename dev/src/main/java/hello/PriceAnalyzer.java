package hello;


import java.io.IOException;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;



public class PriceAnalyzer {

    private String from;
    private String to;
    private String depDate;
    private String retDate;
    private Flights lstFlight = new Flights();
    private ArrayList<FlightInfo> lstFlightInfo = new ArrayList<FlightInfo>();

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
		generateFlightInfo();
		for (FlightInfo fi : lstFlightInfo) {
			ExtractorSpirit eSpirit = new ExtractorSpirit(fi);
			eSpirit.start();		
			lstFlight.add(eSpirit.getFlights().getList());
		}
		System.out.println("finished");	
		getBestFlight();
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
	
	public void generateFlightInfo(){
		if (from == null || to == null || depDate == null) return;
		
		//Normal Way
		lstFlightInfo.add(new FlightInfo(from, to, depDate));
		
		//Other Way
		lstFlightInfo.add(new FlightInfo(to, from, depDate));
		System.out.println(lstFlightInfo.size());
	}
	
	public Flights getBestFlight() {
		Flight bestFlight = new Flight();
		bestFlight.setPriceNum(Float.MAX_VALUE);
		
		for (Flight f : lstFlight.getList()) {
			if (f.getPriceNum() < bestFlight.getPriceNum()) {
				bestFlight = (Flight) f.clone();
			}
			
		}
		return new Flights();
	}

}
