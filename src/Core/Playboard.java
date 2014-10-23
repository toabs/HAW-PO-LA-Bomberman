package Core;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Playboard {
	
	private Field[][] board;
	private Set<Player> players = new HashSet<>();
	private Set<Bomb> bombs = new HashSet<>();;
	private int stepsLeft;
	private final Set<Integer> POSSIBLE_ACTIONS = new HashSet<>(Arrays.asList(new Integer[]{ 0, 1, 2, 3, 4, 5 }));
		
	public Playboard(Field[][] board, int stepsLeft) {
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
