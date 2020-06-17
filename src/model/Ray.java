package model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import mathematic.MathTools;
import mathematic.MathVector;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe représentant un rayon de l'engin de raycasting et sont affichage sur la vue 2D.
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class Ray extends MathVector {
	// Walls and textures are divided in equal parts corresponding to a pixel column width in the 3d view.
	// The part of the wall hit by the ray will determine the part of the texture to use when drawing the pixel column.  
	private int wallPartIndex; 
	private double angle;
	private double distoCorrectLength;
	private GridPosition gridPosition; // grid position of the end of the ray (p2) used to evaluate wall hits
	// indicate if a ray hits an horizontal (hitX) or vertical(!hitX)  part of a wall. Wall hit on the x part will
	// be drawn a bit darker. This augment the 3d effect by adding some contrast. Also used when checking if it's
	// ok for the first or last ray hitting the same wall to not be at the beginning or the end of it. 
	private boolean hitX = false; 
	private Color color;
	private Color colorCorrect;
	// For texture mapping i am grouping the rays by the wall they hit. Sometime a ray is not hitting the fist or last part
	// of a wall but the texture has to be drawn from beginning or up to the end. Thats to preserve texture integrity and
	// make patterns more consistent. The 2 booleans are used to indicate if the first and last ray hitting a wall should
	// hit the first or last pixel column even if they are not.
	private boolean hitWallStart;
	private boolean hitWallEnd;
	
	public Ray() {}
	public Ray(Point2D.Double p1, Point2D.Double p2, double angle, GridPosition gridPosition) {
		this.p1 = p1;
		this.p2 = p2;
		this.angle = angle;
		this.gridPosition = gridPosition;
		this.wallPartIndex = 0;
	}
	
	public Ray(Point2D.Double p1, Point2D.Double p2, double angle, GridPosition gridPosition, Color color, Color colorCorrect) {
		this(p1, p2, angle, gridPosition);
		this.color = color;
		this.colorCorrect = colorCorrect;
	}
	public double getAngle() {
		return angle;
	}
	
	public GridPosition getGridPosition() {
		return gridPosition;
	}
	
	public int getWallPartIndex() {
		return wallPartIndex;
	}
	
	public double getDistoCorrectLength() {
		return distoCorrectLength;
	}	
	public boolean getHitX() {
		return hitX;
	}
	
	public boolean getHitWallStart() {
		return hitWallStart;
	}
	
	public boolean getHitWallEnd() {
		return hitWallEnd;
	}
	
	public void setWallPartIndex(int wallPartIndex) {
		this.wallPartIndex = wallPartIndex;
	}
	
	public void setAngle(double angle) {
		this.angle = angle;
	}
	
	public void setGridPosition(GridPosition gridPosition) {
		this.gridPosition = gridPosition;
	}
	
	public void setDistoCorrectLength(double length) {
		this.distoCorrectLength = length;
	}
	
	public void setHitX(boolean hitX) {
		this.hitX = hitX;
	}
	
	public void setHitWallStart(boolean hitWallStart) {
		this.hitWallStart = hitWallStart;
	}
	
	public void setHitWallEnd(boolean hitWallEnd) {
		this.hitWallEnd = hitWallEnd;
	}
	
	public void reset(Point2D.Double camBase, GridPosition camGridPos, double angle) {
		p2 = new Point2D.Double(camBase.x, camBase.y);
		gridPosition = new GridPosition(camGridPos.getRow(), camGridPos.getColumn());
		this.angle = angle;
	}
	
	public void paint(Graphics g) {
		super.draw(g, color);
	}
	
	public void paintDistoCorrected(Graphics g) {
		int p2x = MathTools.doubleToInt(p2.x);
		int p2y = MathTools.doubleToInt(p2.y);
		int p1x = MathTools.doubleToInt(p2.x);
		int p1y = MathTools.doubleToInt(p1.y);
		g.setColor(colorCorrect);
		g.drawLine(p1x, p1y, p2x, p2y);
	}
}
