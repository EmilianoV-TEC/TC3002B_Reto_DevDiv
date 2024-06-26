import java.io.*;
import java.util.*;
import java.util.logging.LoggingPermission;

public class C_binarytable {

    public static void main(String[] args) throws Exception {

        FastScanner sc = new FastScanner();
        FastWriter writer = new FastWriter();
        int t = sc.nextInt();
        while(t-- > 0) {
            int n = sc.nextInt(), m = sc.nextInt();
            List<Operation> ops = new ArrayList<>();
            char s[][] = new char[n][m];
            for(int i = 0; i < n; i++)
                s[i] = sc.next().toCharArray();

            // diag
            for(int i = 0; i < n - 1; i++)
                for(int j = 0; j < m; j++) {
                    if(s[i][j] == '0')
                        continue;
                    // right diag
                    if(j + 1 < m && s[i+1][j+1] == '1') {
                        s[i][j] = '0';
                        Operation op = new Operation();
                        op.pairs.add(new Pair(i, j));
                        op.pairs.add(new Pair(i + 1, j));
                        op.pairs.add(new Pair(i, j + 1));
                        ops.add(op);

                        op = new Operation();
                        op.pairs.add(new Pair(i, j + 1));
                        op.pairs.add(new Pair(i + 1, j));
                        op.pairs.add(new Pair(i + 1, j + 1));
                        ops.add(op);
                        s[i+1][j+1] = '0';

                        // left diag
                    } else if(j > 0 && s[i+1][j-1] == '1') {
                        s[i][j] = '0';
                        Operation op = new Operation();
                        op.pairs.add(new Pair(i, j));
                        op.pairs.add(new Pair(i, j - 1));
                        op.pairs.add(new Pair(i + 1, j));
                        ops.add(op);

                        op = new Operation();
                        op.pairs.add(new Pair(i, j - 1));
                        op.pairs.add(new Pair(i + 1, j - 1));
                        op.pairs.add(new Pair(i + 1, j));
                        ops.add(op);
                        s[i+1][j-1] = '0';
                    }
                }

            // same row adjacent
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m - 1; j++) {
                    if(s[i][j] == '0')
                        continue;

                    Pair cur = new Pair(i, j);
                    if(i + 1 < n && s[i][j+1] == '1') {
                        s[i][j] = '0';
                        ops.add(get(cur, cur));
                        ops.add(get(cur, new Pair(i, j+1)));
                        s[i][j+1] = '0';

                    } else if(i + 1 == n && s[i][j+1] == '1') {
                        s[i][j] = '0';
                        Pair upper = new Pair(i-1, j);
                        ops.add(get(upper, cur));
                        ops.add(get(upper, new Pair(i, j+1)));
                        s[i][j+1] = '0';
                    }
                }

            // same col adjacent
            for(int i = 0; i < n - 1; i++)
                for(int j = 0; j < m; j++) {
                    if(s[i][j] == '0')
                        continue;
                    Pair prevCol = new Pair(i, j - 1);
                    Pair cur = new Pair(i, j);

                    if(j > 0 && s[i+1][j] == '1') {
                        s[i][j] = '0';
                        ops.add(get(prevCol, cur));
                        ops.add(get(prevCol, new Pair(i+1, j)));
                        s[i+1][j] = '0';

                    } else if(j == 0 && s[i+1][j] == '1') {
                        s[i][j] = '0';
                        ops.add(get(cur, cur));
                        ops.add(get(cur, new Pair(i + 1, j)));
                        s[i+1][j] = '0';
                    }
                }

            // single 1
            for(int i = 0; i < n; i++)
                for(int j = 0; j < m; j++) {
                    if(s[i][j] == '0')
                        continue;
                    Pair prevCol = new Pair(i, j - 1);
                    Pair cur = new Pair(i, j);
                    s[i][j] = '0';

                    if(i < n - 1) {
                        if(j < m-1) {
                            Pair next = new Pair(i, j+1);
                            ops.add(get(cur, next));
                            ops.add(get(cur, new Pair(i+1, j+1)));
                            ops.add(get(cur, new Pair(i+1, j)));

                        } else if(j == m-1) {

                            ops.add(get(prevCol, prevCol));
                            ops.add(get(prevCol, new Pair(i+1, j - 1)));
                            ops.add(get(prevCol, new Pair(i+1, j)));

                        }
                    } else if(i == n-1) {
                        if(j < m-1) {

                            Pair upper = new Pair(i-1, j);
                            ops.add(get(upper, new Pair(i-1, j+1)));
                            ops.add(get(upper, new Pair(i, j+1)));
                            ops.add(get(upper, upper));

                        } else if(j == m-1) {

                            Pair upper = new Pair(i-1, j-1);
                            ops.add(get(upper, upper));
                            ops.add(get(upper, new Pair(i, j-1)));
                            ops.add(get(upper, new Pair(i-1, j)));
                        }
                    }
                }

            int size = ops.size();
            if(size > 3 * n * m)
                throw new RuntimeException();

            writer.println(ops.size());
            for(Operation op : ops)
                writer.println(op.toString());
        }
        writer.close();
    }

    static Operation get(Pair topLeft, Pair ex) {
        Operation op = new Operation();
        for(int i = 0; i <= 1; i++)
            for(int j = 0; j <= 1; j++) {
                Pair p = new Pair(topLeft.x + i, topLeft.y + j);
                if(!p.equals(ex))
                    op.pairs.add(p);
            }
        return op;
    }

    static class Operation {
        List<Pair> pairs = new ArrayList<>();

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for(Pair p : pairs)
                sb.append(p.x+1 + " " + (p.y+1) + " ");
            return sb.toString();
        }
    }

    static final Random random = new Random();

    static class FastScanner {
        private InputStream sin = System.in;
        private final byte[] buffer = new byte[1024];
        private int ptr = 0;
        private int buflen = 0;
        public FastScanner(){}
        public FastScanner(String filename) throws FileNotFoundException {
            File file = new File(filename);
            sin = new FileInputStream(file);
        }
        private boolean hasNextByte() {
            if (ptr < buflen) {
                return true;
            }else{
                ptr = 0;
                try {
                    buflen = sin.read(buffer);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (buflen <= 0) {
                    return false;
                }
            }
            return true;
        }
        private int readByte() { if (hasNextByte()) return buffer[ptr++]; else return -1;}
        private static boolean isPrintableChar(int c) { return 33 <= c && c <= 126;}
        public boolean hasNext() { while(hasNextByte() && !isPrintableChar(buffer[ptr])) ptr++; return hasNextByte();}
        public String next() {
            if (!hasNext()) throw new NoSuchElementException();
            StringBuilder sb = new StringBuilder();
            int b = readByte();
            while(isPrintableChar(b)) {
                sb.appendCodePoint(b);
                b = readByte();
            }
            return sb.toString();
        }
        public long nextLong() {
            if (!hasNext()) throw new NoSuchElementException();
            long n = 0;
            boolean minus = false;
            int b = readByte();
            if (b == '-') {
                minus = true;
                b = readByte();
            }
            if (b < '0' || '9' < b) {
                throw new NumberFormatException();
            }
            while(true){
                if ('0' <= b && b <= '9') {
                    n *= 10;
                    n += b - '0';
                }else if(b == -1 || !isPrintableChar(b) || b == ':'){
                    return minus ? -n : n;
                }else{
                    throw new NumberFormatException();
                }
                b = readByte();
            }
        }
        public int nextInt() {
            long nl = nextLong();
            if (nl < Integer.MIN_VALUE || nl > Integer.MAX_VALUE) throw new NumberFormatException();
            return (int) nl;
        }
        public double nextDouble() { return Double.parseDouble(next());}
        public long[] nextLongArray(final int n){
            final long[] a = new long[n];
            for (int i = 0; i < n; i++)
                a[i] = nextLong();
            return a;
        }
        public int[] nextIntArray(final int n){
            final int[] a = new int[n];
            for (int i = 0; i < n; i++)
                a[i] = nextInt();
            return a;
        }
        public double[] nextDoubleArray(final int n){
            final double[] a = new double[n];
            for (int i = 0; i < n; i++)
                a[i] = nextDouble();
            return a;
        }

        public List<Integer>[] adjacencyList(int nodes, int edges) {
            return adjacencyList(nodes, edges, false);
        }
        public List<Integer>[] adjacencyList(int nodes, int edges, boolean isDirected) {

            List<Integer>[] adj = new ArrayList[nodes + 1];
            Arrays.setAll(adj, (i) -> new ArrayList<>());
            for (int i = 0; i < edges; i++) {
                int a = nextInt(), b = nextInt();
                adj[a].add(b);
                if (!isDirected) adj[b].add(a);
            }
            return adj;
        }
    }

    static class FastWriter {

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        void print(int n) throws Exception { writer.write(n + ""); }
        void println(int n) throws Exception { writer.write(n + "\n"); }

        void print(long n) throws Exception { writer.write(n + ""); }
        void println(long n) throws Exception { writer.write(n + "\n"); }

        void print(String s) throws Exception { writer.write(s); }
        void println(String s) throws Exception { writer.write(s); writer.newLine(); }

        void println(int ar[]) throws Exception { for(int e : ar) writer.write(e + " "); writer.newLine(); }
        void println(long ar[]) throws Exception { for(long e : ar) writer.write(e + " "); writer.newLine(); }
        void println(double ar[]) throws Exception { for(double e : ar) writer.write(e + " "); writer.newLine(); }
        void close() throws Exception { writer.close(); }
        <T> void println(List<T> L) throws Exception { for(T o : L) writer.write(o + " "); writer.newLine(); }
    }

    static int findDistance(List<Integer> G[], int nodes, int src, int dst) {

        LinkedList<Integer> queue = new LinkedList<>();
        int dist[] = new int[nodes + 1];
        int ans = -1;
        dist[src] = 0;
        queue.add(src);
        while(queue.size() > 0) {
            int u = queue.poll();
            if(u == dst) {
                ans = dist[u];
            }
            for(int v : G[u]) {
                if(dist[v] >= 0)
                    continue;
                queue.add(v);
                dist[v] = dist[u] + 1;
            }
        }
        return ans;
    }

    static void sort(List<Integer> A) {
        shuffleList(A);
        Collections.sort(A);
    }

    static void shuffleList(List<Integer> A) {
        int n = A.size();
        for(int i = 0; i < n; i++) {
            int tmp = A.get(i);
            int randomPos = i + random.nextInt(n-i);
            A.set(i, A.get(randomPos));
            A.set(randomPos, tmp);
        }
    }

    static void sort(int A[]) {
        shuffleArray(A);
        Arrays.sort(A);
    }
    static void sort(long A[]) {
        shuffleArray(A);
        Arrays.sort(A);
    }
    static void sort(double A[]) {
        shuffleArray(A);
        Arrays.sort(A);
    }
    static <T> void sort(T[] A) {
        shuffleArray(A);
        Arrays.sort(A);
    }
    static <T> void shuffleArray(T[] A) {
        int n = A.length;
        for(int i = 0; i < n; i++) {
            T tmp = A[i];
            int randomPos = i + random.nextInt(n-i);
            A[i] = A[randomPos];
            A[randomPos] = tmp;
        }
    }
    static void shuffleArray(int[] A) {
        int n = A.length;
        for(int i = 0; i < n; i++) {
            int tmp = A[i];
            int randomPos = i + random.nextInt(n-i);
            A[i] = A[randomPos];
            A[randomPos] = tmp;
        }
    }
    static void shuffleArray(long[] A) {
        int n = A.length;
        for(int i = 0; i < n; i++) {
            long tmp = A[i];
            int randomPos = i + random.nextInt(n-i);
            A[i] = A[randomPos];
            A[randomPos] = tmp;
        }
    }
    static void shuffleArray(double[] A) {
        int n = A.length;
        for(int i = 0; i < n; i++) {
            double tmp = A[i];
            int randomPos = i + random.nextInt(n-i);
            A[i] = A[randomPos];
            A[randomPos] = tmp;
        }
    }

    static int[] subArray(int A[], int x, int y) {
        int B[] = new int[y - x + 1];
        for(int i = x; i <= y; i++)
            B[i-x] = A[i];
        return B;
    }

    static int[] toArray(List<Integer> L) {
        return L.stream().mapToInt(x -> x).toArray();
    }

    static void println(int[] A) {
        for(int e: A) System.out.print(e + " ");
        System.out.println();
    }

    static void println(long[] A) {
        for(long e: A) System.out.print(e + " ");
        System.out.println();
    }

    static void println(List arr) {
        for(Object e: arr) System.out.print(e + " ");
        System.out.println();
    }

    static <T> void println(T A[]) {

        for(T i : A)
            System.out.print(i + " ");
        System.out.println();
    }

    static void print(String s) {
        System.out.print(s);
    }
    static void println(long n) {
        System.out.println(n);
    }

    static void println(String s) {
        System.out.println(s);
    }

    static List<Integer> toList(int ar[]) {
        List<Integer> list = new ArrayList<>();
        for(int x : ar) list.add(x);
        return list;
    }

    static List<Long> toList(long ar[]) {
        List<Long> list = new ArrayList<>();
        for(long x : ar) list.add(x);
        return list;
    }

    static List<Double> toList(double ar[]) {
        List<Double> list = new ArrayList<>();
        for(double x : ar) list.add(x);
        return list;
    }

    static long gcd(long a, long b) {
        if(b == 0)
            return a;
        return gcd(b, a % b);
    }

    private static int abs(int a){  return (a>=0) ? a: -a;  }
    private static int min(int... ins){ int min = ins[0]; for(int x : ins) min = Math.min(min, x); return min;  }
    private static int max(int... ins){ int max = ins[0]; for(int x : ins) max = Math.max(max, x); return max;  }
    private static long sum(int... ins){ long s = 0; for(int x : ins) s += x; return s; }
    private static long abs(long a){ return (a>=0) ? a: -a; }
    private static long min(long... ins){ long min = ins[0]; for(long x : ins) min = Math.min(min, x); return min;  }
    private static long max(long... ins){ long max = ins[0]; for(long x : ins) max = Math.max(max, x); return max; }
    private static long sum(long... ins){ long s = 0; for(long x : ins) s += x; return s; }
    private static double abs(double a){ return (a>=0) ? a: -a; }
    private static double min(double... ins){ double min = ins[0]; for(double x : ins) min = Math.min(min, x); return min;  }
    private static double max(double... ins){ double max = ins[0]; for(double x : ins) max = Math.max(max, x); return max;  }
    private static double sum(double... ins){ double s = 0; for(double x : ins) s += x; return s; }

    private static class Pair implements Comparable<Pair> {
        int x, y;
        Pair(int x, int y) { this.x = x; this.y = y; }
        Pair() {}

        @Override
        public int compareTo(Pair other) {
            return x == other.x ? y - other.y : x - other.x;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Pair pair = (Pair) o;
            return x == pair.x &&
                    y == pair.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }

    static int pow(long n, int p, int mod) {
        if(p == 0)
            return 1;
        long x = pow(n, p/2, mod);
        x = x * x % mod;
        return (int) ( p % 2 == 0 ? x : x * n % mod );
    }

    static long inv(int x, int m) {
        return pow(x, m - 2, m);
    }

    static int lowerBound(int ar[], int L, int R, int val) {
        if(val < ar[L] || val > ar[R])
            return -1;
        while(L <= R) {
            int mid = (L + R) / 2;
            if(ar[mid] >= val)
                R = mid - 1;
            else
                L = mid + 1;
        }
        return ar[L] == val ? L : -1;
    }

    static int upperBound(int ar[], int L, int R, int val) {
        if(val < ar[L] || val > ar[R])
            return -1;
        while(L <= R) {
            int mid = (L + R) / 2;
            if(ar[mid] > val)
                R = mid - 1;
            else
                L = mid + 1;
        }
        return ar[R] == val ? R : -1;
    }
}

