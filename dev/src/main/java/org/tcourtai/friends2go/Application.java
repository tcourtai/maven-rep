package org.tcourtai.friends2go;


import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.tcourtai.friends2go.hello.Extractor;
import org.tcourtai.friends2go.hello.ExtractorUnited;
import org.tcourtai.friends2go.hello.FlightInfo;


//@Configuration
//@EnableAutoConfiguration
//@ComponentScan
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
    	SpringApplication.run(Application.class, args);
    	
    	/*
        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        */
    }
    
    /*
    @Bean
    ServletRegistrationBean h2servletRegistration(){
        ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
        registrationBean.addUrlMappings("/console/*");
        return registrationBean;
    }
    */
    
    private FlightInfo fi;
    
    @Bean
    public ExtractorUnited extractorUnited()
    {
       return new ExtractorUnited(fi);
    }
  
}