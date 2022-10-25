package lab11.graphs;
import java.util.*;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return Math.abs(maze.toX(v) - maze.toX(t)) + Math.abs(maze.toY(v) - maze.toY(t));
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }

    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        Comparator compareByValueComparator = new Comparator<Map.Entry<Integer, Integer>>() {
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
               return o1.getValue().compareTo(o2.getValue());
            }
        };

        PriorityQueue<Map.Entry<Integer, Integer>> priorityQueue = new PriorityQueue<Map.Entry<Integer, Integer>>(compareByValueComparator);
        int[] estimateDistance = new int[t+1];
        Map.Entry entry;
        for (int i =0; i < t;i++){
            if (i == s) {
                entry = new AbstractMap.SimpleEntry(i,0);
            }else{
                entry = new AbstractMap.SimpleEntry(i,Integer.MAX_VALUE);
            }
            priorityQueue.add(entry);
            estimateDistance[i] = h(i);
        }
        while (!priorityQueue.isEmpty()) {
            Map.Entry node = priorityQueue.poll();
            int v = (int) node.getKey();
            marked[v] = true;
            announce();
            if (v==t){
                break;
            }
            for (int w : maze.adj(v)) {
                if (marked[w] == true){
                    continue;
                }
                if (distTo[v] + 1 < distTo[w]){
                    distTo[w] = distTo[v] + 1;
                    edgeTo[w] = v;
                    priorityQueue.removeIf(tmp -> tmp.getKey() == w);
                    int newTotalDistance = distTo[w] + estimateDistance[w];
                    priorityQueue.add(new AbstractMap.SimpleEntry(w,newTotalDistance));
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

