package mathematic;

import java.awt.Color;
import java.awt.Graphics;

// Contexte de r�alisation: cours B65 - Projet synth�se
//
// Description:				
// Classe abstraite servant � la conception de formes g�om�triques.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de cr�ation :		2020/05/02
// Auteur :					Fr�d�ric B�langer

public abstract class GeometricShape {
	protected Color drawColor;
	protected Color fillColor;
	
	public GeometricShape() {}
	
	public GeometricShape(Color color) {
		this.drawColor = color;
		this.fillColor = color;
	}
	
	public GeometricShape(Color drawColor, Color fillColor) {
		this.drawColor = drawColor;
		this.fillColor = fillColor;
	}
	
	public void setDrawColor (Color drawColor) {
		this.drawColor = drawColor;
	}
	
	public void setFillColor (Color fillColor) {
		this.fillColor = fillColor;
	}
	
	public void setColors(Color color) {
		this.drawColor = color;
		this.fillColor = color;
	}
	
	public abstract void translate(double angle, double distance);
	
	public void draw(Graphics g) {
		g.setColor(drawColor);
	}
	
	public void fill(Graphics g)  {
		g.setColor(fillColor);
	}
}
