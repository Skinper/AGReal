package agreal;

import java.io.FileWriter;
import java.io.IOException;

public class GenerateCSV {

	   public static void generateCsvFile(String sFileName, String melhorResultado)
	   {
		   
	    try
	    {
	     
	    	
	        FileWriter writer = new FileWriter(sFileName, true);
	     
//	        writer.append("Teste");
//	        writer.append(',');
//	        writer.append("Resultado");
//	        writer.append('\n');

	        writer.append("Real2");
	        writer.append(';');	
	        writer.append(melhorResultado);
	            writer.append('\n');

 
	        //generate whatever data you want

	        writer.flush();
	        writer.close();
	    }
	    catch(IOException e)
	    {
	         e.printStackTrace();
	    } 
	    }	
	
	
}
