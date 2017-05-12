package S4;

import java.util.HashMap;
import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Topological;

public class WordNet {
	//constructor takes the name of the two input files

	private HashMap<String, Bag<Integer>> lookupByString = new HashMap<String, Bag<Integer>>();
	private HashMap<Integer, String> LookupByID = new HashMap<Integer, String>();
	private SAP SAPObject;
	
	
	public WordNet(String synsets, String hypernyms)
	{ 
		In inputSynsets = new In(synsets);
		In inputHypernyms = new In(hypernyms);
		
		if(inputSynsets.isEmpty() || inputHypernyms.isEmpty()){	//if empty throw exception
			throw new IllegalArgumentException();
		}
		
		int count = ReadSynset(inputSynsets); //Read in the synsets.txt file and build appropriate data structures.
		Digraph graph = new Digraph(count);
		FillGraph(graph, inputHypernyms); 	//Read in the hypernyms.txt file and build a Digraph. 
		CheckDigraph(graph);
		SAPObject = new SAP(graph);
	}
	
	private int ReadSynset(In inputSynsets){
		int count = 0;
		while(!inputSynsets.isEmpty()){
			String getLine = inputSynsets.readLine(); //Get one line in particular
			String [] parts = getLine.split(",");	//split to 3 parts. ID/nouns/gloss
			int synsetID = Integer.parseInt(parts[0]); //ID is in first part
			String [] synset = parts[1].split(" ");	//nouns are in second part, each split by a space
			String words = parts[1];
			
			Bag<Integer> newIDs = null;	//create bag variable
			for(int i = 0; i < synset.length; i++){ //for each noun in the line add it along with its ID to the hashmap
				if(!lookupByString.containsKey(synset[i])){  //if the key is not present in the map
					newIDs = new Bag<Integer>(); 	//initialize the bag
				}
				else{	//bag already exists, get it.
					newIDs = lookupByString.get(synset[i]);
				}
				newIDs.add(synsetID);	//add the ID to the current bag
				lookupByString.put(synset[i], newIDs); //put it in the map
			}
			count++;
			LookupByID.put(synsetID, words);
		}
		return count;
	}
	
	private void FillGraph(Digraph graph, In inputHypernyms){
		while(!inputHypernyms.isEmpty()){
			String getLine = inputHypernyms.readLine();
			String [] parts = getLine.split(",");
			int synsetID = Integer.parseInt(parts[0]);
			for(int i = 1; i < parts.length; i++){
				graph.addEdge(synsetID, Integer.parseInt(parts[i]));
			}
		}
	}
	
	private boolean CheckDigraph(Digraph G){

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
		return true;
	}
	
	//returns all wordnet nouns
	public Iterable<String> nouns()
	{
		return lookupByString.keySet();
		
	}
	
	//is the word a noun??
	public boolean isNoun(String word)
	{
		return lookupByString.containsKey(word);
	}
	
	private boolean checkArgs(String arg1, String arg2){
		if(arg1 == null || arg2 == null){
			return false;
		}
		return true;
	}
	
	//distance between nounA and nounB
	public int distance(String nounA,String nounB)
	{
		if(!checkArgs(nounA, nounB)){
			throw new IllegalArgumentException();
		}
		Bag<Integer> nounAID = lookupByString.get(nounA);
		Bag<Integer> nounBID = lookupByString.get(nounB);
		int distance = SAPObject.length(nounAID, nounBID);
		return distance;
	}
	
	// a synset (second field of synsets.txt) that is a shortest common ancestor
	//of nounA and nounB
	public String sap(String nounA, String nounB)
	{
		if(!checkArgs(nounA, nounB)){
			throw new IllegalArgumentException();
		}
		Bag<Integer> nounAID = lookupByString.get(nounA);
		Bag<Integer> nounBID = lookupByString.get(nounB);

		int SAP = SAPObject.ancestor(nounAID, nounBID);
		String SAPNoun = LookupByID.get(SAP);
		return SAPNoun;
	}

	public static void main(String[] args) {
		//Debugging code plz ignore
		WordNet wordNet = new WordNet(args[0], args[1]); 
	/*
	 * 
		String AllIDs = wordNet.LookupByID.get(24306);
		
		StdOut.println(AllIDs);
		
		Bag<Integer> ALLStringsWithID = wordNet.lookupByString.get("bird");
		
		for(int x:ALLStringsWithID){
			StdOut.println(x);
		}
		
		String sapTest1 = wordNet.sap("a", "a");
		StdOut.print("saptest: ");
		StdOut.println(sapTest1);
		int length = wordNet.distance("a", "a");
		StdOut.print("length: ");
		StdOut.println(length);
		*/
		String sapTest1 = wordNet.sap("nail", "finger");
		StdOut.print("saptest: ");
		StdOut.println(sapTest1);
	}


}