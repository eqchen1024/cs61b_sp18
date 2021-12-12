public class OffByOne implements CharacterComparator {
    @Override
    public boolean equalChars(char x, char y) {
        int xx = x;
        int yy = y;
        if ((xx - yy)*(xx - yy) == 1) {
            return true;
        }
        return false;
    }

}
