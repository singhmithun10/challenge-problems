package com.mithun.processor;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.mithun.constant.SmartPackerConstant;
import com.mithun.errorhandling.APIException;
import com.mithun.resource.Item;
import com.mithun.resource.Package;

/**
 * This class handles the complete steps involved in achieving the desired result of the application
 * @author Mithun
 *
 */
public class SmartPackerProcessor {

	// List to hold the packages after reading from the file 
	List<Package> packingSet = new ArrayList<>();
	
	SmartPackerHelper smartPackerHelper = new SmartPackerHelper();
	
	/**
	 * This method is responsible for handling all the steps required to find the best combination for each package
	 * @param inputFilePath - input path to the text file.
	 * @return String - result in String
	 * @throws APIException - application exception
	 */
	public String processPackaging(String inputFilePath) throws APIException {
		
		// read the file and map it into object
		generatePackagingSet(inputFilePath);
		
		StringBuilder outputSolution = new StringBuilder();
		
		// Iterating over the PackingSet to find the optimal combination for each package
		for(Package packge: packingSet) {
			outputSolution.append(smartPackerHelper.getOptimalCombination(packge));
			outputSolution.append(SmartPackerConstant.NEW_LINE);
		}
		
		return outputSolution.toString();
	
	}
	
	
	/**
	 * This method reads the file and calls the method to convert input record in string to object for each record in file
	 * @param inputFilePath - input file path
	 * @throws APIException  - application exception
	 */
	private void generatePackagingSet(String inputFilePath) throws APIException {
		
		try(Stream<String> stream = Files.lines(Paths.get(inputFilePath))) {
			stream.forEach(this :: mapInputToPackage);
		}catch (IOException exception) {
			throw new APIException("Exception in reading input file");
		}
	}
	
	
	/**
	 * This method accepts the record in String format and converts it into Item object and adds it to the packingSet list
	 * @param recordLine - input record in String format
	 */
	private void mapInputToPackage(String recordLine) {
	
		if(StringUtils.isNotBlank(recordLine)) {
			Package packge = new Package();
			ArrayList<Item> itemList = new ArrayList<>();
			
			// splitting the record on the basis of :
			String[] packageDetailsArray = recordLine.split(SmartPackerConstant.PACKAGE_DETAIL_SEPARATOR);
			
			/*  Adding a basic check for the length of the array after split 
			 * Some more checks have to be added to discard invalid data but skipping them for now*/
			if(packageDetailsArray.length == 2) {
				
				double allowedPackageWeight = Double.parseDouble(packageDetailsArray[0].trim());
				
				// Only packages with allowed weight less than or equal to maximum allowed weight will be considered for processing
				if(allowedPackageWeight <= SmartPackerConstant.MAXIMUM_ALLOWED_PACKAGE_WEIGHT) {
					
					// setting the allowed package weight
					packge.setWeightLimit(allowedPackageWeight);
					
					// Checking for pattern with round parentheses
					Matcher parenthesesMatcher = Pattern.compile(SmartPackerConstant.PARENTHESES_REGEX).matcher(packageDetailsArray[1].trim());
					
					while(parenthesesMatcher.find()) {
						Item item = smartPackerHelper.mapIntoItem(parenthesesMatcher.group());
					
						// Checking if valid item as per weight and cost constraints
						if(null != item  && isValidItem(item)) {
							itemList.add(item);
						}
					}
					
					// setting the parsed & mapped list of items
					packge.setItemList(itemList);
					
					// adding the parsed and mapped package to list
					packingSet.add(packge);
				}
			}else {
				// Adding syso for ease. In production this will be replaced by Logger.
				System.out.println("Invalid record ----->"+ recordLine);
			}
		}
	}
	
	/**
	 * This method checks the validity of the Item based on cost and weight constraints
	 * @param item - input Item to be validated
	 * @return boolean - decision
	 */
	private boolean isValidItem(Item item) {
		
		if((item.getCost() > SmartPackerConstant.MAXIMUM_ALLOWED_ITEM_COST) ||  (item.getWeight() > SmartPackerConstant.MAXIMUM_ALLOWED_ITEM_WEIGHT)) {
			return false;
		}
		
		return true;
	}
	
}
