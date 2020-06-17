package view;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.UIManager;
import control.Config;
import model.Camera;
import model.LevelMap;
import model.PixelColumn;
import model.Player;
import model.Ray;

// Contexte de réalisation: cours B65 - Projet synthèse
//
// Description:				
// Classe qui gère l'affichage du jeu. C'est la vue du patron de conception MVC.
//- - - - - - - - - - - - - - - - - - - - - - - - - - - - 
//
// Date de création :		2020/05/02
// Auteur :					Frédéric Bélanger

public class View {

	private JFrame frame;
	private Canvas2D canvas2D;
	private Canvas3D canvas3D;
	private CanvasFrame frameCanvas;
	


	
	public View() {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } 
				catch (Exception e) {
                }
				setFrameCanvas();
				
			}
		});
	}
	
	public void setupFrame() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(frameCanvas);
		frame.pack();
		frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
	
	public JFrame getJFrame() {
		return frame;
	}
	
	public void refresh() {
		canvas2D.repaint();
		canvas3D.repaint();
	}
	
	public void setCanvas2D(LevelMap levelMap, Player player, Camera camera, Ray [] rays) {
		// j'ai gardé mes dimension de 800*600 pour mes calculs mais j'ai ajouté + 1 pour l'affichange seulement. Si je ne le faisais pas
		// le dernier pixel de chaque rangée et de chaque colonne n'était pas affiché.
		Dimension canvasDim = new Dimension(Config.getDimCanvas2D().width + 1,Config.getDimCanvas2D().height);
		canvas2D = new Canvas2D(canvasDim, Config.getPosCanvas2D(), levelMap, player, camera, rays);
		frame.add(canvas2D);
	}
	
	
	public void setCanvas3D(Dimension dimension, Point position, PixelColumn [] pixelColumns)  {
		canvas3D = new Canvas3D(dimension, position, pixelColumns);
		frame.add(canvas3D);
	}
	
	
	private void setFrameCanvas() {
		frameCanvas = new CanvasFrame(Config.getDimWindow());
	}
	
	private void drawAllColumns() {}
	
	private void drawOneColumn(PixelColumn column) {}
	
}
