import java.util.Arrays;

public class test {

	public static void main(String[] args) {
		WAVLTree wTree=new WAVLTree();


		wTree.insert(20, "5"); 
		wTree.insert(10, "4"); 
		wTree.insert(30, "3"); 
		wTree.insert(5, "2"); 
		wTree.insert(15, "1"); 	
		wTree.insert(25, "1"); 	
		wTree.insert(35, "1"); 	
		wTree.insert(40, "1"); 	
		wTree.delete(35);
		wTree.delete(30);
		wTree.delete(25);
		wTree.delete(5);
		wTree.delete(20);
		//System.out.println(wTree.getRoot().getKey());
		System.out.println(wTree.size());
		System.out.println(Arrays.toString(wTree.keysToArray()));
		//System.out.println(Arrays.toString(wTree.infoToArray())); 


	}
}
