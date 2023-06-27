import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, KeyListener {

	final int MENU = 0;
	final int GAME = 1;
	final int END = 2;

	public static BufferedImage titleImage;
  public static BufferedImage backgroundImage;
  public static BufferedImage endImage;
	public static boolean needBackgroundImage = true;
	public static boolean gotBackgroundImage = false;	
	public static boolean needTitleImage = true;
	public static boolean gotTitleImage = false;	
  public static boolean needEndImage = true;
  public static boolean gotEndImage = false;

  int i;
	// Declaring time variable
	Timer frameDraw;
  Timer backgroundChange;
	// Alien spawns member variable
	Timer alienSpawn;
  Timer neighborSpawn;

	// Setting the state of the game
	int currentState = MENU;

	// Declaring fonts for variables
	Font enterFont, spaceFont, scoreFont;

	// Instantiating rocket ship and passing x, y, width, and height positions
	Rocketship rocketShip = new Rocketship(250, 700, 100, 100);

	// Declaring and initializing objectManage and passing rocket Ship as a
	// parameter
	ObjectManager objectManager = new ObjectManager(rocketShip);


	// Initializing GamePanel. Sets the font of title, enter, space, and variables.
	public GamePanel(int seconds) {
    
		enterFont = new Font("Arial", Font.PLAIN, 30);

		spaceFont = new Font("Arial", Font.PLAIN, 25);
		
		scoreFont = new Font("Arial", Font.BOLD, 40);

		frameDraw = new Timer(1000 / 60, this);
		// We start drawing the frame repeatedly
		frameDraw.start();
    
		// Calling the image to be in the background
		if (needBackgroundImage) {
            loadImage("Images/ChristmasTree.jpg");
		}
    	if (needTitleImage) {
			loadTitleImage("Images/christmasLooters.png");
		}
	}
	// Depending on the current state, either the program calls drawMenuState,
	// or drawGameState, or drawEndState.
	@Override
	public void paintComponent(Graphics g) {

		if (currentState == MENU) {

			drawMenuState(g);

		} else if (currentState == GAME) {

			drawGameState(g);

		} else if (currentState == END) {

			drawEndState(g);

		}
	}

	void updateMenuState() {

	}

	void updateGameState() {
				
		if( rocketShip.isActive == true) {
			
			objectManager.update();
			
		} else {
			
			currentState = END;
			
		}
	}

	void updateEndState() {

	}

	// The color of the window becomes blue when the game begins
	void drawMenuState(Graphics g) {

		// Setting the color of the window to blue
		g.setColor(Color.BLACK);

		// Drawing a rectangle for the background
		g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);

		// sets the title
     if (gotTitleImage) {
			
			// Drawing the space image in the background
			g.drawImage(titleImage, 125, 200, 250, 100, null);
			
		} else {
			
			// If no space image, then draw a black rectangle for the background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		}
	  g.setColor(Color.YELLOW);
		// Sets the color of ENTER to start and its position
		g.setFont(enterFont);
		g.setColor(Color.YELLOW);
		g.drawString("Press ENTER to start", 100, 400);

		// Sets the color of SPACE for instructions and its position
		g.setFont(spaceFont);
		g.setColor(Color.YELLOW);
		g.drawString("How to play: Use the arrow keys",50,425) ;
    g.drawString("to move around the screen.",50,450);
    g.drawString("Press SPACE to shoot at the  ", 50, 475);
    g.drawString("presents. Goal: Avoid neighbor! ", 50, 500); 

	}

	// The color of the window becomes black when the game is in session
	void drawGameState(Graphics g) {
		
		String currentScore = Integer.toString(objectManager.getScore());
		
		if (gotBackgroundImage) {
			 
			// Drawing the space image in the background
			g.drawImage(backgroundImage, 0, 0, Main.WIDTH, Main.HEIGHT, null);
     
			
		} else {
			
			// If no space image, then draw a black rectangle for the background
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		}
		
		// Sets the font and string of current score
		g.setFont(scoreFont);
		g.setColor(Color.RED);
		g.drawString("Score: " + currentScore, 30, 50);
    
		if(currentScore.equals("10")){  
      alienSpawn.stop();
      neighborSpawn.stop();
      currentState++;
    }
		
		// Drawing a rocket ship in the game panel
		objectManager.draw(g);

	}

	// The color of the window becomes red when the game ends
	void drawEndState(Graphics g) {

  if (gotBackgroundImage) {
			if(objectManager.getScore()==10){
	      if (needEndImage) {
			    loadEndImage("Images/angryNeighborWin.jpg");
		    }   
        
      }
      else{
         if (needEndImage) {
			    loadEndImage("Images/endScreenLose.jpg");
		    }    
      }
			g.drawImage(endImage, 0, 0, Main.WIDTH, Main.HEIGHT, null);
     
			
		}
     else {
			
			// If no space image, then draw a black rectangle for the background
			  g.setColor(Color.BLACK);
		  	g.fillRect(0, 0, Main.WIDTH, Main.HEIGHT);
		}
		// Drawing a rectangle for the background
		// Set the color of killed enemies and its position
    if(objectManager.getScore()==10){
		g.setFont(enterFont);
		g.setColor(Color.BLACK);
		g.drawString("You stole " + objectManager.getScore() + "/10 presents", 75, 65);

		// Sets the color of Enter to restart and its position
		g.setFont(spaceFont);
		g.setColor(Color.BLACK);
    g.drawString("Press ENTER to restart", 100, 100);
    g.drawString("Congrats!! You WON.",150,500);
  }
  else{
    g.setFont(enterFont);
    g.setColor(Color.YELLOW);
    g.drawString("You LOST",170,75);
   g.drawString("Press ENTER to restart", 100, 100);
   currentState = END;

  }
	}

	// Checks the state of the game 60 frames a second a
	// and calls the appropriate method
	@Override
	public void actionPerformed(ActionEvent e) {
		if (currentState == MENU) {

			updateMenuState();

		} else if (currentState == GAME) {

			updateGameState();

		} else if (currentState == END) {

			updateEndState();

		}


		// Calls repaint method and the frame becomes redrawn
		repaint();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	@Override
	public void keyPressed(KeyEvent e) {

		int key = e.getKeyCode();

		// Increases the current state of the game and loops back to MENU
		if (key == KeyEvent.VK_ENTER) {

			if (currentState == END) {

				currentState = MENU;
				
				alienSpawn.stop();
				neighborSpawn.stop();
				rocketShip = new Rocketship(250, 700, 50, 50);
				
				objectManager = new ObjectManager(rocketShip);
				
			} else {

				// Changes the current state to GAME
				currentState++;
				
				// game starts and aliens begin to spawn
				startGame();

			}
		}

		// Checks keys only if the current state of the game is GAME
    
		if (currentState == GAME) {

			if (key == KeyEvent.VK_UP) {
				rocketShip.up();
			}
			
			if (key == KeyEvent.VK_DOWN) {
				rocketShip.down();
			}
			
			if (key == KeyEvent.VK_LEFT) {
				rocketShip.left();
			}
			
			if (key == KeyEvent.VK_RIGHT) {
				rocketShip.right();
				
			}
			
			// A projectile is created when the space bar is pressed
			if( key == KeyEvent.VK_SPACE) {
				objectManager.addProjectile(rocketShip.getProjectile());
        
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	// Loads the space.png image into the background
	void loadImage(String imageFile) {
		
	    if (needBackgroundImage) {
	    	
	        try {
	        	
	            backgroundImage = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
	            gotBackgroundImage = true;
	            
	        } catch (Exception e) {
	            
	        }
	        
	        needBackgroundImage = false;
	    }
	}
  void loadTitleImage(String imageFile) {
		
	    if (needTitleImage) {
	    	
	        try {
	        	
	            titleImage = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
	            gotTitleImage = true;
	            
	        } catch (Exception e) {
	            
	        }
	        
	        needTitleImage = false;
	    }
	}
  void loadEndImage(String imageFile) {
		
	    if (needEndImage) {
	    	
	        try {
	        	
	            endImage = ImageIO.read(this.getClass().getResourceAsStream(imageFile));
	            gotEndImage = true;
	            
	        } catch (Exception e) {
	            
	        }
	        
	        needEndImage = false;
	    }
	}
	
	
	// Create aliens in the game when the game is started
	void startGame() {
		
		// spawns a new alien every second and the reference is the objectManager, 
		// where the code of the alien will is implemented
  
	    alienSpawn = new Timer(1000 , objectManager);
	    alienSpawn.start();
      neighborSpawn = new Timer(3000,objectManager);
     backgroundChange = new Timer(4000,objectManager);
      backgroundChange.start();
	}

}

