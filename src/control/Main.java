package control;
import java.io.IOException;

import model.GameEngine;


// Contexte de r�alisation: cours B65 - Projet synth�se
//
// Description:				
// Ce programme est un d�mo technique d'un engin pseudo 3d de type raycaster. Il deviendra �ventuellement un jeu plus complet. 
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de cr�ation :		2020/05/02
// Auteur :					Fr�d�ric B�langer


public class Main {

	
	public static void main(String[] args) throws IOException {
		GameEngine gameEngine = new GameEngine();
		GameClock gameClock = new GameClock(gameEngine);
	}
}


