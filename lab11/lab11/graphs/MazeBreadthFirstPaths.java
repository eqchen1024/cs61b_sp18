package lab11.graphs;
import java.util.Queue;
import java.util.LinkedList;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs(Queue<Integer> queue, int v) {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()

        queue.add(v);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            marked[node] = true;
            announce();

            if (node == t) {
                targetFound = true;
            }

            if (targetFound) {
                return;
            }
            for (int w : maze.adj(node)){
                if (!marked[w]) {
                    edgeTo[w] = node;
                    distTo[w] = distTo[node] + 1;
                    queue.add(w);
                }
            }
        }
    }


    @Override
    public void solve() {
        Queue<Integer> queue = new LinkedList<Integer>();
        bfs(queue,s);
    }
}

