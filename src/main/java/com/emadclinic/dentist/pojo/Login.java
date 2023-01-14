package com.emadclinic.dentist.pojo;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class Login {
	
	@NotBlank(message = "username can't be null or empty")
	public String username;
	
	@NotBlank(message = "password can't be null or empty")
	public String password;
}
