import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

public class ObjectManager implements ActionListener {

	public Rocketship rocketShip;

	public ArrayList<Projectile> projectiles = new ArrayList<>();

	public ArrayList<Alien> aliens = new ArrayList<Alien>();
  public ArrayList<Neighbor> neighbors= new ArrayList<Neighbor>();
	public Random random = new Random();
  static int i;
	private int score = 0;
  public static String currentImage = "";
	// Initializing rocket ship
	public ObjectManager(Rocketship rocketShip) {

		this.rocketShip = rocketShip;

	}

	// Adding projectiles to the arrayList<Projectiles>
	public void addProjectile(Projectile projectile) {

		projectiles.add(projectile);

	}

	// Adding aliens into the game panel at a random x-position
	public void addAlien() {

		aliens.add(new Alien(random.nextInt(Main.WIDTH), 40, 50, 50));

	}
  public void addNeighbor() {
    neighbors.add(new Neighbor(random.nextInt(Main.WIDTH),100,100,100));

	}
	// Object manager updates aliens and projectile independently
	public void update() {

		for (Alien alien : aliens) {

			alien.update();

			// if alien is above the screen, then remove
			if (alien.y >= Main.HEIGHT) {

				alien.isActive = false;

			}
		}
    for (Neighbor neighbor : neighbors) {

			neighbor.update();

			// if alien is above the screen, then remove
			if (neighbor.y >= Main.HEIGHT) {

				neighbor.isActive = false;

			}
      if (neighbor.x >= Main.WIDTH) {

				neighbor.isActive = false;

			}
		}

		for (Projectile projectile : projectiles) {

			projectile.update();

			// if projectile is below the screen, then remove the projectile
			if (projectile.y <= 0) {

				projectile.isActive = false;

			}
		}

		// Game is over is false if the rocket ship is inactive. Otherwise, checks for
		// collisions and purges objects from the frame.
		//if (rocketShip.isActive != false) {

			checkCollision();

			purgeObjects();

		//}
	}

	// Draws the rocket ship, alien, and projectiles in the game panel.
	
	public void draw(Graphics g) {

		rocketShip.draw(g);

		for (Alien alien : aliens) {

			alien.draw(g);

		}
    for(Neighbor neighbor:neighbors){
      neighbor.draw(g);
    }

		for (Projectile projectile : projectiles) {

			projectile.draw(g);

		}
	}

	// Iterates through ArrayList and removes any alien or projectile marked as not
	// active.
	
	public void purgeObjects() {

		// Using regular for loop instead of for-each since aliens are being removed
		for (int i = 0; i < aliens.size(); i++) {

			if (aliens.get(i).isActive == false) {

				aliens.remove(i);

			}
		}
    for (int i = 0; i < neighbors.size(); i++) {

			if (neighbors.get(i).isActive == false) {

				neighbors.remove(i);

			}
		}

		// Using regular for loop instead of for-each since projectiles are being
		// removed
		for (int i = 0; i < projectiles.size(); i++) {

			if (projectiles.get(i).isActive == false) {

				projectiles.remove(i);

			}
		}
	}

	// New alien appears in the game after one second
	@Override
	public void actionPerformed(ActionEvent e) {
  if(aliens.size()<10){
		addAlien();
      

  }
  if(neighbors.size()<1){
    addNeighbor();
  }
    

}

	// Checks collisions between alien and rocket or alien and projectile
	
	public void checkCollision() {

		// checks if aliens collide to the rocket ship
	for (Neighbor neighbor : neighbors) {

			boolean isThereCollision = neighbor.collisionBox.intersects(rocketShip.collisionBox);

			if (isThereCollision == true) {

				neighbor.isActive = false;

				rocketShip.isActive = false;

			}
  }
			
		
		
		// Check if the alien is colliding with the projectiles

		for (Projectile projectile : projectiles) {
			
			for (Alien alien : aliens) {

				boolean isThereCollisionAlien = projectile.collisionBox.intersects(alien.collisionBox);

				if (isThereCollisionAlien == true) {

					alien.isActive = false;

					projectile.isActive = false;
					
					// score is incremented when projectile hits an alien
          
					score++;


				}
			}
		}
	}
	
	// Returns current score
	public int getScore() {
	
		return score;
		
	}
}
