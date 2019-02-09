package com.core.transaction.application.service;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.core.transaction.application.dto.TransactionBO;
import com.core.transaction.application.dto.TransactionDTO;
import com.core.transaction.application.processor.FileProcessorFactory;
import com.core.transaction.application.request.TransactionReportRequest;
import com.core.transaction.application.response.TransactionReportResponse;

@Service
public class TransactionServiceImpl implements TransactionService {

	@Autowired
	FileProcessorFactory fileProcessorFactory;

	@Override
	public TransactionReportResponse validateTransactions(TransactionReportRequest transactionReportRequest)
			throws Exception {

		List<TransactionBO> list = fileProcessorFactory.getFileprocessor(transactionReportRequest.getFileType())
				.process(transactionReportRequest.getFilePath());

		TransactionReportResponse reponse = new TransactionReportResponse();
		reponse.setInvalidTransactions(list);

		return reponse;
	}

	/***
	 * method to process a single transaction at a time It will validate that
	 * transaction against existing references for duplicate and validate the end
	 * balance
	 * 
	 */
	@Override
	public void processTransaction(TransactionDTO transaction, Set<String> allTransactionReferences,
			List<TransactionBO> failedTransactions) {

		// Validating duplicate transaction
		if (allTransactionReferences.contains(transaction.getTransactionReference())) {

			failedTransactions
					.add(new TransactionBO(transaction.getTransactionReference(), transaction.getDescription()));
		} else {
			allTransactionReferences.add(transaction.getTransactionReference());

			// Validating below the balance of transaction record
			if ((transaction.getStartBalance().add(transaction.getMutation())
					.compareTo(transaction.getEndBalance()) != 0)) {

				failedTransactions
						.add(new TransactionBO(transaction.getTransactionReference(), transaction.getDescription()));
			}
		}

	}
}
