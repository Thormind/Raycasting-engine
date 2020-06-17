package model;

// Contexte de r�alisation: cours B65 - Projet synth�se
//
// Description:				
// Classe non utilis�e pour le moment. Elle contiendra tous les �l�ments d'un niveau (le joueur, les ennemies, la cartes, les obstacles, les tr�sors, etc.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de cr�ation :		2020/05/02
// Auteur :					Fr�d�ric B�langer

public class Level {
	private LevelMap levelMap;
	private Player player;
	
	public Level(LevelMap levelMap, Player player) {
		this.levelMap = levelMap;
		this.player = player;
	}
	
	
}
