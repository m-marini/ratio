/**
 * 
 */
package org.mmarini.ratio.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.mmarini.ratio.interpreter.Interpreter;
import org.mmarini.ratio.interpreter.Value;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author US00852
 * 
 */
public class Main {
	public static final Logger logger = LoggerFactory.getLogger(Main.class);

	/**
	 * @param args
	 */
	public static void main(final String[] args) {
		new Main().run();
	}

	private final JFrame frame;
	private final AbstractAction exitAction;
	private final AbstractAction addExpAction;
	private final AbstractAction deleteAction;
	private final AbstractAction newAction;
	private final AbstractAction openAction;
	private final AbstractAction saveAction;
	private final AbstractAction saveAsAction;
	private final AbstractAction restoreAction;
	private final AbstractAction applyAction;
	private final ExpressionTableModel expTable;
	private final JSplitPane splitPane;
	private Map<String, String> defs;
	private Interpreter interpreter;
	private final JFileChooser fileChooser;
	private final PlainDocument editingDoc;
	private final PlainDocument errorDoc;
	private final PlainDocument valueDoc;

	/**
	 * 
	 */
	public Main() {
		frame = new JFrame(Messages.getString("Main.title")); //$NON-NLS-1$
		editingDoc = new PlainDocument();
		errorDoc = new PlainDocument();
		valueDoc = new PlainDocument();
		defs = new HashMap<>();
		expTable = new ExpressionTableModel();
		fileChooser = new JFileChooser(new File("."));
		addExpAction = new AbstractAction(
				Messages.getString("Main.addExp.text")) {
			private static final long serialVersionUID = -5766508291490276836L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		deleteAction = new AbstractAction(
				Messages.getString("Main.delete.text")) {
			private static final long serialVersionUID = -6668398794253288377L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		restoreAction = new AbstractAction(
				Messages.getString("Main.restore.text")) {
			private static final long serialVersionUID = 8383595489191445967L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		applyAction = new AbstractAction(Messages.getString("Main.apply.text")) {
			private static final long serialVersionUID = 8383595489191445967L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				// TODO Auto-generated method stub

			}
		};
		newAction = new AbstractAction(Messages.getString("Main.new.text")) {
			private static final long serialVersionUID = -5621162954617830047L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				defs.clear();
				saveAction.setEnabled(false);
				process();
			}
		};
		openAction = new AbstractAction(Messages.getString("Main.open.text")) {
			private static final long serialVersionUID = 4818687628170975007L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				open();
			}
		};
		saveAction = new AbstractAction(Messages.getString("Main.save.text")) {
			private static final long serialVersionUID = 6076144582375870703L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				save();
			}
		};
		saveAsAction = new AbstractAction(
				Messages.getString("Main.saveAs.text")) {
			private static final long serialVersionUID = -7948173877913109561L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				saveAs();
			}
		};
		exitAction = new AbstractAction(Messages.getString("Main.exit.text")) {
			private static final long serialVersionUID = -5766508291490276836L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				frame.dispose();
			}
		};
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(new JTable(expTable)), createExpEditor());

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setJMenuBar(createMenuBar());

		final Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(createToolBar(), BorderLayout.NORTH);
		c.add(createContent(), BorderLayout.CENTER);
		saveAction.setEnabled(false);
		deleteAction.setEnabled(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Definitions",
				"xml"));
	}

	/**
	 * @return
	 */
	private Component createExpEditor() {

		final JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);

		addComponent(p, new JLabel("Identifier"), gbc);

		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		addComponent(p, new JTextField(), gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		addComponent(p, createEditor(editingDoc, "Expression", true), gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		addComponent(p, createEditor(errorDoc, "Error", false), gbc);

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		addComponent(p, createEditor(valueDoc, "Values", false), gbc);

		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		addComponent(p, createApplyPane(), gbc);

		return p;
	}

	/**
	 * @return
	 */
	private JComponent createApplyPane() {
		final Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(new JButton(restoreAction));
		b.add(Box.createHorizontalStrut(10));
		b.add(new JButton(applyAction));
		return b;
	}

	/**
	 * 
	 * @param doc
	 * @param title
	 * @param editable
	 * @return
	 */
	private JComponent createEditor(final Document doc, final String title,
			final boolean editable) {
		final JTextArea t = new JTextArea(doc);
		t.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		t.setEditable(editable);
		final JScrollPane sp = new JScrollPane(t);
		sp.setBorder(BorderFactory.createTitledBorder(title));
		return sp;
	}

	/**
	 * 
	 */
	private void saveAs() {
		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			final File f = fileChooser.getSelectedFile();
			if (!f.exists()
					|| JOptionPane.showConfirmDialog(frame, String.format(
							"Do you want overwrite the file\n%s? ", f),
							"Warning", JOptionPane.YES_NO_OPTION,
							JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION)
				save();
		}
	}

	/**
	 * 
	 */
	private void save() {
		final File f = fileChooser.getSelectedFile();
		try {
			new DefsSerializer().save(f, defs);
		} catch (final Exception e) {
			showMessage(e);
			saveAction.setEnabled(false);
		}
	}

	/**
	 * 
	 */
	private void open() {
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			final File f = fileChooser.getSelectedFile();
			try {
				defs = new DefsSerializer().load(f);
				saveAction.setEnabled(true);
				process();
			} catch (final Exception e) {
				showMessage(e);
			}
		}
	}

	/**
	 * @param e
	 */
	private void showMessage(final Exception e) {
		logger.error(e.getMessage(), e);
		JOptionPane.showMessageDialog(frame, e.getMessage(), "Error",
				JOptionPane.ERROR_MESSAGE);
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
		em.add(deleteAction);
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
		t.add(deleteAction);
		return t;
	}

	/**
	 * 
	 */
	private void run() {
		frame.setVisible(true);
		final double r = 1. / 2.;
		splitPane.setDividerLocation(r);
		splitPane.setResizeWeight(r);
		process();
		setEditingText("prova");
	}

	/**
	 * 
	 * @param text
	 */
	private void setEditingText(final String text) {
		try {
			editingDoc.remove(0, editingDoc.getLength());
			editingDoc.insertString(0, text, null);
		} catch (final BadLocationException e) {
			logger.error(e.getMessage(), e);
		}
	}

	/**
	 * 
	 */
	private void process() {
		interpreter = new Interpreter(defs);
		final List<ExpressionDef> l = new ArrayList<>();
		final Map<String, Value> values = interpreter.getValues();
		for (final Entry<String, String> e : defs.entrySet()) {
			final String id = e.getKey();
			l.add(new ExpressionDef(id, e.getValue(), values.get(id).toString()));
		}
		expTable.setExpressions(l);
	}
}
