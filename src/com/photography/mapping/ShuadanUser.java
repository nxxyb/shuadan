package com.photography.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.photography.utils.Constants;

/**
 * 日志
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
	 * 车主所在道路或地名
	 */
	@Column(name="chezhu_place")
	private String chezhu_place;
	
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
	 * 乘客所在道路或地名
	 */
	@Column(name="chengke_place")
	private String chengke_place;
	
	/**
	 * 乘客是否发单 
	 * 0-未发 1-已发  2-已发且有车主正在抢单 
	 * 3-车主抢单完成 4-乘客付款完成 
	 * 5-乘客确认搭乘 6-乘客评价完成
	 */
	@Column(name="chengke_fd")
	private String chengke_fd;
	
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

	public String getChezhu_place() {
		return chezhu_place;
	}

	public void setChezhu_place(String chezhu_place) {
		this.chezhu_place = chezhu_place;
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

	public String getChengke_place() {
		return chengke_place;
	}

	public void setChengke_place(String chengke_place) {
		this.chengke_place = chengke_place;
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

	public String getChengke_fd() {
		return chengke_fd;
	}

	public void setChengke_fd(String chengke_fd) {
		this.chengke_fd = chengke_fd;
	}
	
	public String toString(){
		StringBuffer sf = new StringBuffer();
		sf.append(this.getId()).append(Constants.SEPARATOR);
		sf.append(this.getChezhu_name()).append(Constants.SEPARATOR);
		sf.append(this.getChezhu_mobile()).append(Constants.SEPARATOR);
		sf.append(this.getChezhu_password()).append(Constants.SEPARATOR);
		sf.append(this.getChezhu_city()).append(Constants.SEPARATOR);
		sf.append(this.getChezhu_place()).append(Constants.SEPARATOR);
		sf.append(this.getChengke_name()).append(Constants.SEPARATOR);
		sf.append(this.getChengke_mobile()).append(Constants.SEPARATOR);
		sf.append(this.getChengke_password()).append(Constants.SEPARATOR);
		sf.append(this.getChengke_city()).append(Constants.SEPARATOR);
		sf.append(this.getChengke_place()).append(Constants.SEPARATOR);
		sf.append(this.getChengke_fd()).append(Constants.SEPARATOR);
		sf.append(this.getChengke_fd_time()).append(Constants.SEPARATOR);
		sf.append(this.getChezhu_qd_time());
		
		return sf.toString();
	}
	
}
