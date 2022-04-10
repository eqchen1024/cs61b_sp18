package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.Random;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    private static final long SEED = 2873123;
    private static final Random RANDOM = new Random(SEED);
    public static class Pos{
        public int posX;
        public int posY;
        public Pos(int posX, int posY){
            this.posX = posX;
            this.posY = posY;
        }
    }

    public static int[][] hexAnchor(int leftDownX, int size){
        int rowsToFill = size * 2;
        int[][] hexInfo = new int[2][rowsToFill];
        for (int i = 0; i < rowsToFill; i++){
            if (i < size){
                hexInfo[0][i] = leftDownX - i;
                hexInfo[1][i] = size + 2 * i;
            } else{
                hexInfo[0][i] = leftDownX - (rowsToFill- i -1);
                hexInfo[1][i] = size + 2 * (rowsToFill- i - 1);
            }

        }
        return hexInfo;
    }
    public static void addHexagon(TETile[][] world,int hexSize, Pos position,TETile texture) {
        int[][] posInfo =  HexWorld.hexAnchor(position.posX,hexSize);
        int[] rowStartX= posInfo[0];
        int[] rowLength= posInfo[1];
        for (int i = 0; i < 2 * hexSize; i++){
            for (int j=0; j < rowLength[i]; j++){
                world[rowStartX[i]+j][position.posY+i] = texture;
            }
        }
    }
    public static class Hex{
        public Pos posLD;
        public int size;
        public Pos posRB;
        public Pos posRD;
        public Hex(Pos posLD,int size){
            this.posLD = posLD;
            this.size = size;
            this.posRB = new Pos(this.posLD.posX + 2 * this.size - 1,this.posLD.posY + size);
            this.posRD = new Pos(this.posLD.posX + 2 * this.size - 1,this.posLD.posY - size);
        }

    }

    public static Hex addRightUpHex(TETile[][] world,int hexSize,TETile texture,Hex baseHex){
        int posX = baseHex.posRB.posX ;
        int posY = baseHex.posRB.posY;
        Pos rightUpPos = new Pos(posX,posY);
        Hex rightUpHex = new Hex(rightUpPos,3);
        addHexagon(world,hexSize,rightUpHex.posLD,texture);
        return rightUpHex;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(5);
        switch (tileNum) {
            case 0: return Tileset.WALL;
            case 1: return Tileset.FLOWER;
            case 2: return Tileset.NOTHING;
            case 3: return Tileset.TREE;
            case 4: return Tileset.LOCKED_DOOR;
            default: return Tileset.NOTHING;
        }
    }

    public static Hex addRightDownHex(TETile[][] world,int hexSize,TETile texture,Hex baseHex){
        int posX = baseHex.posRD.posX ;
        int posY = baseHex.posRD.posY;
        Pos rightDownPos = new Pos(posX,posY);
        Hex rightDownHex = new Hex(rightDownPos,3);
        addHexagon(world,hexSize,rightDownHex.posLD,texture);
        return rightDownHex;
    }

    public static int recursiveDraw(TETile[][] world,int hexSize,Hex baseHex,int cnt){
        if (cnt == 0){
            return 0;
        }
        Hex RU = HexWorld.addRightUpHex(world,hexSize,randomTile(),baseHex);
        Hex RD = HexWorld.addRightDownHex(world,hexSize,randomTile(),baseHex);
        int a = HexWorld.recursiveDraw(world,hexSize,RU,cnt-1);
        int b = HexWorld.recursiveDraw(world,hexSize,RD,cnt-1);
        return a + b;
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(50, 50);

        // initialize tiles
        TETile[][] world = new TETile[50][50];
        for (int x = 0; x < 50; x += 1) {
            for (int y = 0; y < 50; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        Pos hexLD = new Pos(20,20);
        Hex one = new Hex(hexLD,3);
        HexWorld.addHexagon(world,3,hexLD,Tileset.FLOWER);
//        Hex two = HexWorld.addRightUpHex(world,3,Tileset.MOUNTAIN,one);
//        HexWorld.addRightDownHex(world,3,Tileset.GRASS,one);
        HexWorld.recursiveDraw(world,3,one,3);
        ter.renderFrame(world);

    }
}
