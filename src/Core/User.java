package Core;

public abstract class User {
		
	private static final int POINTS_FOR_WINNING = 10;
	private int id;
	private int points = 0;
	
	public User(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void won() {
		points += POINTS_FOR_WINNING;
	}
	
	abstract public int getAction(Playboard playboard);
	
	abstract public void resetMove();
	
	abstract public void gameOver(boolean won);
}
