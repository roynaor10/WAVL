import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
 

public class test {

	public static void main(String[] args) {
		WAVLTree wTree=new WAVLTree();
		Random rand = new Random();
        Set<Integer> set = new HashSet<Integer>(); 
        
        wTree.insert(-4, "-4"); 
		
		for (int i = 1; i <= 200000; i++) {
			int x = rand.nextInt(200000000); 
			set.add(x); 
			wTree.insert(x, Integer.toString(x)); 
		}
		
		for (int x : set) {
			wTree.delete(x); 
		}
	
		
		
		
		System.out.println("********************************");
		System.out.println(wTree.getRoot().getKey()); 
		System.out.println("********************************");
		System.out.println(wTree.size());
		System.out.println(Arrays.toString(wTree.keysToArray()));
		System.out.println(Arrays.toString(wTree.infoToArray())); 
	}
}
