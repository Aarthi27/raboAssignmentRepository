package com.rabo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rabo.demo.model.PersonPetMapping;

@Repository
public interface PersonPetRepository extends CrudRepository<PersonPetMapping, Integer> {

	public Boolean existsByPersonId(Integer id);

	@Query(value = "SELECT COUNT(*) FROM PERSON_PET_MAPPING WHERE PET_ID =?1", nativeQuery = true)
	public int existsByPetId(Integer id);

	@Modifying
	@Query(value = "DELETE FROM PERSON_PET_MAPPING WHERE PERSON_ID = ?1", nativeQuery = true)
	public void deleteByPersonId(Integer id);

	@Modifying
	@Query(value = "DELETE FROM PERSON_PET_MAPPING WHERE PET_ID = ?1", nativeQuery = true)
	public void deleteByPetId(Integer id);
}
