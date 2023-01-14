 package com.emadclinic.dentist.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.emadclinic.dentist.dao.entity.DoctorEntity;
import com.emadclinic.dentist.dao.entity.PatientEntity;
import com.emadclinic.dentist.dao.entity.User;
import com.emadclinic.dentist.dao.repository.DoctorRepository;
import com.emadclinic.dentist.pojo.Login;
import com.emadclinic.dentist.pojo.Result;
import com.emadclinic.dentist.service.DoctorService;
import com.emadclinic.dentist.service.PatientService;
import com.emadclinic.dentist.service.UserService;
import com.emadclinic.dentist.utility.TokenUtility;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private TokenUtility tokenUtility;

	@Autowired
	private DoctorService doctorService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private DoctorRepository doctorRepository;

	@Autowired
	private PatientService patientService;

////////register/add
	@PostMapping("/addDoctor")
	public Result addDoctor(@Valid @RequestBody DoctorEntity doctor) {		
		return doctorService.addDoctor(doctor);
	}

	@PostMapping("/addPatient")
	public Result addDoctor(@Valid @RequestBody PatientEntity patient) {
		return patientService.addPatient(patient);
	}
	
	@PostMapping("/login")
	public Result login(@RequestBody @Valid Login login) {
		return userService.login(login); 
	}

//	@GetMapping("/findToken")
//	public Result generateToken() {
//		String token = tokenUtility.generateToken("test");
//		Result result = new Result();
//		Map<String, Object> mapResult = new HashMap<>();
//		result.setStatusCode(0);
//
//		result.setStatusDescription("success"); 
//		mapResult.put("token", token);
//		result.setResult(mapResult);
//		return result;
//	}

	//////////////////////////////////////////////////////////////////
//	@GetMapping("/validateToken")
//	public Result validateToken(@RequestParam String token) {
//		String resultFromToken = tokenUtility.checkToken(token);
//		Result result = new Result();
//		Map<String, Object> mapResult = new HashMap<>();
//		result.setStatusCode(0);
//		result.setStatusDescription("success");
//		mapResult.put("token", resultFromToken);
//		result.setResult(mapResult);
//		return result;
//	}

//	public DoctorEntity login(String username, String password) {
//		DoctorEntity doctor =doctorRepository.findByUsername(username);
//	    if (doctor == null || doctor.getPassword().equalsIgnoreCase(password)) {
//	    	return null;
//	    }
//	    return doctor;	
//	}
//	public Result login(String username, String password) {
//		DoctorEntity doctor =doctorRepository.findByUsername(username);
//		Result result = new Result();
//		Map<String,Object> mapResult= new HashMap<>();
//	    if (doctor == null || doctor.getPassword().equalsIgnoreCase(password)) {
//	    	
//	    	return null;
//	    }
//	    return doctor;	
//	}

//////////////////////////////////////////////////////////////////////////Exceptions

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Result handleValidationExceptions(MethodArgumentNotValidException ex) {
		Result result = new Result();

		Map<String, Object> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldfName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldfName, errorMessage);
		});
		result.setStatusCode(1);
		result.setStatusDescription("Failed");

		result.setResult(errors);
		return result;

	}

///////////////////////// NumberFormatException
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public Result handleNumberFormat(NumberFormatException e) {
		Result result = new Result();
		Map<String, Object> errors = new HashMap<>();
// ResponseEntity<String> r= new ResponseEntity<>("Invalid number format, please
// check your input.", HttpStatus.BAD_REQUEST);
		errors.put("error", "Invalid number format, please check your input.");
		result.setStatusCode(1);
		result.setStatusDescription("Failed");
		result.setResult(errors);
		return result;
	}

///////////////////////////////////////////////////////////

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public Result handleAllExceptionMethod(Exception ex, WebRequest request, HttpServletResponse response) {
		Result result = new Result();
		Map<String, Object> errors = new HashMap<>();
		errors.put("Exception", ex.getMessage());
		result.setStatusCode(1);
		result.setStatusDescription("Failed");
		result.setResult(errors);
		return result;

	}

}
