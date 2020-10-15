package fr.ingeniance.kata.bo;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import fr.ingeniance.kata.utils.OperationEnum;
import lombok.Data;

@Data
@Entity
public class History {
	
	@Id
	@GeneratedValue
	private Long id;
	
	private OperationEnum operation;
	
	private Date date;
	
	private BigDecimal amount;
	
	private BigDecimal balance;

}
