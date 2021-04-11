package org.semillita.raycaster.map;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Map {

	private List<int[]> blocks;
	private int startX, startY;
	private int goalX, goalY;
	
	public static List<String> load(InputStream inputStream) {
		Scanner scanner = new Scanner(inputStream);
		List<String> lines = new ArrayList<>();
		while (scanner.hasNext()) {
			lines.add(scanner.nextLine());
		}
		scanner.close();
		return lines;
	}
	
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
					int[] block = {x, y};
					blocks.add(block);
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
	
	
	
}
