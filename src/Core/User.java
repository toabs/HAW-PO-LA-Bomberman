package Core;
/**
 * Abstract class of a user of the game
 */
public abstract class User {
	/**
	 * Instances variables
	 */
	private static final int POINTS_FOR_WINNING = 10;
	private int id;
	private int points = 0;
	
	/**
	 * Constructor 
	 * @param id	id of the user	
	 */
	public User(int id) {
		this.id = id;
	}

	/**
	 * Returns the id of the user
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Returns the points of the user
	 * @return points
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Sets the points of the player
	 * @param points
	 */
	public void setPoints(int points) {
		this.points = points;
	}
	
	/**
	 * Call when a player won and increases the points by one
	 */
	public void won() {
		points += POINTS_FOR_WINNING;
	}
	
	/**
	 * Returns the id of the action the user is doing next
	 * @param playboard
	 * @return action id
	 */
	abstract public int getAction(Playboard playboard);
	
	/**
	 * Only to reset the action id of a human player
	 * Ignore for KI!
	 */
	abstract public void resetMove();
	
	/**
	 * Tells a user that a game is over
	 * @param won			true = won, false = lost or draw
	 * @param playboard
	 */
	abstract public void gameOver(boolean won, Playboard playboard);
}
