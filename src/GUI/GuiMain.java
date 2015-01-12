package GUI;

import java.util.ArrayList;
import java.util.List;

import Core.Game;
import Core.Human;
import Core.User;

public class GuiMain {
	public static void main(String[] args) {		
		List<User> users = new ArrayList<>();		
		
		// Replace this with your KIS
		users.add(new Human(1));
		users.add(new Human(2));
		
		// Config for the game
		int boardsize = 15; 
		int bombCounter = 8;
		int explosionArea = 4;
		int maxSteps = 100;
		long gameoverSleep = 1000; // miliseconds
		long stepSleep = 300; // miliseconds
		
		// To start the game
		new GuiStart(new Game(users, boardsize, bombCounter, explosionArea, maxSteps, stepSleep), gameoverSleep);
	}
}
