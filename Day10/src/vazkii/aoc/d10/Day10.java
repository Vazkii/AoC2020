package vazkii.aoc.d10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day10 {

	public static void main(String[] args) throws IOException {
		List<Integer> joltages = new ArrayList<>();

		Files.lines(Paths.get("input.txt"))
		.filter(s -> !s.isEmpty())
		.map(Integer::parseInt)
		.forEach(joltages::add);

		Collections.sort(joltages);

		System.out.println(joltages);
		//		findTotal(joltages);
		
		long arrangements = findArrangements(joltages, 0);
		System.out.println("Arrangements = " + arrangements);
	}

	private static void findTotal(List<Integer> joltages) {
		int[] offs = new int[3];
		int last = 0;

		for(int i : joltages) {
			int diff = i - last;
			offs[(diff-1)]++;
			last = i;
		}
		offs[2]++;

		int tot = offs[0] * offs[2];
		System.out.println("Total: " + tot);
	}

	private static Map<Integer, Long> KNOWN_ARRANGEMENTS = new HashMap<>();
	private static long findArrangements(List<Integer> joltages, int start) {
		if(KNOWN_ARRANGEMENTS.containsKey(start))
			return KNOWN_ARRANGEMENTS.get(start);
		
		long total = 0;
		for(int i = 1; i <= 3; i++) {
			int target = start + i;
			if(joltages.contains(target))
				total += findArrangements(joltages, target);
		}
		
		if(total == 0)
			total = 1;
		
		KNOWN_ARRANGEMENTS.put(start, total);
		return total;
	}

}
