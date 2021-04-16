package org.semillita.raycaster.player;

import static java.lang.Math.sin;
import static java.lang.Math.asin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;
import static java.lang.Math.atan;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import static java.lang.Math.sqrt;

import org.semillita.raycaster.camera.Camera;
import org.semillita.raycaster.camera.Collision;
import org.semillita.raycaster.camera.Collision.Orientation;
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
	    
	    float angleSum = 0;
	    
	    if(Gdx.input.isKeyPressed(Keys.W)) {
	    	distanceX += sin(toRadians(direction)) * speed * deltaTime;
	    	distanceY += cos(toRadians(direction)) * speed * deltaTime;
	    	
	    	float a = direction;
	    	if(a >= 180) {
	    		a -= 180;
	    		a *= -1;
	    	}
	    	angleSum += a;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.S)) {
	    	distanceX -= sin(toRadians(direction)) * speed * deltaTime;
	    	distanceY -= cos(toRadians(direction)) * speed * deltaTime;
	    		    	
	    	float a = (direction + 180) % 360;
	    	if(a >= 180) {
	    		a -= 180;
	    		a *= -1;
	    	}
	    	angleSum += a;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.A)) {
	    	float a = direction - 90;
	    	if(a < 0) a += 360;
	    	distanceX += sin(toRadians(a)) * speed * deltaTime;
	    	distanceY += cos(toRadians(a)) * speed * deltaTime;
	    	
	    	float an = (direction + 270) % 360;
	    	if(an >= 180) {
	    		an -= 180;
	    		an *= -1;
	    	}
	    	angleSum += an;
	    }
	    
	    if(Gdx.input.isKeyPressed(Keys.D)) {
	    	float d = direction + 90;
	    	if(d >= 360) d -= 360;
	    	distanceX += sin(toRadians(d)) * speed * deltaTime;
	    	distanceY += cos(toRadians(d)) * speed * deltaTime;
	    	
	    	float a = (direction + 90) % 360;
	    	if(a >= 180) {
	    		a -= 180;
	    		a *= -1;
	    	}
	    	angleSum += a;
	    }
	    
	    if(angleSum < 0) {
	    	angleSum *= -1;
	    	angleSum += 180;
	    }
	    
	    System.out.println("x: " + x);
	    System.out.println("y: " + y);
	    
	    System.out.println("distanceX: " + distanceX);
	    System.out.println("distanceY: " + distanceY);
	    
	    //float angle = (float) toDegrees(asin(distanceX / (speed * deltaTime)));
	    
	    Collision forwardCollision = camera.castRay(map, this, angleSum);
	    
	    System.out.println("forward collision distance: " + forwardCollision.getDistance());
	    System.out.println(forwardCollision.getOrientation());
	    
	    float distanceToTarget = (float) sqrt(distanceX * distanceX + distanceY * distanceY);
	    
	    if(forwardCollision.getDistance() < distanceToTarget) {
	    	switch(forwardCollision.getOrientation()) {
	    	case HORIZONTAL:
	    		distanceY = 0;
	    		break;
	    	case VERTICAL:
	    		distanceX = 0;
	    	}
	    }
	    
	    if(!map.hasBlock((int) (x + distanceX), (int) (y + distanceY))) {
	    	x += distanceX;
	    	y += distanceY;
	    }
	    
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
}
