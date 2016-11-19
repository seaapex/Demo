package ca.ljz.demo.xml;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.model.BaseModel;
import ca.ljz.demo.model.UserModel;

public abstract class BaseXML implements BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764276953073217499L;

	protected transient final Logger logger = LoggerFactory.getLogger(getClass());

	private String uuid;

	private Date creatTime;

	private Date editTime;

	private UserModel creator;

	private UserModel editor;

	@Override
	@XmlTransient
	public byte[] getId() {
		return null;
	}

	@Override
	public String getUUID() {
		return this.uuid;
	}

	@Override
	public void setUUID(String uUID) {
		this.uuid = uUID;
	}

	@Override
	public Date getCreatTime() {
		return this.creatTime;
	}

	@Override
	public Date getEditTime() {
		return this.editTime;
	}

	@Override
	@XmlTransient
	public UserModel getCreator() {
		return this.creator;
	}

	@Override
	@XmlElement(type = UserXML.class)
	public void setCreator(UserModel creator) {
		this.creator = creator;
	}

	@Override
	@XmlTransient
	public UserModel getEditor() {
		return this.editor;
	}

	@Override
	@XmlElement(type = UserXML.class)
	public void setEditor(UserModel editor) {
		this.editor = editor;
	}
}
