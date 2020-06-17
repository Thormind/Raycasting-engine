package model;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import javax.imageio.ImageIO;

import mathematic.MathTools;

public class TextureManager {
	private int nbWallSplit;
	private String wallTexturePath;
	private String darkWallTexturePath;
	private String [] wallTextureFiles; // both regular and dark textures have the same file names
	private Vector<BufferedImage> wallTextures;
	private Vector<Vector<BufferedImage>> splitWallsTextures;
	private Vector<BufferedImage> darkWallTextures;
	private Vector<Vector<BufferedImage>> splitDarkWallsTextures;
	
	public TextureManager(int nbWallSplit, String wallTexturePath, String darkWallTexturePath, String [] wallTextureFiles) throws IOException {
		this.nbWallSplit = nbWallSplit;
		this.wallTexturePath = wallTexturePath;
		this.darkWallTexturePath = darkWallTexturePath;
		this.wallTextureFiles = wallTextureFiles;
		this.wallTextures = new Vector<BufferedImage>();
		this.splitWallsTextures = new Vector<Vector<BufferedImage>>();
		this.darkWallTextures = new Vector<BufferedImage>();
		this.splitDarkWallsTextures = new Vector<Vector<BufferedImage>>();
		this.loadWallTextures();
		this.splitWallTextures();
	}
	
	private void loadWallTextures() {
		for(String textureFile : wallTextureFiles) {
			try {
				wallTextures.add(ImageIO.read(new File(wallTexturePath, textureFile)));
				darkWallTextures.add(ImageIO.read(new File(darkWallTexturePath, textureFile)));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public BufferedImage getWallTexturePart(int textureIndex, int partNumber, boolean isDark) {
		if(isDark) {
			return splitDarkWallsTextures.get(textureIndex).get(partNumber);
		}
		return splitWallsTextures.get(textureIndex).get(partNumber);
	}
	
	public Vector<BufferedImage> getDividedWallTextureSection(int textureIndex, double beginRatio, double endRatio, int nbSplit, boolean isDark){
		BufferedImage textureSection = getWallTextureSection(textureIndex, beginRatio, endRatio, nbSplit, isDark);
		return divideTexture(textureSection, nbSplit);
	}
	
	private BufferedImage getWallTextureSection(int textureIndex, double beginRatio, double endRatio, int nbSplit, boolean isDark) {
		BufferedImage texture = isDark? darkWallTextures.get(textureIndex) : wallTextures.get(textureIndex);
		int textureWidth = texture.getWidth();
		int beginPixelX = MathTools.doubleToInt(beginRatio * textureWidth);
		int endPixelX = MathTools.doubleToInt(endRatio * textureWidth);
		int sectionWidth = endPixelX-beginPixelX;
		
		// A section width must be at least equal to the amount of part it will be split into. Otherwise you will try to get a subimage with a width less than 1
		// pixel. This happens when the camera gets close to a wall and more than on ray is hitting the same wall part. Same reason we search a closest divider, its not possible to get a subimage with pixel fraction.
		
		int correctSectionWidth =  sectionWidth < nbSplit? nbSplit : MathTools.FindClosestDivisibleBy(endPixelX-beginPixelX, nbSplit, 0, textureWidth - beginPixelX);
		
		if(beginPixelX+correctSectionWidth > textureWidth) {
			System.out.println("Texture width incorrect");
		}
		
		beginPixelX = beginPixelX+correctSectionWidth > textureWidth ? textureWidth - correctSectionWidth : beginPixelX;

		return texture.getSubimage(beginPixelX, 0, correctSectionWidth, texture.getHeight());
	}
	
	private void splitWallTextures() {
		splitTextures(wallTextures, splitWallsTextures);
		splitTextures(darkWallTextures, splitDarkWallsTextures);
	}
	
	private void splitTextures(Vector<BufferedImage> textures, Vector<Vector<BufferedImage>> splitTextures) {
		for(BufferedImage texture: textures ) {
			splitTextures.add(divideTexture(texture, nbWallSplit));
		}
	}
	
	
	
	private Vector<BufferedImage> divideTexture(BufferedImage texture, int nbSplit) {
		int partWidth = texture.getWidth()/nbSplit;
		int partHeight = texture.getHeight();
		Vector<BufferedImage> splitTexture = new Vector<BufferedImage>();
		for(int i=0; i<nbSplit; ++i) {
			BufferedImage imagePart = texture.getSubimage(i * partWidth, 0,partWidth, partHeight);
			splitTexture.add(imagePart);
		}
		return splitTexture;
	}
	
}
