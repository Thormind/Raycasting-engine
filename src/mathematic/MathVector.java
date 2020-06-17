package mathematic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe représentant un vecteur 2D.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class MathVector {
	protected Point2D.Double p1;
	protected Point2D.Double p2;
	
	public MathVector() {}
	
	public MathVector(Point2D.Double p1, Point2D.Double p2) {
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public void rotateFromBase(double angle) {
		rotateP2(angle, p1);
	}
	
	public void rotateFromPoint(double angle, Point2D.Double rotationCenter) {
		rotateP1(angle, rotationCenter);
		rotateP2(angle, rotationCenter);
	}
	
	public void translate(double angle, double distance) {
		translateP1(angle, distance);
		translateP2(angle, distance);		
	}
	
	public double getLength() {
		return MathTools.calculDistanceTwoPoints(p1, p2);
	}
	
	public void draw(Graphics g, Color color) {
		int p1x = MathTools.doubleToInt(p1.x);
		int p1y = MathTools.doubleToInt(p1.y);
		int p2x = MathTools.doubleToInt(p2.x);
		int p2y = MathTools.doubleToInt(p2.y);
		g.setColor(color);
		g.drawLine(p1x, p1y, p2x, p2y);
	}
	
	public  Point2D.Double getP1() {
		return p1;
	}
	
	public  Point2D.Double getP2() {
		return p2;
	}
	
	public  void setP1(Point2D.Double p1) {
		this.p1 = p1;
	}
	
	public  void setP2(Point2D.Double p2) {
		this.p2 = p2;
	}
	
	private void rotateP1(double angle, Point2D.Double rotationCenter) {
		p1 = MathTools.rotatePointFromCenter(angle, p1, rotationCenter);
	}
	
	private void rotateP2(double angle, Point2D.Double rotationCenter) {
		p2 = MathTools.rotatePointFromCenter(angle, p2, rotationCenter);
	}
	
	private void translateP1(double angle, double distance) {
		p1 = MathTools.translatePointDbl(p1, angle, distance);
	}
	
	private void translateP2(double angle, double distance) {
		p2 = MathTools.translatePointDbl(p2, angle, distance);	
	}
	
}
