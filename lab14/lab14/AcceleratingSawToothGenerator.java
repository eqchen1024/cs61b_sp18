package lab14;

import lab14lib.Generator;

public class AcceleratingSawToothGenerator implements Generator {

    private double period;
    private int state;
    private double factor;
    private int accmuState;

    public AcceleratingSawToothGenerator(int period, double factor) {
        state = 0;
        this.period = period;
        this.factor = factor;
        this.accmuState = 0;

    }

    public static double Normalize(double min, double max, double val) {
        return ((val - min) / max - 0.5) * 2;
    }

    @Override
    public double next() {
        state = (state + 1);
        if ((state - accmuState) % period == 0l) {
            accmuState += period;
            period *= factor;
            period = Math.floor(period);

        }
        return Normalize(0,period,(state - accmuState) % period);
    }
}
