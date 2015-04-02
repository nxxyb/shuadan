package com.photography.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.photography.dao.IHibernateDao;
import com.photography.dao.IShuadanLogDao;
import com.photography.mapping.BaseMapping;
import com.photography.mapping.ShuadanLog;

/**
 * 
 * @author 徐雁斌
 * @since 2015-4-1
 */
@Service("shuadanLogService")
public class ShuadanLogServiceImpl extends BaseServiceImpl implements IShuadanLogService {

	@Autowired
	private IShuadanLogDao shuadanLogDao;

	public void setShuadanLogDao(IShuadanLogDao shuadanLogDao) {
		this.shuadanLogDao = shuadanLogDao;
	}

	public Class<? extends BaseMapping> getPojoClass() {
		return ShuadanLog.class;
	}

	/* 
	 * @see com.photography.service.BaseServiceImpl#getDao()
	 */
	@Override
	public IHibernateDao getDao() {
		return shuadanLogDao;
	}
	
}
