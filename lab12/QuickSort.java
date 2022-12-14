import edu.princeton.cs.algs4.Queue;

public class QuickSort {
    /**
     * Returns a new queue that contains the given queues catenated together.
     *
     * The items in q2 will be catenated after all of the items in q1.
     */
    private static <Item extends Comparable> Queue<Item> catenate(Queue<Item> q1, Queue<Item> q2) {
        Queue<Item> catenated = new Queue<Item>();
        for (Item item : q1) {
            catenated.enqueue(item);
        }
        for (Item item: q2) {
            catenated.enqueue(item);
        }
        return catenated;
    }

    /** Returns a random item from the given queue. */
    private static <Item extends Comparable> Item getRandomItem(Queue<Item> items) {
        int pivotIndex = (int) (Math.random() * items.size());
        Item pivot = null;
        // Walk through the queue to find the item at the given index.
        for (Item item : items) {
            if (pivotIndex == 0) {
                pivot = item;
                break;
            }
            pivotIndex--;
        }
        return pivot;
    }

    /**
     * Partitions the given unsorted queue by pivoting on the given item.
     *
     * @param unsorted  A Queue of unsorted items
     * @param pivot     The item to pivot on
     * @param less      An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are less than the given pivot.
     * @param equal     An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are equal to the given pivot.
     * @param greater   An empty Queue. When the function completes, this queue will contain
     *                  all of the items in unsorted that are greater than the given pivot.
     */
    private static <Item extends Comparable> void partition(
            Queue<Item> unsorted, Item pivot,
            Queue<Item> less, Queue<Item> equal, Queue<Item> greater) {
        // Your code here!
        for (Item item : unsorted) {
            if (item.compareTo(pivot) == 0){
                equal.enqueue(item);
                continue;
            }
            if (item.compareTo(pivot) < 0) {
                less.enqueue(item);
            } else {
                greater.enqueue(item);
            }
        }
    }

    /** Returns a Queue that contains the given items sorted from least to greatest. */
    public static <Item extends Comparable> Queue<Item> quickSort(
            Queue<Item> items) {
        // Your code here!
        if (items.size() <= 1) {
            return items;
        }
        Queue<Item> less = new Queue<>();
        Queue<Item> equal = new Queue<>();
        Queue<Item> greater = new Queue<>();
        Item pivot = getRandomItem(items);
        partition(items,pivot,less,equal,greater);
        less = quickSort(less);
        equal = quickSort(equal);
        greater = quickSort(greater);
        return catenate(catenate(less,equal),greater);
    }

    public static void main(String[] args) {
        Queue<String> students = new Queue<String>();
        students.enqueue("Alice");
        students.enqueue("Vanessa");
        students.enqueue("Ethan");
        System.out.println(students);
        Queue<String> queue = quickSort(students);
        System.out.println(queue);


//        Queue<String> students = new Queue<>();
//        students.enqueue("Alice");
//        students.enqueue("Vanessa");
//        students.enqueue("Ethan");
//        students.enqueue("David");
//        students.enqueue("Jay");
//        students.enqueue("Harry");
//        System.out.println("original queue: " + students);
//        Queue<String> students2 = MergeSort.mergeSort(students);
//        System.out.println("sorted queue: " + students2);
//        System.out.println(students.size() == students2.size());
//
//        Queue<Integer> ints = new Queue<>();
//        ints.enqueue(10);
//        ints.enqueue(5);
//        ints.enqueue(6);
//        ints.enqueue(4);
//        ints.enqueue(1);
//        ints.enqueue(7);
//        ints.enqueue(9);
//        ints.enqueue(3);
//        System.out.println("original queue: " + ints);
//        Queue<Integer> ints2 = MergeSort.mergeSort(ints);
//        System.out.println("sorted queue: " + ints2);
//        System.out.println(ints.size() == ints2.size());
   }


}
