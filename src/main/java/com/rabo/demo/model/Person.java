package com.rabo.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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
public class Person implements java.io.Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
	@SequenceGenerator(name = "seqGen", initialValue = 1, allocationSize = 10, sequenceName = "PER_SEQ")
//	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PERSON_ID")
	public int id;

	@Column(name = "FIRST_NAME")
	public String firstname;

	@Column(name = "LAST_NAME")
	public String lastname;

	@Column(name = "DATE_OF_BIRTH")
	public String dob;

	@Column(name = "ADDRESS")
	public String address;
	
//	@OneToMany(mappedBy = "person")
	@OneToMany(targetEntity=Pet.class, fetch = FetchType.EAGER)
	@Cascade({CascadeType.ALL})
	@JoinColumn(name = "PERSON_ID", nullable = false, insertable=false, updatable = false)
	@JsonManagedReference
	public List<Pet> petList = new ArrayList<>();
	
	public Person() {
		
	}

	public int getId() {
		return id;
	}

	public Person setId(int per_id) {
		this.id = per_id;
		return this;
	}

	public String getFirstname() {
		return firstname;
	}

	public Person setFirstname(String firstname) {
		this.firstname = firstname;
		return this;
	}

	public String getLastname() {
		return lastname;
	}

	public Person setLastname(String lastname) {
		this.lastname = lastname;
		return this;
	}

	public String getDob() {
		return dob;
	}

	public Person setDob(String dob) {
		this.dob = dob;
		return this;
	}

	public String getAddress() {
		return address;
	}

	public Person setAddress(String address) {
		this.address = address;
		return this;
	}

	public List<Pet> getPetList() {
		return petList;
	}

	public void setPetList(List<Pet> petList) {
		this.petList = petList;
	}

	
	@Override 
	public String toString() { 
		return getId()+" "+getFirstname() + " "+getLastname()+" "+getAddress()+" "+getDob();
 }
	 
	
}
