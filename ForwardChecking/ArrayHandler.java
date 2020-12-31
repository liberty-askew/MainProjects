import java.util.ArrayList;

/**
 * This class is used by MAC and Forward for basic array operations.
 */
public class ArrayHandler {

    public ArrayHandler(){}

    /**
     * Takes two arrays for equal length and iterates through multiplying each element. Ultimately returns the
     * intersection of the two lists.
     * e.g. ([0,1,0] , [1,1,0]) -> ([0,1,0])
     * @param array1 - first array to be multiplied (same length as array2)
     * @param array2 - second array to be multiplied (same lenght as array1)
     * @return - binary array of length array1 = length array2
     */
    public int[] MultArray(int[] array1, int[] array2) {

        int[] result = new int[array1.length];
        for (int i = 0; i < array1.length; i++) {
            result[i] = array1[i]*array2[i];
        }
        return result;
    }

    /**
     * Used for smallest domain first ordering. Finds the index (0..n) of the unassigned variable in the varList with
     * the smallest domain.
     * @param varList - list of unassigned variables.
     * @param board - pruned chess board of binary values.
     * @return integer for index of variable with smallest domain.
     */
    public int MinDomainIn(ArrayList<Integer> varList , int[][] board){

        int j = 1000; //arbitrarily large number to begin.
        int minvar = 0; //default value to initialize.
        for(int i = 0; i < varList.size(); i++){
            if(SumArray(board[varList.get(i)])<j){
                minvar = varList.get(i);
                j = SumArray(board[i]);
            }
        }
        return minvar;
    }


    /**
     * Find the sum of an array
     * @param array - array to sum
     * @return - integer answer.
     */
    public int SumArray(int[] array){

        int sum = 0;
        for(int i = 0; i < array.length; i++){
            sum += array[i];
        }
        return sum;
    }

    /**
     * Finds the index (0..n) of the first digit 1 in an array. Used for assigning values from domain / chess board.
     * Index of first 1 give the smallest value in the domain.
     * @param array - array to search
     * @return - integer of index of first 1 or -1 if no occurence.
     */
    public int FirstOne(int[] array) {

        for (int i = 0; i < array.length ; i++) {
            if (array[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Finds the union of 2 binary arrays of the same length
     * e.g. ([0,1,0] , [1,1,0]) -> ([1,1,0])
     * @param array1 - binary array
     * @param array2 - binary array
     * @return array of equal length to array1 and array2
     */
    public int[] UnionArray(int[] array1 ,  int[] array2){
        int[] newarray = new int[array1.length];
        for (int i = 0; i < array1.length ; i++) {
            if (array1[i] == 1 || array2[i] == 1) {
                newarray[i] = 1;
            }
            else{
                newarray[i] = 0;
            }
        }
        return newarray;
    }
}
