package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Clorus extends Creature {

    /**
     * Creates a creature with the name N. The intention is that this
     * name should be shared between all creatures of the same type.
     *
     * @param n
     */
    public Clorus(String n) {
        super(n);
    }
    public Clorus() {
        this(1);
    }

    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
    }

    @Override
    public void attack(Creature c) {
        energy += c.energy();
    }

    @Override
    public Creature replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> emptyDirections = new ArrayList<>();
        List<Direction> plipDirections = new ArrayList<>();
        for(Direction d: neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyDirections.add(d);
            } else if (neighbors.get(d).name().equals("plip")) {
                plipDirections.add(d);
            }
        }
        if (emptyDirections.isEmpty()) {
            return new Action(Action.ActionType.STAY);
        } else if (!plipDirections.isEmpty()) {
            Random rand = new Random();
            int randIndex = rand.nextInt(plipDirections.size());
            return new Action(Action.ActionType.ATTACK,plipDirections.get(randIndex));
        } else if (energy >= 1) {
            Random rand = new Random();
            int randIndex = rand.nextInt(emptyDirections.size());
            return new Action(Action.ActionType.REPLICATE,emptyDirections.get(randIndex));
        }
        Random rand = new Random();
        int randIndex = rand.nextInt(emptyDirections.size());
        return new Action(Action.ActionType.MOVE,emptyDirections.get(randIndex));
    }

    @Override
    public Color color() {
        return color(34,0,231);
    }
}
