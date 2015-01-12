package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Core.Field;
import Core.Bomb;
import Core.Game;
import Core.Human;
import Core.Playboard;
import Core.Player;
import Core.User;


public class GuiStart extends JPanel implements Runnable {
	/**
	 * Constants
	 */
	private static final String RESOURCE_FOLDER = File.separator + "resources";
	
	/**
	 * Instance variables
	 */
	private static final long serialVersionUID = 1L;
	private int boardsize;
	private boolean end = false;
	private JFrame frame;
	private ImagePanel[][] guiBoard;
	private Game game;
	private ImageHelper imageHelper = new ImageHelper();
	private BufferedImage[] playerImages;
	private BufferedImage[] playerWithBombImages;
	BufferedImage bombImg;
	BufferedImage wall;
	BufferedImage emptyField;
	BufferedImage explosion;
	private long gameoverSleep;
	
	/******** Constructor and Initializer ********/
	
	/**
	 * Constructor
	 * @param game
	 * @param gameoverSleep
	 */
	public GuiStart(Game game, long gameoverSleep) {
		this.gameoverSleep = gameoverSleep;
		this.boardsize = game.getBoardSize();
		this.frame = new JFrame();
		this.guiBoard = new ImagePanel[boardsize][boardsize];
		this.game = game;
		init();
		new Thread(this).start();
	}

	/**
	 * Initializes the GUI and the game
	 */
	private void init() {
		setLayout(new GridLayout(boardsize, boardsize));
		frame.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e){
		        GuiStart.this.end();                  
            }
		});
		initPanel();
		initPictures();
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
		initKeyListener();
	}
	
	/**
	 * Initializes the image panel
	 */
	private void initPanel() {
		for (int j = 0; j < boardsize; j++) {
			for (int i = 0; i < boardsize; i++) {
				ImagePanel tempPanel = new ImagePanel();
				guiBoard[i][j] = tempPanel;
				guiBoard[i][j].setPreferredSize(new Dimension(30, 30));
				add(tempPanel);
			}
		}
	}
	
	/**
	 * Initializes the key listener for human players
	 */
	private void initKeyListener() {
		for (User user : game.getUsers()) {
			if (user instanceof Human) {
				frame.addKeyListener((Human) user);
			}
		};
	}
	
	/**
	 * Initializes all pictures needed for the GUI
	 */
    private void initPictures() {
		try {			
			Set<Player> players = game.getPlayboard().getPlayers();
			playerImages = new BufferedImage[players.size()];
			playerWithBombImages = new BufferedImage[players.size()];
			for (Player player : players) {
				int index = player.getId() - 1;
				Color nextColor = imageHelper.getNextColor();
				playerImages[index] = imageHelper.colorImage(readImage("player.png"), nextColor);
				playerWithBombImages[index] = imageHelper.colorImage(readImage("player_with_bomb.png"), nextColor);
			}
			bombImg = readImage("bomb.png");
			wall = readImage("wall.png");
			explosion = readImage("explosion.png");
			emptyField = readImage("emptyField.png");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Called if the thread ends
	 */
    protected void end()    {
        this.end = true;
    }


    /**
     * Game loop
     */
	@Override
	public void run() {
		while (!end) {	
			boolean notGameOver = true;
			while (notGameOver && !end) {
				notGameOver = !game.isGameOver();
				Playboard playboard = game.getPlayboard();				
				for (Field[] row : playboard.getBoard()) {
					for (Field field : row) {
						guiBoard[field.getX()][field.getY()].setImage(field.isWall() ? wall : emptyField);
					}				
				}				
				for (Bomb bomb : playboard.getBombs()) guiBoard[bomb.getX()][bomb.getY()].setImage(bombImg);
				for (Player player : playboard.getPlayers()) {
					guiBoard[player.getX()][player.getY()].setImage(imageForPlayer(player, playboard.getBombs()));
				}				
				for (Field field : game.getExplodedFields()) guiBoard[field.getX()][field.getY()].setImage(explosion);
				try {
					game.doIteration();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();				
			}
			gameover();
		}
        System.exit(0);
	}

	/**
	 * Starts a new game if the old game is over 
	 */
	private void gameover() {
		try {
			Thread.sleep(gameoverSleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		for (User user : game.getUsers()) {
			System.out.println("Spieler " + user.getId() + ": " + user.getPoints() + " Punkte");
		}
		game = new Game(game.getUsers(), game.getBoardSize(), game.getBombCounter(), game.getExplosionRadius(), game.getMaxSteps(), game.getStepSleep());
		
	}
	
	/********* Helpers *********/
	
	/**
	 * Returns a image
	 * @param filename		Name of the image in the resource folder
	 * @return
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	private BufferedImage readImage(String filename) throws IOException, URISyntaxException {
		return ImageIO.read(new File(getClass().getResource(RESOURCE_FOLDER + File.separator + filename).toURI()));
	}

	/**
	 * Loads the player images
	 * @param player		Current players
	 * @param bombs			Current bombs
	 * @return
	 */
	private BufferedImage imageForPlayer(Player player, Set<Bomb> bombs) {
		for (Bomb bomb : bombs) {
			if (bomb.getField() == player.getField()) {
				return playerWithBombImages[player.getId() - 1];
			}			
		}
		return playerImages[player.getId() - 1];
	}
}
