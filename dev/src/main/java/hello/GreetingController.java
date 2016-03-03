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
        Gizmo gizmo = new Gizmo();
        model.addAttribute("gizmo", gizmo);
                
        gizmo.parseResult();
        model.addAttribute("message", gizmo.toHtml());
        return "hello";
    }

    @RequestMapping("/save")
    public String save(Gizmo gizmo) {
        System.out.println(gizmo.getFrom());
        System.out.println(gizmo.getTo());
        gizmo.start();
        
        return "redirect:/";
    }
}