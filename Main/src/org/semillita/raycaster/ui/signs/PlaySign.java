package org.semillita.raycaster.ui.signs;

import static org.semillita.raycaster.core.Game.State;

import org.semillita.raycaster.core.Game;
import org.semillita.raycaster.core.Launcher;
import org.semillita.raycaster.ui.ConfirmButton;
import org.semillita.raycaster.ui.DifficultyButton;
import org.semillita.raycaster.ui.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlaySign {
	
	private double sizeDenominator;
	
	private Texture difficulty;
	
	private DifficultyButton easy;
	private DifficultyButton medium;
	private DifficultyButton hard;
	
	private DifficultyButton selectedButton;
	
	private ConfirmButton go;
	
	public PlaySign(double sizeDenominator) {
		this.sizeDenominator = sizeDenominator;
		
		difficulty = new Texture("Resources/Difficulty.png");
		
		easy = new DifficultyButton(new Texture("Resources/Easy.png"), 0, true);
		medium = new DifficultyButton(new Texture("Resources/Medium.png"), 1, false);
		hard = new DifficultyButton(new Texture("Resources/Hard.png"), 2, false);
		
		selectedButton = easy;
		
		go = new ConfirmButton(new Texture("Resources/GO!.png"), 40);
	}
	
	public void render(SpriteBatch batch, int y, int signWidth, int signHeight) {
		batch.draw(difficulty, (int) (Gdx.graphics.getWidth() / 2 - (difficulty.getWidth() / sizeDenominator) / 2), (int) (y + signHeight / 2.2), 
				(int) (difficulty.getWidth() / sizeDenominator), (int) (difficulty.getHeight() / sizeDenominator));
		
		easy.draw(batch, y, signWidth, signHeight, this);
		medium.draw(batch, y, signWidth, signHeight, this);
		hard.draw(batch, y, signWidth, signHeight, this);
		
		go.draw(batch, y, this);
	}
	
	public void mouseMove(State state, int x, int y, UI ui) {
		easy.mouseMove(x, y, ui);
		medium.mouseMove(x, y, ui);
		hard.mouseMove(x, y, ui);
		
		go.mouseMove(x, y, ui);
	}
	
	public void mousePress(State state, int x, int y) {
		easy.mousePress(x, y);
		medium.mousePress(x, y);
		hard.mousePress(x, y);
		
		go.mousePress(x, y);
	}
	
	public void mouseRelease(State state, int x, int y) {
		easy.mouseRelease(x, y);
		medium.mouseRelease(x, y);
		hard.mouseRelease(x, y);
		
		go.mouseRelease(x, y);
	}
	
	public void confirmButtonCallback() {
		int difficulty;
		if(selectedButton == easy) {
			difficulty = 1;
		} else if(selectedButton == medium) {
			difficulty = 2;
		} else {
			difficulty = 3;
		}
		Launcher.getGame().startGame(difficulty);
	}
	
	public void selectButton(DifficultyButton button) {
		selectedButton = button;
	}
	
	public DifficultyButton getSelectedButton() {
		return selectedButton;
	}
	
}