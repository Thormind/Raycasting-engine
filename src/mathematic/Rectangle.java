package mathematic;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe représentant un rectangle. Utilisé pour plusieurs éléments du programmes comme la grille de la vue 2D ou encore les colonnes de pixels de la vue 3D.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class Rectangle extends GeometricShape{
	protected Point topLeft;
	protected Point bottomRight;
	
	public Rectangle() {
		super(Color.WHITE);
		this.topLeft = new Point(0,0);
		this.bottomRight = new Point(0,0);
		
	}
	
	public Rectangle(Point topLeft, Point bottomRight) {
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	public Rectangle(Point topLeft, Point bottomRight, Color color) {
		super(color);
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	public Rectangle(Point topLeft, int width, int height, Color color) {
		this(topLeft, new Point(topLeft.x+width,topLeft.y+height), color);
	}
	
	public Rectangle(Point topLeft, Point bottomRight, Color drawColor, Color fillColor) {
		super(drawColor, fillColor);
		this.topLeft = topLeft;
		this.bottomRight = bottomRight;
	}
	
	public Rectangle(Point topLeft, int width, int height, Color drawColor, Color fillColor) {
		this(topLeft, new Point(topLeft.x+width,topLeft.y+height), drawColor, fillColor);
	}
	
	public Point getTopLeft() {
		return topLeft;
	}
	
	public Point getBottomRight() {
		return bottomRight;
	}
	
	public Color getFillColor() {
		return fillColor;
	}	
	
	public int getWidth() {
		return bottomRight.x - topLeft.x;
	}	
	
	public int getHeight() {
		return bottomRight.y - topLeft.y;
	}
	
	public void setTopLeft(Point topLeft) {
		this.topLeft = topLeft;
	}
	
	public void setBottomRight(Point bottomRight) {
		this.bottomRight = bottomRight;
	}
	
	public void translate(double angle, double distance) {
		translateTopLeft(angle, distance);
		translateBottomRight(angle, distance);
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		g.drawPolygon(getXForGraphics(), getYForGraphics(),4);
	}
	
	public void fill(Graphics g) {
		super.fill(g);
		g.fillPolygon(getXForGraphics(), getYForGraphics(),4);
	}
	
	private int [] getXForGraphics() {
		int tlx = topLeft.x;
		int brx = bottomRight.x;
		return new int [] {tlx, brx, brx, tlx, tlx};
	}
	
	private int [] getYForGraphics() {
		int tly = topLeft.y;
		int bry = bottomRight.y;
		return new int [] {tly , tly, bry, bry, tly};
	}
	
	private void translateTopLeft(double angle, double distance) {
		topLeft = MathTools.translatePoint(topLeft, angle, distance);
	}
	
	private void translateBottomRight(double angle, double distance) {
		bottomRight = MathTools.translatePoint(bottomRight, angle, distance);
	}
}
