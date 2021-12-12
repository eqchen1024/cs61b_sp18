import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
        @Test
        public void check1() {
                StudentArrayDeque<Integer> sad1 = new StudentArrayDeque<>();
                ArrayDequeSolution<Integer> gold1 = new ArrayDequeSolution<>();
                StringBuilder callsTrack = new StringBuilder();

                for (int i = 0; i < 999; i += 1) {
                        double numberBetweenZeroAndOne1 = StdRandom.uniform();
                        double numberBetweenZeroAndOne2 = StdRandom.uniform();
                        if (numberBetweenZeroAndOne1 < 0.5) {
                                //add
                                if (numberBetweenZeroAndOne2 < 0.5) {
                                        sad1.addLast(i);
                                        gold1.addLast(i);
                                        callsTrack.append(String.format("addLast(%d)\n", i));
                                } else {
                                        sad1.addFirst(i);
                                        gold1.addFirst(i);
                                        callsTrack.append(String.format("addFirst(%d)\n", i));
                                }

                        } else {
                                //remove
                                if (numberBetweenZeroAndOne2 < 0.5) {
                                        callsTrack.append("removeLast()\n");
                                        Integer a = sad1.removeLast();
                                        Integer b = gold1.removeLast();
                                        assertEquals(callsTrack.toString(), a, b);
                                } else {
                                        callsTrack.append("removeFirst()\n");
                                        Integer a = sad1.removeFirst();
                                        Integer b = gold1.removeFirst();
                                        assertEquals(callsTrack.toString(), a, b);

                                }

                        }
                }
        }
        }
