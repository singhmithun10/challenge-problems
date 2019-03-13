package com.mithun.processor;

import java.util.ArrayList;
import java.util.Iterator;

import com.mithun.constant.SmartPackerConstant;
import com.mithun.resource.Item;
import com.mithun.resource.Package;

/**
 * This class acts as a helper for SmartPackerProcessor
 * This responsibility of this class includes filtering the items, generating all possible combinations, finding the best combination
 * This class also includes some basic utility methods which is used in fulfilling the responsibility of this class
 * @author Mithun
 *
 */
public class SmartPackerHelper {

	
	/**
	 * This method calculates the optimal combination for the given input package
	 * @param packge - input package
	 * @return String - optimal combination in String
	 */
	public String getOptimalCombination(Package packge) {
		
		// removing items whose weight is more than the allowed weight
		filterItems(packge);
		
		// generate all possible combinations for the input package
		ArrayList<ArrayList<Item>> possibleCombinations = generateCombinations(packge);
		
		// Calculate best combination
		StringBuilder outptuString = new StringBuilder();
		
		if(!possibleCombinations.isEmpty()) {
			
			ArrayList<Item> optimalCombination = calculateBestCombination(possibleCombinations);
			
			Iterator<Item> iterator = optimalCombination.iterator();
			
			while(iterator.hasNext()) {
				Item item = iterator.next();
				
				outptuString.append(item.getIndexNumber());
				
				if(iterator.hasNext()) {
					outptuString.append(SmartPackerConstant.COMMA);
				}
			}
		}else {
			outptuString.append(SmartPackerConstant.HYPHEN);
		}
		
		return outptuString.toString();
	}
	
	
	
	/**
	 * This method generates all the possible combinations of the input package
	 * It uses the iterator approach. This is done using 2 loops. The outer loop iterates over the list of Items
	 * The inner loop iterates over the list of possible combinations. 
	 *   
	 * @param packge - input package for which all the combinations needs to be calculated
	 * @return ArrayList<ArrayList<Item>> - A list with all the possible combinations
	 */
	private ArrayList<ArrayList<Item>> generateCombinations(Package packge){
		
		// Creating an array list to hold all the combinations
		ArrayList<ArrayList<Item>> possibleCombinations = new ArrayList<ArrayList<Item>>();
		
		// Iterating over the list of items in the package
		for(int itemIterator = 0; itemIterator < packge.getItemList().size(); itemIterator++) {
			
			// current item in iteration
			Item currentItem = packge.getItemList().get(itemIterator);
			
			// size of possible combinations initially set to zero
			int possibleCombinationsSize = possibleCombinations.size();
			
			/*Iterating through every element of possible combinations
			 * Initially as the size is zero, this loop wont run for the first iteration
			 */
			for(int possibleCombinationsIterator = 0; possibleCombinationsIterator < possibleCombinationsSize ; possibleCombinationsIterator++) {
				
				ArrayList<Item> currentPossibleCombinations = possibleCombinations.get(possibleCombinationsIterator);
				
				ArrayList<Item> newGeneratedCombination = new ArrayList<Item>(currentPossibleCombinations);
				newGeneratedCombination.add(currentItem);
				
				/* Check the validity of the combination based on the maximum allowed weight
				 * The combination gets added to the list only if the combined weight is less or equal to the maximum allowed weight of the package
				 * Else, the combination is discarded
				 */
				if(getCombinationWeight(newGeneratedCombination) <= packge.getWeightLimit()) {
					possibleCombinations.add(newGeneratedCombination);
				}
				
			}
			
			/* Creating a new combination with the current item in the iteration in the outer loop and adding it to combination list
			 * No need to check the validity of the combination with regards to weight as this will always be a combination with single element i.e. current item
			 * And as the item list has already been filtered the weight of the current item will never exceed the maximum allowed weight
			 */
			ArrayList<Item> combinationForCurrentItem = new ArrayList<Item>();
			combinationForCurrentItem.add(currentItem);
			possibleCombinations.add(combinationForCurrentItem);
		}
		
		return possibleCombinations;
	}
	
	
	/**
	 * This method calculates the best combination out of all the possible combinations
	 * It takes into consideration weight and cost as  the parameters on which the best combination is decided
	 * The rule set includes:
	 * 1. The total weight of the combination is less than or equal to the maximum bearable weight of the package
	 * 2. The total cost of the combination is as high as possible
	 * 3. In case more than one combination have the same total cose, choose the combination with the lighter total weight
	 * 
	 * @param possibleCombinations - the list of all possible combinations
	 * @return ArrayList<Item> - the best possible combination as per the constraints
	 */
	private ArrayList<Item> calculateBestCombination(ArrayList<ArrayList<Item>> possibleCombinations){
		
		// optimal cost and weight set to zero
		double bestCost 	 = 0;
		double bestWeight = 0;
		
		// ArrayList to hold the best combination
		ArrayList<Item> bestCombination = new ArrayList<>();
		
		// Iterating over the list of possible combinations
		for(ArrayList<Item> combination : possibleCombinations) {
			
			// fetching the cost of combination
			double combinationCost = calculateCost(combination);
			
			// The combination cost becomes the optimal cost if the former is greater than latter
			if(combinationCost > bestCost) {
				bestCost = combinationCost;
				bestWeight = getCombinationWeight(combination);
				bestCombination = combination;
			}
			
			// If there are 2 combinations with the same cost, the one with the lighter weight is chosen
			else if(combinationCost == bestCost) {
				bestCombination = (bestWeight > getCombinationWeight(combination)) ? combination: bestCombination;
			}
		}
		
		return bestCombination;
		
	}
	
	
	/**
	 * This method converts the item details from String to Item DTO
	 * @param itemString - input String with the item details
	 * @return Item - Item DTO with all the details mapped
	 */
	public Item mapIntoItem(String itemString) {
		
		// Sanitizing the input item details by removing parentheses and euro symbol
		String sanitizedItemString = itemString.replaceAll(SmartPackerConstant.LEFT_PARENTHESES, SmartPackerConstant.EMPTY_STRING).
												replaceAll(SmartPackerConstant.RIGHT_PARENTHESES, SmartPackerConstant.EMPTY_STRING).
												replaceAll(SmartPackerConstant.EURO_SYMBOL, SmartPackerConstant.EMPTY_STRING);
		
		// Separating item details
		String[] itemDetails = sanitizedItemString.split(SmartPackerConstant.ITEM_DETAILS_SEPARATOR);
		
		// Creating the Item DTO and setting all the values after trimming them
		Item item = null;
		if(itemDetails.length == 3) {
			item = new Item();
			item.setIndexNumber(Integer.parseInt(itemDetails[0].trim()));
			item.setWeight(Double.parseDouble((itemDetails[1].trim())));
			item.setCost(Double.parseDouble(itemDetails[2].trim()));
		}
		
		return item;
	}
	
	
	/**
	 * This method filters out the items whose weight is more than the maximum bearable weight of the package
	 * @param packge - input Package for which the items needs to be filtered
	 */
	private void filterItems(Package packge) {
		
		for(Iterator<Item> iterator = packge.getItemList().listIterator(); iterator.hasNext();) {
			Item item = iterator.next();
			if(item.getWeight() > packge.getWeightLimit()) {
				iterator.remove();
			}
		}
		
	}
	
	/**
	 * This method calculates the total weight of the combination
	 * @param combination - input combination
	 * @return double - total calculated weight of combination
	 */
	private double getCombinationWeight(ArrayList<Item> combination) {
		
		double weightOfCombination = 0;
		
		for(Item item: combination) {
			weightOfCombination = item.getWeight() + weightOfCombination;
		}
		
		return weightOfCombination;
	}
	
	
	/**
	 * This method calculates the total cost of the items present in the combination
	 * @param combination - input combination
	 * @return double - total cost of the all the items in the combination
	 */
	private double calculateCost(ArrayList<Item> combination) {
		
		double totalCost = 0;
		
		for(Item item: combination) {
			totalCost = item.getCost() + totalCost;
		}
		
		return totalCost;
	}
}
