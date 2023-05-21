package Transformer;

import java.awt.Graphics2D;
import java.awt.Point;

import shapeTools.GShapeTool;

public class GResizer extends GTransformer {

	public GResizer(GShapeTool selectedShape) {
		super(selectedShape);
	}

	@Override
	public void initTransforming(Graphics2D graphics2d, int x, int y) {
		this.selectedShape.initTransform(graphics2d, x, y);
		this.px = x;
		this.py = y;
	}

	@Override
	public void keepTransforming(Graphics2D graphics2d, int x, int y) {
		this.selectedShape.resize(graphics2d, new Point(px, py), new Point(x, y));
		this.px = x;
		this.py = y;
	}

}
