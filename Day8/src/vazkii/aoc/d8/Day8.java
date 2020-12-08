package vazkii.aoc.d8;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day8 {

	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();
		
		Files.lines(Paths.get("input.txt"))
			.forEach(lines::add);
		
		List<Integer> ran = new ArrayList<>();
		
		int acc = 0;
		int ptr = 0;
		
		int execs = 0;
		int currChange = 0;
		
		while(true) {
			System.out.println("Changing i = " + currChange);
			
			while(ptr < lines.size()) {
				String line = lines.get(ptr);
				if(ran.contains(ptr)) {
					break;
				}
				
				ran.add(ptr);
				
				String[] toks = line.split(" ");
				String instr = toks[0];
				int arg = Integer.parseInt(toks[1]);
				
				if(execs == currChange) {
					if(instr.equals("nop"))
						instr = "jmp";
					else if(instr.equals("jmp"))
						instr = "nop";
				}
					
				switch(instr) {
				case "nop":
					ptr++;
					execs++;
					break;
				case "acc":
					acc += arg;
					ptr++;
					break;
				case "jmp":
					ptr += arg;
					execs++;
					break;
				}
			}
			
			if(ptr >= lines.size())
				break;
			
			execs = 0;
			currChange++;
			acc = 0;
			ptr = 0;
			ran.clear();
		}
		
		System.out.println("Program terminated! Acc = " + acc);
	}
	
}