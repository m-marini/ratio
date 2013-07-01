package org.mmarini.ratio.interpreter;

/**
 * @author $Author: marco $
 * @version $Id: AbstractNameExpression.java,v 1.1.2.1 2006/01/18 18:08:52 marco
 *          Exp $
 */
public class VariableNameValidator {
	private static VariableNameValidator instance = new VariableNameValidator();

	/**
	 * @return Returns the instance.
	 */
	public static VariableNameValidator getInstance() {
		return instance;
	}

	/**
	 * 
	 */
	protected VariableNameValidator() {}

	/**
	 * @param token
	 * @return
	 */
	public boolean isName(String token) {
		if (token == null || token.length() == 0)
			return false;
		if (!isNameStart(token.charAt(0)))
			return false;
		for (int i = 1; i < token.length(); ++i) {
			if (!isNameContent(token.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * @param ch
	 * @return
	 */
	public boolean isNameContent(char ch) {
		if (ch >= '0' && ch <= '9')
			return true;
		return isNameStart(ch);
	}

	/**
	 * @param ch
	 * @return
	 */
	public boolean isNameStart(char ch) {
		if (ch >= 'a' && ch <= 'z')
			return true;
		if (ch >= 'A' && ch <= 'Z')
			return true;
		if (ch == '_')
			return true;
		return false;
	}
}
