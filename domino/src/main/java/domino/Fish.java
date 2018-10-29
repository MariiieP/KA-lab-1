package domino;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.arraycopy;

//import static domino.Perms.fact;

public class Fish {

    static boolean first = true;
    private int MAX_BONES_COUNT;
    private List<Bone.BoneModel> bonesAllPlayers;
    private List<Bone.BoneModel>[] playersBones;
    boolean[] tryLayOut;
    int[] arrSPovtor;
    int[] PermsP;


    public Fish(List<Bone.BoneModel> bonesAll, List<Bone.BoneModel>[] pBones, int N, boolean[] trueF, int[] arrP, int[] PermNotPov) {
        bonesAllPlayers =  bonesAll;
        playersBones = pBones;
        MAX_BONES_COUNT = N;
        tryLayOut = trueF;
        arrSPovtor = arrP;
        PermsP = PermNotPov;
    }

    public boolean generatePermutation(List bonesAllPlayers) {
        int max = bonesAllPlayers.size();
        int[] arr1 = new int[max];
        for (int i=0;i<max; i++)
            arr1[i]=i;
        Perms perm = new Perms(max,arr1);

        int[] indexI = new int[1];indexI[0]=0;
        int[] indexINotRepeat = new int[1];indexINotRepeat[0]=0;
        boolean[] check = new boolean[1];check[0]=true;


        int[] notPerm = {3,0,1,5,2,4}; int[] withPerm = {3,3,2,0,0,0};

        if (testFish(notPerm, tryLayOut, withPerm, check)) {
                arraycopy(withPerm, 0,arrSPovtor,0,max);
                arraycopy(notPerm, 0,PermsP,0,max);
                return true;
            }


//        if( first) {
//            if (testFish(perm.getNotRepeatPerm, tryLayOut, perm.getWithRepeatPerm, check)) {
//                arraycopy(perm.getWithRepeatPerm, 0,arrSPovtor,0,max);
//                arraycopy(perm.getNotRepeatPerm, 0,PermsP,0,max);
//                return true;
//            }
//            check[0]=true;
//            while (perm.nextPermWithRepetition(perm.getWithRepeatPerm, 3, indexI) && check[0]) {
//                if (check[0])
////            if (  perm.nextPermWithRepetition(permsMax,3,indexI){
//                    if (testFish(perm.getNotRepeatPerm, tryLayOut, perm.getWithRepeatPerm, check)){
//                        arraycopy(perm.getWithRepeatPerm, 0,arrSPovtor,0,max);
//                        arraycopy(perm.getNotRepeatPerm, 0,PermsP,0,max);
//                        return true;
//                    }
////                check[0] = true;
//            }
//            check[0] = true;
//            Arrays.fill(perm.getWithRepeatPerm, 0);
////            first = true;
//        }
//
//        while (perm.getNextPerm(indexINotRepeat)) {
//
//            if( first)
//                if (testFish(perm.getNotRepeatPerm, tryLayOut, perm.getWithRepeatPerm, check)) {
//                    arraycopy(perm.getWithRepeatPerm, 0,arrSPovtor,0,max);
//                    arraycopy(perm.getNotRepeatPerm, 0,PermsP,0,max);
////                    first=false;
//                    return true;
//                }
//            first=false;
//
//
//            while(perm.nextPermWithRepetition(perm.getWithRepeatPerm,3,indexI) && check[0]){
//                if (check[0])
////            if (  perm.nextPermWithRepetition(permsMax,3,indexI){
//                    if (testFish(perm.getNotRepeatPerm, tryLayOut, perm.getWithRepeatPerm, check)){
//                        arraycopy(perm.getWithRepeatPerm, 0,arrSPovtor,0,max);
//                        arraycopy(perm.getNotRepeatPerm, 0,PermsP,0,max);
//                        return true;
//                    }
//
//            }
//            check[0]=true; Arrays.fill(perm.getWithRepeatPerm,0);first=true;
//        }




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
        return (first == bonesAllPlayers.get(index).getLeftPoint() || first == bonesAllPlayers.get(index).getRightPoint() ||
                last == bonesAllPlayers.get(index).getLeftPoint() || last == bonesAllPlayers.get(index).getRightPoint());
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


    private boolean testFish(int[] arr, boolean[] tryLayOut, int[] arrSPovtor,boolean[] check) {
        int size = arr.length;
//        Arrays.fill(arrSPovtor, 0);
//        while (nextPermWithRepetition(arrSPovtor, 3)) {
            int first = bonesAllPlayers.get(arr[0]).getRightPoint();
            int last = bonesAllPlayers.get(arr[0]).getLeftPoint();
            Arrays.fill(tryLayOut, false);
            tryLayOut[arr[0]] = true;
            int countUseBonePlayers1 = 0;
            int countUseBonePlayers2 = 0;
            if (isWhose(arr[0]) == true)
                countUseBonePlayers1++;
            else
                countUseBonePlayers2++;

            for (int i = 0; i < arr.length - 1; i++) {
                if (!tryPerms(first, last, arr[i], arr[i + 1], arr, tryLayOut)) {
                    check[0]=false;
                    return false;

                }
                else
                    switch (arrSPovtor[i]) {

                        case 0:
                            if (first == bonesAllPlayers.get(arr[i + 1]).getLeftPoint()) {
                                first = bonesAllPlayers.get(arr[i + 1]).getRightPoint();
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i + 1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;

                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr, tryLayOut))
                                if (tryJoinNeighbor(first, last, arr[i + 1], tryLayOut)) {
                                    i = arr.length;
                                    return false;
                                }
                                else{
                                    check[0]=false;
                                    return false;
                                }
                            else
                                return !(countUseBonePlayers1 == MAX_BONES_COUNT || countUseBonePlayers2 == MAX_BONES_COUNT);
//                            break;
                        case 1:
                            if (first == bonesAllPlayers.get(arr[i + 1]).getRightPoint()) {
                                first = bonesAllPlayers.get(arr[i + 1]).getLeftPoint();
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i + 1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr, tryLayOut))
                                if (tryJoinNeighbor(first, last, arr[i + 1], tryLayOut)) {
                                    i = arr.length;
                                    return false;
                                }
                                else{
                                    check[0]=false;
                                    return false;
                                }
                            else
                                return !(countUseBonePlayers1 == MAX_BONES_COUNT || countUseBonePlayers2 == MAX_BONES_COUNT);
//                            break;
                        case 2:
                            if (last == bonesAllPlayers.get(arr[i + 1]).getLeftPoint ()) {
                                last = bonesAllPlayers.get(arr[i + 1]).getRightPoint();
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i + 1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr, tryLayOut))
                                if (tryJoinNeighbor(first, last, arr[i + 1], tryLayOut)){
                                    i = arr.length;
                                    return false;
                                }
                                else{
                                    check[0]=false;
                                    return false;
                                }
                            else
                                return !(countUseBonePlayers1 == MAX_BONES_COUNT || countUseBonePlayers2 == MAX_BONES_COUNT);
//                            break;
                        case 3:
                            if (last == bonesAllPlayers.get(arr[i + 1]).getRightPoint()) {
                                last = bonesAllPlayers.get(arr[i + 1]).getLeftPoint();
                                tryLayOut[arr[i + 1]] = true;

                                if (isWhose(arr[i + 1]) == true)
                                    countUseBonePlayers1++;
                                else
                                    countUseBonePlayers2++;
                                break;
                            }
                            if (tryJoin(first, last, i + 1, arr, tryLayOut))
                                if (tryJoinNeighbor(first, last, arr[i + 1], tryLayOut)) {
                                    i = arr.length;
                                    return false;
                                }
                                else{
                                    check[0]=false;
                                    return false;
                                }
                            else
                                return !(countUseBonePlayers1 == MAX_BONES_COUNT || countUseBonePlayers2 == MAX_BONES_COUNT);
//                            break;
                    }
            }
            if (tryLayOut[arr.length - 1] == true)
                return false;
//        }
        return true;
    }

}


