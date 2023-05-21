package menu;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import frame.GPanel;
import main.GConstants.EColorMenuItem;
import main.GConstants.EThemeItem;
import main.GConstants.EThemeMenu;

public class GColorMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components

	// associations
	private GPanel panel;

	public GColorMenu(String text, ActionListener themeActionListener) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		JMenu themeMenu = new JMenu(EThemeMenu.eTheme.getText());
		for (EColorMenuItem eColorMenuItem : EColorMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eColorMenuItem.getText());
			KeyStroke shortKey = KeyStroke.getKeyStroke(eColorMenuItem.getKey(), InputEvent.ALT_DOWN_MASK);
			menuItem.setActionCommand(eColorMenuItem.name());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(shortKey);
			this.add(menuItem);
			switch (eColorMenuItem.getText()) {
			case "배경색":
				menuItem.setToolTipText("PANEL COLOR");
				this.addSeparator();
				break;
			case "면색":
				menuItem.setToolTipText("FACE COLOR");
				break;
			case "선색":
				menuItem.setToolTipText("LINE COLOR");
				this.addSeparator();
				break;
			default:
				break;
			}
			this.add(themeMenu);
		}
		for (EThemeItem eThemeItem : EThemeItem.values()) {
			JMenuItem themeItem = new JMenuItem(eThemeItem.getText());
			themeItem.setActionCommand(eThemeItem.getTheme());
			themeItem.addActionListener(themeActionListener);
			themeMenu.add(themeItem);
		}
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private void changePanelColor() {
		Color color = JColorChooser.showDialog(null, EColorMenuItem.ePanelColor.name(), null);
		if (color != null) {
			this.panel.setBackground(color);
		}
	}
	
	public void changeLineColor() {
		Color color = JColorChooser.showDialog(null, EColorMenuItem.eLineColor.name(), null);
		if (color != null) {
			this.panel.setLineColor(color);
		}
	}

	public void changeFaceColor() {
		Color color = JColorChooser.showDialog(null, EColorMenuItem.eFaceColor.name(), null);
		if (color != null) {
			this.panel.setFaceColor(color);
		}
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EColorMenuItem eMenuItem = EColorMenuItem.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case ePanelColor:
				changePanelColor();
				break;
			case eFaceColor:
				changeFaceColor();
				break;
			case eLineColor:
				changeLineColor();
				break;
			default:
				break;
			}
		}

	}

}
