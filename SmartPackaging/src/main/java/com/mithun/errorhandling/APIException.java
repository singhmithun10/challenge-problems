package com.mithun.errorhandling;

/**
 * This is an exception class used in the application
 * @author Mithun
 *
 */
public class APIException extends Exception {

	private static final long serialVersionUID = -5937868569493922918L;

	public APIException(String message) {
		super(message);
	}
}
