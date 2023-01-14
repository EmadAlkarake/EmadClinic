package com.emadclinic.dentist.dao.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.emadclinic.dentist.dao.entity.AppointmentEntity;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Integer> {
//@Query("SELECT a FROM appointment a WHERE a.doctorid =:id AND a.date BETWEEN :fromDate  AND :toDate;")
//	List<AppointmentEntity> getSummary(Integer id, LocalDate fromDate, LocalDate toDate);

	List<AppointmentEntity> findByDateAndTime(LocalDate date,LocalTime time);
//	List<AppointmentEntity> findByDateBetween(Integer id,LocalDate fromDate,LocalDate toDate);

}