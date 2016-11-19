package ca.ljz.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

public interface BaseModel extends Serializable {

	@XmlTransient
	public byte[] getId();

	@XmlElement
	public String getUUID();

	@XmlElement
	public void setUUID(String id);

	@XmlElement
	public Date getCreatTime();

	@XmlElement
	public Date getEditTime();

	@XmlTransient
	public UserModel getCreator();

	@XmlElement
	public void setCreator(UserModel creator);

	@XmlTransient
	public UserModel getEditor();

	@XmlElement
	public void setEditor(UserModel editor);
}
