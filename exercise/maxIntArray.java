public class maxIntArray {
    /** Returns the maximum value from m. */
    public static int max(int[] m) {
        int len = m.length;
        int cur = 0;
        int curMax = 0;
        while (cur < len){
            if (curMax <= m[cur]){
              curMax = m[cur];
            }
            cur = cur +1;
        }
        return curMax;
    }
    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
       System.out.println(max(numbers));
    }
}
