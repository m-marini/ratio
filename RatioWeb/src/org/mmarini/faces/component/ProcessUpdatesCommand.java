package org.mmarini.faces.component;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author: marco $
 * @version $Id: ProcessUpdatesCommand.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class ProcessUpdatesCommand extends AbstractComponentCommand {
	private static Log log = LogFactory.getLog(ProcessUpdatesCommand.class);

	/**
	 * 
	 */
	public ProcessUpdatesCommand() {}

	/**
	 * @param context
	 */
	public ProcessUpdatesCommand(FacesContext context) {
		super(context);
	}

	/**
	 * @see org.mmarini.faces.component.ICommand#execute()
	 */
	public void execute() {
		UIComponent comp = getComponent();
		log
				.debug("calling processUpdates on "
						+ comp.getClientId(getContext()));
		comp.processUpdates(getContext());
	}
}
