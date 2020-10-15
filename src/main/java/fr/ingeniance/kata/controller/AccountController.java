package fr.ingeniance.kata.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ingeniance.kata.bo.Account;
import fr.ingeniance.kata.bo.History;
import fr.ingeniance.kata.dto.AccountDto;
import fr.ingeniance.kata.repository.AccountRepository;

@RestController
public class AccountController {
	
	@Autowired
    private AccountRepository repository;

    @PostMapping(path = "/accounts", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> makeOperation(@RequestBody AccountDto accountDto) {
    	Optional<Account> optionalAccount = repository.findById(accountDto.getId());
    	Account result = null;
    	HttpStatus status = null;
    	
    	if (!optionalAccount.isPresent() || accountDto.getAmount().compareTo(BigDecimal.ZERO) < 0) {
    		status = HttpStatus.BAD_REQUEST;
		}else {
			switch (accountDto.getOperation())
			{
			  case WITHDRAWAL	:	result = makeWithdrawal(accountDto, optionalAccount.get());
									status = HttpStatus.OK;
									break;
			  case DEPOSIT		:   result = makeDeposit(accountDto, optionalAccount.get());
			  						status = HttpStatus.OK;
			  						break;
			  default			:   status = HttpStatus.BAD_REQUEST;
			}
    	}
    	return new ResponseEntity<>(result, status);
    }
    
    @PostMapping(path = "/accounts/history", consumes = "application/json", produces = "application/json")
    public ResponseEntity<List<History>> checkOperations(@RequestBody AccountDto accountDto) {
    	Optional<Account> optionalAccount = repository.findById(accountDto.getId());
    	if(optionalAccount.isPresent()) {
    		return new ResponseEntity<>(optionalAccount.get().getHistory(), HttpStatus.OK);
    	}else {
    		return new ResponseEntity<>(new ArrayList<History>(), HttpStatus.NO_CONTENT);
    	}
    }
    
    private Account makeDeposit(AccountDto accountDto, Account account) {
		History history = new History();
		history.setDate(new Date());
		history.setOperation(accountDto.getOperation());
		history.setAmount(accountDto.getAmount());
		BigDecimal newBalance = account.getBalance().add(accountDto.getAmount());
		history.setBalance(newBalance);
		account.getHistory().add(history);
		account.setBalance(newBalance);
		return repository.save(account);
    }
    
    private Account makeWithdrawal(AccountDto accountDto, Account account){
    	return account;
    }
    
}
