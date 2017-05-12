/*************************************************************************
 * Name: Skúli Arnarsson
 * Login: skulia15@ru.is
 * Precept: 
 *
 * Compilation:  javac Point.java
 *
 * Description: An immutable data type for points in the plane.
 *
 *************************************************************************/

package S2;

import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable <Point> {
		
	// compare points by slope to this point
	public final Comparator <Point> SLOPE_ORDER = new Comparator<Point>(){
	    @Override
		public int compare(Point p1, Point p2){
	        double slope1 = slopeTo(p1);
	        double slope2 = slopeTo(p2);
	        if(slope1 > slope2){
	        	return 1;
	        }
	        if(slope1 < slope2){
	        	return -1;
	        }
	        else return 0;
	        //TODO: EDGE CASES AS IN SLOPE
	    }
	};
	
	private final int x;               // x coordinate
	private final int y;               // y coordinate
	
	// create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
	
    // plot this point to standard drawing
    public void draw() {
    	StdDraw.point(x, y);
    }
	
    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
    	StdDraw.line(this.x, this.y, that.x, that.y);
    }
	
    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
	
	// is this point lexicographically smaller than that point ?
	public int compareTo(Point that){
		// compare points by their y-coordinates, breaking ties by their x-coordinates
		// (x0, y0) is less than the argument point (x1, y1) -> y0 < y1 or if y0 = y1 and x0 < x1.
		//  Returns a negative integer, zero, or a positive integer as this object is less than, equal to, or greater than the specified object.
		int x0 = x;
		int x1 = that.x;
		int y0 = y;
		int y1 = that.y;
	
		if(y0 < y1){ // original point is smaller
			return -1;
		}
		if(y0 > y1){ // original point is larger
			return 1;
		}
		 // points have same y coord
		if(x0 < x1){
			return -1;
		}
		if(x0 > x1){
			return 1;
		}
		return 0;
	}
	
	// the slope between this point and that point
	public double slopeTo (Point that){
		// formula (y1 − y0)/(x1 − x0).
		// remember conditions for edge cases: horizontal, vertical, and degenerate line segments.
		
		if(that.y == this.y && that.x == this.x){ // same point
			return Double.NEGATIVE_INFINITY;
		}
		else if(that.y == this.y){ // line is horizontal
			//return positive zero
			return 0;
		}
		else if(that.x == this.x){ // line is vertical
			//return positive inf
			return Double.POSITIVE_INFINITY;
		}
		
		double deltaY = that.y - y;
		double deltaX = that.x - x;
		return deltaY/deltaX;
	}

}