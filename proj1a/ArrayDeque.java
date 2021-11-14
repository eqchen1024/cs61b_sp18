public class ArrayDeque<T> {
    private T[] items;
    private int size;
    private int nextFirst;
    private int nextLast;
    private int length;
    private int big_size = 16;
    private int resize_factor = 2;
    private double usage_ratio = 0.25;


    public ArrayDeque() {
        items = (T[]) new Object[8];
        size = 0;
        length = items.length;
        nextFirst = 0;
        nextLast = 1;

    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    private boolean require_sizeup() {
        return size == length;
    }

    private boolean require_sizedown() {
        return (size < (usage_ratio * length) && size >= big_size);
    }

    private int get_underlying_index(int index, int offset) {
        index += offset;
        if (index >= length) {
            index -= length;
        }
        if (index < 0) {
            index += length;
        }
        return index;
    }

    public T get(int index) {
        if (index >= size) {
            return null;
        } else {
            return items[get_underlying_index(nextFirst, 1 + index)];
        }
    }

    public void printDeque() {
        for (int i = 0; i < size; i++){
            System.out.print(String.valueOf(get(i)) + " ");
        }
    }

    private void reshapeItems(int newCapacity) {
        T[] new_array = (T[]) new Object[newCapacity];
        for (int i =0; i < size; i++) {
            new_array[i] = get(get_underlying_index(nextFirst, 1 + i));
        }
        length = newCapacity;
        items = new_array;
        nextFirst = newCapacity -1;
        nextLast = size;
    }
    private void resize_up() {
        reshapeItems(length *= resize_factor);

    }
    private void resize_down() {
        reshapeItems(length /= resize_factor);
    }


    public void addFirst(T item) {
        if (require_sizeup()) {
            resize_up();
        }
        items[nextFirst] = item;
        nextFirst = get_underlying_index(nextFirst, -1);
        size += 1;
    }

    public void addLast(T item) {
        if (require_sizeup()) {
            resize_up();
        }
        items[nextLast] = item;
        nextLast = get_underlying_index(nextLast, 1);
        size += 1;
    }


    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        nextLast = get_underlying_index(nextLast, -1);
        T last = items[nextLast];
        items[nextLast] = null;
        size -= 1;
        if (require_sizedown()) {
            require_sizedown();
        }
        return last;
    }

    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        nextFirst = get_underlying_index(nextFirst, 1);
        T first = items[nextFirst];
        items[nextFirst] = null;
        size -= 1;
        if (require_sizedown()) {
            resize_down();
        }
        return first;
    }
}
