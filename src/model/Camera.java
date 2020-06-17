package model;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.geom.Point2D;
import mathematic.MathTools;
import mathematic.MathVector;

// Description:				
// Manage the game camera and it's display on the 2D view.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Creation date :		05/02/2020
// By :					Frédéric Bélanger

public class Camera {
	private MathVector fovVector;
	private MathVector directionVector;
	private Point2D.Double fovOriginalP1;
	private Point2D.Double fovOriginalP2;
	private Point2D.Double directionOriginalP2;
	private GridPosition gridPosition;
	private Point mapPosition;
	private double cameraAngle;
	private double fovAngle;
	private double fovLength;
	Color fovColor;
	Color directionColor;
	
	public Camera() {}
	public Camera(double fovAngle, double fovLength, Color fovColor, Color directionColor) {
		this.fovAngle = fovAngle;
		this.fovLength = fovLength;
		this.fovColor = fovColor;
		this.directionColor = directionColor;
	}
	public void rotateCamera(double angle) {
		cameraAngle += angle;
		rotateFov();
		rotateDirection();
	}

	public MathVector getFovVector() {
		return fovVector;
	}
	
	public MathVector getDirectionVector() {
		return directionVector;
	}
	
	public GridPosition getGridPosition() {
		return gridPosition;
	}
	
	public Point getMapPosition() {
		return mapPosition;
	}
	
	public double getCameraAngle() {
		return cameraAngle;
	}
	
	public double getFovAngle() {
		return fovAngle;
	}
	
	public void setMapPosition(Point mapPosition) {
		this.mapPosition = mapPosition;
	}
	
	public void setGridPosition(GridPosition gridPosition) {
		this.gridPosition = gridPosition;
	}
	
	public void paint(Graphics g) {
		fovVector.draw(g, fovColor);
		directionVector.draw(g, directionColor);
	}
	
	public void setup(Point playerPosition, GridPosition gridPosition) {
		this.gridPosition = gridPosition;
		this.mapPosition = playerPosition;
		createDirectionVector(playerPosition);
		createFovVector();
		createOriginalParam();
	}

	private Point2D.Double calculFovP1() {
		double x = directionVector.getP2().x - (fovLength/2);
		double y = directionVector.getP2().y;
		return new Point2D.Double(x, y);
	}
	
	private Point2D.Double calculFovP2() {
		double x = directionVector.getP2().x + (fovLength/2);
		double y = directionVector.getP2().y;
		return new Point2D.Double(x, y);
	}
	private void createFovVector() {
		fovVector = new MathVector(calculFovP1(), calculFovP2());
	}
	
	private double calculDirLength() {
		double angle = fovAngle / 2;
		double oppSide = fovLength / 2;
		return MathTools.calculAdjFromOpp(oppSide, angle);
	}
	private Point2D.Double calculDirectionP2(Point playerPosition) {
		double x = playerPosition.x;
		double y = playerPosition.y - calculDirLength();
		return new Point2D.Double(x,y);
	}
	private void createDirectionVector(Point playerPosition) {
		directionVector = new MathVector(MathTools.pointIntToDouble(playerPosition), calculDirectionP2(playerPosition));
	}
	
	private void createOriginalParam() {
		fovOriginalP1 = new Point2D.Double(fovVector.getP1().x, fovVector.getP1().y);
		fovOriginalP2 = new Point2D.Double(fovVector.getP2().x, fovVector.getP2().y);
		directionOriginalP2 = new Point2D.Double(directionVector.getP2().x, directionVector.getP2().y);
	}
	
	// Le traitement des angles se fait en rad (un double) mais les positions sont en int. Pour éviter 
	// les accumulations d'erreurs de positionnements dûes aux conversions il faut effectuer les rotation
	// à partir des positions d'origine plutôt qu'a partir des dernières positions de la caméra.
	private void rotateFov() {
		// Des nouveaux points son créés pour éviter les références aux points originaux. Sans cela
		// les paramètres d'origine seraient affectés
		double p1x = fovOriginalP1.getX();
		double p1y = fovOriginalP1.getY();
		double p2x = fovOriginalP2.getX();
		double p2y = fovOriginalP2.getY();
		
		fovVector.setP1(new Point2D.Double(p1x,p1y));
		fovVector.setP2(new Point2D.Double(p2x,p2y));
		fovVector.rotateFromPoint(cameraAngle, directionVector.getP1());
	}
	
	// Le traitement des angles se fait en rad (un double) mais les positions sont en int. Pour éviter 
	// les accumulations d'erreurs de positionnements dûes aux conversions il faut effectuer les rotation
	// à partir des positions d'origine plutôt qu'a partir des dernières positions de la caméra.
	private void rotateDirection() {
		// Un nouveau point est créé pour éviter la référence au point original. Sans cela
		// le point d'origine serait affecté.
		double p2x = directionOriginalP2.getX();
		double p2y = directionOriginalP2.getY();
		directionVector.setP2(new Point2D.Double(p2x,p2y));
		directionVector.rotateFromBase(cameraAngle);
	}
	
	private void calculNewCameraAngle(double angle) {
		cameraAngle = MathTools.correctAngle(cameraAngle + angle);
		System.out.println(cameraAngle);
	}
	
	
}
