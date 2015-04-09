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
	public BaseMapping loadPojo(Class<? extends BaseMapping> entityType, String id) {
		return (BaseMapping) getDao().getById(entityType, id);
	}

	/* 
	 * @see com.photography.service.IBaseService#loadPojoByExpression(com.photography.dao.exp.Expression, com.photography.dao.query.Sort)
	 */
	@SuppressWarnings("unchecked")
	public List<BaseMapping> loadPojoByExpression(Class<? extends BaseMapping> entityType,Expression expression, Sort sort) {
		return (List<BaseMapping>) getDao().getByQuery(entityType, expression,sort);
	}

	/* 
	 * @see com.photography.service.IBaseService#savePojo(com.photography.mapping.BaseMapping, com.photography.mapping.User)
	 */
	@Transactional(propagation=Propagation.REQUIRED)
	public void savePojo(BaseMapping pojo) throws ServiceException {
		if("".equals(pojo.getId())){
			pojo.setId(null);
		}
		getDao().saveOrUpdate(pojo);

	}

	/* 
	 * @see com.photography.service.IBaseService#deletePojo(com.photography.mapping.BaseMapping, com.photography.mapping.User)
	 */
	public void deletePojo(BaseMapping pojo) throws ServiceException {
		getDao().delete(pojo);
	}

	/* 
	 * @see com.photography.service.IBaseService#getPojoList(com.photography.dao.query.Pager, com.photography.dao.exp.Expression, com.photography.dao.query.Sort, com.photography.mapping.User)
	 */
	public List<BaseMapping> getPojoList(Class<? extends BaseMapping> entityType,Pager pager, Expression expression, Sort sort) {
		return getDao().getPojoList(entityType, pager, expression, sort,null);
	}
	
	/* 
	 * @see com.photography.service.IBaseService#addUpdateLog(com.photography.mapping.BaseMapping, com.photography.mapping.User)
	 */
	public void addUpdateLog(BaseMapping BaseMapping) {
		// TODO Auto-generated method stub

	}
	
	public List<? extends BaseMapping> queryByHql(String hql, List<?> params){
		return (List<? extends BaseMapping>) getDao().find(hql, params);
	}

}
