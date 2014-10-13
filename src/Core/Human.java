package Core;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Human extends User implements KeyListener {

	public Human(int id) {
		super(id);
	}

	private int action;
	

	// 0 = stay
	// 1 = up
	// 2 = down
	// 3 = left
	// 4 = right
	// 5 = bomb

	@Override
	public void keyPressed(KeyEvent arg0) {
		if (getId() == 1) {			
			if (arg0.getKeyCode() == KeyEvent.VK_UP) {
				action = 1;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
				action = 2;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				action = 3;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				action = 4;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				action = 5;
			}
		} else {			
			if (arg0.getKeyCode() == KeyEvent.VK_W) {
				action = 1;
			}			
			if (arg0.getKeyCode() == KeyEvent.VK_S) {
				action = 2;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_A) {
				action = 3;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_D) {
				action = 4;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
				action = 5;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

		if (getId() == 1) {
			if (arg0.getKeyCode() == KeyEvent.VK_UP) {
				action = 1;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_DOWN) {
				action = 2;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_LEFT) {
				action = 3;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_RIGHT) {
				action = 4;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				action = 5;
			}
		} else {
			if (arg0.getKeyCode() == KeyEvent.VK_W) {
				action = 1;
			}			
			if (arg0.getKeyCode() == KeyEvent.VK_S) {
				action = 2;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_A) {
				action = 3;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_D) {
				action = 4;
			}
			if (arg0.getKeyCode() == KeyEvent.VK_CONTROL) {
				action = 5;
			}
		}

	}

	@Override
	public int getAction(Playboard playboard) {
		return action;
	}

	public void resetMove() {
		action = 0;
	}

	@Override
	public void gameOver(boolean won) {
		// do nothing
	}

}
