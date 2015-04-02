package com.photography.dao.query;



/**
 * 
 * 
 * @author 门宏建 
 * @since 2011-8-11 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class HqlExpParser {

	public static final char OR = '|';
	public static final char AND = '&';
	public static final char LEFT_PARENTHESIS = '(';
	public static final char RIGHT_PARENTHESIS = ')';

	public final char[] OPERATORS = { AND, OR, LEFT_PARENTHESIS, RIGHT_PARENTHESIS };

	private String exp;// Expression
	private String token;// Token
	private int expIdx;

	// The Errors
	private final static int SYNERR = 0;
	private final static int UNBPAREN = 1;
	private final static int NOEXP = 2;

	private final String errs[] = { "SYNTAX ERROR", "UNBALANCE PARENTHESE", "NO EXPRESSSION" };
	// End of Expression
	private final static String EOE = "\0";

	ParseListener parseListener;
	private String lastToken = "";

	// Handle Errors
	public void handleErr(int err) throws Exception {
		throw new Exception(errs[err]);
	}

	// whether it is a Delimiter
	private boolean isDelim(char a) {
		if (String.valueOf(OPERATORS).indexOf(a) != -1) {
			return true;
		}
		return false;
	}

	// Get next token
	private void getToken() {
		token = "";

		if (expIdx == exp.length()) {
			token = EOE;
			return;
		}
		// If there is white space in expression
		while (expIdx < exp.length() && Character.isWhitespace(exp.charAt(expIdx))) {
			++expIdx;
		}

		if (expIdx == exp.length()) {
			token = EOE;
			return;
		}

		/*
		 * if (Character.isDigit(exp.charAt(expIdx))) { while (expIdx <
		 * exp.length() && Character.isDigit(exp.charAt(expIdx))) { token =
		 * token + exp.charAt(expIdx); expIdx++; } } else
		 */
		if (isDelim(exp.charAt(expIdx))) {

			if (parseListener != null && lastToken!=null) {
				parseListener.onGetOperator(exp.charAt(expIdx), lastToken);
			}
			token = token + exp.charAt(expIdx);

			expIdx++;
		} else if (Character.isDigit(exp.charAt(expIdx)) || Character.isLetter(exp.charAt(expIdx))) {
			while (expIdx < exp.length()
					&& (Character.isDigit(exp.charAt(expIdx)) || exp.charAt(expIdx) == '.' || Character.isLetter(exp.charAt(expIdx)))) {
				token = token + exp.charAt(expIdx);
				expIdx++;
			}
			lastToken = new String(token);
			if (parseListener != null) {
				parseListener.onGetFilterItem(token);
			}
		}
	}

	private String convert() throws Exception {// Process plus and subtract
		String result = "";
		char op;

		result = convertParenthese();

		while ((op = token.charAt(0)) == '&' || op == '|') {
			getToken();
			String partialResult = convertParenthese();
			result = result + partialResult + op;
		}
		return result;
	}

	private String convertParenthese() throws Exception {// Process
		String result = "";

		if (token.charAt(0) == '(') {
			getToken();
			result = convert();
			if (token.charAt(0) != ')') {
				handleErr(UNBPAREN);
			}
		} else {
			result = token;
		}
		getToken();
		return result;
	}

	public HqlExpParser() {

	}

	public void setParseListener(ParseListener parseListener) {
		this.parseListener = parseListener;
	}

	public void parse(String linkOperate) throws Exception {
		exp = linkOperate;
		expIdx = 0;
		getToken();
		if (token.length() == 0) {
			handleErr(NOEXP);
		}

		convert();

		if (!token.equals(EOE)) {
			handleErr(SYNERR);
		}

		return;
	}
}
