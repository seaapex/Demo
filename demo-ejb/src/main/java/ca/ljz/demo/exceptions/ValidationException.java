package ca.ljz.demo.exceptions;

import java.util.UUID;

import ca.ljz.demo.utils.UUIDUtils;

public class ValidationException extends javax.validation.ValidationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8885592481266745563L;
	private final byte[] id;
	private final String[] violationMsgs;

	public ValidationException(String[] violationMsgs) {
		id = UUIDUtils.uuidToByteArray(UUID.randomUUID());
		this.violationMsgs = violationMsgs;
	}

	public String[] getMessages() {
		return violationMsgs;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString() + "-" + UUIDUtils.byteArrayToUUIDString(id);
	}
}
