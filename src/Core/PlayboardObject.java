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
