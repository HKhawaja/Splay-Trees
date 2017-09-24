public class Node {
	public Node parent;
	public Node left;
	public Node right;

	public int start;
	public int end;

	public Node(int start, int end, Node p)
	    {
		this.start = start;
		this.end = end;
		parent = p;
	    }

	public boolean isLeftChild()
	{
	    return (parent != null && parent.left == this);
	}

	public boolean isRightChild()
	{
	    return (parent != null && parent.right == this);
	}

	public int compareTo (int n) {
		if (start > n)
			return 1;
		if (n > end)
			return -1;
		return 0;	
	}

	public void iterate() {
		if (left!= null)
			left.iterate();
		if (this.parent == null)
			System.out.print("*");
		System.out.print("<" + this.start + "-" + this.end + "> ");
		if (right!= null)
			right.iterate();
	}
}
