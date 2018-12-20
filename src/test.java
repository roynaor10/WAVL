import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
 

public class test {

	public static void main(String[] args) {
		WAVLTree wTree=new WAVLTree();
		Random rand = new Random();
        Set<Integer> set = new HashSet<Integer>(); 
        
        //wTree.insert(-4, "-4"); 
		
		/*for (int i = 1; i <= 20; i++) {
			int x = rand.nextInt(200000000); 
			set.add(x); 
			wTree.insert(x, Integer.toString(x)); 
		}
		int i=1;
		for (int x : set) {
			System.out.println("deleted "+i);
			i++;
			wTree.delete(x); 
		}*/
		wTree.insert(20, "");
		wTree.insert(10, "");
		wTree.insert(30, "");
		wTree.insert(5, "");
		wTree.insert(15, "");
		wTree.insert(25, "");
		wTree.insert(35, "");
		wTree.delete(5);
		wTree.delete(15);
		wTree.delete(10);
	
		
		
		
		System.out.println("********************************");
		System.out.println(wTree.getRoot().getLeft().getKey()); 
		System.out.println("********************************");
		System.out.println(wTree.size());
		System.out.println(Arrays.toString(wTree.keysToArray()));
		System.out.println(Arrays.toString(wTree.infoToArray())); 
	}
}
