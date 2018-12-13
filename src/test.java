import java.util.Arrays;

public class test {

	public static void main(String[] args) {
		WAVLTree wTree=new WAVLTree();
		wTree.insert(8, "please work"); 
		wTree.insert(78, "hello"); 
		wTree.insert(80, "there"); 

		System.out.println(wTree.size());
		System.out.println(Arrays.toString(wTree.keysToArray()));
		System.out.println(wTree.getRoot().getKey());

	}

}
