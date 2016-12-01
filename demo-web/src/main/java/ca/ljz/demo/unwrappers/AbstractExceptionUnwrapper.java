package ca.ljz.demo.unwrappers;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractExceptionUnwrapper<T extends Throwable> implements ExceptionUnwrapper<T> {
	private static Logger logger = Logger.getLogger(AbstractExceptionUnwrapper.class.getName());

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private final Class<T> exceptionType = ((Class) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0]);

	@SuppressWarnings("unchecked")
	@Override
	public T unwrap(Throwable t) {
		while ((t = t.getCause()) != null) {

			logger.log(Level.INFO, "handling exception: " + t);
			logger.log(Level.INFO, "handling exception: " + t.getClass().getName());

			if (exceptionType.isInstance(t)) {
				logger.log(Level.INFO, "handling exception message");

				T ex = (T) t;

				return ex;
			}
		}
		return null;
	}

}
