package ca.ljz.demo.entities;

import java.io.Serializable;
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

import ca.ljz.demo.utils.UUIDUtils;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Base implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764276953073217499L;

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
	@ManyToOne
	@JoinColumn(name = "CREATOR_ID", nullable = false)
	private User creator;

	// uni-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name = "EDITOR_ID", nullable = false)
	private User editor;

	/**
	 * This method is only used by JPA. Data within is not human readable.
	 * 
	 * @return id
	 */
	@XmlTransient
	public byte[] getId() {
		if (this.id == null || this.id.length != 16) {

			UUID uuid = UUID.randomUUID();

			this.id = UUIDUtils.uuidToByteArray(uuid);
		}

		return this.id;
	}

	public String getUUID() {
		return UUIDUtils.byteArrayToUUIDString(getId());
	}

	public void setUUID(String id) {
		this.id = UUIDUtils.uuidToByteArray(id);
	}


	public Date getCreatTime() {
		return this.creatTime;
	}

	public Date getEditTime() {
		return this.editTime;
	}

	@XmlTransient
	public User getCreator() {
		return this.creator;
	}
	
	public void setCreator(User creator){
		this.creator = creator;
	}

	@XmlTransient
	public User getEditor() {
		return this.editor;
	}
	
	public void setEditor(User editor){
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
