package Core;

/**
 * Represents the player figure on the playboard
 */
public class Player extends PlayboardObject{
	/**
	 * Instance variables
	 */
	private int id;
	private boolean isAlive = true;
	
	/**
	 * Constructor
	 * @param id		id of the player
	 * @param field		field where the player stands on
	 */
	public Player(int id, Field field) {
		super(field);
		this.id = id;
	}
	
	/**
	 * Constructor for cloning
	 * @param player
	 */
	private Player(Player player) {
		super(player.getField().clone());
		this.id = player.id;
	}
	
	/**
	 * Returns a exact copy of the object
	 */
	public Player clone() {
		return new Player(this);
	}

	/**
	 * Is the player alive?
	 * @return isAlive
	 */
	public boolean isAlive() {
		return isAlive;
	}
	
	/**
	 * Returns the id of the player
	 * @return id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets whether the player is alive or not
	 * @param isAlive
	 */
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}		

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}


}
