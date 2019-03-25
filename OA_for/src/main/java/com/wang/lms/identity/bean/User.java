package com.wang.lms.identity.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.wang.lms.hrm.bean.Dept;
import com.wang.lms.hrm.bean.Job;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



@Entity @Table(name="OA_ID_USER", 
		indexes={@Index(columnList="NAME", name="IDX_USER_NAME")})
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class User implements Serializable {
	
	private static final long serialVersionUID = -3417930882448168081L;
	/** 用户ID	PK，大小写英文和数字 */
	@Id @Column(name="USER_ID", length=50)
	private String userId;
	/** 密码	MD5加密 */	
	@Column(name="PASS_WORD", length=50)
	private String passWord;
	/** 姓名 */
	@Column(name="NAME", length=50)
	private String name;
	/** 性别	1:男 2:女 */
	@Column(name="SEX")
	private Short sex = 1;
	/** 用户与部门存在多对一关联    部门	FK(OA_ID_DEPT) */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Dept.class)
	@JoinColumn(name="DEPT_ID", referencedColumnName="ID",
			foreignKey=@ForeignKey(name="FK_USER_DEPT"))
	private Dept dept;
	
	/** 用户与职位存在多对一关联    职位	FK(OA_ID_JOB) */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=Job.class)
	@JoinColumn(name="JOB_CODE", referencedColumnName="CODE",
			foreignKey=@ForeignKey(name="FK_USER_JOB"))
	private Job job;
	
	/** 邮箱 */
	@Column(name="EMAIL", length=50)
	private String email;
	/** 电话号码 */
	@Column(name="TEL", length=50)
	private String tel;
	/** 手机号码 */
	@Column(name="PHONE", length=50)
	private String phone;
	/** QQ号码 */
	@Column(name="QQ_NUM", length=50)
	private String qqNum;
	/** 问题编号 */
	@Column(name="QUESTION")
	private Short question;
	/** 回答结果 */
	@Column(name="ANSWER", length=200)
	private String answer;
	/** 状态	0：新建|冻结 , 1：激活 */
	@Column(name="STATUS")
	private Short status = 0;
	/** 用户创建人与用户存在多对一关联(FK(OA_ID_USER)) */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=User.class)
	@JoinColumn(name="CREATER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_USER_CREATER")) // 更改外键约束名
	private User creater;
	
	
	
	/** 创建时间 */
	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	/** 用户修改人与用户存在多对一关联(FK(OA_ID_USER)) */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=User.class)
	@JoinColumn(name="MODIFIER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_USER_MODIFIER")) // 更改外键约束名
	private User modifier;
	/** 修改时间 */
	@Column(name="MODIFY_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifyDate;
	/** 部门审核人与用户存在多对一关联(FK(OA_ID_USER)) */
	@ManyToOne(fetch=FetchType.EAGER, targetEntity=User.class)
	@JoinColumn(name="CHECKER", referencedColumnName="USER_ID", 
				foreignKey=@ForeignKey(name="FK_USER_CHECKER")) // 更改外键约束名
	private User checker;
	/** 审核时间 */
	@Column(name="CHECK_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date checkDate;
	
	/** 删除标志位 */
	@Column(name="del_flag")
	private String delFlag = "1";// 0:已删除   1：正常
	

	/** 用户与角色存在N-N关联 */
	@ManyToMany(fetch=FetchType.LAZY, targetEntity=Role.class, mappedBy="users")
	private Set<Role> roles = new HashSet<>();
	
	/** setter and getter method */
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getSex() {
		return sex;
	}
	public void setSex(Short sex) {
		this.sex = sex;
	}
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	public Job getJob() {
		return job;
	}
	public void setJob(Job job) {
		this.job = job;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getQqNum() {
		return qqNum;
	}
	public void setQqNum(String qqNum) {
		this.qqNum = qqNum;
	}
	public Short getQuestion() {
		return question;
	}
	public void setQuestion(Short question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
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
	public User getChecker() {
		return checker;
	}
	public void setChecker(User checker) {
		this.checker = checker;
	}
	public Date getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	
	
	public String getDelFlag() {
		return delFlag;
	}
	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
	@Override
	public String toString() {
		return "User [userId=" + userId + ", passWord=" + passWord + ", name=" + name + "]";
	}
	
	
}