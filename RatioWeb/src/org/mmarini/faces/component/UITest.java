package org.mmarini.faces.component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.component.EditableValueHolder;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author $Author: marco $
 * @version $Id: UITest.java,v 1.2 2007/01/09 22:11:33 marco Exp $
 */
public class UITest extends UIComponentBase implements NamingContainer {
	private static final String COMPONENT_FAMILY = "org.mmarini.faces.Test";
	private static Log log = LogFactory.getLog(UITest.class);
	private int idx = -1;
	private Map savedState = new HashMap();
	private boolean defaultState = true;
	private String defaultClientId;

	/**
	 * 
	 */
	public UITest() {
		super();
		setTransient(false);
		log.debug(getClass().getName() + " created");
	}

	/**
	 * @see javax.faces.component.UIComponentBase#getRendererType()
	 */
	public String getRendererType() {
		return COMPONENT_FAMILY;
	}

	/**
	 * @see javax.faces.render.Renderer#getRendersChildren()
	 */
	public boolean getRendersChildren() {
		return true;
	}

	public void processValidators(FacesContext context) {
		if (context == null)
			throw new NullPointerException("context");
		if (!isRendered())
			return;
		log.debug("processValidate");
		setIdx(-1);
		processCellChildren(context, new ProcessValidatorsCommand());
		setIdx(-1);
	}

	/**
	 * @see javax.faces.component.UIComponent#processDecodes(javax.faces.context.FacesContext)
	 */
	public void processDecodes(FacesContext context) {
		if (context == null)
			throw new NullPointerException("context");
		if (!isRendered())
			return;
		log.debug("processDecode");
		setIdx(-1);
		processCellChildren(context, new ProcessDecodesCommand());
		log.debug("decode");
		setIdx(-1);
		try {
			decode(context);
		} catch (RuntimeException e) {
			context.renderResponse();
			throw e;
		}
	}

	/**
	 * @see javax.faces.component.UIComponent#processUpdates(javax.faces.context.FacesContext)
	 */
	public void processUpdates(FacesContext context) {
		if (context == null)
			throw new NullPointerException("context");
		if (!isRendered())
			return;
		log.debug("processUpdates");
		setIdx(-1);
		processCellChildren(context, new ProcessUpdatesCommand());
		setIdx(-1);
	}

	/**
	 * @see javax.faces.component.UIComponentBase#decode(javax.faces.context.FacesContext)
	 */
	public void decode(FacesContext ctx) {
		if (ctx == null)
			throw new NullPointerException("context");
		Renderer renderer = getRenderer(ctx);
		if (renderer != null) {
			renderer.decode(ctx, this);
		}
	}

	/**
	 * @param context
	 * @param command
	 */
	private void processCellChildren(FacesContext context,
			AbstractComponentCommand command) {
		command.setContext(context);
		for (int i = 0; i < 2; i++) {
			setIdx(i);
			executeCommandOnChildren(command);
		}
	}

	/**
	 * @param command
	 */
	private void executeCommandOnChildren(AbstractComponentCommand command) {
		for (Iterator it = getChildren().iterator(); it.hasNext();) {
			UIComponent child = (UIComponent) it.next();
			command.setComponent(child);
			command.execute();
		}
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext,
	 *      java.lang.Object)
	 */
	public void restoreState(FacesContext ctx, Object state) {
		Object[] localState = (Object[]) state;
		super.restoreState(ctx, localState[0]);
		idx = ((Integer) localState[1]).intValue();
	}

	/**
	 * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
	 */
	public Object saveState(FacesContext ctx) {
		Object[] state = new Object[2];
		state[0] = super.saveState(ctx);
		state[1] = new Integer(getIdx());
		return state;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	public void encodeBegin(FacesContext ctx) throws IOException {}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	public void encodeChildren(FacesContext ctx) throws IOException {
		if (getChildCount() <= 0)
			return;
		ResponseWriter writer = ctx.getResponseWriter();
		List children = getChildren();
		for (int i = 0; i < 2; ++i) {
			setIdx(i);

			writer.writeComment("ClientId=" + getClientId(ctx));
			writer.write("\n");
			for (Iterator iter = children.iterator(); iter.hasNext();) {
				UIComponent comp = (UIComponent) iter.next();
				if (comp.isRendered()) {
					comp.encodeBegin(ctx);
					if (comp.getRendersChildren())
						comp.encodeChildren(ctx);
					comp.encodeEnd(ctx);
				}
			}
			writer.write("\n");
		}
	}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeEnd(javax.faces.context.FacesContext)
	 */
	public void encodeEnd(FacesContext ctx) throws IOException {}

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	public String getFamily() {
		return COMPONENT_FAMILY;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#getClientId(javax.faces.context.FacesContext)
	 */
	public String getClientId(FacesContext ctx) {
		return super.getClientId(ctx) + "_" + getIdx();
	}

	/**
	 * @return Returns the idx.
	 */
	public int getIdx() {
		return idx;
	}

	/**
	 * @param idx
	 *            The idx to set.
	 */
	public void setIdx(int idx) {
		if (idx == this.idx)
			return;
		FacesContext ctx = getFacesContext();
		String clientId = getClientId(ctx);
		if (isDefaultState()) {
			setDefaultClientId(clientId);
			setDefaultState(false);
			Object state = saveDescendantComponentStates(getChildren()
					.iterator(), false);
			putSavedState(clientId, state);
		} else {
			Object state = saveDescendantComponentStates(getChildren()
					.iterator(), false);
			putSavedState(clientId, state);
		}
		this.idx = idx;
		if (idx < 0) {
			ctx.getExternalContext().getRequestMap().remove("idx");
		} else {
			ctx.getExternalContext().getRequestMap().put("idx",
					new Integer(idx));
		}
		Object state = getSavedState(getClientId(ctx));
		if (state == null) {
			state = getSavedState(getDefaultClientId());
		}
		restoreDescendantComponentStates(getChildren().iterator(), state, false);
	}

	/**
	 * @param iterator
	 * @param state
	 * @param b
	 */
	private void restoreDescendantComponentStates(Iterator iter, Object state,
			boolean restoreChildFacets) {
		Iterator descendantStateIterator = null;
		while (iter.hasNext()) {
			if (descendantStateIterator == null && state != null) {
				descendantStateIterator = ((Collection) state).iterator();
			}
			UIComponent component = (UIComponent) iter.next();
			// reset the client id (see spec 3.1.6)
			component.setId(component.getId());
			if (!component.isTransient()) {
				Object childState = null;
				Object descendantState = null;
				if (descendantStateIterator != null
						&& descendantStateIterator.hasNext()) {
					Object[] object = (Object[]) descendantStateIterator.next();
					childState = object[0];
					descendantState = object[1];
				}
				if (component instanceof EditableValueHolder) {
					((EditableValueHolderState) childState)
							.restoreState((EditableValueHolder) component);
				}
				Iterator childsIterator;
				if (restoreChildFacets) {
					childsIterator = component.getFacetsAndChildren();
				} else {
					childsIterator = component.getChildren().iterator();
				}
				restoreDescendantComponentStates(childsIterator,
						descendantState, true);
			}
		}
	}

	/**
	 * @param children
	 * @param b
	 * @return
	 */
	private Object saveDescendantComponentStates(Iterator iter,
			boolean saveChildFacets) {
		Collection childStates = new ArrayList();
		while (iter.hasNext()) {
			UIComponent child = (UIComponent) iter.next();
			if (!child.isTransient()) {
				Iterator childsIterator;
				if (saveChildFacets) {
					childsIterator = child.getFacetsAndChildren();
				} else {
					childsIterator = child.getChildren().iterator();
				}
				Object descendantState = saveDescendantComponentStates(
						childsIterator, true);
				Object state = null;
				if (child instanceof EditableValueHolder) {
					state = new EditableValueHolderState(
							(EditableValueHolder) child);
				}
				childStates.add(new Object[] {state, descendantState});
			}
		}
		return childStates;
	}

	/**
	 * @return Returns the savedState.
	 */
	private Map getSavedState() {
		return savedState;
	}

	/**
	 * @param key
	 * @return
	 */
	private Object getSavedState(String key) {
		return getSavedState().get(key);
	}

	/**
	 * @param key
	 * @param state
	 */
	private void putSavedState(String key, Object state) {
		getSavedState().put(key, state);
	}

	/**
	 * @return Returns the defaultState.
	 */
	private boolean isDefaultState() {
		return defaultState;
	}

	/**
	 * @param defaultState
	 *            The defaultState to set.
	 */
	private void setDefaultState(boolean defaultState) {
		this.defaultState = defaultState;
	}

	/**
	 * @return Returns the defaultClientId.
	 */
	private String getDefaultClientId() {
		return defaultClientId;
	}

	/**
	 * @param defaultClientId
	 *            The defaultClientId to set.
	 */
	private void setDefaultClientId(String defaultClientId) {
		this.defaultClientId = defaultClientId;
	}
}
