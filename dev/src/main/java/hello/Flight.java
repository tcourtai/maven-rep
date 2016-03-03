package hello;

public class Flight extends FlightInfo {
	private String departure;
	private String arrival;
	private String price;
	
	public Flight() {
		super();
		this.departure = "UNKNOWN";
		this.arrival = "UNKNOWN";
		this.price = "UNKNOWN";
	};
	
	public Flight(String from, String to, String date, String departure, String arrival, String price) {
		super(from, to, date);
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
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String toString() {
		return "############ Fligth details ###############"
		+ " \nFrom : " + this.getFrom()
		+ " \nTo : " + this.getTo()
		+ " \nDate : " + this.getDate()
		+ " \nDeparture : " + this.getDeparture()
		+ " \nArrival : " + this.getArrival()
		+ " \nPrice : " + this.getPrice();
	}
	
}
