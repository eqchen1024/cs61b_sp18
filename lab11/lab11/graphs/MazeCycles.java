package lab11.graphs;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private boolean targetFound = false;
    private boolean needLine = true;
    private Maze maze;
    public MazeCycles(Maze m) {
        super(m);
        maze = m;
        int sourceX = 1;
        int sourceY = 1;
        int targetX = maze.N();
        int targetY = maze.N();
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        dfsDetector(s,s);
    }

    // Helper methods go here
    public int dfsDetector(int v, int u) {
        marked[v] = true;
        announce();
        int loop_point = -1;
        for (int w : maze.adj(v)) {
            if (marked[w] && w != u) {
                targetFound = true;
                edgeTo[w] = v;
                announce();
            }
            if (marked[w] && w == u) {
                continue;
            }
            if (targetFound) {
                return w;
            } else {
                loop_point = dfsDetector(w, v);
                if (loop_point == v){
                    edgeTo[w] = v;
                    announce();
                    needLine = false;
                }
                if (needLine == true){
                    edgeTo[w] = v;
                    announce();
                }
            }
        }
        return loop_point;
    }
}





