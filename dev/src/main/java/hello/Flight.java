package hello;

public class Flight extends FlightInfo {
	private String departure;
	private String arrival;
	private String price;
	private Company company;



	private float priceNum;
	
	public Flight() {
		super();
		this.departure = "UNKNOWN";
		this.arrival = "UNKNOWN";
		this.price = "";
	};
	
	public Object clone() {
		return new Flight(this.getFrom(), this.getTo(), this.getDate(), this.getFlightType(), this.departure,this.arrival, this.price, this.company);
	};
	
	public Flight(String from, String to, String date, FlightType fligthType, String departure, String arrival, String price, Company company) {
		super(from, to, date, fligthType);
		this.departure = departure;
		this.arrival = arrival;
		this.price = price;
		this.priceNum = parsePrice(price);
		this.company = company;
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
		this.priceNum = parsePrice(price);
	}

	public float getPriceNum() {
		return priceNum;
	}

	public void setPriceNum(float priceNum) {
		this.priceNum = priceNum;
	}
	
	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String toString() {
		return "############ Fligth details ###############"
		+ " \nFrom : " + this.getFrom()
		+ " \nTo : " + this.getTo()
		+ " \nDate : " + this.getDate()
		+ " \nDeparture : " + this.getDeparture()
		+ " \nArrival : " + this.getArrival()
		+ " \nPrice : " + this.getPrice()
		+ " \nCompany : " + this.getCompany();
	}


	public float parsePrice(String p) {
		float fp = Float.MAX_VALUE;
		p = p.replaceAll("\\$", "");
		try {
			fp = Float.parseFloat(p);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return fp;
	}
}
