/**
 * A binary search tree.
 *
 * @author Jim Glenn
 * @version 0.2 2016-09-13 added inorder iterator
 * @version 0.1 2014-10-08
 * @author Harith Khawaja
 * splay properties added - add, remove, search, splay, contains, nextExcluded, rotates
 */

public class BinarySearchTree implements IntSet301
{
    private Node root;
    private int size;

    /**
     * Counts the number of tree in this tree.
     *
     * @return the number if items in this tree
     */
    
    public int size() {
    	return size;
    }
      
    public boolean contains (int r) {
    	Node curr = root;
    	Node parCurr = null;
    		
    	// traverse tree to insertion location or node containing r
    	while (curr != null && curr.compareTo(r) != 0)
    	     {
    		parCurr = curr;
    		if (curr.compareTo(r) > 0)
    	 	    {
    	 		curr = curr.left;
    	 	    }
    	 	else
    	 	    {
    	 		curr = curr.right;
    	 	    }
    	     }

    	 if (curr == null) {
    		 splay (parCurr);
    		 //System.out.println("false");
    		 return false;
    	 }
    	 else { 
    		 splay (curr);
    		 root.iterate();
    		 //System.out.println("true");
    		 return true;
    	 }
    }
    /**
     * Adds the given value to this tree if it is not already present.
     *
     * @param r a value
     */
    
    public void add(int r)
    {
    	
    if (!contains(r)) {

    	//we are definitely adding
    	size ++;
    	
    	// start at root
    	Node curr = root;

    	// keep track of parent of current and last direction traveled
    	Node parCurr = null;
	
    	// traverse tree to insertion location or node containing r
    	while (curr != null)
    		{
    		parCurr = curr;
    		if (curr.compareTo(r) > 0)
    			{
    			curr = curr.left;
    			}
    		else
	 	    	{
    			curr = curr.right;
	 	    	}
    		}

    	Node toBeSplayed = parCurr;
    	boolean easyAdd = false;
    	
    	if (parCurr != null) {
    		
    		//if parCurr != null, check for extension of interval
    		if (parCurr.compareTo(r) < 0) {
    			
    			if (parCurr.end +1 == r) {
    				parCurr.end = parCurr.end + 1;
    				easyAdd = true;
    			}
    			Node suc = successor (parCurr);
    			
    			//if successor != null, then check for merging
    			if (suc != null) {
    				if (suc.start -1 == r) {
    					suc.start = suc.start -1;
    					easyAdd = true;
    				}
    				
    				if (suc.start == parCurr.end) {
    					int newStart = parCurr.start;
    					int newEnd = suc.end;
    					delete (parCurr);
    					toBeSplayed = null;
    					delete (suc);
    					add (newStart, newEnd);
    				}
    			}

    			if (easyAdd == false) {
    				Node newNode = new Node (r, r, parCurr);
    				parCurr.right = newNode;
    				toBeSplayed = newNode;
    			}
    		}
    		
    		else {
    			
    			if (parCurr.start -1 == r) {
    				parCurr.start = parCurr.start - 1;
    				easyAdd = true;
    			}
    			
    			Node pre = predecessor (parCurr);
    			
    			//predecessor can only be an ancestor of parCurr, because left subtree == null
    			if (pre != null) {
    				
    				if (pre.end + 1 == r) {
    					pre.end = pre.end + 1;
    					easyAdd = true;
    				}
    				
    				if (pre.end == parCurr.start) {
    					int newStart = pre.start;
    					int newEnd = parCurr.end;
    					delete (parCurr);
    					toBeSplayed = null;
    					delete (pre);
    					add (newStart, newEnd);
    				}
    			}
    			
    			if (easyAdd == false) {
    				Node newNode = new Node (r,r, parCurr);
    				parCurr.left = newNode;
    				toBeSplayed = newNode;
    			}
    		}
    	}
    	
    	else {

    		// new Node added must be root
    		root = new Node (r,r,parCurr);
    	}
	 	
		// yes, we added
	 	splay(toBeSplayed);
	 	
    }
    else {
    	//node already existed so do nothing
    }
    }

    public void add (int start, int end) {
    	
    	// start at root
    	Node curr = root;

    	// keep track of parent of current and last direction traveled
    	Node parCurr = null;
	
    	// traverse tree to insertion location or node containing r
    	while (curr != null)
    		{
    		parCurr = curr;
    		if (curr.compareTo(start) > 0)
    			{
    			curr = curr.left;
    			}
    		else
	 	    	{
    			curr = curr.right;
	 	    	}
    		}
    	
    	Node newNode = new Node (start, end, parCurr);
    	
    	if (parCurr == null)
    		root = newNode;
    	else if (parCurr.compareTo(start) > 0)
    		parCurr.left = newNode;
    	else 
    		parCurr.right = newNode;
    	
    	splay(newNode);
    }
    
    /**
     * Removes r from this tree.  There is no effect if r is not in this tree.
     *
     * @param r the value to remove
     */
    public void remove(int r)
    {
    if (contains(r)) {
    	
    	//we are definitely deleting
    	size--;
    	
    	// start at root
    	Node curr = root;

    	// traverse tree to insertion location or node containing r
    	while (curr.compareTo(r) != 0)
	    	{
    		if (curr.compareTo(r) > 0)
		    	{
    			curr = curr.left;
		    	}
    		else
    			{
    			curr = curr.right;
    			}
	    	}
    	
    	//only one integer in this interval
    	if (curr.start == curr.end) {
    		delete(curr);
    	}
    	
    	//integer to delete lies at the start of the interval
    	else if (curr.start == r) {
    		curr.start = curr.start + 1;
    		//splay (curr);
    	}
    	
    	//integer to delete lies at the end of this interval
    	else if (curr.end == r) {
    		curr.end = curr.end - 1;
    		System.out.println("end decreased");
    		//splay (curr);
    	}
    	
    	//integer to delete lies in the middle of this interval
    	else {
    		int end = curr.end;
    		curr.end = r-1;
    		splay (curr);
    		add (r+1,end);
    	}
    }
    
    else {
    	//not found so do nothing
    }
    
    root.iterate();
    }

    /**
     * Deletes the given node from this tree.
     *
     * @param curr a node in this tree
     */
    private void delete(Node curr)
    {
    	
	if (curr.left == null && curr.right == null)
	    {
		Node parent = curr.parent;
		if (curr.isLeftChild())
		    {
			parent.left = null;
			splay(parent);
		    }
		else if (curr.isRightChild())
		    {
			parent.right = null;
			splay(parent);
		    }
		else
		    {
			// deleting the root
			root = null;
			//nothing really in the tree to splay
		    }
	    }
	else if (curr.left == null)
	    {
		// node to delete only has right child
		Node parent = curr.parent;

		if (curr.isLeftChild())
		    {
			parent.left = curr.right;
			curr.right.parent = parent;
			splay(curr.right);
		    }
		else if (curr.isRightChild())
		    {
			parent.right = curr.right;
			curr.right.parent = parent;
			splay(curr.right);
		    }
		else
		    {
			root = curr.right;
			root.parent = null;
			splay(root);
		    }
	    }
	else if (curr.right == null)
	    {
		// node to delete only has left child
		Node parent = curr.parent;

		if (curr.isLeftChild())
		    {
			parent.left = curr.left;
			curr.left.parent = parent;
			splay(curr.left);
		    }
		else if (curr.isRightChild())
		    {
			parent.right = curr.left;
			curr.left.parent = parent;
			splay(curr.left);
		    }
		else
		    {
			root = curr.left;
			root.parent = null;
			splay(root);
		    }
	    }
	else
	      {
		  // node to delete has two children

		  // find inorder successor (leftmost in right subtree)
		  Node replacement = curr.right;
		  while (replacement.left != null)
		      {
			  replacement = replacement.left;
		      }

		  Node replacementChild = replacement.right;
		  Node replacementParent = replacement.parent;

		  // stitch up
		  if (curr.isLeftChild())
		      {
			  curr.parent.left = replacement;
		      }
		  else if (curr.isRightChild())
		      {
			  curr.parent.right = replacement;
		      }
		  else
		      {
			  root = replacement;
		      }
		  
		  Node whatToSplay = replacement;
		  
		  if (curr != replacementParent) {
		  
			  whatToSplay = replacementParent;
		      replacement.parent.left = replacementChild;
		      if (replacementChild != null)
			  {
			      replacementChild.parent = replacement.parent;
			      whatToSplay = replacementChild;
			  }
		      
		      replacement.right = curr.right;
		      curr.right.parent = replacement;

		  }
		  
		  
		      replacement.left = curr.left;
		      curr.left.parent = replacement;
		      
		      replacement.parent = curr.parent;
		      
		      
		      splay(whatToSplay);
	      }
    }
    
    public int nextExcluded (int n) {
    	if (contains(n)) {
    		
    		//traverse to the node which contains n
    		Node curr = root;
        	
        	// traverse tree to insertion location or node containing r
        	while (curr.compareTo(n) != 0)
        	     {
        		if (curr.compareTo(n) > 0)
        	 	    {
        	 		curr = curr.left;
        	 	    }
        	 	else
        	 	    {
        	 		curr = curr.right;
        	 	    }
        	     }
        	
        	return curr.end + 1;
    	}
    	
    	else {
    		return n;
    	}
    }
    
    public void splay (Node z) {
    	
    	while (z != null && z != root) {
    		//do nothing because can't splay a null node or z already root
    		
    		if (z.parent == root) { //Zig steps
    			if (z.isLeftChild()) {
    				rightRotate (z.parent);
    				root = z;
    			}
    			else {
    				leftRotate (z.parent);
    				root = z;
    			}
    		}
    		
    		else {
    			if (z.isLeftChild()) { 
    				if (z.parent.isLeftChild()) { //Left Zig Zig here
    					rightRotate (z.parent.parent);
    					//z = z.parent;
    					rightRotate (z.parent);
    					//z = z.parent;
    				}
    				else { //Left Zig Zag here
    					rightRotate (z.parent);
    					//z = z.parent;
    					leftRotate (z.parent);
    					//z = z.parent;
    				}
    			}
    			else { //z is a right child
    				if (z.parent.isRightChild()) { //Right Zig Zig here
    					leftRotate (z.parent.parent);
    					//z = z.parent;
    					leftRotate (z.parent);
    					//z = z.parent;
    				}
    				else {
    					leftRotate (z.parent);
    					//z = z.parent;
    					rightRotate (z.parent);
    					//z = z.parent;
    				}
    			}
    			
    			if (!z.isLeftChild() & !z.isRightChild())
    				root = z;
    		}
    		
    	}
    }
    
    public void leftRotate (Node z) {
    	if (z == null || z.right == null)
    		return; //nothing to be done if null is the right child
    	else {
    		Node child = z.right;
    		z.right = child.left;
    		
    		if (z.right != null) {
    			z.right.parent = z;
    		}
    		
    		child.left = z;
    		child.parent = z.parent;
    		
    		if (z.parent == null) {
    			root = child;
    		}
    		else {
    			if (z.isLeftChild()) {
    				z.parent.left = child;
    			}
    			else {
    				z.parent.right = child;
    			}
    		}
    		
    		z.parent = child;
    		
    		//z = child;
    	}
    }
    
    public void rightRotate (Node z) {
    	if (z == null || z.left == null){
    		//do nothing
    	}
    	else {
    		Node child = z.left;
    		z.left = child.right;
    		
    		if (z.left != null) {
    			z.left.parent = z;
    		}
    		
    		child.right = z;
    		child.parent = z.parent;
    		
    		if (z.parent == null) {
    			root = child;
    		}
    		else {
    			if (z.isLeftChild()) {
    				z.parent.left = child;
    			}
    			else {
    				z.parent.right = child;
    			}
    		}
    		
    		z.parent = child;
    		
    		//z = child;
    	}
    }
    
    /**
     * Returns the inorder successor of the given node.
     *
     * @param curr a node in this tree that is not the rightmost
     * @return the inorder successor of that node
     */
    public Node successor(Node curr)
    {
	if (curr == null)
	    return null;
	else {
	    if (curr.right != null) {
	    	Node suc = curr.right;
	    	while (suc.left != null) {
	    	suc = suc.left;
	    	}
	    	return suc;
	    }
	    else {
	    	Node parCurr = curr.parent;
	    	while ((parCurr != null) && parCurr.left != curr) {
	    		curr = parCurr;
	    		parCurr = parCurr.parent;
	    	}

	    	if (parCurr == null)
	    		return null;
	    	else 
	    		return parCurr;
	    }
	}
    }

    public Node predecessor (Node curr) {
    	if (curr == null)
    		return null;
    	else {
    		if (curr.left != null) {
    			Node suc = curr.left;
    			while (suc.right != null) {
    				suc = suc.right;
    			}
    			return suc;
    		}
    		else {
    			Node parCurr = curr.parent;
    			while ((parCurr != null) && parCurr.right != curr) {
    				curr = parCurr;
    				parCurr = parCurr.parent;
    			}
    			
    			if (parCurr == null)
    				return null;
    			else
    				return parCurr;
    		}
    	}
    }
     /**
     * Returns a printable representation of this tree.
     *
     * @return a printable representation of this tree
     */
    public void iterate()
    {
    	if (root == null)
    		return;
    	else 
    		root.iterate();
    }
}
    

    
    
