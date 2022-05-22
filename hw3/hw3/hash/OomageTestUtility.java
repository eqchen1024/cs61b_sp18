package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] itemCountEachBucket = new int[M];
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            itemCountEachBucket[bucketNum] += 1;
        }
        for (int i = 0; i < M; i++) {
            if (itemCountEachBucket[i] < oomages.size() / 50 || itemCountEachBucket[i] > oomages.size() / 2.5) {
                return false;
            }
        }
        return true;
    }
}
