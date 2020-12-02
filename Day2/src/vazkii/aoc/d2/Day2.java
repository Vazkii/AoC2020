package vazkii.aoc.d2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {

	public static void main(String[] args) throws IOException {
		List<Password> currPasswords = new ArrayList<>(200);
		
		Files.lines(Paths.get("input.txt"))
			.map(Password::new)
			.forEach(currPasswords::add);
		
		int valid = 0;
		for(Password p : currPasswords)
			if(p.isValid())
				valid++;
		
		System.out.println("Valid: " + valid);
	}
	
	private static class Password {
		
		private static final Pattern PATTERN = Pattern.compile("(\\d+)-(\\d+) (.)\\: (.+)");
		
		private final int pos1;
		private final int pos2;
		private final char search;
		private final String pw;
		
		Password(String s) {
			Matcher m = PATTERN.matcher(s.trim());
			m.matches();
			
			pos1 = Integer.parseInt(m.group(1));
			pos2 = Integer.parseInt(m.group(2));
			search = m.group(3).charAt(0);
			pw = m.group(4);
		}
		
		public boolean isValid() {
			boolean first = pw.charAt(pos1 - 1) == search;
			boolean second = pw.charAt(pos2 - 1) == search;
			
			return (first || second) && (!(first && second));
		}
		
	}
	
}

