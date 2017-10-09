package com.louie.learning.springboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by jery on 2016/11/23.
 */
@Entity
public class Person {
	
	@Id
	@GeneratedValue
	private Long personId;
	
	private String personName;
	
	@ManyToOne(targetEntity = Comp.class)
	private Comp comp;
	
	public Person() {
		
	}
	
	public Person(String personName) {
		this.personName = personName;
	}
	
	public Long getPersonId() {
		return personId;
	}
	
	public void setPersonId(Long personId) {
		this.personId = personId;
	}
	
	public String getPersonName() {
		return personName;
	}
	
	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
	public Comp getComp() {
		return comp;
	}
	
	public void setComp(Comp comp) {
		this.comp = comp;
	}
}
