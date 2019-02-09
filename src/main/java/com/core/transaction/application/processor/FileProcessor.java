package com.core.transaction.application.processor;

import java.util.List;

import com.core.transaction.application.dto.TransactionBO;

public interface FileProcessor {
	
	public List<TransactionBO> process(String fileType) throws Exception;

}
