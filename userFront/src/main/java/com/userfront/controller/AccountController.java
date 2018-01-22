package com.userfront.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.User;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private UserService userService;

	@RequestMapping("/primaryAccount")
	public String primaryAccount(Model model, Principal principal) {
		User user = userService.findByUsername(principal.getName());
		PrimaryAccount primaryAccount = user.getPrimaryAccount();
		model.addAttribute("primaryAccount", primaryAccount);
		return "primaryAccount";
	}
	
	@RequestMapping("/savingsAccount")
	public String savingsAccount() {
		return "savingsAccount";
	}
}
