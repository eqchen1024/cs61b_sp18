package byog.Core;

import java.util.Random;
import byog.Core.RandomUtils;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;




public class WorldGeneration {
    private static final int SEED = 11;
    private static final Random RANDOM = new Random(SEED);

    private static class Pos{
        private final int  x;
        private final int y;
        private Pos(int xPos,int yPos){
              x = xPos;
              y = yPos;
        }
    }

    public static class Rectangle{
        public Pos position;
        public int width;
        public int height;
        public Rectangle(Pos p,int w,int h){
            position= p;
            width =w;
            height=h;
        }
    }

    public static class Room {
        public Random RANDOM;
        public Rectangle outer_r;
        public Rectangle inner_r;
        public Room[] neighbour =new Room[4];
        public Pos[] corners = new Pos[4];
        public Pos[] doors = new Pos[4];
        public int[] sideOfDoors = new int[4];

        public Room(Rectangle r, Random random) {
            outer_r = r;
            int inner_pos_x = outer_r.position.x + 1;
            int inner_pos_y = outer_r.position.y + 1;
            int inner_width = outer_r.width - 2;
            int inner_height = outer_r.height - 2;
            Pos inner_pos = new Pos(inner_pos_x, inner_pos_y);
            inner_r = new Rectangle(inner_pos, inner_width, inner_height);
            corners[0] = outer_r.position;
            corners[1] = new Pos(outer_r.position.x + outer_r.width - 1, outer_r.position.y);
            corners[2] = new Pos(outer_r.position.x + outer_r.width, outer_r.position.y + outer_r.height - 1);
            corners[3] = new Pos(outer_r.position.x, outer_r.position.y + outer_r.height - 1);
            RANDOM = random;
        }

        public Pos[] genDoors(int upLimit) {
            int numOfNeighbor = RandomUtils.uniform(RANDOM,2, upLimit);
            Pos posOfDoor;
            int door_x;
            int door_y;
            for (int i = 0; i < numOfNeighbor; i++) {
                int sideOfDoor = RandomUtils.uniform(RANDOM, 4);
                sideOfDoors[i] = sideOfDoor;
                switch (sideOfDoors[i]) {
                    // down
                    case 0:
                        door_x = RandomUtils.uniform(RANDOM,
                                corners[0].x + 1, corners[1].x - 1);
                        door_y = corners[0].y;
                        posOfDoor = new Pos(door_x, door_y);
                        doors[i] = posOfDoor;
                        break;
                    // up
                    case 1:
                        door_x = RandomUtils.uniform(RANDOM,
                                corners[3].x + 1, corners[2].x - 1);
                        door_y = corners[3].y;
                        posOfDoor = new Pos(door_x, door_y);
                        doors[i] = posOfDoor;
                        break;

                    // left
                    case 2:
                        door_x = corners[0].x;
                        door_y = RandomUtils.uniform(RANDOM,
                                corners[0].y + 1, corners[3].y - 1);
                        posOfDoor = new Pos(door_x, door_y);
                        doors[i] = posOfDoor;
                        break;

                    // right
                    case 3:
                        door_x = corners[1].x;
                        door_y = RandomUtils.uniform(RANDOM,
                                corners[1].y + 1, corners[2].y - 1);
                        posOfDoor = new Pos(door_x, door_y);
                        doors[i] = posOfDoor;
                        break;

                    default: System.out.println(-1);
                }
            }
            return doors;
        }


        public static Room genNeighbourRoom(Pos door, int sideOfDoor, int width, int height,Random RANDOM) {
            Pos newRoomPosition;
            Rectangle neighbourRectangle;
            switch (sideOfDoor) {
                // down
                case 0:
                    newRoomPosition = new Pos(door.x - RandomUtils.uniform(RANDOM, width - 1)
                            , door.y - height + 1);
                    neighbourRectangle = new Rectangle(newRoomPosition, width, height);
                    return new Room(neighbourRectangle, RANDOM);

                // up
                case 1:
                    newRoomPosition = new Pos(door.x - RandomUtils.uniform(RANDOM, width - 1)
                            , door.y);
                    neighbourRectangle = new Rectangle(newRoomPosition, width, height);
                    return new Room(neighbourRectangle, RANDOM);

                // left
                case 2:
                    newRoomPosition = new Pos(door.x - width + 1
                            , door.y - RandomUtils.uniform(RANDOM, height - 1));
                    neighbourRectangle = new Rectangle(newRoomPosition, width, height);
                    return new Room(neighbourRectangle, RANDOM);

                // right
                case 3:
                    newRoomPosition = new Pos(door.x
                            , door.y - RandomUtils.uniform(RANDOM, height - 1));
                    neighbourRectangle = new Rectangle(newRoomPosition, width, height);
                    return new Room(neighbourRectangle, RANDOM);

            }
            neighbourRectangle = new Rectangle(new Pos(0, 0), width, height);
            return new Room(neighbourRectangle, RANDOM);
        }

        public void genAllNeighbourRooms(Random RANDOM) {
            for (int i = 0; i < doors.length; i++) {
                if (doors[i] != null) {
                    System.out.println(doors[i].x);
                    int width = RandomUtils.uniform(RANDOM,3, 5);
                    int height = RandomUtils.uniform(RANDOM,3,5);
                    Room room_n = genNeighbourRoom(doors[i], sideOfDoors[i], width, height,RANDOM);
                    neighbour[i] = room_n;
                }
            }
        }
    }

    public static void drawDoors(TETile[][] world,Room room){
        for (int i = 0; i < room.doors.length; i++) {
            if (room.doors[i] != null) {
                int x = room.doors[i].x;
                int y = room.doors[i].y;
                System.out.println(x);
                if (world[x][y] == Tileset.WALL){
                    world[x][y] = Tileset.FLOOR;
                }

            }
        }
    }

    public static void drawAllNeighbourRooms(TETile[][] world,Room room) {
        for (int i = 0; i < room.neighbour.length; i++) {
            if (room.neighbour[i] != null) {
                drawRoom(world, room.neighbour[i]);
            }
        }
    }

    public static void drawRectangle(TETile[][] world,Rectangle r, TETile texture){
        for (int x = r.position.x; x < r.position.x + r.width; x += 1) {
            for (int y = r.position.y; y < r.position.y + r.height; y += 1) {
                world[x][y] = texture;
            }
        }
    }

    public static void drawRoom(TETile[][] world,Room r){
        drawRectangle(world,r.outer_r,Tileset.WALL);
        drawRectangle(world,r.inner_r,Tileset.FLOOR);
    }



    public static void genBackGround(TETile[][] world,Rectangle r){
        drawRectangle(world,r,Tileset.NOTHING);
    }

    //利用递归生成room和way

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        int width = 120;
        int height = 60;
        Pos origin = new Pos(0,0);
        Rectangle canvas = new Rectangle(origin,width,height);
        Pos R1 = new Pos(60,30);

        ter.initialize(width, height);

        // initialize tiles
        TETile[][] world = new TETile[width][height];
        genBackGround(world, canvas);
        // Add rect
        Rectangle rec1 = new Rectangle(R1,8,8);
        Room r1 = new Room(rec1,RANDOM);
        drawRoom(world,r1);
        r1.genDoors(4);
        //TODO 门给盖歪了
        r1.genAllNeighbourRooms(RANDOM);
        drawAllNeighbourRooms(world,r1);
        drawDoors(world,r1);
        //show the world
        ter.renderFrame(world);

    }
}

