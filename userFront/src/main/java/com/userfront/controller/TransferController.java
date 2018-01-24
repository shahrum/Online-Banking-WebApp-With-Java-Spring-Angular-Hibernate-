package com.userfront.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;

import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.Recipient;
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

	@GetMapping("/recipient")
	public String recipient(Model _model, Principal _principal) {
		
		List<Recipient> _recipientList = _transactionService.findRecipientList(_principal);
		
		Recipient _recipient = new Recipient();
		
		_model.addAttribute("recipient", _recipient);
		_model.addAttribute("recipientList", _recipientList);
		return "recipient";
	}
	
	@PostMapping("/recipient/save")
	public String recipient(@ModelAttribute("recipient") Recipient _recipient, Principal _principal) {
		User _user = _userService.findByUsername(_principal.getName());
		_recipient.setUser(_user);
		_transactionService.saveRecipient(_recipient);
		
		return "redirect:/transfer/recipient";
	}
	
	@GetMapping("/recipient/edit")
	public String recipientEdit(@RequestParam(value="recipientName") String _recipientName, Model _model, Principal _principal) {
		Recipient _recipient = _transactionService.findRecipientByName(_recipientName);
		List<Recipient> _recipientList= _transactionService.findRecipientList(_principal);
		_model.addAttribute("recipient", _recipient);
		_model.addAttribute("recipientList", _recipientList);
		return "recipient";
	}
	
	@GetMapping("/recipient/delete")
	public String recipientDelete(@RequestParam("recipientName") String _recipientName, Model _model, Principal _principal) {
		_transactionService.deleteRecipientByName(_recipientName);
		List<Recipient> _recipientList = _transactionService.findRecipientList(_principal);
		Recipient _recipient = new Recipient();
		_model.addAttribute("recipient", _recipient);
		_model.addAttribute("recipientList", _recipientList);
		
		return "recipient";
	}
	
}





























