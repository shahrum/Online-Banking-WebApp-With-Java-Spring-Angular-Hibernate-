package com.userfront.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Controller
@RequestMapping("/transfer")
public class TransferController {
	
	@Autowired
	private UserService _userService;
	
	@Autowired
	private TransactionService _transactionService;
	
	@GetMapping("/betweenAccounts")
	public String betweenAccounts(Model _model) {
		_model.addAttribute("transferFrom", "");
		_model.addAttribute("transferTo","");
		_model.addAttribute("amount","");
		return "betweenAccounts";
	}
	
	@PostMapping("betweenAccounts")
	public String betweenAccounts(@ModelAttribute("transferFrom") String _transferFrom,
								  @ModelAttribute("transferTo") String _transferTo,
								  @ModelAttribute("amount") String _amount,
								  Principal _principal) throws Exception {
		
		User _user = _userService.findByUsername(_principal.getName());
		PrimaryAccount _primaryAccounts = _user.getPrimaryAccount();
		SavingsAccount _savingsAccoutns = _user.getSavingsAccount();
		_transactionService.betweenAccountsTransfer(_transferFrom,_transferTo,_amount,_primaryAccounts,_savingsAccoutns);
		
		
		return "redirect:/userFront";
	}

}
