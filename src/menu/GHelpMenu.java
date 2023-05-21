package menu;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.EHelpMenuItem;

public class GHelpMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components

	// associations
	private GPanel panel;
	String filePath = "src/graphicsEditorA/readme.pdf";
	File pdfFile = new File(filePath);

	public GHelpMenu(String text) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EHelpMenuItem eHelpMenuItem : EHelpMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eHelpMenuItem.getText());
			KeyStroke shortKey = KeyStroke.getKeyStroke(eHelpMenuItem.getKey(), InputEvent.CTRL_DOWN_MASK);
			menuItem.setActionCommand(eHelpMenuItem.name());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(shortKey);
			this.add(menuItem);
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private void info() {
		JOptionPane.showMessageDialog(null, " 21.06.06 Update / Graphics_Editor ", "Help", JOptionPane.INFORMATION_MESSAGE);
	}
	
	private void help() throws IOException {
		Desktop.getDesktop().open(pdfFile);
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EHelpMenuItem eMenuItem = EHelpMenuItem.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eHelp:
				try {
					help();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case eInfo:
				info();
				break;
			default:
				break;
			}
		}

	}

}
