package org.semillita.raycaster.core;

public class GameConfiguration {

	public static enum ColorTheme {
		YELLOW,
		GREEN,
		PURPLE;
	}
	
	private ColorTheme theme;
	private int FOV;
	private int difficulty;
	
	public GameConfiguration(ColorTheme theme, int FOV, int difficulty) {
		this.theme = theme;
		this.FOV = FOV;
	}
	
	public ColorTheme getTheme() {
		return theme;
	}
	
	public int getFOV() {
		return FOV;
	}
	
	public int getDifficulty() {
		return difficulty;
	}
	
}
