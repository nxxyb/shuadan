package com.photography.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.persister.entity.AbstractEntityPersister;
import org.hibernate.persister.entity.SingleTableEntityPersister;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.photography.dao.exp.AliasHelper;
import com.photography.dao.exp.Expression;
import com.photography.dao.exp.ExpressionHelper;
import com.photography.dao.exp.TypeAdaptHelper;
import com.photography.dao.query.Pager;
import com.photography.dao.query.QueryConstants;
import com.photography.dao.query.Sort;
import com.photography.dao.query.SortItem;
import com.photography.mapping.BaseMapping;
import com.photography.mapping.ShuadanLog;


/**
 * 数据处理基类
 */
@Repository("hibernateDao")
public class HibernateDaoImpl implements IHibernateDao {

	private final static Logger log = Logger.getLogger(HibernateDaoImpl.class);
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	private Session getHibernateTemplate(){
		return sessionFactory.getCurrentSession();
	}

	public <T> T loadById(Class<T> entityType, Serializable id) {
		return (T) getHibernateTemplate().load(entityType, id);
	}

	public <T> T getById(Class<T> entityType, Serializable id) {
		return (T) getHibernateTemplate().get(entityType, id);
	}

	@SuppressWarnings("unchecked")
	public <T> T getOneByQuery(Class<T> entityType, Expression expression) {
		Criteria criteria = getHibernateTemplate().createCriteria(entityType);
		if (expression != null) {
			adaptPropertyValueType(expression,entityType);
			creatAlias(criteria, expression);
			ExpressionHelper.init(this);
			Criterion criterion = ExpressionHelper.parseExpression(entityType,expression);
			if (criterion != null) {
				criteria.add(criterion);
			}
		}
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return (T)criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getByQuery(Class<T> entityType, Expression expression) {
		Criteria criteria = getHibernateTemplate().createCriteria(entityType);
		if (expression != null) {
			adaptPropertyValueType(expression,entityType);
			creatAlias(criteria, expression);
			ExpressionHelper.init(this);
			Criterion criterion = ExpressionHelper.parseExpression(entityType,expression);
			if (criterion != null) {
				criteria.add(criterion);
			}
		}
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return criteria.list();
	}
	
	public <T> int getCountByQuery(Class<T> entityType, Expression expression) {
		Criteria criteria = getHibernateTemplate().createCriteria(entityType);
		if (expression != null) {
			adaptPropertyValueType(expression,entityType);
			creatAlias(criteria, expression);
			ExpressionHelper.init(this);
			Criterion criterion = ExpressionHelper.parseExpression(entityType,expression);
			if (criterion != null) {
				criteria.add(criterion);
			}
		}
		List<Long> countList = criteria.setProjection(Projections.rowCount()).list();
		int count = 0;
		for (Long c : countList) {
			count += c.intValue();
		}
		return count;
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> getByQuery(Class<?> entityType, Expression expression, Class<T> caseCade) {
		List<?> dataset = getByQuery(entityType, expression);
		
		List<T> results = new ArrayList<T>();
		for (Object obj : dataset){
			results.add((T) obj);
		}
		
		return results;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getByQuery(Class<T> entityType, Expression expression, Sort sort) {
		Criteria criteria = getHibernateTemplate().createCriteria(entityType);
		Map<String, String> aliasMap = new HashMap<String, String>();
		if (expression != null) {
			adaptPropertyValueType(expression,entityType);
			aliasMap.putAll(creatAlias(criteria, expression));
			ExpressionHelper.init(this);
			Criterion criterion = ExpressionHelper.parseExpression(entityType,expression);
			if (criterion != null) {
				criteria.add(criterion);
			}
		}
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		if (sort != null && !sort.isEmpty()) {
			creatAlias(criteria,sort,aliasMap);
			createOrder(criteria, sort);
		}
		return criteria.list();
	}
	
	/**
	 * 获取查询结果并不进行缓存
	 * @param entityType
	 * @param expression
	 * @param sort
	 * @return
	 */
	public <T> List<T> getByQueryNoCache(Class<T> entityType, Expression expression, Sort sort) {
		Criteria criteria = getHibernateTemplate().createCriteria(entityType);
		if (expression != null) {
			adaptPropertyValueType(expression,entityType);
			creatAlias(criteria, expression);
			ExpressionHelper.init(this);
			Criterion criterion = ExpressionHelper.parseExpression(entityType,expression);
			if (criterion != null) {
				criteria.add(criterion);
			}
		}
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		if (sort != null && !sort.isEmpty()) {
			createOrder(criteria, sort);
		}
		criteria.setCacheable(false);
		return criteria.list();
	}
	
	/**
	 * 添加默认的id排序
	 * @param <T>
	 * @param entityType
	 * @param sort
	 * @return
	 */
	protected <T> Sort addDefaultIdSort(Class<T> entityType, Sort sort) {
		if(sort==null) {
			sort = new Sort();
		}
		sort.addSort(getIdName(entityType));
		return sort;
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getByQuery(Class<T> entityType, Pager pager, Expression expression, Sort sort) {
		Criteria criteria = getHibernateTemplate().createCriteria(entityType);
        Map<String, String> aliasMap = new HashMap<String, String>();
        if (expression != null) {
			adaptPropertyValueType(expression,entityType);
            aliasMap.putAll(creatAlias(criteria, expression));
			ExpressionHelper.init(this);
			Criterion criterion = ExpressionHelper.parseExpression(entityType,expression);
			if (criterion != null) {
				criteria.add(criterion);
			}
		}
		queryCount(pager, criteria);
		criteria.setProjection(null);
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		sort = addDefaultIdSort(entityType, sort);
		if (sort != null && !sort.isEmpty()) {
            creatAlias(criteria,sort,aliasMap);
			createOrder(criteria, sort);
		}
		setPagerNumber(pager, criteria);
		return criteria.list();
	}
	
	/**
	 * 
	 * @function 查询记录总数
	 * @param pager 分页数据
	 * @param criteria 查询从句
	 *
	 * @return
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	@SuppressWarnings("unchecked")
	protected void queryCount(Pager pager, Criteria criteria) {
		if (pager != null) {
			List<Long> countList = criteria.setProjection(Projections.rowCount()).list();
			int count = 0;
			for (Long c : countList) {
				count += c.intValue();
			}
//			int allPage = count/pager.getPageSize();
			pager.setTotalCount(count+pager.getOffset());
			if(pager.getCurrentPage()>pager.getTotalPage()) {
				pager.setCurrentPage(pager.getTotalPage());
			}
		}
	}
	
	/**
	 * 
	 * @function 设置查询数据的起始位置和结束位置
	 * @param pager 分页数据
	 * @param criteria 查询从句
	 *
	 * @return
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	protected void setPagerNumber(Pager pager, Criteria criteria) {
		if (pager != null) {
			if (pager.getTotalCount() <= pager.getFromRowIndex()) {
				pager.setCurrentPage(1);
			}
			if(pager.getCurrentPage()==1) {
				criteria.setFirstResult(pager.getFromRowIndex());
				criteria.setMaxResults(pager.getPageSize()-pager.getOffset());
			} else {
				criteria.setFirstResult(pager.getFromRowIndex()-pager.getOffset());
				criteria.setMaxResults(pager.getPageSize());
			}
		}
	}

	public void add(Object obj) {
		getHibernateTemplate().save(obj);
		getHibernateTemplate().flush();
	}

	public void saveOrUpdate(Object obj) {
		getHibernateTemplate().saveOrUpdate(obj);
	}

	public void merge(Object obj) {
		try {
			getHibernateTemplate().persist(obj);
		} catch (Exception e) {
		}
	
		getHibernateTemplate().merge(obj);
		getHibernateTemplate().flush();
	}

	public void delete(Class<?> entityType, Serializable id) {
		Object obj = loadById(entityType, id);
		getHibernateTemplate().delete(obj);
	}

	public void delete(Object obj) {
		getHibernateTemplate().delete(obj);
	}

	public void deletes(Class<?> entityType, String ids) {
		if (ids == null || ids.length() == 0) {
			return;
		}
		StringBuilder builder = new StringBuilder();
		builder.append("delete from " + entityType.getName());
		builder.append(" where ");
		builder.append(getIDName(entityType));
		builder.append(" in (");
		builder.append(ids + ")");
		getHibernateTemplate().createQuery(builder.toString()).executeUpdate();
	}

	public void flushSession() {
		getHibernateTemplate().flush();
	}

	public void initialize(Object obj) {
//		getHibernateTemplate().initialize(obj);
	}

	/**
	 * 得到主键的名字
	 * @return String
	 * @author dormin
	 */
	protected String getIDName(Class<?> entityType) {
		return getHibernateTemplate().getSessionFactory().getClassMetadata(entityType).getIdentifierPropertyName();
	}

	protected void createOrder(Criteria criteria, Sort sort) {
		for (SortItem item : sort.getSortItemList()) {
			if(item.getFieldName()!=null && item.getFieldName().length()>0) {
				String order = item.getOrder();
				if (QueryConstants.ASC.equalsIgnoreCase(order)) {
					criteria.addOrder(Order.asc(item.getFieldName()));
				} else if (QueryConstants.DESC.equalsIgnoreCase(order)) {
					criteria.addOrder(Order.desc(item.getFieldName()));
				} else {
					criteria.addOrder(Order.asc(item.getFieldName()));
				}
			}
		}
	}
	
	/**
	 * 复制排序
	 * @param sort
	 * @return
	 * @author 汪晗
	 */
	protected Sort copySort(Sort sort) {
		Sort newSort = new Sort();
		newSort.setSortItemList(new ArrayList<SortItem>());
		if(sort!=null && sort.getSortItemList()!=null) {
			for(SortItem item : sort.getSortItemList()) {
				newSort.getSortItemList().add(new SortItem(item.getFieldName(),item.getOrder(),item.getFieldId()));
			}
		}
		return newSort;
	}

	/**
	 * 处理级联查询
	 * @param
	 * 
	 * @author 汪晗
	 */
	protected Map<String, String> creatAlias(Criteria criteria, Expression expression) {
		Map<String, String> aliasMap = AliasHelper.getAliasMap(expression);
		for (String key : aliasMap.keySet()) {
			criteria.createAlias(key, aliasMap.get(key),Criteria.LEFT_JOIN);
		}
		return aliasMap;
	}
	
	/**
	 * 处理级联查询，对指定忽视的别名不进行级联
	 * @param criteria
	 * @param expression
	 * @param ignoreAliasMap 指定忽视的别名集合
	 * @author 汪晗
	 */
	protected Map<String, String> creatAlias(Criteria criteria, Expression expression,Map ignoreAliasMap) {
		Map<String, String> aliasMap = AliasHelper.getAliasMap(expression);
		for (String key : aliasMap.keySet()) {
			if(ignoreAliasMap.get(key)==null) {
				criteria.createAlias(key, aliasMap.get(key),Criteria.LEFT_JOIN);
			}
		}
		return aliasMap;
	}
	
	/**
	 * 处理级联排序
	 * @param criteria
	 * @param sort
	 * @author 汪晗
	 */
	protected Map<String, String> creatAlias(Criteria criteria, Sort sort) {
		Map<String, String> aliasMap = AliasHelper.getAliasMap(sort);
		for (String key : aliasMap.keySet()) {
			criteria.createAlias(key, aliasMap.get(key),Criteria.LEFT_JOIN);
		}
		return aliasMap;
	}
	
	/**
	 * 处理级联排序
	 * @param criteria
	 * @param sort
	 * @param ignoreAliasMap
	 * @author 汪晗
	 */
	protected Map<String, String> creatAlias(Criteria criteria, Sort sort,Map ignoreAliasMap) {
		Map<String, String> aliasMap = AliasHelper.getAliasMap(sort);
		for (String key : aliasMap.keySet()) {
			if(ignoreAliasMap.get(key)==null) {
				criteria.createAlias(key, aliasMap.get(key),Criteria.LEFT_JOIN);
			}
		}
		return aliasMap;
	}

	/**
	 * 对于某些特殊类型的参数，需要将查询值强制进行类型转换
	 * @param expression 表达式
	 * @return
	 * @author 汪晗
	 */
	protected void adaptPropertyValueType(Expression expression,Class<?> entityClass) {
		try {
			TypeAdaptHelper.typeAdapt(expression, entityClass);
		} catch (SecurityException e) {
			log.error("[BaseDAOImpl][adaptPropertyValueType] adapt property type error", e);
		} catch (NoSuchFieldException e) {
			log.error("[BaseDAOImpl][adaptPropertyValueType] adapt property type error:field not found", e);
		} catch (Exception e) {
			log.error("[BaseDAOImpl][adaptPropertyValueType] adapt property type error", e);
		}
	}

	
	public String getColumnName(Class<?> clz, String propertyName) {
		List<String> resultList = new ArrayList<String>();
		SessionFactory factory = getHibernateTemplate().getSessionFactory();
		AbstractEntityPersister classMetadata = (SingleTableEntityPersister) factory.getClassMetadata(clz);
		Type type = null;
		try {
			type = classMetadata.getClassMetadata().getPropertyType(propertyName);
		} catch(Exception e) {
			log.error("Could not get column name from "+clz.getSimpleName()+" by "+propertyName+".");
		}
		if(type==null) {
			return null;
		}
		boolean isCollection = type.isCollectionType();
		if (!isCollection) {
			for (String columnName : classMetadata.getPropertyColumnNames(propertyName)) {
				resultList.add(columnName);
			}
		}
		if (resultList != null && resultList.size() >= 1) {
			return resultList.get(0);
		}
		return null;
	}
	
	/**
	 * 获取id的属性名
	 * @param clz
	 * @return id的属性名
	 * @author 汪晗
	 */
	protected String getIdName(Class<?> clz) {
		SessionFactory factory = getHibernateTemplate().getSessionFactory();
		AbstractEntityPersister classMetadata = (AbstractEntityPersister) factory.getClassMetadata(clz);
		return classMetadata.getIdentifierPropertyName();
	}
	
	public void evict(Object obj) {
		Session session = getHibernateTemplate().getSessionFactory().getCurrentSession();
		session.evict(obj);
	}

	public List<?> find(String hql) {
		return this.getHibernateTemplate().createQuery(hql).list();
	}

	public List<?> find(final String hql, final List<?> params, final boolean paging, final int pageNo, final int pageSize) {
		Query query = getHibernateTemplate().createQuery(hql);
		if (params != null){
			Iterator<? extends Object> it = params.iterator();
			for (int i = 0; it.hasNext(); i++) {
				query.setParameter(i, it.next());
			}
		}
		if (paging && pageSize > 0) {
			int startRow = (pageNo - 1) * pageSize;
			query.setFirstResult(startRow);
			query.setMaxResults(pageSize);
		}
		return query.list();
	}
	
	public List<?> find(String hql, List<?> params, int pageNo, int pageSize) {
		return find(hql, params, true, pageNo, pageSize);
	}
	
	public List<?> find(String hql, List<?> params) {
		return find(hql, params, false, 0, 0);
	}
	
	/**
	 * 对分页查询的支持，根据条件查询业务的列表，并通过参数控制是否使用默认的数据权限
	 * @param entityType
	 * @param pager
	 * @param expression
	 * @param sort
	 * @param user
	 * @return 查询结果
	 */
	public List<BaseMapping> getPojoList(Class<?> entityType,Pager pager, Expression expression, Sort sort,ShuadanLog user){
		Criteria criteria = getHibernateTemplate().createCriteria(entityType);
		Map<String, String> aliasMap = new HashMap<String, String>();
		if(expression!=null) {
			adaptPropertyValueType(expression,entityType);
			aliasMap = creatAlias(criteria,expression);
			ExpressionHelper.init(this);
			Criterion criterion = ExpressionHelper.parseExpression(entityType,expression);
			if (criterion != null) {
				criteria.add(criterion);
			}
		}
		
		if(pager!=null) {
			queryCount(pager, criteria);
			criteria.setProjection(null);
			setPagerNumber(pager, criteria);
		}
		
		sort = copySort(sort);
		sort = addDefaultIdSort(entityType, sort);
		if (sort != null && !sort.isEmpty()) {
			creatAlias(criteria, sort, aliasMap);
			createOrder(criteria, sort);
		}
		criteria.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		return criteria.list();
	}
}
