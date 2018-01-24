package com.userfront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.PrimaryTransactionDao;
import com.userfront.dao.RecipientDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.dao.SavingsTransactionDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
import com.userfront.domain.Recipient;
import com.userfront.domain.SavingsAccount;
import com.userfront.domain.SavingsTransaction;
import com.userfront.domain.User;
import com.userfront.service.TransactionService;
import com.userfront.service.UserService;

@Service
public class TransactionServiceImpl implements TransactionService {
	@Autowired
	private UserService userService;
	
	@Autowired
	private PrimaryTransactionDao priamryTransactionDao;
	
	@Autowired
	private SavingsTransactionDao savingsTransactionDao;
	
	@Autowired
	private PrimaryAccountDao _primaryAccountDao;
	
	@Autowired
	private SavingsAccountDao _savingsAccountDao;
	
	@Autowired
	private RecipientDao _recipientDao;
	
	
	@Override
	public List<PrimaryTransaction> findPrimaryTransactionList(String username) {
		User user = userService.findByUsername(username);
		List<PrimaryTransaction> primaryTransactionList = user.getPrimaryAccount().getPrimaryTransactionList();
		
		return primaryTransactionList;
	}
	
	public List<SavingsTransaction> findSavingsTransactionList(String username){
		
		User user = userService.findByUsername(username);
		List<SavingsTransaction> savingTransactionList = user.getSavingsAccount().getSavingsTransactionList();
		
		return savingTransactionList;
	}

	@Override
	public void savePrimaryDepositTransaction(PrimaryTransaction primaryTransaction) {
		priamryTransactionDao.save(primaryTransaction);
		
	}

	@Override
	public void saveSavingsDepositTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
		
	}

	@Override
	public void savePrimaryWithdrawTransaction(PrimaryTransaction primaryTransaction) {
		priamryTransactionDao.save(primaryTransaction);
		
	}

	@Override
	public void saveSavingsWithdrawTransaction(SavingsTransaction savingsTransaction) {
		savingsTransactionDao.save(savingsTransaction);
		
	}

	@Override
	public void betweenAccountsTransfer(String _transferFrom, String _transferTo, String _amount,
			PrimaryAccount _primaryAccounts, SavingsAccount _savingsAccoutns) throws Exception{
		if (_transferFrom.equalsIgnoreCase("Primary") && _transferTo.equalsIgnoreCase("Savings")) {
			
			//---------Minus from the _transferFrom account------------//
			_primaryAccounts.setAccountBalance(_primaryAccounts.getAccountBalance().subtract(new BigDecimal(_amount)));
			
			//---------Add to _transferTo account----------------//
			_savingsAccoutns.setAccountBalance(_savingsAccoutns.getAccountBalance().add(new BigDecimal(_amount)));
			
			//---------Save the accounts after the modifying by the transfer amount---------------//
			_primaryAccountDao.save(_primaryAccounts);
			_savingsAccountDao.save(_savingsAccoutns);
			
			//---------Save the transaction
			Date _date = new Date();
			
			PrimaryTransaction _primaryTransaction =
					new PrimaryTransaction(_date, "Transfer From Primary Account("+_primaryAccounts.getAccountNumber()+") To :" + _savingsAccoutns.getAccountNumber(), "Transfer", "Finished",
										   Double.parseDouble(_amount), _primaryAccounts.getAccountBalance(), _primaryAccounts);
			priamryTransactionDao.save(_primaryTransaction);
			
			SavingsTransaction _savingstransaction = 
					new SavingsTransaction(_date, "Transfer To Savings Account("+_savingsAccoutns.getAccountNumber()+")"+" From "+_primaryAccounts.getAccountNumber(), "Transfer", "Finished",
										   Double.parseDouble(_amount), _primaryAccounts.getAccountBalance(), _savingsAccoutns);
			savingsTransactionDao.save(_savingstransaction);
			
		} else if (_transferFrom.equalsIgnoreCase("Savings") && _transferTo.equalsIgnoreCase("Primary")) {
			
			//---------Minus from the _transferFrom account------------//
			_savingsAccoutns.setAccountBalance(_savingsAccoutns.getAccountBalance().subtract(new BigDecimal(_amount)));
			//---------Add to _transferTo account----------------//
			_primaryAccounts.setAccountBalance(_primaryAccounts.getAccountBalance().add(new BigDecimal(_amount)));
			
			//---------Save the accounts after the modifying by the transfer amount---------------//
			_primaryAccountDao.save(_primaryAccounts);
			_savingsAccountDao.save(_savingsAccoutns);
			
			//---------Save the transaction
			Date _date = new Date();
			
			PrimaryTransaction _primarytransaction =
					new PrimaryTransaction(_date, "Transfer To Primary Account("+_primaryAccounts.getAccountNumber()+") From :" + _savingsAccoutns.getAccountNumber(),
					    "Transfer", "Finished",
					    Double.parseDouble(_amount), _primaryAccounts.getAccountBalance(), _primaryAccounts);
			
			priamryTransactionDao.save(_primarytransaction);
			
			SavingsTransaction _savingstransaction = 
					new SavingsTransaction(_date, "Transfer from Savings Account("+_savingsAccoutns.getAccountNumber()+")"+" To "+_primaryAccounts.getAccountNumber(), "Transfer", "Finished",
										   Double.parseDouble(_amount), _primaryAccounts.getAccountBalance(), _savingsAccoutns);
			
					savingsTransactionDao.save(_savingstransaction);

		}else {
			throw new Exception("Invalid transfer");
		}
		
	}

	@Override
	public List<Recipient> findRecipientList(Principal _principal) {
		String _username = _principal.getName();
		List<Recipient> _recipienList = 
				_recipientDao.findAll().stream()
				.filter(recipient -> _username.equals(recipient.getUser().getUsername()))
				.collect(Collectors.toList());
		return _recipienList;
	}

	@Override
	public void saveRecipient(Recipient _recipient) {
		_recipientDao.save(_recipient);
		
	}

	@Override
	public Recipient findRecipientByName(String _recipientName) {
		return _recipientDao.findByName(_recipientName);
	}

	@Override
	public void deleteRecipientByName(String _recipientName) {
		_recipientDao.deleteByName(_recipientName);
		
	}

	@Override
	public void toSomeoneElseTransfer(Recipient _recipient, String _accountType, String _amount,
			PrimaryAccount _primaryAccount, SavingsAccount _savingsAccount) {
		if (_accountType.equalsIgnoreCase("Primary")) {
			_primaryAccount.setAccountBalance(_primaryAccount.getAccountBalance().subtract(new BigDecimal(_amount)));
			_primaryAccountDao.save(_primaryAccount);
			
			Date _date = new Date();
			
			PrimaryTransaction _primarytransaction = 
					new PrimaryTransaction(_date, "Transfer to recipient " + _recipient.getName(), "Transfer", "Successful",
											Double.parseDouble(_amount), _primaryAccount.getAccountBalance(), _primaryAccount);
			
			priamryTransactionDao.save(_primarytransaction);
		} else if(_accountType.equalsIgnoreCase("Savings")) {
			_savingsAccount.setAccountBalance(_savingsAccount.getAccountBalance().subtract(new BigDecimal(_amount)));
			_savingsAccountDao.save(_savingsAccount);
			
			Date _date = new Date();
			SavingsTransaction _savingsTransaction = 
					new SavingsTransaction(_date, "Transfer to recipient " + _recipient.getName(), "Transfer", "Successful",
							Double.parseDouble(_amount), _savingsAccount.getAccountBalance(), _savingsAccount);
			savingsTransactionDao.save(_savingsTransaction);
		}
		
	}


}




















































