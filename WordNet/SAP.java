package S4;

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

//TODO: throw a java.lang.IndexOutOfBoundsException
//n if one (or more) of the input arguments is not between 0 and G.V()-1

public class SAP 
{
	private Digraph graph; 
	//Constructor takes a digraph 
	public SAP(Digraph G)
	{
		graph = G; 
		Topological p = new Topological(G);
		if(!p.hasOrder()){ // if there is topological order then the graph is cyclic and we throw exception
			throw new IllegalArgumentException("Graph is not acyclic");
		}
		int count = 0;
		for(int i = 0; i < G.V(); i++){
			if(G.outdegree(i) == 0){
				count++;
			}
		}
		if(count != 1){
			throw new IllegalArgumentException("Graph is not rooted");
		}
		
		
		//if node has no out degree it is a root, we can't have more than 1 root
	}
	
	private boolean checkIfValid(int v, int w){ // Checks if vertexes are valid
		if(v < 0 && v > graph.V() - 1){
			return false;
			
		}
		if(w < 0 && w > graph.V() - 1){
			return false;
		}
		else{
			return true;
		}
	}
	
	private boolean checkIfValid(Iterable<Integer> v, Iterable<Integer> w){
		for(int i : v){
			if(i < 0 && i > graph.V() - 1){
				return false;
			}
		}
		for(int i : w){
			if(i < 0 && i > graph.V() - 1){
				return false;
			}
		}
		return true;
	}
	
	//Length of shortest ancestral path between v and w; return -1 if no path
	public int length(int v, int w)
	{
		if (!checkIfValid(v, w)){
			throw new IndexOutOfBoundsException();
		}
		
		BreadthFirstDirectedPaths BFDPV = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths BFDPW = new BreadthFirstDirectedPaths(graph, w);
		
		int commonAnc = ancestor(v, w);
		if (commonAnc == -1){ // If there is no common ancestor 
			return -1;
		}
		
		return BFDPV.distTo(commonAnc) + BFDPW.distTo(commonAnc); // length equals the distance from v to Anc + distance w to Anc
	}
	
	//a shortest common common ancestor of v and w, return -1 if no path
	public int ancestor(int v,int w)
	{
		if (!checkIfValid(v, w)){
			throw new IndexOutOfBoundsException();
		}
		
		BreadthFirstDirectedPaths BFDPV = new BreadthFirstDirectedPaths(graph, v);
		BreadthFirstDirectedPaths BFDPW = new BreadthFirstDirectedPaths(graph, w);
		
		Stack<Integer> allAncestors = new Stack<Integer>();
		
		for(int i = 0; i < graph.V(); i++){
			if(BFDPV.hasPathTo(i) && BFDPW.hasPathTo(i)){
				allAncestors.push(i);
			}
		}
		
		if(allAncestors.isEmpty()){
			return -1;
		}
		
		int minAnc = Integer.MAX_VALUE;
		int minDist = Integer.MAX_VALUE;
		
		
		for(int vertex : allAncestors){
			if(BFDPV.distTo(vertex) + BFDPW.distTo(vertex) < minDist){
				minAnc = vertex;
				minDist = BFDPV.distTo(vertex) + BFDPW.distTo(vertex);
			}
		}
		return minAnc;
	}
	
	// length of shortest ancestral path of vertex subsets A and B ; -1 if no such path
	public int length(Iterable<Integer> A,Iterable<Integer> B)
	{
		if (!checkIfValid(A, B)){
			throw new IndexOutOfBoundsException();
		}
		
		BreadthFirstDirectedPaths BFDPV = new BreadthFirstDirectedPaths(graph, A);
		BreadthFirstDirectedPaths BFDPW = new BreadthFirstDirectedPaths(graph, B);
		
		int commonAnc = ancestor(A, B);
		if (commonAnc == -1){ // If there is no common ancestor 
			return -1;
		}
		
		return BFDPV.distTo(commonAnc) + BFDPW.distTo(commonAnc); // length equals the distance from v to Anc + distance w to Anc
	}
	
	// a shortest common ancestor of vertex subsets A and B; -1 if no such path
	public int ancestor(Iterable<Integer> A,Iterable<Integer> B)
	{
		if (!checkIfValid(A, B)){
			throw new IndexOutOfBoundsException();
		}
		
		BreadthFirstDirectedPaths BFDPV = new BreadthFirstDirectedPaths(graph, A);
		BreadthFirstDirectedPaths BFDPW = new BreadthFirstDirectedPaths(graph, B);
		
		Stack<Integer> allAncestors = new Stack<Integer>();
		
		for(int i = 0; i < graph.V(); i++){
			if(BFDPV.hasPathTo(i) && BFDPW.hasPathTo(i)){
				allAncestors.push(i);
			}
		}
		
		if(allAncestors.isEmpty()){
			return -1;
		}
		
		int minAnc = Integer.MAX_VALUE;
		int minDist = Integer.MAX_VALUE;
		
		
		for(int vertex : allAncestors){
			if(BFDPV.distTo(vertex) + BFDPW.distTo(vertex) < minDist){
				minAnc = vertex;
				minDist = BFDPV.distTo(vertex) + BFDPW.distTo(vertex);
			}
		}
		return minAnc;
	}
	
	public static void main(String[] args)
	{
		int vertices = StdIn.readInt();
        int edges = StdIn.readInt();
        Digraph G = new Digraph(vertices);
        for(int i = 0; i < edges; i++){
        	int a = StdIn.readInt();
        	int b = StdIn.readInt();
        	G.addEdge(a, b);
        }
		SAP SAPtest = new SAP(G);
		
		int ancestor = SAPtest.ancestor(3, 10);
		int length = SAPtest.length(3, 10);
		StdOut.println(length);
		StdOut.println(ancestor);
		ancestor = SAPtest.ancestor(8, 11);
		length = SAPtest.length(8, 11);
		StdOut.println(length);
		StdOut.println(ancestor);
		ancestor = SAPtest.ancestor(6, 2);
		length = SAPtest.length(6, 2);
		StdOut.println(length);
		StdOut.println(ancestor);
	}
	

}