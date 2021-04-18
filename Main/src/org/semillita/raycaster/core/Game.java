package org.semillita.raycaster.core;

import java.io.InputStream;

import org.semillita.raycaster.camera.Camera;
import org.semillita.raycaster.map.Map;
import org.semillita.raycaster.player.Player;
import org.semillita.raycaster.ui.UI;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
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
	
	public static enum State {
		MAIN_MENU,
		LOADING,
		GAME
	}
	
	private com.badlogic.gdx.graphics.Camera orthoCamera;
	private Viewport viewport;
	
	SpriteBatch batch;
	
	Camera gameCamera;
	Map map;
	Player player;
	UI ui;
	
	State state;
	
	@Override
	public void create() {
		Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
		System.out.println("Screen size: " + Gdx.graphics.getWidth() + "x" + Gdx.graphics.getHeight());
		
		
		orthoCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		orthoCamera.position.set(orthoCamera.viewportWidth / 2, orthoCamera.viewportHeight / 2, 0);
		viewport = new ScreenViewport(orthoCamera);
		
		batch = new SpriteBatch();
		
		gameCamera = new Camera();
		map = new Map(this.getClass().getClassLoader().getResourceAsStream("map.txt"));
		player = new Player(map.getStartX(), map.getStartY(), (float) 20);
		
		ui = new UI();
		
		state = State.MAIN_MENU;
		
		initializeInputListener();
	}

	@Override
	public void render() {
		orthoCamera.update();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    batch.setProjectionMatrix(orthoCamera.combined);
	    if(state == State.GAME) gameCamera.render(batch, map, player);
	    if(state == State.MAIN_MENU) ui.render(batch, state);
	    batch.end();
	    
	    player.move(map, gameCamera);
	}
	
	@Override
	public void resize(int width, int height) {
		viewport.update(width, height, true);
		System.out.println("//");
		System.out.println(width);
		System.out.println(height);
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
	
	private void initializeInputListener() {
		Gdx.input.setInputProcessor(new InputProcessor() {

			@Override
			public boolean keyDown(int key) {
				ui.keyPress(state, key);
				
				System.out.println(key);
				if(key == Keys.ESCAPE) {
					state = State.MAIN_MENU;
				}
				
				if(key == Keys.SPACE) {
					state = State.GAME;
				}
				return false;
			}

			@Override
			public boolean keyTyped(char arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean keyUp(int arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean mouseMoved(int x, int y) {
				y = (Gdx.graphics.getHeight() - 1 - y);
				if(y < 10) System.out.println(y);
				ui.mouseMove(state, x, y);
				return false;
			}

			@Override
			public boolean scrolled(int arg0) {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean touchDown(int x, int y, int arg2, int arg3) {
				y = (Gdx.graphics.getHeight() - 1 - y);
				ui.mousePress(state, x, y);
				return false;
			}

			@Override
			public boolean touchDragged(int x, int y, int arg2) {
				y = (Gdx.graphics.getHeight() - 1 - y);
				ui.mouseMove(state, x, y);
				return false;
			}

			@Override
			public boolean touchUp(int x, int y, int arg2, int arg3) {
				y = (Gdx.graphics.getHeight() - 1 - y);
				ui.mouseRelease(state, x, y);
				return false;
			}
			
		});
	}
	
}
