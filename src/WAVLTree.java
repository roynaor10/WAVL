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
   * updates sizes "on the way down" (if update sizes true)
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
	   insertionNode.right=EXT; //Initialize all of new node's fields
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
			if(temp.parent.left==temp) { //check if in normal or symmetric case
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

   /**
   * public int delete(int k)
   *
   * deletes an item with key k from the binary tree, if it is there;
   * the tree must remain valid (keep its invariants).
   * returns the number of rebalancing operations, or 0 if no rebalancing operations were needed.
   * returns -1 if an item with key k was not found in the tree.
   * algorithm first deletes like unbalanced binary tree, then rebalances like algorithm shown in class. 
   */
      public int delete(int k) { 
    	  
	   // base case: tree is empty or an item with key k was not found in the tree
	   if (empty() || search(k) == null) { 
		   return ERROR_INDICTATOR; 
	   }
	   
	   //deletion
	   WAVLNode deletionNode = treePosition(k, root, false); //we will update sizes later
	   
	   WAVLNode rebalanceNode = deletionNode.parent; //save for later for rebalancing
	   
	   WAVLNode resizeNode=rebalanceNode; //we may mess with rebalanceNode later, save this for size fixing
	   
	   boolean checkLeaf=isLeaf(deletionNode); //pre-saved check if we are deleting leaf or unary
	   boolean checkUnary=isUnary(deletionNode);
	   //if not both- binary:we will find succsesor and update correct checker
	   
	   
	   if (!checkLeaf && !checkUnary) { // if internal binary leaf 
		   WAVLNode suc = successor(deletionNode); 
		   
		   rebalanceNode = suc.parent; //find new rebalancenode (NOTE: suc is NOT the root)
		   resizeNode=rebalanceNode; //update this too
		   
		   checkLeaf=isLeaf(suc);
		   checkUnary=isUnary(suc); //update checker to what we are actually deleting 
		   
		   if(rebalanceNode == deletionNode) {
				   rebalanceNode = suc; //edge case if rebalancenode is suc.parent and deleted from tree (then we want to rebalance from the successor)
				   resizeNode=rebalanceNode;
		   }
		   
		   deleteBinary(deletionNode,suc); 
		   
		   //check cases later using check variables...
		   
		   //we CANNOT know if tree is balanced immediately as there may be a (1,3) or (3,2) case (maybe also others)
	   }
	   else if (checkLeaf) {
		   deleteLeaf(deletionNode);
		   
		   if (root == null) { //there was 1 node in tree- now empty
			   return 0;
		   }
	   }
	   else if (checkUnary) { 
		   deleteUnary(deletionNode);
		   
		   if(root.right == EXT && root.left == EXT) { //was root with one child- now only root
			   root.size = 1;  
			   return 0; //we deleted the last root, now tree definitely balanced (one node in it)
		   }
	   }
	   
		 decreaseSizesUp(resizeNode); //fix sizes up the tree- opposite of insert updates. note rebalanceNode is deletednode.parent
		 
	   
	   //"pre rebalance" -special first cases in accordence with WAVL presentation slides 52,53
	   
	   
	   int preRebalances=0; //don't miss a rebalance count
	   
	   if (checkLeaf) { //we actually deleted a leaf- we check if special first rebalance applies
		   int leafCases = leafDeletionCases(rebalanceNode); 
		   if (leafCases == 1) { //case 1-tree balanced
			   return 0; 
		   }
		   if (leafCases == 2) { //case 2- 2,2 node as leaf (demote)
			   rebalanceNode.rank--; //demote z
			   rebalanceNode = rebalanceNode.parent; //go up
			  preRebalances++; //1 demote
		   }
	   }
	   
	   if (checkUnary) {
		   int unaryNumCases = unaryDeletionCases(rebalanceNode); 
		   if (unaryNumCases == 1 || unaryNumCases == 2) { //  cases 1,2- tree balanced
			   return 0; 
		   }
	}
	   //in both leaf, unary we only take care of case 3 later

		 
		 //rebalance
		 int rebalances = 0+preRebalances; //initial value 1/0- we may have already demoted a leaf
		 
		 if(rebalanceNode==null) return rebalances; //if problem already moved up after reaching root- then fixed
		 
		 int caseNum = whichCaseDelete(rebalanceNode);
		 
		 while (caseNum != 0) { //tree isn't fixed
			   switch (caseNum) {
			   
			case 1: //demote
				rebalanceNode.rank--; // demote z 
				rebalanceNode = rebalanceNode.parent; 
				rebalances++; //one demote
				if (rebalanceNode==null) { //root need not push problem upwards- fixed
					caseNum = 0; 
					break; 
				}
				caseNum = whichCaseDelete(rebalanceNode);
				break;
				
			case 2: //double demote
				int diff1 = rebalanceNode.getRank() - rebalanceNode.right.getRank(); 
				int diff2 = rebalanceNode.getRank() - rebalanceNode.left.getRank();
				if (diff1 == 1) { //check normal of symmetric cases
					rebalanceNode.right.rank--; //demote y
				}
				if (diff2 == 1) {
					rebalanceNode.left.rank--; //demote y
				}
				rebalanceNode.rank--; //demote z
				rebalanceNode = rebalanceNode.parent; 
				
				rebalances+=2; //double demote
				
				if (rebalanceNode==null) { //root need not push problem upwards- fixed
					caseNum = 0; 
					break; 
				}
				caseNum = whichCaseDelete(rebalanceNode);
				break;
				
			case 3: //rotate (+rank changes)
				diff1 = rebalanceNode.getRank() - rebalanceNode.right.getRank(); 
				diff2 = rebalanceNode.getRank() - rebalanceNode.left.getRank();
				if (diff1 == 1) {//check normal of symmetric cases
					leftRotate(rebalanceNode); 
				}
				if (diff2 == 1) {
					rightRotate(rebalanceNode); 
					}
				
				rebalanceNode.parent.rank++; //promote y
				rebalanceNode.rank--; //demote z
				
				int diff3 = rebalanceNode.getRank() - rebalanceNode.right.getRank(); 
				int diff4 = rebalanceNode.getRank() - rebalanceNode.left.getRank(); 
				if (diff3 == 2 && diff4 == 2) { //if z=2,2 demote z again
					rebalanceNode.rank--; 
					rebalances++; 
				}

				rebalances+=3; //rotate, demote,promote (if extra demote: already counted)
				
				caseNum = 0; 
				break; 
				
			case 4: //double rotate (+rank changes)
				diff1 = rebalanceNode.getRank() - rebalanceNode.right.getRank(); 
				diff2 = rebalanceNode.getRank() - rebalanceNode.left.getRank();
				if (diff1 == 1) { //check normal of symmetric cases
					rightRotate(rebalanceNode.right); 
					leftRotate(rebalanceNode); 
				}
				if (diff2 == 1) {
					leftRotate(rebalanceNode.left); 
					rightRotate(rebalanceNode); 
				}
				
				rebalanceNode.rank-=2; //demote z twice (as NOT properly mentioned in class)
				rebalanceNode.parent.rank+=2; //promote a twice
				rebalanceNode.getSibling().rank--; //demote y
				
				rebalances+=7; //2 rotations, 3 demotions, 2 promotions
				
				caseNum = 0; 
				break;
			   } // end switch cases  
			   
		 } // end while loop 
		 
	   return rebalances; 
	   
      }

      /**
       * Receives a leaf node and removes it from the tree (such that it is replaced with EXT and put in a 1 node tree).
       */
   private void deleteLeaf(WAVLNode deletionNode) {
	   
	   deletionNode.size=1;
	   
	   if(deletionNode==root) { //single node in tree
		   root=null;
		   return;
	   }
	   else if (deletionNode.parent.left == deletionNode) { //check normal/ symmetric case (note: deletionNode not EXT or with null parent)
		   deletionNode.parent.left=EXT;
		   deletionNode.parent=null;  
	   }
	   else {
		   deletionNode.parent.right=EXT;
		   deletionNode.parent=null;
	   }
	   
   }
   
   /**
    * Receives an unary node and removes it from the tree (such that its child is now its parents child, and the deleted node is put in a 1 node tree).
    */
   private void deleteUnary(WAVLNode deletionNode) {
	   
	   deletionNode.size=1;
	   
	   boolean parentRight,sonRight; 
	   WAVLNode parentNode,sonNode;
	   sonRight = deletionNode.right != EXT ? true:false; //Indicator whether the son node is a right or left child
	   sonNode = sonRight ? deletionNode.right : deletionNode.left; //keeps pointer to relevant child (whicj is not EXT)
	   deletionNode.right=EXT; //change deleted node's son pointers to be in separate tree (no problem-we have pointer to son)
	   deletionNode.left=EXT;
	   
	   if(deletionNode==root) { //fix edgecase root pointer
		   root=sonNode;
		   sonNode.parent=null;
		   return; //if this is the case sonNode now only node in tree-otherwise tree was unbalanced during insertion
	   }
	   
	   //if not a root (we can change parent pointers without messing with null)
	   
	   parentRight= deletionNode.parent.right==deletionNode ? true:false; //whether the parent link (in "bypass") should be right or not
	   parentNode= deletionNode.parent;
	   deletionNode.parent=null; //null parent pointer for single node tree
	   sonNode.parent=parentNode;
	   if(parentRight) parentNode.right=sonNode; //Complete new parent pointer according to relation with bypassed
	   else parentNode.left=sonNode;
	   
}
   
   /**
    * Receives a binary node and its successor removes it from the tree (such that its successor is put in its place, and the deleted node is put in a 1 node tree).
    * on implementation:
    * we first delete the successor from the tree (it is now in a 1 node tree).
    * we then keep pointers to the binary nodes parent, left and right children, and insert them as the new nodes respective fields while changing the binarys pointers such that it is in a 1 node tree.
    */
   private void deleteBinary(WAVLNode deletionNode, WAVLNode succsesor) {
	   
	   
	   if(isLeaf(succsesor)) deleteLeaf(succsesor);
	   else deleteUnary(succsesor); 
	   //FIRST delete successor then switch pointers with deleted- now pointers are (null,EXT,EXT)
	   
	   if(deletionNode==root) root=succsesor; //update root
	   
	   succsesor.size=deletionNode.size;
	   deletionNode.size=1; //fix sizes
	   
	   succsesor.rank=deletionNode.rank; 
	   deletionNode.rank=0; //fix ranks
	   
	   WAVLNode rightNode=deletionNode.right; //keep pointers for deleted
	   WAVLNode leftNode=deletionNode.left;
	   WAVLNode parentNode=deletionNode.parent;
	   
	   
	   //give successor all pointers,while fixing pointers in other direction too
	   succsesor.right=rightNode;
	   rightNode.parent=succsesor; //not EXT/null
	   succsesor.left=leftNode;
	   leftNode.parent=succsesor; //not EXT/null
	   succsesor.parent=parentNode;
	   if(parentNode!=null) { //avoid root pointer problems (no need to change roots parent...)
	   if(deletionNode==parentNode.right) parentNode.right=succsesor;
	   else parentNode.left=succsesor;
	   }
	   
	   //put deletion in 1 node tree
	   deletionNode.right=EXT;
	   deletionNode.left=EXT;
	   deletionNode.parent=null;
	   
   }
   
   /**
    * returns if given node is a leaf (has no children)
    */
   private boolean isLeaf(WAVLNode node) { 
	   return node.getRight() == null && node.getLeft() == null; 
   }
   
   /**
    * returns if given node is an unary node (has one child)
    */
   private boolean isUnary(WAVLNode node) {
	   return (node.getLeft() != null && node.getRight() == null) || (node.getLeft() == null && node.getRight() != null); 

   }
   
   /**
    * Receives a node (which should be a deleted nodes parent) and decreases sizes by 1 while going up to the root (if we delete a node than its parent should have its size decreased by 1, and also its parent and so on).
    */
   private void decreaseSizesUp(WAVLNode node) {
	while (node!=null) {
		node.size--;
		node=node.parent;
	}
   }
   
   /**
    * Receives the current node from which we are rebalancing (z in presentation) and returns first rebalance operation num in case of leaf deleted
    * (for reference see WAVL slide 52).
    */
   private int leafDeletionCases(WAVLNode node) {
	   int diff1 = node.getRank() - node.right.getRank(); 
	   int diff2 = node.getRank() - node.left.getRank(); 
	   if ((diff1 == 1 && diff2 == 2) || (diff1 == 2 && diff2 == 1)) {
		   return 1; 
	   }
	   if (diff1 == 2 && diff2 == 2) { 
		   return 2; 
	   }
	   return 3; 
   }
   
   /**
    * Receives the current node from which we are rebalancing (z in presentation) and returns first rebalance operation num in case of unary deleted
    * (for reference see WAVL slide 53).
    */
   private int unaryDeletionCases(WAVLNode node) {
	   int diff1 = node.getRank() - node.right.getRank(); 
	   int diff2 = node.getRank() - node.left.getRank(); 
	   if ((diff1 == 1 && diff2 == 2) || (diff1 == 2 && diff2 == 1)) {
		   return 1; 
	   }
	   if (diff1 == 2 && diff2 == 2) {
		   return 2; 
	   }
	   return 3; 
   }
   
   /**
    * Receives the current node from which we are rebalancing (z in presentation) and returns rebalance operation num
    * (for reference see WAVL slide 54).
    * works by process of elimination.
    */
   private int whichCaseDelete(WAVLNode node) {
	   int diff1 = node.getRank() - node.right.getRank(); 
	   int diff2 = node.getRank() - node.left.getRank(); 
	   if (diff1 != 3 && diff2 != 3) {
		   return 0; 
	   }
	   if ((diff1 == 2 && diff2 == 3) || (diff1 == 3 && diff2 == 2)) {
		   return 1; 
	   }
	   if (diff1 == 1) { //normal case
		   WAVLNode y = node.right; 
		   int diff3 = y.getRank() - y.right.getRank(); 
		   int diff4 = y.getRank() - y.left.getRank(); 
		   if (diff3 == 1) {
			   return 3; 
		   }
		   if (diff4 == 1) {
			   return 4; 
		   }
		   return 2;  
	   }
	   else { // diff2 == 1 (symmetric case)
		   WAVLNode y = node.left; 
		   int diff3 = y.getRank() - y.left.getRank(); 
		   int diff4 = y.getRank() - y.right.getRank(); 
		   if (diff3 == 1) {
			   return 3; 
		   }
		   if (diff4 == 1) {
			   return 4; 
		   }
		   return 2; //if not 1,3,4
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
   
   //TODO delete!!!!
   private static final boolean DISPLAY_SUBTREESIZE = true;	

   public void display() {
   		display(!DISPLAY_SUBTREESIZE);
   	}

   	public void display(boolean displayRank) {
   		final int height = root.rank*2+2, width = (root.getSubtreeSize() + 1) * 12;

   		int len = width * height * 2 + 2;
   		StringBuilder sb = new StringBuilder(len);
   		for (int i = 1; i <= len; i++)
   			sb.append(i < len - 2 && i % width == 0 ? "\n" : ' ');

   		displayR(sb, width / 2, 1, width / 4, width, root, " ", displayRank);
   		System.out.println(sb);
   	}

   	private void displayR(StringBuilder sb, int c, int r, int d, int w, WAVLNode n, String edge, boolean displayRank) {
   		if (n != null) {
   			displayR(sb, c - d, r + 2, d / 2, w, n.left, " /", displayRank);

   			String s = (displayRank) ? String.valueOf(n.key) + "[" + n.getSubtreeSize() + "]" : String.valueOf(n.key) + "[" + n.rank + "]";
   			int idx1 = r * w + c - (s.length() + 1) / 2;
   			int idx2 = idx1 + s.length();
   			int idx3 = idx1 - w;
   			if (idx2 < sb.length())
   				sb.replace(idx1, idx2, s).replace(idx3, idx3 + 2, edge);

   			displayR(sb, c + d, r + 2, d / 2, w, n.right, "\\ ", displayRank);
   		}
   	}
   	//TODO end of delete
   
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

   }
}
