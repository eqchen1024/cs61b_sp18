package byog.Core;

import byog.Core.WorldGeneration.Pos;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Player {
    public Pos pos = new Pos(0,0);
    public boolean hasKey = false;

    public void initialize(TETile[][] world, Random rand){
        boolean isFloor = false;
        while (!isFloor) {
            pos.x = RandomUtils.uniform(rand, world.length);
            pos.y = RandomUtils.uniform(rand, world[1].length);
            if (world[pos.x][pos.y] == Tileset.FLOOR) {
                world[pos.x][pos.y] = Tileset.PLAYER;
                break;
            }
        }
    }

//    public void move(TETile[][] world,char direction){
//
//    }
}
