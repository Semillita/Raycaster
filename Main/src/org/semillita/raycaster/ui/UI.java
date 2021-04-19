package org.semillita.raycaster.ui;

import static org.semillita.raycaster.core.Game.State;

import org.semillita.raycaster.core.Game;
import org.semillita.raycaster.core.Game.State;
import org.semillita.raycaster.ui.signs.PlaySign;
import org.semillita.raycaster.ui.signs.SettingsSign;
import org.semillita.raycaster.ui.signs.ReturnSign;
import org.semillita.raycaster.ui.signs.QuitSign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UI {

	public static enum SignProperty {
		PLAY(new PlaySign(2160 / Gdx.graphics.getHeight())),
		SETTINGS(new SettingsSign(2160 / Gdx.graphics.getHeight())),
		RETURN(new ReturnSign(2160 / Gdx.graphics.getHeight())), QUIT(new QuitSign(2160 / Gdx.graphics.getHeight())),
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

	private Texture backgroundYellow;
	private Texture backgroundGreen;
	private Texture backgroundPurple;
	
	private Texture currentBackground;
	
	private Texture sign;

	private double signDropTime = -1;
	private final int signDropBase = 50;
	private final int signDropForce = 80;

	MainMenuButton playButton, settingsButton, quitButton;

	private long lastFrame;
	
	Sound gunShot;
	Sound iteration;
	Sound whip;

	public UI() {
		sizeDenominator = 2160 / Gdx.graphics.getHeight();

		initializeTextures();
		
		currentBackground = backgroundYellow;

		playButton = new MainMenuButton(2, new Texture("Resources/Play.png"));
		settingsButton = new MainMenuButton(1, new Texture("Resources/Settings.png"));
		quitButton = new MainMenuButton(0, new Texture("Resources/QUIT.png"));

		gunShot = Gdx.audio.newSound(Gdx.files.internal("GunShotSound.mp3"));
		iteration = Gdx.audio.newSound(Gdx.files.internal("IterationSound.wav"));
		whip = Gdx.audio.newSound(Gdx.files.internal("WhipSound.mp3"));
		
		lastFrame = System.nanoTime();
	}

	public void render(SpriteBatch batch, Game game, State state) {

		final long thisFrame = System.nanoTime();
		final double deltaTime = (thisFrame - lastFrame) / 1_000_000_000d;
		lastFrame = thisFrame;

		if (state == State.MAIN_MENU) {
			switch(game.getColorTheme()) {
			case YELLOW:
				currentBackground = backgroundYellow;
				break;
			case GREEN:
				currentBackground = backgroundGreen;
				break;
			case PURPLE:
				currentBackground = backgroundPurple;
				break;
			}
			batch.draw(currentBackground, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			drawMainMenu(batch);
		}

		if (signProperty != SignProperty.OFF) {
			int signY;
			if (signDropTime != -1) {
				signDropTime += deltaTime;
				signY = (int) (Gdx.graphics.getHeight()
						- signDropForce * Math.pow(signDropBase, signDropTime) / sizeDenominator);

				if (signY < Gdx.graphics.getHeight() - sign.getHeight() / sizeDenominator) {
					signY = (int) (Gdx.graphics.getHeight() - sign.getHeight() / sizeDenominator);
					signDropTime = -1;
				}
			} else {
				signY = (int) (Gdx.graphics.getHeight() - sign.getHeight() / sizeDenominator);
			}

			batch.draw(sign, (int) (Gdx.graphics.getWidth() / 2 - (sign.getWidth() / sizeDenominator) / 2), signY,
					(int) (sign.getWidth() / sizeDenominator), (int) (sign.getHeight() / sizeDenominator));

			Object signController = signProperty.getController();

			switch (signProperty) {
			case PLAY:
				PlaySign playController = (PlaySign) signController;
				playController.render(batch, signY, (int) (sign.getWidth() / sizeDenominator),
						(int) (sign.getHeight() / sizeDenominator));
				break;
			case SETTINGS:
				SettingsSign settingsController = (SettingsSign) signController;
				settingsController.render(batch, signY, (int) (sign.getWidth() / sizeDenominator),
						(int) (sign.getHeight() / sizeDenominator));
				break;
			case QUIT:
				QuitSign quitController = (QuitSign) signController;
				quitController.render(batch, signY, (int) (sign.getWidth() / sizeDenominator),
						(int) (sign.getHeight() / sizeDenominator));
				break;
			case RETURN:
				ReturnSign returnController = (ReturnSign) signController;
				returnController.render(batch, signY, (int) (sign.getWidth() / sizeDenominator),
						(int) (sign.getHeight() / sizeDenominator));
				break;
			}
		}
	}

	public void mouseMove(State state, int x, int y) {
		if (state == State.MAIN_MENU && signProperty == SignProperty.OFF) {
			playButton.mouseMove(x, y, this);
			settingsButton.mouseMove(x, y, this);
			quitButton.mouseMove(x, y, this);
		} else if (signProperty != SignProperty.OFF) {

			Object signController = signProperty.getController();

			switch (signProperty) {
			case PLAY:
				PlaySign playController = (PlaySign) signController;
				playController.mouseMove(state, x, y, this);
				break;
			case SETTINGS:
				SettingsSign settingsController = (SettingsSign) signController;
				settingsController.mouseMove(state, x, y, this);
				break;
			case QUIT:
				QuitSign quitController = (QuitSign) signController;
				quitController.mouseMove(state, x, y, this);
				break;
			case RETURN:
				ReturnSign returnController = (ReturnSign) signController;
				returnController.mouseMove(state, x, y, this);
				break;
			}
		}
	}

	public void mousePress(State state, int x, int y) {
		if (state == State.MAIN_MENU && signProperty == SignProperty.OFF) {
			playButton.mousePress(x, y);
			settingsButton.mousePress(x, y);
			quitButton.mousePress(x, y);
		} else if (signProperty != SignProperty.OFF) {

			Object signController = signProperty.getController();

			switch (signProperty) {
			case PLAY:
				PlaySign playController = (PlaySign) signController;
				playController.mousePress(state, x, y);
				break;
			case SETTINGS:
				SettingsSign settingsController = (SettingsSign) signController;
				settingsController.mousePress(state, x, y);
				break;
			case QUIT:
				QuitSign quitController = (QuitSign) signController;
				quitController.mousePress(state, x, y);
				break;
			case RETURN:
				ReturnSign returnController = (ReturnSign) signController;
				returnController.mousePress(state, x, y);
				break;
			}
		}
	}

	public void mouseRelease(State state, int x, int y) {
		if (state == State.MAIN_MENU && signProperty == SignProperty.OFF) {
			playButton.mouseRelease(x, y, this);
			settingsButton.mouseRelease(x, y, this);
			quitButton.mouseRelease(x, y, this);
		} else if (signProperty != SignProperty.OFF) {

			Object signController = signProperty.getController();

			switch (signProperty) {
			case PLAY:
				PlaySign playController = (PlaySign) signController;
				playController.mouseRelease(state, x, y);
				break;
			case SETTINGS:
				SettingsSign settingsController = (SettingsSign) signController;
				settingsController.mouseRelease(state, x, y);
				break;
			case QUIT:
				QuitSign quitController = (QuitSign) signController;
				quitController.mouseRelease(state, x, y);
				break;
			case RETURN:
				ReturnSign returnController = (ReturnSign) signController;
				returnController.mouseRelease(state, x, y);
				break;
			}
		}
	}

	public void keyPress(State state, int key) {
		if(key == Keys.ESCAPE) {
			if(state == State.GAME) {
				summonSign(SignProperty.RETURN);
			}
		}
	}

	public void mainMenuButtonCallback(MainMenuButton button) {
		if (button == playButton) {
			summonSign(SignProperty.PLAY);
		} else if (button == settingsButton) {
			summonSign(SignProperty.SETTINGS);
		} else if (button == quitButton) {
			summonSign(SignProperty.QUIT);
		}
	}

	public void playGunShot() {
		gunShot.play();
	}
	
	public void playIteration() {
		iteration.play();
	}
	
	public void playWhip() {
		whip.play();
	}
	
	public void closeSign() {
		signProperty = SignProperty.OFF;
		signDropTime = -1;
	}
	
	public void summonSign(SignProperty sign) {
		signProperty = sign;
		signDropTime = 0;
	}

	private void initializeTextures() {
		TextureFilter minFilter = TextureFilter.MipMap;
		TextureFilter magFilter = TextureFilter.Nearest;

		backgroundYellow = new Texture("BackgroundYellow.png");
		backgroundGreen = new Texture("BackgroundGreen.png");
		backgroundPurple = new Texture("BackgroundPurple.png");

		sign = new Texture(Gdx.files.internal("Resources/Sign.png"), true);
		sign.setFilter(minFilter, magFilter);
	}

	private void drawMainMenu(SpriteBatch batch) {
		playButton.draw(batch, this);
		settingsButton.draw(batch, this);
		quitButton.draw(batch, this);
	}

}
