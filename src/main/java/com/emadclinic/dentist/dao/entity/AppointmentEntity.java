package com.emadclinic.dentist.dao.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Entity
@Table(name = "appointment")
@Data
public class AppointmentEntity {
	
	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name="doctorid",referencedColumnName = "id")
	private DoctorEntity doctorObj;
	
	@ManyToOne
	@JoinColumn(name="patientid",referencedColumnName = "id")
	private PatientEntity patientObj;
	
	@JsonFormat(pattern = "dd/MM/yyyy",shape = Shape.STRING)
	@Column(name="date")
	private LocalDate date;
	
	@JsonFormat(pattern = "HH:mm",shape = Shape.STRING)
	@Column(name="time")
	private LocalTime time;
	
}
