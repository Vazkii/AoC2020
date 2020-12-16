package vazkii.aoc.d15;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day15 {

	private static int[] input = { 7,14,0,17,11,1,2 };
	
	private static Map<Integer, Number> numbers = new HashMap<>();
	
	private static Number last;
	private static int turn = 0;
	
	public static void main(String[] args) {
		int n = 0;
		for(int i = 0; i < 30000000; i++)
			n = say();
		System.out.println(n);
	}
	
	private static int say() {
		int next = 0;
		if(turn < input.length)
			next = input[turn];
		else {
			if(last.turnsSpoken.size() == 1)
				next = 0;
			else 
				next = turn - last.turnsSpoken.get(last.turnsSpoken.size() - 2) - 1;
		}
		
		if(!numbers.containsKey(next))
			numbers.put(next, new Number());
		
		last = numbers.get(next);
		last.turnsSpoken.add(turn);
		
		turn++;
		
		return next;
	}
	
	private static class Number {
		
		public List<Integer> turnsSpoken = new ArrayList<>();
		
	}
	
}
