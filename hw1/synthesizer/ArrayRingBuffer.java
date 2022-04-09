package synthesizer;
// TODO: Make sure to make this class a part of the synthesizer package

import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        this.capacity = capacity;
        first = 0;
        last = 0;
        fillCount = 0;
        rb = (T[]) new Object[this.capacity];
    }

    /**
     * Adds x to the end of the ring buffer. If there is no room, then
     * throw new RuntimeException("Ring buffer overflow"). Exceptions
     * covered Monday.
     */
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (this.fillCount < this.capacity) {
            last = (last + 1) % this.capacity;
            rb[last] = x;
            this.fillCount += 1;
        } else {
            throw new RuntimeException("Ring buffer overflow");
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update 
        if (this.fillCount != 0) {
            T oldest = rb[first];
            first = (first + 1) % this.capacity;
            this.fillCount -= 1;
            return oldest;
        } else {
            throw new RuntimeException("Ring buffer underflow");
        }
    }

    @Override
    public int capacity() {
        return this.capacity;
    }

    @Override
    public int fillCount() {
        return this.fillCount;
    }

    @Override
    public boolean isEmpty() {
        if (this.fillCount == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isFull() {
        if (this.fillCount == this.capacity) {
            return true;
        }
        return false;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        return rb[first];
    }

    // TODO: When you get to part 5, implement the needed code to support iteration.
    @Override
    public Iterator<T> iterator() {
        return new myIterator();
    }
    private class myIterator implements Iterator<T> {
        int cnt = 0;
        @Override
        public boolean hasNext() {
            if (cnt < fillCount()) {
                return true;
            }
            return false;
        }

        @Override
        public T next(){
            if (hasNext()){
                cnt += 1;
                return rb[(first+cnt)% capacity()];
            }
            throw new RuntimeException("No more element");
        }
    }
}
