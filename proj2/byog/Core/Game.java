package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;


public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 100;
    public static final int HEIGHT = 60;


    public void drawTitle(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear(Color.BLACK);
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(WIDTH * 0.5 ,HEIGHT * 0.7, "CS61B The game");
        StdDraw.text(WIDTH * 0.5 ,HEIGHT * 0.6, "New Game (N)");
        StdDraw.text(WIDTH * 0.5 ,HEIGHT * 0.5, "Load Game (L)");
        StdDraw.text(WIDTH * 0.5 ,HEIGHT * 0.4, "Quit Game (Q)");
        StdDraw.text(WIDTH * 0.5 ,HEIGHT * 0.3, s);
        StdDraw.show();

    }

    public String ListenUserCommand(int n) {
        //TODO: Read n letters of player input
        String userAnswer = "";
        StdDraw.setPenColor(StdDraw.WHITE);
        while (userAnswer.length() < n){
            if(StdDraw.hasNextKeyTyped()){
                userAnswer = userAnswer + StdDraw.nextKeyTyped();
                drawTitle(userAnswer);
                StdDraw.show();
            }
        }
        drawTitle(userAnswer);
        StdDraw.show();
        StdDraw.pause(1000);
        return userAnswer;
    }

    public long getSeed(String endSignal) {
        String singal = endSignal.toLowerCase();
        String userAnswer = "";
        StdDraw.setPenColor(StdDraw.WHITE);
        while (true){
            if(StdDraw.hasNextKeyTyped() == true){
                char nextCommand = StdDraw.nextKeyTyped();
                if(!("" + nextCommand).equals(singal)){
                    userAnswer = userAnswer + nextCommand;
                    drawTitle(userAnswer);
                    StdDraw.show();
                } else {
                    break;
                }
            }
        }
        System.out.println(userAnswer);
        return Long.parseLong(userAnswer);
    }

    public void play(TETile[][] world, Random random){
        Player p1 = new Player();
        p1.initialize(world,random);
        ter.renderFrame(world);
    }


    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        // design the main menu
        ter.initialize(Game.WIDTH,Game.HEIGHT);
        drawTitle("");
        String command = ListenUserCommand(1);
        TETile[][] world = null;
        if (command.equals("n")) {
            System.out.println("new game start");
            drawTitle("Please Enter a seed");
            StdDraw.pause(1000);
            long seed = getSeed("s");
            world = WorldGeneration.genWorld(seed, Game.WIDTH,Game.HEIGHT);
            ter.renderFrame(world);
            StdDraw.pause(1000);
            play(world,new Random(seed));
        } else if (command.equals("l")) {
            System.out.println("load game start");
//            world = WorldGeneration.genWorld(seed, Game.WIDTH,Game.HEIGHT);
        } else if (command.equals("q")) {
            System.out.println("quit");
//            world = WorldGeneration.genWorld(seed, Game.WIDTH,Game.HEIGHT);
        }


    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        ter.initialize(Game.WIDTH,Game.HEIGHT);
        // extract commend and seed
        String inputLowerCase = input.toLowerCase();
        String[] inputArray = inputLowerCase.split("");
        String firstCommand = inputArray[0];
        String[] menuConfig = inputLowerCase.split("s");
        String seedStr = menuConfig[0].replaceAll("[a-z]", "");
        long seed = Long.parseLong(seedStr);
        System.out.println(seed);
        TETile[][] world = null;
        if (firstCommand.equals("n")) {
            System.out.println("new game start");
            world = WorldGeneration.genWorld(seed, Game.WIDTH,Game.HEIGHT);

        } else if (firstCommand.equals("l")) {
            System.out.println("load game start");
            world = WorldGeneration.genWorld(seed, Game.WIDTH,Game.HEIGHT);
        } else if (firstCommand.equals("q")) {
            System.out.println("quit");
            world = WorldGeneration.genWorld(seed, Game.WIDTH,Game.HEIGHT);
        }
        //show the world
        ter.renderFrame(world);
        return world;
    }
}

