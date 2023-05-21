package shapeTools;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

import main.GConstants;
import main.GConstants.EAction;
import main.GConstants.EDrawingStyle;
import main.GConstants.EShapeTool;

abstract public class GShapeTool implements Serializable, Cloneable {
	// attributes
	private static final long serialVersionUID = 1L;

	public enum EAnchors {
		x0y0, x0y1, x0y2, x1y0, x1y2, x2y0, x2y1, x2y2, RR
	}

	private EDrawingStyle eDrawingStyle;
	private EShapeTool eShapeTool;
	protected Shape shape;
	private Ellipse2D[] anchors;;
	private boolean isSelected;
	private EAnchors selectedAnchor;
	private EAction eAction;
	private AffineTransform affineTransform;
	private Point2D x0y0, x0y1, x0y2, x1y0, x1y2, x2y0, x2y1, x2y2;
	private Color lineColor, faceColor;
	private int thickness;

	// working variables

	// constructors
	public GShapeTool(EDrawingStyle eDrawingStyle) {
		this.lineColor = Color.BLACK;
		this.faceColor = null;
		this.thickness = 1;
		
		this.anchors = new Ellipse2D.Double[EAnchors.values().length];
		for (EAnchors eAnchor : EAnchors.values()) {
			this.anchors[eAnchor.ordinal()] = new Ellipse2D.Double();
		}
		this.isSelected = false;
		this.eDrawingStyle = eDrawingStyle;
		this.selectedAnchor = null;
		this.affineTransform = new AffineTransform();
		this.affineTransform.setToIdentity();
	}

	public Object clone() {
		GShapeTool cloned = null;
		try {
			cloned = (GShapeTool) super.clone();
			for (EAnchors eAnchor : EAnchors.values()) {
				cloned.anchors[eAnchor.ordinal()]
						= (Ellipse2D) this.anchors[eAnchor.ordinal()].clone();
				}
		cloned.affineTransform = (AffineTransform) this.affineTransform.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cloned;
	}
	
	// getters & setters
	public EDrawingStyle getDrawingStyle() {
		return this.eDrawingStyle;
	}
	
	public EShapeTool getShapeTool() {
		return this.eShapeTool;
	}
	
	public Shape getShape() {
		return this.affineTransform.createTransformedShape(this.shape);
	}
	
	public EAction getAction() {
		return this.eAction;
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public EAnchors getSelectedAnchor() {
		return this.selectedAnchor;
	}

	// methods
	public EAction containes(int x, int y) {
		this.eAction = null;
		if (this.isSelected) {
			for (int i = 0; i < this.anchors.length - 1; i++) {
				if (this.affineTransform.createTransformedShape(
						this.anchors[i]).contains(x, y)) {
					this.selectedAnchor = EAnchors.values()[i];
					this.eAction = EAction.eResize;
				}
			}
			if (this.affineTransform.createTransformedShape(
					this.anchors[EAnchors.RR.ordinal()]).contains(x, y)) {
				this.eAction = EAction.eRotate;
			}
		}
		if (this.affineTransform.createTransformedShape(this.shape).contains(x, y)) {
			this.eAction = EAction.eMove;
		}
		return this.eAction;
	}

	public void setSelected(boolean isSelected) {
//			if (this.isSelected) {
//				drawAnchors(graphics);
//			} else {
//				// erase
//			}
		this.isSelected = isSelected;
	}
	
	public void initTransform (Graphics2D graphics2d, int x, int y) {
		
	}
	
	public void move(Graphics2D graphics2d, double dx, double dy) {
		this.draw(graphics2d);
		this.affineTransform.translate(dx, dy);
		this.draw(graphics2d);
	}
	
	public void resize(Graphics2D graphics2d, Point pStart, Point pEnd) {
		this.draw(graphics2d);
		Point2D resizeOrigin = this.getResizeAnchor();
		
		move(graphics2d, resizeOrigin.getX(), resizeOrigin.getY());
		Point2D resizeFactor = resizeFactor(pStart, pEnd);
		this.affineTransform.scale(resizeFactor.getX(), resizeFactor.getY());
		move(graphics2d, -resizeOrigin.getX(), -resizeOrigin.getY());
		
		this.draw(graphics2d);
	}
	
	private Point getResizeAnchor() {
		Point resizeAnchor = new Point();
		resizeAnchor.setLocation(getAnchor(getSelectedAnchor()));
		return resizeAnchor;
	}
	
	private Point2D resizeFactor(Point2D previousP, Point2D currentP) {
		double px = previousP.getX();
		double py = previousP.getY();
		double cx = currentP.getX();
		double cy = currentP.getY();
		double deltaW = 0;
		double deltaH = 0;
		switch (getSelectedAnchor()) {
		case x2y1:
			deltaW = cx - px;
			deltaH = 0;
			break;
		case x0y1:
			deltaW = -(cx - px);
			deltaH = 0;
			break;
		case x1y2:
			deltaW = 0;
			deltaH = cy - py;
			break;
		case x1y0:
			deltaW = 0;
			deltaH = -(cy - py);
			break;
		case x2y2:
			deltaW = cx - px;
			deltaH = cy - py;
			break;
		case x2y0:
			deltaW = cx - px;
			deltaH = -(cy - py);
			break;
		case x0y2:
			deltaW = -(cx - px);
			deltaH = cy - py;
			break;
		case x0y0:
			deltaW = -(cx - px);
			deltaH = -(cy - py);
			break;
		default:
			break;
		}
		double currentW = getWidth();
		double currentH = getHeight();
		double xFactor = 0.0;
		if (currentW > 1.0)
			xFactor = (1.0 + deltaW / currentW);
		double yFactor = 0.0;
		if (currentH > 1.0)
			yFactor = (1.0 + deltaH / currentH);
		return new Point.Double(xFactor, yFactor);
	}
	
	public void setAnchor(int x0, int x1, int x2, int y0, int y1, int y2) {
		this.x0y0.setLocation(x0, y0);
		this.x0y1.setLocation(x0, y1);
		this.x0y2.setLocation(x0, y2);
		this.x1y0.setLocation(x1, y0);
		this.x1y2.setLocation(x1, y2);
		this.x2y0.setLocation(x2, y0);
		this.x2y1.setLocation(x2, y1);
		this.x2y2.setLocation(x2, y2);

	}
	
	public double getWidth() {
		return this.shape.getBounds2D().getWidth();
	}
	public double getHeight() {
		return this.shape.getBounds2D().getHeight();
	}
	public double getCenterX() {
		return this.shape.getBounds2D().getCenterX();
	}
	public double getCenterY() {
		return this.shape.getBounds2D().getCenterY();
	}
	
	public Point2D getAnchor(EAnchors point) {
		Point2D pt = null;
		
		if(point == EAnchors.x0y0) {
			pt = x2y2;
		} else if (point == EAnchors.x0y1) {
			pt = x2y1;
		} else if (point == EAnchors.x0y2) {
			pt = x2y0;
		} else if (point == EAnchors.x1y0) {
			pt = x1y2;
		} else if (point == EAnchors.x1y2) {
			pt = x1y0;
		} else if (point == EAnchors.x2y0) {
			pt = x0y2;
		} else if (point == EAnchors.x2y1) {
			pt = x0y1;
		} else if (point == EAnchors.x2y2) {
			pt = x0y0;
		}
		return pt;
	}
	
	public void rotate(Graphics2D graphics2d, Point pStart, Point pEnd) {
		this.draw(graphics2d);
		
		double centerX = this.shape.getBounds().getCenterX();
		double centerY = this.shape.getBounds().getCenterY();
		// 사각형을 둘러싼 중심점
		
		double startAngle = Math.toDegrees(
				Math.atan2(centerX - pStart.x, centerY - pStart.y));
		double endAngle = Math.toDegrees(
				Math.atan2(centerX - pEnd.x, centerY - pEnd.y));
		
		double rotationAngle = startAngle - endAngle;
		if (rotationAngle < 0) {
			rotationAngle += 360;
		}
		this.affineTransform.rotate(Math.toRadians(rotationAngle), centerX, centerY);
		
		this.draw(graphics2d);

	}

	private void drawAnchors(Graphics2D graphics) {
		int wAnchor = GConstants.wAnchor;
		int hAnchor = GConstants.hAnchor;

		Rectangle rectangle = this.shape.getBounds();
		int x0 = rectangle.x - wAnchor / 2;
		int x1 = rectangle.x - wAnchor / 2 + (rectangle.width) / 2;
		int x2 = rectangle.x - wAnchor / 2 + rectangle.width;
		int y0 = rectangle.y - hAnchor / 2;
		int y1 = rectangle.y - hAnchor / 2 + (rectangle.height) / 2;
		int y2 = rectangle.y - hAnchor / 2 + rectangle.height;

		this.anchors[EAnchors.x0y0.ordinal()].setFrame(x0, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x0y1.ordinal()].setFrame(x0, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.x0y2.ordinal()].setFrame(x0, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.x1y0.ordinal()].setFrame(x1, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x1y2.ordinal()].setFrame(x1, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y0.ordinal()].setFrame(x2, y0, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y1.ordinal()].setFrame(x2, y1, wAnchor, hAnchor);
		this.anchors[EAnchors.x2y2.ordinal()].setFrame(x2, y2, wAnchor, hAnchor);
		this.anchors[EAnchors.RR.ordinal()].setFrame(x1, y0 - 40, wAnchor, hAnchor);

		for (EAnchors eAnchor : EAnchors.values()) {
			Color color = graphics.getColor();
			graphics.setColor(Color.WHITE);
			graphics.fill(this.affineTransform.createTransformedShape(
					this.anchors[eAnchor.ordinal()]));
			graphics.setColor(color);
			graphics.draw(this.affineTransform.createTransformedShape(
					this.anchors[eAnchor.ordinal()]));
		}
	}

	public void draw(Graphics2D graphics2d) {
		if (this.faceColor != null) {
			graphics2d.setColor(faceColor);
			graphics2d.fill(this.affineTransform.createTransformedShape(this.shape));
		}
		graphics2d.setColor(lineColor);
		graphics2d.setStroke(new BasicStroke(thickness, BasicStroke.CAP_BUTT, 0));
		graphics2d.draw(this.affineTransform.createTransformedShape(this.shape));
		if (isSelected) {
			this.drawAnchors(graphics2d);
		}
	}

	public void animate(Graphics2D graphics2d, int x, int y) {
		this.draw(graphics2d);
		this.movePoint(x, y);
		this.draw(graphics2d);
	}
	
	public void dragLine (Graphics2D graphics2d, int x, int y) {
		this.draw(graphics2d);
		this.movePoint(x, y);
	}
	
	public void setLineColor(Color lineColor) {
		this.lineColor = lineColor;
		}
	
	public void setFaceColor(Color faceColor) {
		this.faceColor = faceColor;
		}
	
	public void setLineThickness(int thickness) {
		this.thickness = thickness;
	}

	// interface

	public abstract void setInitPoint(int x, int y);

	public void setIntermediatePoint(int x, int y) {
	}

	public abstract void setFinalPoint(int x, int y);

	public abstract void movePoint(int x, int y);
	
	public abstract void setText(Graphics2D graphics2d,int x, int y, String input);

}
