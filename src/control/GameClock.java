package control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Timer;
import model.GameEngine;
import view.View;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Cette classe coordonne le rafraichissement du modèle (GameEngine) et de la vue à une cadence de 30 fps. 
// C'est le contrôleur du patron de conception MVC
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger


public class GameClock {
	private Timer timer;
	private Observer observer;
	private TimerAction timerAction;
	private GameEngine gameEngine;
	private View view;
	private boolean paused;
	private boolean quit;
	private boolean updateComplete;
	
	public GameClock() {}
	public GameClock(GameEngine gameEngine) throws IOException {
		this.gameEngine = gameEngine;
		this.paused = false;
		this.quit = false;
		this.updateComplete = true;
		setup();
	}
	
	public void updateAll() {
			observer.setUpdateComplete(false);
			readInputs();
			updateGameEngine();
			refreshView();
			updateComplete = true;
			observer.resetInputActions();
	}
	
//	public void run() {
//		updateAll();
//	}
	
	public void pause() {
	}
	
	private void setup() throws IOException {
		setupGameEngine();
		setupView();
		setupCanvas2D();
		setupCanvas3D();
		setupObserver();
		setupTimer();

	}
	
	private void setupView() {
		this.view = new View();
		view.setupFrame();
	}
	
	private void setupCanvas2D() {
		view.setCanvas2D(gameEngine.getLevelMap(), gameEngine.getPlayer(), gameEngine.getCamera(), gameEngine.getRays());
	}
	
	
	private void setupCanvas3D() {
		view.setCanvas3D(Config.getDimCanvas3D(), Config.getPosCanvas3D(), gameEngine.getPixelColumns());
	}
	
	
	private void setupObserver() {
		observer = new Observer();
		// Required to make sure Jframe is created before adding the observer. 
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		view.getJFrame().addMouseMotionListener(observer);
		view.getJFrame().addKeyListener(observer);
	}
	
	private void setupTimer() {
		timerAction = new TimerAction();
		int fps = Config.getFps();
		int delay = Math.round(1000/fps); 
		this.timer = new Timer(delay, timerAction);
		timer.start();
	}	
	
	private void setupGameEngine() throws IOException {
		gameEngine.setup();
	}
	
	private void readInputs() {
		gameEngine.setInputActions(observer.getInputActions());
	}
	
	private void updateGameEngine() {
		gameEngine.update();
	}
	
	private void refreshView() {
		view.refresh();
	}
	
	private class TimerAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(updateComplete) {
				updateComplete = false;
				updateAll();	
			}
		}
	}

	
	
}
