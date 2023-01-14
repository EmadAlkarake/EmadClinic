package com.emadclinic.dentist.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emadclinic.dentist.dao.entity.PatientEntity;

@Repository

public interface PatientRepository extends JpaRepository<PatientEntity, Integer> {
	
}
