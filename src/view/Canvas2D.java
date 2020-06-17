package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import model.Camera;
import model.LevelMap;
import model.Player;
import model.Ray;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Aire d'affichage de la vue 2D
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur : 				Frédéric Bélanger		

@SuppressWarnings("serial")
public class Canvas2D extends JPanel{
	private final Color BKGND_COLOR = Color.BLACK;
	private Dimension dimension;
	private Point position;
	private Point center;
	private LevelMap levelMap;
	private Camera camera;
	private Player player;
	private Ray [] rays;
	
	/*
	public Canvas2D (Dimension dimension, Point position, LevelMap levelMap, Camera camera, Player player, Ray [] rays) {
		this.dimension = dimension;
		this.position = position;
		this.levelMap = levelMap;
		this.camera = camera;
		this.player = player;
		this.rays = rays;
		calculCenter();
	}
	*/
	
	public Canvas2D (Dimension dimension, Point position, LevelMap levelMap, Player player, Camera camera, Ray [] rays) {
		this.dimension = dimension;
		this.position = position;
		this.levelMap = levelMap;
		this.player = player;
		this.camera = camera;
		this.rays = rays;
		calculCenter();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return dimension;
	}	
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // important
		// anti-aliasing
		Graphics2D g2=(Graphics2D)g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		setBackground(BKGND_COLOR);
		setSize(dimension);
		setLocation(position);
		
		levelMap.paint(g);
		player.paint(g);
		camera.paint(g);
		for(Ray ray : rays) {
			ray.paint(g);
			//ray.paintDistoCorrected(g);
		}	
	}
	
	private void calculCenter() {
		center = new Point(dimension.width/2, dimension.height/2);
	}
	
	public void refresh() {
		repaint();
	}
}
