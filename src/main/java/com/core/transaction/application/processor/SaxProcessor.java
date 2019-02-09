package com.core.transaction.application.processor;

import java.io.File;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.transaction.application.dto.TransactionBO;
import com.core.transaction.application.service.TransactionService;

@Component
public class SaxProcessor  implements FileProcessor{

	@Autowired
	TransactionService transactionService;
	
	private static final Logger logger = LogManager.getLogger(SaxProcessor.class);
	
	/***
	 * method to process the input file
	 */
	public List<TransactionBO> process(String fileName) {
		try {
	         File inputFile = new File(fileName);
	         
	         // Initializing Sax parser factory
	         SAXParserFactory factory = SAXParserFactory.newInstance();
	         
	         // Creating a new Sax parser using factory
	         SAXParser saxParser = factory.newSAXParser();
	         
	         TransactionHandler handler = new TransactionHandler(transactionService);
	         
	         // Below is the invocation of XML parsing
	         saxParser.parse(inputFile, handler); 
	         
	         // Fetch the final list of failed transactions and return them
	         return handler.getFailedTransactions();
	      } catch (Exception e) {
	    	  e.printStackTrace();
	    	  logger.error(e.getMessage());
	      }

		return Collections.emptyList();
	}
}
