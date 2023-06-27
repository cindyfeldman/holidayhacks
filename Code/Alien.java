import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;

public class Alien extends GameObject {
	
	public static BufferedImage alienImage;
	public static boolean needAlienImage = true;
	public static boolean gotAlienImage = false;

	public Alien(int x, int y, int width, int height) {
		
		// Initialize all the parameters in GameObject
		super(x, y, width, height);
		
		// set the speed of the alien to 1
		super.speed = 1;
		
		// Loading background image of the alien
		if (needAlienImage) {
			
		    loadAlienImage ("Images/presentImage.png");
		    
		}
	}
	
	// Alien moves closer downward in the game panel
	public void update() {
		
		// Updates collisionBox in GameObject
		super.update();
		
	}
	
	// Draws alien into the panel
	public void draw(Graphics g) {
		
		if (gotAlienImage) {
			
			// Draws alien.png into the panel
			g.drawImage(alienImage, x, y, width, height, null);
			
		} else {
			
			// If not, then it draws a yellow rectangle
			g.setColor(Color.YELLOW);
			
			g.fillRect(x, y, width, height);
			
		}
	}
	
	void loadAlienImage(String imageFile) {
		
	    if (needAlienImage) {
	        try {
	            alienImage = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
	            gotAlienImage = true;
	        } catch (Exception e) {
	            
	        }
	        needAlienImage = false;
	    }
	}
}
