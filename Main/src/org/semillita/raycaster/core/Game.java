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
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Game implements ApplicationListener {

	public static void main(String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		new LwjglApplication(new Game(), config);
	}

	public static int camAngle = 0;
	public static float camX = 9.5f;
	public static float camY = 9.5f;
	
	Map map;
	Camera camera;
	
	SpriteBatch batch;
	
	Texture g;
	
	@Override
	public void create() {
		batch = new SpriteBatch();
		camera = new Camera();
		camera.green = new Texture("green.png");
		camera.darkGreen = new Texture("darkGreen.png");
		map = new Map(this.getClass().getClassLoader().getResourceAsStream("map.txt"));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1, 1, 1, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    batch.begin();
	    camera.render(batch, map);
	    batch.end();
	    
	    int num = 50;
	    
	    if(Gdx.input.isKeyPressed(Keys.W)) {
	    	camY += 0.1;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.S)) {
	    	camY -= 0.1;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.A)) {
	    	camX += 0.1;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.D)) {
	    	camX -= 0.1;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) camAngle += 1;
	    if(Gdx.input.isKeyPressed(Keys.LEFT)) camAngle -= 1;
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
