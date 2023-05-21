package shapeTools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

import main.GConstants.EDrawingStyle;

public class GPen extends GShapeTool {
	// attributes
	private static final long serialVersionUID = 1L;
	// components

	private Point oldPoint;

	public GPen() {
		super(EDrawingStyle.e1PointDrawing);
		this.oldPoint = new Point(0, 0);
		this.shape = new Line2D.Double();
	}

	public Object clone() {
		GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((Line2D.Double) (this.shape)).clone();
		return cloned;
	}

	// methods
	public void drawLine(Point p1, Point p2) {
		Line2D line = (Line2D) this.shape;
		line.setLine(p1.x, p1.y, p2.x, p2.y);
	}

	@Override
	public void setInitPoint(int x, int y) {
		oldPoint = new Point(x, y);
	}

	@Override
	public void setFinalPoint(int x, int y) {

	}

	@Override
	public void movePoint(int x, int y) {
		Point newPoint = new Point(x, y);
		drawLine(oldPoint, newPoint);
		oldPoint = newPoint;
	}

	@Override
	public void setText(Graphics2D graphics2d, int x, int y, String input) {
		// TODO Auto-generated method stub

	}

}
