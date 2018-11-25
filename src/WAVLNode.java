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
	
	 public int getKey() {
		 return key; 
	 }
	 
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