package control;
import java.io.IOException;

import model.GameEngine;


// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Ce programme est un démo technique d'un engin pseudo 3d de type raycaster. Il deviendra éventuellement un jeu plus complet. 
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger


public class Main {

	
	public static void main(String[] args) throws IOException {
		GameEngine gameEngine = new GameEngine();
		GameClock gameClock = new GameClock(gameEngine);
	}
}


