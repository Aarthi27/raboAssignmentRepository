package com.rabo.demo.constants;

public interface MappingURL {
	String PERSON_ADD = "/persons";
	String PERSON_GET_ALL = "/persons";
	String PERSON_GET_BY_ID = "/person/{id}";
	String PERSON_SEARCH_BY_NAME = "/person";
	String PERSON_DELETE_BY_ID = "/person/{id}";
	String PERSON_DELETE_ALL = "/person";
	String PERSON_UPDATE = "/person";
	String PERSON_PET_MAPPING = "/personpet";

	String PET_ADD = "/pet";
	String PET_GET_ALL = "/pets";
	String PET_GET_BY_ID = "/pet/{id}";
	String PET_DELETE_BY_ID = "/pet/{id}";
	String PET_DELETE_ALL = "/pets";
	String PET_UPDATE = "/pet";

}
