package model;
import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.util.EnumMap;
import control.Config;
import control.Observer.InputCommands;
import mathematic.MathTools;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Cette classe gère l'ensemble des données du jeu. C'est le modèle du patron de conception MVC.
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class GameEngine {
	
	// Lorsque le jeu comprendra d'autres éléments (ennemies, objets, etc) il faudra qu'une partie des actions gérées
	// par le game engine soient transférées dans une classe Level.
	private Level [] level; 
	private LevelMap [] levelMaps;
	private MapFileReader mapFileReader; // Servira éventuellement à lire les fichiers contenant les maps
	private int currentLevel = 0;
	private Player player;
	private Camera camera;
	private RayCaster rayCaster;
	private RayConverter rayConverter;
	private TextureManager textureManager;
	private EnumMap<InputCommands,Integer> inputActions;
	
	public GameEngine() {}
	
	public void setup() throws IOException {
		setupLevelMaps();
		setupLevel();
		setupPlayer();
		setupCamera();
		setupRayCaster();
		setupTextureManager();
		setupRayConverter();
		setupPixelColumns();
	}
	
	public void update() {
		updateCameraRotation();
		movePlayer();
		updateRays();
		updatePixelColumns();
		updatePlayer();
		updateMap();
	}
	
	public LevelMap getLevelMap() {
		return levelMaps[currentLevel];
	}
	
	public Player getPlayer() {
		return player;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public Ray [] getRays() {
		return rayCaster.getRays();
	}
	
	public PixelColumn [] getPixelColumns() {
		return rayConverter.getPixelColumns();
	}
	
	public void setInputActions(EnumMap<InputCommands,Integer> inputActions)  {
		this.inputActions = inputActions;
	}
	
	private void setupLevelMaps() {
		int [][] numberMap = new int [][]{
			{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2},		
			{2,0,0,0,0,3,0,0,3,4,0,0,0,4,5,0,0,0,5,2},
			{2,0,0,0,0,3,0,0,3,4,0,0,0,4,5,0,0,0,5,2},
			{2,0,0,0,0,3,3,3,3,4,0,4,4,4,5,0,5,0,5,2},
			{2,0,0,5,5,5,0,0,0,4,4,4,0,0,5,0,5,0,0,2},
			{2,0,0,5,0,5,0,0,0,0,0,0,0,0,5,5,5,0,0,2},
			{2,0,0,5,5,5,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2},
			{2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2}
		};
		Point mapPos = new Point(0,0);
		Point playerStartPos = new Point(Config.getCenterCanvas2D());
		levelMaps = new LevelMap[] {new LevelMap(mapPos, Config.getDimCanvas2D(),numberMap, playerStartPos) };
		
	}
	
	private void setupLevel() {
		
	}
	
	private void setupPlayer() {
		Point position = Config.getCenterCanvas2D();
		Color color = Config.getColorPlayer();
		int headRay = Config.getPlayerHeadRay();
		player = new Player(position, color, headRay);
	}
	
	private void setupCamera() {
		Point playerPosition = new Point(player.getPosition().x, player.getPosition().y);
		GridPosition gridPosition = new GridPosition(9,12);
		camera = new Camera(Config.getFovAngle(), Config.getFovLength(), Config.getColorCamFov(), Config.getColorCamDir());
		camera.setup(playerPosition, gridPosition);
	}
	
	private void setupRayCaster() {
		int nbRay = MathTools.doubleToInt(Config.getFovLength() / Config.getColumnPerCamPixel());
		rayCaster = new RayCaster(camera, levelMaps[0], nbRay, Config.getColumnWidth(), Config.getColorRay(), Config.getColorRayCorrect());
	}
	
	private void setupTextureManager() throws IOException {
		textureManager = new TextureManager(Config.getNbTextureSplit(),Config.getPathWallTexture(), Config.getPathDarkWallTexture(), Config.getWallTextureFiles());
	}
	
	private void setupPixelColumns() {
		int nbColumn = rayCaster.getNbRay();
		int width = Config.getColumnWidth();
		int height = Config.getDimCanvas3D().height;
		rayConverter.setupPixelColumns(nbColumn, width, height);
	}
	
	private void setupRayConverter() {
		double distancePlayerCamera = camera.getDirectionVector().getLength();
		Ray [] rays = rayCaster.getRays();
		rayConverter = new RayConverter(rays, Config.getRealWllHeight(), distancePlayerCamera, textureManager);
	}
	
	
	private void startLevel() {
		
	} 
	
	private void updateCameraRotation() {
		if(inputActions.containsKey(InputCommands.MOUSE_X_MOVED)) {
			camera.rotateCamera(inputActions.get(InputCommands.MOUSE_X_MOVED));
		}
	}
	
	private void updateMap() {
	}
	
	private void updatePlayer() {
		
	}
	
	private void updateRays() {
		rayCaster.evolveRays();
	}
	
	private void updatePixelColumns() {
		rayConverter.convertAllRays();
		rayConverter.assignTexturesAndColors(levelMaps[0].getNumberMap(), levelMaps[0].getMapColors());
	}
	
	private void movePlayer() {
		
		double angle = -1; // -1 is used to check if the player moved
		if(inputActions.containsKey(InputCommands.UP)) {
			angle = MathTools.correctAngle(camera.getCameraAngle() + 180);
		} else if(inputActions.containsKey(InputCommands.DOWN)) {
			angle = camera.getCameraAngle();
		} else if(inputActions.containsKey(InputCommands.LEFT)) {
			angle = camera.getCameraAngle() + 90;
		} else if(inputActions.containsKey(InputCommands.RIGHT)) {
			angle = camera.getCameraAngle() + 270;
		}
		
		if(angle != -1) {
			int moveDistance = Config.getPlayerSpeed();

			// To evaluate the new camera position we do as if the camera was movie *even if it is not). When the player move, every 
			// most game objets move the opposite was. The angle used here is for the game objects movement. Thats why 180deg is added.
			// Also the collision detection is made when changing the camera grid position. If camera movement is possible the method will
			// return true.
			if(updateCamGridPosition(MathTools.correctAngle(angle + 180), moveDistance)) {
				levelMaps[0].translate(angle, moveDistance);
			}
			
		}
	}
	
	private boolean updateCamGridPosition(double angle, int distance) {
		GridPosition camGridPosition = camera.getGridPosition();
		GridBox box = levelMaps[0].getGridBox(camGridPosition);
		int row = camGridPosition.getRow();
		int column = camGridPosition.getColumn();
		
		// The camera has a fixed position in the middle of the screen. But to evaluate its new grid position we do as if it was moving.
		Point newCameraPosition = MathTools.translatePoint(camera.getMapPosition(), angle, distance);
		
		if(newCameraPosition.x <= box.getTopLeft().x) {
			--column;
		} else if(newCameraPosition.x >= box.getBottomRight().x) {
			++column;
		}
		
		if(newCameraPosition.y <= box.getTopLeft().y) {
			--row;
		} else if(newCameraPosition.y >= box.getBottomRight().y) {
			++row;
		}
		
		GridPosition newGridPos = new GridPosition(row,column);
		if(!checkForCollisions(newGridPos)) {
			camera.setGridPosition(new GridPosition(row,column));
			return true;
		}
		return false;
		
	}
	
	// éventuellement cette fonction serait remplacée par une classe qui gère toutes les collisions
	// possibles (ennemies, objets, etx)
	private boolean checkForCollisions(GridPosition newGridPos) {
		return levelMaps[0].checkIfWall(newGridPos);
	}
}
