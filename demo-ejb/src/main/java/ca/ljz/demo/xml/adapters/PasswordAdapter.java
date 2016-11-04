package ca.ljz.demo.xml.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import ca.ljz.demo.utils.EncryptionUtils;

public class PasswordAdapter extends XmlAdapter<String, String> {

	@Override
	public String marshal(String password) throws Exception {
		return null;
	}

	@Override
	public String unmarshal(String password) throws Exception {
		String encryptedPassword = EncryptionUtils.encrypt(password,"SHA-256");
		return encryptedPassword;
	}

}