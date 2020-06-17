package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JPanel;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Aire d'affichage de l'ensemble du jeu. Va éventuellement contenir l'affichage de tous les éléments de l'interface utilisateur.
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur : 				Frédéric Bélanger	

@SuppressWarnings("serial")
public class CanvasFrame extends JPanel{
	private final Color BKGND_COLOR = Color.DARK_GRAY;
	private Dimension dimension;
	private Point position;
	
	public CanvasFrame (Dimension dimension) {
		this.dimension = dimension;
		this.position = new Point(0,0);
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
	}
}
