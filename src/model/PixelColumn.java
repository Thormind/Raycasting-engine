package model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import mathematic.Rectangle;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe représentant une colonne de pixels pour la vue 3D.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class PixelColumn extends Rectangle {
	private Rectangle wall;
	private Rectangle floor;
	private Rectangle ceiling;
	private BufferedImage texture;
	private boolean isTextured;
	private boolean wallIsDark;
	
	public PixelColumn() {}
	
	public PixelColumn(Point topLeft, int width, int height) {
		super(topLeft, new Point(topLeft.x + width, topLeft.y + height), Color.WHITE);
		setup();
	}
	
	public PixelColumn(Point topLeft, int width, int height, BufferedImage texture) {
		this(topLeft, width, height);
		this.texture = texture;
		this.isTextured = true;
	}
	
	public Color getWallColor() {
		return wall.getFillColor();
	}

	public void setColumn(int wallHeight) {
		int floorHeight = calculFloorHeight(wallHeight);
		Point wallTopLeft = calculWallTopLeft(floorHeight);
		Point wallBottomRight = calculWallBottomRight(wallTopLeft, wallHeight);
		setWall(wallTopLeft, wallBottomRight);
		setFloor(wallBottomRight);
		setCeiling(wallTopLeft);
	}
	
	
	public void setColors(Color floorColor, Color wallColor, Color ceilingColor) {
		floor.setColors(floorColor);
		ceiling.setColors(ceilingColor);
		wall.setColors(wallColor);
	}
	
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	public void setIsTextured(boolean isTextured) {
		this.isTextured = isTextured;
	}
	
	public void setWallIsDark(boolean wallIsDark) {
		this.wallIsDark = wallIsDark;
	}
	
	public void paint(Graphics g) {
		floor.fill(g);
		ceiling.fill(g);
		if(isTextured) {
			if(wallIsDark) {
				drawDarkTexturedWall(g);
			} else {
				drawTexturedWall(g);
			}
		} else {
			if(wallIsDark) {
				wall.setColors(wall.getFillColor().darker());
			} 
			wall.fill(g);
		}
	}
	
	private void setup() {
		wall = new Rectangle();
		floor = new Rectangle();
		ceiling = new Rectangle();
	}
	
	private int calculFloorHeight(int wallHeight) {
		return Math.round((bottomRight.y - wallHeight)/2);
	}
	
	private Point calculWallTopLeft(int floorHeight) {
		return new Point(topLeft.x, topLeft.y + floorHeight);
	}
	
	private Point calculWallBottomRight(Point wallTopLeft, int wallHeight) {
		return new Point(bottomRight.x, wallTopLeft.y + wallHeight);
	}

	private void setWall(Point topLeft, Point bottomRight) {
		wall.setTopLeft(topLeft);
		wall.setBottomRight(bottomRight);
	}
	
	private void setFloor(Point wallBottomRight) {
		floor.setTopLeft(new Point(topLeft.x, wallBottomRight.y));
		floor.setBottomRight(bottomRight);
	}
	
	private void setCeiling(Point wallTopLeft) {
		ceiling.setTopLeft(topLeft);
		ceiling.setBottomRight(new Point(bottomRight.x, wallTopLeft.y));
	}
	
	private void drawTexturedWall(Graphics g) {
		drawTexturedWall(g, texture);
	}
	
	private void drawDarkTexturedWall(Graphics g) {
		RescaleOp darkFilter = 	new RescaleOp(new float[]{0.8f, 0.8f, 0.8f, 1f}, // scale factors for red, green, blue, alpha
		        				new float[]{0, 0, 0, 0}, // offsets for red, green, blue, alpha
		        				null);
		BufferedImage darkTexture = darkFilter.filter(texture, null);
		drawTexturedWall(g,  darkTexture);
	}
	
	private void drawTexturedWall(Graphics g, BufferedImage texture) {
		g.drawImage(texture, wall.getTopLeft().x, wall.getTopLeft().y, wall.getWidth(), wall.getHeight(), null);
	}

}
