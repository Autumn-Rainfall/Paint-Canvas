package Transformer;

import java.awt.Graphics2D;

import shapeTools.GShapeTool;

public abstract class GTransformer {

	protected GShapeTool selectedShape;
	protected int px, py;

	public GTransformer(GShapeTool selectedShape) {
		this.selectedShape = selectedShape;
	}

	public abstract void initTransforming(Graphics2D graphics2d, int x, int y);

	public abstract void keepTransforming(Graphics2D graphics2d, int x, int y);

	public void finishTransforming(Graphics2D graphics2d, int x, int y) {

	}

	public void continueTransforming(Graphics2D graphics2d, int x, int y) {

	}

}
