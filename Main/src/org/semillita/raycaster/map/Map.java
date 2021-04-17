package org.semillita.raycaster.map;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Map {

	private List<List<Integer>> blocks;
	private float startX = 9.5f, startY = 9.5f;
	private int goalX, goalY;
	private int width, height;
	
	public Map(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream);
		List<String> lines = new ArrayList<>();
		while (scanner.hasNext()) {
			lines.add(scanner.nextLine());
		}
		scanner.close();
		
		height = lines.size();
		width = lines.get(0).length();
		
		blocks = new ArrayList<>();
		for(int y = 0; y < lines.size(); y++) {
			for(int x = 0; x < lines.get(y).length(); x++) {
				switch(lines.get(y).charAt(x)) {
				case '1':
					blocks.add(Arrays.asList(x, y));
					break;
				case '2':
					startX = x + 0.5f;
					startY = y + 0.5f;
					break;
				case'3':
					goalX = x;
					goalY = y;
				}
			}
		}
		if(blocks.isEmpty()) System.err.println("No blocks found");
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public float getStartX() {
		return startX;
	}
	
	public float getStartY() {
		return startY;
	}
	
	public boolean hasBlock(int x, int y) {
		List<Integer> block = Arrays.asList(x, y);
		if(blocks.contains(block)) {
			//System.out.println("Collision at " + block);
			return true;
		}
		return false;
	}
	
	public boolean hasGoal(int x, int y) {
		if(x == goalX && y == goalY) {
			return true;
		}
		return false;
	}
	
}
