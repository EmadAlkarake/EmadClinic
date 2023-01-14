package com.emadclinic.dentist.pojo;

import java.util.Map;

import lombok.Data;

@Data
public class Result {

	private Integer statusCode;
	private String statusDescription;	
	private Map<String,Object> result;
}
