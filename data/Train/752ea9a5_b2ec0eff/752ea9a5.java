//package Codeforces;

import java.io.*;
import java.util.*;

public class PheonixAndTowers
{
	public static void main(String[] args)throws Exception{ new PheonixAndTowers().run();} 
	long mod=1000000000+7;
//	int[][] ar;
	void solve() throws Exception
	{
		
		
		for(int tt=ni();tt>0;tt--){

			//int n = ni();
			
			
			int n = ni();
			int m =ni();
			int x =ni();
			
			int[] a = new int[n];
			for(int i=0;i<n;i++){
				a[i] =ni();
			}
			PriorityQueue<Pair> pq = new PriorityQueue<>();
			
			for(int i=1;i<=m;i++) pq.add(new Pair(i));
			out.println("YES");
			for(int i:a){
				
				Pair r = pq.remove();
				r.sum+=i;
				pq.add(r);
				out.print(r.id+" ");
				
			}
			out.println();
			
			
			
			
			
			
			
		}
		
	}
	
	class Pair implements Comparable<Pair>{
		int id;
		long sum=0;
		public Pair(int i){
			this.id=i;
		}
		@Override
		public int compareTo(Pair o) {
			
			return Long.compare(this.sum, o.sum);
		}
		
		
	}
	boolean issubset(int[] a,int k){
		int n = a.length;
		boolean[][]dp = new boolean[n+1][k+1];
		
		for(int i=0;i<=n;i++){
			for(int j=0;j<=k;j++){
				if(i==0) {dp[i][j] = false;continue;}
				if(j==0){dp[i][j]= true; continue;}
				if(a[i-1]<=j){
					dp[i][j] = dp[i][j-a[i-1]] || dp[i-1][j];
				}else{
					dp[i][j] = dp[i-1][j];
				}
			}
		}
		
		return dp[n][k];
	}
	
	 void ruffleSort(int[] a) {
		//ruffle
		int n=a.length;
		Random r=new Random();
		for (int i=0; i<a.length; i++) {
			int oi=r.nextInt(n), temp=a[i];
			a[i]=a[oi];
			a[oi]=temp;
		}
		
		//then sort
		Arrays.sort(a);
	}

	
	/*FAST INPUT OUTPUT & METHODS BELOW*/
	
	private byte[] buf=new byte[1024];
	private int index;
	private InputStream in;
	private int total;
	private SpaceCharFilter filter;
	PrintWriter out;
	
	int min(int... ar){int min=Integer.MAX_VALUE;for(int i:ar)min=Math.min(min, i);return min;}
	long min(long... ar){long min=Long.MAX_VALUE;for(long i:ar)min=Math.min(min, i);return min;}
	int max(int... ar) {int max=Integer.MIN_VALUE;for(int i:ar)max=Math.max(max, i);return max;}
	long max(long... ar) {long max=Long.MIN_VALUE;for(long i:ar)max=Math.max(max, i);return max;}
	
	void shuffle(int a[]) {
		ArrayList<Integer> al = new ArrayList<>();
		for(int i=0;i<a.length;i++) 
			al.add(a[i]);
		
		Collections.sort(al);
		for(int i=0;i<a.length;i++) 
			a[i]=al.get(i);
	}
	long lcm(long a,long b)
	{
		return (a*b)/(gcd(a,b));
	}
	
	int gcd(int a, int b) 
	{ 
		if (a == 0) 
			return b; 
		return gcd(b%a, a); 
	} 
	long gcd(long a, long b) 
	{ 
		if (a == 0) 
			return b; 
		return gcd(b%a, a); 
	}
	/* for (1/a)%mod = ( a^(mod-2) )%mod  ----> use expo to calc -->(a^(mod-2)) */
	long expo(long p,long q)  /*  (p^q)%mod   */
	{
		long z = 1;
		while (q>0) {
			if (q%2 == 1) {
				z = (z * p)%mod;
			}
			p = (p*p)%mod;
			q >>= 1;
		}
		return z;
	}
	void run()throws Exception
	{
		in=System.in; out = new PrintWriter(System.out);
		solve();
		out.flush();
	}
	private int scan()throws IOException
	{
		if(total<0)
			throw new InputMismatchException();
		if(index>=total)
		{
			index=0;
			total=in.read(buf);
			if(total<=0)
				return -1;
		}
		return buf[index++];
	}
	private int ni() throws IOException 
	{
		int c = scan();
		while (isSpaceChar(c))
			c = scan();
		int sgn = 1;
		if (c == '-') {
			sgn = -1;
			c = scan();
		}
		int res = 0;
		do {
			if (c < '0' || c > '9')
				throw new InputMismatchException();
			res *= 10;
			res += c - '0';
			c = scan();
		} while (!isSpaceChar(c));
		return res * sgn;
	}
	private long nl() throws IOException 
	{
		long num = 0;
		int b;
		boolean minus = false;
		while ((b = scan()) != -1 && !((b >= '0' && b <= '9') || b == '-'))
			;
		if (b == '-') {
			minus = true;
			b = scan();
		}
		
		while (true) {
			if (b >= '0' && b <= '9') {
				num = num * 10 + (b - '0');
			} else {
				return minus ? -num : num;
			}
			b = scan();
		}
	}
	private double nd() throws IOException{
		return Double.parseDouble(ns());
	}
	private String ns() throws IOException {
		int c = scan();
		while (isSpaceChar(c))
			c = scan();
		StringBuilder res = new StringBuilder();
		do {
			if (Character.isValidCodePoint(c))
				res.appendCodePoint(c);
			c = scan();
		} while (!isSpaceChar(c));
		return res.toString();
	}
	private String nss() throws IOException
	{
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		return br.readLine();
	}
	private char nc() throws IOException 
	{
		int c = scan();
		while (isSpaceChar(c))
			c = scan();
		return (char) c;
	}
	private boolean isWhiteSpace(int n)
	{
		if(n==' '||n=='\n'||n=='\r'||n=='\t'||n==-1)
			return true;
		return false;
	}
	private boolean isSpaceChar(int c) {
		if (filter != null)
			return filter.isSpaceChar(c);
		return isWhiteSpace(c);
	}
	private interface SpaceCharFilter {
		public boolean isSpaceChar(int ch);
	}
}



