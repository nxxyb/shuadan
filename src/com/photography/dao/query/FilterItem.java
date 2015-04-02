package com.photography.dao.query;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.photography.dao.exp.Condition;
import com.photography.dao.exp.Expression;
import com.photography.dao.formatter.DateFormatter;
import com.photography.dao.formatter.Formatter;
import com.photography.dao.formatter.IntegerFormatter;
import com.photography.dao.formatter.LikeFormatter;
import com.photography.dao.formatter.LongFormatter;
import com.photography.dao.formatter.LongInFormatter;
import com.photography.dao.formatter.SimpleDateFormatter;
import com.photography.dao.formatter.StringInFormatter;
import com.photography.dao.formatter.YearAndMonthFormatter;
import com.photography.utils.StringUtil;

/**
 * 
 * 数据查询条件
 */
public class FilterItem implements Serializable{

	private static final long serialVersionUID = -287165800530810292L;

	private String filterName;

	private Object filterValue;

	private String expType;

	private String formatter;
	
	private String fieldId;

	private List<Expression> expressions;
	
	private String relation;
	
	private String key;

	public FilterItem(){

	}

	public FilterItem(String filterName, String filterValue, String expType){
		this.filterName = filterName;
		this.filterValue = filterValue;
		this.expType = expType;
	}

	public String getFilterName() {
		return filterName;
	}

	public void setFilterName(String filterName) {
		this.filterName = filterName;
	}

	public Object getFilterValue() {
		return filterValue;
	}

	public void setFilterValue(Object filterValue) {
		if(filterValue instanceof String[]) {
			String[] params = (String[]) filterValue;
			if(params.length==1 && !Formatters.StringInFormatter.name().equals(formatter)) {
				filterValue = params[0];
			}
		}
		this.filterValue = filterValue;
	}

	public String getExpType() {
		return expType;
	}

	public void setExpType(String exptype) {
		this.expType = exptype;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	public void setExpressions(List<Expression> expressions) {
		this.expressions = expressions;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}
	
	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@SuppressWarnings("unchecked")
	public Expression getExpression() {
		if (getFilterValue() == null || StringUtil.isEmpty(getFilterValue().toString()) || getExpType() == null || StringUtil.isEmpty(getExpType().toString())) {
			return null;
		}
		if(getFilterValue() instanceof String) {
			String stringValue = (String) getFilterValue();
			this.filterValue = StringUtil.full2Half(stringValue).trim();
		}
		Formatter formatter = null;
		Object value;
		if (getFormatter() != null && !StringUtil.isEmpty(getFormatter().toString())) {
			formatter = getFormatterClass();
		}
		if (formatter != null) {
			value = formatter.format(getFilterValue());
			if(value instanceof Date) {
				Calendar calendar = Calendar.getInstance();
				calendar.setTime((Date) value);
				if(calendar.get(calendar.HOUR_OF_DAY)==0 && calendar.get(calendar.MINUTE)==0 && calendar.get(calendar.SECOND)==0) {
					// 对于日期类型的大于、等于、不等于需要特殊处理
					String type = getExpType().toUpperCase();
					Calendar gtCalendar = Calendar.getInstance();
					gtCalendar.setTimeInMillis(calendar.getTimeInMillis()+(1000L*60L*60L*24L));
					Date nextDay = gtCalendar.getTime();
					if(getFilterValue().toString().indexOf("年")!=-1 && getFilterValue().toString().indexOf("月")!=-1 && getFilterValue().toString().indexOf("日")==-1) {
						gtCalendar = Calendar.getInstance();
						gtCalendar.setTimeInMillis(calendar.getTimeInMillis());
						if(gtCalendar.get(Calendar.MONTH)+1==12) {
							gtCalendar.set(Calendar.YEAR, gtCalendar.get(Calendar.YEAR)+1);
							gtCalendar.set(Calendar.MONTH, 1);
						} else {
							gtCalendar.set(Calendar.MONTH, gtCalendar.get(Calendar.MONTH)+1);
						}
						nextDay = gtCalendar.getTime();
					}
					if (type.equalsIgnoreCase(QueryType.GT.name())) {
						return Condition.ge(getFilterName(), nextDay);
					} else if (type.equalsIgnoreCase(QueryType.EQ.name())) {
						return Condition.and(Condition.ge(getFilterName(), value), Condition.lt(getFilterName(), nextDay));
					} else if (type.equalsIgnoreCase(QueryType.NE.name())) {
						Expression expression = Condition.or(Condition.lt(getFilterName(), value), Condition.ge(getFilterName(), nextDay));
						if(value!=null) {
							expression = Condition.or(expression, Condition.eq(getFilterName(), null));
						}
						return expression;
					} else if (type.equalsIgnoreCase(QueryType.LE.name())) {
						return Condition.lt(getFilterName(), nextDay);
					}
				}
			}
		} else {
			value = getFilterValue();
			if("null".equals(value)) {
				value = null;
			}
		}
		Expression expression = null;
		String type = getExpType().toUpperCase();
		if (type.equalsIgnoreCase(QueryType.LT.name())) {
			expression = Condition.lt(getFilterName(), value);
		} else if (type.equalsIgnoreCase(QueryType.LE.name())) {
			expression = Condition.le(getFilterName(), value);
		} else if (type.equalsIgnoreCase(QueryType.GT.name())) {
			expression = Condition.gt(getFilterName(), value);
		} else if (type.equalsIgnoreCase(QueryType.GE.name())) {
			expression = Condition.ge(getFilterName(), value);
		} else if (type.equalsIgnoreCase(QueryType.EQ.name())) {
			if((value==null || "".equals(value)) && getFilterName().endsWith(".id")) {
					expression = Condition.eq(getFilterName().substring(0, getFilterName().length()-3), value);
			} else {
				expression = Condition.eq(getFilterName(), value);
			}
		} else if (type.equalsIgnoreCase(QueryType.NE.name())) {
			expression = Condition.ne(getFilterName(), value);
		} else if (type.equalsIgnoreCase(QueryType.LIKE.name())) {
			expression = Condition.like(getFilterName(), value);
		} else if (type.equalsIgnoreCase(QueryType.ILIKE.name())) {
			expression = Condition.ilike(getFilterName(), value);
		} else if (type.equalsIgnoreCase(QueryType.IN.name())) {
			expression = Condition.in(getFilterName(), ((List<Object>) value));
		} else if (type.equalsIgnoreCase(QueryType.OR.name())) {
			expression = Condition.or(expressions);
		} else if (type.equalsIgnoreCase(QueryType.AND.name())) {
			expression = Condition.and(expressions);
		}
		return expression;
	}

	public Formatter getFormatterClass() {
		Formatter formatter = null;
		for(Formatters f : Formatters.values()){
			if(f.name().equalsIgnoreCase(getFormatter())){
				formatter = f.getFormatter();
			}
		}
		return formatter;
	}

	public enum QueryType {
		LT, LE, GT, GE, EQ, NE, LIKE, ILIKE,IN,OR,AND
	}

	public enum Formatters {
		LikeFormatter {
			public Formatter getFormatter() {
				return new LikeFormatter();
			}

		},
		DateFormatter {
			public Formatter getFormatter() {
				return new DateFormatter();
			}

		},
		LongFormatter {
			public Formatter getFormatter() {
				return new LongFormatter();
			}

		},
		IntegerFormatter {
			public Formatter getFormatter() {
				return new IntegerFormatter();
			}

		},
		LongInFormatter {
			public Formatter getFormatter() {
				return new LongInFormatter();
			}

		},
		StringInFormatter {
			public Formatter getFormatter() {
				return new StringInFormatter();
			}

		},
		SimpleDateFormatter {
			public Formatter getFormatter() {
				return new SimpleDateFormatter();
			}

		},
		YearAndMonthFormatter {
			public Formatter getFormatter() {
				return new YearAndMonthFormatter();
			}

		}
		;
		abstract Formatter getFormatter();
	}
	
	
}
