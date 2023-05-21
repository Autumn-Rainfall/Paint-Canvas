package frame;

import java.awt.event.ActionListener;

import javax.swing.JMenuBar;

import main.GConstants.EMenu;
import menu.GColorMenu;
import menu.GEditMenu;
import menu.GFileMenu;
import menu.GHelpMenu;

public class GMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private GFileMenu fileMenu;
	private GEditMenu editMenu;
	private GHelpMenu helpMenu;
	private GColorMenu colorMenu;
	
	public GMenuBar(ActionListener themeActionListener) {
		this.fileMenu = new GFileMenu(EMenu.eFile.getText());
		this.add(this.fileMenu);
		
		this.editMenu = new GEditMenu(EMenu.eEdit.getText());
		this.add(this.editMenu);
		
		this.colorMenu = new GColorMenu(EMenu.eColor.getText(), themeActionListener);
		this.add(this.colorMenu);
		
		this.helpMenu = new GHelpMenu(EMenu.eHelp.getText());
		this.add(this.helpMenu);
	}

//	public GMenuBar() {
//		for (EMenu eMenu: EMenu.values()) {
//			JMenu menu = new GFileMenu(eMenu.getText());
//			this.add(menu);
//		}
//	}

	public void setAssociation(GPanel panel) {
		this.fileMenu.setAssociation(panel);
		this.editMenu.setAssociation(panel);
		this.colorMenu.setAssociation(panel);
		this.helpMenu.setAssociation(panel);
	}
	
	public void windowExit() {
		this.fileMenu.exitProgram();

	}
}
