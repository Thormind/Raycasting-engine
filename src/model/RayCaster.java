package model;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Vector;
import mathematic.MathTools;

// Description:				
// Class manage the casting of rays used to convert the 2D view into 3D.
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Creation date :		2020/05/02
// By :					Frédéric Bélanger

public class RayCaster {
	private Ray [] rays;
	private Camera camera;
	private LevelMap levelMap;
	private int nbRay;
	private int pixelColumnWidth;
	private double angleBetweenRay;
	private enum BorderSide {LEFT, RIGHT, TOP, BOTTOM};
	private Vector<BorderSide> camOnBorders; 
	
	public RayCaster() {}
	public RayCaster(Camera camera, LevelMap levelMap, int nbRay, int pixelColumnWidth, Color color, Color colorCorrect) {
		this.camera = camera;
		this.levelMap = levelMap;
		this.nbRay = nbRay;
		this.pixelColumnWidth = pixelColumnWidth;
		setup(color, colorCorrect);
	}
	
	public void evolveRays() {
		resetRays();
		checkCamOnBorders(); 
		int i = 0;
		for(Ray ray : rays) {
			evolveOneRay(ray);
			i++;
		}
		camOnBorders.clear();
	}
	
	public void resetRays() {
		Point2D.Double camBase = camera.getDirectionVector().getP1();
		GridPosition camGridPos = camera.getGridPosition();
		double angle = camera.getCameraAngle() - (camera.getFovAngle()/2);
		for(int i = 0; i < nbRay; ++i) {
			angle = MathTools.correctAngle(angle);
			rays[i].reset(camBase, camGridPos, angle);
			angle += angleBetweenRay;
			if(angle > 359.999999999999999 && angle < 360.000000000000001) {
				angle = 0; // Correction because the rays evolution is done with 0 to 359 deg angles.
			}
		}	
	}
	
	public Ray [] getRays() {
		return rays;
	}
	
	public int getNbRay() {
		return nbRay;
	}
	
	private void setup(Color color, Color colorCorrect) {
		rays = new Ray[nbRay];
		calculAngleBetweenRay();
		double p1x = camera.getDirectionVector().getP1().x;
		double p1y = camera.getDirectionVector().getP1().y;
		double angle = camera.getCameraAngle() - (camera.getFovAngle()/2);
		Point2D.Double p1 = new Point2D.Double(p1x, p1y);
		Point2D.Double p2 = new Point2D.Double(p1x, p1y);
		
		for(int i = 0; i < nbRay; ++i) {
			angle = MathTools.correctAngle(angle);
			rays[i] = new Ray(p1, p2, angle, camera.getGridPosition(), color, colorCorrect);
			angle += angleBetweenRay;
		}
	}
	
	private void calculAngleBetweenRay() {
		double fovAngle = camera.getFovAngle();
		angleBetweenRay = fovAngle/nbRay;
	}
	
	private void evolveOneRay(Ray ray) {
		boolean wallHit = false;
		if(!camOnBorders.isEmpty()) {
			correctGridPos(ray);
		}
		while(!wallHit) {
			findBoxCross(ray);
			if(levelMap.checkIfWall(ray.getGridPosition())) {
				wallHit = true;
				correctDistortion(ray);
				checkWallPartHit(ray);
			}
		}
	}
	
	private void checkCamOnBorders() {
		camOnBorders = new Vector<BorderSide>();
		GridBox gridBox = levelMap.getGridBox(camera.getGridPosition());
		Point mapPosition = camera.getMapPosition();
		for (BorderSide borderSide : BorderSide.values()) { 
			switch(borderSide) {
				case LEFT:
					if(mapPosition.x == gridBox.getTopLeft().x) {
						camOnBorders.add(borderSide);
					}
					break;
				case RIGHT:
					if(mapPosition.x == gridBox.getBottomRight().x) {
						camOnBorders.add(borderSide);
					}
					break;
				case TOP:
					if(mapPosition.y == gridBox.getTopLeft().y) {
						camOnBorders.add(borderSide);					
					}
					break;
				case BOTTOM:
					if(mapPosition.y == gridBox.getBottomRight().y) {
						camOnBorders.add(borderSide);					
					}
					break;
			}
		}
	}
	
	// When the camera is directly on 1 or 2 box border(s) and the ray must evolve in the direction of that 
	// border the starting box of that ray must be corrected. Otherwise the ray will evolve towards its 
	// starting point. This would cause problems when trying to find the next border(s) crossed by the ray.
	private void correctGridPos(Ray ray) {
		int row = ray.getGridPosition().getRow();
		int column = ray.getGridPosition().getColumn();
		double angle = ray.getAngle();
		if(camOnBorders.contains(BorderSide.LEFT) && angle > 180 && angle <= 359) {
			column -= 1;
		} else if(camOnBorders.contains(BorderSide.RIGHT) && angle > 0 && angle < 180) {
			column += 1;
		}
		
		if(camOnBorders.contains(BorderSide.TOP) && (angle < 90 || angle > 270)) {
			row -= 1;
		} else if(camOnBorders.contains(BorderSide.BOTTOM) && angle > 90 && angle < 270) {
			row += 1;
		}
		ray.setGridPosition(new GridPosition(row, column));
	}
	
	private void findBoxCross(Ray ray) {
		double angle = ray.getAngle();
		if(angle % 90 == 0) {
			findBoxCrossRightAngle(ray);
		} else if(angle > 0 && angle < 90) {
			findBoxCrossNorthEast(ray);
		} else if(angle > 90 && angle < 180) {
			findBoxCrossSouthEast(ray);
		} else if(angle > 180 && angle < 270) {
			findBoxCrossSouthWest(ray);
		} else {
			findBoxCrossNorthWest(ray);
		}
	}
	
	private void findBoxCrossRightAngle(Ray ray) {
		Point2D.Double p2 = ray.getP2();
		GridBox box = levelMap.getGridBox(ray.getGridPosition());
		int rowDelta = 0;
		int columnDelta = 0;
		boolean hitX = false;
		double angle = ray.getAngle();
		switch(MathTools.doubleToInt(ray.getAngle())) {
			case 0:
			case 360:
				p2.y = box.getTopLeft().y;
				rowDelta -= 1;
				hitX = true;
				break;
			case 90:
				p2.x = box.getBottomRight().x;
				columnDelta += 1;
				break;
			case 180:
				p2.y = box.getBottomRight().y;
				rowDelta += 1;
				hitX = true;
				break;
			case 270:
				p2.x = box.getTopLeft().x;
				columnDelta -= 1;
				break;
		}
		setRayAfterEvolve(ray, p2, rowDelta, columnDelta, hitX);
	}
	
	private void findBoxCrossNorthEast(Ray ray) {
		Point2D.Double p1 = ray.getP2();
		Point2D.Double p2 = ray.getP2();
		double angle = ray.getAngle();
		GridBox box = levelMap.getGridBox(ray.getGridPosition());
		int rowDelta = 0;
		int columnDelta = 0;
		boolean hitX = false;

		double x = findBoxCrossNEx(p1, angle, box);
		if(x < box.getBottomRight().x) {
			p2.x = x;
			p2.y = box.getTopLeft().y;
			hitX = true;
			rowDelta -= 1;
		} else {
			p2.y = findBoxCrossNEy(p1, angle, box);
			p2.x = box.getBottomRight().x;
			columnDelta += 1;
		}

		setRayAfterEvolve(ray, p2, rowDelta, columnDelta, hitX);
	}
	
	private void findBoxCrossSouthEast(Ray ray) {
		Point2D.Double p1 = ray.getP2();
		Point2D.Double p2 = ray.getP2();
		double angle = ray.getAngle();
		GridBox box = levelMap.getGridBox(ray.getGridPosition());
		int rowDelta = 0;
		int columnDelta = 0;
		boolean hitX = false;
		
		double x = findBoxCrossSEx(p1, angle, box);
		if(x < box.getBottomRight().x) {
			p2.x = x;
			p2.y = box.getBottomRight().y;
			hitX = true;
			rowDelta += 1;
		} else {
			p2.y = findBoxCrossSEy(p1, angle, box);
			p2.x = box.getBottomRight().x;
			columnDelta += 1;
		}
		
		setRayAfterEvolve(ray, p2, rowDelta, columnDelta, hitX);
	}
	
	private void findBoxCrossSouthWest(Ray ray) {
		Point2D.Double p1 = ray.getP2();
		Point2D.Double p2 = ray.getP2();
		double angle = ray.getAngle();
		GridBox box = levelMap.getGridBox(ray.getGridPosition());
		int rowDelta = 0;
		int columnDelta = 0;
		boolean hitX = false;

		double x = findBoxCrossSWx(p1, angle, box);
		if(x > box.getTopLeft().x) {
			p2.x = x;
			p2.y = box.getBottomRight().y;
			hitX = true;
			rowDelta += 1;
		} else {
			p2.y = findBoxCrossSWy(p1, angle, box);
			p2.x = box.getTopLeft().x;
			columnDelta -= 1;
		}
	
		setRayAfterEvolve(ray, p2, rowDelta, columnDelta, hitX);
	}
	
	private void findBoxCrossNorthWest(Ray ray) {
		Point2D.Double p1 = ray.getP2();
		Point2D.Double p2 = ray.getP2();
		double angle = ray.getAngle();
		GridBox box = levelMap.getGridBox(ray.getGridPosition());
		int rowDelta = 0;
		int columnDelta = 0;
		boolean hitX = false;
		
		double x = findBoxCrossNWx(p1, angle, box);
		if(x > box.getTopLeft().x) {
			p2.x = x;
			p2.y = box.getTopLeft().y;
			hitX = true;
			rowDelta -= 1;

		} else {
			p2.y = findBoxCrossNWy(p1, angle, box);
			p2.x = box.getTopLeft().x;
			columnDelta -= 1;
		}

		setRayAfterEvolve(ray, p2, rowDelta, columnDelta, hitX);
	}
	
	private double findBoxCrossNEx(Point2D.Double p1, double rayAngle, GridBox box) {
		double adjacent = p1.y - box.getTopLeft().y;
		double opposite = MathTools.calculOppFromAdj(adjacent, rayAngle);
		return p1.x + opposite;
	}
	
	private double findBoxCrossNEy(Point2D.Double p1, double rayAngle, GridBox box) {
		double angle = 90 - rayAngle;
		double adjacent = box.getBottomRight().x - p1.x;
		double opposite = MathTools.calculOppFromAdj(adjacent, angle);
		return p1.y - opposite;
	}
	
	private double findBoxCrossSEx(Point2D.Double p1, double rayAngle, GridBox box) {
		double angle = 180 - rayAngle;
		double adjacent = box.getBottomRight().y - p1.y;
		double opposite = MathTools.calculOppFromAdj(adjacent, angle);
		return p1.x + opposite;
	} 
	
	private double findBoxCrossSEy(Point2D.Double p1, double rayAngle, GridBox box) {
		double angle = rayAngle - 90;
		double adjacent = box.getBottomRight().x - p1.x;
		double opposite = MathTools.calculOppFromAdj(adjacent, angle);
		return p1.y + opposite;
	}
	
	private double findBoxCrossSWx(Point2D.Double p1, double rayAngle, GridBox box) {
		double angle = rayAngle - 180;
		double adjacent = box.getBottomRight().y - p1.y;
		double opposite = MathTools.calculOppFromAdj(adjacent, angle);
		return p1.x - opposite;
	}
	
	private double findBoxCrossSWy(Point2D.Double p1, double rayAngle, GridBox box) {
		double angle = 270 - rayAngle;
		double adjacent = p1.x - box.getTopLeft().x;
		double opposite = MathTools.calculOppFromAdj(adjacent, angle);
		return p1.y + opposite;
	}
	
	private double findBoxCrossNWx(Point2D.Double p1, double rayAngle, GridBox box) {		
		double angle = 360 - rayAngle;
		double adjacent = p1.y - box.getTopLeft().y;
		double opposite = MathTools.calculOppFromAdj(adjacent, angle);
		return p1.x - opposite;
	}
	
	private double findBoxCrossNWy(Point2D.Double p1, double rayAngle, GridBox box) {
		double angle = rayAngle - 270;
		double adjacent = p1.x - box.getTopLeft().x;
		double opposite = MathTools.calculOppFromAdj(adjacent, angle);
		return p1.y - opposite;
	}
	
	private void setRayAfterEvolve(Ray ray, Point2D.Double p2, int rowDelta, int columnDelta, boolean hitX) {
		ray.setP2(p2);
		int row = ray.getGridPosition().getRow() + rowDelta;
		int column = ray.getGridPosition().getColumn() + columnDelta;
		ray.setGridPosition(new GridPosition(row, column));
		ray.setHitX(hitX);
	}
	
	private void correctDistortion(Ray ray) {
		double rayAngle = ray.getAngle();
		double camAngle = camera.getCameraAngle();
		double rayCamAngle = 0;
		if(camAngle < 30 && rayAngle >= 330) {
			rayCamAngle = 360 - rayAngle + camAngle; 
		} else {
			rayCamAngle = Math.abs(camAngle - rayAngle);
		}
		
		ray.setDistoCorrectLength(MathTools.calculAdjFromHyp(ray.getLength(),rayCamAngle)); 
	}
	
	private void checkWallPartHit(Ray ray) {
		GridBox box = levelMap.getGridBox(ray.getGridPosition());
		double angle = ray.getAngle();
		Point2D.Double rayP2 = ray.getP2();
		int wallPartIndex = 0; 
		
		// All the 0/31 corrections here are due to the fact that ray points are in double.
		// When looking for equality between doubles it can cause small errors making a 
		// ray looks like it is on a border while in fact it is very slightly past the
		// border. It only causes problems here and for precision purpose i decided to
		// keep my ray points in double.
		if(ray.getHitX()) {
			if(angle > 270 || (angle >= 0 && angle < 90)) {
				wallPartIndex = rayP2.x < box.getTopLeft().x? 0 : 
					rayP2.x > box.getBottomRight().x? 31 :	
					MathTools.doubleToInt(rayP2.x - box.getTopLeft().x);
			} else {
				wallPartIndex = rayP2.x > box.getBottomRight().x? 0 : 
					rayP2.x < box.getTopLeft().x? 31 :
					MathTools.doubleToInt(box.getBottomRight().x - rayP2.x);
			}

		} else if(angle > 0 && angle < 180){
			wallPartIndex = rayP2.y < box.getTopLeft().y? 0 :
				rayP2.y > box.getBottomRight().y? 31 :	
				rayP2.y < box.getTopLeft().y? 31 :
				MathTools.doubleToInt(rayP2.y - box.getTopLeft().y);
		} else {
			wallPartIndex = rayP2.y > box.getBottomRight().y? 0 :	
				rayP2.y < box.getTopLeft().y? 0 :
				MathTools.doubleToInt(box.getBottomRight().y - rayP2.y);
		}	
		
		// Boxes share the same boundaries (ie their borders are on top of each other)
		// If a ray is exactly on a border it is possible to get a wall part index of 32.
		if(wallPartIndex >= 32) {
			wallPartIndex = 31;
			System.out.println("32 " + angle );
		}
		
		ray.setWallPartIndex(wallPartIndex);
	}
	
}
