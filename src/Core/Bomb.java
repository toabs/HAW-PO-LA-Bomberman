package Core;

import java.util.HashSet;
import java.util.Set;

/**
 * A bomb in the game
 *
 */
public class Bomb extends PlayboardObject {
	/**
	 * Constants
	 */
	private final int MIN_FIELD = 0;
	private final int EXPLODE_AT = 0;
	private final int ONE_FIELD = 1;
	/**
	 * Instance variables
	 */
	private int counter;
	private int explosionRadius;
	private int playerId;
	private boolean exploded = false;
	
	/**
	 * Constructor
	 * @param counter			Interations needed for a bomb to explode
	 * @param field				Field where the bomb lies on
	 * @param explosionRadius	Count of fields a bomb causes to explode in every direction
	 * @param playerId			Id of the player who laid the bomb
	 */
	public Bomb(int counter, Field field, int explosionRadius, int playerId) {	
		super(field);
		this.playerId = playerId;
		this.counter = counter;
		this.explosionRadius = explosionRadius;
	}
	
	/**
	 * Constructor for cloning
	 * @param bomb	another bomb
	 */
	private Bomb(Bomb bomb) {
		super(bomb.getField().clone());
		this.counter = bomb.counter;
		this.explosionRadius = bomb.explosionRadius;
		this.exploded = bomb.exploded;
	}
	
	/**
	 * Returns a copie of the object
	 */
	public Bomb clone() {
		return new Bomb(this);
	}

	/********* Getter *********/
	
	/**
	 * Returns the count of fields in X and Y direction of the playboard
	 * @return explosionRadius
	 */
	public int getExplosionRadius() {
		return explosionRadius;
	}
	
	/**
	 * Returns the id of the player who laid the bomb
	 * @return playerId
	 */
	public int getPlayerId() {
		return playerId;
	}

	/**
	 * Is the bomb already exploded?
	 * @return exploded?
	 */
	public boolean isExploded() {
		return exploded;
	}
	
	/**
	 * Returns the amount of interations left for the bomb to explode
	 * @return counter
	 */	
	public int getCounter() {
		return counter;
	}
	
	/**
	 * Is the bomb suppose to exlode this round?
	 * @return should explode?
	 */
	public boolean shouldExplode() {		
		return !exploded && counter == EXPLODE_AT;
	}

	/********* Setter *********/
	
	/**
	 * Sets the counter when the bomb will explode
	 * @param counter
	 */
	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	/**
	 * Sets the counter when the bomb will explode down by one
	 */
	public void countDown() {
		counter--;
	}	
	
	/**
	 * Causes this bomb to explode and returns all fields that will explode
	 * @param board
	 * @return explodingFields
	 */
	public Set<Field> explode(Field[][] board) {
		Set<Field> explodingFields = new HashSet<>();
		explodingFields.add(getField());
		for (int x = getX() + ONE_FIELD; x < explosionRadius + getX() && x < board.length; x++) {
			Field field = board[x][getY()];
			if (field.isExplodable()) {
				explodingFields.add(field);
			} else {
				break;
			}
		}
		for (int y = getY() + ONE_FIELD; y < explosionRadius + getY() && y < board[MIN_FIELD].length; y++) {
			Field field = board[getX()][y];
			if (field.isExplodable()) {
				explodingFields.add(field);
			} else {
				break;
			}
		}		
		for (int x = getX() - ONE_FIELD; x > getX() - explosionRadius && x >= MIN_FIELD; x--) {
			Field field = board[x][getY()];
			if (field.isExplodable()) {
				explodingFields.add(field);
			} else {
				break;
			}
		}
		for (int y = getY() - ONE_FIELD; y > getY() - explosionRadius && y >= MIN_FIELD ; y--) {
			Field field = board[getX()][y];
			if (field.isExplodable()) {
				explodingFields.add(field);
			} else {
				break;
			}
		}
		exploded = true;
		return explodingFields;
	}

}
