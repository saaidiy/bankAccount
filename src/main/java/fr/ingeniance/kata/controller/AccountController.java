package fr.ingeniance.kata.controller;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.ingeniance.kata.bo.Account;
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
			status = HttpStatus.OK;
    	}
    	return new ResponseEntity<>(result, status);
    }
    
}
