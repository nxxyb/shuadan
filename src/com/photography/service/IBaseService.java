package com.photography.service;

import java.util.List;

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
public interface IBaseService {
	
	/**
	 * 通过主键获取固化业务pojo
	 * @param id 固化pojo主键
	 * @return 业务pojo
	 */
	public BaseMapping loadPojo(Class<? extends BaseMapping> entityType,String id);
	
	/**
	 * 通过表达式获取pojo列表
	 * @param expression 表达式
	 * @return pojo列表
	 */
	public List<? extends BaseMapping> loadPojoByExpression(Class<? extends BaseMapping> entityType,Expression expression,Sort sort);
	
	/**
	 * 保存当前的pojo,需要先判断是否新建，新建的逻辑需要单独处理
	 * 
	 * @param pojo
	 * @param user 
	 */
	public void savePojo(BaseMapping pojo) throws ServiceException;
	
	
	/**
	 * 通过主键，删除固化pojo
	 * @param id
	 * @return 当出现错误时，返回错误消息
	 */
	public void deletePojo(BaseMapping pojo) throws ServiceException;
	
	/**
	 * 对分页查询的支持，根据条件查询业务的列表
	 * @param pager 分页
	 * @param expression 查询条件
	 * @param sort 排序
	 * @param user 用户
	 * @return
	 */
	public List<BaseMapping> getPojoList(Class<? extends BaseMapping> entityType,Pager pager, Expression expression, Sort sort);
	
	/**
	 * 合计列表，支持分页以及查询
	 * @param pager
	 * @param expression
	 * @param sort
	 * @param user
	 * @return 合计列表数据
	 */
//	public List<BaseMapping> getSumList(Pager pager, Expression expression, Sort sort,User user);
	
	/**
	 * 记录更新字段的操作日志
	 * @param function
	 * @param BaseMapping
	 * @param user
	 */
	public void addUpdateLog(BaseMapping BaseMapping);
	
	/**
	 * 记录典型的业务操作日志
	 * @param businessProcess
	 * @param businessInstance
	 * @param BasicFunction
	 * @param user
	 */
//	public void addSystemLog(BusinessProcess businessProcess,BusinessInstance businessInstance,BasicFunction basicFunction,User user);
	
	/**
	 * 允许自定义操作日志
	 * @param businessProcess
	 * @param operateType
	 * @param severity
	 * @param logMessage
	 * @param params
	 * @param user
	 */
//	public void addSystemLog(BusinessProcess businessProcess,String operateType,Integer severity,String logMessage,String[] params,User user);
	
	/**
	 * 执行hql
	 */
	public List<? extends BaseMapping> queryByHql(String hql, List<?> params);

}
