package hello;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Flights {
	ArrayList<Flight> list;
	
	public Flights (){
		list = new ArrayList<Flight>();
	}
	
	public void add(Flight f) {
		list.add(f);
	}	
	
	public void add(Collection<? extends Flight> fs) {
		list.addAll(fs);
	}
	
	public ArrayList<Flight> getList() {
		return list;
	}
	
	public Flight getBestFlightByFlightType(FlightType ft) {
		Flight bestFlight = new Flight();
		bestFlight.setPriceNum(Float.MAX_VALUE);
		
		for (Flight f : list) {
			if (f.getPriceNum() < bestFlight.getPriceNum() && f.getFlightType() == ft) {
				bestFlight = (Flight) f.clone();
			}
			
		}
		if (bestFlight.getPriceNum() == Float.MAX_VALUE) return null;
		return bestFlight;
	}
	
	public Flights getBestFlights() {
		Flights bestFlights = new Flights();
		
		for (FlightType type : FlightType.values()){
			Flight f = getBestFlightByFlightType(type); 
			if (f != null) bestFlights.add(f);
		}
	
		bestFlights.toString();
		return bestFlights;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Flight f : list) {
			sb.append(f.toString());
			sb.append("\n");
		}
		return sb.toString();
	}	
	
	public List<String> toCSV() {
		List<String> txtToCSV = new ArrayList<String>();
		txtToCSV.add("From;To;Date;Departure;Arrival;Price;Company;FlightType");
		for (Flight f : list) {
			txtToCSV.add(f.toCSV());
		}		
		return txtToCSV;
		
	}
	public String toHtml() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("<table><tr>");
		appendHeaderCell(sb, "From");
		appendHeaderCell(sb, "To");
		appendHeaderCell(sb, "Date");
		appendHeaderCell(sb, "Departure");
		appendHeaderCell(sb, "Arrival");
		appendHeaderCell(sb, "Price");
		appendHeaderCell(sb, "Company");
		appendHeaderCell(sb, "FlightType");
		sb.append("</tr>");
		
		for (Flight f : list) {
		sb.append("<tr>");
		
		appendDataCell(sb, f.getFrom());
		appendDataCell(sb, f.getTo());
		appendDataCell(sb, f.getDate());
		appendDataCell(sb, f.getDeparture());
		appendDataCell(sb, f.getArrival());
		appendDataCell(sb, f.getPrice());
		appendDataCell(sb, f.getCompany().toString());
		appendDataCell(sb, f.getFlightType().toString());

		sb.append("</tr>");
		}
		sb.append("</table>");
		
		return sb.toString();
	}
	
	void appendTag(StringBuilder sb, String tag, String contents) {
	    sb.append('<').append(tag).append('>');
	    sb.append(contents);
	    sb.append("</").append(tag).append('>');
	}
	void appendDataCell(StringBuilder sb, String contents) {
	    appendTag(sb, "td", contents);
	}
	void appendHeaderCell(StringBuilder sb, String contents) {
	    appendTag(sb, "th", contents);
	}
	
	

}
