package GUI;

import java.util.ArrayList;
import java.util.List;

import Core.Game;
import Core.Human;
import Core.User;

public class GuiMain {
	public static void main(String[] args) {
		
		List<User> users = new ArrayList<>();		
		users.add(new Human(1));
		users.add(new Human(2));
		int boardsize = 15;
		int bombCounter = 8;
		int explosionArea = 4;
		int maxSteps = 100;
		
		new GuiStart(new Game(users, boardsize, bombCounter, explosionArea, maxSteps));
	}
}
