package com.photography.dao.exp;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.StringType;
import org.hibernate.type.Type;

import com.photography.dao.IHibernateDao;
import com.photography.dao.query.FilterItem.QueryType;
import com.photography.utils.ReflectUtil;

/**
 * 表达式解析器
 * 将表达式解析成QBC查询条件
 * @author 汪晗 
 * @since 2011-11-29 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public final class ExpressionHelper {
	
	private static final Logger log = Logger.getLogger(ExpressionHelper.class);
	
	private static IHibernateDao baseDao;
	
	/**
	 * 为ExpressionHelper设置baseDao
	 * @param baseDao
	 * @author 汪晗
	 */
	public static void init(IHibernateDao baseDao) {
		ExpressionHelper.baseDao = baseDao;
	}

	public static Criterion parseExpression(Class entityType,Expression expression) {
		if(expression instanceof BetweenExpression) {
			BetweenExpression exp = (BetweenExpression) expression;
			return toCriterion(entityType,exp);
		} else if(expression instanceof NoExpression) {
			NoExpression exp = (NoExpression) expression;
			return toCriterion(entityType,exp);
		} else if(expression instanceof SimpleExpression) {
			SimpleExpression exp = (SimpleExpression) expression;
			return toCriterion(entityType,exp);
		} else if(expression instanceof SimpleInExpression) {
			SimpleInExpression exp = (SimpleInExpression) expression;
			return toCriterion(entityType,exp);
		} else if(expression instanceof LogicExpression) {
			LogicExpression exp = (LogicExpression) expression;
			return toCriterion(entityType,exp);
		} else if(expression instanceof LogicExpressionExtr) {
			LogicExpressionExtr exp = (LogicExpressionExtr) expression;
			return toCriterion(entityType,exp);
		} else if(expression instanceof CriterionExpression) {
			CriterionExpression exp = (CriterionExpression) expression;
				return toCriterion(exp);
			}
		return null;
	}

	/**
	 * 
	 * @function 转化简单表达式
	 * @param
	 *
	 * @return QBC查询条件
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private static Criterion toCriterion(Class entityType,SimpleExpression expression) {
		if(expression == null ||expression.getPropertyName() == null || expression.getSimpleExpression() == null) {
			return null;
		}
		if (expression.getSimpleExpression().equalsIgnoreCase(QueryType.LT.name())) {
			return Restrictions.lt(expression.getPropertyName(), expression.getObject());
		} else if (expression.getSimpleExpression().equalsIgnoreCase(QueryType.LE.name())) {
			return Restrictions.le(expression.getPropertyName(), expression.getObject());
		} else if (expression.getSimpleExpression().equalsIgnoreCase(QueryType.GT.name())) {
			return Restrictions.gt(expression.getPropertyName(), expression.getObject());
		} else if (expression.getSimpleExpression().equalsIgnoreCase(QueryType.GE.name())) {
			return Restrictions.ge(expression.getPropertyName(), expression.getObject());
		} else if (expression.getSimpleExpression().equalsIgnoreCase(QueryType.EQ.name())) {
			if(expression.getObject() == null) {
				if(expression.getPropertyName().endsWith(".id")) {
					return Restrictions.isNull(expression.getPropertyName().substring(0, expression.getPropertyName().length()-3));
				} else {
					return Restrictions.isNull(expression.getPropertyName());
				}
			}
			else {
				return Restrictions.eq(expression.getPropertyName(), expression.getObject());
			}
		} else if (expression.getSimpleExpression().equalsIgnoreCase(QueryType.NE.name())) {
			if(expression.getObject()==null) {
				return Restrictions.isNotNull(expression.getPropertyName());
			}
			else {
				return Restrictions.ne(expression.getPropertyName(), expression.getObject());
			}
		} else if (expression.getSimpleExpression().equalsIgnoreCase(QueryType.LIKE.name())) {
			if(baseDao==null) {
				return Restrictions.like(expression.getPropertyName(), expression.getObject());
			}
			else {
				return toLikeCriterion(entityType,expression);
			}
		} else if (expression.getSimpleExpression().equalsIgnoreCase(QueryType.ILIKE.name())) {
			return Restrictions.ilike(expression.getPropertyName(), expression.getObject());
		} else {
			return null;
		}
	}
	
	/**
	 * 对like查询进行转义
	 * @param expression
	 * @return
	 * @author 汪晗
	 */
	private static Criterion toLikeCriterion(Class entityType,SimpleExpression expression) {
		if(expression.getObject()==null) {
			return null;
		}
		String value = expression.getObject().toString();
		if(value.length()>2) {
			value = value.substring(1, value.length()-1);
			if(value.indexOf("%")>-1 || value.indexOf("_")>-1) {
				AliasSQLCriterion criterion = new AliasSQLCriterion(getAlias(expression.getPropertyName())+"."+getRealColumnName(entityType,expression.getPropertyName())+" like ? escape'/'",new Object[] {escapeSQLLike(expression.getObject().toString())},new Type[]{StringType.INSTANCE});
				criterion.addAlias(expression.getPropertyName());
				return criterion;
			}
		}
		return Restrictions.like(expression.getPropertyName(), expression.getObject());
	}
	
	/**
	 * 获取列名实际的数据库字段名称
	 * @param entityType 类
	 * @param propertyName 属性名
	 * @return 数据库中的字段名称
	 * @author 汪晗
	 */
	public static String getRealColumnName(Class entityType,String propertyName) {
		String columnName = propertyName;
		try {
			Class directClass = ReflectUtil.getDirectClass(entityType,propertyName);
			String directPropertyName = propertyName;
			if(directPropertyName.indexOf(".")>-1) {
				directPropertyName = directPropertyName.substring(directPropertyName.lastIndexOf(".")+1);
			}
			columnName = baseDao.getColumnName(directClass, directPropertyName);
		} catch (SecurityException e) {
			log.error("ExpressionHelper.getRealColumnName(): SecurityException", e);
		}
		return columnName;
	}
	
	/**
	 * 获取别名
	 * @param propertyName
	 * @return
	 * @author 汪晗
	 */
	private static String getAlias(String propertyName) {
		if(propertyName.indexOf(".")==-1) {
			return "{alias}";
		} else {
			return "{"+propertyName+"}";
		}
	}
	
	/**
	 * 转义字符
	 * @param likeStr
	 * @return 转义之后的字符
	 * @author 汪晗
	 */
	private static String escapeSQLLike(String likeStr) {  
        String str = StringUtils.replace(likeStr,"/","//");
        str = StringUtils.replace(str,"_","/_");
        if(str.startsWith("%") && str.endsWith("%")) {
        	str = "%"+StringUtils.replace(str.substring(1, str.length()-1),"%","/%")+"%";
        } else {
        	str = StringUtils.replace(str,"%","/%");
        }
        //str = StringUtils.replace(str,"?","_");
        //str = StringUtils.replace(str,"*","%");
        return str;  
    }
	

	/**
	 * 
	 * @function 转化Between表达式
	 * @param
	 *
	 * @return QBC查询条件
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private static Criterion toCriterion(Class entityType,BetweenExpression expression) {
		return Restrictions.between(expression.getPropertyName(), expression.getLeftObject(), expression.getRightObject());
	}

	/**
	 * 
	 * @function 转化No表达式
	 * @param
	 *
	 * @return QBC查询条件
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private static Criterion toCriterion(Class entityType,NoExpression expression) {
		return Restrictions.not(parseExpression(entityType,expression.getExpression()));
	}

	/**
	 * 
	 * @function 转化In表达式
	 * @param
	 *
	 * @return QBC查询条件
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private static Criterion toCriterion(Class entityType,SimpleInExpression expression) {
		return Restrictions.in(expression.getPropertyName(), expression.getList());
	}

	/**
	 * 
	 * @function 转化Logic表达式
	 * @param
	 *
	 * @return QBC查询条件
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private static Criterion toCriterion(Class entityType,LogicExpression expression) {
		Criterion leftCriterion = parseExpression(entityType,expression.getLeftExpression());
		Criterion rightCriterion = parseExpression(entityType,expression.getRightExpression());
		if(leftCriterion != null && rightCriterion!=null ) {
			if(Condition.AND.equalsIgnoreCase(expression.getLogic())) {
				return Restrictions.and(leftCriterion, rightCriterion);
			}
			else if(Condition.OR.equalsIgnoreCase(expression.getLogic())) {
				return Restrictions.or(leftCriterion, rightCriterion);
			}
		}
		else if(leftCriterion != null){
			//return leftCriterion;
            if(Condition.AND.equalsIgnoreCase(expression.getLogic())) {
                return leftCriterion;
            }
            else if(Condition.OR.equalsIgnoreCase(expression.getLogic())) {
                return null;
            }
		}
		else if(rightCriterion != null){
			//return rightCriterion;
            if(Condition.AND.equalsIgnoreCase(expression.getLogic())) {
                return rightCriterion;
            }
            else if(Condition.OR.equalsIgnoreCase(expression.getLogic())) {
                return null;
            }
		}
		return null;
	}

	/**
	 * 
	 * @function 转化复杂的Logic表达式
	 * @param
	 *
	 * @return QBC查询条件
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private static Criterion toCriterion(Class entityType,LogicExpressionExtr expression) {
		if (expression.getExpressions() == null || expression.getExpressions().size() == 0) {
			return null;
		}
		Criterion criterion = null;
		for(Expression expr : expression.getExpressions()) {
			if(Condition.AND.equalsIgnoreCase(expression.getLogic())) {
				if(criterion==null) {
					criterion = parseExpression(entityType, expr);
				}
				else {
					criterion = Restrictions.and(criterion, parseExpression(entityType, expr));
				}
			}
			else if(Condition.OR.equalsIgnoreCase(expression.getLogic())) {
				if(criterion==null) {
					criterion = parseExpression(entityType, expr);
				}
				else {
					criterion = Restrictions.or(criterion, parseExpression(entityType, expr));
				}
			}
		}
		return criterion;
	}
	
	/**
	 * 
	 * @function 转化Criterion表达式
	 * @param
	 *
	 * @return QBC查询条件
	 * @exception
	 *
	 * @author 汪晗
	 * @history
	 *
	 */
	private static Criterion toCriterion(CriterionExpression expression) {
		return expression.getCriterion();
	}
	
	/**
	 * 复制表达式，并加上对空指针的保护
	 * @param expression
	 * @return
	 * @author 汪晗
	 */
	public static Expression copyExpression(Expression expression) {
		if(expression==null) {
			return null;
		} else {
			return expression.clone();
		}
	}
	
	/**
	 * 查找表达式
	 * @param expression
	 * @param visitor
	 * @return
	 * @author 汪晗
	 */
	public static Expression find(Expression expression,ExpressionVisitor visitor) {
		if(expression == null) {
			return null;
		}
		if(expression instanceof PropertyExpression) {
			if(visitor.find((PropertyExpression) expression)) {
				return expression;
			}
		} else if(expression instanceof NoExpression) {
			Expression expr = find(((NoExpression) expression).getExpression(),visitor);
			if(expr!=null) {
				return expr;
			}
		} else if(expression instanceof LogicExpression) {
			Expression leftExpr = find(((LogicExpression) expression).getLeftExpression(),visitor);
			Expression rightExpr = find(((LogicExpression) expression).getRightExpression(),visitor);
			if(leftExpr!=null) {
				return leftExpr;
			} else if(rightExpr!=null) {
				return rightExpr;
			}
		} else if(expression instanceof LogicExpressionExtr) {
			LogicExpressionExtr logiExpr = (LogicExpressionExtr) expression;
			if(logiExpr.getExpressions()!=null) {
				for(Expression expr : logiExpr.getExpressions()) {
					Expression e = find(expr,visitor);
					if(e!=null) {
						return e;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 根据字段名称查找查询条件
	 * @param expression
	 * @param fieldName
	 * @return
	 * @author 汪晗
	 */
	public static Expression findByFieldName(final Expression expression,final String fieldName) {
		return ExpressionHelper.find(expression, new ExpressionVisitor() {
			public boolean find(PropertyExpression expression) {
				if(expression.getPropertyName() != null && expression.getPropertyName().equals(fieldName)) {
					return true;
				}
				return false;
			}
		});
	}

}
