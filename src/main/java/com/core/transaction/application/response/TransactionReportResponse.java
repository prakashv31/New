package com.core.transaction.application.response;

import java.util.List;

import com.core.transaction.application.dto.TransactionBO;

public class TransactionReportResponse {
	
	private List<TransactionBO> invalidTransactions;

	public List<TransactionBO> getInvalidTransactions() {
		return invalidTransactions;
	}

	public void setInvalidTransactions(List<TransactionBO> list) {
		this.invalidTransactions = list;
	}
	
	

}
