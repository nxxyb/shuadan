package com.photography.dao.query;

import java.util.ArrayList;
import java.util.List;

import com.photography.dao.exp.Condition;
import com.photography.dao.exp.Expression;
import com.photography.exception.ErrorCode;
import com.photography.exception.SystemException;

/**
 * QBC查询表达式解析器
 * 解析页面传入的查询条件组合表达式
 * @author 汪晗 
 * @since 2011-11-23 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class QbcExpParser {

	private static final char OR = '|';
	private static final char AND = '&';
	private static final char LEFT_PARENTHESIS = '(';
	private static final char RIGHT_PARENTHESIS = ')';

	private final char[] OPERATORS = { AND, OR, LEFT_PARENTHESIS, RIGHT_PARENTHESIS };

	private FilterMap filterMap;

	public QbcExpParser(FilterMap filterMap) {
		this.filterMap = filterMap;
	}

	public Expression parse() throws Exception {
		return travel(filterMap.getLinkOperate());
	}

	/**
	 * 处理查询表达式，允许包括（）
	 * 
	 * @param exp 表达式
	 * @return 返回处理后的结果
	 * @throws SystemException
	 * @author 汪晗
	 */
	private Expression travel(String exp) throws Exception {
		if(exp==null || exp.length()==0) {
			return null;
		}
		Expression expression = null;
		if(exp.indexOf(LEFT_PARENTHESIS)>-1 && exp.indexOf(RIGHT_PARENTHESIS)>-1) {
			int cursor = exp.indexOf(LEFT_PARENTHESIS);
			if(cursor>0) {
				if(isDelim(exp.charAt(cursor-1))) {
					if(cursor==1) {
						throw new SystemException(ErrorCode.PARSE_QUERY_EXP_ERROR);
					}
					else {
						if(exp.charAt(cursor-1)==AND) {
							expression = Condition.and(travelSimplePrior(exp.substring(0, cursor-1)), travel(exp.substring(cursor)));
						}
						else if(exp.charAt(cursor-1)==OR) {
							expression = Condition.or(travelSimplePrior(exp.substring(0, cursor-1)), travel(exp.substring(cursor)));
						}
					}
				}
				else {
					throw new SystemException(ErrorCode.PARSE_QUERY_EXP_ERROR);
				}
			}
			else {
				int rightCursor = getRightParenthesisIndex(exp);
				if(rightCursor==exp.length()-1) {
					expression = travel(exp.substring(1, rightCursor));
				}
				else {
					if(isDelim(exp.charAt(rightCursor+1))) {
						if(rightCursor==exp.length()-2) {
							expression = travel(exp.substring(1, rightCursor));
						}
						else {
							if(exp.charAt(rightCursor+1)==AND) {
								expression = Condition.and(travel(exp.substring(1, rightCursor)), travel(exp.substring(rightCursor+2)));
							}
							else if(exp.charAt(rightCursor+1)==OR) {
								expression = Condition.or(travel(exp.substring(1, rightCursor)), travel(exp.substring(rightCursor+2)));
							}
						}
					}
					else {
						throw new SystemException(ErrorCode.PARSE_QUERY_EXP_ERROR);
					}
				}
			}
		}
		else if(exp.indexOf(LEFT_PARENTHESIS) != exp.indexOf(RIGHT_PARENTHESIS)) {
			throw new SystemException(ErrorCode.PARSE_QUERY_EXP_ERROR);
		}
		else {
			expression = travelSimplePrior(exp);
		}
		return expression;
	}

	/**
	 * 找到与第一个左括号相匹配的右括号
	 * 
	 * @param exp 表达式
	 * @return
	 * @throws SystemException
	 * @author 汪晗
	 */
	private int getRightParenthesisIndex(String exp) throws SystemException {
		int cursor = 0;
		int leftNumber = 1;
		int rightNumber = 0;
		for(int i=1;i<exp.length();i++) {
			char c = exp.charAt(i);
			if(c==LEFT_PARENTHESIS) {
				leftNumber++;
			}
			else if(c==RIGHT_PARENTHESIS) {
				rightNumber++;
			}
			if(leftNumber==rightNumber) {
				return i;
			}
		}
		return cursor;
	}

	/**
	 * 
	 * @function 处理简单的查询表达式（没有（）嵌套）
	 * @param exp 表达式
	 *
	 * @return 返回处理后的结果
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private Expression travelSimple(String exp) throws SystemException {
		if(exp==null || exp.length()==0) {
			return null;
		}
		Expression expression = null;
		int cursor = 0;
		while(!isDelim(exp.charAt(cursor)) && cursor < exp.length()-1) {
			cursor++;
		}
		if(cursor==0) {
			expression = travelSimple(exp.substring(1));
		}
		else if(cursor==exp.length()-1) {
			expression = getItemExpression(exp);
		}
		else {
			if(exp.charAt(cursor)==AND) {
				expression = Condition.and(getItemExpression(exp.substring(0, cursor)), travelSimple(exp.substring(cursor+1)));
			}
			else if(exp.charAt(cursor)==OR) {
				expression = Condition.or(getItemExpression(exp.substring(0, cursor)), travelSimple(exp.substring(cursor+1)));
			}
		}
		return expression;
	}
	
	/**
	 * 
	 * @function 处理简单的查询表达式（没有（）嵌套），&优先级更高
	 * @param exp 表达式
	 *
	 * @return 返回处理后的结果
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private Expression travelSimplePrior(String exp) throws SystemException {
		if(exp==null || exp.length()==0) {
			return null;
		}
		Expression expression = null;
		int cursor = exp.indexOf(OR);
		if(cursor == -1) {
			expression = travelSimple(exp);
		} else {
			expression = Condition.or(travelSimple(exp.substring(0, cursor)), travelSimplePrior(exp.substring(cursor+1)));
		}
		return expression;
	}
	
	/**
	 * 
	 * @function 判断是否为分隔符
	 * @param a 字符
	 *
	 * @return 判断结果
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private boolean isDelim(char a) {
		if (String.valueOf(OPERATORS).indexOf(a) != -1) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * @function 获得当前独立项的表达式
	 * @param item 分离出的独立的查询字符
	 *
	 * @return 表达式
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private Expression getItemExpression(String item) {
		FilterItem condition = filterMap.get(item);
		if(condition==null) {
			return null;
		}
		return condition.getExpression();
	}
	
	/**
	 * 获取指定条件组合表达式中的所有查询条件列表
	 * @return
	 * @throws SystemException
	 * @author 汪晗
	 */
	public List<FilterItem> getExpItemList(String exp) throws SystemException {
		List<FilterItem> itemList = new ArrayList<FilterItem>();
		if(exp.indexOf(LEFT_PARENTHESIS)>-1 && exp.indexOf(RIGHT_PARENTHESIS)>-1) {
			int cursor = exp.indexOf(LEFT_PARENTHESIS);
			if(cursor>0) {
				if(isDelim(exp.charAt(cursor-1))) {
					if(cursor==1) {
						throw new SystemException(ErrorCode.PARSE_QUERY_EXP_ERROR);
					}
					else {
						List<FilterItem> rightItemList = getExpItemList(exp.substring(cursor));
						if(exp.charAt(cursor-1)==AND) {
							if(rightItemList!=null && rightItemList.size()>0) {
								rightItemList.get(0).setRelation("&");
							}
						} else if(exp.charAt(cursor-1)==OR) {
							if(rightItemList!=null && rightItemList.size()>0) {
								rightItemList.get(0).setRelation("|");
							}
						}
						itemList.addAll(getSimpleItemList(exp.substring(0, cursor-1)));
						itemList.addAll(rightItemList);
					}
				}
				else {
					throw new SystemException(ErrorCode.PARSE_QUERY_EXP_ERROR);
				}
			}
			else {
				int rightCursor = getRightParenthesisIndex(exp);
				if(rightCursor==exp.length()-1) {
					itemList.addAll(getExpItemList(exp.substring(1, rightCursor)));
				}
				else {
					if(isDelim(exp.charAt(rightCursor+1))) {
						if(rightCursor==exp.length()-2) {
							itemList.addAll(getExpItemList(exp.substring(1, rightCursor)));
						}
						else {
							List<FilterItem> rightItemList = getExpItemList(exp.substring(rightCursor+2));
							if(exp.charAt(rightCursor+1)==AND) {
								if(rightItemList!=null && rightItemList.size()>0) {
									rightItemList.get(0).setRelation("&");
								}
							}
							else if(exp.charAt(rightCursor+1)==OR) {
								if(rightItemList!=null && rightItemList.size()>0) {
									rightItemList.get(0).setRelation("|");
								}
							}
							itemList.addAll(getExpItemList(exp.substring(1, rightCursor)));
							itemList.addAll(rightItemList);
						}
					}
					else {
						throw new SystemException(ErrorCode.PARSE_QUERY_EXP_ERROR);
					}
				}
			}
		}
		else if(exp.indexOf(LEFT_PARENTHESIS) != exp.indexOf(RIGHT_PARENTHESIS)) {
			throw new SystemException(ErrorCode.PARSE_QUERY_EXP_ERROR);
		}
		else {
			itemList.addAll(getSimpleItemList(exp));
		}
		return itemList;
	}
	
	/**
	 * 获取简单条件组合表达式中的所有查询条件列表
	 * @return
	 * @throws SystemException
	 * @author 汪晗
	 */
	private List<FilterItem> getSimpleItemList(String exp) {
		List<FilterItem> itemList = new ArrayList<FilterItem>();
		int cursor = 0;
		while(!isDelim(exp.charAt(cursor)) && cursor < exp.length()-1) {
			cursor++;
		}
		if(cursor==0) {
			itemList.addAll(getSimpleItemList(exp.substring(1)));
		}
		else if(cursor==exp.length()-1) {
			FilterItem item = filterMap.get(exp);
			item.setKey(exp);
			itemList.add(item);
		}
		else {
			List<FilterItem> rightItemList = getSimpleItemList(exp.substring(cursor+1));
			if(exp.charAt(cursor)==AND) {
				if(rightItemList!=null && rightItemList.size()>0) {
					rightItemList.get(0).setRelation("&");
				}
			}
			else if(exp.charAt(cursor)==OR) {
				if(rightItemList!=null && rightItemList.size()>0) {
					rightItemList.get(0).setRelation("|");
				}
			}
			FilterItem item = filterMap.get(exp.substring(0, cursor));
			item.setKey(exp.substring(0, cursor));
			itemList.add(item);
			itemList.addAll(rightItemList);
		}
		return itemList;
	}


}
