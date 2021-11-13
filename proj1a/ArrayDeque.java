public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int length;

    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        length = items.length;
    }

    private void resize_up() {
        length = size * 4;
        T[] newArray = (T[]) new Object[length];
//        System.out.println(size);
//        System.out.println(nextFirst);
//        System.out.println(nextLast);
        System.arraycopy(items, 0, newArray, 0, nextLast);
        System.arraycopy(items, nextFirst + 1, newArray,
                length - (size - nextFirst - 1), size - nextLast);
        items = newArray;
        nextFirst = length - (size - nextFirst - 1) - 1;
//        System.out.println(nextFirst);
//        System.out.println(nextLast);

    }
    private void resize_down() {
        T[] newArray = (T[]) new Object[size];
        if (nextLast > nextFirst) {
            System.arraycopy(items, nextFirst + 1,
                    newArray, 0, size);
        } else {
            System.arraycopy(items, nextFirst + 1, newArray, 0,length - nextFirst - 1);
            System.arraycopy(items, 0, newArray,length - nextFirst - 1, nextLast);
        }
        items = newArray;
        nextFirst = size -1;
        nextLast = 0;
        length = size;
//        System.out.println(items);
    }


    public void addFirst(T item) {
        if (size == length) {
            resize_up();
        }
        items[nextFirst] = item;
        if (nextFirst != 0) {
            nextFirst -= 1;
        } else {
            nextFirst -= 1;
            nextFirst += length;
        }
        size += 1;
    }

    public void addLast(T item) {
        if (size == length) {
            resize_up();
        }
        items[nextLast] = item;
        if (nextLast != length - 1) {
            nextLast += 1;
        } else {
            nextLast += 1;
            nextLast -= length;
        }
        size += 1;
    }

    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }

    public int size() {

        return size;
    }

    public void printDeque() {
        if (nextFirst < nextLast - 1) {
            for (int i = nextFirst + 1; i < nextLast; i++) {
                System.out.print(String.valueOf(items[i]) + ' ');
            }
        } else {
            for (int i = nextFirst + 1; i < length; i++) {
                System.out.print(String.valueOf(items[i]) + ' ');
            }
            for (int i = 0; i < nextLast; i++) {
                System.out.print(String.valueOf(items[i]) + ' ');
            }
        }
    }

    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T toRemove;
        if (nextLast != 0) {
            toRemove = items[nextLast - 1];
            nextLast -= 1;
        } else {
            nextLast -= 1;
            nextLast += length;
            toRemove = items[length - 1];
        }
        size -= 1;
        if (size / length < 0.25) {
            resize_down();
        }
        return toRemove;

    }

    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T toRemove;
        if (nextFirst != length - 1) {
            toRemove = items[nextFirst + 1];
            nextFirst += 1;
        } else {
            toRemove = items[0];
            nextFirst = 0;
        }
        size -= 1;
        if ( length > 4 * size) {
//            System.out.println(size);
//            System.out.println(length);
//            System.out.println("Downsize");
            resize_down();
//            System.out.println(length);
//            System.out.println(size);
        }
        return toRemove;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        }
        if (index + nextFirst + 1 < length) {
            return items[index + nextFirst + 1];
        } else {
            return items[index + nextFirst + 1 - length];
        }
    }
/**
    public static void main(String[] args) {
        ArrayDeque a = new ArrayDeque();
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.addFirst("aa");
        a.removeFirst();
        a.removeFirst();
        a.removeFirst();
        a.printDeque();
    } */
}
