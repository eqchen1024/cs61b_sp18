package lab14;

import lab14lib.Generator;

public class StrangeBitwiseGenerator implements Generator {
    private double period;
    private int state;

    public StrangeBitwiseGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public static double Normalize(double min, double max, double val) {
        return ((val - min) / max - 0.5) * 2;
    }

    @Override
    public double next() {
        state = (state + 1);
//        int weirdState = (int) ((state & (state >>> 3)) % period);
        int weirdState = (int) ((state & (state >> 3) & (state >> 8)) % period);

        return Normalize(0,period,(weirdState % period)) ;
    }
}
