package com.mithun.resource;

import java.util.List;

/**
 * DTO class to hold Package
 * @author Mithun
 *
 */
public class Package {

	private double weightLimit;
	private List<Item> itemList;
	
	/**
	 * @return the weightLimit
	 */
	public double getWeightLimit() {
		return weightLimit;
	}
	/**
	 * @param weightLimit the weightLimit to set
	 */
	public void setWeightLimit(double weightLimit) {
		this.weightLimit = weightLimit;
	}
	/**
	 * @return the itemList
	 */
	public List<Item> getItemList() {
		return itemList;
	}
	/**
	 * @param itemList the itemList to set
	 */
	public void setItemList(List<Item> itemList) {
		this.itemList = itemList;
	}
}
