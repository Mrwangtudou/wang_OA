package com.wang.lms.notice.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wang.lms.identity.bean.User;


@Entity
@Table(name="oa_notice")
public class Notice implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(name="title", length=50)
	private String title;
	@Column(name="content")
	private String content;
	
	/** 角色创建人与用户存在多对一关联(FK(OA_ID_USER)) */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=NoticeType.class)
	@JoinColumn(name="notice_Type", referencedColumnName="id", 
				foreignKey=@ForeignKey(name="FK_notice_Type")) // 更改外键约束名
	private NoticeType noticeType;
	
	
	@Column(name="create_Date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	/** MODIFIER VARCHAR2(50) 修改人	FK(OA_ID_USER) N-1*/
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=User.class)
	@JoinColumn(name="creater", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_notice_creater")) // 更改外键约束名
	private User creater;
	
	/** MODIFIER VARCHAR2(50) 修改人	FK(OA_ID_USER) N-1*/
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=User.class)
	@JoinColumn(name="MODIFIER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_notice_MODIFIER")) // 更改外键约束名
	private User modifier;
	
	/** MODIFY_DATE	DATE	修改时间*/
	@Column(name="MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

	public User getModifier() {
		return modifier;
	}

	public void setModifier(User modifier) {
		this.modifier = modifier;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public NoticeType getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(NoticeType noticeType) {
		this.noticeType = noticeType;
	}
	
	
}