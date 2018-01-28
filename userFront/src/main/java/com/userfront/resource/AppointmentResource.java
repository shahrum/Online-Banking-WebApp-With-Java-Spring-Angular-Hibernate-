package com.userfront.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.userfront.domain.Appointment;
import com.userfront.service.AppointmentService;

@RestController
@RequestMapping("/api/appointment")
@PreAuthorize("hasRole('ADMIN')")
public class AppointmentResource {

	@Autowired
	private AppointmentService appointmentService;

	@RequestMapping("/all")
	public List<Appointment> findAppointmentList() {
		List<Appointment> appointmentlist = appointmentService.findAll();
		return appointmentlist;
	}
	
	@RequestMapping("/{id}/confirm")
	public void confirm(@PathVariable("id") Long id) {
		appointmentService.confirmAppointment(id);
	}

}
