package ca.ljz.demo.exceptions;

public class InvalidUserException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8885592481266745563L;

	private final String[] violationMsgs;

	public InvalidUserException(String[] violationMsgs) {
		this.violationMsgs = violationMsgs;
	}

	public String[] getMessages() {
		return violationMsgs;
	}

}
