package org.semillita.raycaster.player;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

import org.semillita.raycaster.camera.Camera;
import org.semillita.raycaster.camera.Collision;
import org.semillita.raycaster.map.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;

public class Player {

	public float x, y;
	public float direction;
	
	private final float speed = 4;
	private final float cameraRotationSpeed = 50;
	
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
	    
	    if(Gdx.input.isKeyPressed(Keys.RIGHT)) direction += cameraRotationSpeed * deltaTime;
	    if(Gdx.input.isKeyPressed(Keys.LEFT)) direction -= cameraRotationSpeed * deltaTime;
	    if(direction >= 360) direction -= 360;
	    if(direction < 360) direction += 360;
	    
	    float distanceX = 0, distanceY = 0;
	    
	    if(Gdx.input.isKeyPressed(Keys.W)) {
	    	distanceX += sin(toRadians(direction)) * speed * deltaTime;
	    	distanceY += cos(toRadians(direction)) * speed * deltaTime;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.S)) {
	    	distanceX -= sin(toRadians(direction)) * speed * deltaTime;
	    	distanceY -= cos(toRadians(direction)) * speed * deltaTime;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.A)) {
	    	float a = direction - 90;
	    	if(a < 0) a += 360;
	    	distanceX += sin(toRadians(a)) * speed * deltaTime;
	    	distanceY += cos(toRadians(a)) * speed * deltaTime;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.D)) {
	    	float d = direction + 90;
	    	if(d >= 360) d -= 360;
	    	distanceX += sin(toRadians(d)) * speed * deltaTime;
	    	distanceY += cos(toRadians(d)) * speed * deltaTime;
	    }
	    
	    float targetX = x + distanceX;
	    
	    float targetY = y + distanceY;
	  
	    
	    if(!map.hasBlock((int) targetX, (int) targetY)) {
	    	x = targetX;
	    	y = targetY;
	    } else {
	    	Collision collision = camera.castRay(map, this, direction);
	    	if(collision.getOrientation() == Collision.Orientation.HORIZONTAL) {
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
