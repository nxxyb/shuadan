package com.photography.mapping;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * 用户
 * @author 徐雁斌
 * @since 2015-2-3
 */
@Entity(name="shuadan_log")
public class ShuadanLog extends BaseMapping{
	
	private static final long serialVersionUID = 8854336652654077157L;
	
	/**
	 * 用户Id
	 */
	@Column(name="user_id")
	private Integer user_id;
	
	
	/**
	 * 抢单状态  0-乘客已发单  1-车主正在抢单  3-抢单完成  4-抢单失败'
	 */
	@Column(name="chezhu_isqd")
	private String chezhu_isqd;
	
	/**
	 * 抢单失败原因
	 */
	@Column(name="chezhu_qd_yy")
	private String chezhu_qd_yy;
	
	/**
	 * 抢单完成时间
	 */
	@Column(name="qd_time")
	private String qd_time;

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public String getChezhu_isqd() {
		return chezhu_isqd;
	}

	public void setChezhu_isqd(String chezhu_isqd) {
		this.chezhu_isqd = chezhu_isqd;
	}

	public String getChezhu_qd_yy() {
		return chezhu_qd_yy;
	}

	public void setChezhu_qd_yy(String chezhu_qd_yy) {
		this.chezhu_qd_yy = chezhu_qd_yy;
	}

	public String getQd_time() {
		return qd_time;
	}

	public void setQd_time(String qd_time) {
		this.qd_time = qd_time;
	}
}
