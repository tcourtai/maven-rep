package org.tcourtai.friends2go.hello;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extractor {
    private final String slash = "%2F";
	protected Flights flights;
	protected FlightInfo flightInfo;
	protected String html;
	public Company company;
	
	public Extractor(FlightInfo fi) {
		flights = new Flights();
		this.flightInfo = fi;
	}
	
	public Extractor() {
		// TODO Auto-generated constructor stub
	}

	public Flights getFlights() {
		return flights;
	}
	
	public int getNumberOfFlights()
	{
		return flights.list.size();
	}
	
	public String getResultInfo(){
		return company.toString() + " : " + getNumberOfFlights() + " flight";
	}
	
	public String getDateToHtml(String d) {
		return d.split("-")[1]
				+ slash
				+ d.split("-")[2]
				+ slash
				+ d.split("-")[0];
	}
	
	public String msgNoResult(Company comp) {
		return "No result for "  + comp.toString();
	}
	
	public static String extractCode(String airport){
		String code = "";
		Pattern p = Pattern.compile(".*[(]?([A-Z]{3})[)]?.*");
		Matcher m = p.matcher(airport);
		if(m.matches()) {
		    code = m.group(1);
		}	
		return code;
	}
		
}
