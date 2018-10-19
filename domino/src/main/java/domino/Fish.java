package domino;

import java.util.ArrayList;

public class Fish {

    static boolean first=true;
    private int MAX_BONES_COUNT;
    private ArrayList bonesAllPlayers;
    private ArrayList<Bone>[] playersBones;


//    private ArrayList < Bone > [] playersBones = new ArrayList[PLAYERS_COUNT];
//    private ArrayList < Bone >  bonesAllPlayers = new ArrayList<Bone>();



    public Fish(ArrayList<Bone> bonesAll, ArrayList<Bone>[] pBones, int N) {
        bonesAllPlayers = bonesAll;
        playersBones  = pBones;
        MAX_BONES_COUNT = N;
    }

    public void GeneratePermutation(ArrayList bonesAllPlayers) {

        int max = bonesAllPlayers.size() - 1;

        int[] arr = new int[bonesAllPlayers.size()];
        for (int i=0; i<bonesAllPlayers.size();i++)
            arr[i]=i;

        int shift = max;


    }

    static public Boolean nextPermWithRepetition(int[] arr, int n) {
        for (int i = 0; i < arr.length; i++) {
            if (first){
                first=false;
                return true;
            }else
            if (arr[i] == n) {
                arr[i] = 0;
            }
            else
            {
                arr[i]++;
                return true;
            }
        }
        return false;
    }

    public boolean whose( int index )
    {

        if ( (index  >= 0) && (index  <= (MAX_BONES_COUNT-1) ))
            return true;
        else
            return false;

    }


//    public boolean testFish(int[] arr)
//    {
//        // for(int p = 0; p < PLAYERS_COUNT; p++) {
//        for (int i = 0; i < arr.length - 1; i++) {
//
//            int index = arr[i];
//            int index2 = arr[i+1];
//
//            if ( (whose(index) && whose(index2)) == true) {
//                for (int j = 0; j < MAX_BONES_COUNT; j++) {
//                    Bone boneTest = (Bone) bonesAllPlayers.get(i);
//                    Bone bonePlayers = playersBones[1].get(j);
//                    if ((boneTest.points(0) == bonePlayers.points(0)) || (boneTest.points(0) == bonePlayers.points(1)))
//                        return false;
//                }
//            } else
//            if  ( (whose(index) == false) && ( whose(index2) == false) ) {
//                for (int j = 0; j < MAX_BONES_COUNT; j++) {
//                    Bone boneTest = (Bone) bonesAllPlayers.get(i);
//                    Bone bonePlayers = playersBones[0].get(j);
//                    if ((boneTest.points(0) == bonePlayers.points(0)) || (boneTest.points(0) == bonePlayers.points(1)))
//                        return false;
//                }
//            }
//        }
//        bonesAllPlayers
//        int[] zero = new int[bonesAllPlayers.size()];
//        int countUseBonePlayers1=0; int countUseBonePlayers2=0;
//        int first = bonesAllPlayers.get(arr[0]).points(1);
//        int last = bonesAllPlayers.get(arr[0]).points(0);
//        if (whose(arr[0]) == true)
//            countUseBonePlayers1++;
//            //playersBones[0].remove(0);
//        else
//            countUseBonePlayers2++;
//        //playersBones[1].remove(0);
//
//        for (int i = 0; i < arr.length - 1; i++) {
//            while (zero[i] != 4) {
//                switch (zero[i]) {
//
//                    case 0:
//                        if (first == bonesAllPlayers.get(arr[i + 1]).points(0)) {
//                            first = bonesAllPlayers.get(arr[i + 1]).points(1);
//
//                            if (whose(arr[i+1]) == true)
//                                countUseBonePlayers1++;
//                                //playersBones[0].remove(0);
//                            else
//                                countUseBonePlayers2++;
//                            //playersBones[1].remove(0);
//                            zero[i]=3;
//                            break;
//                        }
//                        break;
//                    case 1:
//                        if (first == bonesAllPlayers.get(arr[i + 1]).points(1)) {
//                            first = bonesAllPlayers.get(arr[i + 1]).points(0);
//
//                            if (whose(arr[i+1]) == true)
//                                countUseBonePlayers1++;
//                                //playersBones[0].remove(0);
//                            else
//                                countUseBonePlayers2++;
//                            //playersBones[1].remove(0);
//                            zero[i]=3;
//                            break;
//                        }
//                        break;
//                    case 2:
//                        if (last == bonesAllPlayers.get(arr[i + 1]).points(0)) {
//                            last = bonesAllPlayers.get(arr[i + 1]).points(1);
//                            // zero[i]=4;
//                            if (whose(arr[i+1] ) == true)
//                                countUseBonePlayers1++;
//                                //playersBones[0].remove(0);
//                            else
//                                countUseBonePlayers2++;
//                            //playersBones[1].remove(0);
//                            zero[i]=3;
//                            break;
//                        }
//                        break;
//                    case 3:
//                        if (last == bonesAllPlayers.get(arr[i + 1]).points(1)) {
//                            last = bonesAllPlayers.get(arr[i + 1]).points(0);
//                            //zero[i]=4;
//                            if (whose(arr[i]) == true)
//                                countUseBonePlayers1++;
//                                //playersBones[0].remove(0);
//                            else
//                                countUseBonePlayers2++;
//                            //playersBones[1].remove(0);
//                            zero[i]=3;
//                            break;
//                        }
//                        break;
//                }
//                zero[i]++;
//            }
//
//            if (zero[i] == 4){
//                zero[i]=0;
//                zero[i+1]=1;
//            }
//        }
//        if ( ((first == bonesAllPlayers.get(0).points(1) ) && ( last == bonesAllPlayers.get(0).points(0))) ||
//                ( (countUseBonePlayers1< MAX_BONES_COUNT) && (countUseBonePlayers2 < MAX_BONES_COUNT) ) )
//
//            return true;
//        else
//            return false;
//
//    }
}
