package com.emadclinic.dentist.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emadclinic.dentist.dao.entity.DoctorEntity;
import com.emadclinic.dentist.dao.entity.PatientEntity;
import com.emadclinic.dentist.dao.entity.User;
import com.emadclinic.dentist.dao.repository.DoctorRepository;
import com.emadclinic.dentist.dao.repository.PatientRepository;
import com.emadclinic.dentist.pojo.Result;

@Service
public class PatientService {

	@Autowired
	private PatientRepository patientRepository;

	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private UserService userService;
	

	/////////////////////////////////////////////////////////////////////
	public Result addPatient(PatientEntity patient) {

		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (patient.getUsername() == null || patient.getUsername().isEmpty()) {
			result.setStatusCode(1);
			result.setStatusDescription("the name cant be null or empty");
			mapResult.put("the problem is that you", "can't send an empty name");
			result.setResult(mapResult);
			return result;
		}
		List<PatientEntity> patientEntities=patientRepository.findAll();
		List<String> usernames = new ArrayList<>();
		for (PatientEntity patientEntity:patientEntities) {
			usernames.add(patientEntity.getUsername());
		}
		if(usernames.contains(patient.getUsername())) {
			result.setStatusCode(1);
			result.setStatusDescription("the username cant be dublicated");
			mapResult.put("the problem is that you", "can't use that username cause it is exist");
			result.setResult(mapResult);
			return result;
		}
		
		User user = new User();
        user.setUsername(patient.getUsername());
        user.setPassword(patient.getPassword());
        userService.save(user);

		patientRepository.save(patient);
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your update ", "completed");
		result.setResult(mapResult);
		return result;
	}

	////////////////////////////////////////////////////////////////
	public Result updatePatient(PatientEntity patient) {

		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (patient.getName() == null || patient.getName().isEmpty()) {
			result.setStatusCode(1);
			result.setStatusDescription("the name cant be null or empty");
			mapResult.put("the problem is that you", "can't send an empty name");
			result.setResult(mapResult);
			return result;
		}
		if (patient.getId() == null) {
			result.setStatusCode(1);
			result.setStatusDescription("the id cant be null ");
			mapResult.put("the problem is that you", "can't send an empty");
			result.setResult(mapResult);
			return result;
		}
		if (patient.getId() < 0) {
			result.setStatusCode(1);
			result.setStatusDescription("the id cant be negative ");
			mapResult.put("the problem is that you", "can't send a negative id ");
			result.setResult(mapResult);
			return result;
		}
		PatientEntity current=patientRepository.getById(patient.getId());
		patient.setUsername(current.getUsername());
		
		User user = new User();
        user.setUsername(current.getUsername());
        user.setPassword(patient.getPassword());
        userService.save(user);
		
		
		
		patientRepository.save(patient);
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your update ", "completed");
		result.setResult(mapResult);
		return result;
	}

	//////////////////////////////////////////////////////////////////
	
	
	public Result seeAllDoctors() {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		List<DoctorEntity> doctors= doctorRepository.findAll();
		List<String> names = new ArrayList<>();
		for (DoctorEntity doctorEntity : doctors) {
			names.add(doctorEntity.getName());
		}
		
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("Doctors names : ",names);
		result.setResult(mapResult);
		return result;
	}
	//////////////////////////////////////////////////////////////////
	public Result findallPatients() {

		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your order is  ", patientRepository.findAll());
		result.setResult(mapResult);
		return result;

	}

	///////////////////////////////////////////////////////////////////
	public Result findPatientById(Integer id) {

		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (id == null || id < 0) {
			result.setStatusCode(1);
			result.setStatusDescription("the id cant be null or negative ");
			mapResult.put("the problem is that you", "can't send an empty or negative id");
			result.setResult(mapResult);
			return result;
		}

		//patientRepository.findById(id).orElse(new PatientEntity());
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your information", patientRepository.findById(id).orElse(new PatientEntity()));
		result.setResult(mapResult);
		return result;
	}

	////////////////////////////////////////////////////////////////////////////


}
