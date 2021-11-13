/** Array based list.
 *  @author Josh Hug
 */

public class AList {
    public int[] array;
    public int curr_idx = 0;
    public int sizeLimit = 100;
    /** Creates an empty list. */
    public AList() {
        array = new int[100];
    }

    /** Inserts X into the back of the list. */
    public void addLast(int x) {
        if (curr_idx < sizeLimit){
            array[curr_idx] = x;
            curr_idx += 1;
        }else{
            int[] b = new int[size+1];
            System.arraycopy(array, 0, b, 0, size);
            b[size] = i;
            array = b;
            size += 1;
        }

    }

    /** Returns the item from the back of the list. */
    public int getLast() {
        return array[curr_idx - 1];
    }
    /** Gets the ith item in the list (0 is the front). */
    public int get(int i) {
        return array[i];
    }

    /** Returns the number of items in the list. */
    public int size() {
        return curr_idx;
    }

    /** Deletes item from back of the list and
     * returns deleted item. */
    public int removeLast() {
        int lastItem = array[curr_idx -1];
        array[curr_idx -1] = 0;
        curr_idx -= 1;
        return array[curr_idx -1];
    }
    public static void main(String[] args){
        AList a = new AList();
        a.addLast(1);
        System.out.println(a.getLast());
        a.addLast(2);
        System.out.println(a.getLast());
        a.addLast(3);
        System.out.println(a.getLast());
        a.removeLast();
        System.out.println(a.getLast());

    }
}