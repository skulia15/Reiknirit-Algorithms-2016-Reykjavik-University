package S2;
import java.util.Arrays;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Brute {

	public static void main(String[] args) {
		int N;
		N = StdIn.readInt();
		Point[] arr = new Point[N];
		for(int i = 0; i < N; i++){
			int x = StdRandom.uniform(0, 32767);
			int y = StdRandom.uniform(0, 32767);
			//int x = StdIn.readInt();
			//int y = StdIn.readInt();
			Point temp = new Point(x, y);
			arr[i] = temp;
		}
		Stopwatch stopwatch = new Stopwatch();
		Arrays.sort(arr, 0, N);
		for(int i = 0; i < N; i++){
			for(int j = i + 1; j < N; j++){
				for(int k = j + 1; k < N; k++){
					for(int l = k + 1; l < N; l++){
						//compareSlopes(arr[i], arr[j], arr[k], arr[l]);
						Point p = arr[i];
						Point q = arr[j];
						Point r = arr[k];
						Point s = arr[l];
						
						if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == q.slopeTo(s)){
							StdOut.println(p.toString() + " -> " + q.toString() + " -> " + r.toString() + " -> "  + s.toString());
						}
					}	
				}
			}
		}
		StdOut.println(stopwatch.elapsedTime());
	}
	private static void compareSlopes(Point p, Point q, Point r, Point s){
		if (p.slopeTo(q) == p.slopeTo(r) && p.slopeTo(r) == q.slopeTo(s)){
			StdOut.println(p.toString() + " -> " + q.toString() + " -> " + r.toString() + " -> "  + s.toString());
		}
	}


}
