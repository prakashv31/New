package com.core.transaction.application.dto;

public class TransactionBO {
	
	private String transactionReference;
	private String description;
	
	public TransactionBO(String transactionReference, String description) {
		this.transactionReference = transactionReference;
		this.description = description;
	}
	public String getTransactionReference() {
		return transactionReference;
	}
	public void setTransactionReference(String transactionReference) {
		this.transactionReference = transactionReference;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
