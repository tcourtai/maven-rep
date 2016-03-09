package org.tcourtai.friends2go.repositories;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.tcourtai.friends2go.Application;
import org.tcourtai.friends2go.hello.Toto;
import org.tcourtai.friends2go.hello.TotoRepository;


//@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class TotoRepositoryTest {
	
	@Autowired
	private TotoRepository totoRepository;
	
	public void test() {
	        Toto t = new Toto("test");
	        Toto t2 = new Toto("test2");
	        totoRepository.save(t);
	        totoRepository.save(t2);
	        
	        Iterable<Toto> totos = totoRepository.findAll();
	        
	        int count = 0;
	 
	        for(Toto p : totos){
	            System.out.println(p.toString());
	        }
	        
	    }

}
