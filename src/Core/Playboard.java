package Core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * This class holds every information about the current state of the game
 */
public class Playboard {
	/**
	 * Instance variables
	 */
	private Field[][] board;
	private Set<Player> players = new HashSet<>();
	private Set<Bomb> bombs = new HashSet<>();;
	private int stepsLeft;
	private int explosionRadius;
	private int bombCounter;
	private final Set<Integer> POSSIBLE_ACTIONS = new HashSet<>(Arrays.asList(new Integer[]{ 0, 1, 2, 3, 4, 5 }));
		
	/**
	 * Constructor
	 * @param board				The board of the game
	 * @param stepsLeft			Steps that are left till the game is over
	 * @param explosionRadius	Count of fields a bomb causes to explode in every direction
	 * @param bombCounter		Maximum amout of iteration till the game ends in a draw
	 */
	public Playboard(Field[][] board, int stepsLeft, int explosionRadius, int bombCounter) {
		this.explosionRadius = explosionRadius;
		this.bombCounter = bombCounter;
		this.board = board;
		this.stepsLeft = stepsLeft;
	}
	
	/**
	 * Constructor for cloning
	 * @param playboard
	 */
	private Playboard(Playboard playboard) {
		this.board = playboard.board.clone();
		this.stepsLeft = playboard.stepsLeft;
		for (Bomb bomb : playboard.bombs) {
			this.bombs.add(bomb.clone());
		}
		for (Player player : playboard.players) {
			this.players.add(player.clone());
		}
	}
	
	/**
	 * Returns an exact copy of the object
	 */
	public Playboard clone() {
		return new Playboard(this);
	}

	/******* Getter *******/
	
	/**
	 * Returns the count of fields a bomb causes to explode in every direction
	 * @return
	 */
	public int getExplosionRadius() {
		return explosionRadius;
	}

	/**
	 * Returns the maximum amout of iteration till the game ends in a draw
	 * @return bombCounter
	 */
	public int getBombCounter() {
		return bombCounter;
	}
	
	/**
	 * Returns all action ids that a user can make
	 * @return POSSIBLE_ACTIONS
	 */
	public Set<Integer> getPossibleActions() {
		return POSSIBLE_ACTIONS;
	}
	
	/**
	 * Returns the board of the game
	 * @return board
	 */
	public Field[][] getBoard() {
		return board;
	}
	
	/**
	 * Returns all players currently in the game
	 * @return players
	 */
	public Set<Player> getPlayers() {
		return players;
	}

	/**
	 * Returns alls bombs currenty on the playboard
	 * @return
	 */
	public Set<Bomb> getBombs() {
		return bombs;
	}

	/**
	 * Returns the amount of steps that are left till the game is over
	 * @return stepsLeft
	 */
	public int getStepsLeft() {
		return stepsLeft;
	}
	
	/******* Setter *******/

	/**
	 * Sets the current players
	 * @param players
	 */
	public void setPlayers(Set<Player> players) {
		this.players = players;
	}
	
	/**
	 * Sets the bombs currenty on the board
	 * @param bombs
	 */
	public void setBombs(Set<Bomb> bombs) {
		this.bombs = bombs;
		
	}

	/******* Methods *******/

	/**
	 * Decreases the steps left by one
	 */
	public void decreaseStepsLeft() {
		stepsLeft--;
	}

	/**
	 * Adds a bomb to the playboard
	 * @param bombCounter		Maximum amout of iteration till the game ends in a draw	
	 * @param x					x-coordinate
	 * @param y					y-coordinate
	 * @param explosionRadius	Count of fields a bomb causes to explode in every direction
	 * @param playerId			Id of the player who planted the bomb
	 */
	public void addBomb(int bombCounter, int x, int y, int explosionRadius, int playerId) {
		Field field = board[x][y];
		field.setPassable(false);
		Bomb newBomb = new Bomb(bombCounter, field, explosionRadius, playerId);
		if (!bombs.contains(newBomb)) {
			bombs.add(newBomb);		
		}
	}


	
	
}
