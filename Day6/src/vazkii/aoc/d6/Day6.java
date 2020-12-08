package vazkii.aoc.d6;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day6 {
	
	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();
		Files.lines(Paths.get("input.txt"))
			.forEach(lines::add);
		
		String curr = "";
		int sum = 0;
		boolean firstOfGroup = true;
		
		for(String s : lines) {
			if(s.isEmpty()) {
				sum += curr.length();
				curr = "";
				firstOfGroup = true;
				continue;
			}
			
			if(firstOfGroup) {
				curr = s;
				firstOfGroup = false;
			} else {
				String newStr = curr;
				for(int i = 0; i < curr.length(); i++) {
					char c = curr.charAt(i);
					if(s.indexOf(c) < 0)
						newStr = newStr.replaceAll(""+c, "");
				}
				curr = newStr;
			}
		}
		
		sum += curr.length();
		
		System.out.println("total " + sum);
	}

}
