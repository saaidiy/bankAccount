package fr.ingeniance.kata.bo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

@Data
@Entity
public class Account {

	@Id
	@GeneratedValue
	private Long id;

	private BigDecimal balance;
	
	@OneToMany(cascade = {CascadeType.ALL})
    private List<History> history;

	public Account() {
		super();
	}
	
	public Account(BigDecimal balance) {
		super();
		this.balance = balance;
	}
}
