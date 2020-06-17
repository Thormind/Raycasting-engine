package view;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import model.PixelColumn;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Aire d'affichage de la vue 3D
// - - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur : 				Frédéric Bélanger	

@SuppressWarnings("serial")
public class Canvas3D extends JPanel{
	private final Color BKGND_COLOR = Color.BLACK;
	private Dimension dimension;
	private Point center;
	private Point position;
	private PixelColumn [] pixelColumns;
	
	public Canvas3D (Dimension dimension, Point position, PixelColumn [] pixelColumns) {
		this.dimension = dimension;
		this.position = position;
		this.pixelColumns = pixelColumns;
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
		
		for(PixelColumn column : pixelColumns) {
			column.paint(g);
		}

	}
	
	private void calculCenter() {
		center = new Point(dimension.width/2, dimension.height/2);
	}
}
