package byog.Core;

import java.util.Arrays;
import java.util.Random;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;




public class WorldGeneration {
    public static long SEED;
    public static Random RANDOM;
    public static int MaxRecursionDepth = 10;
    public static Pos keyPos;
    public static Pos doorPos;
    public static Pos playerPos;
    public static boolean isDoorOpened = false;
    public static boolean isKeyGot = false;
    public static boolean reverse = false;

    public static class Pos{
        public int  x;
        public int y;
        public Pos(int xPos,int yPos){
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
        public Room[] neighbour =new Room[10];
        public Pos[] corners = new Pos[4];
        public Pos[] doors = new Pos[10];
        public int[] sideOfDoors = new int[10];
        public int doorFromSide = -1;

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
            corners[2] = new Pos(outer_r.position.x + outer_r.width - 1, outer_r.position.y + outer_r.height - 1);
            corners[3] = new Pos(outer_r.position.x, outer_r.position.y + outer_r.height - 1);
            RANDOM = random;
        }


        public Pos[] genDoors(int upLimit) {
            int freq[] = {0,4,3,1,0};
            int numOfNeighbor = RandomUtils.discrete(RANDOM,freq);
            Pos posOfDoor;
            int door_x;
            int door_y;
            for (int i = 0; i < numOfNeighbor; i++) {
                int sideOfDoor = RandomUtils.uniform(RANDOM, 4);
                if (sideOfDoor != doorFromSide) {
                    sideOfDoors[i] = sideOfDoor;
                    switch (sideOfDoors[i]) {
                        // down
                        case 0:
                            door_x = RandomUtils.uniform(RANDOM,
                                    corners[0].x + 1, corners[1].x);
                            door_y = corners[0].y;
                            posOfDoor = new Pos(door_x, door_y);
                            doors[i] = posOfDoor;
                            break;
                        // up
                        case 1:
                            door_x = RandomUtils.uniform(RANDOM,
                                    corners[3].x + 1, corners[2].x);
                            door_y = corners[3].y;
                            posOfDoor = new Pos(door_x, door_y);
                            doors[i] = posOfDoor;
                            break;

                        // left
                        case 2:
                            door_x = corners[0].x;
                            door_y = RandomUtils.uniform(RANDOM,
                                    corners[0].y + 1, corners[3].y);
                            posOfDoor = new Pos(door_x, door_y);
                            doors[i] = posOfDoor;
                            break;

                        // right
                        case 3:
                            door_x = corners[1].x;
                            door_y = RandomUtils.uniform(RANDOM,
                                    corners[1].y + 1, corners[2].y);
                            posOfDoor = new Pos(door_x, door_y);
                            doors[i] = posOfDoor;
                            break;

                        default:
                            System.out.println(-1);
                    }
                }
            }
            return doors;
        }


        public static Room genNeighbourRoom(Pos door, int sideOfDoor, int width, int height,Random RANDOM) {
            Pos newRoomPosition;
            Rectangle neighbourRectangle;
            Room room;
            switch (sideOfDoor) {
                // down
                case 0:
                    newRoomPosition = new Pos(door.x - RandomUtils.uniform(RANDOM,1, width - 1)
                            , door.y - height + 1);
                    neighbourRectangle = new Rectangle(newRoomPosition, width, height);
                    room = new Room(neighbourRectangle, RANDOM);
                    room.doorFromSide = 2;
                    return room;
                // up
                case 1:
                    newRoomPosition = new Pos(door.x - RandomUtils.uniform(RANDOM,1, width - 1)
                            , door.y);
                    neighbourRectangle = new Rectangle(newRoomPosition, width, height);
                    room = new Room(neighbourRectangle, RANDOM);
                    room.doorFromSide = 3;
                    return room;
                // left
                case 2:
                    newRoomPosition = new Pos(door.x - width + 1
                            , door.y - RandomUtils.uniform(RANDOM, 1, height - 1));
                    neighbourRectangle = new Rectangle(newRoomPosition, width, height);
                    room = new Room(neighbourRectangle, RANDOM);
                    room.doorFromSide = 0;
                    return room;

                // right
                case 3:
                    newRoomPosition = new Pos(door.x
                            , door.y - RandomUtils.uniform(RANDOM, 1, height - 1));
                    neighbourRectangle = new Rectangle(newRoomPosition, width, height);
                    room = new Room(neighbourRectangle, RANDOM);
                    room.doorFromSide = 1;
                    return room;

            }
            neighbourRectangle = new Rectangle(new Pos(0, 0), width, height);
            return new Room(neighbourRectangle, RANDOM);
        }

        public void genAllNeighbourRooms(TETile[][] world,Random RANDOM) {
            boolean breakout;
            for (int i = 0; i < doors.length; i++) {
                breakout = false;
                if (doors[i] != null) {
                    int freq[] = {0,0,0,11,2,1};
                    int freq_rev[] = {0,0,0,5,2,1};
                    int[] freq_final = new int[6];
                    if(reverse) {
                        freq_final= freq;
                        reverse = false;
                    } else {
                        freq_final=freq_rev;
                        reverse = true;
                    }
                    int x = RandomUtils.discrete(RANDOM,freq_final);
                    int y = RandomUtils.uniform(RANDOM,6,10);
                    int z;
                    if (i%2==RandomUtils.uniform(RANDOM,2)){
                        z = x;
                        x = y;
                        y = z;
                    }
                    Room room_n = genNeighbourRoom(doors[i], sideOfDoors[i], x, y,RANDOM);
                    for (int j = 0; j < room_n.corners.length; j++) {
                        if (room_n.corners[j].x < 0 ||
                                room_n.corners[j].x > world.length - 1 ||
                                room_n.corners[j].y < 0 ||
                                room_n.corners[j].y > world[0].length - 1){
                                breakout =true;
                                break;
                        }
                    }
                    if (!breakout) {
                        neighbour[i] = room_n;
                    } else{
                            int a = doors[i].x;
                            int b = doors[i].y;
                            System.out.println(b);
                            world[a][b] = Tileset.WALL;

                    }
                }
            }
        }
    }

    public static void drawDoors(TETile[][] world,Room room){
        for (int i = 0; i < room.doors.length; i++) {
            if (room.doors[i] != null) {
                int x = room.doors[i].x;
                int y = room.doors[i].y;
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
    //todo 记住上一步从哪个方向过来的 不要往回生成方块
    public static int drawAllRoomsRecursion(TETile[][] world,Room room,int cnt, Random RANDOM){
        int a = 0;
        if(cnt < RandomUtils.uniform(RANDOM,0, MaxRecursionDepth/2)){
            drawRoom(world,room);
            return 0;
        } else{
            drawRoom(world,room);
            room.genDoors(4);
            drawDoors(world,room);
            room.genAllNeighbourRooms(world,RANDOM);
            for (int i = 0; i < room.neighbour.length; i++) {
                if (room.neighbour[i] != null) {
                    a += drawAllRoomsRecursion(world, room.neighbour[i],cnt - 1,RANDOM);
                }
            }
            return a;
        }
    }

    public static void drawRectangle(TETile[][] world,Rectangle r, TETile texture){
        for (int x = r.position.x; x < r.position.x + r.width; x += 1) {
            for (int y = r.position.y; y < r.position.y + r.height; y += 1) {
                if (world[x][y] == Tileset.FLOOR && texture == Tileset.WALL){
                    world[x][y] = Tileset.FLOOR;
                } else{
                    world[x][y] = texture;
                }
            }
        }
    }

    public static void drawRoom(TETile[][] world,Room r){
        drawRectangle(world,r.outer_r,Tileset.WALL);
        drawRectangle(world,r.inner_r,Tileset.FLOOR);
    }


    public static boolean isEscape(TETile[][] world, int x, int y){
        if ((world[x-1][y] == Tileset.NOTHING || world[x+1][y] == Tileset.NOTHING ||
                world[x][y-1] == Tileset.NOTHING || world[x][y+1] == Tileset.NOTHING) &&
                (world[x-1][y] == Tileset.FLOOR || world[x+1][y] == Tileset.FLOOR ||
                world[x][y-1] == Tileset.FLOOR || world[x][y+1] == Tileset.FLOOR)){
            return true;
        } else {
            return false;
        }
    }

    public static void drawEscape(TETile[][] world){
        boolean isWall = false;
        while (!isWall) {
            int x = RandomUtils.uniform(RANDOM, world.length);
            int y = RandomUtils.uniform(RANDOM, world[1].length);
            if (world[x][y] == Tileset.WALL && isEscape(world,x,y)) {
                world[x][y] = Tileset.LOCKED_DOOR;
                doorPos = new Pos(x,y);
                break;
            }
        }
    }


    public static void drawKey(TETile[][] world){
        boolean isFloor = false;
        while (!isFloor) {
            int x = RandomUtils.uniform(RANDOM, world.length);
            int y = RandomUtils.uniform(RANDOM, world[1].length);
            if (world[x][y] == Tileset.FLOOR) {
                world[x][y] = Tileset.FLOWER;
                keyPos = new Pos(x,y);
                break;
            }
        }
    }

    public static void genBackGround(TETile[][] world,Rectangle r){
        drawRectangle(world,r,Tileset.NOTHING);
    }

    //利用递归生成room和way

    public static TETile[][] genWorld(long seed,int w, int h) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        SEED = seed;
        RANDOM= new Random(SEED);
        int width = w;
        int height = h;
        Pos origin = new Pos(0,0);
        Rectangle canvas = new Rectangle(origin,width,height);
        Pos R1 = new Pos(60,30);


        // initialize tiles
        TETile[][] world = new TETile[width][height];
        genBackGround(world, canvas);
        // Add rect
        Rectangle rec1 = new Rectangle(R1,4,5);
        Room r1 = new Room(rec1,RANDOM);
        drawRoom(world,r1);
        r1.genDoors(2);

        r1.genAllNeighbourRooms(world,RANDOM);
        drawDoors(world,r1);
        drawAllRoomsRecursion(world, r1,MaxRecursionDepth,RANDOM);
        drawEscape(world);
        drawKey(world);
        return world;
    }
}

