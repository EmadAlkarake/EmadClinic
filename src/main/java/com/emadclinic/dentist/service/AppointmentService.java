package com.emadclinic.dentist.service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emadclinic.dentist.dao.entity.AppointmentEntity;
import com.emadclinic.dentist.dao.entity.DoctorEntity;
import com.emadclinic.dentist.dao.entity.PatientEntity;
import com.emadclinic.dentist.dao.repository.AppointmentRepository;
import com.emadclinic.dentist.dao.repository.DoctorRepository;
import com.emadclinic.dentist.dao.repository.PatientRepository;
import com.emadclinic.dentist.pojo.Result;

@Service
public class AppointmentService {

	@Autowired
	AppointmentRepository appointmentRepository;

	@Autowired
	PatientRepository patientRepository;

	@Autowired
	DoctorRepository doctorRepository;

	public Result allAvailableTime(Integer id) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (id == null) {
			result.setStatusCode(1);
			result.setStatusDescription("the id cant be null or empty");
			mapResult.put("the problem is that you", "can't send an empty id or null");
			result.setResult(mapResult);
			return result;
		}
		List<DoctorEntity> doctors = doctorRepository.findAll();
		List<Integer> ids = new ArrayList<>();
		for (DoctorEntity doctorEntity : doctors) {
			ids.add(doctorEntity.getId());
		}
		if (!(ids.contains(id)) || id < 0) {
			result.setStatusCode(1);
			result.setStatusDescription("the id is not exist");
			mapResult.put("the problem is that you", "can't send an id is not exist");
			result.setResult(mapResult);
			return result;
		}

		LocalDate today = LocalDate.now();
		result.setStatusCode(0);
		result.setStatusDescription("success");
		List<AppointmentEntity> appointments = appointmentRepository.findAll();
		List<LocalTime> times = new ArrayList<>();
		for (int i = 0; i < 9; i++) {
			times.add(LocalTime.of(i + 8, 0));
		}
		for (AppointmentEntity appointment : appointments) {
			if ((appointment.getDoctorObj().getId() == id && appointment.getDate().equals(today))) {
				times.remove(appointment.getTime());
			}
		}
		mapResult.put("your available time/s: ", times);
		result.setResult(mapResult);
		return result;
	}
	///////////////////////////////////////////////////////////// the doctor can see
	///////////////////////////////////////////////////////////// all available time

	public Result allBookedTimetById(Integer id) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		if (id == null || id < 0) {
			result.setStatusCode(1);
			result.setStatusDescription("the id cant be null or empty");
			mapResult.put("the problem is that you", "can't send an empty id or null");
			result.setResult(mapResult);
			return result;
		}
		List<DoctorEntity> doctors = doctorRepository.findAll();
		List<Integer> doctorIds = new ArrayList<>();
		for (DoctorEntity doctor : doctors) {
			if (doctor.getId().equals(id)) {
				doctorIds.add(doctor.getId());
			}
		}
		if (!(doctorIds.contains(id))) {
			result.setStatusCode(1);
			result.setStatusDescription("the id is not found");
			mapResult.put("the problem is that you", "You cannot send a name that does not exist");
			result.setResult(mapResult);
			return result;
		}

		LocalDate today = LocalDate.now();
		result.setStatusCode(0);
		result.setStatusDescription("success");
		List<AppointmentEntity> appointments = appointmentRepository.findAll();
		List<Object> times = new ArrayList<>();
		for (AppointmentEntity appointment : appointments) {
			if (appointment.getDoctorObj().getId() == id && appointment.getDate().equals(today)) {
				times.add("time is :" + appointment.getTime() + " patinent id = " + appointment.getPatientObj().getId()
						+ " patinet name is:" + appointment.getPatientObj().getName());
			}
		}
		mapResult.put("your date/s: ", times);
		result.setResult(mapResult);
		return result;
	}

	//////////////////////////////////////////////////////////////// The Doctor Can
	//////////////////////////////////////////////////////////////// See Booked
	//////////////////////////////////////////////////////////////// Timeline

	public Result deleteAnAppointmentById(Integer id) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();

		List<AppointmentEntity> appointments = appointmentRepository.findAll();
		List<Integer> ids = new ArrayList<>();
		for (AppointmentEntity appointmentEntity : appointments) {
			ids.add(appointmentEntity.getId());
		}
		if (!(ids.contains(id))) {
			result.setStatusCode(0);
			result.setStatusDescription("Failed");
			mapResult.put("you don't have  ", "an appointment has that id");
			result.setResult(mapResult);
			return result;
		}
		AppointmentEntity appointment = appointmentRepository.findById(id).orElse(null);

		appointmentRepository.delete(appointment);
		result.setStatusCode(1);
		result.setStatusDescription("success");
		mapResult.put("your deelete ", "is done");
		result.setResult(mapResult);
		return result;

	}
	////////////////////////////////////////////////////// The Doctor can cancel an
	////////////////////////////////////////////////////// appointment

	/////////////////////////////////////////////////////////////////////////////////////////
	public Result deleteAnAppointmentByPatient(Integer id) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();

		List<AppointmentEntity> appointments = appointmentRepository.findAll();
		List<Integer> ids = new ArrayList<>();
		for (AppointmentEntity appointmentEntity : appointments) {
			ids.add(appointmentEntity.getId());
		}
		if (!(ids.contains(id))) {
			result.setStatusCode(0);
			result.setStatusDescription("Failed");
			mapResult.put("you don't have  ", "an appointment has that id");
			result.setResult(mapResult);
			return result;
		}

		AppointmentEntity appointment = appointmentRepository.findById(id).orElse(null);

		appointmentRepository.delete(appointment);
		result.setStatusCode(1);
		result.setStatusDescription("success");
		mapResult.put("your delete ", "is done");
		result.setResult(mapResult);
		return result;

	}

	public Result patinetProfile(Integer id) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		List<PatientEntity> patientEntities = patientRepository.findAll();
		List<Integer> ids = new ArrayList<>();
		for (PatientEntity patientEntity : patientEntities) {
			if (patientEntity.getId() == id) {
				ids.add(patientEntity.getId());
			}
		}

		for (PatientEntity patient : patientEntities) {
			if (patient.getId() == null || patient.getId() < 0 || !(ids.contains(id))) {
				result.setStatusCode(1);
				result.setStatusDescription("the patient id cant be null or empty or doesn't exist");
				mapResult.put("the problem is that you", "can't send an empty id or null or doesn't exist");
				result.setResult(mapResult);
				return result;
			}

		}
		Map<String, String> details = new HashMap<>();
		for (PatientEntity patient : patientEntities) {
			if (patient.getId() == id) {
				details.put("Name", patient.getName());
				details.put("Phone Number", patient.getPhoneNumber());
				details.put("Age", patient.getAge() + "");
				details.put("Gender", patient.getGender());
			}
		}
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("Patient details : ", details);
		result.setResult(mapResult);
		return result;

	}
	/////////////////////////////////////////////// doctor can get the summary for
	/////////////////////////////////////////////// all

	public Result numnerOfPatientVisitedDoctor(Integer doctorId, Integer patientId) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();

		List<DoctorEntity> doctors = doctorRepository.findAll();
		List<PatientEntity> patients = patientRepository.findAll();
		List<Integer> doctorIds = new ArrayList<>();
		List<Integer> patientIds = new ArrayList<>();
		for (DoctorEntity doctor : doctors) {
			if (doctor.getId().equals(doctorId)) {
				doctorIds.add(doctor.getId());
			}
		}
		for (PatientEntity patient : patients) {
			if (patient.getId().equals(patientId)) {
				patientIds.add(patient.getId());
			}
		}
		if (doctorId == null || doctorId < 0 || !(doctorIds.contains(doctorId))) {
			result.setStatusCode(1);
			result.setStatusDescription("the doctor id cant be null or empty or doesn't exist");
			mapResult.put("the problem is that you", "can't send an empty id or null or doesn't exist");
			result.setResult(mapResult);
			return result;
		}
		if (patientId == null || patientId < 0 || !(patientIds.contains(patientId))) {
			result.setStatusCode(1);
			result.setStatusDescription("the patient id cant be null or empty or doesn't exist");
			mapResult.put("the problem is that you", "can't send an empty id or null or doesn't exist");
			result.setResult(mapResult);
			return result;
		}

		List<AppointmentEntity> appointments = appointmentRepository.findAll();

		Integer count = 0;
		for (AppointmentEntity appointment : appointments) {
			if (appointment.getDoctorObj().getId().equals(doctorId)
					&& (appointment.getPatientObj().getId().equals(patientId))) {
				count++;
			}
		}
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("The doctor has ", count + " visits");
		result.setResult(mapResult);
		return result;

	}
	//////////////////////////////////////////// check how many the patient visited
	//////////////////////////////////////////// the doctor

	public Result updateAppointment(Integer appointmentId, String status) {

		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		List<AppointmentEntity> appointments = appointmentRepository.findAll();
		List<Integer> ids = new ArrayList<>();
		for (AppointmentEntity appointment : appointments) {
			if (appointment.getId() == appointmentId) {
				ids.add(appointment.getId());
			}
		}
		if (appointmentId == null || appointmentId < 0 || !(ids.contains(appointmentId))) {
			result.setStatusCode(0);
			result.setStatusDescription("Faild");
			mapResult.put("failed", "there is no an appointment has that id");
			result.setResult(mapResult);
			return result;
		} else {
			if (status.equals("0")) {
				result.setStatusCode(1);
				result.setStatusDescription("success");
				mapResult.put("status" + status, "visited him.");
				result.setResult(mapResult);
				return result;
			}
			if (status.equals("1")) {
				result.setStatusCode(1);
				result.setStatusDescription("success");
				mapResult.put("status" + status, " not visited him.");
				result.setResult(mapResult);
				return result;
			} else {

				result.setStatusCode(0);
				result.setStatusDescription("Faild");
				mapResult.put("failed", "you have to enter 0 or 1 ");
				result.setResult(mapResult);
				return result;
			}
		}
	}

	/////////////////////////////////////////////////////////// update appointment
	public Result patientVisitedCount(Integer doctorId, Integer patientId) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		List<AppointmentEntity> appointments = appointmentRepository.findAll();
		List<DoctorEntity> doctors = doctorRepository.findAll();
		List<PatientEntity> patients = patientRepository.findAll();

		List<Integer> doctorIds = new ArrayList<>();
		List<Integer> patientIds = new ArrayList<>();
		Integer count = 0;
		for (DoctorEntity doctor : doctors) {
			if (doctor.getId() == doctorId) {
				doctorIds.add(doctor.getId());
			}
		}
		for (PatientEntity patient : patients) {
			if (patient.getId() == patientId) {
				patientIds.add(patient.getId());
			}
		}
		for (AppointmentEntity appointment : appointments) {
			if (appointment.getDoctorObj().getId() == null || appointment.getDoctorObj().getId() < 0
					|| !(doctorIds.contains(doctorId)) || !(patientIds.contains(patientId))) {
				result.setStatusCode(0);
				result.setStatusDescription("Faild");
				mapResult.put("failed", "there is no a doctor or patient has that id");
				result.setResult(mapResult);
				return result;
			}
		}

		for (AppointmentEntity appointment : appointments) {
			if (appointment.getDoctorObj().getId() == doctorId && appointment.getPatientObj().getId() == patientId) {
				count++;
			}
		}

		result.setStatusCode(1);
		result.setStatusDescription("success");
		mapResult.put("The number of patient visit/s =", count);
		result.setResult(mapResult);
		return result;
	}

	public Result CreateAppointmentForPatient(AppointmentEntity appointmentEntity) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		appointmentRepository.save(appointmentEntity);
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your appointment ", " is completed");
		result.setResult(mapResult);
		return result;

	}

	/////////////////////////////////////////////////////////
	public Result CreateAppointmentByPatient(AppointmentEntity appointmentEntity) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		appointmentRepository.save(appointmentEntity);
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your appointment ", " is completed");
		result.setResult(mapResult);
		return result;

	}

////////////////////////////////////////////////////////////////////

	public Result seeAllPatients() {

		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();

		List<PatientEntity> patientEntities = patientRepository.findAll();
		List<String> names = new ArrayList<>();
		for (PatientEntity patientEntity : patientEntities) {
			names.add(patientEntity.getName());
		}
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("Patient names : ", names);
		result.setResult(mapResult);
		return result;
	}
	//////////////////////////////////////////////////////////////////////

	public Result findByDateAndTime(LocalDate date, LocalTime time) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		List<AppointmentEntity> appointmentEntities = appointmentRepository.findByDateAndTime(date, time);
		List<Integer> busyDoctorIds = new ArrayList<>();
		for (AppointmentEntity appointmentEntity : appointmentEntities) {
			busyDoctorIds.add(appointmentEntity.getDoctorObj().getId());
		}
		List<DoctorEntity> availableDoctors = doctorRepository.findAll();
		for (int i = 0; i < availableDoctors.size(); i++) {
			DoctorEntity doctor = availableDoctors.get(i);
			if (busyDoctorIds.contains(doctor.getId())) {
				availableDoctors.remove(i);
				i--;
			}
		}
		List<String> names = new ArrayList<>();
		for (DoctorEntity doctorEntity : availableDoctors) {
			names.add(doctorEntity.getName());
		}
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("Available doctor names : ", names);
		result.setResult(mapResult);
		return result;
	}


	public Result getSummary(Integer id, LocalDate fromDate, LocalDate toDate) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();

		List<AppointmentEntity> allAppointments = appointmentRepository.findAll();

		try (PrintWriter writer = new PrintWriter("D:\\New folder\\summaryDoctorWithId" + id + ".csv");) {

			writer.print("Date,Time\n");
			for (AppointmentEntity appointment : allAppointments) {
				if (appointment.getDoctorObj().getId().equals(id) && appointment.getDate().isAfter(fromDate)
						&& appointment.getDate().isBefore(toDate)) {

					writer.print(appointment.getDate() + "," + appointment.getTime() + "\n");

					writer.flush();
				}
			}

			result.setStatusCode(0);
			result.setStatusDescription("successful");
			mapResult.put("your summary : ", "is downloaded");
			result.setResult(mapResult);
			return result;

		} catch (FileNotFoundException e) {
			result.setStatusCode(1);
			result.setStatusDescription("Failed");
			mapResult.put("exception ", e.getMessage());
			result.setResult(mapResult);
			return result;

		}
	}
	/////////////////////////////////////////////////////////////////////////////////

	public Result showReport(Integer id, LocalDate fromDate, LocalDate toDate) {
		Result result = new Result();
		Map<String, Object> mapResult = new HashMap<>();
		List<AppointmentEntity> appointmentEntities = appointmentRepository.findAll();
		List<String> dates= new ArrayList<>();
		for (AppointmentEntity appointmentEntity : appointmentEntities) {
			if (appointmentEntity.getPatientObj().getId().equals(id) && appointmentEntity.getDate().isAfter(fromDate)
					&& appointmentEntity.getDate().isBefore(toDate)) {
				dates.add("date: "+appointmentEntity.getDate()+"/time :"+appointmentEntity.getTime());
			}
		}
		result.setStatusCode(0);
		result.setStatusDescription("success");
		mapResult.put("your appointments are in: ", dates);
		result.setResult(mapResult);
		return result;
		
	}

}
