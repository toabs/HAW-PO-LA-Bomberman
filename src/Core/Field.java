package Core;

public class Field {
	
	private int x;
	private int y;
	private boolean passable = true;
	private boolean explodable = true;

	public Field(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Field(int x, int y, boolean passable, boolean explodable) {
		this.x = x;
		this.y = y;
		this.passable = passable;
		this.explodable = explodable;
	}

	private Field(Field field) {
		this.x = field.x;
		this.y = field.y;
		this.passable = field.passable;
		this.explodable = field.explodable;
	}
	
	public Field clone() {
		return new Field(this);
	}

	public int getX() {
		return x;
	}	

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isPassable() {
		return passable;
	}
	
	public void setPassable(boolean passable) {
		this.passable = passable;
	}

	public boolean isExplodable() {
		return explodable;
	}	

	public void setExplodable(boolean explodable) {
		this.explodable = explodable;
	}

	public boolean isWall(){
		return !passable && !explodable;
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
