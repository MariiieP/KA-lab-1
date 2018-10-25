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
    boolean[] tryLayOut;
    int[] arrSPovtor;
    int[] PermsP;


    public Fish(ArrayList<Bone> bonesAll, ArrayList<Bone>[] pBones, int N, boolean[] trueF, int[] arrP, int[] PermNotPov) {
        bonesAllPlayers = bonesAll;
        playersBones = pBones;
        MAX_BONES_COUNT = N;
        tryLayOut = trueF;
        arrSPovtor = arrP;
        PermsP = PermNotPov;
    }

    public boolean generatePermutation(ArrayList bonesAllPlayers) {
        int max = bonesAllPlayers.size();
        Perms perms = new Perms(max);
        for (int i = 0; i < fact(max); i++) {
            first = true;
            PermsP = perms.ArrPerms[i];
            if (testFish(PermsP, tryLayOut, arrSPovtor))
                return true;
        }
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

    private boolean isWhose(int index) {
        return ((index >= 0) && (index <= (MAX_BONES_COUNT - 1))) == true ? true : false;
    }

    private boolean Help(int first, int last, int index) {
        return (first == bonesAllPlayers.get(index).points(0) || first == bonesAllPlayers.get(index).points(1) ||
                last == bonesAllPlayers.get(index).points(0) || last == bonesAllPlayers.get(index).points(1));
    }

    private boolean tryJoin(int first, int last, int index, int[] arr, boolean[] tryLayOut) {
        for (int i = index; i <= bonesAllPlayers.size() - 1; i++)
            if (tryJoinNeighbor(first, last, arr[i], tryLayOut))
                return true;
        return false;
    }

    private boolean tryJoinNeighbor(int first, int last, int index, boolean[] tryLayOut) {
        return (tryLayOut[index] == false && Help(first, last, index));
    }

    private boolean tryPerms(int first, int last, int index1, int index2, int[] arr, boolean[] tryLayOut) {
        if ((isWhose(index1) && isWhose(index2)) == true) {
            for (int i = MAX_BONES_COUNT; i < bonesAllPlayers.size(); i++) {
                if (tryLayOut[arr[i]] == false && Help(first, last, i))
                    return false;
            }
        } else if ((isWhose(index1) == false) && (isWhose(index2) == false)) {
            for (int i = 0; i < MAX_BONES_COUNT; i++) {
                if (tryLayOut[arr[i]] == false && Help(first, last, i))
                    return false;
            }
        }
        return true;
    }


    private boolean testFish(int[] arr, boolean[] tryLayOut, int[] arrSPovtor) {
        int size = arr.length;
        Arrays.fill(arrSPovtor, 0);
        while (nextPermWithRepetition(arrSPovtor, 3)) {
            int first = bonesAllPlayers.get(arr[0]).points(1);
            int last = bonesAllPlayers.get(arr[0]).points(0);
            Arrays.fill(tryLayOut, false);
            tryLayOut[arr[0]] = true;
            int countUseBonePlayers1 = 0;
            int countUseBonePlayers2 = 0;
            if (isWhose(arr[0]) == true)
                countUseBonePlayers1++;
            else
                countUseBonePlayers2++;

            for (int i = 0; i < arr.length - 1; i++) {
                if (!tryPerms(first, last, arr[i], arr[i + 1], arr, tryLayOut))
                    return false;
                else
                    switch (arrSPovtor[i]) {

                        case 0:
                            if (first == bonesAllPlayers.get(arr[i + 1]).points(0)) {
                                first = bonesAllPlayers.get(arr[i + 1]).points(1);
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i + 1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;

                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr, tryLayOut))
                                if (tryJoinNeighbor(first, last, arr[i + 1], tryLayOut))
                                    i = arr.length;
                                else
                                    return false;
                            else
                                return !(countUseBonePlayers1 == MAX_BONES_COUNT || countUseBonePlayers2 == MAX_BONES_COUNT);
                            break;
                        case 1:
                            if (first == bonesAllPlayers.get(arr[i + 1]).points(1)) {
                                first = bonesAllPlayers.get(arr[i + 1]).points(0);
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i + 1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr, tryLayOut))
                                if (tryJoinNeighbor(first, last, arr[i + 1], tryLayOut))
                                    i = arr.length;
                                else
                                    return false;
                            else
                                return !(countUseBonePlayers1 == MAX_BONES_COUNT || countUseBonePlayers2 == MAX_BONES_COUNT);
                            break;
                        case 2:
                            if (last == bonesAllPlayers.get(arr[i + 1]).points(0)) {
                                last = bonesAllPlayers.get(arr[i + 1]).points(1);
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i + 1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr, tryLayOut))
                                if (tryJoinNeighbor(first, last, arr[i + 1], tryLayOut))
                                    i = arr.length;
                                else
                                    return false;
                            else
                                return !(countUseBonePlayers1 == MAX_BONES_COUNT || countUseBonePlayers2 == MAX_BONES_COUNT);
                            break;
                        case 3:
                            if (last == bonesAllPlayers.get(arr[i + 1]).points(1)) {
                                last = bonesAllPlayers.get(arr[i + 1]).points(0);
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i + 1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr, tryLayOut))
                                if (tryJoinNeighbor(first, last, arr[i + 1], tryLayOut))
                                    i = arr.length;
                                else
                                    return false;
                            else
                                return !(countUseBonePlayers1 == MAX_BONES_COUNT || countUseBonePlayers2 == MAX_BONES_COUNT);
                            break;
                    }
            }
            if (tryLayOut[arr.length - 1] == true)
                return false;
        }
        return true;
    }

}


