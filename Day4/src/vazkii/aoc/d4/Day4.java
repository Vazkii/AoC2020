package vazkii.aoc.d4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day4 {

	public static void main(String[] args) throws IOException {
		Passport curr = new Passport();
		
		List<String> lines = new ArrayList<>(1105);
		Files.lines(Paths.get("input.txt"))
			.forEach(lines::add);
		
		int valid = 0;
		for(String s : lines) {
			s = s.trim();
			
			if(s.isEmpty()) {
				if(curr.isValid())
					valid++;
				
				curr = new Passport();
			} else {
				String toks[] = s.split(" ");
				for(String t : toks) {
					String[] kv = t.split(":");
					curr.put(kv[0], kv[1]);
				}
			}
		}
		
		if(curr.isValid())
			valid++;
		
		System.out.println("Valid = " + valid);
	}
	
	private static class Passport {
		
		private Map<String, String> vals = new HashMap<>();
		
		void put(String k, String v) {
			vals.put(k, v);
		}
		
		boolean isValid() {
			int target = vals.containsKey("cid") ? 8 : 7;
			return vals.size() == target
				&& isClamped("byr", 4, 1920, 2002)
				&& isClamped("iyr", 4, 2010, 2020)
				&& isClamped("eyr", 4, 2020, 2030)
				&& validHeight()
				&& matches("hcl", "\\#[0-9a-f]{6}")
				&& matches("ecl", "amb|blu|brn|gry|grn|hzl|oth")
				&& matches("pid", "\\d{9}");
		}
		
		private boolean validHeight() {
			String hgt = vals.get("hgt");
			
			String type = hgt.substring(hgt.length() - 2, hgt.length());
			String val =  hgt.substring(0, hgt.length() - 2);
			
			try {
				int valInt = Integer.parseInt(val);
				
				if(type.equals("cm"))
					return isClamped(valInt, 150, 193);
				else if(type.equals("in"))
					return isClamped(valInt, 59, 76);
			} catch(NumberFormatException e) { }
			
			return false;
		}
		
		private boolean matches(String key, String regex) {
			return vals.get(key).matches(regex);
		}
		
		private boolean isClamped(int val, int min, int max) {
			return val >= min && val <= max;
		}

		private boolean isClamped(String key, int digits, int min, int max) {
			String v = vals.get(key);
			if(v.length() != digits)
				return false;
			int vInt = Integer.parseInt(v);
			
			return isClamped(vInt, min, max);
		}
		

		
	}
	

	
}
 