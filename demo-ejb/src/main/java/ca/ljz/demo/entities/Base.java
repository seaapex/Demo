package ca.ljz.demo.entities;

import java.io.Serializable;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Base<I> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764276953073217499L;
	
	abstract public I getId();
}
