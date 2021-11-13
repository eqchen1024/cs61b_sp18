public class ArrayDeque<T> {
    public T[] items;
    public int size;
    public int nextFirst;
    public int nextLast;
    public int length;

    public ArrayDeque(){
        items = (T[]) new Object[8];
        size = 0;
        nextFirst = 4;
        nextLast = 5;
        length = items.length;
    }

    public void resize(){
        length = size * 4;
        T[] newArray = (T[]) new Object[length];
        System.arraycopy(items, 0, newArray, 0, nextLast);
        System.arraycopy(items, nextFirst+1, newArray,length-(size-nextFirst-1), size-nextLast);
        items = newArray;
        nextFirst= length-(size-nextFirst-1) -1;

    }


    public void addFirst(T item){
        if (nextFirst < nextLast ) {
            resize();
        }
        items[nextFirst] = item;
        if (nextLast != 0){
        nextFirst -= 1;
        }else{
            nextFirst -= 1;
            nextFirst += length;
        }
        size += 1;
    }

    public void addLast(T item){
        if (nextFirst < nextLast ) {
            resize();
        }
        items[nextLast] = item;
        if (nextLast != length -1){
            nextLast += 1;
        }else{
            nextLast += 1;
            nextLast -= length;
        }
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
        for(int i = nextFirst+1;i<length;i++){
            System.out.print(String.valueOf(items[i])+ ' ');
        }
        for (int i = 0; i < nextLast; i++){
            System.out.print(String.valueOf(items[i])+ ' ');
        }

    }
    public T removeLast(){
        if (size == 0){
            return null;
        }
        T toRemove = items[nextLast-1];
        nextLast -= 1;
        size -= 1;
        if (size/length < 0.25){
            resize();
        }
        return toRemove;

    }
    public T removeFirst(){
        if (size == 0){
            return null;
        }
        T toRemove = items[nextFirst+1];
        nextFirst += 1;
        size -= 1;
        if (size/length < 0.25){
            resize();
        }
        return toRemove;
    }
    public T get(int index){
        if (index >= size){
            return null;
        }
        if (index+nextFirst+1 <length){
            return items[index+nextFirst+1];
        }else{
            return items[index+nextFirst+1-length];
        }
    }
}
