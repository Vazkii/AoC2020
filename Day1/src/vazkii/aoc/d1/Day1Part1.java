package vazkii.aoc.d1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day1Part1 {

	public static void main(String[] args) throws IOException {
		List<Integer> currInts = new ArrayList<>(200);
		Files.lines(Paths.get("input.txt"))
			.map(Integer::parseInt)
			.forEach(i -> {
				currInts.forEach(j -> {
					if(i + j == 2020) {
						System.out.println(i * j);
						System.exit(0);
					}
				});
				currInts.add(i);
			});
	}
	
}
