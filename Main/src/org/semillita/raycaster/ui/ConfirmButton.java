package org.semillita.raycaster.ui;

import java.util.ArrayList;
import java.util.List;

import org.semillita.raycaster.ui.signs.PlaySign;
import org.semillita.raycaster.ui.signs.QuitSign;
import org.semillita.raycaster.ui.signs.ReturnSign;
import org.semillita.raycaster.ui.signs.SettingsSign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ConfirmButton {

	private static Texture body;
	private static Texture hover;
	private static Texture press;
	private static List<Texture> selectAnimation;
	
	private static final int fps = 10;
	private static final int frames = 4;
	
	private static double sizeDenominator;
	
	private static int width, height;
	
	static {
		body = new Texture("ConfirmButton.png");
		hover = new Texture("ConfirmButtonHover.png");
		press = new Texture("ConfirmButtonHold.png");
		selectAnimation = loadAnimation("confirmButtonAnimation/", frames);
		
		sizeDenominator = 2160 / Gdx.graphics.getHeight();
		
		width = (int) (body.getWidth() / sizeDenominator);
		height = (int) (body.getHeight() / sizeDenominator);
	}
	
	private static enum Mode {
		NEUTRAL,
		HOVERED,
		PRESSED,
		ANIMATING;
	}
	
	private int x, y;
	
	private Mode mode;
	
	private Texture text;
	
	private boolean blockInput = false;
	
	private int signY;
	
	private double animationProgress = 0;
	
	private long lastFrame;
	
	public ConfirmButton(Texture text, int y) {
		this.text = text;
		x = Gdx.graphics.getWidth() / 2 - width / 2;
		this.y = y;
		
		mode = Mode.NEUTRAL;
		
		lastFrame = System.nanoTime();
	}
	
	public void draw(SpriteBatch batch, int signY, Object signController) {
		
		this.signY = signY;
		
		long thisFrame = System.nanoTime();
		double deltaTime = (thisFrame - lastFrame) / 1_000_000_000d;
		lastFrame = thisFrame;
				
		batch.draw(body, x, signY + y, width, height);
		
		switch(mode) {
		case HOVERED:
			final int hoverWidth = (int) (hover.getWidth() / sizeDenominator);
			final int hoverHeight = (int) (hover.getHeight() / sizeDenominator);
			final int hoverX = x + width / 2 - hoverWidth / 2;
			final int hoverY = signY + y + height / 2 - hoverHeight / 2;
			batch.draw(hover, hoverX, hoverY, hoverWidth, hoverHeight);
			break;
		case PRESSED:
			final int pressWidth = (int) (hover.getWidth() / sizeDenominator);
			final int pressHeight = (int) (hover.getHeight() / sizeDenominator);
			final int pressX = x + width / 2 - pressWidth / 2;
			final int pressY = signY + y + height / 2 - pressHeight / 2;
			batch.draw(press, pressX, pressY, pressWidth, pressHeight);
			break;
		case ANIMATING:
			animationProgress += fps * deltaTime;
			if(animationProgress >= frames) {
				mode = Mode.NEUTRAL;
				blockInput = false;
				animationProgress = 0;
				
				if(signController instanceof PlaySign) {
					PlaySign playController = (PlaySign) signController;
					playController.confirmButtonCallback();
				} else if(signController instanceof SettingsSign) {
					SettingsSign settingsController = (SettingsSign) signController;
					settingsController.confirmButtonCallback();
				} else if(signController instanceof QuitSign) {
					QuitSign quitController = (QuitSign) signController;
					quitController.confirmButtonCallback();
				} else if(signController instanceof ReturnSign) {
					ReturnSign returnController = (ReturnSign) signController;
					returnController.confirmButtonCallback();
				}
			} else {
				Texture animationTexture = selectAnimation.get((int) animationProgress);
				final int animationWidth = (int) (animationTexture.getWidth() / sizeDenominator);
				final int animationHeight = (int) (animationTexture.getHeight() / sizeDenominator);
				final int animationX = x + (width / 2) - (animationWidth / 2);
				final int animationY = signY + y + (height / 2) - (animationHeight / 2);
				batch.draw(animationTexture, animationX, animationY, animationWidth, animationHeight);
			}
		}
		
		final int textWidth = (int) (text.getWidth() / sizeDenominator);
		final int textHeight = (int) (text.getHeight() / sizeDenominator);
		final int textX = x + width / 2 - textWidth / 2;
		final int textY = signY + y + height / 2 - textHeight / 2;
		batch.draw(text, textX, textY, textWidth, textHeight);
		
		//draw text
	}
	
	public void mouseMove(int x, int y, UI ui) {
		if(blockInput) {
			return;
		}
		switch(mode) {
		case NEUTRAL:
			if(isInside(x, y)) {
				mode = Mode.HOVERED;
				ui.playIteration();
			}
			break;
		case HOVERED:
			if(!isInside(x, y)) {
				mode = Mode.NEUTRAL;
			}
			break;
		case PRESSED:
			if(!isInside(x, y)) {
				mode = Mode.NEUTRAL;
			}
		}
	}
	
	public void mousePress(int x, int y) {
		if(blockInput) {
			return;
		}
		if(isInside(x, y)) {
			mode = Mode.PRESSED;
		}
	}
	
	public void mouseRelease(int x, int y) {
		if(blockInput) {
			return;
		}
		switch(mode) {
		case PRESSED:
			if(isInside(x, y)) {
				blockInput = true;
				mode = Mode.ANIMATING;
			} else {
				mode = Mode.NEUTRAL;
			}
			break;
		case NEUTRAL:
			if(isInside(x, y)) {
				mode = Mode.HOVERED;
			}
			break;
		}
	}
	
	private boolean isInside(int x, int y) {
		if(x >= this.x && x <= this.x + width) {
			if(y >= this.y + signY && y <= this.y + height + signY) {
				return true;
			}
		}
		return false;
	}
	
	private static List<Texture> loadAnimation(String source, int size) {
		List<Texture> textures = new ArrayList<>();
		for(int i = 1; i <= size; i++) {
			textures.add(new Texture(source + i + ".png"));
		}
		return textures;
	}
	
}