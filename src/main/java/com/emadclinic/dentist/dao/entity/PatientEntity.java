package com.emadclinic.dentist.dao.entity;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Entity
@Table(name = "patient")
@Data
public class PatientEntity {

	@Column(name = "id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "username")
	@NotNull(message = "username can't be null")
	private String username;

	@Column(name = "password")
	private String password;

	@Column(name = "name")
	private String name;

	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "age")
	@Min(message = "your age can't be negative",value = 0)
	@Max(message = "your age can't be greater than 100",value = 100)
	private Integer age;

	@Column(name = "gender")
	private String gender;
	
	
	
}
