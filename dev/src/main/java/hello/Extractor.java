package hello;

public class Extractor {
    private final String slash = "%2F";
	protected Flights flights;
	protected FlightInfo flightInfo;
	
	public Extractor(FlightInfo fi) {
		flights = new Flights();
		this.flightInfo = fi;
	}
	
	public Flights getFlights() {
		return flights;
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
		
}
