package org.tcourtai.friends2go.hello;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;










import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.thymeleaf.util.DateUtils;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;

@Component
public class PriceAnalyzer {
	

	private String searchID;
    private String fromCode;
    private String toCode;
    private String depDate;
    private String retDate;
    private int range = 1;
    private Flights lstFlight = new Flights();
    private ArrayList<FlightInfo> lstFlightInfo = new ArrayList<FlightInfo>();
    private ArrayList<String> lstDepDate = new ArrayList<String>();
    private ArrayList<String> lstFromCode = new ArrayList<String>();
    private ArrayList<String> lstToCode = new ArrayList<String>();

    public String getFromCode() {
		return fromCode;
	}
	public void setFromCode(String from) {
		this.fromCode = from;
	}
	public String getToCode() {
		return toCode;
	}
	public void setToCode(String to) {
		this.toCode = to;
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
	
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public boolean start() {
		System.out.println("lets go!");
		searchID = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		computeDates();
		computeAirportCode();
		generateFlightInfo();
		for (FlightInfo fi : lstFlightInfo) {
			ExtractorSpirit eSpirit = new ExtractorSpirit(fi);
			eSpirit.start();		
			lstFlight.add(eSpirit.getFlights().getList());
			
			ExtractorUnited eUnited = new ExtractorUnited(fi);
			eUnited.start();		
			lstFlight.add(eUnited.getFlights().getList());		
			
		}
		System.out.println("finished");	
		//System.out.println(lstFlight.getList().toString());
		//System.out.println(lstFlight.getBestFlights().toString());;
		//*/
		//FileUtil.writeCSV(Paths.get("C:\\Users\\tcour\\Documents\\result_" + searchID + ".csv"), lstFlight.toCSV());
		return true;
	}
   
	public Flights getFlights() {
		return lstFlight;
	}
	
	public String toHtml() {
		return lstFlight.toHtml();		
	}
	
	public String bestFlightsToHtml() {
		return lstFlight.getBestFlights().toHtml();
	}
	
	public void computeDates() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		
		try {
			if  (depDate != null) {
			Date depDateToDate = formatter.parse(depDate);
			//lstDepDate.add(formatter.format(depDateToDate));
			for (int i = 0; i < range; i++) {
				lstDepDate.add(formatter.format(DateUtil.addDays(depDateToDate, i)));
			}
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void computeAirportCode() {
		lstFromCode = getAirportCodes(fromCode);
		lstToCode = getAirportCodes(toCode);
	}
	
	public ArrayList<String> getAirportCodes(String code){		
		ArrayList<String> lst = new ArrayList<String>();
		
		switch (code) {
		case "NYC" : 
			lst.add("JFK");
			lst.add("LGA");
			lst.add("EWR");
			break;
		case "WAS" : 
			lst.add("IAD");
			lst.add("DCA");
			break;
		case "YMQ" : 
			lst.add("YUL");
			lst.add("YMX");	
			break;
		default : 
			lst.add(code);		
		}

		return lst;
		
	}
	
	public void generateFlightInfo(){
		if (fromCode == null || toCode == null) return;
		
		System.out.println(lstDepDate.size());
		for (String d : lstDepDate) {
			for (String f : lstFromCode) {
				for (String t : lstToCode) {
			//Normal Way
			lstFlightInfo.add(new FlightInfo(searchID, f, t, d, FlightType.DEP_A));
			//Other Way
			lstFlightInfo.add(new FlightInfo(searchID, t, f, d,FlightType.DEP_B));
				}
			}
		}
		
		/*for (String d : lstRetDate) {
			//Normal Return
			if (retDate != null) lstFlightInfo.add(new FlightInfo(to, from, d, FlightType.RET_A));
			
			//Other Return
			if (retDate != null) lstFlightInfo.add(new FlightInfo(from, to, d, FlightType.RET_B));
			
		}
		//*/
	}
}
