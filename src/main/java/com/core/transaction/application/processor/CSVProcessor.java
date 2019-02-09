package com.core.transaction.application.processor;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.core.transaction.application.dto.TransactionBO;
import com.core.transaction.application.dto.TransactionDTO;
import com.core.transaction.application.service.TransactionService;
import com.opencsv.CSVReader;

@Component
public class CSVProcessor implements FileProcessor {

	@Autowired
	TransactionService transactionService;
	
	private static final Logger logger = LogManager.getLogger(CSVProcessor.class);

	public List<TransactionBO> process(String fileName) {
		CSVReader reader = null;
		Set<String> allTransactionReferences = new HashSet<>();
		List<TransactionBO> failedTransactions = new LinkedList<>();
		try {

			// Get the CSVReader instance with specifying the delimiter to be used
			Path myPath = Paths.get(fileName);
			BufferedReader br = Files.newBufferedReader(myPath, StandardCharsets.UTF_8);
			reader = new CSVReader(br);
			reader.readNext();
			String[] nextLine;
			
			// Read one line at a time
			while ((nextLine = reader.readNext()) != null) {
				TransactionDTO transaction = new TransactionDTO(nextLine[0], nextLine[1], nextLine[2], nextLine[3],
						nextLine[4], nextLine[5]);

				transactionService.processTransaction(transaction, allTransactionReferences, failedTransactions );
				
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			try {
				if (null != reader)
					reader.close();
			} catch (IOException e) {
				logger.error(e.getMessage());
			}
		}
		return failedTransactions;
	}

}
