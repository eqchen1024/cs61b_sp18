public class drawTraingle {

    public static void drawNlevelTraingle(int level) {
        int x = 1;
        while (x<=level){
            int n = 1;
            while (n <= x){
              System.out.print("*");
              n = n + 1;
            }
        System.out.println("");
        x = x + 1;
        }
    }
    public static void main(String[] args) {
        drawNlevelTraingle(10);
        }
}
