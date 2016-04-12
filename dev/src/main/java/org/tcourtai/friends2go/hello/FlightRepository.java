package org.tcourtai.friends2go.hello;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface FlightRepository extends CrudRepository<Flight, Long> {

    List<Flight> findByDeparture(String departure);
}