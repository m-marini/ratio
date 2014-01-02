/**
 * 
 */
package org.mmarini.ratio.swing;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

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

	/**
	 * 
	 */
	public Main() {
		frame = new JFrame(Messages.getString("Main.title")); //$NON-NLS-1$
		exitAction = new AbstractAction() {
			private static final long serialVersionUID = -5766508291490276836L;

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		};

		exitAction.putValue(Action.NAME, Messages.getString("Main.exit.text")); //$NON-NLS-1$

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setJMenuBar(createMenuBar());
	}

	/**
	 * 
	 * @return
	 */
	private JMenuBar createMenuBar() {
		final JMenu fm = new JMenu(Messages.getString("Main.file.text")); //$NON-NLS-1$
		fm.add(exitAction);
		final JMenuBar b = new JMenuBar();
		b.add(fm);
		return b;
	}

	/**
	 * 
	 */
	private void run() {
		frame.setVisible(true);
	}

}
