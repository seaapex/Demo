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
		if (obj == null)
			return null;
		if (obj instanceof BaseXML) {
			logger.debug("this is a BaseXML");
			String className = obj.getClass().getSimpleName();
			Class<?> xmlClass = obj.getClass();
			Class<?> entityClass = Class.forName(ENTITY_PACKAGE + "." + className.substring(0, className.length() - 3));
			Object entity = entityClass.newInstance();
			Method[] methods = xmlClass.getMethods();
			for (Method method : methods) {
				String xmlMethodName = method.getName();
				if (xmlMethodName.startsWith("get")) {
					logger.debug("transferData: get method detected - " + xmlMethodName);
					if ("getClass".equals(xmlMethodName))
						continue;
					Object xmlData = method.invoke(obj);
					logger.debug("transferData: get method returned - " + xmlData);
					if (xmlData != null) {
						logger.debug("transferData: xmlData is not null - " + xmlData);
						xmlData = convertData(xmlData);
						String entityMethodName = xmlMethodName.replaceFirst("get", "set");
						logger.debug("transferData: set method preparing - " + entityMethodName + " - Data type - "
								+ xmlData);
						Method entityMethod = null;
						try {
							entityMethod = entityClass.getMethod(entityMethodName, xmlData.getClass());
						} catch (Exception e) {
							e.printStackTrace();
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
