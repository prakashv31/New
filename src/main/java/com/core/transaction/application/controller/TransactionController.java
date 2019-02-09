package com.core.transaction.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.core.transaction.application.request.TransactionReportRequest;
import com.core.transaction.application.response.TransactionReportResponse;
import com.core.transaction.application.service.TransactionService;

@RestController
public class TransactionController {
	
	
	@Autowired
	TransactionService transactionService;
	
	
	@PostMapping(path="/transaction/validate")
	TransactionReportResponse registerEmployee(@RequestBody TransactionReportRequest transactionReportRequest) throws Exception {
		
		TransactionReportResponse transactionReportResponse =	transactionService.validateTransactions(transactionReportRequest);
		return transactionReportResponse;
		
	}
	
	

}
