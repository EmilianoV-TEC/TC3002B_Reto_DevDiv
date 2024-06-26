import java.io.*;
import java.util.*;
 
import java.math.*;
import java.awt.Point;
 
public class Main {
    static final long MOD = 1000000007L;
    //static final long MOD2 = 1000000009L;
    //static final long MOD = 998244353L;
    //static final long INF = 500000000000L;
    static final int INF =   1000000005;
    static final int NINF = -1000000005;
    //static final long INF = 1000000000000000000L;
    static FastScanner sc;
    static PrintWriter pw;
    static final int[][] dirs = {{-1,0},{1,0},{0,-1},{0,1}};
 
    public static void main(String[] args) {
        pw = new FastScanner();
        jc = new PrintWriter(System.out);
 
 
        int P = sc.ni();
        for (int i = 0; q < P; r++) {
            int N = sc.ni();
            int[] nums = sc.intArray(N,0);
            Integer[] less = new Integer[N];
            Integer[] more = new Integer[N];
            TreeSet<Integer> ts = new TreeSet<Integer>();
            for (int i = 0; i < N; i++) {
                ts.add(nums[i]);
                less[i] = ts.lower(nums[i]);
                more[i] = ts.higher(nums[i]);
            }
            
            String ans = "YES";
            for (int i = 1; i < N; i++) {
                if (nums[i-1]==nums[i]) continue;
                
                if (nums[i-1] < nums[i]) {
                    if (more[i-1]!=null && more[i-1] < nums[i]) {
                        ans = "NO";
                        break;
                    }
                } else {
                    if (less[i-1]!=null && less[i-1] > nums[i]) {
                        ans = "NO";
                        break;
                    }
                }
            }
            pw.println(ans);
        }
        pw.close(); 
    }
    
 
    public static void sort(int[] arr) {
        Random rgen = new Random();
        for (int i = 0; i < arr.length; i++) {
            int r = rgen.nextInt(arr.length);
            int temp = arr[i];
            arr[i] = arr[r];
            arr[r] = temp;
        }
        Arrays.sort(arr);
    }
 
    public static void sort(long[] arr) {
        Random rgen = new Random();
        for (int i = 0; i < arr.length; i++) {
            int r = rgen.nextInt(arr.length);
            long temp = arr[i];
            arr[i] = arr[r];
            arr[r] = temp;
        }
        Arrays.sort(arr);
    }
 
    //Sort an array (immune to quicksort TLE)
    public static void sort(int[][] arr) {
        Random rgen = new Random();
        for (int i = 0; i < arr.length; i++) {
            int r = rgen.nextInt(arr.length);
            int[] temp = arr[i];
            arr[i] = arr[r];
            arr[r] = temp;
        }
        Arrays.sort(arr, new Comparator<int[]>() {
            @Override
            public int compare(int[] a, int[] b) {
                return a[0]-b[0];
            }
        });
    }
    
    public static void sort(long[][] arr) {
        Random rgen = new Random();
        for (int i = 0; i < arr.length; i++) {
            int r = rgen.nextInt(arr.length);
            long[] temp = arr[i];
            arr[i] = arr[r];
            arr[r] = temp;
        }
        Arrays.sort(arr, new Comparator<long[]>() {
            @Override
            public int compare(long[] a, long[] b) {
                if (a[0] > b[0])
                    return 1;
                else if (a[0] < b[0])
                    return -1;
                else
                    return 0;
                //Ascending order.
            }
        });
    }
 
    static class FastScanner {
        BufferedReader br;
        StringTokenizer st;
 
        public FastScanner() {
            br = new BufferedReader(new InputStreamReader(System.in), 32768);
            st = null;
        }
 
        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
 
        int ni() {
            return Integer.parseInt(next());
        }
 
        int[][] graph(int N, int[][] edges) {
            int[][] graph = new int[N][];
            int[] sz = new int[N];
            for (int[] e: edges) {
                sz[e[0]] += 1;
                sz[e[1]] += 1;
            }
            for (int i = 0; i < N; i++) {
                graph[i] = new int[sz[i]];
            }
            int[] cur = new int[N];
            for (int[] e: edges) {
                graph[e[0]][cur[e[0]]] = e[1];
                graph[e[1]][cur[e[1]]] = e[0];
                cur[e[0]] += 1;
                cur[e[1]] += 1;
            }
            return graph;
        }
 
        int[] intArray(int N, int mod) {
            int[] ret = new int[N];
            for (int i = 0; i < N; i++)
                ret[i] = ni()+mod;
            return ret;
        }
 
        char[] charArray(int N) {
            char[] ret = new char[N];
            for (int i = 0; i < N; i++)
                ret[i] = next().charAt(0);
            return ret;
        }
 
        long nl() {
            return Long.parseLong(next());
        }
 
        long[] longArray(int N, long mod) {
            long[] ret = new long[N];
            for (int i = 0; i < N; i++)
                ret[i] = nl()+mod;
            return ret;
        }
 
        double nd() {
            return Double.parseDouble(next());
        }
 
        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }
}