package vazkii.aoc.d12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Day12 {

	public static void main(String[] args) throws IOException {
		Ship ship = new Ship();
		
		Files.lines(Paths.get("input.txt"))
			.forEach(s -> {
				String func = s.substring(0, 1);
				int arg = Integer.parseInt(s.substring(1));
				
				switch(func) {
				case "N": ship.moveWp(0, arg); break;
				case "S": ship.moveWp(0, -arg); break;
				case "E": ship.moveWp(arg, 0); break;
				case "W": ship.moveWp(-arg, 0); break;
				
				case "L": ship.rotateWp(-arg); break;
				case "R": ship.rotateWp(arg); break;
				
				case "F": ship.forward(arg); break;
				}
				
				System.out.println(s + " => (east " + ship.e + ", north " + ship.n + ", wpEast " + ship.we + ", wpNorth " + ship.wn + ")");
			});
		
		System.out.println("Manhattan: " + ship.manhattan());
	}
	
	private static class Ship {
		
		int e, n;
		int we, wn;
		
		public Ship() {
			we = 10;
			wn = 1;
		}
		
		public void forward(int l) {
			e += we * l;
			n += wn * l;
		}
		
		public void rotateWp(int a) {
			if(a < 0)
				a = 360 + a;
			
			int cwe = we;
			int cwn = wn;
			
			switch(a) {
			case 90:
				we = cwn;
				wn = -cwe;
				break;
			case 180:
				we = -cwe;
				wn = -cwn;
				break;
			case 270:
				we = -cwn;
				wn = cwe;
				break;
			}
		}
		
		public void moveWp(int ed, int nd) {
			we += ed;
			wn += nd;
		}
		
		public int manhattan() {
			return Math.abs(e) + Math.abs(n);
		}
		
	}
	
}
