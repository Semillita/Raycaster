package org.semillita.raycaster.camera;

public class Collision {

	public static enum Orientation {
		HORIZONTAL,
		VERTICAL;
	}
	
	public static Collision getClosestCollision(Collision col1, Collision col2) {
		if(col1 == null) {
			return col2;
		} else if(col2 == null) {
			return col1;
		} else if(col1.getDistance() < col2.getDistance()) {
			return col1;
		} else {
			return col2;
		}
	}
	
	private float distance;
	private Orientation orientation;
	private boolean goal;
	
	public Collision(float distance, Orientation orientation, boolean goal) {
		this.distance = distance;
		this.orientation = orientation;
		this.goal = goal;
	}
	
	public float getDistance() {
		return distance;
	}
	
	public Orientation getOrientation() {
		return orientation;
	}
	
	public boolean isGoal() {
		return goal;
	}
	
}