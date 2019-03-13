package com.mithun.processor;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.mithun.processor.SmartPackerHelper;
import com.mithun.resource.Item;
import com.mithun.resource.Package;

/**
 * Test class for SmartPackerHelper
 * @author Mithun
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SmartPackerHelperTest {

	@InjectMocks
	SmartPackerHelper smartPackerHelper;
	
	/**
	 * test case for positive flow of getOptimalCombination method
	 */
	@Test
	public void getOptimalCombinationPositive() {
	
		double weightLimit = 81;
		String expectedResult = "4";
		
		
		Item item1 = new Item();
		item1.setIndexNumber(1);
		item1.setWeight(53.38);
		item1.setCost(45);
				
		Item item2 = new Item();
		item2.setIndexNumber(2);
		item2.setWeight(88.62);
		item2.setCost(98);
	
		Item item3 = new Item();
		item3.setIndexNumber(3);
		item3.setWeight(78.48);
		item3.setCost(3);
		
		Item item4 = new Item();
		item4.setIndexNumber(4);
		item4.setWeight(72.30);
		item4.setCost(76);
		
		Item item5 = new Item();
		item5.setIndexNumber(5);
		item5.setWeight(30.18);
		item5.setCost(9);
		
		Item item6 = new Item();
		item6.setIndexNumber(6);
		item6.setWeight(46.34);
		item6.setCost(48);
		
		List<Item> itemList = new ArrayList<>();
		itemList.add(item1);
		itemList.add(item2);
		itemList.add(item3);
		itemList.add(item4);
		itemList.add(item5);
		itemList.add(item6);
		
		
		Package packge = new Package();
		packge.setWeightLimit(weightLimit);
		packge.setItemList(itemList);
		
		try {
		String actualResult = smartPackerHelper.getOptimalCombination(packge);
		Assert.assertEquals("Checking result", expectedResult, actualResult);
		}catch(Exception exception) {
			Assert.fail("Exception occurred");
		}
	}
	
	/**
	 * test case for positive flow of mapIntoItem method
	 */
	@Test
	public void mapIntoItemPositive() {
		
		String itemString = "(1,53.38,€45)";
		double delta = 0;
		
		Item expectedItem = new Item();
		expectedItem.setIndexNumber(1);
		expectedItem.setWeight(53.38);
		expectedItem.setCost(45);
		
		try {
			Item actualItem = smartPackerHelper.mapIntoItem(itemString);
			Assert.assertEquals("Comparing index number", expectedItem.getIndexNumber(), actualItem.getIndexNumber());
			Assert.assertEquals("Comparing Weight", expectedItem.getWeight(), actualItem.getWeight(), delta);
			Assert.assertEquals("Comparing Cost", expectedItem.getCost(), actualItem.getCost(), delta);
		}catch(Exception exception) {
			Assert.fail("Exception occurred");
		}
	}
	
	
}
