package vazkii.aoc.d9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day9 {

	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();
		
		Files.lines(Paths.get("input.txt"))
			.forEach(lines::add);
		
		final int bufSize = 25;
		int linePtr = 0;
		
		long invalidNumber = -1;
		
		List<Long> numbers = new ArrayList<>();
		
		for(int i = 0; i < lines.size(); i++) {
			String s = lines.get(i);
			if(s.isEmpty())
				break;
			
			long l = Long.parseLong(s);
			numbers.add(l);

			// preamble over
			if(i >= bufSize && !isSum(l, linePtr, linePtr + bufSize, numbers)) {
				invalidNumber = l;
				break;
			}
			
			if(numbers.size() > bufSize)
				linePtr++;
		}
		
		System.out.println("Invalid Number is " + invalidNumber);
		System.out.println("Weakness is " + findWeakness(invalidNumber, numbers));
	}
	
	private static boolean isSum(long val, int start, int end, List<Long> numbers) {
		int ptr1 = start;
		int ptr2 = start + 1;
		
		while(ptr1 < end && ptr2 < end) {
			long l1 = numbers.get(ptr1);
			long l2 = numbers.get(ptr2);
			
			long sum = l1 + l2;
			if(sum == val)
				return true;
			
			if((ptr2 - ptr1) == 1) {
				ptr1 = start;
				ptr2++;
			} else ptr1++;
		}
		
		return false;
	}
	
	private static long findWeakness(long target, List<Long> numbers) {
		int start = numbers.indexOf(target) - 1;
		int ptr = start;

		while(ptr >= 0) {
			int ptr2 = ptr;
			long total = 0;
			long min = Long.MAX_VALUE;
			long max = Long.MIN_VALUE;

			do {
				long val = numbers.get(ptr2);
				if(val > max)
					max = val;
				else if(val < min)
					min = val;
				
				total += val;
				ptr2--;
			} while(total < target && ptr2 >= 0);
			
			if(total == target)
				return min + max;
			
			ptr--;
		}
		
		return -1;
	}
	
}
