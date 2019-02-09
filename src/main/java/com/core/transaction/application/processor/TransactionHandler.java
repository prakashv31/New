package com.core.transaction.application.processor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.core.transaction.application.dto.TransactionBO;
import com.core.transaction.application.dto.TransactionDTO;
import com.core.transaction.application.service.TransactionService;

@Component
public class TransactionHandler extends DefaultHandler {

	TransactionService transactionService;
	
	private String chars = null;
	private TransactionDTO transaction = null;
	private Set<String> allTransactionReferences = new HashSet<>();
	private List<TransactionBO> failedTransactions = new LinkedList<>();

	public TransactionHandler(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

		if (qName.equalsIgnoreCase("record")) {
			String reference = attributes.getValue("reference");
			transaction = new TransactionDTO(reference);
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		if (qName.equalsIgnoreCase("record")) { // This indicates a record has ended. So the transaction record is
												// validated and processed here

			transactionService.processTransaction(transaction, allTransactionReferences, failedTransactions );

		} else if (qName.equalsIgnoreCase("accountNumber")) {
			transaction.setAccountNumber(chars);
		} else if (qName.equalsIgnoreCase("description")) {
			transaction.setDescription(chars);
		} else if (qName.equalsIgnoreCase("startBalance")) {
			transaction.setStartBalance(new BigDecimal(chars));
		} else if (qName.equalsIgnoreCase("mutation")) {
			transaction.setMutation(new BigDecimal(chars));
		} else if (qName.equalsIgnoreCase("endBalance")) {
			transaction.setEndBalance(new BigDecimal(chars));
		}
	}

	@Override
	public void characters(char ch[], int start, int length) throws SAXException {
		chars = new String(ch, start, length);
	}

	public List<TransactionBO> getFailedTransactions() {
		return failedTransactions;
	}

}