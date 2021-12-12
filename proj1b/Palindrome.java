public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> deque = new LinkedListDeque<>();
        for (int i = 0; i < word.length(); i++) {
            char s = word.charAt(i);
            deque.addLast(s);
        }
        return deque;
    }
    public boolean isPalindrome(String word) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindrome(deque);
    }
    private boolean isPalindrome(Deque<Character> deque) {
        if (deque.size() <= 1){
            return true;
        } else {
            char head = deque.removeFirst();
            char tail = deque.removeLast();
            if (head == tail) {
                return isPalindrome(deque);
            } else {
                return false;
            }
        }
    }
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque<Character> deque = wordToDeque(word);
        return isPalindrome(deque,cc);
    }
    private boolean isPalindrome(Deque<Character> deque,CharacterComparator cc) {
        if (deque.size() <= 1){
            return true;
        } else {
            char head = deque.removeFirst();
            char tail = deque.removeLast();
            if (cc.equalChars(head,tail)) {
                return isPalindrome(deque,cc);
            } else {
                return false;
            }
        }
    }
}

