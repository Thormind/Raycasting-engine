package mathematic;
import java.awt.Point;
import java.awt.geom.Point2D;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe contenant plusieurs outils de calcul mathématique.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class MathTools {
	private MathTools() {}
	
	static public double degToRad(double angleDeg) {
		return angleDeg *Math.PI / 180;
	}
	
	static public double radToDeg( double angleRad) {
		return angleRad * 180 / Math.PI;
	}
	
	static public double correctAngle(double angleDeg) {
		double correctAngle = angleDeg;
		
		if(angleDeg>=360) {
			correctAngle = angleDeg % 360;
		} else if(angleDeg < 0) {
			correctAngle = 360 + (angleDeg % 360);
		} 
		
		return correctAngle;
	}
	
	static public Point2D.Double pointIntToDouble(Point point) {
		return new Point2D.Double(point.x, point.y);
	}
	
	static public Point pointDoubleToInt(Point2D.Double point) {
		return new Point(doubleToInt(point.x), doubleToInt(point.y));
	}
	
	static public int doubleToInt(double d) {
		return (int)Math.round(d);
	}
	
	static public double calculDistanceTwoPoints(Point2D.Double p1, Point2D.Double p2) {
		double nbToSquare = ((p2.x-p1.x)*(p2.x-p1.x))+((p2.y-p1.y)*(p2.y-p1.y));
		return Math.sqrt(nbToSquare);
	}
	
	static public double calculAngleFromOppAdj(double oppSide, double adjSide) {
		return radToDeg(Math.atan(oppSide/adjSide));
	}
	
	static public double calculAngleFromOppHypo(double oppSide, double hypotenuse) {
		return radToDeg(Math.asin(oppSide/hypotenuse));
	}
	
	static public double calculAngleFromAdjtHypo(double adjSide, double hypotenuse) {
		return radToDeg(Math.acos(adjSide/hypotenuse));
	}
	
	static public double calculAdjFromOpp(double oppSide, double angleDeg) {
		double angleRad = degToRad(angleDeg);
		return oppSide / Math.tan(angleRad);
	}
	
	static public double calculAdjFromHyp(double hypotenuse, double angleDeg) {
		double angleRad = degToRad(angleDeg);
		return hypotenuse * Math.cos(angleRad);
	}
	
	static public double calculOppFromAdj(double adjSide, double angleDeg) {
		double angleRad = degToRad(angleDeg);
		return adjSide * Math.tan(angleRad);
	}
	
	static public Point2D.Double rotatePointFromCenter(double angle, Point2D.Double p, Point2D.Double center) {
		double angleRad = MathTools.degToRad(angle); // convert angle from deg to rad
		
		double rotatedX = Math.cos(angleRad)*(p.x-center.x)-Math.sin(angleRad)*(p.y-center.y) + center.x;
		double rotatedY = Math.sin(angleRad) * (p.x-center.x) + Math.cos(angleRad) * (p.y-center.y) + center.y;

		return new Point2D.Double(rotatedX,rotatedY);
	}
	
	static public Point translatePoint(Point point, double angle, double distance) {
		double correctAngle = -angle+90; // correction because the formula used is made for 0 angle at north going counterclockwise. This project 0 angle is north and going clockwise. 
		double angleRad = degToRad(correctAngle);
		int x = doubleToInt(angle != 0 && angle != 180? point.x + (distance * Math.cos(angleRad)):point.x);
		int y = doubleToInt(angle != 90 && angle != 270? point.y - (distance * Math.sin(angleRad)): point.y);
		return new Point(x,y);
	}
	
	static public Point2D.Double translatePointDbl( Point2D.Double point, double angle, double distance) {
		double correctAngle = -angle+90; // correction because the formula used is made for 0 angle at north going counterclockwise. This project 0 angle is north and going clockwise. 
		double angleRad = degToRad(correctAngle);
		double x = angle != 0 && angle != 180? point.x + (distance * Math.cos(angleRad)):point.x;
		double y = angle != 90 && angle != 270? point.y - (distance * Math.sin(angleRad)): point.y;
		return new Point2D.Double(x,y);
	}
	
	static public int FindClosestDivisibleBy(int number, int divider) 
    { 
        int quotient = number / divider; 
           
        // There is 2 possible closest number, we need to find them and compare 
        int n1 = divider * quotient; 
        int n2 = (number * divider) > 0 ? (divider * (quotient + 1)) : (divider * (quotient - 1)); 

        return Math.abs(number - n1) < Math.abs(number - n2)? n1 : n2;         
    } 
	
	static public int FindClosestDivisibleBy(int number, int divider, int lowLimit, int highLimit) 
    { 
        int quotient = number / divider; 
           
        // There is 2 possible closest number, we need to find them and compare 
        int n1 = divider * quotient; 
        int n2 = (number * divider) > 0 ? (divider * (quotient + 1)) : (divider * (quotient - 1)); 

        if(Math.abs(number - n1) < Math.abs(number - n2)) {
        	if(n1 > lowLimit && n1 <= highLimit) {
        		if(n1 <= 0) {
        			System.out.println("negative");
        		}
        		return n1;
        	} else {
        		if(n2 <= 0) {
        			System.out.println("negative");
        		}
        		return n2;
        	}
        } else if(n2 > lowLimit && n2 <= highLimit) {
    		if(n2 <= 0) {
    			System.out.println("negative");
    		}
        	return n2;
        } else {
    		if(n1 <= 0) {
    			System.out.println("negative");
    		}
        	return n1;
        }        
    } 
	
}
