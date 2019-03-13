package com.mithun.resource;

/**
 * DTO to hold Items
 * @author Mithun
 *
 */
public class Item {

	private int indexNumber;
	private double weight;
	private double cost;

	/**
	 * @return the indexNumber
	 */
	public int getIndexNumber() {
		return indexNumber;
	}
	/**
	 * @param indexNumber the indexNumber to set
	 */
	public void setIndexNumber(int indexNumber) {
		this.indexNumber = indexNumber;
	}
	/**
	 * @return the weight
	 */
	public double getWeight() {
		return weight;
	}
	/**
	 * @param weight the weight to set
	 */
	public void setWeight(double weight) {
		this.weight = weight;
	}
	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}
	/**
	 * @param cost the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
}
