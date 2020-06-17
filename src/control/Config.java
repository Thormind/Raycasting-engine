package control;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.io.File;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Pour le moment cette classe contient toutes les constantes du programme. L'objectif éventuel est que toutes les valeurs se trouvant
// dans cette classe soient éventuellement conservées dans un fichier "config.ini". Cette classe aurait alors la responsabilité de lire
// le fichier et rendre les données disponibles à l'ensemble du programme.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger	

public class Config {
	static private final Dimension DIM_WINDOW = new Dimension(1920, 1080);
	static private final Dimension DIM_CANVAS_2D = new Dimension(800, 600);
	static private final Dimension DIM_CANVAS_3D = new Dimension(800, 600);
	
	static private final Point POS_CANVAS_2D = new Point(1010,240);
	static private final Point POS_CANVAS_3D = new Point(110,240);
	static private final Point CENTER_CANVAS_2D = new Point(DIM_CANVAS_2D.width/2, DIM_CANVAS_2D.height/2);
	
	static private final Color COLOR_WINDOW = Color.darkGray;
	static private final Color COLOR_CANVAS = Color.black;
	static private final Color COLOR_PLAYER = new Color(255,217,198);
	static private final Color COLOR_CAM_FOV = Color.GREEN;
	static private final Color COLOR_CAM_DIR = Color.BLUE;
	static private final Color COLOR_RAY = Color.RED;
	static private final Color COLOR_RAY_CORRECT = Color.ORANGE;

	static private final int GRID_BOX_ON_WIDTH = 25; // nombre de case de la grille sur la largeur du canevas 2D.
	static private final int GRID_BOX_WIDTH = 32;
	static private final int REAL_WALL_HEIGHT = 256;
	static private final int COLUMN_PER_CAM_PIXEL = 1;
	static private final int COLUMN_WIDTH = 8;
	static private final int NB_TEXTURE_SPLIT = REAL_WALL_HEIGHT / COLUMN_WIDTH;
	static private final int FPS = 30;
	static private final int PLAYER_HEAD_RAY = 10; // rayon du cercle représentant la tete du joueur
	static private final int PLAYER_SPEED = 5;
	
	static private final double FOV_ANGLE = 60;
	static private final double FOV_LENGTH = DIM_CANVAS_3D.width / COLUMN_WIDTH / COLUMN_PER_CAM_PIXEL;
	
	static private final String MAPS_DIRECTORY = "à déterminer";
	static private final String PATH_TEXTURE = "textures";
	static private final String PATH_WALL_TEXTURE = PATH_TEXTURE +"/walls";
	static private final String PATH_DARK_WALL_TEXTURE = PATH_TEXTURE +"/darkWalls";
	static private final String [] WALL_TEXTURE_FILES = {	"brickWall01_256x256.png",
												"brickWall02_256x256.png",
												"brickWall03_256x256.png",
												"stoneWall01_256x256.png",
												"stoneWall02_256x256.png"}; 
	
	private Config() {}
	
	static public Dimension getDimWindow() {
		return DIM_WINDOW;
	}
	
	static public Dimension getDimCanvas2D() {
		return DIM_CANVAS_2D;
	}
	
	static public Dimension getDimCanvas3D() {
		return DIM_CANVAS_3D;
	}
	
	static public Point getPosCanvas2D() {
		return POS_CANVAS_2D;
	}
	
	static public Point getPosCanvas3D() {
		return POS_CANVAS_3D;
	}
	
	static public Point getCenterCanvas2D() {
		return CENTER_CANVAS_2D;
	}
	
	static public Color getColorWindow() {
		return COLOR_WINDOW;
	}
	
	static public Color getColorCanvas() {
		return COLOR_CANVAS;
	}
	
	static public Color getColorPlayer() {
		return COLOR_PLAYER;
	}
	
	static public Color getColorCamFov() {
		return COLOR_CAM_FOV;
	}
	static public Color getColorCamDir() {
		return COLOR_CAM_DIR;
	}
	
	static public Color getColorRay() {
		return COLOR_RAY;
	}
	
	static public Color getColorRayCorrect() {
		return COLOR_RAY_CORRECT;
	}
	
	static public int getGridBoxOnWidth() {
		return GRID_BOX_ON_WIDTH;
	}
	
	static public int getGridBoxWidth() {
		return GRID_BOX_WIDTH;
	}
	
	static public int getRealWllHeight() {
		return REAL_WALL_HEIGHT;
	}

	static public int getColumnWidth() {
		return COLUMN_WIDTH;
	}
	
	static public int getNbTextureSplit() {
		return NB_TEXTURE_SPLIT;
	}
	
	static public int getColumnPerCamPixel() {
		return COLUMN_PER_CAM_PIXEL;
	}
	
	static public int getFps() {
		return FPS;
	}
	
	static public int getPlayerHeadRay() {
		return PLAYER_HEAD_RAY;
	}
	
	static public int getPlayerSpeed() {
		return PLAYER_SPEED;
	}
	
	static public double getFovAngle() {
		return FOV_ANGLE;
	}
	
	static public double getFovLength() {
		return FOV_LENGTH;
	}
	
	static public String getPathWallTexture() {
		return PATH_WALL_TEXTURE;
	}
	
	static public String getPathDarkWallTexture() {
		return PATH_DARK_WALL_TEXTURE;
	}
	
	static public String [] getWallTextureFiles() {
		return WALL_TEXTURE_FILES;
	}

}
