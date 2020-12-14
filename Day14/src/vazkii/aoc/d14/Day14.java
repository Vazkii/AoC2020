package vazkii.aoc.d14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day14 {
	
	private static final Pattern MEM_PATTERN = Pattern.compile("mem\\[(\\d+)\\] = (\\d+)");
	
	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();

		Files.lines(Paths.get("input.txt"))
		.forEach(lines::add);

		long mask0 = 0;
		long mask1 = 0;
		
		List<Integer> floating = new ArrayList<>();
		Map<Long, Long> mem = new HashMap<>();
		
		for(String s : lines) {
			if(s.startsWith("mask = ")) {
				String maskStr = s.substring("mask = ".length());
				
				mask0 = 0L;
				mask1 = 0L;
				floating.clear();
				
				for(int i = 0; i < maskStr.length(); i++) {
					char c = maskStr.charAt(maskStr.length() - i - 1);
					if(c == '0')
						mask0 += (1L << i);
					else if(c == '1')
						mask1 += (1L << i);
					else if(c == 'X')
						floating.add(i);
				}
				
				System.out.println("Applying Mask: " + maskStr);
				System.out.println("       0 Mask: " + Long.toBinaryString(mask0));
				System.out.println("       1 Mask: " + Long.toBinaryString(mask1));
				System.out.println();
			}
			
			else {
				Matcher m = MEM_PATTERN.matcher(s);
				if(m.matches()) {
					long addr = Long.parseLong(m.group(1));
					long val = Long.parseLong(m.group(2));
					
					System.out.println(addr + " -> " + val);
					
					addr |= mask1;
					addPossibleAddresses(addr, val, mem, floating, 0);
				}
			}
		}
		
		long total = 0;
		for(long l : mem.values())
			total += l;
		
		System.out.println("Total: " + total);
	}
	
	private static void addPossibleAddresses(long addr, long val, Map<Long, Long> mem, List<Integer> floating, int start) {
		if(floating.size() == start) {
			System.out.println("Put: " + addr + " : " + val);
			mem.put(addr, val);
			return;
		}
		
		int index = floating.get(start);
		long addr0 = addr & ~(1L << index);
		long addr1 = addr | (1L << index);

		if(start == 0)
			System.out.println();
		
		System.out.println("Index: " + start);
		System.out.println("Floating: " + floating);
		System.out.println("Addr:  " + Long.toBinaryString(addr));
		System.out.println("Addr0: " + Long.toBinaryString(addr0));
		System.out.println("Addr1: " + Long.toBinaryString(addr1));
		
		addPossibleAddresses(addr0, val, mem, floating, start + 1);
		addPossibleAddresses(addr1, val, mem, floating, start + 1);
	}
	
}

