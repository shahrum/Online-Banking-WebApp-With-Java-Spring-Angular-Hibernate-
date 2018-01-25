package com.userfront.service;

import java.util.List;

import com.userfront.domain.Appointment;

public interface AppointmentService {

	Appointment createAppointment(Appointment _appointment);
	
	List<Appointment> findAll();
	
	Appointment findAppointment(Long _id);
	
	void confirmAppointment(Long _id);
	
}
