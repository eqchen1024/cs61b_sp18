public class sumUpArgs {
    public static void main(String[] args) {
        int res =0;
        for (String i:args){
          res+=Integer.parseInt(i);
        }
        System.out.println(res);
        }
    }
