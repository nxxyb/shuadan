package com.photography.mapping;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 
 * @author 徐雁斌
 * @since 2015-2-6
 * 
 * @copyright 2015 天大求实电力新技术股份有限公司 版权所有
 */
@MappedSuperclass
public class BaseMapping implements Serializable{

	private static final long serialVersionUID = 4967549690310825900L;

	@Id
    @Column(name="id")
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
