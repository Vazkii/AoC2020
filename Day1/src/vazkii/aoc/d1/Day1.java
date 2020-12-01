package vazkii.aoc.d1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

	public static void main(String[] args) throws IOException {
		List<Integer> currInts = new ArrayList<>(200);
		
		Files.lines(Paths.get("input.txt"))
			.map(Integer::parseInt)
			.forEach(currInts::add);
		
		Collections.sort(currInts);
		System.out.println(currInts);
		
		final int count = 3;
		final int sumTarget = 2020;
		
		int[] pointers = new int[count];
		int curr[] = new int[count];
		
		int sum = 0;
		
		for(int i = 0; i < count; i++)
			pointers[i] = i;
		
		while(true) {
			for(int i = 0; i < count; i++) {
				curr[i] = currInts.get(pointers[i]);
				sum += curr[i];
			}
			
			if(sum != sumTarget) {
				for(int i = 0; i < count; i++) {
					if(i == 0 || pointers[i - 1] == pointers[i]) {
						pointers[i]++;
						
						if(i != 0) {
							pointers[i - 1] = (i > 1 ? pointers[i - 2] + 1 : 0);
						}
					}
				}
			} else {
				int prod = 1;
				for(int i = 0; i < count; i++)
					prod *= curr[i];
				
				System.out.println("prod = " + prod);
				return;
			}
			
			sum = 0;
		}
	}
	
}
