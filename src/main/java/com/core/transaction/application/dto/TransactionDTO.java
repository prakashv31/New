package com.core.transaction.application.dto;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class TransactionDTO {
	
	private String transactionReference;
	private String accountNumber;
	private String description;
	private BigDecimal startBalance;
	private BigDecimal mutation;
	private BigDecimal endBalance;
	private Boolean isValid;
	
	
	public TransactionDTO(String transactionReference, String acountNumber, String description, String startBalance,
			String mutation, String endBalance) {
		this.transactionReference = transactionReference;
		this.accountNumber = acountNumber;
		this.description = description;
		this.startBalance = convertToBigDecimal(startBalance);
		this.mutation= convertToBigDecimal(mutation);
		this.endBalance = convertToBigDecimal(endBalance);
		
	}
	

	
	
	public TransactionDTO(String reference) {
		this.transactionReference = reference;
	}




	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String acountNumber) {
		this.accountNumber = acountNumber;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public BigDecimal getStartBalance() {
		return startBalance;
	}
	public void setStartBalance(BigDecimal startBalance) {
		this.startBalance = startBalance;
	}
	public BigDecimal getMutation() {
		return mutation;
	}
	public void setMutation(BigDecimal mutation) {
		this.mutation = mutation;
	}
	public BigDecimal getEndBalance() {
		return endBalance;
	}
	public void setEndBalance(BigDecimal endBalance) {
		this.endBalance = endBalance;
	}
	public Boolean getIsValid() {
		return isValid;
	}
	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}
	
	@Override
    public String toString() 
    { 
        return "Transaction [transactionReference=" + transactionReference + ", description=" + description + ",  startBalance= " + startBalance + ", mutation = " + mutation  + ", endBalance=" + endBalance + "]"; 
    } 
	
	private static BigDecimal convertToBigDecimal(Object o) {
	    DecimalFormat df = new DecimalFormat();
	    df.setParseBigDecimal(true);
	    try {
	    	String parsebleString = o.toString();
	    	if(parsebleString.startsWith("+")) {
	    		parsebleString = parsebleString.substring(1);
	    	}
	        return (BigDecimal) df.parse(parsebleString);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return null;
	    }
	}
	

}
