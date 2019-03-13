package com.mithun.processor;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.mithun.errorhandling.APIException;
import com.mithun.processor.SmartPackerProcessor;


/**
 * Test class for SmartPackerProcessor
 * @author Mithun
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SmartPackerProcessorTest {

	@InjectMocks
	SmartPackerProcessor smartPackerProcessor;
	
	/**
	 * test case for positive scenario for processPackaging method
	 */
	@Test
	public void processPackagingPositive() {
		
		File file = new File("src/test/resources/input.txt");
		String inputFilePath = file.getAbsolutePath();
		
		StringBuilder expectedString = new StringBuilder();
		expectedString.append("4").append("\n").append("-").append("\n");
		
		try {
			String actualResult = smartPackerProcessor.processPackaging(inputFilePath);
			Assert.assertTrue("Comparing result", actualResult.equals(expectedString.toString()));
		} catch (Exception exception) {
			Assert.fail("Exception occurred");
		}
	}
	
	/**
	 * test case for file exception scenario for processPackaging method
	 */
	@Test
	public void processPackingFileException() {
		File file = new File("input.txt");
		String inputFilePath = file.getAbsolutePath();
			
		try {
			smartPackerProcessor.processPackaging(inputFilePath);
			Assert.fail("Exception not occurred");
		} catch (Exception exception) {
			Assert.assertTrue("Comparing Exception Type", exception instanceof APIException);
			Assert.assertEquals("Comparing Exception Message", ((APIException)exception).getMessage(), "Exception in reading input file");
		}
	}
}
