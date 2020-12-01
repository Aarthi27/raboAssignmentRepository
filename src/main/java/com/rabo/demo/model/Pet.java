package com.rabo.demo.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="pet")
public class Pet {
	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
	@SequenceGenerator(name = "seqGen", initialValue = 1, allocationSize = 10, sequenceName = "PET_SEQ")
	@Column(name = "PET_ID")
	@Id
	public int pet_id;
	
	@Column(name = "PET_NAME")
	public String name;
	
	@Column(name = "AGE")
	public int age;
	
	@ManyToOne(cascade = CascadeType.MERGE, optional=false)
	@JoinColumn(name = "PERSON_ID")
//	@JsonIgnore
	@JsonBackReference
	public Person person;
	
	public Pet() {
		
	}
	public int getPet_id() {
		return pet_id;
	}
	public void setPet_id(int pet_id) {
		this.pet_id = pet_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}
	
	@Override
	public String toString() {
		return "Pet Id :"+getPet_id()+" name:"+getName()+" Age:"+getAge();
	}
	

}
