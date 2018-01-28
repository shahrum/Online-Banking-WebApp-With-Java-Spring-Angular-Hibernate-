package com.userfront.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;
//RestController is not just a controller it says that we need it in Restful App so give the result in JSON format to me

@RestController
@RequestMapping("/api")
@PreAuthorize("hasRole('ADMIN')")
public class UserResource {

	@Autowired
	private UserService userService;

	@Autowired
	private TransactionService transactionService;

	@GetMapping("/user/all")
	public List<User> findAll() {
		System.out.println("********--------FindAllUser--------*********");
		return userService.findUserList();
	}

	@GetMapping("/user/primary/transaction")
	public List<PrimaryTransaction> getPrimaryTransactionList(@RequestParam("username") String username) {
		System.out.println("********--------findPrimaryTransactionList--------*********");
		return transactionService.findPrimaryTransactionList(username);
	}

	@GetMapping("/user/savings/transaction")
	public List<SavingsTransaction> getSavingsTransactionList(@RequestParam("username") String username) {
		System.out.println("********--------getSavingsTransactionList--------*********");
		return transactionService.findSavingsTransactionList(username);
	}
	
	@RequestMapping("/user/{username}/enable")
	public void enableUser(@PathVariable("username") String username) {
		System.out.println("********--------enableUser--------*********");
		userService.enableUser(username);
	}
	
	@RequestMapping("/user/{username}/disable")
	public void disableUser(@PathVariable("username") String username) {
		System.out.println("********--------disable--------*********");
		userService.disableUser(username);
	}

}




















