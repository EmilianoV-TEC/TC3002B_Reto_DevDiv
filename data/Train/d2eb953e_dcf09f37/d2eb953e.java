import java.util.*;
// import java.lang.*;
import java.io.*;

//           THIS TEMPLATE MADE BY AKSH BANSAL.

public class Solution {
    static class FastReader {
        BufferedReader br;
        StringTokenizer st;

        public FastReader() {
            br = new BufferedReader(new InputStreamReader(System.in));
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

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
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
    private static boolean[] isPrime;
    private static void primes(){
        int num = (int)1e6; // PRIMES FROM 1 TO NUM
        isPrime = new boolean[num];
     
        for (int i = 2; i< isPrime.length; i++) {
           isPrime[i] = true;
        }
        for (int i = 2; i< Math.sqrt(num); i++) {
           if(isPrime[i] == true) {
              for(int j = (i*i); j<num; j = j+i) {
                 isPrime[j] = false;
              }
           }
        }
    }
    private static long gcd(long a, long b){
        if(b==0)return a;
        return gcd(b,a%b);
    }
    private static long pow(long x,long y){
        if(y==0)return 1;
        long temp = pow(x, y/2);
        if(y%2==1){
            return x*temp*temp;
        }
        else{
            return temp*temp;
        }
    }
    static ArrayList<Integer>[] adj;
    static void getAdj(int n,int q, FastReader sc){
        adj = new ArrayList[n+1];
        for(int i=1;i<=n;i++){
            adj[i] = new ArrayList<>();
        }
        for(int i=0;i<q;i++){
            int a = sc.nextInt();
            int b = sc.nextInt();

            adj[a].add(b);
            adj[b].add(a);
        }
    }
    static PrintWriter out;
    public static void main(String[] args) throws IOException {
        FastReader sc = new FastReader();
        out = new PrintWriter(System.out);
        // primes();
        // ________________________________

        int t = sc.nextInt();
        StringBuilder output = new StringBuilder();

        while (t-- > 0) {
            int n = sc.nextInt();
            pair = new int[n+1][2];
            dp = new long[n+1][2];
            for(int i=1;i<=n;i++){
                pair[i][0]=sc.nextInt();
                pair[i][1]=sc.nextInt();
            }
            getAdj(n, n-1, sc);
            output.append(solver(n)).append("\n");
        }

        out.println(output);
        // _______________________________

        // int n = sc.nextInt();
        // out.println(solver());
        // ________________________________
        out.flush();
    }

    static int[][] pair;
    static long[][] dp;

    public static long solver(int n) {
        dfs(1,  0);
        return Math.max(dp[1][0], dp[1][1]);
    }

    static void dfs(int node, int parent){
        for(Integer child: adj[node]){
            if(child!=parent){
                dfs(child,  node);
                long left1 = Math.abs(pair[node][0]-pair[child][0]) + dp[child][0];
                long right1 = Math.abs(pair[node][0]-pair[child][1]) + dp[child][1];
                long left2 = Math.abs(pair[node][1]-pair[child][0]) + dp[child][0];
                long right2 = Math.abs(pair[node][1]-pair[child][1]) + dp[child][1];
                dp[node][0] += Math.max(left1, right1);
                dp[node][1] += Math.max(left2, right2);
            }
        }
    }
}