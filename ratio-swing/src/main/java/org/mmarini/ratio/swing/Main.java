/**
 * 
 */
package org.mmarini.ratio.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.Arrays;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 * @author US00852
 * 
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new Main().run();
	}

	private final JFrame frame;
	private final AbstractAction exitAction;
	private final AbstractAction addExpAction;
	private final AbstractAction delExpAction;
	private final AbstractAction changeExpAction;
	private final AbstractAction newAction;
	private final AbstractAction openAction;
	private final AbstractAction saveAction;
	private final AbstractAction saveAsAction;
	private final ExpressionTableModel expTable;
	private final TableModel arrayTable;
	private final JSplitPane splitPane;

	/**
	 * 
	 */
	public Main() {
		frame = new JFrame(Messages.getString("Main.title")); //$NON-NLS-1$
		expTable = new ExpressionTableModel();
		expTable.setExpressions(Arrays.asList(new ExpressionDef("id1",
				"expression1", "value1")));
		arrayTable = new DefaultTableModel(3, 3);

		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(new JTable(expTable)), createArrayPane());

		addExpAction = new AbstractAction() {
			private static final long serialVersionUID = -5766508291490276836L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		delExpAction = new AbstractAction() {
			private static final long serialVersionUID = -6668398794253288377L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		changeExpAction = new AbstractAction() {
			private static final long serialVersionUID = 8383595489191445967L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		newAction = new AbstractAction() {
			private static final long serialVersionUID = -5621162954617830047L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		openAction = new AbstractAction() {
			private static final long serialVersionUID = 4818687628170975007L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		saveAction = new AbstractAction() {
			private static final long serialVersionUID = 6076144582375870703L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		saveAsAction = new AbstractAction() {
			private static final long serialVersionUID = -7948173877913109561L;

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		exitAction = new AbstractAction() {
			private static final long serialVersionUID = -5766508291490276836L;

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		};

		exitAction.putValue(Action.NAME, Messages.getString("Main.exit.text")); //$NON-NLS-1$
		newAction.putValue(Action.NAME, Messages.getString("Main.new.text")); //$NON-NLS-1$
		openAction.putValue(Action.NAME, Messages.getString("Main.open.text")); //$NON-NLS-1$
		saveAction.putValue(Action.NAME, Messages.getString("Main.save.text")); //$NON-NLS-1$
		saveAsAction.putValue(Action.NAME,
				Messages.getString("Main.saveAs.text")); //$NON-NLS-1$
		addExpAction.putValue(Action.NAME,
				Messages.getString("Main.addExp.text")); //$NON-NLS-1$
		delExpAction.putValue(Action.NAME,
				Messages.getString("Main.delExp.text")); //$NON-NLS-1$
		changeExpAction.putValue(Action.NAME,
				Messages.getString("Main.changeExp.text")); //$NON-NLS-1$

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setJMenuBar(createMenuBar());

		final Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(createToolBar(), BorderLayout.NORTH);
		c.add(createContent(), BorderLayout.CENTER);
	}

	/**
	 * 
	 * @param container
	 * @param comp
	 * @param constraints
	 */
	private void addComponent(final Container container, final Component comp,
			final GridBagConstraints constraints) {
		final GridBagLayout l = (GridBagLayout) container.getLayout();
		l.setConstraints(comp, constraints);
		container.add(comp);
	}

	/**
	 * 
	 * @return
	 */
	private JComponent createArrayPane() {
		return new JScrollPane(new JTable(arrayTable));
	}

	/**
	 * 
	 * @return
	 */
	private Component createContent() {
		return splitPane;
	}

	/**
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		final JMenu fm = new JMenu(Messages.getString("Main.file.text")); //$NON-NLS-1$
		fm.add(newAction);
		fm.add(openAction);
		fm.add(new JSeparator());
		fm.add(saveAction);
		fm.add(saveAsAction);
		fm.add(new JSeparator());
		fm.add(exitAction);
		final JMenu em = new JMenu(Messages.getString("Main.edit.text")); //$NON-NLS-1$
		em.add(addExpAction);
		em.add(changeExpAction);
		em.add(delExpAction);
		final JMenuBar b = new JMenuBar();
		b.add(fm);
		b.add(em);
		return b;
	}

	/**
	 * 
	 * @return
	 */
	private JToolBar createToolBar() {
		final JToolBar t = new JToolBar();
		t.add(newAction);
		t.add(openAction);
		t.add(saveAction);
		t.add(saveAsAction);
		t.add(addExpAction);
		t.add(changeExpAction);
		t.add(delExpAction);
		return t;
	}

	/**
	 * 
	 */
	private void run() {
		frame.setVisible(true);
		splitPane.setDividerLocation(2. / 3.);
	}

}
