public class UnionFind implements DisjointSets{
    private int[] id;

    public UnionFind(int n) {
        id = new int[n];
        for (int i = 0; i < n ; i++) {
            id[i] = -1;
        }
    }

    public void validate(int v1) {
        if (v1 >= 0 && v1 < id.length){
            System.out.println("");
        } else {
            throw new IllegalArgumentException("not valid index");
        }
    }

    public int find(int v1) {
        if (parent(v1) >= 0) {
            int root = find(parent(v1));
            id[v1] = root;
            return root;
        } else {
            return v1;
        }
    }

    public int sizeOf(int v1) {
        int size = - id[find(v1)];
        return size;
    }

    public int parent(int v1) {
        validate(v1);
        return id[v1];
    }

    @Override
    public void union(int v1, int v2) {
        if (isConnected(v1, v2)) {
            return;
        }
        int rootV1 = find (v1);
        int rootV2 = find (v2);
        int sizeV1 = sizeOf(v1);
        int sizeV2 = sizeOf(v2);
        if (sizeV1 <= sizeV2) {
            id[rootV1] = rootV2;
            id[rootV2] = - sizeV1 -sizeV2;
        } else {
            id[rootV2] = rootV1;
            id[rootV1] = - sizeV1 -sizeV2;
        }
    }

    @Override
    public boolean isConnected(int v1, int v2) {
        return (find(v1) == find(v2));
    }
}
