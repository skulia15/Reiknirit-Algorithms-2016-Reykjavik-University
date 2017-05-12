package FixLeft;

import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FixLeft
{
	public static void main(String[] args)
	{
		Stack<String> opsStack = new Stack<String>();
		Stack<String> valsStack = new Stack<String>();
		while (!StdIn.isEmpty())
		{ // Read token, push if operator.
			//StdOut.println("test");
			String input = StdIn.readString();
			if (input.equals("(")) ;
			else if (input.equals("+"))
				opsStack.push(input);
			else if (input.equals("-"))
				opsStack.push(input);
			else if (input.equals("*"))	
				opsStack.push(input);
			else if (input.equals("/")) 
				opsStack.push(input);
			else if (input.equals("sqrt")) 
				opsStack.push(input);
			else if (input.equals(")"))
			{ // Pop, evaluate, and push result if token is ")".
				String poppedOp = opsStack.pop();
				String poppedVal2 = valsStack.pop();
				String poppedVal1 = valsStack.pop();
				String result = "( " + poppedVal1 + " " + poppedOp + " " + poppedVal2 + " )";
				
				valsStack.push(result);
			} // Token not operator or paren: push double value.
		else valsStack.push(input);
		}
		StdOut.println(valsStack.pop());
	}
}