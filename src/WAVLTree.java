/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree.
 * (Haupler, Sen & Tarajan �15)
 *
 */

public class WAVLTree {
	
	private WAVLNode root;
	private final WAVLNode EXT=new WAVLNode(-1,null); //general object used as external leaf

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
	  return root==null;
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k)
  {
	  return treeSearch(k, root);
  }
  
  /**
   * search for subtree as shown in class
   */
  private String treeSearch(int k,WAVLNode current) { 
	  while (current!=null) {
		if (k==current.key) return current.value;
		else {
			if(k<current.key) current=current.left;
			else current=current.right;
		}
	}
        return null;
  }
  
  /**
   * finds insertion point in subtree.
   * Returns insertion position if not in tree, or the node with insertion key 
   * if already in tree. returns null if tree is empty.
   */
  private WAVLNode treePosition(int k,WAVLNode inserted) {
	  WAVLNode prev=null;
	  while (inserted!=null) {
		prev=inserted;
		if(k==inserted.key) return inserted;
		else {
			if(k<inserted.key) inserted=inserted.left;
			else inserted=inserted.right;
		}
	}
	  return prev;
  }
  
  
  //TODO successor (22)?
  
  
  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   */
   public int insert(int k, String i) {
          return 42;    // to be replaced by student code
   }

   /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
   public int delete(int k)
   {
           return 42;   // to be replaced by student code
   }

   /**
    * rotates tree right as shown in class
    * makes y the axis, x=y.left , and B=x.left .
    * updates sizes.
    * @param x rotation axis, where y=x.left and B=y.right
    * for reference see BST slide 31
    * TODO ranks?
    */
   private void rightRotate(WAVLNode x) {
	   WAVLNode y=x.left;
	   WAVLNode B=y.right;
	   
	   y.parent=x.parent;
	   y.right=x;
	   x.parent=y;
	   x.left=B;
	   B.parent=x;
	   
	   x.size=x.left.size+x.right.size+1; //only x,y sizes changed- so we can use unchanged sizes
	   y.size=y.left.size+y.right.size+1; //y relies on x size- important to update it first
   }
   
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min()
   {
           return "42"; // to be replaced by student code
   }

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max()
   {
           return "42"; // to be replaced by student code
   }

   /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   */
   public int[] keysToArray()
   {
        int[] arr = new int[42]; // to be replaced by student code
        return arr;              // to be replaced by student code
   }

   /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   */
   public String[] infoToArray()
   {
        String[] arr = new String[42]; // to be replaced by student code
        return arr;                    // to be replaced by student code
   }

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    */
   public int size()
   {
           return root.size; // to be replaced by student code
   }
   
     /**
    * public WAVLNode getRoot()
    *
    * Returns the root WAVL node, or null if the tree is empty
    *
    */
   public WAVLNode getRoot()
   {
           return root;
   }

   /**
    * public int select(int i)
    *
    * Returns the value of the i'th smallest key (return -1 if tree is empty)
    * Example 1: select(1) returns the value of the node with minimal key 
        * Example 2: select(size()) returns the value of the node with maximal key 
        * Example 3: select(2) returns the value 2nd smallest minimal node, i.e the value of the node minimal node's successor  
    *
    */   
   public String select(int i)
   {
           return null; 
   }
   
   /**
    * public class WAVLNode
   */
   public class WAVLNode {
  	 
  	 private int rank; 
  	 private int key; 
  	 private int size; 
  	 private String value; 
  	 private WAVLNode left; 
  	 private WAVLNode parent; 
  	 private WAVLNode right; 
  	 
  	 public WAVLNode() {
  		 
  	 }
  	
  	 public WAVLNode(int key,String value) { //added constructor with arguments
  		 this.key=key;
  		 this.value=value;
  	 }
  	 
  	 public int getKey() {
  		 return key; 
  	 }
  	 
  	 /*
  	  * returns value or null if external leaf
  	  */
  	 public String getValue() {
  		 return rank == -1 ? null : value;  
  	 }
  	 
  	 public WAVLNode getLeft() {
  		 return left; 
  	 }
  	 
  	 public WAVLNode getParent( ) {
  		 return parent; 
  	 }
  	 
  	 public WAVLNode getRight() {
  		 return right; 
  	 }
  	 
  	 public boolean isInnerNode() {
  		 return rank != -1 ? true : false; 
  	 }

       public int getSubtreeSize() {
      	 return size; 
       }
       
    }
}