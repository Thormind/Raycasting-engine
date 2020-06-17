package model;

// Contexte de r�alisation: cours B65 - Projet synth�se
//
// Description:				
// Cette classe repr�sent la position d'un �l�ment dans une grille 2D. 
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de cr�ation :		2020/05/02
// Auteur :					Fr�d�ric B�langer

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
