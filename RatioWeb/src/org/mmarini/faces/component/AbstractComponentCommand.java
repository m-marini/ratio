package org.mmarini.faces.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 * @author $Author: marco $
 * @version $Id: AbstractComponentCommand.java,v 1.1.2.1 2006/02/14 18:15:47
 *          marco Exp $
 */
public abstract class AbstractComponentCommand implements ICommand {
	private UIComponent component;
	private FacesContext context;

	/**
	 * 
	 */
	public AbstractComponentCommand() {}

	/**
	 * @param context
	 */
	public AbstractComponentCommand(FacesContext context) {
		this.context = context;
	}

	/**
	 * @return Returns the component.
	 */
	public UIComponent getComponent() {
		return component;
	}

	/**
	 * @return Returns the context.
	 */
	public FacesContext getContext() {
		return context;
	}

	/**
	 * @param component
	 *            The component to set.
	 */
	public void setComponent(UIComponent component) {
		this.component = component;
	}

	/**
	 * @param context
	 *            The context to set.
	 */
	public void setContext(FacesContext context) {
		this.context = context;
	}

}
