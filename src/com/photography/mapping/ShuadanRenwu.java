package com.photography.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.photography.utils.Constants;
import com.photography.utils.DateUtil;

/**
 * 搭乘任务
 */
@Entity(name="shuadan_renwu")
public class ShuadanRenwu extends BaseMapping{
	
	private static final long serialVersionUID = -5155878212794168022L;
	
	/**
	 * 车主手机号
	 */
	@Column(name="chezhu_mobile")
	private String chezhu_mobile;
	
	/**
	 * 车主密码
	 */
	@Column(name="chezhu_password")
	private String chezhu_password;
	
	/**
	 * 车主设备序列号
	 */
	@Column(name="chezhu_deviceid")
	private String chezhu_deviceid;
	
	/**
	 * 乘客手机号
	 */
	@Column(name="chengke_mobile")
	private String chengke_mobile;
	
	/**
	 * 乘客密码
	 */
	@Column(name="chengke_password")
	private String chengke_password;
	
	/**
	 * 乘客设备序列号
	 */
	@Column(name="chengke_deviceid")
	private String chengke_deviceid;
	
	/**
	 * 乘客支付宝帐号
	 */
	@Column(name="chengke_zfb_num")
	private String chengke_zfb_num;
	
	/**
	 * 乘客支付宝密码
	 */
	@Column(name="chengke_zfb_password")
	private String chengke_zfb_password;
	
	/**
	 * 车主状态
	 */
	@Column(name="chezhu_status")
	private String chezhu_status;
	
	/**
	 * 乘客状态
	 */
	@Column(name="chengke_status")
	private String chengke_status;
	
	/**
	 * 分组
	 * 乘客和司机分为一组才能匹配搭乘
	 */
	@Column(name="group_type")
	private String group_type;
	
	/**
	 * 乘客标识图片名称
	 */
	@Column(name="chengke_sb_bstp")
	private String chengke_sb_bstp;
	
	/**
	 * 乘客标识图片名称
	 */
	@Column(name="chengke_xb_bstp")
	private String chengke_xb_bstp;
	
	/**
	 * 乘客发单状态 
	 * 0-未发 1-已发  2-已发且有车主正在抢单 
	 * 3-车主抢单完成 4-乘客付款完成 
	 * 5-乘客确认搭乘 6-乘客评价完成
	 */
	@Column(name="fd_status")
	private String fd_status;
	
	/**
	 * 乘客付款状态 
	 * 0-未付 1-已付
	 */
	@Column(name="fk_status")
	private String fk_status;
	
	/**
	 * 乘客发单时间
	 */
	@Column(name="chengke_fd_time")
	private String chengke_fd_time;
	
	/**
	 * 车主抢单时间
	 */
	@Column(name="chezhu_qd_time")
	private String chezhu_qd_time;

	public String getChezhu_mobile() {
		return chezhu_mobile;
	}

	public void setChezhu_mobile(String chezhu_mobile) {
		this.chezhu_mobile = chezhu_mobile;
	}

	public String getChengke_mobile() {
		return chengke_mobile;
	}

	public void setChengke_mobile(String chengke_mobile) {
		this.chengke_mobile = chengke_mobile;
	}

	public String getFd_status() {
		return fd_status;
	}

	public void setFd_status(String fd_status) {
		this.fd_status = fd_status;
	}

	public String getFk_status() {
		return fk_status;
	}

	public void setFk_status(String fk_status) {
		this.fk_status = fk_status;
	}

	public String getChengke_fd_time() {
		return chengke_fd_time;
	}

	public void setChengke_fd_time(String chengke_fd_time) {
		this.chengke_fd_time = chengke_fd_time;
	}

	public String getChezhu_qd_time() {
		return chezhu_qd_time;
	}

	public void setChezhu_qd_time(String chezhu_qd_time) {
		this.chezhu_qd_time = chezhu_qd_time;
	}

	public String getChezhu_deviceid() {
		return chezhu_deviceid;
	}

	public void setChezhu_deviceid(String chezhu_deviceid) {
		this.chezhu_deviceid = chezhu_deviceid;
	}

	public String getChengke_deviceid() {
		return chengke_deviceid;
	}

	public void setChengke_deviceid(String chengke_deviceid) {
		this.chengke_deviceid = chengke_deviceid;
	}
	
	public String getChezhu_password() {
		return chezhu_password;
	}

	public void setChezhu_password(String chezhu_password) {
		this.chezhu_password = chezhu_password;
	}

	public String getChengke_password() {
		return chengke_password;
	}

	public void setChengke_password(String chengke_password) {
		this.chengke_password = chengke_password;
	}

	public String getChezhu_status() {
		return chezhu_status;
	}

	public void setChezhu_status(String chezhu_status) {
		this.chezhu_status = chezhu_status;
	}

	public String getChengke_status() {
		return chengke_status;
	}

	public void setChengke_status(String chengke_status) {
		this.chengke_status = chengke_status;
	}

	public String getGroup_type() {
		return group_type;
	}

	public void setGroup_type(String group_type) {
		this.group_type = group_type;
	}

	public String getChengke_sb_bstp() {
		return chengke_sb_bstp;
	}

	public void setChengke_sb_bstp(String chengke_sb_bstp) {
		this.chengke_sb_bstp = chengke_sb_bstp;
	}

	public String getChengke_xb_bstp() {
		return chengke_xb_bstp;
	}

	public void setChengke_xb_bstp(String chengke_xb_bstp) {
		this.chengke_xb_bstp = chengke_xb_bstp;
	}

	public String getChengke_zfb_num() {
		return chengke_zfb_num;
	}

	public void setChengke_zfb_num(String chengke_zfb_num) {
		this.chengke_zfb_num = chengke_zfb_num;
	}

	public String getChengke_zfb_password() {
		return chengke_zfb_password;
	}

	public void setChengke_zfb_password(String chengke_zfb_password) {
		this.chengke_zfb_password = chengke_zfb_password;
	}

	public String toString(){
		StringBuffer sf = new StringBuffer();
		sf.append(this.getId()).append(Constants.SEPARATOR);					//0 id
		sf.append(this.getChezhu_mobile()).append(Constants.SEPARATOR);			//1 车主手机
		sf.append(this.getChezhu_password()).append(Constants.SEPARATOR);		//2 车主密码
		sf.append(this.getChezhu_deviceid()).append(Constants.SEPARATOR);		//3 车主设备序列号
		sf.append(this.getChezhu_status()).append(Constants.SEPARATOR);			//4 车主状态
		sf.append(this.getChengke_mobile()).append(Constants.SEPARATOR);		//5 乘客手机
		sf.append(this.getChengke_password()).append(Constants.SEPARATOR);		//6 乘客密码
		sf.append(this.getChengke_deviceid()).append(Constants.SEPARATOR);		//7 乘客设备序列号
		sf.append(this.getChengke_zfb_num()).append(Constants.SEPARATOR);		//8 乘客支付宝帐号
		sf.append(this.getChengke_zfb_password()).append(Constants.SEPARATOR);	//9 乘客支付宝密码
		sf.append(this.getChengke_status()).append(Constants.SEPARATOR);		//10 乘客状态
		sf.append(this.getFd_status()).append(Constants.SEPARATOR);				//11 发单状态 
		sf.append(this.getFk_status()).append(Constants.SEPARATOR);				//12 乘客付款状态 
		sf.append(this.getChengke_sb_bstp()).append(Constants.SEPARATOR);		//13 乘客标识图片名称
		sf.append(this.getChengke_xb_bstp()).append(Constants.SEPARATOR);		//14 乘客标识图片名称
		sf.append(this.getGroup_type()).append(Constants.SEPARATOR);			//15 分组
		sf.append(DateUtil.getSX());											//16 发单标识 0-上班发单  1-下班发单 2-附近发单
		
		return sf.toString();
	}

}
