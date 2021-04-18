package org.semillita.raycaster.ui.signs;

import org.semillita.raycaster.core.Launcher;
import org.semillita.raycaster.core.Game.ColorTheme;
import org.semillita.raycaster.core.Game.State;
import org.semillita.raycaster.ui.ColorButton;
import org.semillita.raycaster.ui.ConfirmButton;
import org.semillita.raycaster.ui.DifficultyButton;
import org.semillita.raycaster.ui.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class SettingsSign {

	private double sizeDenominator;
	
	private Texture themes;
	
	private ColorButton yellow;
	private ColorButton green;
	private ColorButton purple;
	
	private ColorButton selectedButton;
	
	private ConfirmButton ok;
	
	public SettingsSign(double sizeDenominator) {
		this.sizeDenominator = sizeDenominator;
		
		themes = new Texture("Resources/Themes.png");
		
		yellow = new ColorButton(new Texture("Resources/YellowColor.png"), 0, true);
		green = new ColorButton(new Texture("Resources/GreenColor.png"), 1, false);
		purple = new ColorButton(new Texture("Resources/PurpleColor.png"), 2, false);
		
		selectedButton = yellow;
		
		ok = new ConfirmButton(new Texture("OK.png"), 40);
	}
	
	public void render(SpriteBatch batch, int y, int signWidth, int signHeight) {
		batch.draw(themes, (int) (Gdx.graphics.getWidth() / 2 - (themes.getWidth() / sizeDenominator) / 2), (int) (y + signHeight / 2.2), 
				(int) (themes.getWidth() / sizeDenominator), (int) (themes.getHeight() / sizeDenominator));
		
		yellow.draw(batch, y, signWidth, signHeight, this);
		green.draw(batch, y, signWidth, signHeight, this);
		purple.draw(batch, y, signWidth, signHeight, this);
		
		ok.draw(batch, y, this);
	}
	
	public void mouseMove(State state, int x, int y, UI ui) {
		yellow.mouseMove(x, y, ui);
		green.mouseMove(x, y, ui);
		purple.mouseMove(x, y, ui);
		
		ok.mouseMove(x, y, ui);
	}
	
	public void mousePress(State state, int x, int y) {
		yellow.mousePress(x, y);
		green.mousePress(x, y);
		purple.mousePress(x, y);
		
		ok.mousePress(x, y);
	}
	
	public void mouseRelease(State state, int x, int y) {
		yellow.mouseRelease(x, y);
		green.mouseRelease(x, y);
		purple.mouseRelease(x, y);
		
		ok.mouseRelease(x, y);
	}
	
	public void confirmButtonCallback() {
		ColorTheme color;
		if(selectedButton == yellow) {
			color = ColorTheme.YELLOW;
		} else if(selectedButton == green) {
			color = ColorTheme.GREEN;
		} else {
			color = ColorTheme.PURPLE;
		}
		Launcher.getGame().setColorTheme(color);;
	}
	
	public void selectButton(ColorButton colorButton) {
		selectedButton = colorButton;
	}
	
	public ColorButton getSelectedButton() {
		return selectedButton;
	}
	
}
