package org.semillita.raycaster.camera;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.atan;
import static java.lang.Math.tan;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;
import java.util.ArrayList;
import java.util.List;

import org.semillita.raycaster.map.Map;
import org.semillita.raycaster.player.Player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Camera {

	private static final float FOV = 70;

	public Texture green;
	public Texture darkGreen;

	public void render(SpriteBatch batch, Map map, Player player) {
		
		int amountOfRays = Gdx.graphics.getWidth();
		final float cameraDistanceToScreen = (float) ((Gdx.graphics.getWidth() / 2) / tan((toRadians(FOV / 2))));
		
		for (int ray = 0; ray < amountOfRays; ray++) {
			
			float screenCollisionOffset = -(((float) amountOfRays - 1) / 2) + ray;
			float angleFromCamera = (float) (toDegrees(atan(screenCollisionOffset / cameraDistanceToScreen)));
			float rayAngle = player.direction + angleFromCamera;
						
			while (rayAngle < 0) {
				rayAngle += 360;
			}
			while (rayAngle >= 360) {
				rayAngle -= 360;
			}
			
			if(rayAngle >= 360) System.out.println("!!!");
			
			Collision collision = castRay(map, player, rayAngle);
			
			float distance = collision.getDistance();
			Texture texture;
			if(collision.getOrientation() == Collision.Orientation.HORIZONTAL) {
				texture = green;
			} else {
				texture = darkGreen;
			}
			
			distance *= cos(toRadians(Math.max(angleFromCamera, -angleFromCamera)));
			int height = (int) ((Gdx.graphics.getWidth() * 1.5) / distance);
			int windowHeight = Gdx.graphics.getHeight();
			int yOffset = (windowHeight - height) / 2;
			batch.draw(texture, ray, yOffset, 1, height);
			
			
		}
	}

	public Collision castRay(Map map, Player player, float rayAngle) {
		
		rayAngle %= 360;
		
		float xIncreasePerY = -1, yIncreasePerX = -1;
				
		int zoneIndex = (int) rayAngle / 90;
				
		xIncreasePerY = (float) tan(toRadians(rayAngle));
		yIncreasePerX = (float) (1 / tan(toRadians(rayAngle)));
		
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
		
		Collision closestCollision = Collision.getClosestCollision(horizontalCollision, verticalCollision);
				
		return closestCollision;
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
					return new Collision((float) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), Collision.Orientation.HORIZONTAL, false);
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
					return new Collision((float) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), Collision.Orientation.VERTICAL, false);
				}
		}
		return null;
	}
}
