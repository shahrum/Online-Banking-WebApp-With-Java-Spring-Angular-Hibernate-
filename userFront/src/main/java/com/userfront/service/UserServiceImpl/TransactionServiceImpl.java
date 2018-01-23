package com.userfront.service.UserServiceImpl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.userfront.dao.PrimaryAccountDao;
import com.userfront.dao.PrimaryTransactionDao;
import com.userfront.dao.SavingsAccountDao;
import com.userfront.dao.SavingsTransactionDao;
import com.userfront.domain.PrimaryAccount;
import com.userfront.domain.PrimaryTransaction;
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


}




















































