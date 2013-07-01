package org.mmarini.faces.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author: marco $
 * @version $Id: ProcessDecodesCommand.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class ProcessDecodesCommand extends AbstractComponentCommand {
	private static Log log = LogFactory.getLog(ProcessDecodesCommand.class);

	/**
	 * 
	 */
	public ProcessDecodesCommand() {}

	/**
	 * @param context
	 */
	public ProcessDecodesCommand(FacesContext context) {
		super(context);
	}

	/**
	 * @see org.mmarini.faces.component.ICommand#execute()
	 */
	public void execute() {
		UIComponent comp = getComponent();
		FacesContext context = getContext();
		String key = comp.getClientId(context);
		log.debug("calling processDecodes on " + key);
		comp.processDecodes(context);
	}
}
