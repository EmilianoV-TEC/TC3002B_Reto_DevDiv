import java.util.*;
public class ss
{
    public static void main(String[]args)
    {
        Scanner in=new Scanner (System.in);
        int t=in.nextInt();
        for(int i1=0;i1<t;i1++)
        {
            int n=in.nextInt();
	
	long[] ar=new long[n];
	
	for(int i=0;i<n;i++)
	{
		ar[i]=in.nextLong();
		
	}
	
	long[] ans=new long[n];
	ans[0]=ar[0]*n;
	long m=ar[0];
	long s1=ar[0];
	for(int i=2;i<n;i+=2)
	{
		if(m>ar[i])
		{
			m=ar[i];
		}
		s1+=ar[i];
		ans[i]=s1-m+m*(n-i/2);
 
		
 
 
	}
	ans[1]=ar[1]*n;
	 m=ar[1];
	 s1=ar[1];
	for(int i=3;i<n;i+=2)
	{
		if(m>ar[i])
		{
			m=ar[i];
		}
		s1+=ar[i];
		ans[i]=s1-m+m*(n-i/2);
	}
	long mini=ans[0]+ans[1];
	for(int i=1;i<n-1;i++)
	{
		mini=Math.min(ans[i]+ans[i+1],mini);
	}
	System.out.println(mini);
}
}
}

