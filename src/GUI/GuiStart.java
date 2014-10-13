package GUI;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Core.Field;
import Core.Bomb;
import Core.Game;
import Core.Playboard;
import Core.Player;
import Core.User;

public class GuiStart extends JPanel implements Runnable {
	private static final long serialVersionUID = 1L;
	private int boardsize;

	private JFrame frame;
	private ImagePanel[][] guiBoard;
	private Game game;

	BufferedImage bombe;
	BufferedImage player1;
	BufferedImage player2;
	BufferedImage player1Bombe;
	BufferedImage player2Bombe;
	BufferedImage wall;
	BufferedImage freiesFeld;
	BufferedImage explosion;

	public GuiStart(Game game) {
		this.boardsize = game.getBoardSize();
		this.frame = new JFrame();
		this.guiBoard = new ImagePanel[boardsize][boardsize];
		this.game = game;
		init();
	}

	private void init() {
		setLayout(new GridLayout(boardsize, boardsize));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		for (int j = 0; j < boardsize; j++) {
			for (int i = 0; i < boardsize; i++) {
				ImagePanel tempPanel = new ImagePanel();
				guiBoard[i][j] = tempPanel;
				guiBoard[i][j].setPreferredSize(new Dimension(30, 30));
				add(tempPanel);
			}
		}
		loadPictures();
		frame.add(this);
		frame.pack();
		frame.setVisible(true);

		for (User user : game.getUsers()) {
			if (user instanceof Human) {
				frame.addKeyListener((Human) user);
			}
		};
		new Thread(this).start();
	}

	private void loadPictures() {
		try {
			bombe = ImageIO.read(new File(getClass().getResource("/GUI/Bombe.png").toURI()));
			player1Bombe = ImageIO.read(new File(getClass().getResource("/GUI/BombeSpieler1.png").toURI()));
			player2Bombe = ImageIO.read(new File(getClass().getResource("/GUI/BombeSpieler2.png").toURI()));
			player1 = ImageIO.read(new File(getClass().getResource("/GUI/Spieler1.png").toURI()));
			player2 = ImageIO.read(new File(getClass().getResource("/GUI/Spieler2.png").toURI()));
			wall = ImageIO.read(new File(getClass().getResource("/GUI/Wall.png").toURI()));
			explosion = ImageIO.read(new File(getClass().getResource("/GUI/Explosion.png").toURI()));
			freiesFeld = ImageIO.read(new File(getClass().getResource("/GUI/LeeresFeld.png").toURI()));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void run() {
		while (frame.isVisible()) {	
			boolean notGameOver = true;
			while (notGameOver) {
				notGameOver = !game.isGameOver();
				Playboard playboard = game.getPlayboard();
				
				for (Field[] row : playboard.getBoard()) {
					for (Field field : row) {
						if (field.isWall()) {
							guiBoard[field.getX()][field.getY()].setImage(wall);
						} else {
							guiBoard[field.getX()][field.getY()].setImage(freiesFeld);
						}
					}				
				}
				
				for (Bomb bomb : playboard.getBombs()) {
					guiBoard[bomb.getX()][bomb.getY()].setImage(bombe);
				}
				
				for (Player player : playboard.getPlayers()) {
					switch (player.getId()) {
					case 1:	
						guiBoard[player.getX()][player.getY()].setImage(player1);
						break;
					case 2:	
						guiBoard[player.getX()][player.getY()].setImage(player2);
						break;
					case 3:	
						guiBoard[player.getX()][player.getY()].setImage(player1);
						break;
					case 4:	
						guiBoard[player.getX()][player.getY()].setImage(player2);
						break;
					default:
						break;
					}
				}
				
				for (Field field : game.getExplodedFields()) {
					guiBoard[field.getX()][field.getY()].setImage(explosion);
				}
				try {
					game.doIteration();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();				
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (User user : game.getUsers()) {
				System.out.println("Spieler " + user.getId() + ": " + user.getPoints() + " Punkte");
			}
			System.out.println();
			game = new Game(game.getUsers(), game.getBoardSize(), game.getBombCounter(), game.getExplosionRadius(), game.getMaxSteps());
		}
	}
}
