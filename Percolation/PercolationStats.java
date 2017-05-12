package S1;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
	private double[] results;
	private int T;
	private int N;
	public PercolationStats(int N, int T){ // perform T independent experiments on an N-by-N grid
		if(T <= 0 || N <= 0){
			throw new java.lang.IllegalArgumentException();
		}
		results = new double[T];
		this.T = T;
		this.N = N;
		for(int i = 0; i < T; i++){
			results[i] = monteCarlo(N);
		}
	}
	public double mean(){
		// sample mean of percolation threshold
		return StdStats.mean(results);
	}
	public double stddev(){
		// sample standard deviation of percolation threshold
		return StdStats.stddevp(results);
	}
	public double confidenceLow(){
		// low endpoint of 95% confidence interval
		return mean() - (1.96 * (stddev() / Math.sqrt(T)));
	}
	public double confidenceHigh(){
		// high endpoint of 95% confidence interval
		return mean() + (1.96 * (stddev() / Math.sqrt(T)));
	}
	public double monteCarlo(int N){
		Percolation test = new Percolation(N);
		while(!test.percolates()){
			int randRow = StdRandom.uniform(0, N);
			int randCol = StdRandom.uniform(0, N);
			test.open(randRow, randCol);
		}
		return test.numberOfOpenSites() / (double)(N * N);
	}
	public static void main(String[] args){		
		int N = StdIn.readInt();
		int T = StdIn.readInt();
		Stopwatch stopWatch = new Stopwatch();
		PercolationStats test = new PercolationStats(N, T);
		StdOut.println("Mean: ");
		StdOut.println(test.mean());
		StdOut.println("Stddev: ");
		StdOut.println(test.stddev());
		StdOut.println("Confidence High: ");
		StdOut.println(test.confidenceHigh());
		StdOut.println("Confidence Low: ");
		StdOut.println(test.confidenceLow());
		StdOut.println("Time:  ");
		StdOut.println(stopWatch.elapsedTime());
	}

}
