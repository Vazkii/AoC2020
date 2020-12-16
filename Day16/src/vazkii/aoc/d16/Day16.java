package vazkii.aoc.d16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day16 {

	private static final Pattern FIELD_PATTERN = Pattern.compile("((?:\\w|\\s)+)\\: (\\d+)-(\\d+) or (\\d+)-(\\d+)");

	private static final String MY_TICKET = "127,109,139,113,67,137,71,97,53,103,163,167,131,83,157,101,107,79,73,89";

	public static void main(String[] args) throws IOException {
		List<Field> fields = new ArrayList<>();
		List<Ticket> tickets = new ArrayList<>();

		Files.lines(Paths.get("input_fields.txt"))
		.map(Field::from)
		.forEach(fields::add);

		Files.lines(Paths.get("input_tickets.txt"))
		.map(Ticket::from)
		.forEach(tickets::add);

		int totalError = 0;
		for(Ticket t : tickets)
			totalError += t.getError(fields);

		System.out.println("Total error: " + totalError);

		tickets.removeIf(Ticket::isErrored);
		
		for(Field f : fields)
			f.updateIndex(tickets, fields.size());
		
		boolean culled = false;
		do {
			culled = false;
			for(Field f : fields)
				culled = culled || f.cullOthers(fields);
		} while(culled);
		
		Ticket myTicket = Ticket.from(MY_TICKET);
		long v = 1;
		for(Field f : fields)
			if(f.name.startsWith("departure"))
				v *= myTicket.values[f.validIndexes.get(0)];
		
		System.out.println("Value: " + v);
	}

	private static class Field {

		final String name;
		final int low1, high1, low2, high2;
		
		List<Integer> validIndexes = new ArrayList<>();
		boolean culled = false;

		public Field(String name, int low1, int high1, int low2, int high2) {
			this.name = name;
			this.low1 = low1;
			this.high1 = high1;
			this.low2 = low2;
			this.high2 = high2;
		}

		public boolean isValid(int i) {
			return (i >= low1 && i <= high1) || (i >= low2 && i <= high2); 
		}

		public static Field from(String s) {
			Matcher m = FIELD_PATTERN.matcher(s);
			m.matches();

			return new Field(m.group(1), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)), Integer.parseInt(m.group(5)));
		}

		public void updateIndex(List<Ticket> tickets, int fieldCount) {
			for(int i = 0; i < fieldCount; i++) {
				validIndexes.add(i);
				
				for(Ticket t : tickets) {
					int val = t.values[i];
					if(!isValid(val)) {
						validIndexes.remove(Integer.valueOf(i));
						break;
					}
				}
			}
		}
		
		public boolean cullOthers(List<Field> fields) {
			if(!culled && validIndexes.size() == 1) {
				int remove = validIndexes.get(0);
				for(Field f : fields)
					if(f != this)
						f.validIndexes.remove(Integer.valueOf(remove));
				
				culled = true;
				return true;
			}
			
			return false;
		}

		@Override
		public String toString() {
			return String.format("%s -> [%d, %d], [%d, %d]\n", name, low1, high1, low2, high2);
		}

	}

	private static class Ticket {

		private final int[] values;
		private boolean errored = false;

		public Ticket(int[] values) {
			this.values = values;
		}

		public int getError(List<Field> fields) {
			int error = 0;

			for(int i : values) {
				boolean valid = false;
				for(Field f : fields)
					if(f.isValid(i)) {
						valid = true;
						break;
					}

				if(!valid)
					error += i;
			}

			if(error > 0)
				errored = true;

			return error;
		}

		public boolean isErrored() {
			return errored;
		}

		public static Ticket from(String s) {
			String[] toks = s.split(",");
			int[] vals = new int[toks.length];

			for(int i = 0; i < toks.length; i++)
				vals[i] = Integer.parseInt(toks[i]);

			return new Ticket(vals);
		}

		@Override
		public String toString() {
			return Arrays.toString(values) + "\n";
		}

	}

}
