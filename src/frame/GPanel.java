package frame;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Vector;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;

import Transformer.GMover;
import Transformer.GResizer;
import Transformer.GRotator;
import Transformer.GTransformer;
import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;
import menu.GUndoStack;
import shapeTools.GShapeTool;

public class GPanel extends JPanel {
	//////////////////////////////////////////////////

	// attributes
	private static final long serialVersionUID = 1L;

	// components
	private Vector<GShapeTool> shapes;
	private GMouseHandler mouseHandler;
	
	private GUndoStack undoStack;

	// associations

	// working objects
	private GShapeTool shapeTool;
	private GShapeTool selectedShape;
	private GTransformer transformer;
	private boolean bModified;
	private GShapeTool temp;
	
	JMenuItem redoItem = new JMenuItem("Redo");
	JMenuItem undoItem = new JMenuItem("Undo");

	///////////////////// ÇÔ¼ö///////////////////////
	// getters and setters
	public void setSelection(GShapeTool shapeTool) {
		this.shapeTool = shapeTool;
	}

	public Vector<GShapeTool> getShapes() {
		return this.shapes;
	}

	public void setShapes(Vector<GShapeTool> shapes) {
		this.shapes = shapes;
		this.repaint();
	}
	
	public boolean isModified() {
		return this.bModified;
	}
	
	public void setModified(boolean bModified) {
		this.bModified = bModified;
	}

	// constructors
	public GPanel() {
		this.shapes = new Vector<GShapeTool>();
		this.mouseHandler = new GMouseHandler();

		this.addMouseListener(this.mouseHandler);
		this.addMouseMotionListener(this.mouseHandler);
		this.addMouseWheelListener(this.mouseHandler);
		
		this.undoStack = new GUndoStack();
		
//		this.undoStack.undo();
		
		this.bModified = false;

	}

	public void initialize() {
		this.setBackground(Color.WHITE);
	}
	
	public void clearScreen() {
		this.shapes.clear();
		this.repaint();
	}
	
	private void clear() {
		for (GShapeTool shape: this.shapes) {
			shape.setSelected(false);
			this.repaint();
		}
	}
	
	// methods
	public Vector<GShapeTool> deepCopy(Vector<GShapeTool> original) {
		@SuppressWarnings("unchecked")
		Vector<GShapeTool> clonedShapes = 
				(Vector<GShapeTool>) this.shapes.clone();
		for (int i = 0; i < this.shapes.size(); i++) {
			clonedShapes.set(i, (GShapeTool) this.shapes.get(i).clone());
		}
		return clonedShapes;
	}
	
	public void setLineColor(Color color) {
		Graphics2D g2D = (Graphics2D) this.getGraphics();
		g2D.setXORMode(this.getBackground());
		if (this.selectedShape != null) {
			this.selectedShape.setLineColor(color);
		}
		this.selectedShape.draw(g2D);
	}

	public void setFaceColor(Color color) {
		if (this.selectedShape != null) {
			this.selectedShape.setFaceColor(color);
		}
	}
	
	public void undo() {
		if (this.shapes.size() > 1) {
		this.shapes = this.undoStack.undo();
		this.repaint();
		} else if  (this.shapes.size() == 1) {
			this.temp = shapes.lastElement();
			Vector<GShapeTool> tempVector = null;
			tempVector = this.shapes;
			tempVector.remove(temp);
			setShapes(tempVector);
			this.undoStack.deleteUndo();
		}
	}
	
	public void redo() {
		if (this.shapes.size() > 0) {
		this.shapes = this.undoStack.redo();
		this.repaint();
		} else if  (this.shapes.size() == 0) {
			this.shapes.add(temp);
			this.undoStack.insertRedo();
			repaint();
		}
	}
	
	public void paste() {
		Vector<GShapeTool> shapeclone = new Vector<GShapeTool>();
		for (GShapeTool shape : this.shapes) {
			GShapeTool clonedShape = (GShapeTool) shape.clone();
			shapeclone.add(clonedShape);
		} 
		Vector<GShapeTool> shapeVector = shapeclone;
		this.shapes.addAll(shapeVector);
		this.repaint();
	}
	
	public void copy() {
		if (this.selectedShape != null) {
			this.shapeTool = this.selectedShape;
		}
	}
	
	public void cut() {
		Vector<GShapeTool> selectedShapes = new Vector<GShapeTool>();
		if (this.selectedShape != null) {
			this.shapeTool = this.selectedShape;
			this.shapes.remove(this.selectedShape);
			this.shapes.addAll(selectedShapes);
			this.repaint();	
		}
	}
	
	public void delete() {
		Vector<GShapeTool> selectedShapes = new Vector<GShapeTool>();
		if (this.selectedShape != null) {
			this.shapeTool = this.selectedShape;
			this.shapes.remove(this.selectedShape);	
		}
	}
	
	public void changeCursor (int x, int y) {
		GShapeTool shape = onShape(x, y);
		if (shape == null)
			setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		else
			setCursor(new Cursor(Cursor.MOVE_CURSOR));
	}
	
	public void setLineThickness(int thickness) {
		if (this.selectedShape != null) {
			shapeTool.setLineThickness(thickness);
		}
	}
	
	public void paint(Graphics graphics) {
		super.paint(graphics);
		// graphics.drawRect(10, 10, 20, 20);
		// graphics.drawOval(30, 30, 40, 40);

		for (GShapeTool shape : this.shapes) {
			shape.draw((Graphics2D) graphics);
		}
	}

	private void setSelected(GShapeTool selectedShape) {
//		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		
		for (GShapeTool shape: this.shapes) {
			shape.setSelected(false);
		}
//		if (this.selectedShape != null) {
//			this.selectedShape.setSelected(graphics2d, false);
//		}
		this.selectedShape = selectedShape;
		this.selectedShape.setSelected(true);
		this.repaint();
	}

	private GShapeTool onShape(int x, int y) {
		for (GShapeTool shape : this.shapes) {
			EAction eAction = shape.containes(x, y);
			if (eAction != null) {
				return shape;
			}
		}
		return null;
	}
	
	private void setText(int x, int y) {
		Graphics2D graphics = (Graphics2D) this.getGraphics();
		String text = JOptionPane.showInputDialog("Please enter your text");
		if ((text != null) && (text.length() > 0)) {
			this.shapeTool.setText(graphics, x, y, text);
		} else {
			
		}
			
	}

	private void initDrawing(int x, int y) {
		// initDrawing
		this.selectedShape = (GShapeTool) this.shapeTool.clone();
		this.selectedShape.setInitPoint(x, y);
	}

	private void setIntermediatePoint(int x, int y) {
		this.selectedShape.setIntermediatePoint(x, y);
		// this.shapeTool.animate(graphics2d, x, y);
	}

	private void keepDrawing(int x, int y) {
		// exclusive or mode
		Graphics2D graphics2D = (Graphics2D) getGraphics();
		graphics2D.setXORMode(getBackground());
		// erase
		selectedShape.animate(graphics2D, x, y);
	}
	
	private void keepDrawingLine(int x, int y) {
		// exclusive or mode
		Graphics2D graphics2D = (Graphics2D) getGraphics();
		// erase
		selectedShape.dragLine(graphics2D, x, y);
	}

	private void finishDrawing(int x, int y) {
		// finishDrawing
		this.selectedShape.setFinalPoint(x, y);
		this.shapes.add(this.selectedShape);
		this.bModified = true;
		
		this.undoStack.push(this.deepCopy(this.shapes));
	}

	private void initTransforming(GShapeTool selectedShape, int x, int y) {
		
		this.selectedShape = selectedShape;
		EAction eAction = this.selectedShape.getAction();
		switch(eAction) {
		case eMove:
			this.transformer = new GMover(this.selectedShape);
			break;
		case eResize:
			this.transformer = new GResizer(this.selectedShape);
			break;
		case eRotate:
			this.transformer = new GRotator(this.selectedShape);
			break;
		default:
			break;
		}
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		
		this.transformer.initTransforming(graphics2d, x, y);
	}

	private void keepTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		
		this.transformer.keepTransforming(graphics2d, x, y);
	}

	private void finishTransforming(int x, int y) {
		Graphics2D graphics2d = (Graphics2D) this.getGraphics();
		graphics2d.setXORMode(this.getBackground());
		
		this.transformer.finishTransforming(graphics2d, x, y);
		
		this.setSelected(this.selectedShape);
		this.bModified = true;

		this.undoStack.push(this.deepCopy(this.shapes));

	}

	/////////////////////////////////////////////////
	// inner classes
	private class GMouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {

		private boolean isDrawing;
		private boolean isTransforming;

		public GMouseHandler() {
			this.isDrawing = false;
			this.isTransforming = false;
		}

		public void mousePressed(MouseEvent e) {
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (selectedShape == null) {
					if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					} else if (shapeTool.getDrawingStyle() == EDrawingStyle.e1PointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					}
				} else {
					initTransforming(selectedShape, e.getX(), e.getY());
					this.isTransforming = true;
				}
			}
		}

		public void mouseDragged(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					keepDrawing(e.getX(), e.getY());
				} else if (shapeTool.getDrawingStyle() == EDrawingStyle.e1PointDrawing) {
					keepDrawingLine(e.getX(), e.getY());				}
			} else if (this.isTransforming) {
				keepTransforming(e.getX(), e.getY());
			}
		}

		public void mouseReleased(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.e2PointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				} else if (shapeTool.getDrawingStyle() == EDrawingStyle.e1PointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				}
			} else if (this.isTransforming) {
				finishTransforming(e.getX(), e.getY());
				this.isTransforming = false;
			}
		}

		public void mouseMoved(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					keepDrawing(e.getX(), e.getY());
				}
			} else
				changeCursor(e.getX(), e.getY());
		}

		public void mouseClicked(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				if (e.getClickCount() == 1) {
					this.mouseLButton1Clicked(e);
				} else if (e.getClickCount() == 2) {
					this.mouseLButton2Clicked(e);
				}
			} else if (SwingUtilities.isRightMouseButton(e)) {
				if (e.getClickCount() == 1) {
					this.mouseRButton1Clicked(e);
				}
			}
		}

		public void mouseLButton1Clicked(MouseEvent e) {
			if (!isDrawing) {
				GShapeTool selectedShape = onShape(e.getX(), e.getY());
				if (onShape(e.getX(), e.getY()) == null) {
					clear();
					if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
						initDrawing(e.getX(), e.getY());
						this.isDrawing = true;
					} else if(shapeTool.getDrawingStyle() == EDrawingStyle.eTextDrawing){
						setText(e.getX(), e.getY());
						}
				} else {
					setSelected(selectedShape);
				}
			} else {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					setIntermediatePoint(e.getX(), e.getY());
				}
			}
		}

		public void mouseLButton2Clicked(MouseEvent e) {
			if (isDrawing) {
				if (shapeTool.getDrawingStyle() == EDrawingStyle.eNPointDrawing) {
					finishDrawing(e.getX(), e.getY());
					this.isDrawing = false;
				}
			}
		}

		public void mouseRButton1Clicked(MouseEvent e) {
			JPopupMenu popup = new JPopupMenu();
			popup.add(undoItem);
			popup.add(redoItem);
			popup.show(e.getComponent(), e.getX(), e.getY());
			
			undoItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					undo();
				}
			});
			
			redoItem.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					redo();
				}
			});
			
		}

		public void mouseEntered(MouseEvent arg0) {
		}

		public void mouseExited(MouseEvent arg0) {
		}

		public void mouseWheelMoved(MouseWheelEvent e) {
		}
	}
}
