package S3;


/*************************************************************************
 *************************************************************************/

import java.util.Arrays;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Out;

public class KdTree {
	private Node root;
	
	private static class Node {
		 private Point2D p; // the point
		 private RectHV rect; // the axis-aligned rectangle
		 private Node lb; // the left/bottom subtree
		 private Node rt; // the right/top subtree
		 
		 public Node(Point2D point, RectHV rec){
			 p = point;
			 lb = null;
			 rt = null;
			 rect = rec;
		 }
	}
	
    // construct an empty set of points
    public KdTree() {
    	root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node n) {
        if(n == null){
        	return 0;
        }
        else return 1 + size(n.lb) + size(n.rt);
    }
    // add the point p to the set (if it is not already in the set)
    public void insert(Point2D p) {
    		RectHV rec = new RectHV(0,0,1,1);
        	if(!contains(p)){
        		if(p != null){
        			root = insert(root,p,true,rec);
        		}
        	}
    };
    
    private Node insert(Node n, Point2D p, boolean orient, RectHV rec){
    	if(n == null){
    		return new Node(p,rec);
    	}
    	else{
	    	if(orient){
	    		double oldX = n.p.x();
	    		double newX = p.x();
	    		if(newX < oldX){
	    			RectHV rect = new RectHV(rec.xmin(),rec.ymin(),oldX,rec.ymax());
	    			n.lb = insert(n.lb,p,!orient,rect);
	    		}
	    		else{ 
	    			RectHV rect = new RectHV(oldX,rec.ymin(),rec.xmax(),rec.ymax());
	    			n.rt = insert(n.rt,p,!orient,rect);
	    		}
	    	}
	    	else{
	    		double oldY = n.p.y();
	    		double newY = p.y();
	    		if(newY < oldY){
	    			RectHV rect = new RectHV(rec.xmin(),rec.ymin(),rec.xmax(),oldY);
	    			n.lb = insert(n.lb,p,!orient,rect);
	    		}
	    		else {
	    			RectHV rect = new RectHV(rec.xmin(),oldY,rec.xmax(),rec.ymax());
	    			n.rt = insert(n.rt,p,!orient,rect);
	    		}
	    	}
    	}
    	return n;
    }

    // does the set contain the point p?
    public boolean contains(Point2D p) {
        return contains(root,p,true);
    }
    
    private boolean contains(Node n, Point2D p, boolean orient){
    	if(n == null){
    		return false;
    	}
		if(p.equals(n.p)){
			return true;
		}
    	if(orient){
    		double oldX = n.p.x();
    		double newX = p.x();
    		if(newX < oldX)
    			return contains(n.lb,p,!orient);
    		else 
    			return contains(n.rt,p,!orient);
    	}
    	else{
    		double oldY = n.p.y();
    		double newY = p.y();
    		if(newY < oldY)
    			return contains(n.lb,p,!orient);
    		else 
    			return contains(n.rt,p,!orient);
    	}
    }

    // draw all of the points to standard draw
    public void draw() {
    	draw(root);
    }
    
    public void draw(Node n){
    	if(n == null){
    		return;
    	}
    	else{
    		draw(n.lb);
    		StdDraw.point(n.p.x(), n.p.y());
    		draw(n.rt);
    	}
    }

    // all points in the set that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
    	Queue<Point2D> results = new Queue<Point2D>();
    	if(!isEmpty()){
    		range(root,rect,results);
    	}
        return results;
    }
    
    private void range(Node n, RectHV rect, Queue<Point2D> q) {
    	if(n == null){
    		return;
    	}
    	if(!n.rect.intersects(rect)){
    		return;
    	}
    	if(rect.contains(n.p)){
    		q.enqueue(n.p);
    	}
    	if(n.lb != null){
    		range(n.lb,rect,q);
    	}
    	if(n.rt != null){
    		range(n.lb,rect,q);
    	}
    }


    // a nearest neighbor in the set to p; null if set is empty
    public Point2D nearest(Point2D p) {
    	double startingDistance = p.distanceSquaredTo(root.p);
        return findNearestPoint(root, p, root.p, startingDistance); //2 is outside of unit square and thus has greater distance than any other point in the unit square
    }
    
    private Point2D findNearestPoint(Node n, Point2D queryPoint, Point2D closestPoint, double minSoFar){
    	double min = minSoFar;
    	Point2D nearest = closestPoint;
    	
    	if(minSoFar <= n.rect.distanceSquaredTo(queryPoint)){
    		return closestPoint;
    	}
    	double newDistance = queryPoint.distanceSquaredTo(n.p);
    	if(newDistance < minSoFar){
    		min = newDistance;
    		nearest = n.p;
    	}
    	if(n.lb.rect.contains(queryPoint)){
    		nearest = findNearestPoint(n.lb,queryPoint,closestPoint,min);
    	}
    	else{
    		nearest = findNearestPoint(n.rt,queryPoint,closestPoint,min);
    	}
    	return nearest;
    }

    /*******************************************************************************
     * Test client
     ******************************************************************************/
    public static void main(String[] args) {
        In in = new In();
        Out out = new Out();
        int nrOfRecangles = in.readInt();
        int nrOfPointsCont = in.readInt();
        int nrOfPointsNear = in.readInt();
        RectHV[] rectangles = new RectHV[nrOfRecangles];
        Point2D[] pointsCont = new Point2D[nrOfPointsCont];
        Point2D[] pointsNear = new Point2D[nrOfPointsNear];
        for (int i = 0; i < nrOfRecangles; i++) {
            rectangles[i] = new RectHV(in.readDouble(), in.readDouble(),
                    in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsCont; i++) {
            pointsCont[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        for (int i = 0; i < nrOfPointsNear; i++) {
            pointsNear[i] = new Point2D(in.readDouble(), in.readDouble());
        }
        KdTree set = new KdTree();
        for (int i = 0; !in.isEmpty(); i++) {
            double x = in.readDouble(), y = in.readDouble();
            set.insert(new Point2D(x, y));
        }
        for (int i = 0; i < nrOfRecangles; i++) {
            // Query on rectangle i, sort the result, and print
            Iterable<Point2D> ptset = set.range(rectangles[i]);
            int ptcount = 0;
            for (Point2D p : ptset)
                ptcount++;
            Point2D[] ptarr = new Point2D[ptcount];
            int j = 0;
            for (Point2D p : ptset) {
                ptarr[j] = p;
                j++;
            }
            Arrays.sort(ptarr);
            out.println("Inside rectangle " + (i + 1) + ":");
            for (j = 0; j < ptcount; j++)
                out.println(ptarr[j]);
        }
        out.println("Contain test:");
        for (int i = 0; i < nrOfPointsCont; i++) {
            out.println((i + 1) + ": " + set.contains(pointsCont[i]));
        }

        out.println("Nearest test:");
        for (int i = 0; i < nrOfPointsNear; i++) {
            out.println((i + 1) + ": " + set.nearest(pointsNear[i]));
        }

        out.println();
    }
}