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

	private float x, y;
	public float direction;

	private final float movementSpeed = 4;
	private final float rotationSpeed = 70;

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

		if (Gdx.input.isKeyPressed(Keys.RIGHT))
			direction += rotationSpeed * deltaTime;
		if (Gdx.input.isKeyPressed(Keys.LEFT))
			direction -= rotationSpeed * deltaTime;
		if (direction >= 360)
			direction -= 360;
		if (direction < 360)
			direction += 360;

		float distanceX = 0, distanceY = 0;

		float movementAngle = 0;

		float ma = direction;

		int movementForward = 0;
		int movementSideway = 0;

		if (Gdx.input.isKeyPressed(Keys.W)) {
			movementForward++;
		}

		if (Gdx.input.isKeyPressed(Keys.S)) {
			movementForward--;
		}

		if (Gdx.input.isKeyPressed(Keys.A)) {
			movementSideway--;
		}

		if (Gdx.input.isKeyPressed(Keys.D)) {
			movementSideway++;
		}

		boolean moving = true;

		switch (movementSideway) {
		case 1:
			ma += 90;
			ma -= 45 * movementForward;
			break;
		case -1:
			ma += 270;
			ma += 45 * movementForward;
			break;
		case 0:
			switch (movementForward) {
			case 1:
				// movement angle remains the direction of the player
				break;
			case -1:
				ma += 180;
				break;
			case 0:
				moving = false;
			}
		}

		ma %= 360;

		if (moving) {
			distanceX = (float) sin(toRadians(ma)) * movementSpeed * deltaTime;
			distanceY = (float) cos(toRadians(ma)) * movementSpeed * deltaTime;
		}

		Collision forwardCollision = camera.castRay(map, this, ma);

		float distanceToTarget = (float) sqrt(distanceX * distanceX + distanceY * distanceY);
		
		if ((int) (x + distanceX) == 12 && (int) (y + distanceY) == 14) {
			//System.out.println("GOAL");
		}
		
		if (forwardCollision.getDistance() < distanceToTarget) {
			switch (forwardCollision.getOrientation()) {
			case HORIZONTAL:
				distanceY = 0;
				break;
			case VERTICAL:
				distanceX = 0;
			}
		}
		
		if (!map.hasBlock((int) (x + distanceX), (int) (y + distanceY))) {
			if (map.hasGoal((int) (x + distanceX), (int) (y + distanceY))) {
			} else {
				x += distanceX;
				y += distanceY;
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
