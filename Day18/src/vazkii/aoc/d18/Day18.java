package vazkii.aoc.d18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day18 {

	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();

		Files.lines(Paths.get("input.txt"))
		.forEach(lines::add);

		final boolean precedence = true;

		long val = 0;
		for(String s : lines)
			val += solve(s, precedence);
		
		System.out.println("Total: " + val);
	}
	
	private static long solve(String formula, boolean usePrecedence) {
		System.out.println(formula);
		
		int par = 0;
		int parStart = -1;
		
		// resolve parenthesis
		for(int i = 0; i < formula.length(); i++) {
			char c = formula.charAt(i);
			if(c == '(') {
				par++;
				if(parStart == -1)
					parStart = i;
			} else if(c == ')') {
				par--;
				if(par == 0)
					return solve(formula.substring(0, parStart) + Long.toString(solve(formula.substring(parStart + 1, i), usePrecedence)) + formula.substring(i + 1), usePrecedence); 
					
			}
		}
		
		if(usePrecedence) {
			if(formula.contains("*") && formula.contains("+")) {
				formula = formula.replaceAll("(\\d+) \\+ (\\d+)", "($1 + $2)");
				return solve(formula, usePrecedence);
			}
		}
		
		String[] toks = formula.trim().split(" ");
		long val = 0;
		char next = ' ';
		
		for(String s : toks) {
			switch(s) {
			case "*":
				next = '*';
				break;
			case "+":
				next = '+';
				break;
			default:
				long i = Long.parseLong(s);
				switch(next) {
				case ' ':
					val = i;
					break;
				case '*':
					val *= i;
					break;
				case '+':
					val += i;
					break;
				}
				
				next = ' ';
				break;
			}
		}
		
		System.out.println("Value = " + val);
		return val;
	}
	
}
