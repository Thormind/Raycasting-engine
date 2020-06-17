package model;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe non utilisée pour le moment. Elle contiendra tous les éléments d'un niveau (le joueur, les ennemies, la cartes, les obstacles, les trésors, etc.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class Level {
	private LevelMap levelMap;
	private Player player;
	
	public Level(LevelMap levelMap, Player player) {
		this.levelMap = levelMap;
		this.player = player;
	}
	
	
}
