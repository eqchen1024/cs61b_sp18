public class LinkedListDeque<T> {

    private int size;
    private intNode sentinel;

    public LinkedListDeque(){
        sentinel = new intNode((T) "-518273", null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
        size = 0;
    }


    private class intNode{
        public T item;
        public intNode next;
        public intNode prev;
        public intNode(T i, intNode n, intNode p) {
            prev = p;
            item = i;
            next = n;
        }
    }
    public void addFirst(T item){
        intNode newFirst = new intNode(item, sentinel.next,sentinel);
        sentinel.next.prev = newFirst;
        sentinel.next = newFirst;
        size += 1;
    }
    public void addLast(T item){
        intNode newLast = new intNode(item, sentinel,sentinel.prev);
        sentinel.prev.next = newLast;
        sentinel.prev = newLast;
        size += 1;
    }
    public boolean isEmpty(){
        if (size == 0){
            return true;
        }
        return false;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        int cnt = 0;
        intNode toPrint = sentinel.next;
        while (cnt < size){
            System.out.print(String.valueOf(toPrint.item)+ ' ');
            cnt += 1;
        }
    }
    public T removeLast(){
        if (size == 0){
            return null;
        }
        intNode toRemove = sentinel.prev;
        toRemove.prev.next =sentinel;
        sentinel.prev = toRemove.prev;
        toRemove.prev = null;
        toRemove.next = null;
        size -= 1;
        return toRemove.item;
    }
    public T removeFirst(){
        if (size == 0){
            return null;
        }
        intNode toRemove = sentinel.next;
        sentinel.next = toRemove.next;
        toRemove.next.prev = sentinel;
        toRemove.prev = null;
        toRemove.next = null;
        size -= 1;
        return toRemove.item;
    }
    public T get(int index){
        if (size == 0){
            return null;
        }
        int cnt = 0;
        intNode toCnt = sentinel.next;
        while (cnt < index){
            cnt += 1;
            toCnt = toCnt.next;
        }
        return toCnt.item;
    }
    public T getRecursive(int index){
        if (size == 0){
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
    private T getRecursiveHelper(int index, intNode cur){
        if (index == 0){
            return cur.item;
        }
        return getRecursiveHelper(index - 1, cur.next);


    }

}
