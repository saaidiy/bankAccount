package fr.ingeniance.kata.bo;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Data
@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	private BigDecimal balance;

	public Account() {
		super();
	}
	
	public Account(BigDecimal balance) {
		super();
		this.balance = balance;
	}
}
