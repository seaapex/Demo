package ca.ljz.demo.ejbs.interceptors;

import java.lang.reflect.Method;
import java.util.List;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.xml.BaseXML;

@Interceptor
public class DataTransferInterceptor {

	protected transient final Logger logger = LoggerFactory.getLogger(getClass());

	private static final String ENTITY_PACKAGE = "ca.ljz.demo.entities";

	@AroundInvoke
	public Object transferData(InvocationContext context) throws Exception {
		logger.debug("transferData");
		Object[] params = context.getParameters();
		for (int i = 0; i < params.length; i++)
			params[i] = convertData(params[i]);

		context.setParameters(params);
		logger.debug("transferData: finished");
		return context.proceed();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Object convertData(Object obj) throws Exception {
		// if the parameter is null, then return immediately
		if (obj == null)
			return null;

		/*
		 * Check if the parameter is an XML holder class, or a list, or
		 * something else. If it's an XML holder class, copy all the properties
		 * into the associated entity class. Else if it's a list, loop through
		 * each element and recursively check it with this method. Else will
		 * just return whatever it is.
		 * 
		 */
		if (obj instanceof BaseXML) {
			logger.debug("this is a BaseXML");

			// get the XML holder class
			Class<?> xmlClass = obj.getClass();

			// use the name of the XML holder class to reflect the entity class
			String className = obj.getClass().getSimpleName();
			Class<?> entityClass = Class.forName(ENTITY_PACKAGE + "." + className.substring(0, className.length() - 3));

			/*
			 * Instantiate an instance of the entity class. This instance will
			 * eventually be returned and passed to the EJB methods as a
			 * parameter
			 */
			Object entity = entityClass.newInstance();

			/*
			 * Get all the methods and loop through them to get the values
			 * stored in the properties of this XML holder class. Then find the
			 * corresponding properties in the associated entity class, and
			 * store them.
			 */
			Method[] methods = xmlClass.getMethods();
			for (Method method : methods) {
				String xmlMethodName = method.getName();
				if (xmlMethodName.startsWith("get")) {
					logger.debug("transferData: get method detected - " + xmlMethodName);

					// getClass() is the only known method that may confuse
					// finding the actual properties, so just skip it
					if ("getClass".equals(xmlMethodName))
						continue;

					// get the value stored in the property
					Object xmlData = method.invoke(obj);
					logger.debug("transferData: get method returned - " + xmlData);

					/*
					 * If there is no value stored in the property, no action
					 * should be performed. The value of the property in the
					 * entity should remain as it is. If there is value stored,
					 * then get it from the XML holder and set it into the
					 * entity.
					 */
					if (xmlData != null) {
						logger.debug("transferData: xmlData is not null - " + xmlData);
						xmlData = convertData(xmlData);
						String entityMethodName = xmlMethodName.replaceFirst("get", "set");
						logger.debug("transferData: set method preparing - " + entityMethodName + " - Data type - "
								+ xmlData);
						Method entityMethod = null;

						/*
						 * If the argument type is list, no such method
						 * exception will be thrown. I don't know exactly why
						 * this is happening. But to complete the functionality,
						 * I have to catch the exception and loop through all
						 * the methods to target the one I want to invoke.
						 */
						try {
							entityMethod = entityClass.getMethod(entityMethodName, xmlData.getClass());
						} catch (Exception e) {
							Method[] entityMethods = entityClass.getMethods();
							logger.debug("looping the methods");
							for (Method m : entityMethods) {
								if (m.getName().equals(entityMethodName)) {
									entityMethod = m;
									break;
								}
							}
						}
						logger.debug("transferData: set method prepared - " + entityMethod.getName());
						entityMethod.invoke(entity, xmlData);
						logger.debug("transferData: set method executed");
					}
				}
			}
			return entity;
		} else if (obj instanceof List) {
			logger.debug("this is a List - " + obj.getClass().getName());
			logger.debug("this is a List - ");

			for (int i = 0; i < ((List) obj).size(); i++)
				((List) obj).set(i, convertData(((List) obj).get(i)));
			logger.debug("finished processing list - " + obj.getClass().getName());
			return obj;
		} else {
			logger.debug("this is other type - " + obj.getClass().getName());
			return obj;
		}
	}
}
