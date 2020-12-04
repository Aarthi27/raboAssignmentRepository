package com.rabo.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@Entity
@Table(name = "person", uniqueConstraints = @UniqueConstraint(columnNames = { "first_name", "last_name" }))
public class Person implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGen")
	@SequenceGenerator(name = "seqGen", initialValue = 1, allocationSize = 10, sequenceName = "PER_SEQ")
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

	@OneToMany(targetEntity = PersonPetMapping.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "person_id", referencedColumnName = "PERSON_ID")
	@JsonIgnore
	public List<PersonPetMapping> personPetLinkList = new ArrayList<>();

	@Transient
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<Pet> petList = new ArrayList<>();

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

	public List<PersonPetMapping> getPersonPetLinkList() {
		return personPetLinkList;
	}

	public List<Pet> getPetList() {
		return petList;
	}

	public void setPetList(List<Pet> petList) {
		this.petList = petList;
	}

	public void setPersonPetLinkList(List<PersonPetMapping> personPetLinkList) {
		this.personPetLinkList = personPetLinkList;
	}

	@Override
	public String toString() {
		return getId() + " " + getFirstname() + " " + getLastname() + " " + getAddress() + " " + getDob();
	}

}
