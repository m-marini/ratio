/**
 * 
 */
package org.mmarini.ratio.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.WindowConstants;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.JTextComponent;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLFrameHyperlinkEvent;

import org.mmarini.ratio.RationalNumber;
import org.mmarini.ratio.interpreter.ArrayValue;
import org.mmarini.ratio.interpreter.ErrorValue;
import org.mmarini.ratio.interpreter.Interpreter;
import org.mmarini.ratio.interpreter.ParserException;
import org.mmarini.ratio.interpreter.ScalarValue;
import org.mmarini.ratio.interpreter.Value;
import org.mmarini.ratio.interpreter.ValueVisitor;
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e) {
			logger.error(e.getMessage(), e);
		}
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
	private final ExpressionTableModel expTableModel;
	private final JSplitPane splitPane;
	private Map<String, String> defs;
	private Interpreter interpreter;
	private final JFileChooser fileChooser;
	private final JTable expTable;
	private final JTextField idField;
	private final JTextArea editingField;
	private final JTextArea errorField;
	private final JTextArea valueField;
	private boolean saveEnabled;
	private final AbstractAction helpAction;
	private final JDialog helpDialog;

	/**
	 * 
	 */
	public Main() {

		frame = new JFrame(Messages.getString("Main.title")); //$NON-NLS-1$
		defs = new HashMap<>();
		expTableModel = new ExpressionTableModel();
		fileChooser = new JFileChooser(new File(".")); //$NON-NLS-1$
		idField = new JTextField(10);
		editingField = new JTextArea();
		errorField = new JTextArea();
		valueField = new JTextArea();
		helpDialog = new JDialog(frame);
		final ActionBuilder ab = new ActionBuilder();

		addExpAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = -5766508291490276836L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				addExpression();
			}
		}, "addExpAction"); //$NON-NLS-1$
		helpAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = -5766508291490276836L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				help();
			}
		}, "helpAction"); //$NON-NLS-1$
		deleteAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = -6668398794253288377L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				delete();
			}
		}, "deleteAction"); //$NON-NLS-1$
		restoreAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = 8383595489191445967L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				selectExp();
			}
		}, "restoreAction"); //$NON-NLS-1$
		applyAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = 8383595489191445967L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				apply();
			}
		}, "applyAction"); //$NON-NLS-1$
		newAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = -5621162954617830047L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				defs.clear();
				saveAction.setEnabled(false);
				process();
			}
		}, "newAction"); //$NON-NLS-1$
		openAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = 4818687628170975007L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				open();
			}
		}, "openAction"); //$NON-NLS-1$
		saveAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = 6076144582375870703L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				save();
			}
		}, "saveAction"); //$NON-NLS-1$
		saveAsAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = -7948173877913109561L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				saveAs();
			}
		}, "saveAsAction"); //$NON-NLS-1$
		exitAction = ab.setUp(new AbstractAction() {
			private static final long serialVersionUID = -5766508291490276836L;

			@Override
			public void actionPerformed(final ActionEvent e) {
				frame.dispose();
			}
		}, "exitAction"); //$NON-NLS-1$

		expTable = new JTable(expTableModel);
		expTable.setAutoCreateRowSorter(true);
		expTable.getSelectionModel().setSelectionMode(
				ListSelectionModel.SINGLE_SELECTION);
		splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				new JScrollPane(expTable), createEditor());

		expTable.getSelectionModel().addListSelectionListener(
				new ListSelectionListener() {

					@Override
					public void valueChanged(final ListSelectionEvent e) {
						if (!e.getValueIsAdjusting())
							selectExp();
					}
				});

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setJMenuBar(createMenuBar());

		idField.setEnabled(false);

		editingField.setRows(5);
		editingField.invalidate();
		editingField.setEnabled(false);

		errorField.setRows(3);
		errorField.invalidate();
		errorField.setEditable(false);

		valueField.setEditable(false);

		final Container c = frame.getContentPane();
		c.setLayout(new BorderLayout());
		c.add(createToolBar(), BorderLayout.NORTH);
		c.add(createContent(), BorderLayout.CENTER);

		saveAction.setEnabled(false);
		deleteAction.setEnabled(false);
		applyAction.setEnabled(false);
		restoreAction.setEnabled(false);

		fileChooser.setFileFilter(new FileNameExtensionFilter(Messages
				.getString("Main.filetype.text"), //$NON-NLS-1$
				"xml")); //$NON-NLS-1$
		try {
			final URL url = getClass().getResource("/help/help.html"); //$NON-NLS-1$
			final JEditorPane hp = new JEditorPane(url);
			hp.addHyperlinkListener(new HyperlinkListener() {

				@Override
				public void hyperlinkUpdate(final HyperlinkEvent e) {
					if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
						final JEditorPane pane = (JEditorPane) e.getSource();
						if (e instanceof HTMLFrameHyperlinkEvent) {
							final HTMLFrameHyperlinkEvent evt = (HTMLFrameHyperlinkEvent) e;
							final HTMLDocument doc = (HTMLDocument) pane
									.getDocument();
							doc.processHTMLFrameHyperlinkEvent(evt);
						} else {
							try {
								pane.setPage(e.getURL());
							} catch (final IOException e1) {
								showMessage(e1);
							}
						}
					}
				}
			});
			hp.setEditable(false);
			helpDialog.setContentPane(new JScrollPane(hp));
		} catch (final IOException e1) {
			logger.error(e1.getMessage(), e1);
		}
		helpDialog.setSize(800, 600);
		helpDialog.setLocation(20, 20);
		helpDialog.setTitle(Messages.getString("Main.help.title")); //$NON-NLS-1$
		helpDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
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
	 */
	private void addExpression() {
		String id;
		for (int i = 1;; ++i) {
			id = "exp_" + i; //$NON-NLS-1$
			if (!defs.containsKey(id))
				break;
		}
		defs.put(id, "0"); //$NON-NLS-1$
		process();
		select(id);
		if (saveEnabled)
			saveAction.setEnabled(true);
	}

	/**
	 * 
	 */
	private void apply() {
		final int row = expTable.getSelectedRow();
		if (row >= 0) {
			final String oldId = expTable.getValueAt(row, 0).toString();
			final String newId = idField.getText();
			final String exp = editingField.getText();
			if (!newId.matches("\\w*")) { //$NON-NLS-1$
				showMessage("Main.notAnIdentifier.text", newId); //$NON-NLS-1$
				return;
			}
			if (!newId.equals(oldId) && defs.containsKey(newId)) {
				showMessage("Main.alreadyDefined", newId); //$NON-NLS-1$
				return;
			}
			defs.remove(oldId);
			defs.put(newId, exp);
			process();
			select(newId);
			if (saveEnabled)
				saveAction.setEnabled(true);
		}
	}

	/**
	 * @param values
	 * @return
	 */
	private String composeArrayValue(final RationalNumber[][] values) {
		final int n = values.length;
		final int m = values[0].length;
		final String[][] v = new String[n][m];
		int l = 0;
		for (int i = 0; i < n; ++i)
			for (int j = 0; j < m; ++j) {
				final String s = String.valueOf(values[i][j]);
				final int k = s.length();
				if (k > l)
					l = k;
				v[i][j] = s;
			}

		final StringBuilder b = new StringBuilder();
		boolean firstRow = true;
		for (final String[] r : v) {
			if (firstRow)
				firstRow = false;
			else
				b.append('\n');
			boolean firstCell = true;
			for (final String c : r) {
				if (firstCell)
					firstCell = false;
				else
					b.append(' ');
				b.append(fill(c, l));
			}
		}
		return b.toString();
	}

	/**
	 * 
	 * @return
	 */
	private Component createContent() {
		return splitPane;
	}

	/**
	 * @return
	 */
	private Component createEditor() {

		final JPanel p = new JPanel();
		p.setLayout(new GridBagLayout());
		final GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(2, 2, 2, 2);

		addComponent(p,
				new JLabel(Messages.getString("Main.identifier.text")), gbc); //$NON-NLS-1$

		gbc.weightx = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.WEST;
		addComponent(p, idField, gbc);

		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.anchor = GridBagConstraints.CENTER;
		addComponent(p,
				createEditorPane(editingField, "Main.expression.text"), gbc); //$NON-NLS-1$

		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		addComponent(p, createEditorPane(errorField, "Main.error.text"), gbc); //$NON-NLS-1$

		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.BOTH;
		addComponent(p, createEditorPane(valueField, "Main.value.text"), gbc); //$NON-NLS-1$

		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		addComponent(p, createEditorBar(), gbc);

		return p;
	}

	/**
	 * @return
	 */
	private JComponent createEditorBar() {
		final Box b = Box.createHorizontalBox();
		b.add(Box.createHorizontalGlue());
		b.add(new JButton(deleteAction));
		b.add(Box.createHorizontalStrut(10));
		b.add(new JButton(restoreAction));
		b.add(Box.createHorizontalStrut(10));
		b.add(new JButton(applyAction));
		return b;
	}

	/**
	 * @param field
	 * @param title
	 * @return
	 */
	private Component createEditorPane(final JTextComponent field,
			final String title) {
		field.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 14));
		final JScrollPane sp = new JScrollPane(field);
		sp.setBorder(BorderFactory.createTitledBorder(Messages.getString(title)));
		return sp;
	}

	/**
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		return new ActionBuilder().createMenuBar("file", newAction, openAction, //$NON-NLS-1$
				null, saveAction, saveAsAction, null, exitAction, "edit", //$NON-NLS-1$
				addExpAction, "help", helpAction); //$NON-NLS-1$
	}

	/**
	 * 
	 * @return
	 */
	private JToolBar createToolBar() {
		return new ActionBuilder().createHorizontalToolBar(newAction,
				openAction, saveAction, saveAsAction, null, addExpAction, null);
	}

	/**
	 * 
	 */
	private void delete() {
		final int row = expTable.getSelectedRow();
		if (row >= 0) {
			final String id = expTable.getValueAt(row, 0).toString();
			defs.remove(id);
			process();
			if (saveEnabled)
				saveAction.setEnabled(true);
		}
	}

	/**
	 * @param c
	 * @param l
	 * @return
	 */
	private String fill(final String c, final int l) {
		final StringBuilder b = new StringBuilder(c);
		while (b.length() < l)
			b.append(' ');
		return b.toString();
	}

	/**
	 * 
	 */
	private void help() {
		final Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
		final Dimension fs = frame.getSize();
		final Point fl = frame.getLocation();
		final int hw = 400;
		final int hx = Math.min(fl.x + fs.width, ss.width - hw);
		helpDialog.setLocation(hx, fl.y);
		helpDialog.setSize(hw, fs.height);
		helpDialog.setVisible(true);
	}

	/**
	 * 
	 */
	private void open() {
		if (fileChooser.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION) {
			final File f = fileChooser.getSelectedFile();
			try {
				defs = new DefsSerializer().load(f);
				process();
				saveEnabled = true;
			} catch (final Exception e) {
				saveEnabled = false;
				showMessage(e);
			}
			saveAction.setEnabled(false);
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
		expTableModel.setExpressions(l);
		selectExp();
	}

	/**
	 * 
	 */
	private void run() {
		frame.setVisible(true);
		final double r = 1. / 3.;
		splitPane.setDividerLocation(r);
		splitPane.setResizeWeight(r);
		process();
	}

	/**
	 * 
	 */
	private void save() {
		final File f = fileChooser.getSelectedFile();
		try {
			new DefsSerializer().save(f, defs);
			saveEnabled = true;
		} catch (final Exception e) {
			showMessage(e);
			saveEnabled = false;
		}
		saveAction.setEnabled(false);
	}

	/**
	 * 
	 */
	private void saveAs() {
		if (fileChooser.showSaveDialog(frame) == JFileChooser.APPROVE_OPTION) {
			final File f = fileChooser.getSelectedFile();
			if (!f.exists()
					|| JOptionPane
							.showConfirmDialog(
									frame,
									String.format(
											Messages.getString("Main.ovverrideFile.text"), f), //$NON-NLS-1$
									Messages.getString("Main.warinig.text"), JOptionPane.YES_NO_OPTION, //$NON-NLS-1$
									JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
				save();
		}
	}

	/**
	 * @param id
	 */
	private void select(final String id) {
		final ListSelectionModel m = expTable.getSelectionModel();
		m.clearSelection();
		for (int i = 0; i < expTable.getRowCount(); ++i)
			if (expTable.getValueAt(i, 0).equals(id)) {
				m.setSelectionInterval(i, i);
				break;
			}
	}

	/**
	 *
	 */
	private void selectExp() {
		final int row = expTable.getSelectedRow();
		if (row >= 0) {
			final String id = expTable.getValueAt(row, 0).toString();
			idField.setText(id);
			idField.setEnabled(true);
			editingField.setText(defs.get(id));
			editingField.setEnabled(true);
			showValue(interpreter.getValues().get(id));
			deleteAction.setEnabled(true);
			applyAction.setEnabled(true);
			restoreAction.setEnabled(true);
		} else {
			idField.setText(""); //$NON-NLS-1$
			idField.setEnabled(false);
			editingField.setText(""); //$NON-NLS-1$
			editingField.setEnabled(false);
			errorField.setText(""); //$NON-NLS-1$
			valueField.setText(""); //$NON-NLS-1$
			deleteAction.setEnabled(false);
			applyAction.setEnabled(false);
			restoreAction.setEnabled(false);
		}
	}

	/**
	 * @param e
	 */
	private void showMessage(final Exception e) {
		logger.error(e.getMessage(), e);
		JOptionPane.showMessageDialog(frame, e.getMessage(),
				Messages.getString("Main.error.title"), //$NON-NLS-1$
				JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * @param key
	 * @param args
	 */
	private void showMessage(final String key, final Object... args) {
		JOptionPane.showMessageDialog(frame,
				String.format(Messages.getString(key), args),
				Messages.getString("Main.error.title"), //$NON-NLS-1$
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * @param value
	 */
	private void showValue(final Value value) {
		try {
			errorField.setText(value.apply(new ValueVisitor<String>() {

				@Override
				public String visit(final ArrayValue value) {
					return ""; //$NON-NLS-1$
				}

				@Override
				public String visit(final ErrorValue value) {
					return value.getException().getDescription();
				}

				@Override
				public String visit(final ScalarValue value) {
					return ""; //$NON-NLS-1$
				}
			}));
			valueField.setText(value.apply(new ValueVisitor<String>() {

				@Override
				public String visit(final ArrayValue value) {
					return composeArrayValue(value.getValue().getValues());
				}

				@Override
				public String visit(final ErrorValue value) {
					return value.toString();
				}

				@Override
				public String visit(final ScalarValue value) {
					return value.toString();
				}
			}));
		} catch (final ParserException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
