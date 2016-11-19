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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ca.ljz.demo.model.BaseModel;
import ca.ljz.demo.model.UserModel;
import ca.ljz.demo.utils.UUIDUtils;

@MappedSuperclass
@Access(AccessType.FIELD)
public abstract class Base implements BaseModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8764276953073217499L;
	
	private static final Logger logger = LoggerFactory.getLogger(Base.class);

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
	private UserModel creator;

	// uni-directional many-to-one association to User
	@ManyToOne(targetEntity = User.class)
	@JoinColumn(name = "EDITOR_ID", nullable = false)
	private UserModel editor;

	/**
	 * This method is only used by JPA. Data within is not human readable.
	 * 
	 * @return id
	 */
	@Override
	public byte[] getId() {
		logger.info("getId");
		if (this.id == null || this.id.length != 16) {

			UUID uuid = UUID.randomUUID();

			this.id = UUIDUtils.uuidToByteArray(uuid);
		}

		return this.id;
	}

	@Override
	public String getUUID() {
		logger.info("getUUID");
		return UUIDUtils.byteArrayToUUIDString(getId());
	}

	@Override
	public void setUUID(String id) {
		logger.info("setUUID");
		this.id = UUIDUtils.uuidToByteArray(id);
	}

	@Override
	public Date getCreatTime() {
		logger.info("getCreatTime");
		return this.creatTime;
	}

	@Override
	public Date getEditTime() {
		logger.info("getEditTime");
		return this.editTime;
	}

	@Override
	public UserModel getCreator() {
		logger.info("getCreator");
		return this.creator;
	}

	@Override
	public void setCreator(UserModel creator) {
		logger.info("setCreator");
		this.creator = creator;
	}

	@Override
	public UserModel getEditor() {
		logger.info("getEditor");
		return this.editor;
	}

	@Override
	public void setEditor(UserModel editor) {
		logger.info("setEditor");
		this.editor = editor;
	}

	@PrePersist
	protected void prepersist() {
		logger.info("prepersist");
		this.creatTime = new Date();
		preupdate();
	}

	@PreUpdate
	protected void preupdate() {
		logger.info("preupdate");
		this.editTime = new Date();
	}

}
