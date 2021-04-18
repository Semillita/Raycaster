package org.semillita.raycaster.ui;

import static org.semillita.raycaster.core.Game.State;
import org.semillita.raycaster.ui.signs.PlaySign;
import org.semillita.raycaster.ui.signs.SettingsSign;
import org.semillita.raycaster.ui.signs.ReturnSign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI {
	
	private static enum SignProperty {
		PLAY(new PlaySign(2)),
		SETTINGS(new SettingsSign(2)),
		RETURN(new ReturnSign(2)),
		OFF(null);
		
		private Object controller;
		
		private SignProperty(Object controller) {
			this.controller = controller;
		}
		
		private Object getController() {
			return controller;
		}
	}
	
	private SignProperty signProperty = SignProperty.OFF;
	
	private double sizeDenominator = 1;
	
	private Texture buttonBorder;
	private Texture buttonBackground;
	private Texture background;
	private Texture sign;
	
	private int signWidth, signHeight;
	private int buttonWidth, buttonHeight;
	private int buttonBackgroundWidth, buttonBackgroundHeight;
	private int distanceBetweenButtons;
	private int buttonLayoutOffsetX, buttonLayoutOffsetY;
	
	private int signDropProgression = 0;
	
	MainMenuButton playButton, settingsButton, quitButton;
	
	public UI() {
		sizeDenominator = 2160 / Gdx.graphics.getHeight();
		
		initializeTextures();
		
		signWidth = (int) (sign.getWidth() / sizeDenominator);
		signHeight = (int) (sign.getHeight() / sizeDenominator);
		
		buttonWidth = (int) (buttonBorder.getWidth() / sizeDenominator);
		buttonHeight = (int) (buttonBorder.getHeight() / sizeDenominator);
		
		buttonBackgroundWidth = (int) (buttonBackground.getWidth() / sizeDenominator);
		buttonBackgroundHeight = (int) (buttonBackground.getHeight() / sizeDenominator);
		
		distanceBetweenButtons = buttonHeight / 2;
		
		buttonLayoutOffsetX = Gdx.graphics.getWidth() / 2 - buttonWidth / 2;
		buttonLayoutOffsetY = Gdx.graphics.getHeight() / 2 - 2 * buttonHeight;
		
		playButton = new MainMenuButton(2, new Texture("Resources/Play.png"));
		settingsButton = new MainMenuButton(1, new Texture("Resources/Settings.png"));
		quitButton = new MainMenuButton(0, new Texture("Resources/Play.png"));
	}
	
	public void render(SpriteBatch batch, State state) {
		
		if(state == State.MAIN_MENU) {
			drawMainMenu(batch);
		}
		
		batch.draw(background, 0, 0);
		
		playButton.draw(batch, this);
		settingsButton.draw(batch, this);
		quitButton.draw(batch, this);
		
	}
	
	public void mouseMove(State state, int x, int y) {
		if(state == State.MAIN_MENU && signProperty == SignProperty.OFF) {
			playButton.mouseMove(x, y);
			settingsButton.mouseMove(x, y);
			quitButton.mouseMove(x, y);
		}
	}
	
	public void mousePress(State state, int x, int y) {
		if(state == State.MAIN_MENU && signProperty == SignProperty.OFF) {
			playButton.mousePress(x, y);
			settingsButton.mousePress(x, y);
			quitButton.mousePress(x, y);
		}
	}
	
	public void mouseRelease(State state, int x, int y) {
		if(state == State.MAIN_MENU && signProperty == SignProperty.OFF) {
			playButton.mouseRelease(x, y);
			settingsButton.mouseRelease(x, y);
			quitButton.mouseRelease(x, y);
		}
	}
	
	public void keyPress(State state, int key) {
		
	}
	
	public void mainMenuButtonCallback(MainMenuButton button) {
		
	}
	
	private void initializeTextures() {
		TextureFilter minFilter = TextureFilter.MipMap;
		TextureFilter magFilter = TextureFilter.Nearest;
		
		buttonBorder = new Texture(Gdx.files.internal("Resources/ButtonBorder.png"), true);
		buttonBorder.setFilter(minFilter, magFilter);
		
		buttonBackground = new Texture(Gdx.files.internal("Resources/ButtonBackground.png"), true);
		buttonBackground.setFilter(minFilter, magFilter);
		
		background = new Texture("mainMenuBackground.png");
		
		sign = new Texture(Gdx.files.internal("Resources/Sign.png"), true);
		sign.setFilter(minFilter, magFilter);
	}
	
	private void drawMainMenu(SpriteBatch batch) {
		
	}
	
}
