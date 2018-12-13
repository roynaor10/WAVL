import java.util.Arrays;

public class test {

	public static void main(String[] args) {
		WAVLTree wTree=new WAVLTree();


		wTree.insert(5, "5"); 
		wTree.insert(4, "4"); 
		wTree.insert(3, "3"); 
		wTree.insert(2, "2"); 
		wTree.insert(1, "1"); 	
		System.out.println(wTree.size());
		System.out.println(Arrays.toString(wTree.keysToArray()));
		System.out.println(Arrays.toString(wTree.infoToArray())); 
	}
}
