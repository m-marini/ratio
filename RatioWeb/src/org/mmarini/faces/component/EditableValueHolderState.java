package org.mmarini.faces.component;

import javax.faces.component.EditableValueHolder;

/**
 * @author $Author: marco $
 * @version $Id: EditableValueHolderState.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
class EditableValueHolderState {
	private final Object value;
	private final boolean localValueSet;
	private final boolean valid;
	private final Object submittedValue;

	/**
	 * @param evh
	 */
	public EditableValueHolderState(EditableValueHolder evh) {
		value = evh.getLocalValue();
		localValueSet = evh.isLocalValueSet();
		valid = evh.isValid();
		submittedValue = evh.getSubmittedValue();
	}

	/**
	 * @param evh
	 */
	public void restoreState(EditableValueHolder evh) {
		evh.setValue(value);
		evh.setLocalValueSet(localValueSet);
		evh.setValid(valid);
		evh.setSubmittedValue(submittedValue);
	}
}