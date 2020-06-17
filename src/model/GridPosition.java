package model;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Cette classe représent la position d'un élément dans une grille 2D. 
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class GridPosition {
	private int row;
	private int column;
	
	public GridPosition(int row, int column) {
		this.row = row;
		this.column = column;
	}
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public boolean isEqual(GridPosition posToCompare) {
		return this.row == posToCompare.getRow() && this.column == posToCompare.getColumn();
	}
	
}
