/**
 * 
 */
package org.mmarini.ratio.swing;

import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

/**
 * @author US00852
 * 
 */
public class ActionBuilder {
	public <A extends Action> A setUp(final A a, final String id) {
		a.putValue(Action.NAME,
				Messages.getString(String.format("ActionBuilder.%s.name", id)));

		final String ak = Messages.getString(String.format(
				"ActionBuilder.%s.acceleratorKey", id));
		if (!ak.startsWith("!"))
			a.putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke(ak));

		final String mk = Messages.getString(String.format(
				"ActionBuilder.%s.menmonicKey", id));
		if (!mk.startsWith("!"))
			a.putValue(Action.MNEMONIC_KEY,
					KeyEvent.getExtendedKeyCodeForChar(mk.charAt(0)));

		final String si = Messages.getString(String.format(
				"ActionBuilder.%s.smallIcon", id));
		if (!si.startsWith("!")) {
			final URL url = getClass().getResource(si);
			if (url != null)
				a.putValue(Action.SMALL_ICON, new ImageIcon(url));
		}

		final String li = Messages.getString(String.format(
				"ActionBuilder.%s.largeIcon", id));
		if (!li.startsWith("!")) {
			final URL url = getClass().getResource(li);
			if (url != null)
				a.putValue(Action.LARGE_ICON_KEY, new ImageIcon(url));
		}

		final String sd = Messages.getString(String.format(
				"ActionBuilder.%s.shortDescription", id));
		if (!sd.startsWith("!"))
			a.putValue(Action.SHORT_DESCRIPTION, sd);

		final String ld = Messages.getString(String.format(
				"ActionBuilder.%s.longDescription", id));
		if (!ld.startsWith("!"))
			a.putValue(Action.LONG_DESCRIPTION, ld);
		return a;
	}

	/**
	 * 
	 * @return
	 */
	public JToolBar createHorizontalToolBar(final Action... actions) {
		final JToolBar tb = new JToolBar();
		for (final Action a : actions)
			if (a != null)
				tb.add(a);
			else
				tb.add(new JSeparator(JSeparator.VERTICAL));
		return tb;
	}

	/**
	 * 
	 * @return
	 */
	public JMenuBar createMenuBar(final Object... defs) {
		final JMenuBar mb = new JMenuBar();
		JMenu m = null;
		for (final Object d : defs) {
			if (d == null)
				m.add(new JSeparator());
			else if (d instanceof String) {
				m = createMenu(d.toString());
				mb.add(m);
			} else if (d instanceof Action)
				m.add((Action) d);

		}
		return mb;
	}

	/**
	 * @param id
	 * @return
	 */
	private JMenu createMenu(final String id) {
		final JMenu m = new JMenu(Messages.getString(String.format(
				"ActionBuilder.%s.name", id)));

		final String ak = Messages.getString(String.format(
				"ActionBuilder.%s.acceleratorKey", id));
		if (!ak.startsWith("!"))
			m.setAccelerator(KeyStroke.getKeyStroke(ak));

		final String mk = Messages.getString(String.format(
				"ActionBuilder.%s.menmonicKey", id));
		if (!mk.startsWith("!"))
			m.setMnemonic(KeyEvent.getExtendedKeyCodeForChar(mk.charAt(0)));

		final String si = Messages.getString(String.format(
				"ActionBuilder.%s.smallIcon", id));
		if (!si.startsWith("!")) {
			final URL url = getClass().getResource(si);
			if (url != null)
				m.setIcon(new ImageIcon(url));
		}

		final String sd = Messages.getString(String.format(
				"ActionBuilder.%s.shortDescription", id));
		if (!sd.startsWith("!"))
			m.setToolTipText(sd);
		return m;
	}
}