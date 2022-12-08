import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import edu.princeton.cs.algs4.MinPQ;

/**
 * This class provides a shortestPath method for finding routes between two points
 * on the map. Start by using Dijkstra's, and if your code isn't fast enough for your
 * satisfaction (or the autograder), upgrade your implementation by switching it to A*.
 * Your code will probably not be fast enough to pass the autograder unless you use A*.
 * The difference between A* and Dijkstra's is only a couple of lines of code, and boils
 * down to the priority you use to order your vertices.
 */
public class Router {
    /**
     * Return a List of longs representing the shortest path from the node
     * closest to a start location and the node closest to the destination
     * location.
     * @param g The graph to use.
     * @param stlon The longitude of the start location.
     * @param stlat The latitude of the start location.
     * @param destlon The longitude of the destination location.
     * @param destlat The latitude of the destination location.
     * @return A list of node id's in the order visited on the shortest path.
     */
    public static List<Long> shortestPath(GraphDB g, double stlon, double stlat,
                                          double destlon, double destlat) {

        GraphDB.Node startNode = g.getNode(g.closest(stlon,stlat));
        GraphDB.Node endNode = g.getNode(g.closest(destlon,destlat));
        List<Long> path= aStar(g,startNode,endNode);
        return path;

    }

    static class SearchNode implements Comparable<SearchNode>{
        public GraphDB.Node node;
        public SearchNode prev;
        public double priority = Double.MAX_VALUE;
        public SearchNode (GraphDB.Node nd,SearchNode p) {
            node = nd;
            prev = p;
        }
        @Override
        public int compareTo(SearchNode o) {
            return (int) Math.signum(this.priority - o.priority);
        }
    }

    public static List<Long> aStar(GraphDB g, GraphDB.Node startNode,GraphDB.Node endNode) {
        MinPQ<SearchNode> pq = new MinPQ<>();
        List<Long> path = new LinkedList<>();
        Set<Long> markedIds = new HashSet<>();
        Map<Long,Double> best = new HashMap<>();
        SearchNode startSearchNode = new SearchNode(startNode, null);
        pq.insert(startSearchNode);
        best.put(startNode.id, 0.0);
        while (!pq.isEmpty()) {
            SearchNode curSearchNode = pq.delMin();
            GraphDB.Node curNode = curSearchNode.node;
            if (markedIds.contains(curNode.id)) {
                continue;
            }
            if (curNode.id == endNode.id) {
                List<Long> tmp_path = new ArrayList<>();
                tmp_path.add(endNode.id);
                SearchNode outPutNode = curSearchNode;
                while (outPutNode.prev != null) {
                    tmp_path.add(outPutNode.prev.node.id);
                    outPutNode = outPutNode.prev;
                }
                for (int i = tmp_path.size() - 1 ; i >= 0; i--){
                    path.add(tmp_path.get(i));
                }
                break;
            }
            markedIds.add(curNode.id);
            for (Long nodeID : g.adjacent(curNode.id)) {
                GraphDB.Node nd = g.getNode(nodeID);
                if (!best.containsKey(nodeID) || best.get(nodeID) > best.get(curNode.id) + g.distance(curNode.id, nodeID) ) {
                    SearchNode nodeToInsert = new SearchNode(nd,curSearchNode);
                    best.put(nodeID,best.get(curNode.id) + g.distance(curNode.id, nodeID));
                    nodeToInsert.priority = best.get(curNode.id) + g.distance(curNode.id, nodeID) + g.distance(nodeID,endNode.id);
                    pq.insert(nodeToInsert);
                }
            }
        }
        return path;
    }

    /**
     * Create the list of directions corresponding to a route on the graph.
     * @param g The graph to use.
     * @param route The route to translate into directions. Each element
     *              corresponds to a node from the graph in the route.
     * @return A list of NavigatiionDirection objects corresponding to the input
     * route.
     */
    public static List<NavigationDirection> routeDirections(GraphDB g, List<Long> route) {
        List<NavigationDirection> NaviList = new LinkedList<>();
        // need 3 pointer to assert whether we are now on a new road.
        Long curPointer = -1l;
        Long prevPointer = -1l;
        Long prevPrevPointer = -1l;
        NavigationDirection tempND = new NavigationDirection();
        for (Long curId : route) {
            // move pointers forward
            prevPrevPointer = prevPointer;
            prevPointer = curPointer;
            curPointer = curId;
            if (prevPrevPointer == -1l) {
                tempND.distance = 0;
                tempND.direction = NavigationDirection.START;
                if (prevPointer != -1l) {
                    // move on the first road
                    tempND.distance += g.distance(prevPointer,curPointer);
                    // the start point might be an intersection !
                    tempND.way = g.getWay(commonElement(g.getNode(curPointer).ways,
                            g.getNode(prevPointer).ways)).tags.get("name");
                }
                continue;
            }

            // each 2 pointers use to determine which road,these 2 pointers are on.
            // compare 2 pairs of pointers to see where we have changed to a new road.
            Long curWay = commonElement(g.getNode(curPointer).ways,g.getNode(prevPointer).ways);
            Long prevWay = commonElement(g.getNode(prevPointer).ways,g.getNode(prevPrevPointer).ways);

            if (g.getWay(curWay).tags.get("name").equals(g.getWay(prevWay).tags.get("name"))) {
                tempND.distance += g.distance(prevPointer,curPointer);
                tempND.way = g.getWay(curWay).tags.get("name");
            } else {
                NaviList.add(tempND);
                tempND = new NavigationDirection();
                tempND.distance = g.distance(prevPointer,curPointer);
                tempND.way = g.getWay(curWay).tags.get("name");
                // use relative bearing
                tempND.direction = bearingToDirection(relBearingAdjust(g.bearing(prevPointer,curPointer)
                        ,g.bearing(prevPrevPointer,prevPointer)));
            }
        }
        NaviList.add(tempND);
        return NaviList;
    }

    public static double relBearingAdjust(Double b1, Double b2) {
        double diff = b1 - b2;
        // may over range
        if (diff < -180) {
            return diff +360;
        }
        if (diff > 180) {
            return diff - 360;
        }
        return diff;
    }

    // calculate the road which the 2 nodes are on
    public static Long commonElement(Set<Long> s1, Set<Long> s2) {
        Long res = -1L;
        for (Long id : s1) {
            if (s2.contains(id)) {
                res =id;
                break;
            }
        }
        return res;
    }

    public static int bearingToDirection(double bearing) {
        if (Math.abs(bearing) <= 15) {
            return NavigationDirection.STRAIGHT;
        }
        if (Math.abs(bearing) <= 30 && bearing < 0) {
            return NavigationDirection.SLIGHT_LEFT;
        }
        if (Math.abs(bearing) <= 30 && bearing > 0) {
            return NavigationDirection.SLIGHT_RIGHT;
        }
        if (Math.abs(bearing) <= 100 && bearing < 0) {
            return NavigationDirection.LEFT;
        }
        if (Math.abs(bearing) <= 100 && bearing > 0) {
            return NavigationDirection.RIGHT;
        }
        if (Math.abs(bearing) > 100 && bearing < 0) {
            return NavigationDirection.SHARP_LEFT;
        }
        return NavigationDirection.SHARP_RIGHT;

    }

    /**
     * Class to represent a navigation direction, which consists of 3 attributes:
     * a direction to go, a way, and the distance to travel for.
     */
    public static class NavigationDirection {

        /** Integer constants representing directions. */
        public static final int START = 0;
        public static final int STRAIGHT = 1;
        public static final int SLIGHT_LEFT = 2;
        public static final int SLIGHT_RIGHT = 3;
        public static final int RIGHT = 4;
        public static final int LEFT = 5;
        public static final int SHARP_LEFT = 6;
        public static final int SHARP_RIGHT = 7;

        /** Number of directions supported. */
        public static final int NUM_DIRECTIONS = 8;

        /** A mapping of integer values to directions.*/
        public static final String[] DIRECTIONS = new String[NUM_DIRECTIONS];

        /** Default name for an unknown way. */
        public static final String UNKNOWN_ROAD = "unknown road";
        
        /** Static initializer. */
        static {
            DIRECTIONS[START] = "Start";
            DIRECTIONS[STRAIGHT] = "Go straight";
            DIRECTIONS[SLIGHT_LEFT] = "Slight left";
            DIRECTIONS[SLIGHT_RIGHT] = "Slight right";
            DIRECTIONS[LEFT] = "Turn left";
            DIRECTIONS[RIGHT] = "Turn right";
            DIRECTIONS[SHARP_LEFT] = "Sharp left";
            DIRECTIONS[SHARP_RIGHT] = "Sharp right";
        }

        /** The direction a given NavigationDirection represents.*/
        int direction;
        /** The name of the way I represent. */
        String way;
        /** The distance along this way I represent. */
        double distance;

        /**
         * Create a default, anonymous NavigationDirection.
         */
        public NavigationDirection() {
            this.direction = STRAIGHT;
            this.way = UNKNOWN_ROAD;
            this.distance = 0.0;
        }

        public String toString() {
            return String.format("%s on %s and continue for %.3f miles.",
                    DIRECTIONS[direction], way, distance);
        }

        /**
         * Takes the string representation of a navigation direction and converts it into
         * a Navigation Direction object.
         * @param dirAsString The string representation of the NavigationDirection.
         * @return A NavigationDirection object representing the input string.
         */
        public static NavigationDirection fromString(String dirAsString) {
            String regex = "([a-zA-Z\\s]+) on ([\\w\\s]*) and continue for ([0-9\\.]+) miles\\.";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(dirAsString);
            NavigationDirection nd = new NavigationDirection();
            if (m.matches()) {
                String direction = m.group(1);
                if (direction.equals("Start")) {
                    nd.direction = NavigationDirection.START;
                } else if (direction.equals("Go straight")) {
                    nd.direction = NavigationDirection.STRAIGHT;
                } else if (direction.equals("Slight left")) {
                    nd.direction = NavigationDirection.SLIGHT_LEFT;
                } else if (direction.equals("Slight right")) {
                    nd.direction = NavigationDirection.SLIGHT_RIGHT;
                } else if (direction.equals("Turn right")) {
                    nd.direction = NavigationDirection.RIGHT;
                } else if (direction.equals("Turn left")) {
                    nd.direction = NavigationDirection.LEFT;
                } else if (direction.equals("Sharp left")) {
                    nd.direction = NavigationDirection.SHARP_LEFT;
                } else if (direction.equals("Sharp right")) {
                    nd.direction = NavigationDirection.SHARP_RIGHT;
                } else {
                    return null;
                }

                nd.way = m.group(2);
                try {
                    nd.distance = Double.parseDouble(m.group(3));
                } catch (NumberFormatException e) {
                    return null;
                }
                return nd;
            } else {
                // not a valid nd
                return null;
            }
        }

        @Override
        public boolean equals(Object o) {
            if (o instanceof NavigationDirection) {
                return direction == ((NavigationDirection) o).direction
                    && way.equals(((NavigationDirection) o).way)
                    && distance == ((NavigationDirection) o).distance;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return Objects.hash(direction, way, distance);
        }
    }
}
