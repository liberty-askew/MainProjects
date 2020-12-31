import java.util.ArrayList;

/**
 * MAC3 constraint solving algorithm implementing AC3 for n-queens problem.
 */
public class MAC{

    ArrayList<Integer> master; //list of values assigned to varaibles.
    ArrayList<Integer> assigVar; //list of assigned variables
    ArrayHandler arh; //ArrayHandler object for array operations.
    final int n;
    int[][] board;
    String ordering;

    public static void main(String[] args){

        if(!args[1].equals("sdf") && !args[1].equals("asc")){
            System.out.println("Please enter <sdf> (smallest domain first) or <asc> (ascending) for ordering.");
        }
        new MAC(Integer.parseInt(args[0]), args[1]);
    }

    /**
     * Initializes MAC3.
     * @param n - integer dimension
     */
    public MAC (int n, String ordering) {

        this.n = n;
        this.ordering = ordering;
        this.master = new ArrayList<>();
        this.assigVar = new ArrayList<>();
        this.arh = new ArrayHandler();
        this.board = new int[n][n];
        ArrayList<Integer> varList = new ArrayList<>();
        for(int i = 0; i<n ; i++) {
            varList.add(i); //populates initial varList with all variables (0..n) in ascending order.
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                board[i][j] = 1; //fills board with 1's at start.
            }
        }
        System.out.println(MAC3(varList).toString());
    }

    /**
     * Implements the MAC3 algorithm. Recursive as calls itself if AC3 (checking for
     * arc consistency) is successful.
     * @param varList - List of unassigned variables.
     * @return - ArrayList of value assignment. Where ith value in array corresponds
     *. to value of ith variable.
     */
    public int[] MAC3(ArrayList<Integer> varList) {

        try {
            int var = 0;
            while (master.size() != n) { //continues until all the nodes are assigned.
                if (ordering.equals("sdf")) {
                    // for smallest domain first call MinDomainIn function to find
                    // index of variable with smallest domain
                    // and select this one.
                    var = varList.get(arh.MinDomainIn(varList, board));
                    varList.remove(arh.MinDomainIn(varList, board));
                    assigVar.add(var); //adds var to assigned variables.
                }
                if (ordering.equals("asc")) {
                    // for ascending variable assignment, because varList was
                    //initialized with ascending values, can
                    // take the first variable from the varList.
                    var = varList.get(0);
                    varList.remove(0);
                    assigVar.add(var);
                }
                //assign value of first occuring '1' on board, i.e smallest value in
                // domain of var.
                int val = arh.FirstOne(board[var]);
                master.add(val);
                if (val == -1) {
                //sums to -1 if 1 is not on the row i.e no more possible assignments.
                    throw new Exception();
                }
                int[][] oldboard = board;
                if (AC3(varList)) {
                    MAC3(varList); //recursive step
                } else {
                    board = oldboard; //undo pruning
                    board[var][val] = 0; //assign 0 to remove this value from the variable's domain.
                    varList.add(0, var); //return variable to unassigned list. Must be at start incase of asc ordering
                    master.remove(master.size()-1); //remove variable from master list
                    assigVar.remove(assigVar.size()-1); //removes variable from assigned variable list.
                }
                if (arh.SumArray(board[var]) != 0) { //domain wipeout if true
                    oldboard = board; //store old board incase need to undo pruning.
                    if (AC3(varList)) {
                        MAC3(varList); //recursive step.
                    } else {
                        board = oldboard; //undo pruning
                    }
                } else {
                    varList.add(0,var); //unassign & replace val in domain. Must be at start incase of asc ordering
                    master.remove(master.size()-1); //remove variable from master list
                    assigVar.remove(assigVar.size()-1); //removes variable from assigned variable list.
                }
            }
        }
        catch (Exception e) { //exception handling.
            System.out.println(e.getMessage());
        }
        int[] result = new int[n];
         //Reorders master list incase of sdf, where ith value in array corresponds
         // to value of ith variable
        for (int i = 0 ; i < n ; i++) {
            result[assigVar.get(i)] = master.get(assigVar.get(i));
        }
        return result;
    }

    /**
     * Handles AC3 algorithm as called by MAC3.
     * @param varList - list of unassigned variables.
     * @return - true if successful, false if failure
     */
    public boolean AC3(ArrayList<Integer> varList){

        ArrayList< int[] > queue = new ArrayList<int[]>();
        int var = assigVar.get(master.size()-1);   // variable which has just been assigned
        int val = master.get(master.size()-1); // value which has just been assigned to said variable
        board[var] = new int[n]; //sets the new board for assigned value
        board[var][val] = 0; //adds 0 to location of new placement, removes it from domain.
        for(int i = var+1 ; i <n ;i++){
            queue.add(new int[]{i, val}); //adds all subsequent arcs checking back to one which has just been assigned.
        }
        while(queue.size() != 0) {
            int[] currentarc = queue.get(0); //pop off first arc from queue.
            queue.remove(0);
            int[] oldline = board[currentarc[0]]; //stores old line to check if changes made.
            BinaryCSPReader bscp = new BinaryCSPReader();
            int[] supported = new int[n]; //storage for supported arcs
            for (int i = 0 ; i <n ;i++) {
                if (board[currentarc[0]][i] == 1 ) { //checks support for available values in domain
                    supported = arh.UnionArray(supported,bscp.getBinaryArray(n +
                            "Queens.csp", currentarc[0], currentarc[1], i , n));
                                                //fills board with supported vectors
                }
            }
            //returns binary array with 1 at index of values still supported.
            board[currentarc[0]] = arh.MultArray(board[currentarc[0]],supported);
            if(arh.SumArray(board[currentarc[0]])==0){ //if domain wipeout
                return false;
            }
            if(board[currentarc[0]] != oldline) { //checks for change in domain
                for(int i = 0 ; i < varList.size();i++ ){
                    if(i != currentarc[0]){ //repopulates queue.
                        if(!queue.contains(new int[] {i , currentarc[0]})){ //checks if value already exists in queue.
                            queue.add(new int[]{i , currentarc[0]});
                        }
                    }
                }
            }
        }
        return true; //only reaches this point if empties queue without domain wipeout.
    }
}
