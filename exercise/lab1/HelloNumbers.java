public class HelloNumbers {
    public static void main(String[] args) {
        int x = 0;
        int cum = 0;
        while (x < 10) {
            System.out.print(cum + " ");
            x = x + 1;
            cum = cum+x;
        }
    }
}
