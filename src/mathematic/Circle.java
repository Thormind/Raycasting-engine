package mathematic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe représentant un cercle. Utilisée pour représenter le joueur sur la vue 2D.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class Circle extends GeometricShape{
	private Point center;
	private int ray;
	
	public Circle() {}
	
	public Circle(Point center, int ray, Color color) {
		this(center, ray, color, color);
		this.center = center;
		this.ray = ray;
	}
	
	public Circle(Point center, int ray, Color drawColor, Color fillColor) {
		super(drawColor, fillColor);
		this.center = center;
		this.ray = ray;
	}
	
	public Point getCenter() {
		return center;
	}
	
	public int getRay() {
		return ray;
	}
	
	public void setCenter(Point center) {
		this.center = center;
	}
	
	public void setRay(int ray) {
		this.ray = ray;
	}
	
	public void translate(double angle, double distance) {
		center = MathTools.translatePoint(center, angle, distance);
	}
	
	public void draw(Graphics g) {
		super.draw(g);
		g.drawOval(getTopLeftForGraphics().x, getTopLeftForGraphics().y, 2*ray, 2*ray);
	}
	
	public void fill(Graphics g) {
		super.fill(g);
		g.fillOval(getTopLeftForGraphics().x, getTopLeftForGraphics().y, 2*ray, 2*ray);
	}
	
	private Point getTopLeftForGraphics() {
		int topLeftX = center.x-ray;
		int topLeftY = center.y-ray;
		return new Point(topLeftX, topLeftY);
	}
	
}
