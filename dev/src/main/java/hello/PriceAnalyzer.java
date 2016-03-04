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
		//System.out.println(lstFlight.getList().toString());
		System.out.println(lstFlight.getBestFlights().toString());;
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
	
	public String bestFlightsToHtml() {
		return lstFlight.getBestFlights().toHtml();
	}
	
	public void generateFlightInfo(){
		if (from == null || to == null || depDate == null) return;
		
		//Normal Way
		lstFlightInfo.add(new FlightInfo(from, to, depDate, FlightType.DEP_A));
		//Normal Return
		//*
		if (retDate != null) lstFlightInfo.add(new FlightInfo(to, from, retDate, FlightType.RET_A));
		
		//Other Way
		lstFlightInfo.add(new FlightInfo(to, from, depDate,FlightType.DEP_B));
		//Other Return
		if (retDate != null) lstFlightInfo.add(new FlightInfo(from, to, retDate, FlightType.RET_B));
		//*/
	}
	


}
