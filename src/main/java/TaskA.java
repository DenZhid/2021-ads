import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.StringTokenizer;

/**
 * Problem solution template.
 */
public final class TaskA {
    private TaskA() {
        // Should not be instantiated
    }

    private static class Entry {
        private final int id;
        private final int score;

        public Entry(int id, int score) {
            this.id = id;
            this.score = score;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(id);
            sb.append(" ");
            sb.append(score);
            return sb.toString();
        }
    }

    private static void solve(final FastScanner in, final PrintWriter out) {
        int size = in.nextInt();
        Entry[] array = new Entry[size];
        for (int i = 0; i < size; i++) {
            array[i] = new Entry(in.nextInt(), in.nextInt());
        }
        Entry[] arrayForMerge = new Entry[size];
        sort(array,arrayForMerge, 0, array.length, (o1, o2) -> {
            if (o1.score < o2.score || (o1.score == o2.score && o1.id > o2.id)) {
                return -1;
            } else if (o1.score > o2.score || o1.id < o2.id) {
                return 1;
            }
            return 0;
        });
        for (int i = 0; i < size; i++) {
            out.println(array[i].toString());
        }
    }

    private static void sort(Entry[] in, Entry[] arrayForMerge, int left, int right, Comparator<Entry> comp) {
        if (left == right - 1) {
            return;
        }
        int mid = left + ((right - left) >> 1);
        sort(in, arrayForMerge, left, mid, comp);
        sort(in, arrayForMerge, mid, right, comp);
        merge(in, arrayForMerge, left, mid, right, comp);
    }

    private static void merge(Entry[] in, Entry[] arrayForMerge, int left, int mid, int right, Comparator<Entry> comp) {
        int firstPointer = left;
        int secondPointer = mid;
        if (right - left >= 0) System.arraycopy(in, left, arrayForMerge, left, right - left);
        for (int i = left; i < right; i++) {
            if (firstPointer ==  mid) {
                in[i] = arrayForMerge[secondPointer++];
            } else if (secondPointer == right) {
                in[i] = arrayForMerge[firstPointer++];
            } else if (comp.compare(arrayForMerge[firstPointer], arrayForMerge[secondPointer]) < 0) {
                in[i] = arrayForMerge[secondPointer++];
            } else {
                in[i] = arrayForMerge[firstPointer++];
            }
        }
    }

    private static class FastScanner {
        private final BufferedReader reader;
        private StringTokenizer tokenizer;

        FastScanner(final InputStream in) {
            reader = new BufferedReader(new InputStreamReader(in));
        }

        String next() {
            while (tokenizer == null || !tokenizer.hasMoreTokens()) {
                try {
                    tokenizer = new StringTokenizer(reader.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return tokenizer.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }
    }

    public static PrintWriter createPrintWriterForLocalTests() {
        return new PrintWriter(System.out, true);
    }

    public static void main(final String[] arg) {
        final FastScanner in = new FastScanner(System.in);
        try (PrintWriter out = new PrintWriter(System.out)) {
            solve(in, out);
        }
    }
}
