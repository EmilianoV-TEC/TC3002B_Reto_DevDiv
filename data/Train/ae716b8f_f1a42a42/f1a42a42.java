import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Jumps {

    public static void main(String[] args) {

        FastScanner sc = new FastScanner();
        int n = sc.nextInt();
        int ar[] = sc.nextIntArray(n);
        // 1 4 6 9 3 2
        int dp[] = new int[n];
        int nextLower[] = new int[n];
        int nextHigher[] = new int[n];
        int nextLowerE[] = new int[n];
        int nextHigherE[] = new int[n];
        Arrays.fill(nextHigher, n);
        Arrays.fill(nextLower, n);
        Arrays.fill(nextHigherE, n);
        Arrays.fill(nextLowerE, n);
        dp[n-1] = 0;
        Stack<Integer> stack = new Stack<>();

        for(int i = n-1; i >= 0; i--) {

            while(stack.size() > 0 && ar[stack.peek()] < ar[i])
                stack.pop();
            if(stack.size() > 0)
                nextHigherE[i] = stack.peek();
            stack.push(i);
        }
        stack.clear();

        for(int i = n-1; i >= 0; i--) {

            while(stack.size() > 0 && ar[stack.peek()] <= ar[i])
                stack.pop();
            if(stack.size() > 0)
                nextHigher[i] = stack.peek();
            stack.push(i);
        }
        stack.clear();

        for(int i = n-1; i >= 0; i--) {

            while(stack.size() > 0 && ar[stack.peek()] > ar[i])
                stack.pop();
            if(stack.size() > 0)
                nextLowerE[i] = stack.peek();
            stack.push(i);
        }
        stack.clear();

        for(int i = n-1; i >= 0; i--) {

            while(stack.size() > 0 && ar[stack.peek()] >= ar[i])
                stack.pop();
            if(stack.size() > 0)
                nextLower[i] = stack.peek();
            stack.push(i);
        }

        for(int i = n-2; i >= 0; i--) {

            int maxIndex = max(nextHigherE[i], nextLowerE[i]);
            int ans = dp[i+1];
            if(maxIndex < n) {
                ans = min(ans, dp[maxIndex]);
            }

            if(nextHigherE[i] < nextLowerE[i]) {
                int cur = nextHigherE[i];
                while(cur < maxIndex) {
                    ans = min(ans, dp[cur]);
                    cur = nextLower[cur];
                }
            } else {
                int cur = nextLowerE[i];
                while(cur < maxIndex) {
                    ans = min(ans, dp[cur]);
                    cur = nextHigher[cur];
                }
            }
            dp[i] = ans + 1;
        }
        System.out.println(dp[0]);
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

    static void print(String s) {
        System.out.print(s);
    }

    static void println(String s) {
        System.out.println(s);
    }

    static List<Integer> toList(int ar[]) {
        return Arrays.stream(ar).boxed().collect(Collectors.toList());
    }

    static List<Long> toList(long ar[]) {
        return Arrays.stream(ar).boxed().collect(Collectors.toList());
    }

    static List<Double> toList(double ar[]) {
        return Arrays.stream(ar).boxed().collect(Collectors.toList());
    }

    static long gcd(long a, long b) {
        if(b == 0)
            return a;
        return gcd(b, a % b);
    }

    private static int abs(int a){  return (a>=0) ? a: -a;  }
    private static int min(int... ins){ return Arrays.stream(ins).min().getAsInt(); }
    private static int max(int... ins){ return Arrays.stream(ins).max().getAsInt(); }
    private static int sum(int... ins){ return Arrays.stream(ins).sum(); }
    private static long abs(long a){ return (a>=0) ? a: -a; }
    private static long min(long... ins){ return Arrays.stream(ins).min().getAsLong(); }
    private static long max(long... ins){ return Arrays.stream(ins).max().getAsLong(); }
    private static long sum(long... ins){ return Arrays.stream(ins).sum(); }
    private static double abs(double a){ return (a>=0) ? a: -a; }
    private static double min(double... ins){ return Arrays.stream(ins).min().getAsDouble(); }
    private static double max(double... ins){ return Arrays.stream(ins).max().getAsDouble(); }
    private static double sum(double... ins){ return Arrays.stream(ins).sum(); }

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
    }
}