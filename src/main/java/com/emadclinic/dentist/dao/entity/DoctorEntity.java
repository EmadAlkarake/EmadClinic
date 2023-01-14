package com.emadclinic.dentist.dao.entity;

import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "doctor")
@Data
public class DoctorEntity {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull(message = "username can't be null")
	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	
	@Column(name = "national_id")
	private String nationalId;

	@Column(name = "specialty")
	private String specialty;

	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name="start_time")
	 private  LocalTime startTime=LocalTime.of(8, 0);

	 @Column(name="end_time")
	 private   LocalTime endTime= LocalTime.of(17, 0);
	 
	
}
