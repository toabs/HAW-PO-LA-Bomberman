package Core;

import java.util.HashSet;
import java.util.Set;

public class Bomb extends PlayboardObject {

	private final int MIN_FIELD = 0;
	private final int EXPLODE_AT = 0;
	private final int ONE_FIELD = 1;
	
	private int counter;
	private int explosionRadius;
	private int playerId;
	private boolean exploded = false;
	


	public Bomb(int counter, Field field, int explosionRadius, int playerId) {	
		super(field);
		this.playerId = playerId;
		this.counter = counter;
		this.explosionRadius = explosionRadius;
	}
	
	private Bomb(Bomb bomb) {
		super(bomb.getField().clone());
		this.counter = bomb.counter;
		this.explosionRadius = bomb.explosionRadius;
		this.exploded = bomb.exploded;
	}
	
	public Bomb clone() {
		return new Bomb(this);
	}

	public int getExplosionRadius() {
		return explosionRadius;
	}
	
	public int getPlayerId() {
		return playerId;
	}

	public boolean isExploded() {
		return exploded;
	}
	
	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
	public void countDown() {
		counter--;
	}
	
	public boolean shouldExplode() {		
		return !exploded && counter == EXPLODE_AT;
	}

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
