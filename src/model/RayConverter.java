package model;
import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Vector;

import control.Config;
import mathematic.MathTools;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe qui gère la conversion des rayons de la vue 2D en colonne de pixels pour la vue 3D. 
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger


public class RayConverter {
	private Ray [] rays;
	private int realWallHeight;
	private double distancePlayerCamera;
	private PixelColumn [] pixelColumns;
	private TextureManager textureManager;
	private Vector<int []> raysHitSameWall;
	
	public RayConverter() {}
	
	public RayConverter(Ray rays [], int realWallHeight, double distancePlayerCamera, TextureManager textureManager) {
		this.rays = rays;
		this.realWallHeight = realWallHeight;
		this.distancePlayerCamera = distancePlayerCamera;
		this.textureManager = textureManager;
		this.raysHitSameWall = new Vector<int []>();
	}
	
	public void setupPixelColumns(int nbColumn, int columnWidth, int columnHeight) {
		pixelColumns = new PixelColumn [nbColumn];
		for(int i = 0; i < nbColumn; ++i) {
			Point topLeft = new Point (i * columnWidth, 0);
			pixelColumns[i] = new PixelColumn(topLeft, columnWidth, columnHeight);
		}
	}
	
	public void resetColumns() {}
	
	public void convertAllRays() {
		for(int i = 0; i < rays.length; ++i) {
			convertOneRay(i);
		}
	}
	
	// Unused code for now. Allows to draw walls with textures instead of colors. Kept because it might be used
	// eventually for demo purpose.
//	public void assignColors(int [][] numberMap, Color [] mapColors) {
//		int colorNumber = 0;
//		Color wallColor = Color.white;
//		Color floorColor = mapColors[0];
//		Color ceilingColor = mapColors[1];
//		GridPosition gridPosition = new GridPosition(0,0);
//		Ray ray = new Ray();
//		PixelColumn pixelColumn = new PixelColumn();
//		for(int i = 0; i < rays.length; ++i) {
//			ray = rays[i];
//			pixelColumn = pixelColumns[i];
//			gridPosition = ray.getGridPosition();
//			colorNumber = numberMap[gridPosition.getRow()][gridPosition.getColumn()];
//			wallColor = mapColors[colorNumber];
//			if(colorNumber == 3) {
//				//pixelColumn.setTexture(textureManager.getTexture(ray.getWallPartIndex()));
//				pixelColumn.setIsTextured(true);
//			} else {
//				pixelColumn.setIsTextured(false);
//			}
//			pixelColumn.setColors(floorColor, wallColor, ceilingColor);
//			correctColorSide(ray, pixelColumn);
//		}
//	}
	
	
	// Unused code for now. Allows to draw a mix of colored and textured walls. Kept because it might be used
	// eventually for demo purpose.
//	public void assignTextures(int [][] numberMap, Color [] mapColors) {
//		raysHitSameWall.clear();
//		groupRaysPerBox();
//		assignColumnTextures();
//		int colorNumber = 0;
//		Color wallColor = Color.white;
//		Color floorColor = mapColors[0];
//		Color ceilingColor = mapColors[1];
//		GridPosition gridPosition = new GridPosition(0,0);
//		Ray ray = new Ray();
//		PixelColumn pixelColumn = new PixelColumn();
//		for(int i = 0; i < rays.length; ++i) {
//			ray = rays[i];
//			pixelColumn = pixelColumns[i];
//			gridPosition = ray.getGridPosition();
//			colorNumber = numberMap[gridPosition.getRow()][gridPosition.getColumn()];
//			wallColor = mapColors[colorNumber];
//			if(colorNumber == 3) {
//				//pixelColumn.setTexture(textureManager.getTexture(ray.getWallPartIndex()));
//				pixelColumn.setIsTextured(true);
//			} else {
//				pixelColumn.setIsTextured(false);
//			}
//			pixelColumn.setColors(floorColor, wallColor, ceilingColor);
//			correctColorSide(ray, pixelColumn);
//		}
//	}
	
	public void assignTexturesAndColors(int [][] numberMap, Color [] mapColors) {
		raysHitSameWall.clear();
		groupRaysPerBox();
		assignColumnTextures(numberMap);
		Color wallColor = Color.white;
		Color floorColor = mapColors[0];
		Color ceilingColor = mapColors[1];
		PixelColumn pixelColumn = new PixelColumn();
		for(int i = 0; i < pixelColumns.length; ++i) {
			pixelColumn = pixelColumns[i];
			pixelColumn.setIsTextured(true);
			pixelColumn.setColors(floorColor, wallColor, ceilingColor);
			// correctColorSide(rays[i], pixelColumn);
		}
	}
	
	public PixelColumn [] getPixelColumns() {
		return pixelColumns;
	}
	
	private void convertOneRay(int index) {
		int wallHeight = calculWallHeight(rays[index].getDistoCorrectLength());
		pixelColumns[index].setColumn(wallHeight);
	}
	
	private int calculWallHeight(double rayLength) {
		return MathTools.doubleToInt(realWallHeight / rayLength * distancePlayerCamera);
	}
	
	private void correctColorSide(Ray ray, PixelColumn pixelColumn) {
		pixelColumn.setWallIsDark(ray.getHitX());
//		if(ray.getHitX()) {
//			Color wallColor = pixelColumn.getWallColor().darker();
//			pixelColumn.setWallColor(wallColor);
//		}
	}
	
	// Group rays by the GridBox section they hit. All rays hitting the same GridBox section will be together. Used to draw textures.
	
	private void groupRaysPerBox() {
		int firstIndex = 0;
		int lastIndex = 0;
		int iterator = 1;
		int nbRay = rays.length;
		boolean finished = false;
		while(!finished) {
			if(iterator < nbRay) {
				Ray firstRay = rays[firstIndex]; 
				Ray iteratorRay = rays[iterator];
				if(iteratorRay.getHitX() == firstRay.getHitX() && iteratorRay.getGridPosition().isEqual(firstRay.getGridPosition())) {
					++lastIndex;
					++iterator;
				} else {
					raysHitSameWall.add(new int []{new Integer(firstIndex), new Integer(lastIndex)});
					firstIndex = new Integer(iterator);
					lastIndex = new Integer(iterator);
					++iterator;
				}
			} else {
				raysHitSameWall.add(new int []{firstIndex,lastIndex});
				finished = true;
			}
		}
	}
	
	// if multiple rays are hitting the same wall section we need to 
	private void filterRaysHitSameSection() {
		
	}
	
	private void assignColumnTextures(int [][] numberMap) {
		for(int [] rayPair : raysHitSameWall) {
			int firstIndex = rayPair[0];
			int lastIndex = rayPair[1];
			Ray ray = rays[firstIndex];
			GridPosition posForTextureIndex = ray.getGridPosition();
			boolean isDark = ray.getHitX();
			// -2 because 0 and 1 are reserved for floor and ceiling colors in numberMap. When all numbers
			// are assigned a texture this wont be a problem.
			int textureIndex =  numberMap[posForTextureIndex.getRow()][posForTextureIndex.getColumn()] - 2;
			if(firstIndex == lastIndex) { // Only one ray is hitting the wall
				pixelColumns[firstIndex].setTexture(textureManager.getWallTexturePart(textureIndex, ray.getWallPartIndex(), isDark));
			} else {
				checkTextureExtremities(firstIndex, lastIndex);
				int nbSplit = lastIndex - firstIndex + 1;
				double textureBeginRatio = 0;
				double textureEndRatio = 1;
				
				if(!rays[firstIndex].getHitWallStart()) {
					textureBeginRatio = getTextureRatio(firstIndex);
				}
				if(!rays[lastIndex].getHitWallEnd()) {
					textureEndRatio = getTextureRatio(lastIndex);
				}

				Vector<BufferedImage> splitTextureSection = textureManager.getDividedWallTextureSection(textureIndex, textureBeginRatio, textureEndRatio, nbSplit, isDark);
				for(int i = firstIndex ; i <= lastIndex ; ++i) {
					pixelColumns[i].setTexture(splitTextureSection.get(i-firstIndex));
				}
			}
		}
	}
	
	private void checkTextureExtremities(int firstRayIndex, int lastRayIndex) {
		rays[firstRayIndex].setHitWallStart(checkRayHitStart(firstRayIndex));
		rays[lastRayIndex].setHitWallEnd(checkRayHitEnd(lastRayIndex));
	}
	
	private boolean checkRayHitStart(int rayIndex) {
		boolean rayHitWallStart = true;
		if(rayIndex == 0) {
			rayHitWallStart = false;
		} else {
			Ray ray = rays[rayIndex];
			double angle = rays[rayIndex].getAngle();
			boolean rayHitX = ray.getHitX();
			GridPosition rayGridPos = ray.getGridPosition();
			Ray prevRay = rays[rayIndex-1];
			boolean prevRayHitX = prevRay.getHitX();
			GridPosition prevRayGridPos = prevRay.getGridPosition();
			
			if(
			(angle >=0 && angle < 90 && rayHitX && prevRayHitX && rayGridPos.getRow() < prevRayGridPos.getRow()) ||
			(angle >=90 && angle < 180 && !rayHitX && !prevRayHitX && rayGridPos.getColumn() > prevRayGridPos.getColumn()) ||
			(angle >=180 && angle < 270 && rayHitX && prevRayHitX && rayGridPos.getRow() > prevRayGridPos.getRow()) ||
			(angle >=270 && angle < 360 && !rayHitX && !prevRayHitX && rayGridPos.getColumn() < prevRayGridPos.getColumn()) 
			){	
				rayHitWallStart = false;
			} 
		}
		return rayHitWallStart;
	}
	
	private boolean checkRayHitEnd(int rayIndex) {
		boolean rayHitWallEnd = true;
		if(rayIndex == rays.length-1) {
			rayHitWallEnd = false;
		} else {
			Ray ray = rays[rayIndex];
			double angle = rays[rayIndex].getAngle();
			boolean rayHitX = ray.getHitX();
			GridPosition rayGridPos = ray.getGridPosition();
			Ray nextRay = rays[rayIndex+1];
			boolean nextRayHitX = nextRay.getHitX();
			GridPosition nextRayGridPos = nextRay.getGridPosition();
			
			if(
			(angle >=0 && angle < 90 && !rayHitX && !nextRayHitX && rayGridPos.getColumn() > nextRayGridPos.getColumn()) ||
			(angle >=90 && angle < 180 && rayHitX && nextRayHitX && rayGridPos.getRow() > nextRayGridPos.getRow()) ||
			(angle >=180 && angle < 270 && !rayHitX && !nextRayHitX && rayGridPos.getColumn() < nextRayGridPos.getColumn()) ||
			(angle >=270 && angle < 360 && rayHitX && nextRayHitX && rayGridPos.getRow() < nextRayGridPos.getRow()) 
			){	
				rayHitWallEnd = false;
			} 
		}
		return rayHitWallEnd;
	}
	
	private double getTextureRatio(int rayIndex) {	
		double ratio = (double)rays[rayIndex].getWallPartIndex() / (double)Config.getGridBoxWidth();
		return ratio;
	}
	
}
