import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import fr.ingeniance.kata.StartKataApplication;
import fr.ingeniance.kata.bo.Account;
import fr.ingeniance.kata.bo.History;
import fr.ingeniance.kata.dto.AccountDto;
import fr.ingeniance.kata.utils.OperationEnum;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartKataApplication.class, 
webEnvironment = WebEnvironment.DEFINED_PORT)
public class AccountControllerTest {
	
	private TestRestTemplate restTemplate = new TestRestTemplate();

    private final String uri = "http://localhost:" + 8088 + "/accounts";
    private HttpHeaders headers = new HttpHeaders();
    
    /*
     * I want to make a deposit in not found account
     * expected : BAD_REQUEST
     */
    @Test
    public void accountNotFoundTest() {    
    	AccountDto accountDto = new AccountDto();
    	accountDto.setAmount(new BigDecimal(100));
    	accountDto.setId(4L);
    	accountDto.setOperation(OperationEnum.DEPOSIT);
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto, headers);
        ResponseEntity<Account> result = this.restTemplate.postForEntity(uri, request, Account.class);
    	assertTrue(HttpStatus.BAD_REQUEST == result.getStatusCode());
    }
    
    /*
     * I want to make a deposit of -100 in my account number 1
     * expected : 
     * 		- HttpStatus	: BAD_REQUEST 
     */
    @Test
    public void makeUnauthorizedDepositTest(){    
    	AccountDto accountDto = new AccountDto();
    	accountDto.setAmount(new BigDecimal(-100));
    	accountDto.setId(1L);
    	accountDto.setOperation(OperationEnum.DEPOSIT);
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto, headers);
        ResponseEntity<Account> result = this.restTemplate.postForEntity(uri, request, Account.class);
    	assertTrue(HttpStatus.BAD_REQUEST == result.getStatusCode());
    }
    
    
    /*
     * I want to make a deposit of 500 in my account number 1
     * expected : 
     * 		- HttpStatus	: OK 
     * 		- new Balance	: 1500
     */
    @Test
    public void makeDepositTest() {    
    	AccountDto accountDto = new AccountDto();
    	accountDto.setAmount(new BigDecimal(500));
    	accountDto.setId(1L);
    	accountDto.setOperation(OperationEnum.DEPOSIT);
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto, headers);
        ResponseEntity<Account> result = this.restTemplate.postForEntity(uri, request, Account.class);
    	assertTrue(HttpStatus.OK == result.getStatusCode());
    	assertEquals(1500L, result.getBody().getBalance().longValue());
    }
    
    /*
     * I want to make a withdrawal of 300 from my account number 2
     * expected : 
     * 		- HttpStatus	: OK 
     * 		- new Balance	: 1700
     */
    @Test
    public void makeWithdrawalTest(){
    	AccountDto accountDto = new AccountDto();
    	accountDto.setAmount(new BigDecimal(300));
    	accountDto.setId(2L);
    	accountDto.setOperation(OperationEnum.WITHDRAWAL);
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto, headers);
        ResponseEntity<Account> result = this.restTemplate.postForEntity(uri, request, Account.class);
    	assertTrue(HttpStatus.OK == result.getStatusCode());
    	//assertEquals(1700L, result.getBody().getBalance().longValue());
    }
    
    /*
     * I want to see the history (operation, date, amount, balance) of my operations, account number 1
     * expected : 
     * 		- HttpStatus	: BAD_REQUEST 
     */
    @Test
    public void checkOperationsTest(){    
    	AccountDto accountDto = new AccountDto();
    	accountDto.setId(1L);
        HttpEntity<AccountDto> request = new HttpEntity<>(accountDto, headers);
        ResponseEntity<History[]> result = this.restTemplate.postForEntity(uri+"/history", request, History[].class);
    	assertTrue(HttpStatus.OK == result.getStatusCode());
    	assertTrue(1 == result.getBody().length);
    	int idLastOperation = result.getBody().length - 1;
    	
    	assertEquals(OperationEnum.DEPOSIT, result.getBody()[idLastOperation].getOperation());
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	assertEquals(sdf.format(new Date()), sdf.format(result.getBody()[idLastOperation].getDate()));

    	assertEquals(500L, result.getBody()[idLastOperation].getAmount().longValue());
    	assertEquals(1500L, result.getBody()[idLastOperation].getBalance().longValue());
    }

}
