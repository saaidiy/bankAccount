package fr.ingeniance.kata;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fr.ingeniance.kata.bo.Account;
import fr.ingeniance.kata.repository.AccountRepository;

@SpringBootApplication
public class StartKataApplication  {
	
	public static void main(String[] args) {
        SpringApplication.run(StartKataApplication.class, args);
    }
	
	// init bean to insert 3 Accounts into h2 database.
    @Bean
    CommandLineRunner initDatabase(AccountRepository repository) {
        return args -> {
            repository.save(new Account(new BigDecimal(1000)));
            repository.save(new Account(new BigDecimal(2000)));
            repository.save(new Account(new BigDecimal(3000)));
        };
    }

}
