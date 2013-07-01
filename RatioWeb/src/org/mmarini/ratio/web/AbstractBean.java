package org.mmarini.ratio.web;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

/**
 * @author $Author: marco $
 * @version $Id: AbstractBean.java,v 1.2 2007/01/09 22:11:32 marco Exp $
 */
public abstract class AbstractBean {
	/**
	 * 
	 */
	public AbstractBean() {}

	/**
	 * @param ctx
	 * @param msgId
	 * @param parms
	 * @return
	 */
	protected ValidatorException createException(FacesContext ctx,
			String msgId, Object[] parms) {
		FacesMessage msg = createMessage(ctx, msgId, parms);
		ValidatorException exp = new ValidatorException(msg);
		return exp;
	}

	/**
	 * @param ctx
	 * @param msgId
	 * @param parms
	 * @return
	 */
	protected FacesMessage createMessage(FacesContext ctx, String msgId,
			Object[] parms) {
		UIViewRoot root = ctx.getViewRoot();
		Locale locale = root.getLocale();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Application appl = ctx.getApplication();
		String bundleName = appl.getMessageBundle();
		ResourceBundle bundle = ResourceBundle
				.getBundle(bundleName, locale, cl);
		String msgFmt = bundle.getString(msgId);
		String msgText = MessageFormat.format(msgFmt, parms);
		FacesMessage msg = new FacesMessage(msgText);
		return msg;
	}
}
