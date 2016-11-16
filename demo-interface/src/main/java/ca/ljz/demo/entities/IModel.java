package ca.ljz.demo.entities;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

public interface IModel extends Serializable {

	@XmlTransient
	public byte[] getId();

	public String getUUID();

	public void setUUID(String id);

	public Date getCreatTime();

	public Date getEditTime();

	@XmlTransient
	public IUser getCreator();

	public void setCreator(IUser creator);

	@XmlTransient
	public IUser getEditor();

	public void setEditor(IUser editor);
}
