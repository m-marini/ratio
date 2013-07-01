package org.mmarini.faces.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author: marco $
 * @version $Id: ProcessValidatorsCommand.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class ProcessValidatorsCommand extends AbstractComponentCommand {
	private static Log log = LogFactory.getLog(ProcessValidatorsCommand.class);

	/**
	 * 
	 */
	public ProcessValidatorsCommand() {}

	/**
	 * @param context
	 */
	public ProcessValidatorsCommand(FacesContext context) {
		super(context);
	}

	/**
	 * @see org.mmarini.faces.component.ICommand#execute()
	 */
	public void execute() {
		UIComponent comp = getComponent();
		FacesContext context = getContext();
		String key = comp.getClientId(context);
		log.debug("calling processValidators on " + key);
		comp.processValidators(context);
	}
}