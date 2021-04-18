package org.semillita.raycaster.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainMenuButton {

	private static double sizeDenominator;
	
	private static Texture border;
	private static Texture background;
	
	private static Texture hover;
	
	private static Texture gun;
	private static Texture gunHold;
	private static List<Texture> gunAnimation;
	
	private static List<Texture> borderAnimation;
	
	private static int buttonWidth;
	private static int buttonHeight;
	
	private static int distanceBetweenButtons;
	
	private static int buttonX;
	
	private static double stretchFactor = 1.1;
	
	private static int fps = 24;
	
	private static int frames = 6;
	
	private static boolean blockInput = false;
	
	static {
		sizeDenominator = 2160 / Gdx.graphics.getHeight();
		
		System.out.println("sd = " + sizeDenominator);
		
		border = new Texture("Resources/ButtonBorder.png");
		background = new Texture("Resources/ButtonBackground.png");
		
		hover = new Texture("Resources/ButtonHover.png");
		
		gun = new Texture("Resources/Gun.png");
		gunHold = new Texture("Resources/GunHold.png");
		gunAnimation = loadAnimation("gunAnimation/", 10);
		
		borderAnimation = loadAnimation("borderAnimation/", 10);
		
		buttonWidth = (int) (border.getWidth() / sizeDenominator);
		buttonHeight = (int) (border.getHeight() / sizeDenominator);
		
		buttonX = Gdx.graphics.getWidth() / 2;
	}
	
	private static enum Mode {
		NEUTRAL,
		HOVERED,
		SELECTED,
		ANIMATING;
	}
	
	private int y;
	private Texture text;
	private Mode mode;
	
	private int offset;
	
	private double animationProgress;
	private long lastFrame;
	
	public MainMenuButton(int offset, Texture text) {
		y = (int) (Gdx.graphics.getHeight() / 2 + (1.5 * (offset - 1)) * (buttonHeight));
		
		System.out.println("///");
		System.out.println(offset);
		System.out.println(y);
		
		this.text = text;
		
		mode = Mode.NEUTRAL;
		
		this.offset = offset;
		
		animationProgress = 0;
		
		lastFrame = System.nanoTime();
	}
	
	public void draw(SpriteBatch batch, UI ui) {
		double scale = 1;
		if(mode == Mode.SELECTED || mode == Mode.ANIMATING) {
			scale = stretchFactor;
		}
		
		long thisFrame = System.nanoTime();
		double deltaTime = (thisFrame - lastFrame) / 1_000_000_000d;
		lastFrame = thisFrame;
		
		Texture borderTex = border;
		Texture gunTex = gun;
		
		if(mode == Mode.ANIMATING) {
			animationProgress += fps * deltaTime;
			if(animationProgress >= frames) {
				mode = Mode.HOVERED;
				animationProgress = 0;
				blockInput = false;
				ui.mainMenuButtonCallback(this);
			} else {
				borderTex = borderAnimation.get((int) animationProgress);
				gunTex = gunAnimation.get((int) animationProgress);
			}
		}
		
		if(mode == Mode.SELECTED) {
			gunTex = gunHold;
		}
		
		final int borderTexHeight = (int) (borderTex.getHeight() / sizeDenominator);
		
		batch.draw(borderTex, buttonX - (int) (scale * buttonWidth / 2), y - (borderTexHeight / 2), (int) (buttonWidth * scale), borderTexHeight);
		batch.draw(background, buttonX - ((float) (scale * background.getWidth() / sizeDenominator) / 2), y - ((float) (background.getHeight() / sizeDenominator) / 2), 
				(int) (scale * background.getWidth() / sizeDenominator), (int) (background.getHeight() / sizeDenominator));
		
		if(mode == Mode.HOVERED || mode == Mode.SELECTED || mode == Mode.ANIMATING) {
			batch.draw(gunTex, (int) (buttonX -  (buttonWidth * 1.2)), (int) (y - (gun.getHeight() / sizeDenominator) / 1.2), 
					(int) (gunTex.getWidth() / sizeDenominator), (int) (gunTex.getHeight() / sizeDenominator));	
		}
		
		if(mode == Mode.HOVERED) {
			batch.draw(hover, buttonX - ((float) (hover.getWidth() / sizeDenominator) / 2), y - ((float) (hover.getHeight() / sizeDenominator) / 2), 
					(int) (hover.getWidth() / sizeDenominator), (int) (hover.getHeight() / sizeDenominator));
		}
		
		batch.draw(text, buttonX - ((float) (text.getWidth() / sizeDenominator) / 2), y - ((float) (text.getHeight() / sizeDenominator) / 2), 
				(int) (text.getWidth() / sizeDenominator), (int) (text.getHeight() / sizeDenominator));
	}
	
	public void mouseMove(int x, int y) {
		if(blockInput) {
			return;
		}
		switch(mode) {
		case NEUTRAL:
			if(isInside(x, y)) {
				mode = Mode.HOVERED;
				System.out.println(offset);
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
		if(x >= buttonX - (buttonWidth / 2) && x <= buttonX + (buttonWidth / 2)) {
			if(y >= this.y - (buttonHeight / 2) && y <= this.y + (buttonHeight / 2)) {
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
