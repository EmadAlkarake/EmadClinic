package com.emadclinic.dentist.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emadclinic.dentist.dao.entity.DoctorEntity;
@Repository

public interface DoctorRepository extends JpaRepository<DoctorEntity, Integer> {

	DoctorEntity findByUsername(String username);

	
}
