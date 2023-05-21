package shapeTools;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;

import main.GConstants.EDrawingStyle;

public class GRoundRectangle extends GShapeTool {
	// attributes
	private static final long serialVersionUID = 1L;

	// components

	// constructors
	public GRoundRectangle() {
		super(EDrawingStyle.e2PointDrawing);
		this.shape = new RoundRectangle2D.Double();
	}

	public Object clone() {
		GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((RoundRectangle2D.Double)(this.shape)).clone();
		return cloned;
	}

	// methods
	@Override
	public void setInitPoint(int x, int y) {
		RoundRectangle2D.Double rectangle = (RoundRectangle2D.Double) this.shape;
		rectangle.setFrame(x, y, 0, 0);
	}

	@Override
	public void setFinalPoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
		RoundRectangle2D.Double rectangle = (RoundRectangle2D.Double) this.shape;
		rectangle.setRoundRect(rectangle.x, rectangle.y, x - rectangle.x, y - rectangle.y, 30.0, 30.0);

	}

	@Override
	public void setText(Graphics2D graphics2d, int x, int y, String input) {
		// TODO Auto-generated method stub
		
	}

}
