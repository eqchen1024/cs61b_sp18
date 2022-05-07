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

    public void move(TETile[][] world,String direction){
        int targetX = pos.x;
        int targetY = pos.y;
        String cmd = ""+direction;
        if (cmd.equals("w")){
            targetY += 1;
        } else if (cmd.equals("a")){
            targetX -= 1;
        } else if (cmd.equals("s")){
            targetY -= 1;
        } else if (cmd.equals("d")){
            targetX += 1;
        }
        if (world[targetX][targetY] == Tileset.WALL) {
            System.out.println("you can not pass through a wall");
        } else if (world[targetX][targetY] == Tileset.FLOOR){
            world[pos.x][pos.y] = Tileset.FLOOR;
            pos.x = targetX;
            pos.y = targetY;
            world[ pos.x ][pos.y] = Tileset.PLAYER;
        } else if (world[targetX][targetY] == Tileset.LOCKED_DOOR){
            if (hasKey==false) {
                System.out.println("Please find a key");
            } else {
                world[targetX][targetY] = Tileset.UNLOCKED_DOOR;
                WorldGeneration.isDoorOpened = true;
            }
        } else if (world[targetX][targetY] == Tileset.FLOWER) {
            System.out.println("you get the key");
            hasKey = true;
            world[pos.x][pos.y] = Tileset.FLOOR;
            pos.x = targetX;
            pos.y = targetY;
            world[ pos.x ][pos.y] = Tileset.PLAYER;
            WorldGeneration.isKeyGot = true;
        }
        WorldGeneration.playerPos = pos;
    }
}
