import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;

public class D {
	
	static final int mod=998244353;
	
	static long[] facts, factInvs;

	public static void main(String[] args) {
		precomp();
		FastScanner fs=new FastScanner();
		int n=fs.nextInt(), k=fs.nextInt();
		Seg[] segs=new Seg[n];
		for (int i=0; i<n; i++) segs[i]=new Seg(fs.nextInt(), fs.nextInt());
		Event[] events=new Event[n*2];
		for (int i=0; i<n; i++) {
			events[2*i]=new Event(segs[i], true);
			events[2*i+1]=new Event(segs[i], false);
		}
		long ans=0;

		Arrays.sort(events);
		int counter=0;
		for (Event e:events) {
			if (e.start) {
				counter++;
			}
			else {
				counter--;
				if (counter+1<k)
					continue;
				else
					ans=add(ans, nCk(counter, k-1));
			}
		}
		System.out.println(ans);
	}

	static final Random random=new Random();
	
	static void ruffleSort(int[] a) {
		int n=a.length;//shuffle, then sort 
		for (int i=0; i<n; i++) {
			int oi=random.nextInt(n), temp=a[oi];
			a[oi]=a[i]; a[i]=temp;
		}
		Arrays.sort(a);
	}
	
	static long add(long a, long b) {
		return (a+b)%mod;
	}
	
	static long mul(long a, long b) {
		return a*b%mod;
	}
	
	static long exp(long base, long e) {
		if (e==0) return 1;
		long half=exp(base, e/2);
		if (e%2==0) return mul(half, half);
		return mul(half, mul(half, base));
	}
	
	static long modInv(long x) {
		return exp(x, mod-2);
	}
	
	static void precomp() {
		facts=new long[1_000_000];
		factInvs=new long[1_000_000];
		factInvs[0]=facts[0]=1;
		for (int i=1; i<facts.length; i++)
			facts[i]=mul(facts[i-1], i);
		factInvs[facts.length-1]=modInv(facts[facts.length-1]);
		for (int i=facts.length-2; i>=0; i--)
			factInvs[i]=mul(factInvs[i+1], i+1);
	}
	
	static long nCk(int n, int k) {
		return mul(facts[n], mul(factInvs[k], factInvs[n-k]));
	}
	
	static class Seg {
		int l, r;
		public Seg(int l, int r) {
			this.l=l;
			this.r=r;
		}
	}
	
	static class Event implements Comparable<Event> {
		boolean start;
		Seg s;
		public Event(Seg s, boolean start) {
			this.s=s;
			this.start=start;
		}

		int x() {
			if (start) return s.l;
			return s.r;
		}
		
		public int compareTo(Event o) {
			if (x()!=o.x()) return Integer.compare(x(), o.x());
			if (start==o.start)return 0;
			if (start) return -1;
			return 1;
		}
	}
	
	static class FastScanner {
		BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st=new StringTokenizer("");
		String next() {
			while (!st.hasMoreTokens())
				try {
					st=new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++) a[i]=nextInt();
			return a;
		}
		long nextLong() {
			return Long.parseLong(next());
		}
	}

	
}
