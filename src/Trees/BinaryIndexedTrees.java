package Trees;
/**
 * Given a list of N numbers in a row, we can use this structure for
 * exactly 1 of 2 uses:
 * a) range query, point update
 * b) range update, point query
 * 
 * For more info, see:
 * http://community.topcoder.com/tc?module=Static&d1=tutorials&d2=binaryIndexedTrees
 * @author Lewin
 */
public class BinaryIndexedTrees {
    private static int []   tree;
    private static int      N;
    
    private static int query (int K) {
        int sum = 0;
        for (int i = K; i > 0; i -= (i & -i))
            sum += tree [i];
        return sum;
    }
    
    private static void update (int K, int val) {
        for (int i = K; i <= N; i += (i & -i))
            tree [i] += val;
    }
}
