package org.mmarini.ratio.web;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UISelectItems;
import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.component.html.HtmlInputText;
import javax.faces.component.html.HtmlSelectOneListbox;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.mmarini.ratio.interpreter.AssignExpression;
import org.mmarini.ratio.interpreter.StringContextInterpreter;
import org.mmarini.ratio.interpreter.SyntaxException;
import org.mmarini.ratio.processor.ArrayRationalValue;
import org.mmarini.ratio.processor.CheckForArrayVisitor;
import org.mmarini.ratio.processor.IRationalValue;
import org.mmarini.ratio.processor.ProcessException;
import org.mmarini.ratio.processor.RationalException;

/**
 * @author $Author: marco $
 * @version $Id: RatioBean.java,v 1.2 2007/01/09 22:11:32 marco Exp $
 */
public class RatioBean extends AbstractBean implements Serializable {
	private static final long serialVersionUID = 1L;

	private static final int MAX_LOG_MESSAGE = 8;

	private static Log log = LogFactory.getLog(RatioBean.class);

	private static Comparator comparator = new Comparator() {

		/**
		 * @param arg0
		 * @param arg1
		 * @return
		 */
		public int compare(Object arg0, Object arg1) {
			SelectItem si0 = (SelectItem) arg0;
			SelectItem si1 = (SelectItem) arg1;
			return si0.getLabel().compareTo(si1.getLabel());
		}

	};

	private HtmlInputText command;
	private HtmlCommandButton newArrayCommand;
	private HtmlCommandButton changeValueCommand;
	private HtmlCommandButton deleteValueCommand;
	private HtmlCommandButton deleteAllCommand;
	private HtmlSelectOneListbox variableList;

	private VariableResolver variableResolver = new VariableResolver();
	private HtmlDataTable logList;

	/**
	 * 
	 */
	public RatioBean() {}

	/**
	 * @param ev
	 */
	public void handleVariableSelected(ValueChangeEvent ev) {
		updateButtonState();
	}

	/**
	 * 
	 *
	 */
	public void executeCommand(ActionEvent ev) {
		long start = System.currentTimeMillis();
		String cmd = (String) getCommand().getValue();
		StringContextInterpreter sci = new StringContextInterpreter(cmd,
				getVariableResolver());
		try {
			AssignExpression.getInstance().interpret(sci);
			sci.execute();
			populateVariableList();
			long elaps = System.currentTimeMillis() - start;
			log("comandExecutedMsg", new Object[] {cmd,
					new Double(elaps / 1000.)});
		} catch (SyntaxException e) {
			log.error("Error interpreting \"" + cmd + "\"", e);
			log(e);
		} catch (ProcessException e) {
			log.error("Error executing \"" + cmd + "\"", e);
			log(e);
		}
	}

	/**
	 * 
	 *
	 */
	private void populateVariableList() {
		/*
		 * Create variable list
		 */
		List selectItems = new ArrayList();
		VariableResolver varRes = getVariableResolver();
		for (Iterator iter = varRes.getVariableMap().keySet().iterator(); iter
				.hasNext();) {
			String name = (String) iter.next();
			selectItems.add(new SelectItem(name, name));
		}
		Collections.sort(selectItems, comparator);
		HtmlSelectOneListbox varList = getVariableList();
		List children = varList.getChildren();
		UISelectItems items;
		if (children.isEmpty()) {
			items = new UISelectItems();
			children.add(items);
		} else {
			items = (UISelectItems) children.get(0);
		}
		items.setValue(selectItems);
		/*
		 * select the value
		 */
		String name = varRes.getLastInsert();
		varList.setValue(name);

		if (selectItems.isEmpty()) {
			getDeleteAllCommand().setDisabled(true);
		} else {
			getDeleteAllCommand().setDisabled(false);
		}
		updateButtonState();
	}

	/**
	 */
	private void updateButtonState() {
		/*
		 * set buttons state
		 */
		HtmlSelectOneListbox varList = getVariableList();
		if (varList.getValue() != null
				&& varList.getValue().toString().length() > 0) {
			getChangeValueCommand().setDisabled(false);
			getDeleteValueCommand().setDisabled(false);
		} else {
			getChangeValueCommand().setDisabled(true);
			getDeleteValueCommand().setDisabled(true);
		}
	}

	/**
	 * @param exception
	 */
	private void log(RationalException exception) {
		log(exception.getMessage(), exception.getParameters());
	}

	/**
	 * @param msgKey
	 * @param parms
	 */
	private void log(String msgKey, Object[] parms) {
		Object[] newParms = new Object[parms.length + 1];
		newParms[0] = new Date();
		System.arraycopy(parms, 0, newParms, 1, parms.length);
		FacesContext ctx = FacesContext.getCurrentInstance();
		UIViewRoot root = ctx.getViewRoot();
		Locale locale = root.getLocale();
		log.debug("Locale = " + locale);
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Application appl = ctx.getApplication();
		String bundleName = appl.getMessageBundle();
		ResourceBundle bundle = ResourceBundle
				.getBundle(bundleName, locale, cl);
		String msgFmt;
		try {
			msgFmt = bundle.getString(msgKey);
		} catch (MissingResourceException e) {
			msgFmt = "??? " + msgKey + " ???";
		}
		MessageFormat fmt = new MessageFormat(msgFmt, locale);
		String line = fmt.format(newParms);
		log(line);
	}

	/**
	 * @param line
	 */
	private void log(String line) {
		HtmlDataTable table = getLogList();
		List list = (List) table.getValue();
		if (list == null) {
			list = new LinkedList() {
			private static final long serialVersionUID = 1L;

				/**
				 * @see java.util.ArrayList#add(java.lang.Object)
				 */
				public boolean add(Object object) {
					add(0, object);
					int n = size();
					if (n > MAX_LOG_MESSAGE)
						remove(n - 1);
					return true;
				}

			};
			table.setValue(list);
		}
		list.add(line);
	}

	/**
	 * @return
	 */
	public IRationalValue getSelectedValue() {
		String name = (String) getVariableList().getValue();
		if (name == null)
			return null;
		VariableResolver res = getVariableResolver();
		return res.get(name);
	}

	/**
	 * 
	 *
	 */
	public String changeValue() {
		log.debug("ChangeValue");
		String name = (String) getVariableList().getValue();
		IRationalValue value = getVariableResolver().get(name);
		if (value == null) {
			log.debug("Cannot edit a null value");
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = createMessage(ctx, "errorEditNullValue",
					new Object[0]);
			ctx.addMessage(null, msg);
			return null;
		}
		CheckForArrayVisitor visitor = new CheckForArrayVisitor();
		value.accept(visitor);
		ArrayRationalValue array = visitor.getArray();
		if (array == null) {
			log.debug("Cannot edit a scalar value");
			FacesContext ctx = FacesContext.getCurrentInstance();
			FacesMessage msg = createMessage(ctx, "errorEditScalarValue",
					new Object[0]);
			ctx.addMessage(null, msg);
			return null;
		}
		FacesContext ctx = FacesContext.getCurrentInstance();
		EditArrayBean editArrayBean = (EditArrayBean) ctx.getApplication()
				.getVariableResolver().resolveVariable(ctx, "editArrayBean");
		editArrayBean.setArray(array);
		editArrayBean.setName(name);
		return "editArray";
	}

	/**
	 * 
	 *
	 */
	public String deleteValue() {
		String name = (String) getVariableList().getValue();
		if (name != null) {
			getVariableResolver().remove(name);
		}
		populateVariableList();
		return null;
	}

	/**
	 * 
	 *
	 */
	public String deleteAllValues() {
		getVariableResolver().clear();
		populateVariableList();
		return null;
	}

	/**
	 * @return Returns the command.
	 */
	public HtmlInputText getCommand() {
		return command;
	}

	/**
	 * @param command
	 *            The command to set.
	 */
	public void setCommand(HtmlInputText command) {
		this.command = command;
	}

	/**
	 * @return Returns the logList.
	 */
	public HtmlDataTable getLogList() {
		return logList;
	}

	/**
	 * @param name
	 * @param value
	 */
	public void setValue(String name, ArrayRationalValue value) {
		log.debug("setValue(" + name + "," + value + ")");
		getVariableResolver().put(name, value);
		populateVariableList();
	}

	/**
	 * @return Returns the changeValueCommand.
	 */
	public HtmlCommandButton getChangeValueCommand() {
		return changeValueCommand;
	}

	/**
	 * @return Returns the deleteAllCommand.
	 */
	public HtmlCommandButton getDeleteAllCommand() {
		return deleteAllCommand;
	}

	/**
	 * @return Returns the deleteValueCommand.
	 */
	public HtmlCommandButton getDeleteValueCommand() {
		return deleteValueCommand;
	}

	/**
	 * @return Returns the newArrayCommand.
	 */
	public HtmlCommandButton getNewArrayCommand() {
		return newArrayCommand;
	}

	/**
	 * @param changeValueCommand
	 *            The changeValueCommand to set.
	 */
	public void setChangeValueCommand(HtmlCommandButton changeValueCommand) {
		if (this.changeValueCommand == null) {
			changeValueCommand.setDisabled(true);
		}
		this.changeValueCommand = changeValueCommand;
	}

	/**
	 * @param deleteAllCommand
	 *            The deleteAllCommand to set.
	 */
	public void setDeleteAllCommand(HtmlCommandButton deleteAllCommand) {
		if (this.deleteAllCommand == null) {
			// deleteAllCommand.setDisabled(true);
		}
		this.deleteAllCommand = deleteAllCommand;
	}

	/**
	 * @param deleteValueCommand
	 *            The deleteValueCommand to set.
	 */
	public void setDeleteValueCommand(HtmlCommandButton deleteValueCommand) {
		if (this.deleteValueCommand == null) {
			deleteValueCommand.setDisabled(true);
		}
		this.deleteValueCommand = deleteValueCommand;
	}

	/**
	 * @param newArrayCommand
	 *            The newArrayCommand to set.
	 */
	public void setNewArrayCommand(HtmlCommandButton newArrayCommand) {
		this.newArrayCommand = newArrayCommand;
	}

	/**
	 * @return Returns the variableList.
	 */
	public HtmlSelectOneListbox getVariableList() {
		return variableList;
	}

	/**
	 * @param variableList
	 *            The variableList to set.
	 */
	public void setVariableList(HtmlSelectOneListbox variableList) {
		this.variableList = variableList;
	}

	/**
	 * @return Returns the variableResolver.
	 */
	public VariableResolver getVariableResolver() {
		return variableResolver;
	}

	/**
	 * @param logList
	 *            The logList to set.
	 */
	public void setLogList(HtmlDataTable logList) {
		this.logList = logList;
	}
}