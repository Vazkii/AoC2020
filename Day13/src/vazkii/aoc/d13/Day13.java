package vazkii.aoc.d13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Day13 {

	public static void main(String[] args) throws IOException {
		List<String> lines = new ArrayList<>();

		Files.lines(Paths.get("input.txt"))
		.forEach(lines::add);

		int min = Integer.parseInt(lines.get(0).trim());

		String timetable = lines.get(1).trim();
		String[] toks = timetable.split(",");

		List<Integer> busses = new ArrayList<>();
		for(String s : toks)
			if(!s.equals("x"))
				busses.add(Integer.parseInt(s));
			else busses.add(-1);
		System.out.println(busses);

		//		for(int b : busses) {
		//			if(b == -1)
		//				continue;
		//			
		//			int div = min / b;
		//			int first = b * (div + 1);
		//			
		//			System.out.println("Bus: " + b);
		//			System.out.println("First after: " + first);
		//			System.out.println("Res: " + (b * (first - min)));
		//			System.out.println();
		//		}

		List<Bus> busObjs = new ArrayList<>();		

		for(int i = 0; i < busses.size(); i++) {
			int bus = busses.get(i);
			if(bus != -1)
				busObjs.add(new Bus(bus, i));
		}

		while(busObjs.size() > 1) {
			Bus b1 = busObjs.get(0);
			Bus b2 = busObjs.get(1);

			long mod = b1.id * b2.id;

			System.out.println("x mod " + b1.id + " = " + b1.off);
			System.out.println("x mod " + b2.id + " = " + b2.off);
			System.out.println("Target: " + mod);

			for(long i = b1.off; i < mod; i += b1.id)
				if(((i - b1.off) % b1.id) == 0 && ((i - b2.off) % b2.id) == 0) {
					System.out.println("<=> x mod " + mod + " = " + i);

					b1.id = mod;
					b1.off = i;
					busObjs.remove(1);

					break;
				}
		}

		System.out.println(busObjs.get(0).id - busObjs.get(0).off);
	}

	private static class Bus {
		long id, off;

		public Bus(int id, int off) {
			this.id = id;
			this.off = off;
		}

		@Override
		public String toString() {
			return "(" + id + ", " + off + ")";
		}

	}

}

