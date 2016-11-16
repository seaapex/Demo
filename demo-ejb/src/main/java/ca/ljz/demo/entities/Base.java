package ca.ljz.demo.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlTransient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.utils.UUIDUtils;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Base implements IModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764276953073217499L;
	
	protected transient final Logger logger = LoggerFactory.getLogger(getClass());

	@Id
	@Column(unique = true, nullable = false, length = 16)
	private byte[] id;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREAT_TIME", nullable = false)
	private Date creatTime;

	@Temporal(TemporalType.DATE)
	@Column(name = "EDIT_TIME", nullable = false)
	private Date editTime;

	// uni-directional many-to-one association to User
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "CREATOR_ID", nullable = false)
	private IUser creator;

	// uni-directional many-to-one association to User
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "EDITOR_ID", nullable = false)
	private IUser editor;

	/**
	 * This method is only used by JPA. Data within is not human readable.
	 * 
	 * @return id
	 */
	@XmlTransient
	@Override
	public byte[] getId() {
		if (this.id == null || this.id.length != 16) {

			UUID uuid = UUID.randomUUID();

			this.id = UUIDUtils.uuidToByteArray(uuid);
		}

		return this.id;
	}

	@Override
	public String getUUID() {
		return UUIDUtils.byteArrayToUUIDString(getId());
	}

	@Override
	public void setUUID(String id) {
		this.id = UUIDUtils.uuidToByteArray(id);
	}

	@Override
	public Date getCreatTime() {
		return this.creatTime;
	}

	@Override
	public Date getEditTime() {
		return this.editTime;
	}

	@XmlTransient
	@Override
	public IUser getCreator() {
		return this.creator;
	}

	@Override
	public void setCreator(IUser creator) {
		this.creator = creator;
	}

	@XmlTransient
	@Override
	public IUser getEditor() {
		return this.editor;
	}

	@Override
	public void setEditor(IUser editor) {
		this.editor = editor;
	}

	@PrePersist
	protected void prepersist() {
		this.creatTime = new Date();
		preupdate();
	}

	@PreUpdate
	protected void preupdate() {
		this.editTime = new Date();
	}

}
