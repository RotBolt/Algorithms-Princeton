/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String randChamp = "";
        int i = 1;
        do {
            String word = StdIn.readString();
            double probability = 1.0 / i;
            boolean res = StdRandom.bernoulli(probability);
            if (res) {
                randChamp = word;
            }
            i++;
        } while (!StdIn.isEmpty());
        StdOut.println(randChamp);
    }
}
