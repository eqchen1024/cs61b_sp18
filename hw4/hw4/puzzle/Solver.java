package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Solver {
    public class SearchNode implements Comparable<SearchNode> {
        public WorldState state;
        public int moves;
        public SearchNode preNode;

        public SearchNode(WorldState s,int m, SearchNode p){
            state = s;
            moves = m;
            preNode = p;
        }

        @Override
        public int compareTo(SearchNode o){
            return (this.moves + this.getEstDis()) - (o.moves + o.getEstDis());
        }
        // caching
        private int getEstDis(){
            if (!cache.containsKey(this.state)){
                cache.put(this.state,this.state.estimatedDistanceToGoal());
            }
                return cache.get(this.state);

        }

    }
    public MinPQ<SearchNode> pq  = new MinPQ<>();
    public int totalMoves = 0;
    public List<WorldState> path = new LinkedList<>();
    public Map<WorldState,Integer> cache = new HashMap<>();

    public Solver(WorldState initial){
        SearchNode startNode = new SearchNode(initial,0,null);
        pq.insert(startNode);
        while (!pq.isEmpty()){
            SearchNode poppedNode = pq.delMin();
            WorldState poppedState = poppedNode.state;
            if (poppedState.isGoal()){
                SearchNode tmpNode = poppedNode;
                totalMoves = poppedNode.moves;
                path.add(tmpNode.state);
                while (tmpNode.preNode != null){
                    tmpNode = tmpNode.preNode;
                    path.add(tmpNode.state);
                }
                return;
            }
            for (WorldState neighbour:poppedState.neighbors()){
                // 比较的state的时候用equal 不要用等号
                if (poppedNode.preNode == null || !neighbour.equals(poppedNode.preNode.state)) {
                    SearchNode neighbourNode = new SearchNode(neighbour,poppedNode.moves + 1,poppedNode);
                    pq.insert(neighbourNode);
                }
            }
        }


    }
    public int moves() {
        return totalMoves;
    }
    public Iterable<WorldState> solution(){
        List<WorldState> res = new LinkedList<>();
        for (int i = path.size() - 1 ; i >= 0; i--){
            res.add(path.get(i));
        }
        return res;
    }
}