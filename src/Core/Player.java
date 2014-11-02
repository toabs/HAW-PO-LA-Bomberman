package Core;


public class Player extends PlayboardObject{

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

	private int id;
	private boolean isAlive = true;
	
	public Player(int id, Field field) {
		super(field);
		this.id = id;
	}
	
	private Player(Player player) {
		super(player.getField().clone());
		this.id = player.id;
	}
	
	public Player clone() {
		return new Player(this);
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
