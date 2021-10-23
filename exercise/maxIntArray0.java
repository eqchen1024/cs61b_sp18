public class maxIntArray0 {
    /** Returns the maximum value from m. */
    public static int max(int[] m) {
        int len = m.length;
        int curMax = 0;
        for (int i = 0; i<len;i+=1){
          if (m[i]>=curMax){
            curMax=m[i];
          }
        }
        return curMax;
    }
    public static void main(String[] args) {
       int[] numbers = new int[]{9, 2, 15, 2, 22, 10, 6};
       System.out.println(max(numbers));
    }
}
