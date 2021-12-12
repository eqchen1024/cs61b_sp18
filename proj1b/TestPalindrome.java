import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    //You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator cc = new OffByOne();
    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome() {
        boolean flag = palindrome.isPalindrome("wdnmd");
        boolean flag1 = palindrome.isPalindrome("noon");
        assertFalse(flag);
        assertTrue(flag1);

        boolean flag2 = palindrome.isPalindrome("a");
        boolean flag3 = palindrome.isPalindrome("");
        assertTrue(flag2);
        assertTrue(flag3);
    }

    @Test
    public void testIsPalindromeOBO() {

        boolean flag = palindrome.isPalindrome("flake", cc);
        boolean flag1 = palindrome.isPalindrome("noon", cc);

        assertTrue(flag);
        assertFalse(flag1);

    }
}


