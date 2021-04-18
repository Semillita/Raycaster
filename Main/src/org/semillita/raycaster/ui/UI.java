package org.semillita.raycaster.ui;

import static org.semillita.raycaster.core.Game.State;
import org.semillita.raycaster.ui.signs.PlaySign;
import org.semillita.raycaster.ui.signs.SettingsSign;
import org.semillita.raycaster.ui.signs.ReturnSign;
import org.semillita.raycaster.ui.signs.QuitSign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI {
	
	private static enum SignProperty {
		PLAY(new PlaySign(2160 / Gdx.graphics.getHeight())),
		SETTINGS(new SettingsSign(2160 / Gdx.graphics.getHeight())),
		RETURN(new ReturnSign(2160 / Gdx.graphics.getHeight())),
		QUIT(new QuitSign(2160 / Gdx.graphics.getHeight())),
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
	
	private Texture background;
	private Texture sign;
	
	private double signDropTime = -1;
	
	MainMenuButton playButton, settingsButton, quitButton;
	
	private long lastFrame;
	
	public UI() {
		sizeDenominator = 2160 / Gdx.graphics.getHeight();
		
		initializeTextures();
		
		playButton = new MainMenuButton(2, new Texture("Resources/Play.png"));
		settingsButton = new MainMenuButton(1, new Texture("Resources/Settings.png"));
		quitButton = new MainMenuButton(0, new Texture("Resources/Play.png"));
		
		lastFrame = System.nanoTime();
	}
	
	public void render(SpriteBatch batch, State state) {
		
		final long thisFrame = System.nanoTime();
		final double deltaTime = (thisFrame - lastFrame) / 1_000_000_000d;
		lastFrame = thisFrame;
		
		if(state == State.MAIN_MENU) {
			batch.draw(background, 0, 0);
			drawMainMenu(batch);
		}
		
		if(signProperty != SignProperty.OFF) {
			int signY;
			if(signDropTime != -1) {
				signDropTime += deltaTime;
				signY = (int) (Gdx.graphics.getHeight() - Math.pow(10, signDropTime) / sizeDenominator);
				
				if(signY < Gdx.graphics.getHeight() - sign.getHeight() / sizeDenominator) {
					signY = (int) (Gdx.graphics.getHeight() - sign.getHeight() / sizeDenominator);
					signDropTime = -1;
				}
			} else {
				signY = (int) (Gdx.graphics.getHeight() - sign.getHeight() / sizeDenominator);
			}
			
			//batch.draw(sign, x, y);
			
			Object signController = signProperty.getController();
			
			switch(signProperty) {
			case PLAY:
				PlaySign playController = (PlaySign) signController;
				playController.render(batch, 500);
				break;
			case SETTINGS:
				SettingsSign settingsController = (SettingsSign) signController;
				settingsController.render(batch, 500);
				break;
			case QUIT:
				QuitSign quitController = (QuitSign) signController;
				quitController.render(batch, 500);
				break;
			case RETURN:
				break;
			}	
		}
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
		if(button == playButton) {
			summonSign(SignProperty.PLAY);
		} else if(button == settingsButton) {
			summonSign(SignProperty.SETTINGS);
		} else if(button == quitButton) {
			summonSign(SignProperty.QUIT);
		}
	}
	
	private void summonSign(SignProperty sign) {
		signProperty = sign;
		signDropTime = 0;
	}
	
	private void initializeTextures() {
		TextureFilter minFilter = TextureFilter.MipMap;
		TextureFilter magFilter = TextureFilter.Nearest;
		
		background = new Texture("mainMenuBackground.png");
		
		sign = new Texture(Gdx.files.internal("Resources/Sign.png"), true);
		sign.setFilter(minFilter, magFilter);
	}
	
	private void drawMainMenu(SpriteBatch batch) {
		playButton.draw(batch, this);
		settingsButton.draw(batch, this);
		quitButton.draw(batch, this);
	}
	
}
