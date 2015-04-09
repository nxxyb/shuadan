package com.photography.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.photography.utils.Constants;

/**
 * 乘客信息
 */
@Entity(name="shuadan_chengke")
public class ShuadanChengke extends BaseMapping{

	private static final long serialVersionUID = -2819821289288238847L;
	
	/**
	 * 乘客姓名
	 */
	@Column(name="chengke_name")
	private String chengke_name;
	
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
	 * 乘客所在城市
	 */
	@Column(name="chengke_city")
	private String chengke_city;
	
	/**
	 * 乘客家道路或地名
	 */
	@Column(name="chengke_jplace")
	private String chengke_jplace;
	
	/**
	 * 乘客单位道路或地名
	 */
	@Column(name="chengke_dplace")
	private String chengke_dplace;
	
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
	 * 分组
	 * 乘客和司机分为一组才能匹配搭乘
	 */
	@Column(name="group_type")
	private String group_type;
	
	/**
	 * 乘客上班标识图片名称
	 */
	@Column(name="chengke_sb_bstp")
	private String chengke_sb_bstp;
	
	/**
	 * 乘客下班标识图片名称
	 */
	@Column(name="chengke_xb_bstp")
	private String chengke_xb_bstp;

	public String getChengke_name() {
		return chengke_name;
	}

	public void setChengke_name(String chengke_name) {
		this.chengke_name = chengke_name;
	}

	public String getChengke_mobile() {
		return chengke_mobile;
	}

	public void setChengke_mobile(String chengke_mobile) {
		this.chengke_mobile = chengke_mobile;
	}

	public String getChengke_password() {
		return chengke_password;
	}

	public void setChengke_password(String chengke_password) {
		this.chengke_password = chengke_password;
	}

	public String getChengke_city() {
		return chengke_city;
	}

	public void setChengke_city(String chengke_city) {
		this.chengke_city = chengke_city;
	}

	public String getChengke_jplace() {
		return chengke_jplace;
	}

	public void setChengke_jplace(String chengke_jplace) {
		this.chengke_jplace = chengke_jplace;
	}

	public String getChengke_dplace() {
		return chengke_dplace;
	}

	public void setChengke_dplace(String chengke_dplace) {
		this.chengke_dplace = chengke_dplace;
	}

	public String getChengke_deviceid() {
		return chengke_deviceid;
	}

	public void setChengke_deviceid(String chengke_deviceid) {
		this.chengke_deviceid = chengke_deviceid;
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

	public String toString(){
		StringBuffer sf = new StringBuffer();
		sf.append(this.getId()).append(Constants.SEPARATOR);					//0 id
		sf.append(this.getChengke_name()).append(Constants.SEPARATOR);			//1 乘客姓名
		sf.append(this.getChengke_mobile()).append(Constants.SEPARATOR);		//2 乘客手机
		sf.append(this.getChengke_password()).append(Constants.SEPARATOR);		//3 乘客密码
		sf.append(this.getChengke_city()).append(Constants.SEPARATOR);			//4 乘客城市
		sf.append(this.getChengke_jplace()).append(Constants.SEPARATOR);		//5 乘客家地址
		sf.append(this.getChengke_dplace()).append(Constants.SEPARATOR);		//6 乘客单位地址
		sf.append(this.getChengke_deviceid()).append(Constants.SEPARATOR);		//7 乘客设备序列号
		sf.append(this.getChengke_zfb_num()).append(Constants.SEPARATOR);		//8 乘客支付宝帐号
		sf.append(this.getChengke_zfb_password()).append(Constants.SEPARATOR);	//9 乘客支付宝密码
		sf.append(this.getGroup_type()).append(Constants.SEPARATOR);			//10 分组
		sf.append(this.getChengke_sb_bstp()).append(Constants.SEPARATOR);		//11 乘客上班标识图片名称
		sf.append(this.getChengke_xb_bstp());									//12 乘客下班标识图片名称
		
		return sf.toString();
	}
	
	
}
