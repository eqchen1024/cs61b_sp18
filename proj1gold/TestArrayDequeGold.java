import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
        @Test
        public void check1() {
                StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
                ArrayDequeSolution<Integer> gold1 = new ArrayDequeSolution<>();

                for (int i = 0; i < 10; i += 1) {
                        double numberBetweenZeroAndOne = StdRandom.uniform();
                        double numberBetweenZeroAndOne1 = StdRandom.uniform();
                        if (numberBetweenZeroAndOne < 0.5) {
                                sad1.addLast(i);
                                gold1.addLast(i);
                                if (numberBetweenZeroAndOne1 < 0.5) {
                                        Integer a = sad1.removeLast();
                                        Integer b = gold1.removeLast();
                                        assertEquals(a, b);
                                } else {
                                        sad1.removeFirst();
                                        gold1.removeFirst();
                                }

                        } else {
                                sad1.addFirst(i);
                                gold1.addFirst(i);
                                if (numberBetweenZeroAndOne1 < 0.5) {
                                        sad1.removeLast();
                                        gold1.removeLast();
                                } else {
                                        sad1.removeFirst();
                                        gold1.removeFirst();
                                }
                        }
                }

        }
        }
