package hw3.hash;

public class Hint {
    public static void main(String[] args) {
        System.out.println("The powers of 256 in Java are:");
        long x = 1;
        long m = 2147483647;
        for (int i = 0; i < 10; i += 1) {
            System.out.println(i + "th power: " + x);
            x = (x * 256) %  m;
        }
    }
} 
