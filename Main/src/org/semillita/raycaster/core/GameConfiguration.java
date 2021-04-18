package org.semillita.raycaster.core;

public class GameConfiguration {

	public static enum ColorTheme {
		YELLOW,
		GREEN,
		PURPLE;
	}
	
	private ColorTheme theme;
	private int FOV;
	
	public GameConfiguration(ColorTheme theme, int FOV) {
		this.theme = theme;
		this.FOV = FOV;
	}
	
	public ColorTheme getTheme() {
		return theme;
	}
	
	public int getFOV() {
		return FOV;
	}
	
}
