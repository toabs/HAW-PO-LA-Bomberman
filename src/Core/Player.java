package Core;


public class Player extends PlayboardObject{

	private int id;
	private boolean isAlive = true;
	
	public Player(int id, Field field) {
		super(field);
		this.id = id;
	}

	public boolean isAlive() {
		return isAlive;
	}

	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}	

	public int getId() {
		return id;
	}


}
