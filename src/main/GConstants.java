package main;

import java.awt.Dimension;
import java.awt.Point;

import shapeTools.GLine;
import shapeTools.GOval;
import shapeTools.GPen;
import shapeTools.GPolygon;
import shapeTools.GRectangle;
import shapeTools.GRoundRectangle;
import shapeTools.GShapeTool;
import shapeTools.GText;

public class GConstants {
	public static class CFrame {
		public final static Point point = new Point(200, 300);
		public final static Dimension dimension = new Dimension(400, 600);
	}
	
	public enum EDrawingStyle {
		e2PointDrawing,
		eNPointDrawing,
		eTextDrawing,
		e1PointDrawing
	}
	
	public final static int wAnchor = 10;
	public final static int hAnchor = 10;

	public enum EAction {
		eDraw, eMove, eResize, eRotate, eShear
	}

	public enum EShapeTool {

		eRectangle(new GRectangle(), "Rectangle", "src/graphicsEditorA/Image/Rect.jpg","src/graphicsEditorA/Image/Rect.jpg"), // "eRectangle"
		// int variable; => "variable" (String 문자열)
		eRoundRectangle(new GRoundRectangle(), "RoundRectangle", "src/graphicsEditorA/Image/RoundRect.jpg","src/graphicsEditorA/Image/RoundRect.jpg"),
		eOval(new GOval(), "Oval", "src/graphicsEditorA/Image/Oval.jpg","src/graphicsEditorA/Image/Oval.jpg"), 
		eLine(new GLine(), "Line", "src/graphicsEditorA/Image/Line.jpg","src/graphicsEditorA/Image/Line.jpg"),
		ePolygon(new GPolygon(), "Polygon", "src/graphicsEditorA/Image/Polygon.jpg","src/graphicsEditorA/Image/Polygon.jpg"),
		ePen(new GPen(), "Pen", "src/graphicsEditorA/Image/Pen.jpg","src/graphicsEditorA/Image/Pen.jpg"),
		eText(new GText(), "Text", "src/graphicsEditorA/Image/Text.jpg","src/graphicsEditorA/Image/Text.jpg");

		private GShapeTool shapeTool;
		private String text;
		private String iconImage;
		private String iconSelect;
		
		private EShapeTool(GShapeTool shapeTool, String text, String iconImage, String iconSelect) {
			this.shapeTool = shapeTool;
			this.text = text;
			this.iconImage = iconImage;
			this.iconSelect = iconSelect;
		}

		public String getIconImage() {
			return this.iconImage;
		}

		public String getIconSelect() {
			return this.iconSelect;
		}
		
		public GShapeTool getShapeTool() {
			return this.shapeTool;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EMenu {
		eFile("File"),
		eEdit("Edit"),
		eColor("Color"),
		eHelp("Help");
		private String text;
		private EMenu (String text) {
			this.text = text;
		}
		public String getText() {
			return this.text;
		}
	}
	
	public enum EHelpMenuItem {
		eHelp("도움말", 'H'),
		eInfo("정보", 'I');
		
		private String text;
		private char key;

		private EHelpMenuItem(String text, char key) {
			this.text = text;
			this.key = key;
		}
		public String getText() {
			return this.text;
		}
		public char getKey() {
			return this.key;
		}
	}
	
	public enum EFileMenuItem {
		eNew("새로 만들기", 'N'),
		eOpen("열기", 'O'),
		eSave("저장", 'S'),
		eSaveAs("다른 이름으로 저장", 'D'),
		ePrint("프린트", 'P'),
		eExit("나가기", 'E');
		
		private String text;
		private char key;

		private EFileMenuItem(String text, char key) {
			this.text = text;
			this.key = key;
		}
		public String getText() {
			return this.text;
		}
		public char getKey() {
			return this.key;
		}
	}
	
	public enum EEditMenuItem {
		eRedo("Redo", 'Y'),
		eUndo("Undo", 'Z'),
		eCut("Cut", 'X'),
		eCopy("Copy", 'C'),
		ePaste("Paste", 'V'),
		eDelete("Delete", 'D'),
		eGroup("Group", 'G'),
		eUnGroup("UnGroup", 'H'),
		eClear("Clear", 'A');

		private String text;
		private char key;
		
		private EEditMenuItem(String text, char key) {
			this.text = text;
			this.key = key;
		}
		public String getText() {
			return this.text;
		}
		public char getKey() {
			return this.key;
		}
	}
	
	public enum EColorMenuItem {
		ePanelColor("배경색", 'B'),
		eFaceColor("면색", 'F'),
		eLineColor("선색", 'L');
		
		private String text;
		private char key;

		private EColorMenuItem(String text, char key) {
			this.text = text;
			this.key = key;
		}
		public String getText() {
			return this.text;
		}
		public char getKey() {
			return this.key;
		}
	}
	
	public enum EThemeMenu {
		eTheme("테마", "");
		
		private String text;
		private String theme;
		
		private EThemeMenu(String text, String theme) {
			this.text = text;
			this.theme = theme;
		}
		
		public String getText() {
			return this.text;
		}
		
		public String getTheme() {
			return this.theme;
		}

	}
	
	public enum EThemeItem{
		theme1("Windows", "com.sun.java.swing.plaf.windows.WindowsLookAndFeel"),
		theme2("Metal", "javax.swing.plaf.metal.MetalLookAndFeel"),
		theme3("Motif", "com.sun.java.swing.plaf.motif.MotifLookAndFeel"),
		theme4("Nimbus", "javax.swing.plaf.nimbus.NimbusLookAndFeel"),
		theme5("Quaqua", "ch.randelshofer.quaqua.QuaquaLookAndFeel"),
		theme6("Liquid", "com.birosoft.liquid.LiquidLookAndFeel"),
		theme7("InfoNode", "net.infonode.gui.laf.InfoNodeLookAndFeel"),
		theme8("JTattoo1", "com.jtattoo.plaf.smart.SmartLookAndFeel"), 
		theme9("JTattoo2", "com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		
		private String text;
		private String theme;
		
		private EThemeItem(String text,String theme) {
			this.text = text;
			this.theme = theme;
		}
		
		public String getText() {
			return this.text;
		}
		
		public String getTheme() {
			return this.theme;
		}
	}
}
