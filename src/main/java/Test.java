import com.core.transaction.application.processor.CSVProcessor;
import com.core.transaction.application.processor.SaxProcessor;

public class Test {

	public static void main(String[] args) {
		
		CSVProcessor csvProcessor = new CSVProcessor();
		
		SaxProcessor xmlProcessor = new SaxProcessor();
		
		csvProcessor.process("C://Users//admin-pc//Desktop//Assignment//records.csv");
		//xmlProcessor.process("C://Users//admin-pc//Desktop//Assignment//records.xml");
		
		// TODO Auto-generated method stub

	}

}
