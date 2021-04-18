package org.semillita.raycaster.ui;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class UIElement {

	private int x, y, width, height;

	public UIElement(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public void press(int x, int y) {
	}

	public void release(int x, int y) {
	}

	public static class Text {

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

	public static class Button extends UIElement {

		private enum Mode {
			NEUTRAL, HOVERED, SELECTED;
			
			
		}
		
		Mode mode;

		public Button(int x, int y, int width, int height) {
			super(x, y, width, height);
			mode = Mode.NEUTRAL;
		}

		@Override
		public void press(int x, int y) {

		}

		public static class MainMenuButton extends Button {

			public MainMenuButton(int x, int y, int width, int height) {
				super(x, y, width, height);
			}

		}

	}

}
