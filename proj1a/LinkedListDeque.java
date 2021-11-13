public class LinkedListDeque<T> {
    private int size;
    private IntNode sentinel;

    public LinkedListDeque() {
        sentinel = new IntNode((T) "-518273", null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }


    private class IntNode {
        private T item;
        private IntNode next;
        private IntNode prev;
        public IntNode(T i, IntNode n, IntNode p) {
            prev = p;
            item = i;
            next = n;
        }
    }
    public void addFirst(T item) {
        IntNode newFirst = new IntNode(item, sentinel.next, sentinel);
        sentinel.next.prev = newFirst;
        sentinel.next = newFirst;
        size += 1;
    }
    public void addLast(T item) {
        IntNode newLast = new IntNode(item, sentinel, sentinel.prev);
        sentinel.prev.next = newLast;
        sentinel.prev = newLast;
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
        int cnt = 0;
        IntNode toPrint = sentinel.next;
        while (cnt < size) {
            System.out.print(String.valueOf(toPrint.item) + ' ');
            cnt += 1;
        }
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        IntNode toRemove = sentinel.prev;
        toRemove.prev.next = sentinel;
        sentinel.prev = toRemove.prev;
        toRemove.prev = null;
        toRemove.next = null;
        size -= 1;
        return toRemove.item;
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        IntNode toRemove = sentinel.next;
        sentinel.next = toRemove.next;
        toRemove.next.prev = sentinel;
        toRemove.prev = null;
        toRemove.next = null;
        size -= 1;
        return toRemove.item;
    }
    public T get(int index) {
        if (size == 0) {
            return null;
        }
        int cnt = 0;
        IntNode toCnt = sentinel.next;
        while (cnt < index) {
            cnt += 1;
            toCnt = toCnt.next;
        }
        return toCnt.item;
    }
    public T getRecursive(int index) {
        if (size == 0) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
    private T getRecursiveHelper(int index, IntNode cur) {
        if (index == 0) {
            return cur.item;
        }
        return getRecursiveHelper(index - 1, cur.next);

    }
}

