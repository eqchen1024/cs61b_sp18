import org.junit.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();

    // Your tests go here.
    //Uncomment this class once you've created your CharacterComparator interface and OffByOne class.
    @Test
    public void testOBO() {
        Assert.assertTrue(offByOne.equalChars('a', 'b'));
        Assert.assertFalse(offByOne.equalChars('a', 'e'));
        Assert.assertTrue(offByOne.equalChars('Z', 'Y'));
        Assert.assertTrue(offByOne.equalChars('&', '%'));
    }

}
