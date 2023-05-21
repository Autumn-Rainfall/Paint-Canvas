package shapeTools;

import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;

import main.GConstants.EDrawingStyle;

public class GPolygon extends GShapeTool {
	private static final long serialVersionUID = 1L;
	
	public GPolygon() {
		super(EDrawingStyle.eNPointDrawing);
		this.shape = new Polygon();
	}
	
	public Object clone() {
		GShapeTool cloned = null;
		cloned = (GShapeTool) super.clone();
		Polygon polygon = new Polygon();
		// this.shape => polygon value copy
		// polygon.add(this.shape.x[i], this.shape.y[i]);
		for (int i = 0; i < polygon.npoints; i++) {
			((Polygon)this.shape).addPoint(polygon.xpoints[i], polygon.ypoints[i]);
		}
//		cloned.shape = polygon;
		cloned.shape = (Shape)this.shape;
		return cloned;
	}
	
	@Override
	public void setInitPoint(int x, int y) {
		this.shape = new Polygon();
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
		polygon.addPoint(x, y);
	}

	public void setIntermediatePoint(int x, int y) {
		Polygon polygon = (Polygon) this.shape;
		polygon.addPoint(x, y);
	}
	
	@Override
	public void setFinalPoint(int x, int y) {		
	}

	@Override
	public void movePoint(int x, int y) {	
		Polygon polygon = (Polygon) this.shape;
		polygon.xpoints[polygon.npoints-1] = x;
		polygon.ypoints[polygon.npoints-1] = y;
	}

	@Override
	public void setText(Graphics2D graphics2d, int x, int y, String input) {
		
	}

}
