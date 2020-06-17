package model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import mathematic.Circle;
import mathematic.MathTools;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe représentant le héro du jeu. Elle va éventuellement contenir toutes les caractéristiques du joueur:
// inventaire, point de vie, vitesse, distance de vue, force, etc.
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class Player {
	
	private Point position;
	private Circle head;
	private Color color;
	private int headRay;
	
	public Player() {}
	public Player(Point position) {
		this.position = position;
	}
	public Player(Point position,Color color, int headRay) {
		this(position);
		this.color = color;
		this.headRay = headRay;
		setupHead();	
	}

	public Point getPosition() {
		return position;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void paint(Graphics g) {
		head.fill(g);
	}
	
	private void setupHead() {
		head = new Circle(position, headRay, color);
	}
	
}
