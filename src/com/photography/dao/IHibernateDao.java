package com.photography.dao;

import java.io.Serializable;
import java.util.List;

import com.photography.dao.exp.Expression;
import com.photography.dao.query.Pager;
import com.photography.dao.query.Sort;
import com.photography.mapping.BaseMapping;
import com.photography.mapping.ShuadanLog;


/**
 * Hibernate数据交互接口
 * 
 */
public interface IHibernateDao {

	/**
	 * 根据所传入的id得到完整的对象
	 * @param
	 * @return Object
	 */
	public <T> T loadById(Class<T> entityType, Serializable id);

	/**
	 * 根据所传入的id得到完整的对象
	 * @param
	 * @return Object
	 */
	public <T> T getById(Class<T> entityType, Serializable id);

	public <T> T getOneByQuery(Class<T> entityType, Expression expression);

	public <T> List<T> getByQuery(Class<T> entityType, Expression expression);
	
	public <T> List<T> getByQuery(Class<?> entityType, Expression expression, Class<T> caseCade);

	public <T> List<T> getByQuery(Class<T> entityType, Expression expression,Sort sort);
	
	public <T> List<T> getByQueryNoCache(Class<T> entityType, Expression expression,Sort sort);

	public <T> List<T> getByQuery(Class<T> entityType, Pager pager, Expression expression, Sort sort);
	
	public <T> int getCountByQuery(Class<T> entityType, Expression expression);
	
	/**
	 * 保存一个对象
	 * @param
	 */
	public void add(Object obj);

	/**
	 * 根据是否含有主键保存或更新一个对象
	 * @param
	 */
	public void saveOrUpdate(Object obj);

	/**
	 * merge
	 * @param
	 */
	public void merge(Object obj);

	/**
	 * 删除一个对象
	 * @param
	 */
	public void delete(Class<?> entityType, Serializable id);

	/**
	 * 删除一个对象
	 * @param
	 */
	public void delete(Object t);

	/**
	 * 删除多个对象
	 * @param
	 */
	public void deletes(Class<?> entityType, String id);

	/**
	 * 把session入库
	 */
	public void flushSession();

	public void initialize(Object obj);
	
	/**
	 * 获取属性列名
	 * @param clz 类
	 * @param propertyName 属性名
	 * @return 列名
	 * @author 汪晗
	 */
	public String getColumnName(Class<?> clz,String propertyName);
	
	/**
	 * 将对象从session中移除
	 * @param obj
	 * @author Zhao Zhihong
	 */
	public void evict(Object obj);
	
	/**
	 * 更新SessionFactory
	 * @param sessionFactory
	 */
//	public void refreshSessionFactory(final SessionFactory sessionFactory);
	
	/**
	 * 获取一个session，程序自己管理
	 * @return
	 */
//	public Session openSession();
	
	public List<?> find(String hql);

	public List<?> find(String hql, List<?> params);

	public List<?> find(String hql, List<?> params, int pageNo, int pageSize);
	
	/**
	 * 对分页查询的支持，根据条件查询业务的列表，并通过参数控制是否使用默认的数据权限
	 * @param entityType
	 * @param pager
	 * @param expression
	 * @param sort
	 * @param user
	 * @return 查询结果
	 */
	public List<BaseMapping> getPojoList(Class<?> entityType,Pager pager, Expression expression, Sort sort,ShuadanLog user);
}
