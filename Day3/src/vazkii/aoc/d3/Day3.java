package vazkii.aoc.d3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day3 {

	public static void main(String[] args) throws IOException {
		int lines = 323;
		List<String> rows = new ArrayList<>(lines);
		
		Files.lines(Paths.get("input.txt"))
			.forEach(rows::add);
		

		long total = checkTrees(lines, rows, 1, 1);
		total *= checkTrees(lines, rows, 3, 1);
		total *= checkTrees(lines, rows, 5, 1);
		total *= checkTrees(lines, rows, 7, 1);
		total *= checkTrees(lines, rows, 1, 2);
		
		System.out.println(total);
	}
	
	private static int checkTrees(int lines, List<String> rows, int horz, int vert) {
		int x = 0;
		int y = 0;
		
		int trees = 0;
		
		while(y < lines) {
			String row = rows.get(y);
			char c = row.charAt(x);
			if(c == '#')
				trees++;
			
			x = (x + horz) % row.length();
			
			y += vert;
		}
		
		
		System.out.println("(" + horz + ", " + vert + ") = " + trees);
		return trees;
	}
	
}
