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
		
		for (int i = 1; i <= 20; i++) {
			int x = rand.nextInt(200000000); 
			set.add(x); 
			wTree.insert(x, Integer.toString(x)); 
		}
		int i=1;
		for (int x : set) {
			System.out.println("deleted "+i);
			i++;
			wTree.delete(x); 
		}
/*        int h=0;
		h+= wTree.insert(35, "");
		h+= wTree.insert(30, "");
		h+= wTree.insert(25, "");
		h+= wTree.insert(20, "");
		h+= wTree.insert(15, "");
		h+= wTree.insert(10, "");
		h+= wTree.insert(5, "");
		wTree.delete(5);
		wTree.delete(15);
		wTree.delete(10);
	
		
		int[] a=wTree.keysToArray();
		String[] b=new String[a.length];
		for (int i = 0; i < a.length; i++) {
			WAVLTree.WAVLNode w=wTree.treePosition(a[i], wTree.getRoot(), false);
			b[i]=""+w.getKey()+":"+w.getRank();
		}
		System.out.println(Arrays.toString(b));
		System.out.println(h);*/
		System.out.println("********************************");
		System.out.println(wTree.getRoot().getKey()); 
		System.out.println("********************************");
		System.out.println(wTree.size());
		System.out.println(Arrays.toString(wTree.keysToArray()));
		System.out.println(Arrays.toString(wTree.infoToArray())); 
	}
}
