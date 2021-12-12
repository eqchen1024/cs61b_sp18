public class OffByN implements CharacterComparator{
    public int offset;
    public void OffByN(int n){
        offset = n;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int xx = x;
        int yy = y;
        if ((xx - yy)*(xx - yy) == offset) {
            return true;
        }
        return false;
    }
}
