package hello;

public class FlightInfo {
	private String from;
	private String to;
	private String fromCode;
	private String toCode;
	private String date;
	private FlightType flightType;
	
	public FlightInfo() {
		this.from = "UNKNOWN";
		this.to = "UNKNOWN";
		this.date = "UNKNOWN";
		this.fromCode = "N/A";
		this.toCode = "N/A";
	};
	
	public FlightInfo(String fromCode, String toCode, String date, FlightType flightType) {
		this.fromCode = fromCode;
		this.toCode = toCode;
		this.date = date;
		this.flightType = flightType;
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
	
	public String getFromCode() {
		return fromCode;
	}

	public void setFromCode(String fromCode) {
		this.fromCode = fromCode;
	}

	public String getToCode() {
		return toCode;
	}

	public void setToCode(String toCode) {
		this.toCode = toCode;
	}

	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}

	public FlightType getFlightType() {
		return flightType;
	}

	public void setFlightType(FlightType flightType) {
		this.flightType = flightType;
	}

	public String toString() {
		return "############ Flight info ###############"
				+ " \nFrom : " + this.from
				+ " \nTo : " + this.to
				+ " \nFrom(Code) : " + this.fromCode
				+ " \nTo(Code) : " + this.toCode
				+ " \nDate : " + this.date;
	}
	
}