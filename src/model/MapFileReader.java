package model;
import java.awt.Color;
import java.io.InputStream;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe non utilisée. Elle servira éventuellement à lire les fichiers contenant les maps des niveaux. L'objectif original
// était de faire un créateur de niveau à l'aide d'Excel. Le créateur sera intégré au programme et conçu en Java.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class MapFileReader {
	private final int MAP_SHEET_INDEX = 2;
	private final int COLOR_SHEET_INDEX = 1;
	private String directoryPath;
	private String [] fileNames;
	private InputStream inputStream;
	/*
	private XSSFWorkbook excelFile;
	private XSSFSheet mapSheet;
	private XSSFSheet colorSheet;
	private XSSFRow row;
	private XSSFCell cell;
	*/
	private LevelMap [] maps;
	private Color [] mapColors;
	private int rowIndex;
	private int columnIndex;
	
	public MapFileReader() {}
	public MapFileReader(String directoryPath, LevelMap [] maps, Color [] mapColors) {}
	public void readMapFiles() {}
	public LevelMap [] getMaps() {
		return maps;
	}
	private void readFileNames() {}
	private void readOneFile() {}
	private void readMapSheet() {}
	private void readColorSheet() {}
	public void readIntRow() {}
	public void readColorRow() {}
	public int readIntCel() {
		return 0;
	}
	public Color readColorCel() {
		return Color.white;
	}	
}
