package domino;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import static domino.Perms.fact;

public class Fish {

    static boolean first = true;
    private int MAX_BONES_COUNT;
    private ArrayList<Bone> bonesAllPlayers;
    private ArrayList<Bone>[] playersBones;


    public Fish(ArrayList<Bone> bonesAll, ArrayList<Bone>[] pBones, int N) {
        bonesAllPlayers = bonesAll;
        playersBones = pBones;
        MAX_BONES_COUNT = N;
    }

    public boolean GeneratePermutation(ArrayList bonesAllPlayers) {
        int max = bonesAllPlayers.size();
        Perms perms = new Perms(max);
        for (int i = 0; i < fact(max); i++) {
            first = true;
            if (testFish(perms.ArrPerms[i]))
                return true;
        }
//            first = true;
//            int[]  testarray = {1,2,0,3};
//            if (testFish(testarray))
//                return true;
        return false;
    }

    static public Boolean nextPermWithRepetition(int[] arr, int n) {
        for (int i = 0; i < arr.length; i++) {
            if (first) {
                first = false;
                return true;
            } else if (arr[i] == n) {
                arr[i] = 0;
            } else {
                arr[i]++;
                return true;
            }
        }
        return false;
    }

    public boolean isWhose(int index) {
        return ((index >= 0) && (index <= (MAX_BONES_COUNT - 1))) == true ? true : false;
    }

    public boolean tryPerms(int first, int last, int index1, int index2 , int[] arr, boolean[] tryLayOut) {
        if ((isWhose(index1) && isWhose(index2)) == true) {
            for (int i = 0; i < MAX_BONES_COUNT; i++) {
                Bone bonePlayers = playersBones[1].get(i);
                if (( tryLayOut[arr[i]] == false && first == bonePlayers.points(0))
                        || ( tryLayOut[arr[i]] == false &&  first == bonePlayers.points(1)) ||
                        (tryLayOut[arr[i]] == false &&  last == bonePlayers.points(0)) ||
                        (tryLayOut[arr[i]] == false &&  last == bonePlayers.points(1)))
                    return false;
            }
        } else if ((isWhose(index1) == false) && (isWhose(index2) == false)) {
            for (int i = 0; i < MAX_BONES_COUNT; i++) {
                Bone bonePlayers = playersBones[0].get(i);
                if ((tryLayOut[arr[i]] == false && first == bonePlayers.points(0)) ||
                        (tryLayOut[arr[i]] == false && first == bonePlayers.points(1)) ||
                        (tryLayOut[arr[i]] == false && last == bonePlayers.points(0)) ||
                        (tryLayOut[arr[i]] == false && last == bonePlayers.points(1)))
                    return false;
            }
        }
        return true;
    }

    public boolean tryJoin(int first, int last, int index, int[] arr, boolean[] tryLayOut) {
        for (int i = index; i <= bonesAllPlayers.size()-1; i++)
            if ( (tryLayOut[arr[i ]] == false && first == bonesAllPlayers.get(arr[i ]).points(0)) ||
                    (first == bonesAllPlayers.get(arr[i ]).points(1) && tryLayOut[arr[i]] == false) ||
                    ( tryLayOut[arr[i ]] == false && last == bonesAllPlayers.get(arr[i]).points(0)) ||
                    ( tryLayOut[arr[i ]] == false && last == bonesAllPlayers.get(arr[i]).points(1)))
                return true;
        return false;
    }

    public boolean tryJoinTwo(int first, int last, int index, int[] arr, boolean[] tryLayOut) {
//        for (int i = index; i <= bonesAllPlayers.size()-1; i++)
        if ( (tryLayOut[arr[index]] == false && first == bonesAllPlayers.get(arr[index]).points(0)) ||
                (first == bonesAllPlayers.get(arr[index]).points(1) && tryLayOut[arr[index]] == false) ||
                ( tryLayOut[arr[index]] == false && last == bonesAllPlayers.get(arr[index]).points(0)) ||
                ( tryLayOut[arr[index]] == false && last == bonesAllPlayers.get(arr[index]).points(1)))
            return true;
        return false;
    }

    public boolean testFish(int[] arr) {

        int size = arr.length;
        boolean[] tryLayOut = new boolean[size];
        int[] arrSPovtor = new int[size];

        while (nextPermWithRepetition(arrSPovtor, 3))
        {
            int first = bonesAllPlayers.get(arr[0]).points(1);
            int last = bonesAllPlayers.get(arr[0]).points(0);
//            boolean[] tryLayOut = new boolean[size];
            Arrays.fill(tryLayOut,false);
            tryLayOut[arr[0]] = true;
            int countUseBonePlayers1=0; int countUseBonePlayers2=0;
            if (isWhose(arr[0]) == true)
                countUseBonePlayers1++;
            else
                countUseBonePlayers2++;

            for (int i = 0; i < arr.length - 1; i++) {
                if (!tryPerms(first, last, arr[i],arr[i+1],arr,tryLayOut))
                    return false;
                else
                    switch (arrSPovtor[i]) {

                        case 0:
                            if (first == bonesAllPlayers.get(arr[i + 1]).points(0)) {
                                first = bonesAllPlayers.get(arr[i + 1]).points(1);
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i+1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;

                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr,tryLayOut))
                                if (tryJoinTwo(first, last, i + 1, arr,tryLayOut))
                                    i = arr.length;
                                else
                                    return false;
                            else
                                if ( countUseBonePlayers1==MAX_BONES_COUNT || countUseBonePlayers2==MAX_BONES_COUNT)
                                    return false;
                                else
                                    return true;
                            break;
                        case 1:
                            if (first == bonesAllPlayers.get(arr[i + 1]).points(1)) {
                                first = bonesAllPlayers.get(arr[i + 1]).points(0);
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i+1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr,tryLayOut))
                                if (tryJoinTwo(first, last, i + 1, arr,tryLayOut))
                                    i = arr.length;
                                else
                                    return false;
                            else
                                if ( countUseBonePlayers1==MAX_BONES_COUNT || countUseBonePlayers2==MAX_BONES_COUNT)
                                    return false;
                                else
                                    return true;
                            break;
                        case 2:
                            if (last == bonesAllPlayers.get(arr[i + 1]).points(0)) {
                                last = bonesAllPlayers.get(arr[i + 1]).points(1);
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i+1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr,tryLayOut))
                                if (tryJoinTwo(first, last, i + 1, arr,tryLayOut))
                                    i = arr.length;
                                else
                                    return false;
                            else
                                if ( countUseBonePlayers1==MAX_BONES_COUNT || countUseBonePlayers2==MAX_BONES_COUNT)
                                    return false;
                                else
                                    return true;
                            break;
                        case 3:
                            if (last == bonesAllPlayers.get(arr[i + 1]).points(1)) {
                                last = bonesAllPlayers.get(arr[i + 1]).points(0);
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i+1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr,tryLayOut))
                                if (tryJoinTwo(first, last, i + 1, arr,tryLayOut))
                                    i = arr.length;
                                else
                                    return false;
                            else
                                if ( countUseBonePlayers1==MAX_BONES_COUNT || countUseBonePlayers2==MAX_BONES_COUNT)
                                    return false;
                                else
                                    return true;
                            break;
                    }
            }
        if (tryLayOut[arr.length - 1]==true )
                    return false;
        }
        return true;
    }

}


