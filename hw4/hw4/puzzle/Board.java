package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

public class Board implements WorldState {
    public int[][] tilesArray;
    private int N;

    public Board(int[][] tiles) {
        N = tiles.length;
        tilesArray = new int[N][N];
        for (int i=0;i<N;i++){
            for (int j=0;j<N;j++){
                tilesArray[i][j]=tiles[i][j];
            }
        };
    }

    public int tileAt(int i, int j) {
        if (i >= 0 && i < size() && j >= 0 && j < size()) {
            return tilesArray[i][j];
        }
        else{
            throw new IndexOutOfBoundsException();
        }
    }
    public int size() {
        return  N;
    }
    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }
    public int hamming() {
        int counter = 0;
        int sum =0;
        for (int k=0;k<size();k++){
            for (int l=0;l<size();l++){
                if (tileAt(k,l) == 0){
                    counter += 1;
                    continue;
                }
                if (tileAt(k,l) - counter != 0) {
                    sum += 1;
                }
                counter += 1;
                }
            }
        return sum;
    }
    private int[] getTargetPos(int value){
        int[] res = new int[2];
        res[0] = (value - 1) / size();
        res[1] = (value - 1) % size();
        return res;
    }
    public int manhattan() {
        int dis = 0;
        for (int m = 0; m < size(); m++) {
            for (int n = 0; n < size(); n++) {
                if (tileAt(m,n) ==0){
                    continue;
                }
                int[] tagetdisList = getTargetPos(tileAt(m, n));
                dis += Math.abs(m - tagetdisList[0]) + Math.abs(n - tagetdisList[1]);
            }
        }
        return dis;
    }


    public int estimatedDistanceToGoal() {
         return manhattan();
    }

    @Override
    public boolean equals(Object y) {
        Board yy = (Board) y;
        for (int o = 0; o < size(); o++) {
            for (int p = 0; p < size(); p++) {
                if (this.tileAt(o, p) - yy.tileAt(o, p) != 0) {
                    return false;
                }
            }
        }
        return true;
    }
}




