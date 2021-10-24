public class IntList {
    public int first;
    public IntList rest;

    public IntList(int f, IntList r) {
        first = f;
        rest = r;
    }
/** Return the size of the list using... recursion! */
    public int size() {
        if (rest == null) {
            return 1;
        }
        return 1 + this.rest.size();
    }
/** Return the size of the list using no recursion! */
    public int iterativeSize() {
        IntList p = this;
        int totalSize = 0;
        while (p != null) {
            totalSize += 1;
            p = p.rest;
        }
        return totalSize;
    }
/** Return ith item in intList */
    public int get(int i){
        if (i == 0){
          return this.first;
        }
        return this.rest.get(i-1);
    }
/** create a new intList with each element is added x */
    public static IntList incrList(IntList L, int x){
        IntList Cur = new IntList(L.first + x,null);
        IntList Res = Cur;
        while (L != null){
            Cur.first = L.first + x;
            Cur.rest = new IntList(L.first + x,null);
            Cur = Cur.rest;
            L = L.rest;
        }
        return Res;
      }
/** create a new intList with each element is added x with Recursion */
    public static IntList incrListRecur(IntList L, int x){
        if (L.rest == null){
          return new IntList(L.first + x, null);
        }
        IntList Res = new IntList(L.first + x, null);
        Res.rest = IntList.incrListRecur(L.rest, x);
        return Res;
    }
/** mutate the origin intLint to add x to each element*/
    public static IntList incrListRecurMutate(IntList L, int x){
        if (L == null){
          return null;
        }
        L.first=L.first + x;
        L.rest = IntList.incrListRecurMutate(L.rest, x);
        return L;
    }

/** add First*/
    public static IntList addFirst(int x, IntList L){
        L = new IntList(x,L);
        return L;
    }
    public static void main(String[] args) {
        IntList L = new IntList(15,null);
        L = new IntList(10,L);
        L = new IntList(5,L);
        System.out.println(L.get(0));
        System.out.println(L.get(1));
        System.out.println(L.get(2));
        System.out.println(incrList(L,5).get(0));
        System.out.println(incrList(L,5).get(1));
        System.out.println(incrList(L,5).get(2));
        System.out.println(incrListRecur(L,5).get(0));
        System.out.println(incrListRecur(L,5).get(1));
        System.out.println(incrListRecur(L,5).get(2));
        System.out.println(incrListRecurMutate(L,6).get(0));
        System.out.println(L.get(1));
        System.out.println(L.get(2));
        L=addFirst(-100,L);
        System.out.println(L.get(0));
        System.out.println(L.get(1));
        System.out.println(L.get(2));
        System.out.println(L.get(3));
    }
}
