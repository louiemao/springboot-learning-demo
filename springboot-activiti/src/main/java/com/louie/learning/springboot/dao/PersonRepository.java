package com.louie.learning.springboot.dao;

import com.louie.learning.springboot.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jery on 2016/11/23.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	public Person findByPersonName(String personName);
	
}

