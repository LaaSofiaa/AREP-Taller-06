package com.edu.arep.Taller6.Repository;

import com.edu.arep.Taller6.Entity.Property;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PropertyRepository extends CrudRepository<Property, Long> {
    List<Property> findByAddress(String address);
    Property findById(long id);
}
