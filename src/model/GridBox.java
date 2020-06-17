package model;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import mathematic.Square;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// scription:				
// Cette classe représente une case de la grille.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class GridBox extends Square{
	public GridBox() {}
	public GridBox(Point topLeft, int width, Color color) {
		super(topLeft, width, color);
	}
}
