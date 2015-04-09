package com.photography.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.photography.utils.Constants;

/**
 * 车主信息
 */
@Entity(name="shuadan_chezhu")
public class ShuadanChezhu extends BaseMapping{

	private static final long serialVersionUID = 4454119415517033878L;

	/**
	 * 车主姓名
	 */
	@Column(name="chezhu_name")
	private String chezhu_name;
	
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
	 * 车主所在城市
	 */
	@Column(name="chezhu_city")
	private String chezhu_city;
	
	/**
	 * 车主家地址
	 */
	@Column(name="chezhu_jplace")
	private String chezhu_jplace;
	
	/**
	 * 车主单位地址
	 */
	@Column(name="chezhu_dplace")
	private String chezhu_dplace;
	
	/**
	 * 车主设备序列号
	 */
	@Column(name="chezhu_deviceid")
	private String chezhu_deviceid;
	
	/**
	 * 车主支付宝帐号
	 */
	@Column(name="chezhu_zfb_num")
	private String chezhu_zfb_num;
	
	/**
	 * 车主支付宝密码
	 */
	@Column(name="chezhu_zfb_password")
	private String chezhu_zfb_password;
	
	/**
	 * 分组
	 * 乘客和司机分为一组才能匹配搭乘
	 */
	@Column(name="group_type")
	private String group_type;

	public String getChezhu_name() {
		return chezhu_name;
	}

	public void setChezhu_name(String chezhu_name) {
		this.chezhu_name = chezhu_name;
	}

	public String getChezhu_mobile() {
		return chezhu_mobile;
	}

	public void setChezhu_mobile(String chezhu_mobile) {
		this.chezhu_mobile = chezhu_mobile;
	}

	public String getChezhu_password() {
		return chezhu_password;
	}

	public void setChezhu_password(String chezhu_password) {
		this.chezhu_password = chezhu_password;
	}

	public String getChezhu_city() {
		return chezhu_city;
	}

	public void setChezhu_city(String chezhu_city) {
		this.chezhu_city = chezhu_city;
	}

	public String getChezhu_jplace() {
		return chezhu_jplace;
	}

	public void setChezhu_jplace(String chezhu_jplace) {
		this.chezhu_jplace = chezhu_jplace;
	}

	public String getChezhu_dplace() {
		return chezhu_dplace;
	}

	public void setChezhu_dplace(String chezhu_dplace) {
		this.chezhu_dplace = chezhu_dplace;
	}

	public String getChezhu_deviceid() {
		return chezhu_deviceid;
	}

	public void setChezhu_deviceid(String chezhu_deviceid) {
		this.chezhu_deviceid = chezhu_deviceid;
	}

	public String getChezhu_zfb_num() {
		return chezhu_zfb_num;
	}

	public void setChezhu_zfb_num(String chezhu_zfb_num) {
		this.chezhu_zfb_num = chezhu_zfb_num;
	}

	public String getChezhu_zfb_password() {
		return chezhu_zfb_password;
	}

	public void setChezhu_zfb_password(String chezhu_zfb_password) {
		this.chezhu_zfb_password = chezhu_zfb_password;
	}
	
	public String getGroup_type() {
		return group_type;
	}

	public void setGroup_type(String group_type) {
		this.group_type = group_type;
	}

	public String toString(){
		StringBuffer sf = new StringBuffer();
		sf.append(this.getId()).append(Constants.SEPARATOR);					//0 id
		sf.append(this.getChezhu_name()).append(Constants.SEPARATOR);			//1 车主姓名
		sf.append(this.getChezhu_mobile()).append(Constants.SEPARATOR);			//2 车主手机
		sf.append(this.getChezhu_password()).append(Constants.SEPARATOR);		//3 车主密码
		sf.append(this.getChezhu_city()).append(Constants.SEPARATOR);			//4 车主城市
		sf.append(this.getChezhu_jplace()).append(Constants.SEPARATOR);			//5 车主家地址
		sf.append(this.getChezhu_dplace()).append(Constants.SEPARATOR);			//6 车主单位地址
		sf.append(this.getChezhu_deviceid()).append(Constants.SEPARATOR);		//7 车主设备序列号
		sf.append(this.getChezhu_zfb_num()).append(Constants.SEPARATOR);		//8 车主支付宝帐号
		sf.append(this.getChezhu_zfb_password()).append(Constants.SEPARATOR);	//9 车主支付宝密码
		sf.append(this.getGroup_type());												//10 分组
		
		return sf.toString();
	}
	
}
