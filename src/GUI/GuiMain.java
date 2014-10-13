package GUI;

import java.util.HashSet;
import java.util.Set;

import Core.Game;
import Core.Human;
import Core.User;

public class GuiMain {
	public static void main(String[] args) {
		
		Set<User> players = new HashSet<>();		
		players.add(new Human(1));
		players.add(new Human(2));
		int boardsize = 15;
		int bombCounter = 8;
		int explosionArea = 4;
		int maxSteps = 50;
		
		new GuiStart(new Game(players, boardsize, bombCounter, explosionArea, maxSteps));
	}
}
