package org.semillita.raycaster.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class DifficultyButton {
	
	private static enum Mode {
		NEUTRAL,
		HOVERED,
		PRESSED,
		SELECTED,
		ANIMATING;
	}
	
	private static Texture hover;
	private static Texture hold;
	
	private static boolean blockInput = false;
	
	static {
		hover = new Texture("Resources/QuadButtonHover.png");
		hold = new Texture("Resources/QuadButtonHold.png");
	}
	
	private Texture texture;
	
	private int offset;
	
	private int x = -1, y = -1;
	private int width, height;
	
	private Mode mode;
	
	public DifficultyButton(Texture texture, int offset) {
		this.texture = texture;
		
		this.offset = offset;
		
		mode = Mode.NEUTRAL;
		/*this.x = x;
		this.y = y;*/
	}
	
	public void draw(SpriteBatch batch, int signY, int signWidth, int signHeight) {
		final double sizeDenominator = 2160 / Gdx.graphics.getHeight();
				
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
		
		switch(mode) {
		case HOVERED:
			batch.draw(hover, hoverX, hoverY, hoverWidth, hoverHeight);
			break;
		case SELECTED:
			
		}
	}
	
	public void mouseMove(int x, int y) {
		if(blockInput) {
			return;
		}
		switch(mode) {
		case NEUTRAL:
			if(isInside(x, y)) {
				mode = Mode.HOVERED;
			}
			break;
		case HOVERED:
			if(!isInside(x, y)) {
				mode = Mode.NEUTRAL;
			}
			break;
		case SELECTED:
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
			mode = Mode.SELECTED;
		}
	}
	
	public void mouseRelease(int x, int y) {
		if(blockInput) {
			return;
		}
		switch(mode) {
		case SELECTED:
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
	
}
