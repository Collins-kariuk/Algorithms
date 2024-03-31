
// %   Your code must allow the user to specify two filenames on the command line, say {\tt
// %     infile.txt} and {\tt outfile.txt} (these could be any filename, but I'm using these for examples.)  We don't
// %   want to edit your code, so they need to be command-line parameters.  For example:
// %   \begin{itemize}
// %   \item If using C, your code might be compiled and executed as follows:
// % \begin{verbatim}
// %   % gcc stockmarket.c -o stockmarket
// %   % ./stockmarket infile.txt outfile.txt
// % \end{verbatim}
// % \item If using Java\footnote{See url{https://docs.oracle.com/javase/tutorial/essential/environment/cmdLineArgs.html}}, your code might be compiled and
// %   executed as follows:
// % 
// %   \end{itemize}

// %   The contents of the input file will be in the
// %   following form:
// %   \begin{verbatim}
// %     8
// %     42
// %     40
// %     45
// %     45
// %     44
// %     43
// %     50
// %     49
// % \end{verbatim}
// % The first line specifies the number of days (which may not always be a power of 2).  The
// % following lines give the value of the stock on each day.  

// % In this case the output file should contain the following:
// % \begin{verbatim}
// %     3
// %     2
// %     40
// %     45
// %     45
// % \end{verbatim}
// % The first line gives the length of the longest non-decreasing subsequence,
// % the second line gives the day on which the subsequence begins (note the
// % 1-based indexing!), and the rest of the lines give the prices of the
// % stock on the days included in the subsequence.  If there is more than one
// % longest non-decreasimg subsequence, your code can return either one.

// % Most of the grade for this problem will be based on correctly implementing a
// % divide-and-conquer algorithm that solves the problem described in
// // % last week's assignment.  Note that we will evaluate outputs using
// // % {\tt diff} so please make sure your output 
// // % is in the format described above!  We may also
// // % consider code efficiency in an absolute (wall clock) sense.  

import java.io.*;
import java.util.*;

public class StockMarket {

    public static void main(String[] args) throws IOException {
        if (args.length < 2) {
            System.out.println("Usage: java StockMarket <input file> <output file>");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];

        ArrayList<Integer> prices = (ArrayList<Integer>) readStockPrices(inputFile);
        List<Integer> longestNonDecreasingSubsequence = noneDecreasing(prices, 0, prices.get(prices.size() - 1));
        writeOutput(outputFile, longestNonDecreasingSubsequence);
    }

    private static List<Integer> readStockPrices(String filename) throws IOException {
        List<Integer> prices = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            int days = scanner.nextInt();
            for (int i = 0; i < days; i++) {
                if (scanner.hasNextInt()) {
                    prices.add(scanner.nextInt());
                }
            }
        }
        return prices;
    }

    // There are three possible situations for this question as we divide the list
    // into smaller
    // parts recursively. 1 situation: When you have 0 element in the sublist, you
    // return null.
    // 2 situation: When you have 1 element in the sublist, you return the value.
    // If there are
    // more than one element, we divide the group in half and repeat this procedure
    // until we
    // reach situation 1 or 2.
    // When we reach situation 1 or 2, we compare the two elements of
    // the sublist, if the next one is greater than the current one, we return both
    // numbers. If
    // the next one is less than the current one, we return the number on the left
    // =>??right. Then we
    // count the number of consecutive elements on this level and call it a group.
    // If the last
    // number of the group with the first number of next group is consecutive, we
    // compare the
    // last number of the group with the first number of next group, if it's still
    // decreasing, we
    // combine the groups. We compare which group has a greater number of items and
    // we
    // return that for the whole list
    public static ArrayList<Integer> noneDecreasing(ArrayList<Integer> list, int start, int end) {
        int length = list.size() - 1;
        int mid = (1 + length) / 2;
        int maxlength = 0;
        ArrayList<Integer> midstrap = new ArrayList<>();

        ArrayList<Integer> sublist;
        ArrayList<Integer> maxresult = new ArrayList<>();

        if (end < start) {
            // maxresult.add();
            // maxresult.add(sublist);
            return maxresult; // return start index and array
        }

        if (end == start) {
            maxresult.add(start);
            maxresult.add(list.get(start));
            return maxresult; // return start index and array
        }

        // if (list.get(0)<=list.get(length)){
        if (list.size() > 1) {
            // return noneDecreasing(list,);
            ArrayList<Integer> left = noneDecreasing(list, start, mid - 1);
            ArrayList<Integer> right = noneDecreasing(list, mid, end);
            int midL = mid - 1;
            int midR = mid + 1;
            while (list.get(midL) <= list.get(mid)) {
                midL = midL - 1;

            }
            while (list.get(midR) >= list.get(mid)) {
                midR = midL + 1;
            }

            sublist = (ArrayList<Integer>) list.subList(midL, midR);
            maxlength = midR - midL;
            midstrap.addAll(sublist);
            if (left.size() > right.size() && left.size() > sublist.size()) {
                maxresult = left;
            }
            if (right.size() > right.size() && right.size() > right.size()) {
                maxresult = right;
            } else {
                maxresult = midstrap;
            }

        }
        return maxresult;
    }

    private static void writeOutput(String filename, List<Integer> subsequence) throws IOException {
        try (PrintWriter writer = new PrintWriter(filename, "UTF-8")) {
            writer.println(subsequence.size());
            for (int price : subsequence) {
                writer.println(price);
            }
        }
    }
}
