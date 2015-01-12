package Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Class that handles one iteration of the game
 * and holds all informations of the current game
 */
public class Game {
	
	/**
	 * Constants
	 */
	private final int PLAYER_RANGE = 1;
	private final int MIN_FIELD = 0;
	private final int INDEX_0 = 0;
	
	/**
	 * Instance variables
	 */
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
	private long stepSleep;
	
	/******* Constructor and Initializer *******/
	
	/**
	 * Constructor
	 * @param usersList			List of users that are playing the game
	 * @param boardSize			Count of fields in X and Y direction of the playboard
	 * @param bombCounter		Interations needed for a bomb to explode
	 * @param explosionRadius 	Count of fields a bomb causes to explode in every direction
	 * @param maxSteps			Maximum amout of iteration till the game ends in a draw
	 * @param stepSleep			Time to wait between each iteration
	 */
	public Game(List<User> usersList, int boardSize, int bombCounter, int explosionRadius, int maxSteps, long stepSleep) {
		this.boardSize = boardSize;		
		this.maxBoardIndex = boardSize - PLAYER_RANGE;		
		this.bombCounter = bombCounter;
		this.explosionRadius = explosionRadius;
		this.maxSteps = maxSteps;
		this.usersList = usersList;		
		this.stepSleep = stepSleep;
		initializeBoard();
		initializePlayers();
	}
	
	/**
	 * Initializes the player objects for the users
	 */
	private void initializePlayers() {
		initializeStartingFields();		
		for (User user : usersList) {			
			this.users.put(user, new Player(user.getId(), starting_fields.pop()));
		}
		playboard.setPlayers(new HashSet<Player>(this.users.values()));
	}

	/**
	 * Initializes the fields where player start on
	 */
	private void initializeStartingFields() {
		Field[][] board = playboard.getBoard();
		starting_fields.add(board[MIN_FIELD][MIN_FIELD]);
		starting_fields.add(board[maxBoardIndex][maxBoardIndex]);
		starting_fields.add(board[maxBoardIndex][MIN_FIELD]);
		starting_fields.add(board[MIN_FIELD][maxBoardIndex]);		
				
	}
	
	/**
	 * Initializes the board the game will take place on
	 */
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
		this.playboard = new Playboard(board, maxSteps, explosionRadius, bombCounter);
	}
	
	/******* Getter *******/
	
	/**
	 * Returns maximum amout of iteration till the game ends in a draw
	 * @return maxSteps
	 */
	public int getMaxSteps() {
		return maxSteps;
	}

	/**
	 * Returns the amount of interations needed for a bomb to explode
	 * @return bombCounter
	 */
	public int getBombCounter() {
		return bombCounter;
	}

	/**
	 * Returns the count of fields a bomb causes to explode in every direction
	 * @return explosionRadius
	 */
	public int getExplosionRadius() {
		return explosionRadius;
	}
	
	/**
	 * Returns the count of fields in X and Y direction of the playboard
	 * @return explosionRadius
	 */
	public int getBoardSize() {
		return boardSize;
	}
	
	/**
	 * Returns the list of users that are playing the game
	 * @return explosionRadius
	 */
	public List<User> getUsers(){
		List<User> usersList = new ArrayList<>();
		usersList.addAll(users.keySet());
		return usersList;
	}
	
	/**
	 * Returns the playboard with every information about the current state of the game
	 * @return playboard
	 */
	public Playboard getPlayboard() {
		return playboard;
	}	
	
	/**
	 * Returns all fields that are affected by an explosion
	 * @return explodedFields
	 */
	public Set<Field> getExplodedFields() {
		return explodedFields;
	}

	/**
	 * Is the game over?
	 * @return gameOver
	 */
	public boolean isGameOver() {
		return gameOver;
	}
	
	
	/**
	 * Returns the time the game sleeps between every iteration
	 * @return stepSleep
	 */
	public long getStepSleep() {
		return stepSleep;
	}
	
	/******* Public Methods *******/	

	/**
	 * Does a iteration of the game. Sleeps for a certain amount of time
	 * @throws InterruptedException
	 */
	public void doIteration() throws InterruptedException {
		Thread.sleep(stepSleep);
		playerActions();	
		updatePlayboard();
		checkGameOver();
	}

	/******* Private Helpers *******/		
	
	/**
	 * Checks if the game is over
	 */
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
			player.gameOver(true, playboard);	
			
			//gameOver(false) to everyone, who lost			
			for(Entry<User, Player> entry : users.entrySet()) {
			    if(entry.getKey() != player) {
			        entry.getKey().gameOver(false, playboard);
			    }
			}
			gameOver = true;
		} else if (playersAlive.size() == 0 && !gameOver || playboard.getStepsLeft() == 0) {
			for (User user : usersList) {
				user.gameOver(false, playboard);
			}
			gameOver = true;
		}
		playboard.decreaseStepsLeft();
	}

	/**
	 * Executes all player actions
	 */
	private void playerActions() {
	    //Get the playboard only once
	    Playboard currentBoard = playboard.clone();
	    
		for (Entry<User, Player> entry : users.entrySet()) {
			Player player = entry.getValue();
			User user = entry.getKey();
			switch (user.getAction(currentBoard)) {
			case 1:
				if (player.getY() - PLAYER_RANGE >= MIN_FIELD) {
					setPlayerPosition(player.getX(), player.getY() - PLAYER_RANGE, player);
				}				
				break;
			case 2:
				if (player.getY() + PLAYER_RANGE <= maxBoardIndex) {
					setPlayerPosition(player.getX(), player.getY() + PLAYER_RANGE, player);
				}
				break;
			case 3:
				if (player.getX() - PLAYER_RANGE >= MIN_FIELD) {
					setPlayerPosition(player.getX() - PLAYER_RANGE, player.getY(), player);
				}
				break;
			case 4:
				if (player.getX() + PLAYER_RANGE <= maxBoardIndex) {
					setPlayerPosition(player.getX() + PLAYER_RANGE, player.getY(), player);
				}
				break;
			case 5:
				playboard.addBomb(bombCounter, player.getX(), player.getY(), explosionRadius, player.getId());
				break;
			default:
				break;
			}
			user.resetMove();
		}
	}
	
	/**
	 * Sets the position of a certain player to the coordinates x and y
	 * @param x
	 * @param y
	 * @param player
	 */
	private void setPlayerPosition(int x, int y, Player player) {
		Field destination = playboard.getBoard()[x][y];
		if (destination.isPassable()) {
			player.setField(destination);
		}
	}
	
	/**
	 * Updates the player isAlive status if a player is in an explosion
	 */
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


	/**
	 * Calculates all fields that are exploding in this iteration and 
	 * sets the counter of every bomb that is not exploding minus one
	 * @return
	 */
	private Set<Field> explodingFields() {
		Set<Field> explodedFields = new HashSet<>();
		Set<Bomb> bombs = playboard.getBombs();
		explodedFields = chainExplosions(explodedFields);
		Set<Bomb> bombsToRemove = new HashSet<>();
		for (Bomb bomb : bombs) {
			if (bomb.isExploded()) {
				bomb.getField().setPassable(true);
				bombsToRemove.add(bomb);
			} else {
				bomb.countDown();
			}
		}
		bombs.removeAll(bombsToRemove);
		playboard.setBombs(bombs);
		return explodedFields;
	}
	
	/**
	 * Helper to calculate a chain explosion of a bomb
	 * @param explodedFields
	 * @return exploding fields from chainexplosion
	 */
	private Set<Field> chainExplosions(Set<Field> explodedFields) {
		for (Bomb bomb : playboard.getBombs()) {
			if (bomb.shouldExplode() || explodedFields.contains(bomb.getField()) && !bomb.isExploded()) {
				explodedFields.addAll(bomb.explode(playboard.getBoard()));	
				return chainExplosions(explodedFields);					
			}			
		}
		return explodedFields;
	}
	
}
