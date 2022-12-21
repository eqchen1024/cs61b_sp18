package lab14;

import edu.princeton.cs.algs4.StdAudio;
import lab14lib.Generator;

public class SawToothGenerator implements Generator {
    private double period;
    private int state;

    public SawToothGenerator(int period) {
        state = 0;
        this.period = period;
    }

    public static double Normalize(double min, double max, double val) {
        return ((val - min) / max - 0.5) * 2;
    }

    @Override
    public double next() {
        state = (state + 1);
        return Normalize(0,period,(state % period)) ;
    }
}
