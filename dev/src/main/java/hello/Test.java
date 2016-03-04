package hello;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExtractorUnited eu = new ExtractorUnited(new FlightInfo());
		eu.start();
		
		/*
		String url = "https://www.united.com/ual/fr/fr/flight-search/book-a-flight/results/rev?f=DFW&t=NYC&d=2016-03-13&tt=1&sc=7&px=1&taxng=1&idx=1";
		HttpResponse<String> response;
		try {
			response = Unirest.get(url).asString();
	
				
			//System.out.println(response.getBody().toString());
			String html = response.getBody().toString();	
		
			List<String> lst = new ArrayList<>();
			lst.add(html);
			
			Path file = Paths.get("C:\\Users\\tcour\\Documents\\unitedv2.html");
			try {
				Files.write(file, lst, Charset.forName("UTF-8"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	*/
	}
	

}
