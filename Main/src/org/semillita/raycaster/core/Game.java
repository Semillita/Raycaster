package org.semillita.raycaster.core;

import java.io.InputStream;

import org.semillita.raycaster.camera.Camera;
import org.semillita.raycaster.map.Map;
import org.semillita.raycaster.player.Player;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class Game implements ApplicationListener {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		new LwjglApplication(new Game(), config);
	}

	//public static float camAngle = 0;
	//public static float camX = 9.5f;
	//public static float camY = 9.5f;
	
	Map map;
	Camera gameCamera;
	Player player;
	
	SpriteBatch batch;
	
	Texture g;
	
	private com.badlogic.gdx.graphics.Camera orthoCamera;
	private Viewport viewport;
	
	long lastFrame;
	
	@Override
	public void create() {
		orthoCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		orthoCamera.position.set(orthoCamera.viewportWidth / 2, orthoCamera.viewportHeight / 2, 0);
		viewport = new ScreenViewport(orthoCamera);
		
		batch = new SpriteBatch();
		gameCamera = new Camera();
		gameCamera.green = new Texture("green.png");
		gameCamera.darkGreen = new Texture("darkGreen.png");
		map = new Map(this.getClass().getClassLoader().getResourceAsStream("map.txt"));
		player = new Player(map.getStartX(), map.getStartY(), 20);
		
		lastFrame = System.nanoTime();
	}

	@Override
	public void render() {
		orthoCamera.update();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    batch.setProjectionMatrix(orthoCamera.combined);
	    gameCamera.render(batch, map, player);
	    batch.end();
	    
	    player.move(map, gameCamera);
	    
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) player.direction += 1f;
	    if(Gdx.input.isKeyPressed(Keys.LEFT)) player.direction -= 1f;
	    if(player.direction >= 360) player.direction -= 360;
	    if(player.direction < 0) player.direction += 360;
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
	}

	@Override
	public void pause() {
		
	}
	
	@Override
	public void resume() {
		
	}
	
	@Override
	public void dispose() {
		
	}
	
}
