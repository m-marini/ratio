package org.mmarini.ratio.web;

import java.util.Locale;

import javax.faces.component.UIViewRoot;
import javax.faces.component.html.HtmlCommandButton;
import javax.faces.context.FacesContext;

/**
 * @author $Author: marco $
 * @version $Id: LocaleBean.java,v 1.2 2007/01/09 22:11:32 marco Exp $
 */
public class LocaleBean {
	private HtmlCommandButton selectItCommand;
	private HtmlCommandButton selectEnCommand;

	/**
	 * @return
	 */
	public String selectEnglish() {
		setLocale(Locale.ENGLISH);
		return null;
	}

	/**
	 * @return
	 */
	public String selectItalian() {
		setLocale(Locale.ITALIAN);
		return null;
	}

	/**
	 * @return Returns the selectEnComand.
	 */
	public HtmlCommandButton getSelectEnCommand() {
		return selectEnCommand;
	}

	/**
	 * @return Returns the selectItComand.
	 */
	public HtmlCommandButton getSelectItCommand() {
		return selectItCommand;
	}

	/**
	 * @param selectEnComand
	 *            The selectEnComand to set.
	 */
	public void setSelectEnCommand(HtmlCommandButton selectEnComand) {
		this.selectEnCommand = selectEnComand;
		setDisableOnLocale(selectEnComand, Locale.ENGLISH);
	}

	/**
	 * @param selectEnComand
	 * @param commandLocale
	 */
	private void setDisableOnLocale(HtmlCommandButton selectEnComand,
			Locale commandLocale) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		UIViewRoot vr = ctx.getViewRoot();
		Locale locale = vr.getLocale();
		if (commandLocale.equals(locale)) {
			selectEnComand.setDisabled(true);
		} else {
			selectEnComand.setDisabled(false);
		}
	}

	/**
	 * 
	 *
	 */
	private void setLocale(Locale locale) {
		FacesContext ctx = FacesContext.getCurrentInstance();
		UIViewRoot vr = ctx.getViewRoot();
		vr.setLocale(locale);
		setDisableOnLocale(getSelectItCommand(), Locale.ITALIAN);
		setDisableOnLocale(getSelectEnCommand(), Locale.ENGLISH);
	}

	/**
	 * @param selectItComand
	 *            The selectItComand to set.
	 */
	public void setSelectItCommand(HtmlCommandButton selectItComand) {
		this.selectItCommand = selectItComand;
		setDisableOnLocale(selectItComand, Locale.ITALIAN);
	}
}
