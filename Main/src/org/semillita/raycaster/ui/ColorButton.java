package org.semillita.raycaster.ui;

import java.util.ArrayList;
import java.util.List;

import org.semillita.raycaster.ui.signs.PlaySign;
import org.semillita.raycaster.ui.signs.SettingsSign;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ColorButton {

	private static enum Mode {
		NEUTRAL,
		HOVERED,
		PRESSED,
		SELECTED,
		ANIMATING;
	}
	
	private static Texture hover;
	private static Texture hold;
	private static Texture selected;
	private static List<Texture> selectAnimation;
	
	private static boolean blockInput = false;
	
	private static final int fps = 10;
	private static final int frames = 3;
	
	static {
		hover = new Texture("Resources/QuadButtonHover.png");
		hold = new Texture("Resources/QuadButtonHold.png");
		selected = new Texture("QuadButtonSelected.png");
		selectAnimation = loadAnimation("quadButtonAnimation/", 3);
	}
	
	private Texture texture;
	
	private int offset;
	
	private int x, y;
	private int width, height;
	
	private Mode mode;
	
	private double animationProgress;
	
	private long lastFrame;
	
	public ColorButton(Texture texture, int offset, boolean selected) {
		this.texture = texture;
		
		this.offset = offset;
		
		if(selected) {
			mode = Mode.SELECTED;
		} else {
			mode = Mode.NEUTRAL;
		}
	}
	
	public void draw(SpriteBatch batch, int signY, int signWidth, int signHeight, SettingsSign settingsSign) {
		final double sizeDenominator = 2160 / Gdx.graphics.getHeight();
				
		long thisFrame = System.nanoTime();
		double deltaTime = (thisFrame - lastFrame) / 1_000_000_000d;
		lastFrame = thisFrame;
		
		width = (int) (texture.getWidth() / sizeDenominator);
		height = (int) (texture.getHeight() / sizeDenominator);
		
		final int distanceBetweenButtons = (int) ((signWidth - 3 * (texture.getWidth() / sizeDenominator)) / 4);
		
		x = (int) (Gdx.graphics.getWidth() / 2 + (offset - 1) * (texture.getWidth() / sizeDenominator + distanceBetweenButtons) - 50);
		
		y = (int) (signY - signHeight / 2 + 0.75 * signHeight);
		
		batch.draw(texture, x, y, width, height);
		
		final int hoverWidth = (int) (hover.getWidth() / sizeDenominator);
		final int hoverHeight = (int) (hover.getHeight() / sizeDenominator);
		final int hoverX = x + (width / 2) - (hoverWidth / 2);
		final int hoverY = y + (height / 2) - (hoverHeight / 2);
		
		final int holdWidth = (int) (hold.getWidth() / sizeDenominator);
		final int holdHeight = (int) (hold.getHeight() / sizeDenominator);
		final int holdX = x + (width / 2) - (holdWidth / 2);
		final int holdY = y + (height / 2) - (holdHeight / 2);
		
		switch(mode) {
		case HOVERED:
			batch.draw(hover, hoverX, hoverY, hoverWidth, hoverHeight);
			break;
		case PRESSED:
			batch.draw(hold, holdX, holdY, holdWidth, holdHeight);
			break;
		case ANIMATING:
			animationProgress += fps * deltaTime;
			if(animationProgress >= frames) {
				mode = Mode.SELECTED;
				blockInput = false;
				animationProgress = 0;
				settingsSign.selectButton(this);
			} else {
				Texture animationTexture = selectAnimation.get((int) animationProgress);
				final int animationWidth = (int) (animationTexture.getWidth() / sizeDenominator);
				final int animationHeight = (int) (animationTexture.getHeight() / sizeDenominator);
				final int animationX = x + (width / 2) - (animationWidth / 2);
				final int animationY = y + (height / 2) - (animationHeight / 2);
				batch.draw(animationTexture, animationX, animationY, animationHeight, animationWidth);
			}
			break;
		case SELECTED:
			final int selectedWidth = (int) (selected.getWidth() / sizeDenominator);
			final int selectedHeight = (int) (selected.getHeight() / sizeDenominator);
			final int selectedX = x + (width / 2) - (selectedWidth / 2);
			final int selectedY = y + (height / 2) - (selectedHeight / 2);
			batch.draw(selected, selectedX, selectedY, selectedWidth, selectedHeight);
			
			if(settingsSign.getSelectedButton() != this) {
				mode = Mode.NEUTRAL;
			}
		}
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
			if(y >= this.y && y <= this.y + height) {
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
