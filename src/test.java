import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
 

public class test {
	
	public static int height(WAVLTree.WAVLNode w) {
		if(w.getRight()==null &&w.getLeft()==null) return 0;
		int leftmax= w.getLeft()==null ? -1:height(w.getLeft());
		int rightmax= w.getRight()==null ? -1:height(w.getRight());
		return 1+Math.max(leftmax, rightmax);
	}

	public static void main(String[] args) {
		WAVLTree wTree=new WAVLTree();
		Random rand = new Random();
        Set<Integer> set = new HashSet<Integer>(); 
        
        wTree.insert(-4, "-4"); 
		
		for (int i = 1; i <= 10000; i++) {
			int x = rand.nextInt(50000); 
			set.add(x); 
			System.out.println("inserted "+x);
			wTree.insert(x, Integer.toString(x)); 
//			wTree.display();
//			System.out.println("********************************");
		}
//        int[] k=new int[] {8,18,15,35,16,43,19};
//		for (int i = 0; i < k.length; i++) {
//			wTree.insert(k[i], "");
//			int[] a=wTree.keysToArray();
//			String[] b=new String[a.length];
//			for (int j = 0; j < a.length; j++) {
//				WAVLTree.WAVLNode w=wTree.treePosition(a[j], wTree.getRoot(), false);
//				b[j]=""+w.getKey()+":"+w.getRank();
//			}
//			System.out.println(Arrays.toString(b));
//		}
//		int[] a=wTree.keysToArray();
//		String[] b=new String[a.length];
//		for (int j = 0; j < a.length; j++) {
//			WAVLTree.WAVLNode w=wTree.treePosition(a[j], wTree.getRoot(), false);
//			b[j]=""+w.getKey()+":"+w.getRank()+":"+height(w);
//		}
//		System.out.println(Arrays.toString(b));
//		System.out.println(Arrays.toString(a));
//		System.out.println(l.toString());
//		System.out.println(wTree.size());
//		System.out.println(height(wTree.getRoot()));
//		
//		wTree.display();
		int i=1;
		
		int stop=(set.size());
		for (int x : set) {
//			System.out.println("********************************");
			System.out.println("deleted "+x);
			System.out.println(wTree.size());
			i++;
			//if(i==stop) break;
			wTree.delete(x); 
			 //wTree.display();
//			wTree.display();
		}
		System.out.println(Arrays.toString(wTree.keysToArray()));
		System.out.println(height(wTree.getRoot()));
//        int h=0;
//		h+= wTree.insert(35, "");
//		h+= wTree.insert(30, "");
//		h+= wTree.insert(25, "");
//		h+= wTree.insert(20, "");
//		h+= wTree.insert(15, "");
//		h+= wTree.insert(10, "");
//		h+= wTree.insert(5, "");
//		wTree.delete(5);
//		wTree.delete(15);
//		wTree.delete(10);
//		wTree.delete(30);
//		wTree.delete(20);
//		wTree.delete(25);
//        wTree.display();
		
	
		

		//System.out.println(h);
		System.out.println("********************************");
		//System.out.println(wTree.getRoot().getRight().getRight().getKey()); 
		System.out.println("********************************");
		System.out.println(wTree.size());
		//System.out.println(Arrays.toString(wTree.keysToArray()));
		//System.out.println(Arrays.toString(wTree.infoToArray())); 
	}
}
