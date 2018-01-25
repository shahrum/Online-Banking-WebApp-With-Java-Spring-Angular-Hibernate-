package com.userfront.controller;

import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userfront.domain.Appointment;
import com.userfront.domain.User;
import com.userfront.service.AppointmentService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/appointment")
public class AppointmentController {
	
	@Autowired
	private UserService _userService;
	
	@Autowired
	private AppointmentService _appointmentService;
	
	@GetMapping("/create")
	public String createAppointment(Model _model) {
		Appointment _appointment = new Appointment();
		
		_model.addAttribute("appointment",_appointment);
		_model.addAttribute("dateString","");
		
		return "appointment";
	}
	
	@PostMapping("/create")
	public String createAppointment(@ModelAttribute("appointment") Appointment _appointment,@ModelAttribute("dateString") String _date,
									Principal _principal, Model model) throws ParseException{
		
		SimpleDateFormat _format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm"); 
		Date _d1 = _format1.parse(_date);
		_appointment.setDate(_d1);
		
		User _user = _userService.findByUsername(_principal.getName());
		_appointment.setUser(_user);
		
		_appointmentService.createAppointment(_appointment);
		
		return "redirect:/userFront";
	}

}
