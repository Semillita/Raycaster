package org.semillita.raycaster.camera;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

import org.semillita.raycaster.core.Game;
import org.semillita.raycaster.map.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Camera {

	private static final int FOV = 130;
	private static float cameraAngle = 20f;

	private class Collision {
		private float distance;
		private int orientation;
		
		private Collision(float distance, int orientation) {
			this.distance = distance;
			this.orientation = orientation;
		}
	}
	
	public Texture green;
	public Texture darkGreen;

	public void render(SpriteBatch batch, Map map) {

		List<Integer> rays = new ArrayList<>();

		int amountOfRays = Gdx.graphics.getWidth();
		float distanceBetweenRays = (float) FOV / (amountOfRays);

		for (int ray = 0; ray < amountOfRays; ray++) {
			/*float rayAngle = Game.camAngle - (FOV / 2) + (distanceBetweenRays / 2) + distanceBetweenRays * ray;*/

			final float cameraDistanceToScreen = (float) ((Gdx.graphics.getWidth() / 2) / Math.tan(Math.toRadians(FOV / 2)));
			
			float screenCollisionOffset = -(((float) amountOfRays - 1) / 2) + ray;
			
			//System.out.println("Ray " + ray + " SCO " + screenCollisionOffset + " CDTS " + cameraDistanceToScreen);
			
			int angleCoefficient = 1;
			if(screenCollisionOffset < 0) {
				screenCollisionOffset *= -1;
				angleCoefficient = -1;
			}
			float angleFromCamera = (float) Math.toDegrees(Math.atan(screenCollisionOffset / cameraDistanceToScreen));
			
			float rayAngle = Game.camAngle + (angleFromCamera * angleCoefficient);
			
			if (rayAngle < 0)
				rayAngle += 360;
			if (rayAngle >= 360)
				rayAngle -= 360;
			
			//System.out.println("Ray " + ray + " at angle " + rayAngle);
			
			Collision collision = castRay(rayAngle, map);
			
			float a1 = Game.camAngle - rayAngle;
			if(a1 < 0) a1 += 360;
			float a2 = rayAngle - Game.camAngle;
			if(a2 < 0) a2 += 360;
			
			float distance = collision.distance;
			Texture texture;
			if(collision.orientation == 1) {
				texture = green;
			} else {
				texture = darkGreen;
			}
			
			distance *= Math.cos(rad(Math.min(a1, a2)));
			int height = (int) (1500 / distance);
			batch.draw(texture, ray, Gdx.graphics.getHeight() / 2 - height / 2, 1, height);
		}
	}

	private Collision castRay(float rayAngle, Map map) {
		
		float xIncreasePerY = -1, yIncreasePerX = -1;
				
		int zoneIndex = (int) rayAngle / 90;
				
		switch(zoneIndex) {
		case 0:
			float angle0 = rayAngle;
			if(angle0 == 0) {
				xIncreasePerY = 0;
			} else {
				float tan = (float) Math.tan(rad(angle0));
				xIncreasePerY = tan;
				yIncreasePerX = 1 / tan;
			}
			break;
		case 1:
			float angle1 = 180 - rayAngle;
			if(angle1 == 90) {
				yIncreasePerX = 0;
			} else {
				float tan = (float) Math.tan(rad(angle1));
				xIncreasePerY = -tan;
				yIncreasePerX = -1 / tan;
			}
			break;
		case 2:
			float angle2 = 90 - (270 - rayAngle);
			if(angle2 == 0) {
				xIncreasePerY = 0;
			} else {
				float tan = (float) Math.tan(rad(angle2));
				xIncreasePerY = tan;
				yIncreasePerX = 1 / tan;
			}
			break;
		case 3:
			float angle3 = 360 - rayAngle;
			System.out.println("Angle3: " + angle3);
			if(angle3 == 90) {
				yIncreasePerX = 0;
			} else {
				float tan = (float) Math.tan(rad(angle3));
				System.out.println("Tan: " + tan);
				System.out.println("Or: " + Math.tan(angle3));
				xIncreasePerY = -tan;
				yIncreasePerX = -1 / tan;
			}
		}
		int dirX, dirY;
		if(zoneIndex == 0 || zoneIndex == 3) {
			dirY = 1;
		} else {
			dirY = -1;
		}
		if(zoneIndex == 0 || zoneIndex == 1) {
			dirX = 1;
		} else {
			dirX = -1;
		}
		
		Collision horizontalCollision = checkHorizontalCollision(map, xIncreasePerY, dirY);
		Collision verticalCollision = checkVerticalCollision(map, yIncreasePerX, dirX);
		
		Collision closestCollision;
		if(horizontalCollision == null) {
			closestCollision = verticalCollision;
		} else if(verticalCollision == null) {
			closestCollision = horizontalCollision;
		} else if(horizontalCollision.distance < verticalCollision.distance) {
			closestCollision = horizontalCollision;
		} else {
			closestCollision = verticalCollision;
		}
				
		return closestCollision;
	}
	
	private float rad(float degree) {
		return (float) (degree / (180 / Math.PI));
	}
	
	private Collision checkHorizontalCollision(Map map, float xIncreasePerY, int dir) {
		int start;
		if(dir == 1) {
			start = (int) Game.camY + 1;
		} else {
			start = (int) Game.camY;
		}
		for(int yIndex = start; yIndex < map.getHeight() && yIndex >= 0; yIndex += dir) {
			
				float yIncrease = yIndex - Game.camY;
				float xIncrease = xIncreasePerY * yIncrease;
				float x = Game.camX + xIncrease;
				
				if((dir == 1 && map.hasBlock((int) x, yIndex)) || (dir == -1 && map.hasBlock((int) x, yIndex - 1))) {
					return new Collision((float) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), 1);
					//System.out.println("Hor " + (int) x + ", " + (yIndex - 1));
				}
			
		}
		return null;
	}
	
	private Collision checkVerticalCollision(Map map, float yIncreasePerX, int dir) {
		int start;
		if(dir == 1) {
			start = (int) Game.camX + 1;
		} else {
			start = (int) Game.camX;
		}
		for(int xIndex = start; xIndex < map.getHeight() && xIndex >= 0; xIndex += dir) {
			
				float xIncrease = xIndex - Game.camX;
				float yIncrease = yIncreasePerX * xIncrease;
				float y = Game.camY + yIncrease;
				
				if((dir == 1 && map.hasBlock(xIndex,(int) y)) || (dir == -1 && map.hasBlock(xIndex - 1, (int) y))) {
					return new Collision((float) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), 0);
				}
		}
		return null;
	}
}
