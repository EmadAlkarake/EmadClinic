package com.emadclinic.dentist.controller;

import java.time.LocalDate;
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
import com.emadclinic.dentist.dao.entity.DoctorEntity;
import com.emadclinic.dentist.pojo.Result;
import com.emadclinic.dentist.service.AppointmentService;
import com.emadclinic.dentist.service.DoctorService;
import com.emadclinic.dentist.utility.TokenUtility;

@RestController
@RequestMapping("/doctor")
public class DoctorController {

	@Autowired
	private DoctorService doctorService;

	@Autowired
	private AppointmentService appointmentService;

	@Autowired
	private TokenUtility tokenUtility;
//	//////// register/add
//	@PostMapping("/addDoctor")
//	public Result addDoctor(@Valid @RequestBody DoctorEntity doctor) {
//		return doctorService.addDoctor(doctor);
//	}

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
	//////////////////////////////////////////token
	///////////// update
	@PutMapping("/updateDoctor")
	public Result updateDoctor(@Valid @RequestBody DoctorEntity doctor,@RequestParam String token,HttpServletRequest requset,HttpServletResponse response) {
		return doctorService.updateDoctor(doctor);
	}

	//////////// the doctor can see all available time
	@GetMapping("/allAvailableTime")
	public Result allAvailableTime(@RequestParam Integer id,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.allAvailableTime(id);
		}else {
			return result;
		}
		
	}

	/////// The Doctor Can See Booked Timeline
	@GetMapping("/allBookedTimetById")
	public Result allBookedTimetById(@RequestParam Integer id,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.allBookedTimetById(id);
		}else {
			return result;
		}
		
	}

	///////////////// The Doctor can cancel an appointment
	@DeleteMapping("/deleteAppointmentByDoctorById")
	public Result deleteAppointmentForPatientById(@RequestParam Integer id,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.deleteAnAppointmentById(id);
		}else {
			return result;
		}
		
	}

	/////////////// updateAppointment
	@GetMapping("/updateAppointment")
	public Result updateAppointment(@RequestParam Integer appointmentId, @RequestParam String status,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.updateAppointment(appointmentId, status);
		}else {
			return result;
		}
		
	}

	///////////////////// doctor can get the summary for all
	@GetMapping("/patinetProfile")
	public Result patinetProfile(@RequestParam Integer id,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.patinetProfile(id);
		}else {
			return result;
		}
		
	}

	///////////////// check how many the patient visited the doctor
	@GetMapping("/patientVisitedCount")
	public Result patientVisitedCount(@RequestParam Integer doctorId, @RequestParam Integer patientId,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.patientVisitedCount(doctorId, patientId);
		}else {
			return result;
		}
		
	}

	/////////////// seeAllPatients
	@GetMapping("/seeAllPatients")
	public Result seeAllPatients(HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.seeAllPatients();
		}else {
			return result;
		}
		
	}

	@GetMapping("numnerOfPatientVisitedDoctor")
	public Result numnerOfPatientVisitedDoctor(@RequestParam Integer doctorId, @RequestParam Integer patientId,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.numnerOfPatientVisitedDoctor(doctorId, patientId);
		}else {
			return result;
		}
		
	}

	@PostMapping("/createAppointmentByDoctor")
	public Result createAppointmentByDoctor(@Valid @RequestBody AppointmentEntity appointmentEntity,HttpServletRequest requset,HttpServletResponse response) {
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.CreateAppointmentForPatient(appointmentEntity);
		}else {
			return result;
		}
		

	}

	/////////////////////////////////////////////////////////////////////////////
//////////////////////////////////////////////////////////////////
	@GetMapping("/downloadSummary")
	public Result downloadSummary(@RequestParam Integer id, @RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate fromDate,
			@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate toDate,HttpServletRequest requset,HttpServletResponse response) {
		
		Result result=tokenUtility.checkToken(requset.getHeader("token"));
		if(result.getStatusDescription().equalsIgnoreCase("success")) {
			return appointmentService.getSummary(id, fromDate, toDate);
		}else {
			return result;
		}
		
	}

	////////////////////////////////////////////////////////////////////////// Exceptions

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
