package S1;

import edu.princeton.cs.algs4.QuickFindUF;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
	private boolean grid[];
	private int N;
	private int numOfOpenSites;
	private QuickFindUF QUF;
	
	public Percolation(int N){ // create N-by-N grid, with all sites initially blocked
		 //check if N <= 0
		 if(N <= 0){
			 throw new java.lang.IllegalArgumentException();
		 }
		 this.N = N;
		 grid = new boolean[N * N];
		 QUF = new QuickFindUF((N * N) + 2);
		 numOfOpenSites = 0;
	}
	
	public void open(int row, int col){ // open the site (row, col) if it is not open already
		isOk(row, col);
		if(!isOpen(row, col)){
			int val = convertTo1D(row, col);
			grid[val] = true;
			numOfOpenSites++;
			unite(row, col);
		}
	}
	
	private int convertTo1D(int row, int col){
		return (N * row) + col;
	}
	
	private void isOk(int row, int col){
		if(row < 0 || row >= N|| col < 0 || col >= N){
			throw new java.lang.IndexOutOfBoundsException();
		}
	}
	
	private void unite(int row, int col){ // Unites the site with all adjacent open sites
		
		int site = convertTo1D(row, col);
		if(row == 0){ //If we are in the top row, connect the site with the sentinel node at the top
			QUF.union(site, N * N);
		}
		else{
			if(isOpen(row - 1, col)){
				QUF.union(site, convertTo1D(row - 1, col));
			}
		}
		
		if(row == N - 1){ //If we are in the bottom row, connect the site with the sentinel node at the bottom
			QUF.union(site, N * N + 1);
		}
		else{
			if(isOpen(row + 1, col)){
				QUF.union(site, convertTo1D(row + 1, col));
			}
		}
		
		if(col == 0){
			;
		}
		else{
			if(isOpen(row, col - 1)){
				QUF.union(convertTo1D(row, col), convertTo1D(row, col - 1));
			}
		}
		
		if(col == N - 1){
			;
		}
		else{
			if(isOpen(row, col + 1)){
				QUF.union(convertTo1D(row, col), convertTo1D(row, col + 1));
			}
		}
		
	}
	public boolean isOpen(int row, int col){ // is the site (row, col) open?
		isOk(row, col);
		int val = convertTo1D(row, col);
		return grid[val];
	}
	public boolean isFull(int row, int col){ // is the site (row, col) full? 
		//if site is connected to top virtual site = true
		isOk(row, col);
		return QUF.connected(convertTo1D(row, col), N * N);
	}
	public int numberOfOpenSites(){ // number of open sites
		return numOfOpenSites;
	}
	public boolean percolates(){ // does the system percolate?
		//true if both virtual sites are connected
		return QUF.connected(N * N, N * N + 1);
	}
	public static void main(String[] args){ // unit testing (required)
		int input = StdIn.readInt();
		Percolation test = new Percolation(input);
		test.N = input;
		StdOut.println("Number of open sites: " + test.numberOfOpenSites());
		StdOut.println("Percolates? ");
		if(test.percolates()){
			StdOut.println("Yes, percolates");
		}
		else{
			StdOut.println("No, does not percolate ");
		}
		test.open(0, 0);
		test.open(1, 0);
		StdOut.println("Percolates? ");
		if(test.percolates()){
			StdOut.println("Yes, percolates");
		}
		else{
			StdOut.println("No, does not percolate ");
		}
		if(test.isOpen(0, 1)){
			StdOut.println("Yes, is open");
		}
		else{
			StdOut.println("No, is not open");
		}
		if(test.isOpen(0, 0)){
			StdOut.println("Yes, is open");
		}
		else{
			StdOut.println("No, is not open");
		}
		StdOut.println("Number of open sites: " + test.numberOfOpenSites());
		StdOut.println("Site is full? ");
		if(test.isFull(1, 0)){
			StdOut.println("Yes, site is full");
		}
		else{
			StdOut.println("No, site is not full ");
		}
		
		StdOut.println("Site is full? ");
		if(test.isFull(1, 1)){
			StdOut.println("Yes, site is full");
		}
		else{
			StdOut.println("No, site is not full ");
		}
	}
}
