package domino;

import java.util.Arrays;

import static java.lang.System.arraycopy;

public class Perms {

    public  int[] getWithRepeatPerm;
    public  int[] getNotRepeatPerm;
    public  int[] indexI;
     int[]  indexINotRepeat;

    public  Perms(int nSize,int[] arr) { //конструктор
        getWithRepeatPerm  = new int[nSize];
        getNotRepeatPerm =  new int[nSize];
        arraycopy(arr ,0,getNotRepeatPerm,0,nSize);
        indexI = new int[nSize];
        indexINotRepeat =  new int[nSize];
    }

    public  int[] getFirstPerm(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    public  Boolean getNextPerm( int[] indexINotRepeat) {
        int n = getNotRepeatPerm.length;
        int i = n - 1;
        for (; i > 0 && getNotRepeatPerm[i] < getNotRepeatPerm[i - 1]; i--) ;
        if (i == 0)
            return false;
        int j = n - 1;
        for (; j >= 0 && getNotRepeatPerm[j] < getNotRepeatPerm[i - 1]; j--) ;
        swap(getNotRepeatPerm, i - 1, j);
        for (int k = indexINotRepeat[0]; k <= (n - i) / 2 - 1; k++) {
            indexINotRepeat[0]++;
            swap(getNotRepeatPerm, k + i, n - k - 1);
        }
        return true;
    }

     public Boolean nextPermWithRepetition(int[] getWithRepeatPerm, int n, int[] indexI) {
        for (int i = indexI[0]; i < getWithRepeatPerm.length; i++) {

            if (getWithRepeatPerm[i] == n) {
                getWithRepeatPerm[i] = 0;
//                indexI[0]++;
            } else {
                getWithRepeatPerm[i]++;
//                indexI[0]++;
                return true;
            }
        }
        return false;
    }

    public  void swap(int[] perm, int a, int b) {
        int t = perm[a];
        perm[a] = perm[b];
        perm[b] = t;
    }


}