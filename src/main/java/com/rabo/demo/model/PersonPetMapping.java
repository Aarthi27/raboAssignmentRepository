package com.rabo.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "person_pet_mapping")
public class PersonPetMapping implements java.io.Serializable {

	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mappingSeq")
	@SequenceGenerator(name = "mappingSeq", initialValue = 1, allocationSize = 10, sequenceName = "MAPPING_SEQ")
	@Id
	@Column(name = "ID")
	private int id;

	@ManyToOne
	@JoinColumn(name = "PERSON_ID")
	private Person person;

	@ManyToOne
	@JoinColumn(name = "PET_ID")
	private Pet pet;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
	}

}
