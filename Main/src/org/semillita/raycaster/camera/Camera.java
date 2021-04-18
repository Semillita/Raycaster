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

	private static final double FOV = 70;

	public Texture green;
	public Texture darkGreen;
	public Texture red;

	public Camera() {
		green = new Texture("green.png");
		darkGreen = new Texture("darkGreen.png");
		red = new Texture("red.png");
	}
	
	public void render(SpriteBatch batch, Map map, Player player) {
		
		int amountOfRays = Gdx.graphics.getWidth();
		final double cameraDistanceToScreen = (double) ((Gdx.graphics.getWidth() / 2) / tan((toRadians(FOV / 2))));
		
		for (int ray = 0; ray < amountOfRays; ray++) {
			
			double screenCollisionOffset = -(((double) amountOfRays - 1) / 2) + ray;
			double angleFromCamera = (double) (toDegrees(atan(screenCollisionOffset / cameraDistanceToScreen)));
			double rayAngle = player.direction + angleFromCamera;
						
			while (rayAngle < 0) {
				rayAngle += 360;
			}
			rayAngle %= 360;
			
			
			Collision collision = castRay(map, player, rayAngle);
			
			double distance = collision.getDistance();
			Texture texture;
			
			if(collision.isGoal()) {
				texture = red;
			} else if(collision.getOrientation() == Collision.Orientation.HORIZONTAL) {
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

	public Collision castRay(Map map, Player player, double rayAngle) {
		
		rayAngle %= 360;
		
		double xIncreasePerY = -1, yIncreasePerX = -1;
				
		int zoneIndex = (int) rayAngle / 90;
				
		xIncreasePerY = (double) tan(toRadians(rayAngle));
		yIncreasePerX = (double) (1 / tan(toRadians(rayAngle)));
		
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
	
	private Collision checkHorizontalCollision(Map map, Player player, double xIncreasePerY, int dir) {
		int start;
		if(dir == 1) {
			start = (int) player.getY() + 1;
		} else {
			start = (int) player.getY();
		}
		for(int yIndex = start; yIndex < map.getHeight() && yIndex >= 0; yIndex += dir) {
			
				double yIncrease = yIndex - player.getY();
				double xIncrease = xIncreasePerY * yIncrease;
				double x = player.getX() + xIncrease;
				
				if((dir == 1 && map.hasBlock((int) x, yIndex)) || (dir == -1 && map.hasBlock((int) x, yIndex - 1))) {
					return new Collision((double) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), Collision.Orientation.HORIZONTAL, false);
				} else if ((dir == 1 && map.hasGoal((int) x, yIndex)) || (dir == -1 && map.hasGoal((int) x, yIndex - 1))) {
					return new Collision((double) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), Collision.Orientation.HORIZONTAL, true);
				}
			
		}
		return null;
	}
	
	private Collision checkVerticalCollision(Map map, Player player, double yIncreasePerX, int dir) {
		int start;
		if(dir == 1) {
			start = (int) player.getX() + 1;
		} else {
			start = (int) player.getX();
		}
		for(int xIndex = start; xIndex < map.getHeight() && xIndex >= 0; xIndex += dir) {
			
				double xIncrease = xIndex - player.getX();
				double yIncrease = yIncreasePerX * xIncrease;
				double y = player.getY() + yIncrease;
				
				if((dir == 1 && map.hasBlock(xIndex,(int) y)) || (dir == -1 && map.hasBlock(xIndex - 1, (int) y))) {
					return new Collision((double) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), Collision.Orientation.VERTICAL, false);
				} else if ((dir == 1 && map.hasGoal(xIndex,(int) y)) || (dir == -1 && map.hasGoal(xIndex - 1,(int) y))) {
					return new Collision((double) Math.sqrt(yIncrease * yIncrease + xIncrease * xIncrease), Collision.Orientation.VERTICAL, true);
				}
		}
		return null;
	}
}
