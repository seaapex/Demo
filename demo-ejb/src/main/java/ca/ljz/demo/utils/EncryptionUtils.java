package ca.ljz.demo.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;

public class EncryptionUtils {

	public static String encrypt(String data, String algorithm) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance(algorithm);
		md.update(data.getBytes());
		return base64encoding(md.digest());
	}

	public static String base64encoding(byte[] bytes) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(bytes);
	}

	public static String base64decoding(String data, String charset) throws UnsupportedEncodingException {
		Decoder decoder = Base64.getDecoder();
		return new String(decoder.decode(data), charset);
	}
}
