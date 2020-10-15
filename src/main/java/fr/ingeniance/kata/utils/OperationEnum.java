/**
 * 
 */
package fr.ingeniance.kata.utils;

/**
 * @author Admin
 *
 */
public enum OperationEnum {
	
	DEPOSIT("deposit"),
	
	WITHDRAWAL("withdrawal");
	
    private String value;
    
    OperationEnum(String value) {
        this.value = value;
    }
 
    public String getValue() {
        return value;
    }

}
