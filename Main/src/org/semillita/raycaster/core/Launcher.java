package org.semillita.raycaster.core;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Launcher {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Raycaster game";
		new LwjglApplication(new Game(), config);
	}
	
}