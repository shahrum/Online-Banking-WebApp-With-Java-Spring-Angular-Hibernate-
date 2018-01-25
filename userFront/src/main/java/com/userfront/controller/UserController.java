package com.userfront.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userfront.domain.User;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService _userService;
	
	@GetMapping("/profile")
	public String profile(Model _model, Principal _principal) {
		User _user = _userService.findByUsername(_principal.getName());
		_model.addAttribute("user",_user);
		return "profile";
	}
	
	@PostMapping("/profile")
	public String profile(@ModelAttribute("user") User _newUser, Model _model) {
		User _user = _userService.findByUsername(_newUser.getUsername());
		
		
		_model.addAttribute("user",_user);
		
		_userService.save(_user);
		return "profile";
	}

}
