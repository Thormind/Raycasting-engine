package model;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import control.Config;
import mathematic.MathTools;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe représent les éléments de la carte d'un niveau.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class LevelMap {
	private Point position;
	private Point playerStartPos;
	Dimension dimCanvas2D;
	private int [][] numberMap;
	private Color [] mapColors;
	private Grid grid;
	
	public LevelMap() {}
	public LevelMap(Point position, Dimension dimCanvas2D,  int [][] numberMap, Point playerStartPos) {
		this.position = position;
		this.dimCanvas2D = dimCanvas2D;
		this.numberMap = numberMap;
		this.playerStartPos = playerStartPos;
		setup();
	}
	
	public boolean checkIfWall(GridPosition gridPos) {
		return numberMap[gridPos.getRow()][gridPos.getColumn()] > 0;
	}
	
	public void translate(double angle, int distance) {
		translatePosition(angle, distance);
		translateGrid(angle, distance);
	}
	
	public Grid getGrid() {
		return grid;
	}
	
	public GridBox getGridBox(GridPosition gridPos) {
		return grid.getBox(gridPos);
	}
	
	public Point getPlayerStartPos() {
		return playerStartPos;
	}
	
	public int [][] getNumberMap() {
		return numberMap;
	}
	
	public Color [] getMapColors() {
		return mapColors;
	}
	
	public void setMap (int [][] numberMap) {
		this.numberMap = numberMap;
	}
	
	public void setMapColors(Color [] mapColors) {
		this.mapColors = mapColors;
	}
	
	public void paint(Graphics g) {
		fillGrid(g);
		drawGrid(g);
	}
	
	private void setup() {
		setupMapColors();
		setupGrid();
		setupGridColors();
	}
	
	private void setupGrid() {
		int nbRow = numberMap.length;
		int nbColumn = numberMap[0].length;
		//int boxWidth = Config.getDimCanvas2D().width/Config.getGridBoxOnWidth();
		int boxWidth = Config.getGridBoxWidth();
		grid = new Grid(position, nbRow, nbColumn, boxWidth);
	}
	
	private void setupMapColors() {
		Color color0 = Color.LIGHT_GRAY;
		Color color1 = Color.BLACK;
		Color color2 = new Color(128, 128, 128);
		Color color3 = new Color(255, 0, 0);
		Color color4 = new Color(255, 128, 0);
		Color color5 = new Color(255, 0, 255);

		mapColors = new Color[] {Color.LIGHT_GRAY, Color.BLACK, Color.GRAY, Color.RED, Color.ORANGE, Color.MAGENTA };
	}
	
	private void setupGridColors() {
		for(int r = 0;r < grid.getNbRow();++r) {
			for(int c = 0;c < grid.getNbColumn(); ++c) {
				GridPosition gridPos = new GridPosition(r,c);
				Color fillColor = selectBoxColor(gridPos);
				grid.getBox(gridPos).setFillColor(fillColor);
			}
		}
	}
	
	private Color selectBoxColor(GridPosition gridPos) {
		return mapColors[numberMap[gridPos.getRow()][gridPos.getColumn()]];
	}
	
	private void translatePosition(double angle, int distance) {
		position = MathTools.translatePoint(position, angle, distance); 
	}
	
	private void translateGrid(double angle, int distance) {
		grid.translate(angle, distance);
	}
	
	private void drawGrid(Graphics g) {
		grid.draw(g);
	}
	
	private void fillGrid(Graphics g) {
		grid.fill(g);
	}
}
