package com.rabo.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import com.fasterxml.jackson.annotation.JsonManagedReference;



@Entity
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = { "first_name", "last_name" }))
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
	@SequenceGenerator(name = "seqGen", initialValue = 1, allocationSize = 10, sequenceName = "PER_SEQ")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERSON_ID")
	public int per_id;

	@Column(name = "FIRST_NAME")
	public String firstname;

	@Column(name = "LAST_NAME")
	public String lastname;

	@Column(name = "DATE_OF_BIRTH")
	public String dob;

	@Column(name = "ADDRESS")
	public String address;
	
//	@OneToMany(mappedBy = "person")
	@OneToMany(targetEntity = Pet.class)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID")
	@JsonManagedReference
	public List<Pet> petList = new ArrayList<>();
	
	public Person() {
		
	}

	public int getPer_id() {
		return per_id;
	}

	public void setPer_id(int per_id) {
		this.per_id = per_id;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Pet> getPetList() {
		return petList;
	}

	public void setPetList(List<Pet> petList) {
		this.petList = petList;
	}

	
}
