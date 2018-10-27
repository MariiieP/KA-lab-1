package domino;

import java.util.Arrays;

public class Perms {

    public int[] ArrPerms; //массив перестановок
    private int n;
    private int k;

    public Perms(int nSize) { //конструктор

    n=nSize;
    int[] getSPovtorPerm = new int[nSize];

    int[] getNotPovtorPerm = new int[nSize];
    int[] arr1 = new int[nSize];
    for (int i=0;i<nSize; i++)
        arr1[i]=i;

}
    public static int[] getFirstPerm(int n)
    {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++)
        {
            arr[i] = i;
        }
        return arr;
    }

    public static Boolean getNextPerm( int[] perm)
    {
        int n = perm.length;
        int i = n - 1;
        for (; i > 0 && perm[i] < perm[i - 1]; i--) ;
        if (i == 0)
            return false;
        int j = n - 1;
        for (; j >= 0 && perm[j] < perm[i - 1]; j--) ;
        swap( perm,i - 1, j);
        for (int k = 0; k <= (n - i) / 2 - 1; k++)
        {
            swap(perm,k + i, n - k - 1);
        }
        return true;
    }

    public static void swap(int[] perm,int a, int b) {
        int t = perm[a];
        perm[a] = perm[b];
        perm[b] = t;
    }



}