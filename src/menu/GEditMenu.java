package menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.EEditMenuItem;

public class GEditMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components

	// associations
	private GPanel panel;

	public GEditMenu(String text) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EEditMenuItem eEditMenuItem : EEditMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eEditMenuItem.getText());
			KeyStroke shortKey = KeyStroke.getKeyStroke(eEditMenuItem.getKey(), InputEvent.CTRL_DOWN_MASK);
			menuItem.setActionCommand(eEditMenuItem.name());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(shortKey);
			this.add(menuItem);
			switch (eEditMenuItem.getText()) {
			case "Redo":
				break;
			case "Undo":
				this.addSeparator();
				break;
			case "Cut":
				break;
			case "Copy":
				break;
			case "Paste":
				break;
			case "Delete":
				this.addSeparator();
				break;
			case "Group":
				break;
			case "UnGroup":
				this.addSeparator();
				break;
			case "Clear":
				break;
			default:
				break;
			}
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}
	
	private void redo() {
		this.panel.redo();
	}

	private void undo() {	
		this.panel.undo();
	}
	
	private void delete() {
		this.panel.delete();
	}
	
	private void paste() {
		this.panel.paste();
	}

	private void copy() {
		this.panel.copy();
	}

	private void cut() {
		this.panel.cut();	
	}
	

	private void clear() {
		this.panel.clearScreen();
	}

	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EEditMenuItem eMenuItem = EEditMenuItem.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eRedo:
				redo();
				break;
			case eUndo:
				undo();
				break;
			case eCut:
				cut();
				break;
			case eCopy:
				copy();
				break;
			case ePaste:
				paste();
				break;
			case eDelete:
				delete();
				break;
			case eGroup:
				break;
			case eUnGroup:
				break;
			case eClear:
				clear();
				break;
			default:
				break;
			}
		}

	}

}
