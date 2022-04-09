import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T>{
    public int capacity();     // return size of the buffer
    public int fillCount();    // return number of items currently in the buffer
    public void enqueue(T x);  // add item x to the end
    public T dequeue();        // delete and return item from the front
    public T peek();           // return (but do not delete) item from the front
    public default boolean isEmpty() {
        if (fillCount() == 0) {
            return true;
        }
        return false;
    }     // is the buffer empty (fillCount equals zero)?
    public default boolean isFull() {
        if (capacity() == fillCount()) {
            return true;
        }
        return false;
    }        // is the buffer full (fillCount is same as capacity)?
    Iterator<T> iterator();
}