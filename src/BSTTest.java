import java.util.Scanner;

public class BSTTest
{
    public static void main(String[] args)
    {
	BinarySearchTree t = new BinarySearchTree();

	Scanner in = new Scanner(System.in);
	while (in.hasNext())
	    {
		String command = in.next();
		if (command.toLowerCase().startsWith("a"))
		    {
			int i = in.nextInt();
			t.add(i);
			System.out.println(t.nextExcluded(i));
		    }
		else if (command.toLowerCase().startsWith("r"))
		    {
			int i = in.nextInt();
			t.remove(i);
			//System.out.println(t.nextExcluded(i));
		    }
		else
		    {
			in.nextLine();
		    }
		
		t.iterate();
		System.out.println(t.size());
	    }
	
    }
}