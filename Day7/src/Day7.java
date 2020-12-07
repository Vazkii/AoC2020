import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
		
		for(BagType type : types.values()) {
			type.computeContents();
//			type.validate();
		}
		
		List<BagType> candidates = findCandidates(types.get("shiny gold"));
		
		System.out.println(candidates);
		System.out.println("Candidates: " + candidates.size());
//		
//		List<String> colors = new ArrayList<>();
//		for(BagType b : candidates)
//			if(!colors.contains(b.color))
//				colors.add(b.color);
//		
//		System.out.println(colors);
//		System.out.println("Colors: " + colors.size());
	}
	
	private static List<BagType> findCandidates(BagType target) {
		List<BagType> allCandidates = new ArrayList<>();
		
		List<BagType> currentTests = new ArrayList<>();
		List<BagType> nextTests = new ArrayList<>();
		
		nextTests.add(target);
		
		do {
			for(BagType test : currentTests)
				for(BagType t : types.values())
						if(t.contents.containsKey(test))
							if(!allCandidates.contains(t) && !currentTests.contains(t))
								nextTests.add(t);
			
			allCandidates.addAll(currentTests);
			currentTests = nextTests;
			nextTests = new ArrayList<>();
		} while(currentTests.size() > 0);
		
		allCandidates.remove(target);
		
		return allCandidates;
	}
	
	private static class BagType {
		
		private static final Pattern PATTERN = Pattern.compile("((?:[a-z]+\\s){2})bags contain((?: no other bags.)|(?:(?: \\d+ (?:[a-z]+\\s){2}bags?[,\\.])+))");
		
		final String src;
		
		String name;
		String pattern, color;
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
			
			String[] nameToks = name.split(" ");
			pattern = nameToks[0];
			color = nameToks[1];
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
		
//		// for debugging
//		void validate() {
//			String s = name + " bags contain";
//			if(contents.isEmpty())
//				s += " no other bags.";
//			
//			else for(BagType t : contents.keySet()) {
//				int ct = contents.get(t);
//				s += (" " + ct + " " + t.name + " bag" + (ct == 1 ? "," : "s,"));
//			}
//			s = s.replaceAll(",$", ".");
//
//			if(!s.equals(src))
//				System.out.println(this + " is invalid -> " + contents);
//		}
		
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
