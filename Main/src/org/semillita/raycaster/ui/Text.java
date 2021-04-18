package org.semillita.raycaster.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Text {

	private Texture texture;
	private int x, y, width, height;
	
	public Text(Texture texture, int x, int y, int width, int height) {
		this.texture = texture;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void render(SpriteBatch batch) {
		batch.draw(texture, x, y, width, height);
	}
	
}
