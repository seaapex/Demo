package ca.ljz.demo.contexts;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;

import ca.ljz.demo.annotations.Property;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PropertyManager {
	private static Logger logger = Logger.getLogger(PropertyManager.class.getName());
	private Properties properties = new Properties();

	@Property
	@Produces
	public String produceString(InjectionPoint ip) {
		logger.log(Level.INFO, "produceString");
		System.out.println(getKey(ip));
		return properties.getProperty(getKey(ip));
	}

	@Property
	@Produces
	public int produceInt(InjectionPoint ip) {
		logger.log(Level.INFO, "produceInt");
		return Integer.valueOf(properties.getProperty(getKey(ip)));
	}

	@Property
	@Produces
	public boolean produceBoolean(InjectionPoint ip) {
		logger.log(Level.INFO, "produceBoolean");
		return Boolean.valueOf(properties.getProperty(getKey(ip)));
	}

	private String getKey(InjectionPoint ip) {
		logger.log(Level.INFO, "getKey");
		return ip.getAnnotated().getAnnotation(Property.class).value();
	}

	@PostConstruct
	public void init() {

		try (InputStream is = PropertyManager.class.getResourceAsStream("/application.properties")) {
			properties.load(is);
		} catch (IOException e) {
			throw new RuntimeException("application.properties failed to load");
		}
	}
}