package org.semillita.raycaster.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {

	private static Game game;
	
	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Raycaster game";
		game = new Game();
		
		new LwjglApplication(game, config);
	}
	
	public static Game getGame() {
		return game;
	}
	
}