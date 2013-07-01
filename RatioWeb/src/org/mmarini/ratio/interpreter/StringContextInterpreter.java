package org.mmarini.ratio.interpreter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.mmarini.ratio.processor.ICommand;
import org.mmarini.ratio.processor.IRationalValue;
import org.mmarini.ratio.processor.ProcessException;

/**
 * @author $Author: marco $
 * @version $Id: StringContextInterpreter.java,v 1.1.2.1 2006/01/18 16:52:19
 *          marco Exp $
 */
public class StringContextInterpreter extends AbstractInterpreterAdapter
		implements ICommand {
	private List commandList = new ArrayList();
	private List stack = new LinkedList();
	private String assignName;
	private IVariableResolver variableResolver;

	/**
	 * @param expression
	 */
	public StringContextInterpreter(String expression,
			IVariableResolver variableResolver) {
		super(new StringParser(expression));
		this.variableResolver = variableResolver;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IInterpreterContext#getValue(java.lang.String)
	 */
	public IRationalValue getValue(String name) {
		return getVariableResolver().get(name);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IInterpreterContext#push(org.mmarini.ratio.RationalNumber)
	 */
	public void push(IRationalValue num) {
		getStack().add(0, num);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IInterpreterContext#pop()
	 */
	public IRationalValue pop() {
		return (IRationalValue) getStack().remove(0);
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IInterpreterContext#addCommand(org.mmarini.ratio.processor.ICommand)
	 */
	public void addCommand(ICommand command) {
		getCommandList().add(command);
	}

	/**
	 * @see org.mmarini.ratio.processor.ICommand#execute()
	 */
	public void execute() throws ProcessException {
		for (Iterator iter = getCommandList().iterator(); iter.hasNext();) {
			ICommand cmd = (ICommand) iter.next();
			cmd.execute();
		}
	}

	/**
	 * @return Returns the commandList.
	 */
	private List getCommandList() {
		return commandList;
	}

	/**
	 * @return Returns the stack.
	 */
	private List getStack() {
		return stack;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IInterpreterContext#assign(org.mmarini.ratio.RationalNumber)
	 */
	public void assign(IRationalValue a) {
		getVariableResolver().put(getAssignName(), a);
	}

	/**
	 * @return Returns the assignName.
	 */
	private String getAssignName() {
		return assignName;
	}

	/**
	 * @see org.mmarini.ratio.interpreter.IInterpreterContext#setAssignName(java.lang.String)
	 */
	public void setAssignName(String assignName) {
		this.assignName = assignName;
	}

	/**
	 * @return Returns the variableResolver.
	 */
	private IVariableResolver getVariableResolver() {
		return variableResolver;
	}
}
