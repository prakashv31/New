package com.core.transaction.application.processor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class FileProcessorFactory {
	
	@Autowired
    private SaxProcessor xmlProcessor;
	
	@Autowired
    private CSVProcessor csvProcessor;
	

	public FileProcessor getFileprocessor(String fileType) {

		if (fileType == null) {
			return null;
		}
		if (fileType.equalsIgnoreCase(FileTypeEnum.CSV.getFileType())) {
			return csvProcessor;

		} else if (fileType.equalsIgnoreCase(FileTypeEnum.XML.getFileType())) {
			return xmlProcessor;
		}

		return null;
	}


}
