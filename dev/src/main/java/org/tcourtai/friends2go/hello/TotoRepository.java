package org.tcourtai.friends2go.hello;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TotoRepository extends CrudRepository<Toto, Integer> {

    List<Toto> findByName(String name);
}