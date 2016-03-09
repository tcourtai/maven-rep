package org.tcourtai.friends2go.hello;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.tcourtai.friends2go.Application;


@Controller
public class GreetingController {
	
	private static final Logger log = LoggerFactory.getLogger(Application.class);
	
	private FlightRepository flightRepository;
	
	@Autowired
	public GreetingController(FlightRepository flightRepository) {
		this.flightRepository = flightRepository;
	}	
	
	
    @RequestMapping("/")
    public String index(Model model) {
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer();
        model.addAttribute("PriceAnalyzer", priceAnalyzer);
        //priceAnalyzer.test(totoRepository);
        //gizmo.parseResult();
        //model.addAttribute("message", gizmo.toHtml());
        return "hello";
    }

    @RequestMapping("/save")
    public String save(PriceAnalyzer pa, Model model) {
        System.out.println(pa.getFromCode());
        System.out.println(pa.getToCode());
        pa.start();
        Flights flights = pa.getFlights();
        flightRepository.save(flights.getList());

        //model.addAttribute("message", pa.toHtml());
        model.addAttribute("bestFlights", pa.bestFlightsToHtml());
        return "result";
        //return "redirect:/";
    }
    
    
	
}