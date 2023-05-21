package menu;

import java.util.Vector;

import shapeTools.GShapeTool;

public class GUndoStack {

	private static final int maxIndex = 10;
	private int bottomIndex;
	private int topIndex;
	private int size;

	private Vector<Vector<GShapeTool>> elements;

	public GUndoStack() {
		this.bottomIndex = 0;
		this.topIndex = 0;
		this.size = 0;
		this.elements = new Vector<Vector<GShapeTool>>();
		for (int i = 0; i < maxIndex; i++) {
			this.elements.add(new Vector<GShapeTool>());
		}
	}

	public void push(Vector<GShapeTool> shapes) {
		this.topIndex++;
		this.elements.set(this.topIndex % maxIndex, shapes);
		if ((this.topIndex - this.bottomIndex) == maxIndex) {
			this.bottomIndex++;
		} else {
			this.size++;
		}
	}

	public void deleteUndo() {
		this.topIndex--;
	}

	public void insertRedo() {
		this.topIndex++;
	}

	public Vector<GShapeTool> undo() {
		Vector<GShapeTool> undoElement = null;
		if (this.topIndex >= this.bottomIndex) {
			if (this.topIndex > 1) {
				this.topIndex--;
				undoElement = this.elements.get(this.topIndex % maxIndex - 1);
			}
		}
		return undoElement;
	}

	public Vector<GShapeTool> redo() {
		Vector<GShapeTool> redoElement = null;
		if ((this.topIndex - this.bottomIndex) < this.size) {
			redoElement = this.elements.get(this.topIndex % maxIndex);
			this.topIndex++;
		} else if ((this.topIndex - this.bottomIndex) >= this.size) {
			redoElement = this.elements.get(this.topIndex % maxIndex - 1);
		}
		return redoElement;
	}

}
