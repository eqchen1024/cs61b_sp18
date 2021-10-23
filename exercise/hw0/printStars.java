public class printStars {
    public static void main(String[] args) {
        int x = 1;
        while (x<=5){
            int n = 1;
            while (n <= x){
              System.out.print("*");
              n = n + 1;
            }
        System.out.println("");
        x = x + 1;
        }
    }
}
