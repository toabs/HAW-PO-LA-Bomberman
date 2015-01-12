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
	
	public Playboard clone() {
		return new Playboard(this);
	}

	public int getExplosionRadius() {
		return explosionRadius;
	}

	public int getBombCounter() {
		return bombCounter;
	}

	public void setBoard(Field[][] board) {
		this.board = board;
	}

	public Set<Integer> getPossibleActions() {
		return POSSIBLE_ACTIONS;
	}

	public void decreaseStepsLeft() {
		stepsLeft--;
	}

	public Field[][] getBoard() {
		return board;
	}
	
	public Set<Player> getPlayers() {
		return players;
	}

	public void setPlayers(Set<Player> players) {
		this.players = players;
	}

	public Set<Bomb> getBombs() {
		return bombs;
	}

	public void addBomb(int bombCounter, int x, int y, int explosionRadius, int playerId) {
		Field field = board[x][y];
		field.setPassable(false);
		Bomb newBomb = new Bomb(bombCounter, field, explosionRadius, playerId);
		if (!bombs.contains(newBomb)) {
			bombs.add(newBomb);		
		}
	}

	public int getStepsLeft() {
		return stepsLeft;
	}

	public void setBombs(Set<Bomb> bombs) {
		this.bombs = bombs;
		
	}	
	
}
