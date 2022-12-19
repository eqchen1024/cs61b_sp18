import java.util.HashMap;

/**
 * Class for doing Radix sort
 *
 * @author Akhil Batra, Alexander Hwang
 *
 */
public class RadixSort {
    /**
     * Does LSD radix sort on the passed in array with the following restrictions:
     * The array can only have ASCII Strings (sequence of 1 byte characters)
     * The sorting is stable and non-destructive
     * The Strings can be variable length (all Strings are not constrained to 1 length)
     *
     * @param asciis String[] that needs to be sorted
     *
     * @return String[] the sorted array
     */
    public static String[] sort(String[] asciis) {
        // TODO: Implement LSD Sort
        // make a copy
        String[] asciisCopy = new String[asciis.length];
        // get max length
        int maxLength = 0;
        for (String str : asciis) {
            if (str.length() > maxLength) {
                maxLength = str.length();
            }
        }
        // copy
        for (int n = 0; n < asciis.length; n++) {
            asciisCopy[n] = asciis[n];
        }
        // pad with placeholder
        for (int i = 0; i < asciisCopy.length; i++) {
            while (asciisCopy[i].length() < maxLength) {
                asciisCopy[i] += (char) 0;
            }
        }
        // sort
        for (int digit = maxLength - 1; digit >= 0; digit--) {
            sortHelperLSD(asciisCopy,digit);
        }
        // remove placeholder
        for (int m = 0; m < asciis.length; m++) {
            asciisCopy[m] = asciisCopy[m].replace(""+(char) 0,"");
        }
        return asciisCopy;
    }

    /**
     * LSD helper method that performs a destructive counting sort the array of
     * Strings based off characters at a specific index.
     * @param asciis Input array of Strings
     * @param index The position to sort the Strings on.
     */
    private static void sortHelperLSD(String[] asciis, int index) {
        // Optional LSD helper method for required LSD radix sort
        // single step sort
        int[] oneDigitSlice = new int[asciis.length];
        String[] res = new String[asciis.length];
        int[] countDict = new int[256];
        int[] startArray = new int[256];
        int pos = 0;

        for (int i = 0; i < 256; i++) {
            countDict[i] = 0;
        }

        for (String str : asciis) {
            oneDigitSlice[pos] = (int) str.charAt(index);
            pos += 1;
        }

        for (int digit : oneDigitSlice) {
            countDict[digit] += 1;
        }

        startArray[0] = 0;
        for (int j = 1; j < 256 ; j ++) {
            startArray[j] = countDict[j - 1] + startArray[j - 1];
        }

        for (int k = 0; k < asciis.length; k++) {
            res[startArray[oneDigitSlice[k]]] = asciis[k];
            startArray[oneDigitSlice[k]] += 1;
        }

        for (int m = 0; m < asciis.length; m++) {
            asciis[m] = res[m];
        }
    }

    /**
     * MSD radix sort helper function that recursively calls itself to achieve the sorted array.
     * Destructive method that changes the passed in array, asciis.
     *
     * @param asciis String[] to be sorted
     * @param start int for where to start sorting in this method (includes String at start)
     * @param end int for where to end sorting in this method (does not include String at end)
     * @param index the index of the character the method is currently sorting on
     *
     **/
    private static void sortHelperMSD(String[] asciis, int start, int end, int index) {
        // Optional MSD helper method for optional MSD radix sort
        return;
    }

    public static void main(String[] args) {
        String[] asciis = new String[] { "56", "112", "94", "4", "9", "82", "394", "80" };
        String[] res = RadixSort.sort(asciis);
        for (String s : res) {
            System.out.print(s + " ");
        }

        System.out.println();

        String[] asciis2 = new String[] {"  ", "      ", "    ", " "};
        String[] res2 = RadixSort.sort(asciis2);
        for (String s : res2) {
            System.out.print(s + ",");
        }
    }
}
