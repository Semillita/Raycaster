package org.semillita.raycaster.player;

import org.semillita.raycaster.camera.Camera;
import org.semillita.raycaster.map.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player {

	public float x, y;
	public float direction;
	
	private final float speed = 4;
	private final float collisionHitbox = 0.2f;
	
	private long lastFrame;
	
	public Player(float startX, float startY, float startDirection) {
		x = startX;
		y = startY;
		direction = startDirection;
		
		lastFrame = System.nanoTime();
	}
	
	public void move(Map map, Camera camera) {
		final long thisFrame = System.nanoTime();
	    final float deltaTime = (thisFrame - lastFrame) / 1_000_000_000f;
	    lastFrame = thisFrame;
	    
	    float distanceX = 0, distanceY = 0;
	    
	    if(Gdx.input.isKeyPressed(Keys.W)) {
	    	distanceX += Math.sin(Math.toRadians(direction)) * speed * deltaTime;
	    	distanceY += Math.cos(Math.toRadians(direction)) * speed * deltaTime;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.S)) {
	    	distanceX -= Math.sin(Math.toRadians(direction)) * speed * deltaTime;
	    	distanceY -= Math.cos(Math.toRadians(direction)) * speed * deltaTime;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.A)) {
	    	float a = direction - 90;
	    	if(a < 0) a += 360;
	    	distanceX += Math.sin(Math.toRadians(a)) * speed * deltaTime;
	    	distanceY += Math.cos(Math.toRadians(a)) * speed * deltaTime;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.D)) {
	    	float d = direction + 90;
	    	if(d >= 360) d -= 360;
	    	distanceX += Math.sin(Math.toRadians(d)) * speed * deltaTime;
	    	distanceY += Math.cos(Math.toRadians(d)) * speed * deltaTime;
	    }
	    
	    float targetX = x + distanceX;
	    
	    float targetY = y + distanceY;
	  
	    
	    if(!map.hasBlock((int) targetX, (int) targetY)) {
	    	x = targetX;
	    	y = targetY;
	    } else {
	    	Camera.Collision collision = camera.castRay(map, this, direction);
	    	if(collision.orientation == 1) {
	    		targetY = y;
	    	} else {
	    		targetX = x;
	    	}
	    	
	    	if(!map.hasBlock((int) targetX, (int) targetY)) {
	    		x = targetX;
		    	y = targetY;
		    }
	    }
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
