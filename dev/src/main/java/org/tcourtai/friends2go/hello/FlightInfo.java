package org.tcourtai.friends2go.hello;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
public class FlightInfo {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String xfrom;
	private String xto;
	private String fromCode;
	private String toCode;
	private String date;
	
	@Enumerated(EnumType.STRING)
	private FlightType flightType;
	
	public FlightInfo() {
		this.xfrom = "UNKNOWN";
		this.xto = "UNKNOWN";
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
		return xfrom;
	}
	public void setFrom(String from) {
		this.xfrom = from;
		this.fromCode = Extractor.extractCode(from);
	}
	public String getTo() {
		return xto;
	}
	public void setTo(String to) {
		this.xto = to;
		this.toCode = Extractor.extractCode(to);
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
				+ " \nFrom : " + this.xfrom
				+ " \nTo : " + this.xto
				+ " \nFrom(Code) : " + this.fromCode
				+ " \nTo(Code) : " + this.toCode
				+ " \nDate : " + this.date;
	}
	
}