package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class Game {
	
	private final int PLAYER_RANGE = 1;
	private final int MIN_FIELD = 0;
	private final int INDEX_0 = 0;
	
	private Playboard playboard;
	private Map<User, Player> users = new HashMap<User, Player>();
	private List<User> usersList;
	private int maxBoardIndex;
	private LinkedList<Field> starting_fields = new LinkedList<>();
	private Set<Field> explodedFields = new HashSet<>();
	private int bombCounter;
	private int explosionRadius;
	private int boardSize;
	private boolean gameOver = false;
	private int maxSteps;
	

	public Game(List<User> usersList, int boardSize, int bombCounter, int explosionArea, int maxSteps) {
		this.boardSize = boardSize;		
		this.maxBoardIndex = boardSize - PLAYER_RANGE;		
		this.bombCounter = bombCounter;
		this.explosionRadius = explosionArea;
		this.maxSteps = maxSteps;
		this.usersList = usersList;		
		initializeBoard();
		initializePlayers();
	}
	
	private void initializePlayers() {
		initializeStartingFields();		
		for (User user : usersList) {			
			this.users.put(user, new Player(user.getId(), starting_fields.pop()));
		}
		playboard.setPlayers(new HashSet<Player>(this.users.values()));
	}

	private void initializeStartingFields() {
		Field[][] board = playboard.getBoard();
		starting_fields.add(board[MIN_FIELD][MIN_FIELD]);
		starting_fields.add(board[maxBoardIndex][maxBoardIndex]);
		starting_fields.add(board[maxBoardIndex][MIN_FIELD]);
		starting_fields.add(board[MIN_FIELD][maxBoardIndex]);		
				
	}
	
	private void initializeBoard() {
		Field[][] board = new Field[boardSize][boardSize];
		for (int i = 0; i < boardSize; i++) {
			for (int j = 0; j < boardSize; j++) {
				if (i % 2 == 1 && j % 2 == 1) {
					board[i][j] = new Field(i, j, false, false);
				} else {
					board[i][j] = new Field(i, j);
				}
			}
		}
		this.playboard = new Playboard(board, maxSteps);
	}
	
	public int getMaxSteps() {
		return maxSteps;
	}

	public int getBombCounter() {
		return bombCounter;
	}

	public int getExplosionRadius() {
		return explosionRadius;
	}
	
	public int getBoardSize() {
		return boardSize;
	}
	
	public List<User> getUsers(){
		List<User> usersList = new ArrayList<>();
		usersList.addAll(users.keySet());
		return usersList;
	}
	
	public Playboard getPlayboard() {
		return playboard;
	}	
	
	public Set<Field> getExplodedFields() {
		return explodedFields;
	}


	public boolean isGameOver() {
		return gameOver;
	}

	public void doIteration() throws InterruptedException {
		Thread.sleep(300);
		playerActions();	
		updatePlayboard();
		checkGameOver();
	}

	private void checkGameOver() {
		List<User> playersAlive = new ArrayList<>();
		for (Entry<User, Player> entry : users.entrySet()) {
			if (entry.getValue().isAlive()) {
				playersAlive.add(entry.getKey());
			}
		}
		if (playersAlive.size() == 1 && !gameOver) {
			User player = playersAlive.get(INDEX_0);
			player.won();
			player.gameOver(true);	
			
			//gameOver(false) to everyone, who lost			
			for(Entry<User, Player> entry : users.entrySet()) {
			    if(entry.getKey() != player) {
			        entry.getKey().gameOver(false);
			    }
			}
			gameOver = true;
		} else if (playersAlive.size() == 0 && !gameOver || playboard.getStepsLeft() == 0) {
			for (User user : usersList) {
				user.gameOver(false);
			}
			gameOver = true;
		}
		playboard.decreaseStepsLeft();
	}

	private void playerActions() {
	    //Get the playboard only once
	    Playboard currentBoard = playboard.clone();
	    
		for (Entry<User, Player> entry : users.entrySet()) {
			Player player = entry.getValue();
			User user = entry.getKey();
			Field field = player.getField();
			switch (user.getAction(currentBoard)) {
			case 1:
				if (field.getY() - PLAYER_RANGE >= MIN_FIELD) {
					setPlayerPosition(field.getX(), field.getY() - PLAYER_RANGE, player);
				}				
				break;
			case 2:
				if (field.getY() + PLAYER_RANGE <= maxBoardIndex) {
					setPlayerPosition(field.getX(), field.getY() + PLAYER_RANGE, player);
				}
				break;
			case 3:
				if (field.getX() - PLAYER_RANGE >= MIN_FIELD) {
					setPlayerPosition(field.getX() - PLAYER_RANGE, field.getY(), player);
				}
				break;
			case 4:
				if (field.getX() + PLAYER_RANGE <= maxBoardIndex) {
					setPlayerPosition(field.getX() + PLAYER_RANGE, field.getY(), player);
				}
				break;
			case 5:
				playboard.addBomb(bombCounter, player.getX(), player.getY(), explosionRadius);
				break;
			default:
				break;
			}
			user.resetMove();
		}
	}
	
	private void setPlayerPosition(int x, int y, Player player) {
		Field destination = playboard.getBoard()[x][y];
		if (destination.isPassable()) {
			player.setField(destination);
		}
	}
	
	private void updatePlayboard() {
		explodedFields = explodingFields();
		for (Player player : playboard.getPlayers()) {			
			for (Field field : explodedFields) {
				if (player.getField().equals(field)) {
					player.setAlive(false);
				}
			}			
		}
	}



	private Set<Field> explodingFields() {
		Set<Field> explodedFields = new HashSet<>();
		Set<Bomb> bombs = playboard.getBombs();
		for (Bomb bomb : bombs) {
			if (bomb.shouldExplode()) {				
				explodedFields.addAll(chainExplosions(bomb.explode(playboard.getBoard()), bombs));					
			} else {
				bomb.countDown();
			}
		}	
		Set<Bomb> bombsToRemove = new HashSet<>();
		for (Bomb bomb : bombs) {
			if (bomb.isExploded()) {
				bomb.getField().setPassable(true);
				bombsToRemove.add(bomb);
			}
		}
		bombs.removeAll(bombsToRemove);
		playboard.setBombs(bombs);
		return explodedFields;
	}
	
	private Set<Field> chainExplosions(Set<Field> explodedFields, Set<Bomb> bombs) {
		for (Bomb bomb : bombs) {
			if (explodedFields.contains(bomb.getField()) && !bomb.isExploded()) {
				explodedFields.addAll(bomb.explode(playboard.getBoard()));	
				chainExplosions(explodedFields, bombs);					
			}			
		}
		return explodedFields;
	}
	
}
