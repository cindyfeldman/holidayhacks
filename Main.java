

import javax.swing.JFrame;

public class Main {
  // Declaring and initializing width
  public static final int WIDTH = 500;

  // Declaring and initializing height
  public static final int HEIGHT =800;

  // Creating window of the game
  JFrame window;

  // Declaring gamePanel
  GamePanel gamePanel;

  // Creating a league invader object and calling for setup method
  public static void main(String[] args) {

    Main leagueInvaders = new Main();

    leagueInvaders.setup();

  }

  // League invader constructor and initializing window
  public Main() {

    // Creating a game panel object
    gamePanel = new GamePanel(3);

    window = new JFrame();

    // Adding gamePanel to the window
    window.add(gamePanel);

    // Adding a key listener to the window
    window.addKeyListener(gamePanel);

  }

  // Preparing the league invader window
  public void setup() {

    window.setSize(WIDTH, HEIGHT);

    window.setVisible(true);

    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

  }

}
