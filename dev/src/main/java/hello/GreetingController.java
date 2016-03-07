package hello;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {

    @RequestMapping("/")
    public String index(Model model) {
        PriceAnalyzer priceAnalyzer = new PriceAnalyzer();
        model.addAttribute("PriceAnalyzer", priceAnalyzer);
                
        //gizmo.parseResult();
        //model.addAttribute("message", gizmo.toHtml());
        return "hello";
    }

    @RequestMapping("/save")
    public String save(PriceAnalyzer pa, Model model) {
        System.out.println(pa.getFromCode());
        System.out.println(pa.getToCode());
        pa.start();
        model.addAttribute("message", pa.toHtml());
        model.addAttribute("bestFlights", pa.bestFlightsToHtml());
        return "result";
        //return "redirect:/";
    }
}