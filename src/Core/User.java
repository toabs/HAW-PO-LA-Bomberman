package Core;

import java.awt.event.KeyListener;

public abstract class User implements KeyListener {
		
	private static final int POINTS_FOR_WINNING = 10;
	private int id;
	private int points = 0;
	
	public User(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}
	
	abstract public int getAction(Playboard playboard);
	
	abstract public void resetMove();

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public void won() {
		points += POINTS_FOR_WINNING;
	}
}
