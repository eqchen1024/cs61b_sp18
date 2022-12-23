package creatures;
import huglife.Creature;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.HugLifeUtils;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.Random;

/** An implementation of a motile pacifist photosynthesizer.
 *  @author Josh Hug
 */
public class Plip extends Creature {

    /** red color. */
    private int r;
    /** green color. */
    private int g;
    /** blue color. */
    private int b;

    /** creates plip with energy equal to E. */
    public Plip(double e) {
        super("plip");
        r = 99;
        g = calculateGreen(e,0,2);
        b = 76;
        energy = e;
    }

    /** creates a plip with energy equal to 1. */
    public Plip() {
        this(1);
    }

    public Plip(String s) {
        super(s);
        r = 99;
        g = 63;
        b = 76;
    }

    /** Should return a color with red = 99, blue = 76, and green that varies
     *  linearly based on the energy of the Plip. If the plip has zero energy,
     *  it should have a green value of 63. If it has max energy, it should
     *  have a green value of 255. The green value should vary with energy
     *  linearly in between these two extremes. It's not absolutely vital
     *  that you get this exactly correct.
     */
    public Color color() {
        return color(r, g, b);
    }

    /** Do nothing with C, Plips are pacifists. */
    public void attack(Creature c) {
    }

    /** Plips should lose 0.15 units of energy when moving. If you want to
     *  to avoid the magic number warning, you'll need to make a
     *  private static final variable. This is not required for this lab.
     */
    public void move() {
        energy -= 0.15;
        g = calculateGreen(energy,0,2);
    }


    /** Plips gain 0.2 energy when staying due to photosynthesis. */
    public void stay() {
        energy += 0.2;
        if (energy > 2) {
            energy = 2;
        }
        g = calculateGreen(energy,0,2);
    }

    /** Plips and their offspring each get 50% of the energy, with none
     *  lost to the process. Now that's efficiency! Returns a baby
     *  Plip.
     */
    public Plip replicate() {
        this.energy /= 2;
        g = calculateGreen(this.energy,0,2);
        return new Plip(this.energy);
    }

    /** Plips take exactly the following actions based on NEIGHBORS:
     *  1. If no empty adjacent spaces, STAY.
     *  2. Otherwise, if energy >= 1, REPLICATE.
     *  3. Otherwise, if any Cloruses, MOVE with 50% probability.
     *  4. Otherwise, if nothing else, STAY
     *
     *  Returns an object of type Action. See Action.java for the
     *  scoop on how Actions work. See SampleCreature.chooseAction()
     *  for an example to follow.
     */
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> emptyDirections = new ArrayList<>();
        List<Direction> clorusDirections = new ArrayList<>();
        for(Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyDirections.add(d);
            } else if (neighbors.get(d).name().equals("chorus")) {
                clorusDirections.add(d);
            }
        }
        if (emptyDirections.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (energy > 1) {
            Random rand = new Random();
            int randIndex = rand.nextInt(emptyDirections.size());
            return new Action(Action.ActionType.REPLICATE,emptyDirections.get(randIndex));
        } else if (!clorusDirections.isEmpty()) {
            if (new Random().nextFloat() < 0.5) {
                Random rand = new Random();
                int randIndex = rand.nextInt(emptyDirections.size());
                return new Action(Action.ActionType.MOVE,emptyDirections.get(randIndex));
            }
        }
        return new Action(Action.ActionType.STAY);
    }

    public static int calculateGreen(double e, double min, double max) {
        return (int) Math.floor(63 + (255 - 63) * ((e - min) / (max - min))) ;
    }

    public static void main(String[] args) {
       Plip p = new Plip();
       System.out.println(p.name());
       System.out.println(p.color());
       System.out.println(p.energy);

    }
}
