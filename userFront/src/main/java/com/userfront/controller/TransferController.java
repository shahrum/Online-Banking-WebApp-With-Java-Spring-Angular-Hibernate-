package com.userfront.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/transfer")
public class TransferController {
	
	@GetMapping("/betweenAccounts")
	public String betweenAccounts(Model model) {
		model.addAttribute("transferFrom", "");
		model.addAttribute("transferTo","");
		model.addAttribute("amount","");
		return "betweenAccounts";
	}
	
	@PostMapping("betweenAccounts")
	public String betweenAccounts(@ModelAttribute("transferFrom") String transferFrom,
								  @ModelAttribute("transferTo") String transferTo,
								  @ModelAttribute("amount") String amount,
								  Principal principal) throws Exception {
		
		
		
		return "redirect:/userFront";
	}

}
