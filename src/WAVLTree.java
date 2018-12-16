/**
 *
 * WAVLTree
 *
 * An implementation of a WAVL Tree.
 * (Haupler, Sen & Tarajan ‘15)
 *
 */

public class WAVLTree {
	
	private WAVLNode root;
	private final WAVLNode EXT = new WAVLNode(-1, null); //general object used as external leaf
	private final int ERROR_INDICTATOR = -1;
	

  /**
   * public boolean empty()
   *
   * returns true if and only if the tree is empty
   *
   */
  public boolean empty() {
	  return root == null;
  }

 /**
   * public String search(int k)
   *
   * returns the info of an item with key k if it exists in the tree
   * otherwise, returns null
   */
  public String search(int k) {
	  return treeSearch(k, root);
  }
  
  /**
   * search for subtree as shown in class
   */
  private String treeSearch(int k, WAVLNode current) { 
	  while (current != null) {
		if (k == current.key) {
			return current.value; 
		}
		else {
			if (k < current.key) {
				current = current.getLeft(); 
			}
			else {
				current = current.getRight();
			}
		}
	  }
        return null;
  }
  
  /**
   * finds insertion point in subtree.
   * Returns insertion position if not in tree, or the node with insertion key 
   * if already in tree. returns null if tree is empty.
   * updates sizes "on the way down"
   */
  private WAVLNode treePosition(int k,WAVLNode searched,boolean updateSizes) {
	  WAVLNode prev = null;
	  while (searched != null) {
		 if(updateSizes) searched.size++; //updates sizes on the way
		prev = searched;
		if (k == searched.key) {
			return searched;
		}
		else {
			if (k < searched.key) {
				searched = searched.getLeft();
			}
			else {
				searched = searched.getRight();
			}
		}
	}
	  return prev;
  }
  
  /**
   * returns the node with the next key by value in the tree (such that the key is the minimal key that satisfies key>x.key)
   * implementation identical to one shown in class
   */
  private WAVLNode successor(WAVLNode x) {
	  if (x.getRight()!=null) return minNode(x.right);
	  
	  WAVLNode y=x.parent;
	  
	  while(y!=null && x== y.getRight()) {
		  x=y;
		  y=x.parent;
	  }
	  
	  return y;

  }
  
  /**
   * public int insert(int k, String i)
   *
   * inserts an item with key k and info i to the WAVL tree.
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were necessary.
   * returns -1 if an item with key k already exists in the tree.
   * algorithm first inserts like unbalanced binary tree, then  rebalances like algorithm shown in class. 
   */
   public int insert(int k, String i) {
	   
	   if(empty()) {
		   initializeRoot(k, i); //base case
		   return 0;
	   }
	   	   
	   //insertion
	   if (search(k)!=null) { 
		   return ERROR_INDICTATOR; //for error: key in tree
	   }
	   // by this point we know for sure that the key does not exist in the tree 
	   WAVLNode insertionNode=new WAVLNode(k,i);
	   insertionNode.rank=0;
	   insertionNode.size=1;
	   insertionNode.left=EXT;
	   insertionNode.right=EXT;
	   WAVLNode parentNode=treePosition(k, root,true);
	   if(parentNode.key>insertionNode.key) {
		   parentNode.left=insertionNode;
		   insertionNode.parent=parentNode;
	   }
	   else { //parentNode.key<insertionNode.key
		   parentNode.right=insertionNode;
		   insertionNode.parent=parentNode;
	   }
	   
	   //rebalance
	   int rebalances=0;
	   WAVLNode temp=insertionNode;
	   int caseNum=whichCase(temp);
	   while (caseNum!=0) { //tree isn't fixed
		   switch (caseNum) {
		case 1:
			temp.parent.rank++;  //promote x
			temp=temp.parent; 
			rebalances++; //one promote
			if (temp==root) { //root need not push problem upwards- fixed
				caseNum=0; 
				break; 
			}
			caseNum=whichCase(temp);
			break;
		case 2:
			if(temp.parent.left==temp) {
				rightRotate(temp.parent);
				temp.right.rank--; //demote z
			}
			else { //symmetric case
				leftRotate(temp.parent);
				temp.left.rank--; //demote z
			}
			rebalances+=2; //1 rotate, 1 demote
			caseNum=0; 
			break; 
		case 3:
			if (temp.parent.left==temp) {
				leftRotate(temp);
				rightRotate(temp.parent.parent); //left-right double rotate
				temp.rank--; //demote x
				temp.parent.right.rank--; //demote z
				temp.parent.rank++; //promote b
			}
			else {
				rightRotate(temp);
				leftRotate(temp.parent.parent); //right-left double rotate
				temp.rank--; //demote x
				temp.parent.left.rank--; //demote z
				temp.parent.rank++; //promote b
			}
			rebalances+=5; //2 rotates, 3 demotes/promotes
			caseNum=0; 
			break; 
		} //switch ends  
	   }
	   return rebalances;
   }
   
   /**
    * initilizes an empty tree by inserting first node to the root of the tree, init size,rank
    */
   private void initializeRoot(int k, String i) {
	   root=new WAVLNode(k,i);
	   root.size=1;
	   root.rank=0;
	   root.left=EXT;
	   root.right=EXT;
}

   /**
    * a methods which determines the current needed rebalancing action needed 
    * in relation to the position of node checked up the tree.
    * method uses process of elimintion to determine case under the assumption that there are no other
    * cases (told but not proven in class). 
    * @return 0 if tree is balanced, 1,2,3 according to cases shown in lecture (including symmetric cases).
    */
   private int whichCase(WAVLNode currentNode) { //0=fine, 1,2,3=case according to lecture
	   int diff1 = currentNode.parent.getRank() - currentNode.getRank(); //z
	   if(diff1!=0) return 0;
	   int diff2 = currentNode.parent.getRank() - currentNode.getSibling().getRank(); //z-y
	   if (diff2==1) return 1;
	   int diff3=currentNode.getRank()-currentNode.left.getRank();
	   if(currentNode.parent.left==currentNode && diff3==1) return 2;
	   if(currentNode.parent.right==currentNode && diff3==2) return 2;
	   return 3;
   }
   
   /*
    * old code
   private boolean case1(WAVLNode currentNode) {
	   int diff1 = currentNode.parent.rank - currentNode.rank; 
	   int diff2 = currentNode.parent.rank - currentNode.parent.getSibling().rank;
	   return diff1 == 0 && diff2 == 1;  
   }
   
   private boolean case2(WAVLNode currentNode) {
	   boolean case2A = false;
	   boolean case2B = false; 
	   int diff1 = currentNode.rank - currentNode.left.rank; 
	   int diff2 = currentNode.rank - currentNode.right.rank; 	   
	   if (!(diff1 == 1 && diff2 == 2)) { // condition for case 2A
		   
	   }
	   
	   if (!(diff2 == 2 && diff1 == 1)) { // condition for case 2B
		   
	   }
		   
		   
		   return false; 
	   diff1 = currentNode.parent.rank - currentNode.rank; 
	   diff2 = currentNode.parent.right.rank - currentNode.parent.rank; 
	   return diff1 == 0 && diff2 == 2; 
   }
   */

   
   /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   */
      public int delete(int k) { 
	   // base case: tree is empty or an item with key k was not found in the tree
	   if (empty() || search(k) == null) { 
		   return ERROR_INDICTATOR; 
	   }
	   
	   //deletion
	   WAVLNode deletionNode = treePosition(k, root,false); //we will update sizes later
	   WAVLNode rebalanceNode=deletionNode.parent; //save for later for rebalancing
	   
	   if (!isLeaf(deletionNode) && !isUnary(deletionNode)) { // if internal binary leaf 
		   WAVLNode suc = successor(deletionNode); 
		   rebalanceNode=suc.parent; //NOTE: suc is NOT the root
		   
		   if(rebalanceNode==deletionNode) {
			   if(rebalanceNode!=root) rebalanceNode=rebalanceNode.parent; //edge case if rebalancenode is suc.parent and deleted from tree
			   else rebalanceNode=suc; //fixes problem if root==deletion with suc==deletion.right
		   }
		   
		   //replace(suc, deletionNode);  TODO not use this?
		   deleteBinary(deletionNode,suc);
		   //we CANNOT know if tree is balanced immediately as there may be a (1,3) or (3,2) case (maybe also others)
		   
	   }
	   else if (isLeaf(deletionNode)) {
		   
/*		   int leafNumCase = leafDeletionCases(deletionNode); 
		   if (leafNumCase == 2) { // demote z 
			   deletionNode.parent.rank--; 
		   }*/
		   
		   deleteLeaf(deletionNode);
		   
		   if (root==null) return 0;
		   
		   int leafCases = leafDeletionCases(rebalanceNode); 
		   if (leafCases == 1) {
			   decreaseSizesUp(rebalanceNode); //fix sizes up the tree- opposite of insert updates. note rebalanceNode is deleted.parent
			   return 0; 
		   }
		   if (leafCases)
		   
/*		   if (leafNumCase == 1) { // fixed case 1 
			   return 0; 
		   }*/

	   }
	   else if (isUnary(deletionNode)) { 
//		   int unaryNumCase = unaryDeletionCases(deletionNode);  
		   
		   deleteUnary(deletionNode);
		   
		   if(root.right==EXT && root.left==EXT) return 0; //we deleted the last root, now tree definitely balanced (one node in it)
		   
/*		   if (unaryNumCase == 1 || unaryNumCase == 2) { // fixed cases 1,2
			   return 0; 
		   }*/
	   }
	   
	   decreaseSizesUp(rebalanceNode); //fix sizes up the tree- opposite of insert updates. note rebalanceNode is deleted.parent
	   
	   return 42; 
   }


//TODO replaces node with succsesor for delete- may return deleted's parent for rebalancing
   private void replace(WAVLNode suc, WAVLNode deletionNode) {
	   

   }

   private void deleteLeaf(WAVLNode deletionNode) {
	   
	   deletionNode.size=1;
	   
	   if(deletionNode==root) { //single node in tree
		   root=null;
		   return;
	   }
	   else if (deletionNode.parent.left == deletionNode) { //deletionNode not EXT or with null parent
		   deletionNode.parent.left=EXT;
		   deletionNode.parent=null;  
	   }
	   else {
		   deletionNode.parent.right=EXT;
		   deletionNode.parent=null;
	   }
	   
   }
   
   private void deleteUnary(WAVLNode deletionNode) {
	   
	   deletionNode.size=1;
	   
	   boolean parentRight,sonRight;
	   WAVLNode parentNode,sonNode;
	   sonRight = deletionNode.right != EXT ? true:false;
	   sonNode = sonRight ? deletionNode.right : deletionNode.left;
	   deletionNode.right=EXT; //no problem-we have pointer to son
	   deletionNode.left=EXT;
	   
	   if(deletionNode==root) {
		   root=sonNode;
		   sonNode.parent=null;
		   return; //if this is the case sonNode now only node in tree-otherwise tree was unbalanced during insertion
	   }
	   //not a root
	   parentRight= deletionNode.parent.right==deletionNode ? true:false;
	   parentNode= deletionNode.parent;
	   deletionNode.parent=null;
	   sonNode.parent=parentNode;
	   if(parentRight) parentNode.right=sonNode;
	   else parentNode.left=sonNode;
	   
}
   
   private void deleteBinary(WAVLNode deletionNode, WAVLNode succsesor) {
	   
	   
	   if(isLeaf(succsesor)) deleteLeaf(succsesor);
	   else deleteUnary(succsesor); 
	   //FIRST delete succsesor then switch pointers with deleted- now pointers are (null,EXT,EXT)
	   
	   if(deletionNode==root) root=succsesor; //update root
	   
	   succsesor.size=deletionNode.size;
	   deletionNode.size=1; //fix sizes
	   
	   succsesor.rank=deletionNode.rank; //fix ranks
	   
	   WAVLNode rightNode=deletionNode.right;
	   WAVLNode leftNode=deletionNode.left;
	   WAVLNode parentNode=deletionNode.parent;
	   
	   
	   //give successor all pointers
	   succsesor.right=rightNode;
	   rightNode.parent=succsesor; //not EXT/null
	   succsesor.left=leftNode;
	   leftNode.parent=succsesor; //not EXT/null
	   succsesor.parent=parentNode;
	   if(parentNode!=null) {
	   if(deletionNode==parentNode.right) parentNode.right=succsesor;
	   else parentNode.left=succsesor;
	   }
	   
	   //null deletion pointers
	   deletionNode.right=EXT;
	   deletionNode.left=EXT;
	   deletionNode.parent=null;
	   
   }
   
   private boolean isLeaf(WAVLNode node) { 
	   return node.getRight() == null && node.getLeft() == null; 
   }
   
   private boolean isUnary(WAVLNode node) {
	   return (node.getLeft() != null && node.getRight() == null) || (node.getLeft() == null && node.getRight() != null); 

   }
   
   private void decreaseSizesUp(WAVLNode node) {
	while (node!=null) {
		node.size--;
		node=node.parent;
	}
   }
   
   private int leafDeletionCases(WAVLNode node) {
	   int diff1 = node.rank - node.right.rank; 
	   int diff2 = node.rank - node.left.rank; 
	   if ((diff1 == 1 && diff2 == 2) || (diff1 == 2 && diff2 == 1)) {
		   return 1; 
	   }
	   if (diff1 == 1 && diff2 == 1) {
		   return 2; 
	   }
	   return 3; 
   }
   
   private int unaryDeletionCases(WAVLNode node) {
	   int diff1 = node.rank - node.right.rank; 
	   int diff2 = node.rank - node.left.rank; 
	   if ((diff1 == 1 && diff2 == 2) || (diff1 == 2 && diff2 == 1)) {
		   return 1; 
	   }
	   if (diff1 == 2 && diff2 == 2) {
		   return 2; 
	   }
	   return 3; 
   }
   
   private int whichCaseDelete(WAVLNode node) {
	   int diff1 = node.rank - node.right.rank; 
	   int diff2 = node.rank - node.left.rank;  
	   if ((diff1 == 2 && diff2 == 3) || (diff1 == 3 && diff2 == 2)) {
		   return 1; 
	   }
	   if (diff1 == 1) {
		   WAVLNode y = node.right; 
		   int diff3 = y.rank - y.right.rank; 
		   int diff4 = y.rank - y.left.rank; 
		   if (diff3 == 1) {
			   return 3; 
		   }
		   if (diff4 == 1) {
			   return 4; 
		   }
		   return 2;  
	   }
	   else { // diff2 == 1
		   WAVLNode y = node.left; 
		   int diff3 = y.rank - y.left.rank; 
		   int diff4 = y.rank - y.right.rank; 
		   if (diff3 == 1) {
			   return 3; 
		   }
		   if (diff4 == 1) {
			   return 4; 
		   }
		   return 2; 
	   } 
   }
	

   /**
    * rotates tree right as shown in class
    * makes y the axis, x=y.right , and B=x.left .
    * updates sizes.
    * @param x rotation axis, where y=x.left and B=y.right
    * for reference see BST slide 31
    * rank update done separately
    */
   private void rightRotate(WAVLNode x) {
	   WAVLNode y=x.left;
	   WAVLNode B=y.right;
	   
	   if(x!=root && x.parent.left==x) x.parent.left=y; //fix upper tree connection
	   else if(x!=root && x.parent.right==x) x.parent.right=y;
	   
	   y.parent=x.parent;
	   y.right=x;
	   x.parent=y;
	   x.left=B;
	   B.parent=x;
	   
	   if(root.parent!=null) root=root.parent; //fix root pointer
	   
	   x.size=x.left.size+x.right.size+1; //only x,y sizes changed- so we can use unchanged sizes
	   y.size=y.left.size+y.right.size+1; //y relies on x size- important to update it first
   }
   
   /**
    * rotates tree left
    * makes x the axis, x=y.right , and B=x.left .
    * updates sizes.
    * @param y rotation axis, where y=x.left and B=y.right
    * for reference see BST slide 31
    * rank update done separately
    */
   private void leftRotate(WAVLNode y) {
	   WAVLNode x=y.right;
	   WAVLNode B=x.left;
	   
	   if(y!=root && y.parent.left==y) y.parent.left=x; //fix upper tree connection
	   else if(y!=root && y.parent.right==y) y.parent.right=x;
	   
	   x.parent=y.parent;
	   x.left=y;
	   y.parent=x;
	   y.right=B;
	   B.parent=y;
	   
	   if(root.parent!=null) root=root.parent; //fix root pointer
	   
	   y.size=y.left.size+y.right.size+1; //only x,y sizes changed- so we can use unchanged sizes
	   x.size=x.left.size+x.right.size+1; //x relies on y size- important to update it first
   }
   
   /**
    * public String min()
    *
    * Returns the info of the item with the smallest key in the tree,
    * or null if the tree is empty
    */
   public String min() { // Bottom - Left 
	   return minNode(root).getValue(); 
   }
   
   private WAVLNode minNode(WAVLNode root) {
	   if (root == null) {
		   return null; 
	   }
	   while (root.getLeft() != null) {
		   root = root.getLeft(); 
	   }
	   return root;  
   } 

   /**
    * public String max()
    *
    * Returns the info of the item with the largest key in the tree,
    * or null if the tree is empty
    */
   public String max() { // Bottom - Right  
	   return maxNode(root).getValue(); 
   }
   
   private WAVLNode maxNode(WAVLNode root) { 
	   if (root == null) {
		   return null; 
	   }
	   while (root.getRight() != null) {
		   root = root.getRight(); 
	   }
	   return root;  
   }

   /**
   * public int[] keysToArray()
   *
   * Returns a sorted array which contains all keys in the tree,
   * or an empty array if the tree is empty.
   * implemented using  inorder recursion.
   */
   public int[] keysToArray() {
	   int[] keys = new int[size()];
	   WAVLNode temp = root; 
	   keysInOrder(temp, keys, 0); 
	   return keys; 
   }
   
 //traverses tree in order using recursion and keeps current index to insert to
   private int keysInOrder(WAVLNode temp, int[] arr, int i) { 
	   if (temp == null) {
			return i;
	   }
	   i = keysInOrder(temp.getLeft(), arr, i);
	   arr[i++] = temp.getKey(); 
	   i = keysInOrder(temp.getRight(), arr, i);
	   return i; 
   }

   /**
   * public String[] infoToArray()
   *
   * Returns an array which contains all info in the tree,
   * sorted by their respective keys,
   * or an empty array if the tree is empty.
   * implemented using  inorder recursion.
   */
   public String[] infoToArray() {
	   String[] info = new String[size()]; 
	   WAVLNode temp = root; 
	   infoInOrder(temp, info, 0); 
	   return info;  
   }
    
 //traverses tree in order using recursion and keeps current index to insert to
   private int infoInOrder(WAVLNode temp, String[] arr, int i) {
	if (temp == null) {
		return i;
	}
	i = infoInOrder(temp.getLeft(), arr, i);
	arr[i++] = temp.getValue();
	i = infoInOrder(temp.getRight(), arr, i);
	return i;
	}

   /**
    * public int size()
    *
    * Returns the number of nodes in the tree.
    *
    */
   public int size() {
	   return root != null ? root.size : 0; 
   }
   
     /**
    * public WAVLNode getRoot()
    *
    * Returns the root WAVL node, or null if the tree is empty
    *
    */
   public WAVLNode getRoot() {
	   return root; 
   }

   /**
    * public int select(int i)
    *
    * Returns the value of the i'th smallest key (return -1 if tree is empty)
    * Example 1: select(1) returns the value of the node with minimal key 
        * Example 2: select(size()) returns the value of the node with maximal key 
        * Example 3: select(2) returns the value 2nd smallest minimal node, i.e the value of the node minimal node's successor  
    */   
   public String select(int i) { //    * TODO change? may need to implement in O(log(n))
	   return infoToArray()[i]; 
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
  		 return this != null ? key : ERROR_INDICTATOR; 
  	 }
  	 
  	 /*
  	  * returns value or null if external leaf
  	  */
  	 public String getValue() {
  		 return rank == -1 ? null : value;  
  	 }
  	 
  	 public WAVLNode getLeft() {
  		 if(this.left==EXT) return null;
  		 return left; 
  	 }
  	 
  	 public WAVLNode getParent( ) {
  		 return parent; 
  	 }
  	 
  	 public WAVLNode getRight() {
  		 if(this.right==EXT) return null;
  		 return right; 
  	 }
  	 
  	 public boolean isInnerNode() {
  		 return rank != -1 ? true : false; 
  	 }

       public int getSubtreeSize() {
      	 return size; 
       }
       
       public int getRank() { //to access EXT rank
		if(this==EXT) return -1;
		return rank;
	}
       
       public WAVLNode getSibling() {
		if (this.parent.right==this) { //checks if right child and returns left and vice versa
			return parent.left;
		}
		return parent.right;
	}

       //TODO for now i'll implement double sided-we can easily change later.
       /*
       public void setRight(WAVLNode rightnode) {
    	   this.right=rightnode;
    	   if (rightnode!=EXT) rightnode.parent=this; //only one EXT node- problematic with parent 
       }
       public void setParent(WAVLNode parentnode,boolean isRightNode) {
    	   this.parent=parentnode; 
    	   if(this!=root) {
    		   if(isRightNode) parentnode.right=this;
    		   else parentnode.left=this;
    	   }
       }
       public void setLeft(WAVLNode leftnode) {
    	   this.left=leftnode;
    	   if (leftnode!=EXT) leftnode.parent=this; //only one EXT node- problematic with parent 
       }
       */
   }
}
