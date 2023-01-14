package com.emadclinic.dentist.service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emadclinic.dentist.dao.entity.DoctorEntity;
import com.emadclinic.dentist.dao.entity.User;
import com.emadclinic.dentist.dao.repository.DoctorRepository;
import com.emadclinic.dentist.dao.repository.UserRepository;
import com.emadclinic.dentist.pojo.Result;



@Service
public class DoctorService {
	
	@Autowired
	private DoctorRepository doctorRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserService userService;
	
	public Result addDoctor(DoctorEntity doctor) {

		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		
		
		if (doctor.getUsername() == null || doctor.getUsername().isEmpty()) {
			result.setStatusCode(1);
			result.setStatusDescription("the username cant be null or empty");
			mapResult.put("the problem is that you", "can't send an empty username");
			result.setResult(mapResult);
			return result;
		}
		List<DoctorEntity> doctorEntities=doctorRepository.findAll();
		List<String> usernames = new ArrayList<>();
		List<String> nationalIds = new ArrayList<>();
		for (DoctorEntity doctorEntity:doctorEntities) {
			usernames.add(doctorEntity.getUsername());
		}
		for (DoctorEntity doctorEntity:doctorEntities) {
			nationalIds.add(doctorEntity.getNationalId());
		}
		if(usernames.contains(doctor.getUsername())) {
			result.setStatusCode(1);
			result.setStatusDescription("the username cant be dublicated");
			mapResult.put("the problem is that you", "can't use that username cause it is exist");
			result.setResult(mapResult);
			return result;
		}
		if( nationalIds.contains(doctor.getNationalId())){
			result.setStatusCode(1);
			result.setStatusDescription("the national id cant be dublicated");
			mapResult.put("the problem is that you", "can't use that national id cause it is exist");
			result.setResult(mapResult);
			return result;
		}
		
		doctor.setStartTime(LocalTime.of(8, 0));
		doctor.setEndTime(LocalTime.of(17, 0));
		doctorRepository.save(doctor);		
		
		User user = new User();
        user.setUsername(doctor.getUsername());
        user.setPassword(doctor.getPassword());
        userService.save(user);
		
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your update ", "completed");
		result.setResult(mapResult);
		return result;
	}
	//////////////////////////////////////////////////////////////////add doctor
	public Result updateDoctor(DoctorEntity doctor) {

		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (doctor.getName() == null || doctor.getName().isEmpty()) {
			result.setStatusCode(1);
			result.setStatusDescription("the name cant be null or empty");
			mapResult.put("the problem is that you", "can't send an empty name");
			result.setResult(mapResult);
			return result;
		}
		if (doctor.getId() == null ) {
			result.setStatusCode(1);
			result.setStatusDescription("the id cant be null ");
			mapResult.put("the problem is that you", "can't send an empty");
			result.setResult(mapResult);
			return result;
		}
		if (doctor.getId() < 0) {
			result.setStatusCode(1);
			result.setStatusDescription("the id cant be negative ");
			mapResult.put("the problem is that you", "can't send a negative id ");
			result.setResult(mapResult);
			return result;
		}
		DoctorEntity current=doctorRepository.getById(doctor.getId());
		doctor.setUsername(current.getUsername());
		
		doctor.setStartTime(LocalTime.of(8, 0));
		doctor.setEndTime(LocalTime.of(17, 0));
		
		User user = new User();
        user.setUsername(current.getUsername());
        user.setPassword(doctor.getPassword());
        userService.save(user);

		
		doctorRepository.save(doctor);
		
		
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your update ", "completed");
		result.setResult(mapResult);
		return result;
	}
	///////////////////////////////////////////////////////////update doctor
	

}
