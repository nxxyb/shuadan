package com.photography.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photography.dao.IHibernateDao;

/**
 * 
 * @author 徐雁斌
 * @since 2015-3-13
 */
@Service("shuadanService")
public class ShuadanServiceImpl extends BaseServiceImpl implements IShuadanService {
	
	@Autowired
	private IHibernateDao hibernateDao;
	

	public IHibernateDao getHibernateDao() {
		return hibernateDao;
	}


	public void setHibernateDao(IHibernateDao hibernateDao) {
		this.hibernateDao = hibernateDao;
	}


	/* 
	 * @see com.photography.service.BaseServiceImpl#getDao()
	 */
	@Override
	public IHibernateDao getDao() {
		return getHibernateDao();
	}

}
