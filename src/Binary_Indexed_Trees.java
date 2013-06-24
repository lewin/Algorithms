/**
 * Given a list of N numbers in a row, we can use this structure for
 * exactly 1 of 2 uses:
 * a) range query, point update
 * b) range update, point query
 * @author Lewin
 */
public class Binary_Indexed_Trees {
    private static int []   tree;
    private static int      N;
    
    /**
     * In model:
     * a) ask for sum of numbers from 1 to K
     * b) ask for value of number at K
     * @param K position
     * @return depends on model
     */
    private static int query (int K) {
        int sum = 0;
        for (int i = K; i > 0; i -= (i & -i))
            sum += tree [i];
        return sum;
    }
    
    /**
     * In model:
     * a) increment position K by val
     * b) increment positions 1 to K by val
     * @param K position
     * @param val increment
     */
    private static void update (int K, int val) {
        for (int i = K; i <= N; i += (i & -i))
            tree [i] += val;
    }
}
