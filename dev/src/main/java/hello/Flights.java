package hello;

import java.util.ArrayList;
import java.util.List;

public class Flights {
	ArrayList<Flight> list;
	
	public Flights (){
		list = new ArrayList<Flight>();
	}
	
	public void add(Flight f) {
		list.add(f);
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
		sb.append("</tr>");
		
		for (Flight f : list) {
		sb.append("<tr>");
		
		appendDataCell(sb, f.getFrom());
		appendDataCell(sb, f.getTo());
		appendDataCell(sb, f.getDate());
		appendDataCell(sb, f.getDeparture());
		appendDataCell(sb, f.getArrival());
		appendDataCell(sb, f.getPrice());

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
