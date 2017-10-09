package com.louie.learning.springboot.dao;

import com.louie.learning.springboot.model.Comp;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompRepository extends JpaRepository<Comp, Long> {
	
}
