package vazkii.aoc.d7;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day7 {

	private static Map<String, BagType> types = new LinkedHashMap<>();
	
	public static void main(String[] args) throws IOException {
		Files.lines(Paths.get("input.txt"))
			.forEach(l -> {
				if(!l.isEmpty()) {
					BagType type = new BagType(l);
					types.put(type.name, type);
				}
			});
		
		for(BagType type : types.values())
			type.computeContents();
		
		
		BagType shinyGold = types.get("shiny gold");
		// p1:
//		Collection<BagType> candidates = findCandidates(shinyGold);
//		System.out.println(candidates);
//		System.out.println("Candidates: " + candidates.size());
		
		// p2:
		System.out.println("Total bags: " + getTotalBags(shinyGold));
	}
	
	private static Collection<BagType> findCandidates(BagType target) {
		Set<BagType> allCandidates = new HashSet<>();
		
		Set<BagType> currentTests = new HashSet<>();
		Set<BagType> nextTests = new HashSet<>();
		
		currentTests.add(target);
		
		do {
			for(BagType test : currentTests)
				for(BagType t : types.values())
						if(t.contents.containsKey(test))
							if(!allCandidates.contains(t) && !currentTests.contains(t))
								nextTests.add(t);
			
			allCandidates.addAll(currentTests);
			currentTests = nextTests;
			nextTests = new HashSet<>();
		} while(currentTests.size() > 0);
		
		allCandidates.remove(target);
		return allCandidates;
	}
	
	private static int getTotalBags(BagType type) {
		int total = 0;
		
		for(BagType next : type.contents.keySet())
			total += type.contents.get(next) * (1 + getTotalBags(next));
		
		return total;
	}
	
	private static class BagType {
		
		private static final Pattern PATTERN = Pattern.compile("((?:[a-z]+\\s){2})bags contain((?: no other bags.)|(?:(?: \\d+ (?:[a-z]+\\s){2}bags?[,\\.])+))");
		
		final String src;
		
		String name;
		String contentsStr;
		
		Map<BagType, Integer> contents = new LinkedHashMap<>();
		
		public BagType(String src) {
			this.src = src.trim();
			
			parse();
		}
		
		private void parse() {
			Matcher m = PATTERN.matcher(src);
			m.matches();
			
			name = m.group(1).trim();
			contentsStr = m.group(2).trim();
		}
		
		void computeContents() {
			if(contentsStr.contains("no other bags"))
				return;
			
			String s = contentsStr.replaceAll("\\.", "");
			
			String[] tokens = s.split(",");
			for(String t : tokens) {
				t = t.replaceAll(" bags?", "").trim();

				int c = Integer.parseInt(t.substring(0, 1));
				String tp = t.substring(2);
				BagType bt = types.get(tp);
				contents.put(bt, c);
			}
		}
		
		@Override
		public String toString() {
			return name;
		}
		
		@Override
		public int hashCode() {
			return name.hashCode();
		}
		
		@Override
		public boolean equals(Object obj) {
			return obj == this || (obj instanceof BagType && ((BagType) obj).name.equals(name));
		}
		
	}
	
}
