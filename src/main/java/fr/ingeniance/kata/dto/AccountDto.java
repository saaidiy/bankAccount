package fr.ingeniance.kata.dto;

import java.math.BigDecimal;

import fr.ingeniance.kata.utils.OperationEnum;
import lombok.Data;

@Data
public class AccountDto {
	
	private Long id;

	private BigDecimal amount;
	
	private BigDecimal balance;
	
	private OperationEnum operation;

}
