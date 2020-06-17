package control;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.EnumMap;

// Contexte de réalisation: cours B65 - Projet synthèse
// 
// Description:				
// Cette classe prend les inputs du joueur et les met à la disposition du contrôleur dans une table de hashage
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class Observer extends MouseMotionAdapter implements KeyListener{
	public enum InputCommands {UP,DOWN,LEFT,RIGHT,RAY_DISTO, PAUSE, 
		MOUSE_X_MOVED, NONE}
	private EnumMap<InputCommands,Integer> inputActions;
	private int oldMouseX;
	private int oldMouseY;
	boolean updateComplete;
	
	public Observer() {
		inputActions = new EnumMap<>(InputCommands.class);
		this.updateComplete = true;
		this.oldMouseX = 2000;
		this.oldMouseY = 2000;
	}
	
	
	@Override 
	public void mouseMoved(MouseEvent mEvent) {
		int rotationAngle;
		if(oldMouseX == 2000 || oldMouseY == 2000) {
			oldMouseX = mEvent.getX();
			oldMouseY = mEvent.getY();
		} else {
			rotationAngle = mEvent.getX()-oldMouseX;

			if(rotationAngle!=0) {
				oldMouseX = mEvent.getX();
				updateInputActions(InputCommands.MOUSE_X_MOVED, rotationAngle);
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent kEvent) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent kEvent) {
		InputCommands command = InputCommands.NONE;
		switch(kEvent.getKeyCode()) {
			case KeyEvent.VK_W:
				command = InputCommands.UP;
				break;
			case KeyEvent.VK_A:
				command = InputCommands.LEFT;
				break;
			case KeyEvent.VK_S:
				command = InputCommands.DOWN;
				break;
			case KeyEvent.VK_D:
				command = InputCommands.RIGHT;
				break;
		}

		updateInputActions(command, 1);
	}

	@Override
	public void keyReleased(KeyEvent kEvent) {
		InputCommands command = InputCommands.NONE;
		switch(kEvent.getKeyCode()) {
			case KeyEvent.VK_P:
				command = InputCommands.PAUSE; // à faire
				break;
			case KeyEvent.VK_R:
				command = InputCommands.RAY_DISTO; // abandonné pour le moment
				break;
		}

		updateInputActions(command, 1);
		
	}
	
	public EnumMap<InputCommands,Integer> getInputActions() {
		return inputActions;
	}

	public void setUpdateComplete(boolean updateComplete) {
		
	}
	
	public void resetInputActions() {
		inputActions.clear();
	}
	
	private void updateInputActions(InputCommands key, int value) {
		if(key != InputCommands.NONE) {
			if(inputActions.containsKey(key)) {
				inputActions.put(key, inputActions.get(key) + value);
			} else {
				this.inputActions.put(key, value);
			}
			
			// Code conservé car utilisé pour tester les inputs il y en aura éventuellement d'autres à ajouter. 
//			for(Entry<InputCommands, Integer> entry : inputActions.entrySet()) {
//				System.out.println(entry.getKey());
//			}
		}
	}
	

}
