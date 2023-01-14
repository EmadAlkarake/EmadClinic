package com.emadclinic.dentist.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.emadclinic.dentist.dao.entity.AppointmentEntity;
import com.emadclinic.dentist.dao.entity.PatientEntity;
import com.emadclinic.dentist.pojo.Result;
import com.emadclinic.dentist.service.AppointmentService;
import com.emadclinic.dentist.service.PatientService;
import com.emadclinic.dentist.utility.TokenUtility;

@RestController
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@Autowired
	private AppointmentService appointmentService;
	@Autowired
	private TokenUtility tokenUtility;

//	@PostMapping("/addPatient")
//	public Result addDoctor(@Valid @RequestBody PatientEntity patient) {
//		return patientService.addPatient(patient);
//	}

	@PutMapping("/updatePatient")
	public Result updateDoctor(@Valid @RequestBody PatientEntity patient,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return patientService.updatePatient(patient);
		}else {
			return result;
		}
		
	}

	@GetMapping("/seeAllDoctors")
	public Result seeAllDoctors(HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return patientService.seeAllDoctors();
		}else {
			return result;
		}
		
	}

	@DeleteMapping("/deleteAppointmentByPatientById")

	public Result deleteAppointmentByPatientById(@RequestParam Integer id,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.deleteAnAppointmentById(id);
		}else {
			return result;
		}
		
	}

	@GetMapping("/findAllOfPatients")
	public Result findAllPatients(HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return patientService.findallPatients();
		}else {
			return result;
		}
		
	}

	@GetMapping("/findPatientById")
	public Result findPatientById(@RequestParam Integer id,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return patientService.findPatientById(id);
		}else {
			return result;
		}
		
	}

	@DeleteMapping("deleteAnAppointmentByPatient")
	public Result deleteAnAppointmentByPatient(Integer id,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.deleteAnAppointmentById(id);
		}else {
			return result;
		}
		
	}

	@GetMapping("patientShowAllAvailableDoctors")
	public Result patientShowAllAvailableDoctors(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate date,
			@RequestParam @DateTimeFormat(pattern = "HH:mm") LocalTime time,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.findByDateAndTime(date, time);
		}else {
			return result;
		}
		
	}

	@PostMapping("/createAppointmentByPatient")
	public Result createAppointmentByDoctor(@Valid @RequestBody AppointmentEntity appointmentEntity,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.CreateAppointmentForPatient(appointmentEntity);
		}else {
			return result;
		}
		

	}

	@GetMapping("showReportFprPatient")
	public Result showReport(@RequestParam Integer id, @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate,HttpServletRequest requset,HttpServletResponse response) {

		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.showReport(id, fromDate, toDate);
		}else {
			return result;
		}
		
	}

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
