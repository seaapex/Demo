package ca.ljz.demo.contexts;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import ca.ljz.demo.annotations.AuthContext;
import ca.ljz.demo.annotations.Property;
import ca.ljz.demo.utils.EncryptionUtils;

@AuthContext
@RequestScoped
public class CallerManager {
	private static Logger logger = Logger.getLogger(CallerManager.class.getName());

	@Inject
	@Property("charset")
	private String charset;

	@Inject
	@Property("encryption")
	private String encryption;

	@Inject
	private HttpServletRequest request;

	public String getCallerPrincipal() {
		try {
			return getAuthInfo()[0].trim();
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	public String getCallerPassword() {
		try {
			return EncryptionUtils.encrypt(getAuthInfo()[1].trim(), encryption);
		} catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private String[] getAuthInfo() throws UnsupportedEncodingException {
		logger.log(Level.INFO, "getAuthInfo");

		String[] header = request.getHeader("authorization").split(" ");
		String type = header[0];
		String authorization = header[1];

		switch (type.trim().toUpperCase()) {
		case "BASIC":
			return EncryptionUtils.base64decoding(authorization, charset).split(":");

		default:
			return null;
		}
	}
}
