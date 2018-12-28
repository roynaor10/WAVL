import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class timetest {

	public static void main(String[] args) {
		
		
		final int TEST_MULTIPLAYER=10000;
		WAVLTree testtree=new WAVLTree();
		Set<Integer> set=new HashSet<>();
		Random random=new Random();
		double insertcount=0, deletecount=0;
		int maxinsert=-1, maxdelete=-1;
		int temp,rebalances;
		
		
		for (int i = 1; i <= 10; i++) {
		while (set.size()<i*TEST_MULTIPLAYER) {
			temp=random.nextInt(10*i*TEST_MULTIPLAYER);
			if(set.contains(temp)) continue;
			set.add(temp);
			rebalances=testtree.insert(temp, "*");
			if(rebalances>maxinsert) maxinsert=rebalances;
			insertcount+=rebalances;
		}
		
		for (Integer num : set) {
			rebalances=testtree.delete(num);
			assert(rebalances>=0);
			if(rebalances>maxdelete) maxdelete=rebalances;
			deletecount+=rebalances;
		}
		
		set.clear();
		
		System.out.println("***********************");
		System.out.println("tree size: "+(i*TEST_MULTIPLAYER));
		System.out.println("avg insert: "+(insertcount/(i*TEST_MULTIPLAYER)));
		System.out.println("max insert: "+maxinsert);
		System.out.println("avg delete: "+(deletecount/(i*TEST_MULTIPLAYER)));
		System.out.println("max delete: "+maxdelete);
		
		insertcount=0; deletecount=0;maxinsert=-1; maxdelete=-1;
		}

	}

}
