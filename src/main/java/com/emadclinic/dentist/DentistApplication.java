package com.emadclinic.dentist;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.emadclinic.dentist.dao.entity.AppointmentEntity;
import com.emadclinic.dentist.dao.repository.AppointmentRepository;



@SpringBootApplication
public class DentistApplication {

	public static void main(String[] args) {
		SpringApplication.run(DentistApplication.class, args);
	}

	@Autowired
	AppointmentRepository repo;
	@PostConstruct
	public void name() {
		AppointmentEntity doctor=repo.findById(1).orElse(null);
		System.out.println(doctor);
	}
}
