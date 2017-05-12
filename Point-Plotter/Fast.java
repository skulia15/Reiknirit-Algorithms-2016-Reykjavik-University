package S2;

import java.util.ArrayList;
import java.util.Arrays;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

public class Fast {
	public static void main(String[] args) {
		// Initialize variables
		int N;
		N = StdIn.readInt();
		Point[] pointArr = new Point[N];
		
		for(int i = 0; i < N; i++){										// Reads all points to the point Array
			int x = StdRandom.uniform(0, 32767);
			int y = StdRandom.uniform(0, 32767);
			//if(x < 0 || y < 0 || x > 32767 || y > 32767){
			//	throw new IndexOutOfBoundsException("Index is out of bounds!");
			//}
			Point temp = new Point(x, y);
			pointArr[i] = temp;
		}
		Stopwatch stopwatch = new Stopwatch();
		for(int i = 0; i < N - 3; i++){ 								// N-3 because we check the next consecutive points
			Arrays.sort(pointArr); 										
			Point currP = pointArr[i]; 									// set (next) point P
			Arrays.sort(pointArr, i, N, currP.SLOPE_ORDER); 			// Sort the point array from i to N by slope order for the new P
			int displace = 0;
																		// Create variable next outside of loop
			for(int j = i + 1; j < N - 2; j = j + displace){ 			// Initialize a point Q to compare the slope to P, let all points (except P) be Q to compare to P.
				int next = 0;
				displace = 1;
				ArrayList<Point> listOfPoints = new ArrayList<Point>(); // List contains all points with the same slope, have to print them when slope changes.
				listOfPoints.add(currP);								// Add first and second point to list
				listOfPoints.add(pointArr[j]);
				next = j + 1;											// Indicates the index (of the point array) of the next point to compare to P

				while(currP.slopeTo(pointArr[j]) == currP.slopeTo(pointArr[next])){ // Checks the slope from origin point P to Q and if slope of Q to the "next" point 
					listOfPoints.add(pointArr[next]);					// If so add the point to the list that prints
					next++; 											// If slope from P -> Q equals the slope from Q -> next, check if it equal from next -> after Next 
					if(next >= N){
						break;
					}
				}
				if(next - j >= 3){										// If condition works for 4 items or more, print them
					for(int g = 0; g < listOfPoints.size(); g++){
						StdOut.print(listOfPoints.get(g));
						if(g != listOfPoints.size() - 1)
						StdOut.print(" -> ");
					}
					StdOut.println();									// Empty line
				}
				displace = listOfPoints.size() - 1;						// Avoid printing multiple times if more than 4 points have the same slope
				listOfPoints.clear();									// Clear array for printing
			}
		}
		StdOut.println(stopwatch.elapsedTime());
	}
}
