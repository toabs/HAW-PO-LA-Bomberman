package Core;

/**
 * Represents a field on the playboard
 */
public class Field {
	/**
	 * Instance variables
	 */
	private int x;
	private int y;
	private boolean passable = true;
	private boolean explodable = true;

	/********* Constructor *********/
	
	/**
	 * Constructor
	 * @param x   x-coordiante 
	 * @param y   y-coordiante 
	 */
	public Field(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Constructor
	 * @param x				x-coordiante 
	 * @param y				y-coordiante 
	 * @param passable		is the field passable?
	 * @param explodable	is the field explodable?
	 */
	public Field(int x, int y, boolean passable, boolean explodable) {
		this.x = x;
		this.y = y;
		this.passable = passable;
		this.explodable = explodable;
	}

	/**
	 * Constructor for clone
	 * @param field
	 */
	private Field(Field field) {
		this.x = field.x;
		this.y = field.y;
		this.passable = field.passable;
		this.explodable = field.explodable;
	}
	
	/**
	 * Returns a exact copy of the object
	 */
	public Field clone() {
		return new Field(this);
	}
	
	/********* Getters *********/
	
	/**
	 * Returns the x-coordinate of the field
	 * @return x
	 */	
	public int getX() {
		return x;
	}	

	/**
	 * Returns the y-coordinate of the field
	 * @return y
	 */	
	public int getY() {
		return y;
	}

	/**
	 * Is the field passable?
	 * @return passable?
	 */
	public boolean isPassable() {
		return passable;
	}
	
	/**
	 * Is the field explodable?
	 * @return explodable?
	 */
	public boolean isExplodable() {
		return explodable;
	}	

	/**
	 * Is the field not explodable and not passable?
	 * @return wall?
	 */
	public boolean isWall(){
		return !passable && !explodable;
	}
	
	/********* Setters *********/
	
	/**
	 * Sets the x-coordiante
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * Sets the y-coordiante
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	/**
	 * Sets whether the field is passable or not
	 * @param passable
	 */
	public void setPassable(boolean passable) {
		this.passable = passable;
	}
	
	/**
	 * Sets whether the field is explodable or not
	 * @param explodable
	 */	
	public void setExplodable(boolean explodable) {
		this.explodable = explodable;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Field other = (Field) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

}
