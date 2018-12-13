import java.util.Arrays;

public class test {

	public static void main(String[] args) {
		WAVLTree wTree=new WAVLTree();
		wTree.insert(35, "please work"); 
		System.out.println(Arrays.toString(wTree.keysToArray()));
		wTree.insert(30, "there"); 
		System.out.println(Arrays.toString(wTree.keysToArray()));
		wTree.insert(25, "hello"); 
		System.out.println(Arrays.toString(wTree.keysToArray()));
		wTree.insert(20, "ther6e"); 
		System.out.println(Arrays.toString(wTree.keysToArray()));
		wTree.insert(15, "there"); 
		System.out.println(Arrays.toString(wTree.keysToArray()));
		System.out.println(wTree.getRoot().getRight().getKey());
		wTree.insert(10, "there"); 
		System.out.println(Arrays.toString(wTree.keysToArray()));
		wTree.insert(5, "there"); 
		System.out.println(Arrays.toString(wTree.keysToArray()));

		//System.out.println(wTree.size());
		//System.out.println(Arrays.toString(wTree.keysToArray()));
		//System.out.println(wTree.getRoot().getKey());
		//System.out.println(wTree.getRoot().getRight().getRight().getKey());

	}

}
