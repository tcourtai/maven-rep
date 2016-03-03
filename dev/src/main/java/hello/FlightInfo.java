package hello;

public class FlightInfo {
	private String from;
	private String to;
	private String date;
	
	public FlightInfo() {
		this.from = "UNKNOWN";
		this.to = "UNKNOWN";
		this.date = "UNKNOWN";
	};
	
	public FlightInfo(String from, String to, String date) {
		this.from = from;
		this.to = to;
		this.date = date;
	}
	
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
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String toString() {
		return "############ Flight info ###############"
		+ " \nFrom : " + this.from
		+ " \nTo : " + this.to
		+ " \nDate : " + this.date;
	}
	
}