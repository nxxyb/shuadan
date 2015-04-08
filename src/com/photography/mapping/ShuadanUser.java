package com.photography.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.photography.utils.Constants;

/**
 * 用户
 * @author 徐雁斌
 * @since 2015-2-5
 */
@Entity(name="shuadan_user")
public class ShuadanUser extends BaseMapping{
	
	private static final long serialVersionUID = -5155878212794168022L;
	
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

	public String toString(){
		StringBuffer sf = new StringBuffer();
		sf.append(this.getId()).append(Constants.SEPARATOR);					//0 id
		sf.append(this.getChezhu_name()).append(Constants.SEPARATOR);			//1 车主姓名
		sf.append(this.getChezhu_mobile()).append(Constants.SEPARATOR);			//2 车主手机
		sf.append(this.getChezhu_password()).append(Constants.SEPARATOR);		//3 车主密码
		sf.append(this.getChezhu_city()).append(Constants.SEPARATOR);			//4 车主城市
		sf.append(this.getChezhu_jplace()).append(Constants.SEPARATOR);			//5 车主家地址
		sf.append(this.getChezhu_dplace()).append(Constants.SEPARATOR);			//6 车主单位地址
		sf.append(this.getChengke_name()).append(Constants.SEPARATOR);			//7 乘客姓名
		sf.append(this.getChengke_mobile()).append(Constants.SEPARATOR);		//8 乘客手机
		sf.append(this.getChengke_password()).append(Constants.SEPARATOR);		//9 乘客密码
		sf.append(this.getChengke_city()).append(Constants.SEPARATOR);			//10 乘客城市
		sf.append(this.getChengke_jplace()).append(Constants.SEPARATOR);		//11 乘客家地址
		sf.append(this.getChengke_dplace()).append(Constants.SEPARATOR);		//12 乘客单位地址
		sf.append(this.getFd_status()).append(Constants.SEPARATOR);			    //13 拼车状态
		sf.append(this.getFk_status()).append(Constants.SEPARATOR);			    //14 付款状态
		sf.append(this.getChengke_fd_time()).append(Constants.SEPARATOR);		//15 乘客发单时间
		sf.append(this.getChezhu_qd_time()).append(Constants.SEPARATOR);		//16 车主抢单时间
		
		sf.append(this.getChezhu_deviceid()).append(Constants.SEPARATOR);		//17 车主设备序列号
		sf.append(this.getChezhu_zfb_num()).append(Constants.SEPARATOR);		//18 车主支付宝帐号
		sf.append(this.getChezhu_zfb_password()).append(Constants.SEPARATOR);	//19 车主支付宝密码
		sf.append(this.getChengke_deviceid()).append(Constants.SEPARATOR);		//20 乘客设备序列号
		sf.append(this.getChengke_zfb_num()).append(Constants.SEPARATOR);		//21 乘客支付宝帐号
		sf.append(this.getChengke_zfb_password());								//22 乘客支付宝密码
		
		return sf.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(1);
	}
}
