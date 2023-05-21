package menu;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import frame.GPanel;
import main.GConstants.EFileMenuItem;
import shapeTools.GShapeTool;

public class GFileMenu extends JMenu {
	private static final long serialVersionUID = 1L;

	// components

	// associations
	private GPanel panel;
	private File file;
	private String extension;

	public GFileMenu(String text) {
		super(text);
		ActionHandler actionHandler = new ActionHandler();
		for (EFileMenuItem eFileMenuItem : EFileMenuItem.values()) {
			JMenuItem menuItem = new JMenuItem(eFileMenuItem.getText());
			KeyStroke shortKey = KeyStroke.getKeyStroke(eFileMenuItem.getKey(), InputEvent.CTRL_DOWN_MASK);
			menuItem.setActionCommand(eFileMenuItem.name());
			menuItem.addActionListener(actionHandler);
			menuItem.setAccelerator(shortKey);
			this.add(menuItem);
		}
		this.file = null;
	}

	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private void openFile() {
		try {
			ObjectInputStream objectInputStream = new ObjectInputStream(
					new BufferedInputStream(new FileInputStream(this.file)));
			Vector<GShapeTool> shapes = (Vector<GShapeTool>) objectInputStream.readObject();
			this.panel.setShapes(shapes);
			objectInputStream.close();
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void saveFile() {
		File file = new File("test");
		
		if(!this.extension.endsWith(".dge")&&!this.extension.endsWith(".DGE")) {
			this.extension += ".dge";
		}
		File saveFile = new File(this.extension);
		
		try {
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(this.file)));
			objectOutputStream.writeObject(this.panel.getShapes());
			objectOutputStream.close();
			this.panel.setModified(false);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private boolean checkSaveOrNot() {
		boolean bCancel = true;
		if (this.panel.isModified()) {
			// save
			int reply = JOptionPane.showConfirmDialog(this.panel, "Do you want to save your changes before exiting?");
			if (reply == JOptionPane.OK_OPTION) {
				this.save();
				bCancel = false;
			} else if (reply == JOptionPane.NO_OPTION) {
				this.panel.setModified(false);
				bCancel = false;
			}
		} else {
			bCancel = false;
		}
		return bCancel;
	}

	private void nnew() {
		if (!checkSaveOrNot()) { // 상황 취소
			this.panel.clearScreen();
			this.file = null;
		}
	}

	private void open() {
		if (!checkSaveOrNot()) { // if not cancel
			JFileChooser chooser = new JFileChooser();
			int returnVal = chooser.showOpenDialog(this.panel);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				this.file = chooser.getSelectedFile();
				this.openFile();
			}
		}
	}

	private void save() {
		if (this.panel.isModified()) {
			if (this.file == null) {
				this.saveAs();
			}
		} else {
			this.saveFile();
		}
	}

	private void saveAs() {
		// save
		JFileChooser chooser = new JFileChooser(System.getProperty("user.home") + "/Desktop");
		
		chooser.setSelectedFile(this.file);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Drawing Graphics Extension", "dge");
		chooser.setFileFilter(filter);
		
		int returnVal = chooser.showSaveDialog(this.panel);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			this.file = chooser.getSelectedFile();
			this.saveFile();
		}
	}
	
	public void print(){
		Component component;
		component = this.panel;
		PrinterJob pj = PrinterJob.getPrinterJob();
		pj.setJobName("Print Component");
		pj.setPrintable(new Printable() {
			public int print(Graphics pg, PageFormat pf, int pageNum) {
				if(pageNum > 0) {
					return Printable.NO_SUCH_PAGE;
				}
				Graphics2D g2 = (Graphics2D) pg;
				g2.translate(pf.getImageableX(), pf.getImageableY());
				component.paint(g2);
				return Printable.PAGE_EXISTS;
			}
		});
		if(pj.printDialog()==false) {
			return;
		}
		try {
			pj.print();
		} catch (PrinterException e) {
			
		}
	}

	public void exitProgram() {
		if (!checkSaveOrNot()) {
			System.exit(0);
		}
	}
	
	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			EFileMenuItem eMenuItem = EFileMenuItem.valueOf(e.getActionCommand());
			switch (eMenuItem) {
			case eNew:
				nnew();
				break;
			case eOpen:
				open();
				break;
			case eSave:
				save();
				break;
			case eSaveAs:
				saveAs();
				break;
			case ePrint:
				print();
				break;
			case eExit:
				exitProgram();
				break;
			default:
				break;
			}
		}
	}

}
