package ca.ljz.demo.entities;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

public interface IModel<U extends IUser<U, G>, G extends IGroup<U, G>> extends Serializable {

	@XmlTransient
	public byte[] getId();

	public String getUUID();

	public void setUUID(String id);

	public Date getCreatTime();

	public Date getEditTime();

	@XmlTransient
	public U getCreator();

	public void setCreator(U creator);

	@XmlTransient
	public U getEditor();

	public void setEditor(U editor);
}
