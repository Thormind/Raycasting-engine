package mathematic;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe représentant un carré. Utilisé pour les cases de la grille de la vue 2D.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class Square extends Rectangle{
	public Square() {}
	
	public Square(Point topLeft, int width, Color color) {
		super(topLeft, width, width, color);
	}
	
	public Square(Point topLeft, int width, Color drawColor, Color fillColor) {
		super(topLeft, width, width, drawColor, fillColor);
	}
	
}
