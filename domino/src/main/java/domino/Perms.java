package domino;

import java.util.Arrays;

public class Perms {

    public int[][] ArrPerms; //массив перестановок
    private int[] p; //следующая перестановка
    private int n;
    private int k;

    public Perms(int s) { //конструктор

        n=s;
        int[] arr1 = new int[s];
        for (int i=0;i<s; i++)
            arr1[i]=i;
        ArrPerms = new int[fact(s)][s];
        p = Arrays.copyOf(arr1, s);
        k = 0;
        fiilPerms(s-1);
    }

    public void fiilPerms(int m) {
        if (m == 0)
            ArrPerms[k++] = Arrays.copyOf(p, n);
        else {
            for (int i = 0; i <= m; i++) {
                fiilPerms(m - 1);
                if (i < m) {
                    swap(i, m);
                    reverse(m - 1);
                }
            }
        }
    }

    public void swap(int a, int b) {
        int t = p[a];
        p[a] = p[b];
        p[b] = t;
    }

    public void reverse(int m) {
        int i = 0;
        int j = m;
        while (i < j) {
            swap(i, j);
            i++;
            j--;
        }
    }

    public static int fact(int n)  { return (n > 0) ? n * fact(n - 1) : 1; }

}