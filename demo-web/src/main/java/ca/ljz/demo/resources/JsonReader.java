package ca.ljz.demo.resources;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.entities.IModel;
import ca.ljz.demo.entities.IUser;

@Provider
@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
public class JsonReader implements MessageBodyReader<IUser> {

	protected transient final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
		logger.info("isReadable");
		boolean isIModel = type.getClass().isInstance(IModel.class);

		return isIModel;
	}

	@Override
	public IUser readFrom(Class<IUser> type, Type genericType, Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {
		logger.info("readFrom");

		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(/* No way to use User.class */);
			final Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
			return (IUser) unMarshaller.unmarshal(entityStream);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
