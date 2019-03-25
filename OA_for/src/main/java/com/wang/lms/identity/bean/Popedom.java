package com.wang.lms.identity.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;



@Entity @Table(name="OA_ID_POPEDOM")
public class Popedom implements Serializable {
	
	private static final long serialVersionUID = -1246107000138494011L;
	/** 编号	PK主键自增长 */
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Long id;
	/** 权限与模块存在N-1关联  模块代码 FK(OA_ID_MODULE) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Module.class)
	@JoinColumn(name="MODULE_CODE", referencedColumnName="CODE", 
					foreignKey=@ForeignKey(name="FK_POPEDOM_MODULE")) // 更改外键约束名
	private Module module;
	/** 权限与操作存在N-1关联  操作代码 FK(OA_ID_MODULE) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Module.class)
	@JoinColumn(name="OPERA_CODE", referencedColumnName="CODE", 
					foreignKey=@ForeignKey(name="FK_POPEDOM_OPERA")) // 更改外键约束名
	private Module opera;
	/** 权限与角色存在N-1关联  角色  FK(OA_ID_ROLE) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=Role.class)
	@JoinColumn(name="ROLE_ID", referencedColumnName="ID", 
					foreignKey=@ForeignKey(name="FK_POPEDOM_ROLE")) // 更改外键约束名
	private Role role;
	/** 权限创建人与用户存在多对一关联(FK(OA_ID_USER)) */
	@ManyToOne(fetch=FetchType.LAZY, targetEntity=User.class)
	@JoinColumn(name="CREATER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_POPEDOM_CREATER")) // 更改外键约束名
	private User creater;
	/** 创建时间 */
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	/** setter and getter method */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Module getModule() {
		return module;
	}
	public void setModule(Module module) {
		this.module = module;
	}
	public Module getOpera() {
		return opera;
	}
	public void setOpera(Module opera) {
		this.opera = opera;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public User getCreater() {
		return creater;
	}
	public void setCreater(User creater) {
		this.creater = creater;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}