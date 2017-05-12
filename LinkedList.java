import edu.princeton.cs.algs4.StdOut;

public class LinkedList {
	
	private Node front;
	private int N;
	
	private class Node
	{
		String key;
		Node next;
	}

	/**
	 * Constructor
	 */
	public LinkedList()
	{
		N = 0;
		front = null;
	}
	
	/**
	 * Pushes the item to the back of the list.
	 */
	public void add(String str)
	{
		Node node = new Node();
		node.key = str;
		node.next = front;
		front = node;
	}
	
	public void print()
	{
		Node iter = front;
		while( iter != null )
		{
			StdOut.print(iter.key);
			iter = iter.next;
			if( iter != null )
			{
				StdOut.print(" ");
			}
		}
		StdOut.println();
	}
	
	public void remove(String str)
	{
		front = remove_helper(str, front);
	}
	
	private Node remove_helper(String str, Node node)
	{
		if(node == null){
			return null;
		}
		else if(node.key.equals(str)){
			node.next = remove_helper(str, node.next);
			return node.next;
		}
		else{
			node.next = remove_helper(str, node.next);
			return node;
		}
	}
}