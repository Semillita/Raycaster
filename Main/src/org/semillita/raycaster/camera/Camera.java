package org.semillita.raycaster.camera;

import java.nio.charset.MalformedInputException;
import java.util.ArrayList;
import java.util.List;

import org.semillita.raycaster.core.Game;
import org.semillita.raycaster.map.Map;
import org.semillita.raycaster.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Camera {

	private static final int FOV = 70;

	public static class Collision {
		private float distance;
		public int orientation;
		
		private Collision(float distance, int orientation) {
			this.distance = distance;
			this.orientation = orientation;
		}
	}
	//
	public Texture green;
	public Texture darkGreen;

	public void render(SpriteBatch batch, Map map, Player player) {

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
			
			float rayAngle = player.direction + (angleFromCamera * angleCoefficient);
			
			if (rayAngle < 0)
				rayAngle += 360;
			if (rayAngle >= 360)
				rayAngle -= 360;
			
			//System.out.println("Ray " + ray + " at angle " + rayAngle);
			
			Collision collision = castRay(map, player, rayAngle);
			
			float a1 = player.direction - rayAngle;
			if(a1 < 0) a1 += 360;
			float a2 = rayAngle - player.direction;
			if(a2 < 0) a2 += 360;
			
			float distance = collision.distance;
			Texture texture;
			if(collision.orientation == 1) {
				texture = green;
			} else {
				texture = darkGreen;
			}
			
			distance *= Math.cos(rad(Math.min(a1, a2)));
			int height = (int) ((Gdx.graphics.getWidth() * 1.5) / distance);
			int windowHeight = Gdx.graphics.getHeight();
			int yOffset = (windowHeight - height) / 2;
			batch.draw(texture, ray, yOffset, 1, height);
			
			
		}
	}

	public Collision castRay(Map map, Player player, float rayAngle) {
		
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
			//System.out.println("Angle3: " + angle3);
			if(angle3 == 90) {
				yIncreasePerX = 0;
			} else {
				float tan = (float) Math.tan(rad(angle3));
				//System.out.println("Tan: " + tan);
				//System.out.println("Or: " + Math.tan(angle3));
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
		
		Collision horizontalCollision = checkHorizontalCollision(map, player, xIncreasePerY, dirY);
		Collision verticalCollision = checkVerticalCollision(map, player, yIncreasePerX, dirX);
		
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
	
	private Collision checkHorizontalCollision(Map map, Player player, float xIncreasePerY, int dir) {
		int start;
		if(dir == 1) {
			start = (int) player.y + 1;
		} else {
			start = (int) player.y;
		}
		for(int yIndex = start; yIndex < map.getHeight() && yIndex >= 0; yIndex += dir) {
			
				float yIncrease = yIndex - player.y;
				float xIncrease = xIncreasePerY * yIncrease;
				float x = player.x + xIncrease;
				
				if((dir == 1 && map.hasBlock((int) x, yIndex)) || (dir == -1 && map.hasBlock((int) x, yIndex - 1))) {
					return new Collision((float) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), 1);
					//System.out.println("Hor " + (int) x + ", " + (yIndex - 1));
				}
			
		}
		return null;
	}
	
	private Collision checkVerticalCollision(Map map, Player player, float yIncreasePerX, int dir) {
		int start;
		if(dir == 1) {
			start = (int) player.x + 1;
		} else {
			start = (int) player.x;
		}
		for(int xIndex = start; xIndex < map.getHeight() && xIndex >= 0; xIndex += dir) {
			
				float xIncrease = xIndex - player.x;
				float yIncrease = yIncreasePerX * xIncrease;
				float y = player.y + yIncrease;
				
				if((dir == 1 && map.hasBlock(xIndex,(int) y)) || (dir == -1 && map.hasBlock(xIndex - 1, (int) y))) {
					return new Collision((float) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), 0);
				}
		}
		return null;
	}
}
