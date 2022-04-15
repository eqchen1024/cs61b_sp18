package byog.Core;

import java.util.Random;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;


public class WorldGeneration {
    private static final int SEED = 114514;
    private static final Random RANDOM = new Random(SEED);

    public static void genBackGround(TETile[][] world, int width, int height){
        for (int x = 0; x < width; x += 1) {
            for (int y = 0; y < height; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
    }
    //TODO define a draw line function and wrap it with a rectangle draw function

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        int width = 100;
        int height = 100;
        ter.initialize(width, height);

        // initialize tiles
        TETile[][] world = new TETile[width][height];
        genBackGround(world, width, height);
        ter.renderFrame(world);

    }
}

