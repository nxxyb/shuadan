package com.photography.dao;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.CallableStatementCallback;

/**
 * JDBC数据交互接口
 *
 * @since 2011-6-28
 */
public interface IJdbcDao {

	/**
	 * 通过sql查询结果集
	 * @param sql 查询的sql语句
	 * @return 查询结果(List<Map>)
	 */
	public List<Map<String, Object>> query(String sql);

	/**
	 * 通过sql查询结果集，允许预处理查询，推荐使用此种方式
	 * @param sql 带预处理的查询语句
	 * @param args 参数
	 * @return 查询结果(List<Map>)
	 */
	public List<Map<String, Object>> query(String sql,Object[] args) ;
	
	/**
	 * 通过sql查询唯一结果，允许预处理查询，推荐使用此种方式
	 * @param sql 带预处理的查询语句
	 * @param args 参数
	 * @return 查询结果(<Map>)
	 */
	public Map<String, Object> querySingleResult(String sql,Object[] args) ;

	/**
	 * 执行更新操作
	 * @param sql 更新语句
	 * @return
	 */
	public int update(String sql);

	/**
	 * 执行预处理更新操作
	 * @param sql 更新语句
	 * @param args 预处理参数
	 * @return
	 */
	public int update(String sql,Object[] args);

	/**
	 * 执行sql语句，提供更新以及删除功能
	 * @param sql 更新语句
	 */
	public void execute(String sql) ;

	/**
	 * 批量执行sql语句（小批量）
	 * @param sqls sql语句数组
	 */
	public void batchUpdate(String[] sqls);

	/**
	 * 调用存储过程
	 * @param callSql 调用存储过程语句
	 * @param callBack 回调函数
	 * @return 调用结果
	 */
	public <T> T callProcedure(String callSql,CallableStatementCallback<T> callBack) ;

}
