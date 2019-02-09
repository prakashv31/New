package com.core.transaction.application.service;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.core.transaction.application.dto.TransactionBO;
import com.core.transaction.application.dto.TransactionDTO;
import com.core.transaction.application.request.TransactionReportRequest;
import com.core.transaction.application.response.TransactionReportResponse;

@Service
public interface TransactionService {
	
	public TransactionReportResponse validateTransactions(TransactionReportRequest transactionReportRequest) throws Exception;

	public void processTransaction(TransactionDTO transaction, Set<String> allTransactionReferences,
			List<TransactionBO> failedTransactions);

	
}
