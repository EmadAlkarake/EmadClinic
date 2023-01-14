package com.emadclinic.dentist.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emadclinic.dentist.dao.entity.User;
import com.emadclinic.dentist.dao.repository.UserRepository;
import com.emadclinic.dentist.pojo.Login;
import com.emadclinic.dentist.pojo.Result;
import com.emadclinic.dentist.utility.TokenUtility;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private TokenUtility tokenUtility;
	
	public Result login (Login login) {
		Result result = new Result();
		Map<String,Object> mapResult= new HashMap<>();
		
		User user =userRepository.findByUsername(login.getUsername());
		if(user==null) {
			mapResult.put("user", ":not found");
			result.setStatusCode(1);
			result.setStatusDescription("Failed");
			result.setResult(mapResult);
			return result;
		}
		if(!(user.getPassword().equalsIgnoreCase(login.getPassword()))) {
			mapResult.put("password", ":Inncorect password");
			result.setStatusCode(1);
			result.setStatusDescription("Failed");
			result.setResult(mapResult);
			return result;
		}
		String token=tokenUtility.generateToken(login.getUsername());
		mapResult.put("token", token);
		result.setStatusCode(0);
		result.setStatusDescription("success");
		result.setResult(mapResult);
		return result;
	}
	
	public void save(User user){
		userRepository.save(user);
	}
}
