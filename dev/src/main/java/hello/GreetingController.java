package hello;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GreetingController {
	
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	
	@RequestMapping("/")
	public String index(Model model){
		model.addAttribute("gizmo", new Gizmo());
		return "hello";
	}
	
	@RequestMapping("/save")
	public String save(Gizmo gizmo) {
	    System.out.println(gizmo.getField1());
	    System.out.println(gizmo.getField2());
	    return "redirect:/";
	}
	
	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
		return new Greeting(counter.incrementAndGet(),
							String.format(template, name));
	}
	
}
