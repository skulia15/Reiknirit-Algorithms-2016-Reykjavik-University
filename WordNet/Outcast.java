package S4;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
	private WordNet wordNet;
	public Outcast(WordNet wordNet)
	{
		this.wordNet = wordNet;
	}
	
	public String outcast(String [] nouns){
		int Di = 0;
		String DiString = "";
		for(int i = 0; i < nouns.length; i++){
			int currDist = 0;
			for(int j = 0; j < nouns.length; j++){
				if(nouns[j] != nouns[i]){
					currDist = currDist + wordNet.distance(nouns[i], nouns[j]);
				}
			}
			if(currDist > Di){
				Di = currDist;
				DiString = nouns[i];
			}
		}
		
		return DiString;
	}
	public static void main(String [] args){
		WordNet wordnet = new WordNet (args[0], args[1]);
		Outcast outcast = new Outcast (wordnet);
		for (int t = 2; t < args.length ; t++){
			@SuppressWarnings("deprecation")
			String [] nouns = In.readStrings(args[t]) ;
			StdOut.println(args[t] + ": " + outcast.outcast (nouns));
		}

	}
}
