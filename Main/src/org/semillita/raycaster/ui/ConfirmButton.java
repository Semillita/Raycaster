package org.semillita.raycaster.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class ConfirmButton {

	private static Texture body;
	private static Texture hover;
	private static Texture press;
	private static List<Texture> selectAnimation;
	
	private static double sizeDenominator;
	
	private static int width, height;
	
	static {
		//Define textures;
		
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
	
	public ConfirmButton(Texture text, int y) {
		this.text = text;
		x = Gdx.graphics.getWidth() / 2 - width / 2;
		this.y = y;
	}
	
	public void draw(SpriteBatch batch, int signY, Object signController) {
		
		batch.draw(body, x, signY + y, width, height);
		
		switch(mode) {
		case NEUTRAL:
			
			break;
		case HOVERED:
			
		}
		
		//draw text
	}
	
	private static List<Texture> loadAnimation(String source, int size) {
		List<Texture> textures = new ArrayList<>();
		for(int i = 1; i <= size; i++) {
			textures.add(new Texture(source + i + ".png"));
		}
		return textures;
	}
	
}