package vazkii.aoc.d11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day11 {


	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();

		Files.lines(Paths.get("input.txt"))
		.forEach(lines::add);
		
		char[][] cells = new char[lines.size()][lines.get(0).length()];
		for(int i = 0; i < lines.size(); i++) {
			String s = lines.get(i);
			for(int j = 0; j < s.length(); j++)
				cells[i][j] = s.charAt(j);
		}
		
		int steps = 0;
		while(true) {
			printMap(cells);
			
			char[][] next = step(cells);
			if(arraysEqual(cells, next))
				break;
			
			steps++;
			cells = next;
		}
		
		System.out.println("Done. " + steps + " steps");
		
		int occupied = 0;
		for(char[] arr : cells)
			for(char c : arr)
				if(c == '#')
					occupied++;
			
		System.out.println("Occupied Seats: " + occupied);
	}
	
	private static void printMap(char[][] cells) {
		for(char[] arr : cells) {
			for(char c : arr)
				System.out.print(c);
			System.out.println();
		}
		System.out.println("===========================");
	}
	
	private static char[][] step(char[][] cells) {
		char[][] next = new char[cells.length][cells[0].length];
		
		for(int i = 0; i < cells.length; i++)
			for(int j = 0; j < cells[0].length; j++) {
				char curr = cells[i][j];
				next[i][j] = curr;
				
				if(curr == 'L') {
					if(getAdjacentOccupied(cells, i, j) == 0)
						next[i][j] = '#';
				} else if(curr == '#') {
					if(getAdjacentOccupied(cells, i, j) >= 5)
						next[i][j] = 'L';
				}
			}
		
		return next;
	}
	
	private static int getAdjacentOccupied(char[][] cells, int x, int y) {
		return  getProjectedOccupied(cells, x, y, 0, 1)
				+ getProjectedOccupied(cells, x, y, 1, 0)
				+ getProjectedOccupied(cells, x, y, 0, -1)
				+ getProjectedOccupied(cells, x, y, -1, 0)
				+ getProjectedOccupied(cells, x, y, 1, 1)
				+ getProjectedOccupied(cells, x, y, -1, 1)
				+ getProjectedOccupied(cells, x, y, 1, -1)
				+ getProjectedOccupied(cells, x, y, -1, -1);
	}
	
	private static int getProjectedOccupied(char[][] cells, int x, int y, int xShift, int yShift) {
		int xp = x + xShift;
		int yp = y + yShift;
		
		while(xp >= 0 && yp >= 0 && xp < cells.length && yp < cells[0].length) {
			char c = cells[xp][yp];
			if(c == 'L')
				return 0;
			else if(c == '#')
				return 1;
			
			xp += xShift;
			yp += yShift;
		}
		
		return 0;
	}
	
	private static boolean arraysEqual(char[][] a1, char[][] a2) {
		if(a1.length != a2.length || a1[0].length != a2[0].length)
			return false;
		
		for(int i = 0; i < a1.length; i++)
			for(int j = 0; j < a1[0].length; j++)
				if(a1[i][j] != a2[i][j])
					return false;

		return true;
	}
	
}
