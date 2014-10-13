package Core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Playboard {
	
	private Field[][] board;
	private Set<Player> players;
	private Set<Bomb> bombs = new HashSet<>();;
	private int stepsLeft;
	private final Set<Integer> POSSIBLE_ACTIONS = new HashSet<>(Arrays.asList(new Integer[]{ 0, 1, 2, 3, 4, 5 }));
		
	public Playboard(Field[][] board, int stepsLeft) {
		this.board = board;
		this.stepsLeft = stepsLeft;
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

	public void addBomb(int bombCounter, int x, int y, int explosionRadius) {
		Field field = board[x][y];
		field.setPassable(false);
		bombs.add(new Bomb(bombCounter, field, explosionRadius));
	}

	public int getStepsLeft() {
		return stepsLeft;
	}

	public void setBombs(Set<Bomb> bombs) {
		this.bombs = bombs;
		
	}	
	
}
