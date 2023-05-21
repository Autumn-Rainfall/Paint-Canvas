package frame;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import main.GConstants.CFrame;

public class GFrame extends JFrame {
	// attributes
	private static final long serialVersionUID = 1L;

	// components (Aggregation Hierarchy의 자식들)
	private GPanel panel;
	private GToolBar toolBar;
	private GMenuBar menuBar;
	private WindowHandler windowHandler;
	private ActionListener themeActionListener;
	File file = new File("src/graphicsEditorA/sound_effect_1.wav");

	public GFrame() {
		// initialize attributes
		this.setLocation(CFrame.point);
		this.setSize(CFrame.dimension);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		setTitle("Graphics Editor");

		this.windowHandler = new WindowHandler();
		this.addWindowListener(windowHandler);
		this.themeActionListener = new ThemeActionHandler();

		// initialize components
		this.menuBar = new GMenuBar(this.themeActionListener);
		this.setJMenuBar(this.menuBar);
		LayoutManager layoutManager = new BorderLayout();
		this.getContentPane().setLayout(layoutManager);

		this.toolBar = new GToolBar();
		this.getContentPane().add(this.toolBar, BorderLayout.NORTH);

		this.panel = new GPanel();
		this.getContentPane().add(this.panel, BorderLayout.CENTER);

		// set associations
		this.menuBar.setAssociation(this.panel);
		this.toolBar.setAssociation(this.panel);
	}

	public void initialize() {
		this.toolBar.initialize();
		this.panel.initialize();
	}

	public void setTheme(String theme) {
		try {
			UIManager.setLookAndFeel(theme);
			SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
		}
	}

	public class ThemeActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent e1) {
			setTheme(e1.getActionCommand());
//			System.out.println(file.exists());
			try {
				AudioInputStream stream = AudioSystem.getAudioInputStream(file);
				Clip clip = AudioSystem.getClip();
				clip.open(stream);
				clip.start();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	private class WindowHandler implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosed(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowClosing(WindowEvent arg0) {
			menuBar.windowExit();
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowIconified(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void windowOpened(WindowEvent arg0) {
			// TODO Auto-generated method stub

		}

	}
}
