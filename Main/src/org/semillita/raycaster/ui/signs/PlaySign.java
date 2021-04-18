package org.semillita.raycaster.ui.signs;

import static org.semillita.raycaster.core.Game.State;
import org.semillita.raycaster.ui.DifficultyButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlaySign {
	
	private double sizeDenominator;
	
	private Texture difficulty;
	
	private DifficultyButton easy;
	private DifficultyButton medium;
	private DifficultyButton hard;
	
	public PlaySign(double sizeDenominator) {
		this.sizeDenominator = sizeDenominator;
		
		difficulty = new Texture("Resources/Difficulty.png");
		
		easy = new DifficultyButton(new Texture("Resources/Easy.png"), 0);
		medium = new DifficultyButton(new Texture("Resources/Medium.png"), 1);
		hard = new DifficultyButton(new Texture("Resources/Hard.png"), 2);
	}
	
	public void render(SpriteBatch batch, int y, int signWidth, int signHeight) {
		batch.draw(difficulty, (int) (Gdx.graphics.getWidth() / 2 - (difficulty.getWidth() / sizeDenominator) / 2), (int) (y + signHeight / 2.2), 
				(int) (difficulty.getWidth() / sizeDenominator), (int) (difficulty.getHeight() / sizeDenominator));
		
		easy.draw(batch, y, signWidth, signHeight);
		medium.draw(batch, y, signWidth, signHeight);
		hard.draw(batch, y, signWidth, signHeight);
	}
	
	public void mouseMove(State state, int x, int y) {
		easy.mouseMove(x, y);
		medium.mouseMove(x, y);
		hard.mouseMove(x, y);
	}
	
	public void mousePress(State state, int x, int y) {
		easy.mousePress(x, y);
		medium.mousePress(x, y);
		hard.mousePress(x, y);
	}
	
	public void mouseRelease(State state, int x, int y) {
		easy.mouseRelease(x, y);
		medium.mouseRelease(x, y);
		hard.mouseRelease(x, y);
	}
	
}