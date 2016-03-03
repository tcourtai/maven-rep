package hello;

public class Flight {
	private String departure;
	private String arrival;
	private String from;
	private String to;
	private String date;
	private String price;
	
	public Flight() {
		this.from = "UNKNOWN";
		this.to = "UNKNOWN";
		this.date = "UNKNOWN";
		this.departure = "UNKNOWN";
		this.arrival = "UNKNOWN";
		this.price = "UNKNOWN";
	};
	
	public Flight(String from, String to, String date, String departure, String arrival, String price) {
		this.from = from;
		this.to = to;
		this.date = date;
		this.departure = departure;
		this.arrival = arrival;
		this.price = price;
	}
	
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public String toString() {
		return "############ Fligth details ###############"
		+ " \nFrom : " + this.from
		+ " \nTo : " + this.to
		+ " \nDate : " + this.date
		+ " \nDeparture : " + this.departure
		+ " \nArrival : " + this.arrival
		+ " \nPrice : " + this.price;
	}
	
}
