
public class BST {
	
	public static void main (String [] args) {

		int[] test = new int[1000000];
		IntSet301 tree = new BinarySearchTree();
	
		for (int i = 0; i <test.length; i++) {
			double random = Math.random();	
			double random1 = random*36976947;
			int randomF = (int) random1;
			
			test[i] = randomF;
		}
		
		System.out.println("done");
	
		for (int i = 0; i<test.length; i++) {
			tree.add(test[i]);
		}
		
		System.out.println("done");
		System.out.println(tree.size());
		
		for (int i = 0; i<test.length; i++) {
			tree.nextExcluded(test[i]);
			tree.remove(test[i]);
		}
		
		
		
		System.out.println("done");
		
	}
}
