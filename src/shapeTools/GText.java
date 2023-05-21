package shapeTools;

import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Shape;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.GConstants.EDrawingStyle;

public class GText extends GShapeTool {
	private static final long serialVersionUID = 1L;

	JTextField txtField = new JTextField();
	JTextArea txtArea = new JTextArea();

	public GText() {
		super(EDrawingStyle.eTextDrawing);
		Graphics2D graphics = (Graphics2D) this.shape;
		this.shape = (Shape) graphics;
	}
	
	public Object clone() {
		GShapeTool cloned = (GShapeTool) super.clone();
		Graphics2D graphics = (Graphics2D) this.shape;
		cloned.shape = (Shape) graphics;
		return cloned;
	}

	public void setText(Graphics2D graphics, int x, int y, String input) {
		graphics.setFont(new Font(Font.SERIF, Font.PLAIN, 50));
		graphics.drawString(input, x, y);
	}

	@Override
	public void setInitPoint(int x, int y) {
	}

	@Override
	public void setFinalPoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
	}

}
