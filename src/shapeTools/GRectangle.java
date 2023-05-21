package shapeTools;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

import main.GConstants.EDrawingStyle;

public class GRectangle extends GShapeTool {
	// attributes
	private static final long serialVersionUID = 1L;

	// components

	// constructors
	public GRectangle() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new Rectangle();
	}

	public Object clone() {
		GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((Rectangle)(this.shape)).clone();
		return cloned;
	}

	// methods
	@Override
	public void setInitPoint(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setLocation(x, y);
		rectangle.setSize(0, 0);
	}

	@Override
	public void setFinalPoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
		Rectangle rectangle = (Rectangle) this.shape;
		rectangle.setSize(x - rectangle.x, y - rectangle.y);
	}

	@Override
	public void setText(Graphics2D graphics2d, int x, int y, String input) {
		// TODO Auto-generated method stub
		
	}

}
