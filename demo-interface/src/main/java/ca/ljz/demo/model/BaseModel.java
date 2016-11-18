package ca.ljz.demo.model;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlTransient;

public interface BaseModel extends Serializable {

	@XmlTransient
	public byte[] getId();

	public String getUUID();

	public void setUUID(String id);

	public Date getCreatTime();

	public Date getEditTime();

	@XmlTransient
	public UserModel getCreator();

	public void setCreator(UserModel creator);

	@XmlTransient
	public UserModel getEditor();

	public void setEditor(UserModel editor);
}
