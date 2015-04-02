package com.photography.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photography.dao.IHibernateDao;
import com.photography.dao.IShuadanUserDao;
import com.photography.mapping.BaseMapping;
import com.photography.mapping.ShuadanUser;

/**
 * 
 * @author 徐雁斌
 * @since 2015-3-13
 */
@Service("shuadanUserService")
public class ShuadanUserServiceImpl extends BaseServiceImpl implements IShuadanUserService {
	
	@Autowired
	private IShuadanUserDao shuadanUserDao;

	public void setShuadanUserDao(IShuadanUserDao shuadanUserDao) {
		this.shuadanUserDao = shuadanUserDao;
	}

	public Class<? extends BaseMapping> getPojoClass() {
		return ShuadanUser.class;
	}

	/* 
	 * @see com.photography.service.BaseServiceImpl#getDao()
	 */
	@Override
	public IHibernateDao getDao() {
		return shuadanUserDao;
	}

}
