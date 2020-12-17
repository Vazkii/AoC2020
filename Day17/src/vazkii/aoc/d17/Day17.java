package vazkii.aoc.d17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day17 {

	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();

		Files.lines(Paths.get("input.txt"))
		.forEach(lines::add);
		
		int w = lines.get(0).length();
		int h = 1;
		boolean[][][][] curr = new boolean[h][h][w][w];
		
		for(int i = 0; i < lines.size(); i++) {
			String s = lines.get(i);
			for(int j = 0; j < s.length(); j++)
				curr[0][0][i][j] = (s.charAt(j) == '#');
		}
		
		int cycles = 6;

		print(curr);
		
		for(int i = 0; i < cycles; i++) {
			System.out.println("CYCLE " + (i+1) + "\n");
			h += 2;
			w += 2;
			
			boolean[][][][] next = new boolean[h][h][w][w];
			curr = next(curr, next);
			print(curr);
		}
		
		int total = 0;
		for(int i = 0; i < curr.length; i++)
			for(int j = 0; j < curr[0].length; j++)
				for(int k = 0; k < curr[0][0].length; k++)
					for(int l = 0; l < curr[0][0][0].length; l++)
						if(curr[i][j][k][l])
							total++;
		
		System.out.println("Total " + total);
	}
	
	private static boolean[][][][] next(boolean[][][][] curr, boolean[][][][] next) {
		for(int i = 0; i < next.length; i++)
			for(int j = 0; j < next[0].length; j++)
				for(int k = 0; k < next[0][0].length; k++)
					for(int l = 0; l < next[0][0][0].length; l++)
						next[i][j][k][l] = isEnabled(curr, i - 1, j - 1, k - 1, l - 1);
		
		return next;
	}
	
	private static boolean isEnabled(boolean[][][][] curr, int x, int y, int z, int w) {
		int neighbors = 0;
		
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				for(int k = 0; k < 3; k++)
					for(int l = 0; l < 3; l++) 
						if(i != 1 || j != 1 || k != 1 || l != 1)
							if(check(curr, x - 1 + i, y - 1 + j, z - 1 + k, w - 1 + l))
								neighbors++;
		
		boolean active = check(curr, x, y, z, w);
		if(active)
			return neighbors == 2 || neighbors == 3;
		else return neighbors == 3;
	}
	
	private static boolean check(boolean[][][][] curr, int x, int y, int z, int w) {
		return !(x < 0 || y < 0 || z < 0 || w < 0 ||
				x >= curr.length || y >= curr[0].length || z >= curr[0][0].length || w >= curr[0][0][0].length) 
				&& curr[x][y][z][w];
	}
	
	private static void print(boolean[][][][] curr) {
		for(int i = 0; i < curr.length; i++) {
			System.out.println("HYPERLAYER" + i);
			for(int j = 0; j < curr[i].length; j++) {
				System.out.println("layer" + j);
				
				for(int k = 0; k < curr[i][j].length; k++) {
					for(int l = 0; l < curr[i][j][k].length; l++)
						if(curr[i][j][k][l])
							System.out.print("#");
						else System.out.print(".");
					System.out.println();
				}
				
				System.out.println();
			}
			
			System.out.println();
		}
	}

}
