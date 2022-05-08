public interface DisjointSets {
    /** connects two items P and Q */

    void union(int v1, int v2);

    /** checks to see if two items are connected */
    boolean isConnected(int v1, int v2);
}