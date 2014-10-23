package Core;

public abstract class PlayboardObject {

	private Field field;
	
	public PlayboardObject(Field field) {
		this.field = field;
	}
	
	public PlayboardObject() {
	}
	
	public int getX() {
		return field.getX();
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

	public int getY() {
		return field.getY();
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
}
