package vazkii.aoc.d5;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day5 {

	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>(1105);
		Files.lines(Paths.get("input.txt"))
			.forEach(lines::add);
		
		List<Integer> missing = new ArrayList<>(970);
		for(int i = 0; i < 970; i++)
			missing.add(i);

		int maxId = 0;
		
		for(String s : lines) {
			int id = getId(s);
			missing.remove(Integer.valueOf(id));
			
			if(id > maxId)
				maxId = id;
		}
		
		System.out.println(maxId);
		System.out.println(missing);
		// part 2 solved by intuition on the resulting values
	}
	
	private static int getId(String s) {
		int rows = 128;
		int cols = 8;
		
		int row = getVal(s.substring(0, 7), 0, rows, 'F', 'B');
		int col = getVal(s.substring(7, s.length()), 0, cols, 'L', 'R');
		
		int id = (row * 8) + col;
		
		return id;
	}
	
	private static int getVal(String s, int min, int max, char minChar, char maxChar) {
		int currMin = min;
		int currMax = max;
		
		for(int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			if(c == minChar)
				currMax = currMin + (currMax - currMin) / 2;
			else if(c == maxChar)
				currMin = currMin + (currMax - currMin + 1) / 2;
		}
		
		return currMin;
	}
	
}
