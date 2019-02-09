package com.core.transaction.application;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.core.transaction.application.request.TransactionReportRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;

public class TransactionValidationTest extends ServiceApplicationTests{

	@Autowired
	private WebApplicationContext webApplicationContext;
	
	 @Autowired private ObjectMapper objectMapper;

	private MockMvc mockMvc;
	
	 private JacksonTester < TransactionReportRequest > jsonTester;

	@Before
	public void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        JacksonTester.initFields(this, objectMapper);

	}

	@Test
	public void testTransactionCSV() throws Exception {
		File file = getCSVFile();
		TransactionReportRequest content = new TransactionReportRequest();
		content.setFilePath(file.getAbsolutePath());
		content.setFileType("CSV");
		final String requestContent = jsonTester.write(content).getJson();
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/validate").content(requestContent).contentType("application/json;charset=UTF-8")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
				//.andExpect(jsonPath("$.fileType").value("CSV")).andExpect(jsonPath("$.filePath").value(file.getAbsolutePath()+"\\"+file.getName()));
				

	}

	
	@Test
	public void testTransactionXML() throws Exception {
		File file = getXmlFile();
		TransactionReportRequest content = new TransactionReportRequest();
		content.setFilePath(file.getAbsolutePath());
		content.setFileType("XML");
		final String requestContent = jsonTester.write(content).getJson();
		mockMvc.perform(MockMvcRequestBuilders.post("/transaction/validate").content(requestContent).contentType("application/json;charset=UTF-8")).andExpect(status().isOk())
				.andExpect(content().contentType("application/json;charset=UTF-8"));
				//.andExpect(jsonPath("$.fileType").value("CSV")).andExpect(jsonPath("$.filePath").value(file.getAbsolutePath()+"\\"+file.getName()));
				

	}

	private File getCSVFile() {
		 File file = new File("recordss.csv"); 
		    try { 
		        // create FileWriter object with file as parameter 
		        FileWriter outputfile = new FileWriter(file); 
		  
		        // create CSVWriter object filewriter object as parameter 
		        CSVWriter writer = new CSVWriter(outputfile); 
		  
		        // adding header to csv 
		        String[] header = { "Reference", "AccountNumber", "Description","Start Balance", "Mutation", "End Balance" }; 
		        writer.writeNext(header); 
		  
		        // add data to csv 
		        String[] data1 = { "112806","NL69ABNA0433647324","Clothes for Richard de Vries","90.83"	,"-10.91","79.92" }; 
		        writer.writeNext(data1); 
		        String[] data2 = { "112806","NL93ABNA0585619023","Tickets from Richard Bakker","102.12","45.87","147.99" }; 
		        writer.writeNext(data2); 
		  
		        // closing writer connection 
		        writer.close(); 
		    } 
		    catch (IOException e) { 
		        // TODO Auto-generated catch block 
		        e.printStackTrace(); 
		    }
		    return file;
		} 
		
	private File getXmlFile() {
		File file = null; 
		try {
			 
            DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
 
            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();
 
            Document document = documentBuilder.newDocument();
 
            // root element
            Element root = document.createElement("record");
            document.appendChild(root);
 
            // employee element
            Element reference = document.createElement("Reference");
            reference.setNodeValue("130498");
            root.appendChild(reference);
            
 
            // set an attribute to staff element
            Attr attr = document.createAttribute("accountNumber");
            attr.setValue("NL69ABNA0433647324");
            reference.setAttributeNode(attr);
 
            //you can also use staff.setAttribute("id", "1") for this
 
            // firstname element
            Element description = document.createElement("description");
            description.appendChild(document.createTextNode("Tickets for Peter Theu"));
            reference.appendChild(description);
 
            // lastname element
            Element startBalance = document.createElement("startBalance");
            startBalance.appendChild(document.createTextNode("26.9"));
            reference.appendChild(startBalance);
 
            // email element
            Element mutation = document.createElement("mutation");
            mutation.appendChild(document.createTextNode("18.78"));
            reference.appendChild(mutation);
 
            // department elements
            org.w3c.dom.Element endBalance = document.createElement("endBalance");
            endBalance.appendChild(document.createTextNode("8.72"));
            reference.appendChild(endBalance);
 
            // create the xml file
            //transform the DOM Object to an XML File
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            file = new File("recordsxml.xml");
            StreamResult streamResult = new StreamResult(file);
 
            // If you use
            // StreamResult result = new StreamResult(System.out);
            // the output will be pushed to the standard output ...
            // You can use that for debugging 
 
            transformer.transform(domSource, streamResult);
 
            System.out.println("Done creating XML File");
 
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }
		return file;
	}
}
