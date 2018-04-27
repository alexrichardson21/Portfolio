package tree.binary;

import java.util.ArrayList;

public class BinaryTreeNode {

	private String data;
	private BinaryTreeNode left, right;

	BinaryTreeNode (String s) {
		data = s;
		left = null;
		right = null;
	}

	public void add (String s, String child) {
		if (child.equals("L")) {
			left = new BinaryTreeNode(s);
		} else if (child.equals("R")) {
			right = new BinaryTreeNode(s);
		} else {
			throw new IllegalArgumentException();
		}
	}

	public BinaryTreeNode getChild (String child) {
		return (child.equals("L")) ? left : right;
	}

	public String getString () {
		return data;
	}
	
	public void printTree()
	{
		if (left != null) {
			left.printTree(); 
		}
		System.out.print(data);
		if (right != null) {
			right.printTree();
		}
	}

	public void doubleTree () {
		if (left != null) {
			left.doubleTree();
		}
		if (right != null) {
			right.doubleTree();
		}
		
		BinaryTreeNode temp = left;
		add(data, "L");
		left.left = temp;
	}

	public static boolean sameTree (BinaryTreeNode n1, BinaryTreeNode n2) {
		
		if (n1 == null && n2 == null) {
			return true;
		}
		
		if (n1 != null && n2 != null) {
			if (n1.data.equals(n2.data)
				&& sameTree(n1.left, n2.left)
				&& sameTree(n1.right, n2.right)) {
				return true;
			}
		}
		
		return false;
	}
	
	public static void main(String ar[])
    {
        BinaryTreeNode root = new BinaryTreeNode("2");
        root.add("1", "L");
        root.add("3", "R");
        
        root.printTree();
        System.out.println();
        
        root.doubleTree();
        
        root.printTree();
        
        BinaryTreeNode root2 = new BinaryTreeNode("2");
        root2.add("1", "L");
        root2.add("3", "R");
   
        root2.doubleTree();
        
        System.out.println();
        System.out.println(sameTree(root, root2));
        
        root2.doubleTree();
        
        root2.printTree();
        System.out.println();

        
        System.out.println(sameTree(root, root2));
    }
}