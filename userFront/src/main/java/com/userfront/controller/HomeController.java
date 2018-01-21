package com.userfront.controller;

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
public class HomeController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/")
	public String home() {
		return "redirect:/index";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "index";
	}
	
	//@RequestMapping(value="/signup" , method= RequestMethod.GET)
	@GetMapping("/signup")
	public String signup(Model model) {
		
		User user = new User();
		model.addAttribute("user",user);
		
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signup(@ModelAttribute("user") User user, Model model) {
		if (userService.checkUserExists(user.getUsername(), user.getEmail())) {
			if (userService.checkUsernameExists(user.getUsername())) {
				model.addAttribute("usernameExists",true);
			}
			if (userService.checkEmailExists(user.getEmail())) {
				model.addAttribute("emailExists",true);
			}
			return "signup";
		} else {
			userService.save(user);
			return "redirect:/";
		}
		
	}
	
	
}




























