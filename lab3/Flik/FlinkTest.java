import org.junit.Test;
import static org.junit.Assert.*;
public class FlinkTest {
    @Test
    public void testEqual() {
        Integer a = 5;
        Integer b = 5;
        Integer c = b +1;
        assertTrue(Flik.isSameNumber(a,c));
    }
}

