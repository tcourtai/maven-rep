package org.tcourtai.friends2go.hello;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FlightRepository extends CrudRepository<Flight, Long> {

    List<Flight> findByDeparture(String departure);
}