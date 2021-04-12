package org.semillita.raycaster.core;

import java.io.InputStream;

import org.semillita.raycaster.camera.Camera;
import org.semillita.raycaster.map.Map;

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

	public static float camAngle = 0;
	public static float camX = 9.5f;
	public static float camY = 9.5f;
	
	Map map;
	Camera gameCamera;
	
	SpriteBatch batch;
	
	Texture g;
	
	private com.badlogic.gdx.graphics.Camera orthoCamera;
	private Viewport viewport;
	
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
	}

	@Override
	public void render() {
		orthoCamera.update();
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    batch.setProjectionMatrix(orthoCamera.combined);
	    gameCamera.render(batch, map);
	    batch.end();
	    
	    float speed = 0.1f;
	    
	    if(Gdx.input.isKeyPressed(Keys.W)) {
	    	camX += Math.sin(Math.toRadians(camAngle)) * speed;
	    	camY += Math.cos(Math.toRadians(camAngle)) * speed;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.S)) {
	    	camX -= Math.sin(Math.toRadians(camAngle)) * speed;
	    	camY -= Math.cos(Math.toRadians(camAngle)) * speed;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.A)) {
	    	float a = camAngle - 90;
	    	if(a < 0) a += 360;
	    	camX += Math.sin(Math.toRadians(a)) * speed;
	    	camY += Math.cos(Math.toRadians(a)) * speed;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.D)) {
	    	float d = camAngle + 90;
	    	if(d >= 360) d -= 360;
	    	camX += Math.sin(Math.toRadians(d)) * speed;
	    	camY += Math.cos(Math.toRadians(d)) * speed;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) camAngle += 1.5f;
	    if(Gdx.input.isKeyPressed(Keys.LEFT)) camAngle -= 1.5f;
	    if(camAngle >= 360) camAngle -= 360;
	    if(camAngle < 0) camAngle += 360;
	    
	    /*if(camAngle >= 0 && camAngle < 90) {
    	} else if(camAngle >= 90 && camAngle < 180) {
    		
    	} else if(camAngle >= 180 && camAngle < 270) {
    		
    	} else {
    		
    	}*/
	    
	    /*ShapeRenderer renderer = new ShapeRenderer();
	    renderer.begin(ShapeRenderer.ShapeType.Filled);
	    renderer.setColor(Color.BLUE);
	    renderer.line(10, 10, 400, 200);
	    renderer.end();*/
	    
	    //while(true) {}
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
