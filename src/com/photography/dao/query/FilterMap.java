package com.photography.dao.query;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.photography.dao.exp.Expression;
import com.photography.exception.SystemException;


/**
 * 数据查询条件
 */
public class FilterMap extends LinkedHashMap<String, FilterItem> {

	/**
	 * @function
	 * @param
	 *
	 * @return
	 * @exception
	 *
	 * @history
	 *
	 */
	private static final long serialVersionUID = -5882860884875393358L;
	//final Logger logger = Logger.getLogger(getClass());
	private String linkOperate = null;// may be a Reverse Polish Notation
	// 高级检索条件组合表达式
	private String searchLinkOperate = null;

	public static final String AND = " AND ";
	public interface ProcessItemListener extends Serializable {
		public void onProcessItem(FilterItem item);
	}

	private ProcessItemListener processItemListener;

	public void setProcessItemListener(ProcessItemListener processItemListener) {
		this.processItemListener = processItemListener;
	}

	public ProcessItemListener getProcessItemListener() {
		return processItemListener;
	}

	public Expression toExpression() {
		if (!isEmpty()) {
			final QueryCriterions queryCriterions = new QueryCriterions();
			if (linkOperate == null || linkOperate.equals("")) {
				for (Object element : values()) {
					FilterItem condition = (FilterItem) element;
					if (processItemListener != null) {
						processItemListener.onProcessItem(condition);
					}
					Expression expression = condition.getExpression();
					if (expression != null) {
						queryCriterions.and(expression);
					}
				}
			} else {
				QbcExpParser parser = new QbcExpParser(this);
				try {
					queryCriterions.and(parser.parse());
				} catch (Exception e) {
					//logger.error("[FilterMap][toExpression]parse expression "+linkOperate+" error", e);
				}
				for(String key : this.keySet()) {
					if(linkOperate.indexOf(key)==-1) {
						FilterItem condition = (FilterItem) this.get(key);
						if (processItemListener != null) {
							processItemListener.onProcessItem(condition);
						}
						Expression expression = condition.getExpression();
						if (expression != null) {
							queryCriterions.and(expression);
						}
					}
				}
				return queryCriterions.toExpression();
			}
			return queryCriterions.toExpression();
		}
		return null;
	}

	public String getLinkOperate() {
		return linkOperate;
	}

	public void setLinkOperate(String linkOperators) {
		this.linkOperate = linkOperators;
	}

	public String getSearchLinkOperate() {
		return searchLinkOperate;
	}

	public void setSearchLinkOperate(String searchLinkOperate) {
		this.searchLinkOperate = searchLinkOperate;
	}
	
	/**
	 * 获取高级查询的条件列表
	 * @return 条件列表
	 */
	public List<FilterItem> getSearchExpItemList() {
		QbcExpParser parser = new QbcExpParser(this);
		try {
			return parser.getExpItemList(this.searchLinkOperate);
		}	catch (SystemException e) {
			//logger.error("[FilterMap][toExpression]parse expression "+linkOperate+" error", e);
		}
		return null;
	}

}
