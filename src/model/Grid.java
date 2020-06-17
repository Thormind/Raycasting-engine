package model;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import mathematic.MathTools;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Cette classe représente une grille dont chaque case est un objet. Gère également l'affichage sur la vue 2D. Utilisé pour former les cartes
// des niveaux.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class Grid {
	private Point position;
	private Dimension dimension;
	private int boxWidth;
	private int nbRow;
	private int nbColumn;
	private GridBox [][] boxes;
	
	public Grid() {}
	public Grid(Point position, int nbRow, int nbColumn, int boxWidth) {
		this.position = position;
		this.nbRow = nbRow;
		this.nbColumn = nbColumn;
		this.boxWidth = boxWidth;
		setup();
	}

	public void translate(double angle, double distance) {
		translatePosition(angle, distance);
		translateAllBox(angle, distance);
	}
	
	public GridBox getBox(GridPosition gridPos) {
		return boxes [gridPos.getRow()][gridPos.getColumn()];
	}
	
	public int getNbRow() {
		return nbRow;
	}	
	
	public int getNbColumn() {
		return nbColumn;
	}	
	
	public void draw(Graphics g) {
		paint(g,false);
	}
	
	public void fill(Graphics g) {
		paint(g,true);
	}
	
	private void setup() {
		calculDimension();
		setupBoxes();
	}
	
	private void calculDimension() {
		dimension = new Dimension(nbColumn * boxWidth,  nbRow * boxWidth);
	}
	
	private void setupBoxes() {
		boxes = new GridBox [nbRow][nbColumn];
		for(int r=0;r<nbRow;++r) {
			// We remove 1 from the box width because the first and last pixel must be included in the width
			// for example a 32 pixel box will have it's first pixel at x = 0 and it's last at x = 31 
			for(int c=0;c<nbColumn;++c) {
				Point topLeft = new Point(position.x+(c*(boxWidth-1)),position.y+(r*(boxWidth-1)));
				boxes[r][c]=new GridBox(topLeft,boxWidth-1,Color.WHITE);	
			}
		}
	}
	
	private void translatePosition(double angle, double distance) {
		position = MathTools.translatePoint(position, angle, distance); 
	}
	
	private void translateAllBox(double angle, double distance) {
		for(GridBox [] boxRow: boxes) {
			for( GridBox box: boxRow) {
				box.translate(angle, distance);
			}
		}
	}
	
	private void paint(Graphics g, boolean fill) {
		for(GridBox [] row:boxes) {
			for(GridBox box:row) {
				if(fill) {
					box.fill(g);
				} else {
					box.draw(g);
				}
			}
		}
	}
}
