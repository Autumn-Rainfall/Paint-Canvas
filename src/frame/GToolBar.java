package frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JSlider;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.GConstants.EShapeTool;

public class GToolBar extends JToolBar {
	// attributes
	private static final long serialVersionUID = 1L;

	// associations
	private GPanel panel;
	public JSlider slider;

	public GToolBar() {
		// initialize components
		ActionHandler actionHandler = new ActionHandler();

		for (EShapeTool eButton : EShapeTool.values()) {
			JButton button = new JButton(new ImageIcon());
			button.setActionCommand(eButton.toString());
			button.addActionListener(actionHandler);
			button.setIcon(new ImageIcon(eButton.getIconImage()));
			button.setSelectedIcon(new ImageIcon(eButton.getIconSelect()));
			
			switch (eButton.getText()) {
			case "Rectangle":
				button.setToolTipText("Rectangle");
				break;
			case "RoundRectangle":
				button.setToolTipText("RoundRectangle");
				break;
			case "Oval":
				button.setToolTipText("Oval");
				break;
			case "Line":
				button.setToolTipText("Line");
				break;
			case "Polygon":
				button.setToolTipText("Polygon");
				break;
			case "Pen":
				button.setToolTipText("Pen");
				break;
			case "Text":
				button.setToolTipText("Text");
				break;
			default:
				break;
			}
			
			button.addActionListener(new ActionListener() {
			      public void actionPerformed(ActionEvent evt) {
			    	  
			        button.setSelected(!button.isSelected());
//			        System.out.println("selecting");
			      }
			    }
			);
			this.add(button);
		}
		
		this.slider = new JSlider(JSlider.HORIZONTAL, 0, 20, 1);
		this.slider.setPaintLabels(true);
		this.slider.setPaintTicks(true);
		this.slider.setPaintTrack(true);
		this.slider.setMinorTickSpacing(1);
		this.slider.setMajorTickSpacing(5);
		this.slider.addChangeListener((ChangeListener) new thicknessListener());
		this.add(slider);
	}
	
	private void setLineThickness (Object e) {
		this.panel.setLineThickness(((JSlider) e).getValue());
	}

	public void initialize() {
		((JButton)(this.getComponent(EShapeTool.eRectangle.ordinal()))).doClick();
		
	}
	
	public void setAssociation(GPanel panel) {
		this.panel = panel;
	}

	private class ActionHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			EShapeTool eShapeTool = EShapeTool.valueOf(event.getActionCommand());
			panel.setSelection(eShapeTool.getShapeTool());
		}
	}
	
	private class thicknessListener implements ChangeListener {

		@Override
		public void stateChanged(ChangeEvent arg0) {
			setLineThickness(arg0.getSource());
		}
		
	}

}
