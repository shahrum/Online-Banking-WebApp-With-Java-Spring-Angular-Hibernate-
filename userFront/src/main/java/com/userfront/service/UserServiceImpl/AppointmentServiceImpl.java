package com.userfront.service.UserServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.AppointmentDao;
import com.userfront.domain.Appointment;
import com.userfront.service.AppointmentService;

@Service
public class AppointmentServiceImpl implements AppointmentService {

	@Autowired
	private AppointmentDao _appointmentDao;
	
	
	
	public Appointment createAppointment(Appointment _appointment) {
		
		return _appointmentDao.save(_appointment);

	}
	
	public List<Appointment> findAll(){
		return _appointmentDao.findAll();
	}
	
	public Appointment findAppointment(Long _id) {
		return _appointmentDao.findOne(_id);
	}
	
	public void confirmAppointment(Long _id) {
		Appointment _appointment = findAppointment(_id);
		_appointment.setConfirmed(true);
		_appointmentDao.save(_appointment);
	}

}
