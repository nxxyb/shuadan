package com.photography.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.photography.dao.IHibernateDao;
import com.photography.dao.exp.Expression;
import com.photography.dao.query.Pager;
import com.photography.dao.query.Sort;
import com.photography.exception.ServiceException;
import com.photography.mapping.BaseMapping;
import com.photography.mapping.ShuadanLog;

/**
 * 
 * @author 徐雁斌
 * @since 2015-2-6
 * 
 * @copyright 2015 天大求实电力新技术股份有限公司 版权所有
 */
@Service
public abstract class BaseServiceImpl implements IBaseService {
	
	public abstract IHibernateDao getDao();
	/* 
	 * @see com.photography.service.IBaseService#loadPojo(java.lang.String)
	 */
	public BaseMapping loadPojo(String id) {
		return (BaseMapping) getDao().getById(getPojoClass(), id);
	}

	/* 
	 * @see com.photography.service.IBaseService#loadPojoByExpression(com.photography.dao.exp.Expression, com.photography.dao.query.Sort)
	 */
	@SuppressWarnings("unchecked")
	public List<BaseMapping> loadPojoByExpression(Expression expression, Sort sort) {
		return (List<BaseMapping>) getDao().getByQuery(getPojoClass(), expression,sort);
	}

	/* 
	 * @see com.photography.service.IBaseService#savePojo(com.photography.mapping.BaseMapping, com.photography.mapping.User)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void savePojo(BaseMapping pojo, ShuadanLog user) throws ServiceException {
		if("".equals(pojo.getId())){
			pojo.setId(null);
		}
		getDao().saveOrUpdate(pojo);

	}

	/* 
	 * @see com.photography.service.IBaseService#deletePojo(com.photography.mapping.BaseMapping, com.photography.mapping.User)
	 */
	public void deletePojo(BaseMapping pojo, ShuadanLog user) throws ServiceException {
		getDao().delete(pojo);
	}

	/* 
	 * @see com.photography.service.IBaseService#getPojoList(com.photography.dao.query.Pager, com.photography.dao.exp.Expression, com.photography.dao.query.Sort, com.photography.mapping.User)
	 */
	public List<BaseMapping> getPojoList(Pager pager, Expression expression, Sort sort, ShuadanLog user) {
		return getDao().getPojoList(getPojoClass(), pager, expression, sort, user);
	}
	
	/* 
	 * @see com.photography.service.IBaseService#addUpdateLog(com.photography.mapping.BaseMapping, com.photography.mapping.User)
	 */
	public void addUpdateLog(BaseMapping BaseMapping, ShuadanLog user) {
		// TODO Auto-generated method stub

	}

}
