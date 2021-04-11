package org.semillita.raycaster.camera;

import org.semillita.raycaster.map.Map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Camera {

	public void render(SpriteBatch batch, Map map) {
		ShapeRenderer renderer = new ShapeRenderer();
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.BLUE);
		renderer.rect(10, 10, 5, 5);
	}
	
}
