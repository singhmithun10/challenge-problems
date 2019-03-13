package com.mithun.packer;

import org.apache.commons.lang3.StringUtils;

import com.mithun.errorhandling.APIException;
import com.mithun.processor.SmartPackerProcessor;

/**
 * This is the Main class of the application from where the processing starts
 * @author Mithun
 *
 */
public class Packer {
	
	/**
	 * This is the main method of the application
	 * @param args - input arguments
	 * @throws APIException - Application exception
	 */
	public static void main(String[] args) throws APIException{
		
		if (args.length < 1) {
			throw new APIException("Insufficient arguments");
		}

		String filePath = args[0];
		
		SmartPackerProcessor smartPackerProcessor = new SmartPackerProcessor();
		System.out.println(smartPackerProcessor.processPackaging(filePath));
	}
	
	
	/**
	 * This method processes the input file to calculate best combination of items for each package and returns the results in String
	 * @param filePath - input file path to application
	 * @return String - result combination in String
	 * @throws APIException - application exception
	 */
	public static String pack(String filePath) throws APIException{
		
		if(StringUtils.isBlank(filePath)) {
			throw new APIException("Invalid input argument");
		}
		
		SmartPackerProcessor smartPackerProcessor = new SmartPackerProcessor();
		return smartPackerProcessor.processPackaging(filePath);
	}

}
