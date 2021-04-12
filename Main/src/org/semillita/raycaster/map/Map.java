package org.semillita.raycaster.map;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Map {

	private List<List<Integer>> blocks;
	private int startX, startY;
	private int goalX, goalY;
	
	public Map(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream);
		List<String> lines = new ArrayList<>();
		while (scanner.hasNext()) {
			lines.add(scanner.nextLine());
		}
		scanner.close();
		
		blocks = new ArrayList<>();
		for(int y = 0; y < lines.size(); y++) {
			for(int x = 0; x < lines.get(y).length(); x++) {
				switch(lines.get(y).charAt(x)) {
				case '1':
					blocks.add(Arrays.asList(x, y));
					System.out.println("Added block " + x + ", " + y);
					break;
				case '2':
					startX = x;
					startY = y;
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
		return 20;
	}
	
	public int getHeight() {
		return 20;
	}
	
	public boolean hasBlock(int x, int y) {
		List<Integer> block = Arrays.asList(x, y);
		if(blocks.contains(block)) {
			//System.out.println("Collision at " + block);
			return true;
		}
		return false;
	}
	
}