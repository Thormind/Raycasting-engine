package mathematic;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;

// Contexte de r�alisation: cours B65 - Projet synth�se
//
// Description:				
// Classe repr�sentant un carr�. Utilis� pour les cases de la grille de la vue 2D.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de cr�ation :		2020/05/02
// Auteur :					Fr�d�ric B�langer

public class Square extends Rectangle{
	public Square() {}
	
	public Square(Point topLeft, int width, Color color) {
		super(topLeft, width, width, color);
	}
	
	public Square(Point topLeft, int width, Color drawColor, Color fillColor) {
		super(topLeft, width, width, drawColor, fillColor);
	}
	
}
