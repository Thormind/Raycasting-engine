package model;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import mathematic.Square;

// Contexte de r�alisation: cours B65 - Projet synth�se
//
// scription:				
// Cette classe repr�sente une case de la grille.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de cr�ation :		2020/05/02
// Auteur :					Fr�d�ric B�langer

public class GridBox extends Square{
	public GridBox() {}
	public GridBox(Point topLeft, int width, Color color) {
		super(topLeft, width, color);
	}
}
