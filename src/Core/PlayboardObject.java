package Core;

/**
 * Abstract class of an object that is on the playboard
 */
public abstract class PlayboardObject {
	/**
	 * Instance variables
	 */
	private Field field;
	
	/**
	 * Constructor
	 * @param field		field where the object lies on
	 */
	public PlayboardObject(Field field) {
		this.field = field;
	}
	
	/**
	 * Constructor
	 */
	public PlayboardObject() {
	}
	
	/**
	 * Returns the x-coordinate of the object
	 * @return x-coordinate
	 */
	public int getX() {
		return field.getX();
	}

	/**
	 * Returns the y-coordinate of the object
	 * @return y-coordinate
	 */
	public int getY() {
		return field.getY();
	}

	/**
	 * Returns the field where the object lies on
	 * @return field
	 */
	public Field getField() {
		return field;
	}

	/**
	 * Sets the field where the object lies on
	 * @param field
	 */
	public void setField(Field field) {
		this.field = field;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((field == null) ? 0 : field.hashCode());
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
		PlayboardObject other = (PlayboardObject) obj;
		if (field == null) {
			if (other.field != null)
				return false;
		} else if (!field.equals(other.field))
			return false;
		return true;
	}
}
