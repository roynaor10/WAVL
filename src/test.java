import java.util.Arrays;

public class test {

	public static void main(String[] args) {
		WAVLTree wTree=new WAVLTree();
		wTree.insert(20, "please work"); 
		wTree.insert(10, "hello"); 
		wTree.insert(30, "there"); 
		wTree.insert(5, "there"); 
		wTree.insert(15, "there"); 
		wTree.insert(25, "there"); 
		wTree.insert(35, "there"); 

		System.out.println(wTree.size());
		System.out.println(Arrays.toString(wTree.keysToArray()));
		//System.out.println(wTree.getRoot().getKey());
		System.out.println(wTree.getRoot().getRight().getRight().getKey());

	}

}
