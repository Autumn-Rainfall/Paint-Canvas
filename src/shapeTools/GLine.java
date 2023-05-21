package shapeTools;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.Line2D;

import main.GConstants.EDrawingStyle;

public class GLine extends GShapeTool {
	// attributes
	private static final long serialVersionUID = 1L;

	private Point point;

	public GLine() {
		super(EDrawingStyle.e2PointDrawing);
		this.point = new Point(0, 0);
		this.shape = new Line2D.Double();
	}

	public Object clone() {
		GShapeTool cloned = (GShapeTool) super.clone();
		cloned.shape = (Shape) ((Line2D.Double)(this.shape)).clone();
		return cloned;
	}

	@Override
	public void setInitPoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		this.point = new Point(x, y);
		line.setLine(x, y, x, y);
	}

	@Override
	public void setFinalPoint(int x, int y) {
	}

	@Override
	public void movePoint(int x, int y) {
		Line2D line = (Line2D) this.shape;
		line.setLine(point.x, point.y, x, y);
	}

	@Override
	public void setText(Graphics2D graphics2d, int x, int y, String input) {
		// TODO Auto-generated method stub
		
	}

}
