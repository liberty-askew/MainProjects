import java.util.* ;

/**
 * Forward checking constraint solver algorithm for n-queens problem.
 */
public class Forward {

    ArrayList<Integer> master; //master represents the order of assigned values so far.
    ArrayList<Integer> assigVar; //list of assigned variables
    final int n; //dimensions of the nxn chess grid.
    int [][] board; // represents playing board. Binary values where 0 represents a unsupported node and 1 represents an supported node.
    String ordering; //true if smallest domain first, false if
    ArrayHandler arh; //ArrayHandler object for array operations.

    public static void main(String[] args) {

        if(args[1] != "sdf" && args[1] != "asc") {
            System.out.println("Please enter <sdf> (smallest domain first) or <asc> (ascending) for ordering.");
        }
        new Forward(Integer.parseInt(args[0]), args[1]);
    }

    /**
     * Initializes class and starts forward checking algorithm. Also assigns class attributes.
     * @param n - dimensions of nxn grid.
     * @param ordering - ordering type for variables: smallest domain first or ascending.
     */
    public Forward(int n, String ordering) {

        this.n = n;
        this.master = new ArrayList<>();
        this.assigVar = new ArrayList<>();
        this.arh = new ArrayHandler();
        this.ordering = ordering;
        ArrayList<Integer> varList = new ArrayList<>();
        for(int i = 0; i<n ; i++) {
            varList.add(i); //populates initial varList with all variables
        }
        for (int i = 0 ; i < n ; i++) {
            for (int j = 0 ; j < n ; j++) {
                board[i][j] = 1; //fills board with 1's at start.
            }
        }
        System.out.println(ForwardChecking(varList));
    }

    /**
     * Forward checking algorithm. Recursive because calls BranchLeft and BranchRight which again call Forward checking.
     * @param varList - list of unassigned variables.
     * @return - returns master array list.
     */
    public int[] ForwardChecking(ArrayList<Integer> varList){

        try {
            while (master.size() != n) { //continues until all the nodes are assigned. TODO: add a catch in case it is unsolvable.
                int var = 0; //initialize var
                if(ordering == "sdf") {
                    // for smallest domain first call MinDomainIn function to find index of variable with smallest domain
                    // and select this one.
                    var = varList.get(arh.MinDomainIn(varList,board));
                    assigVar.add(var);
                    varList.remove(arh.MinDomainIn(varList,board));
                }
                if(ordering == "asc") {
                    // for ascending variable assignment, because varList was initialized with ascending values, can
                    // take the first variable from the varList.
                    var = varList.get(0);
                    assigVar.add(var);
                    varList.remove(0);
                }
                int val = arh.FirstOne(board[var]); //assign value of first occuring '1' on board, i.e smallest value in domain of var.
                if (val == -1){
                    throw new Exception(); //sums to -1 if 1 is not on the row i.e no more possible assignments.
                }
                BranchLeft(varList,var,val);
                BranchRight(varList,var,val);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        int[] result = new int[n];
        for (int i = 0 ; i < n ; i++) { //Reorders master list incase of sdf, where ith value in array corresponds to value of ith variable
            result[assigVar.get(i)] = master.get(assigVar.get(i));
        }
        return result;
    }

    /**
     * Handles left branch of algorithm. Recursive because calls the ForwardChecking method in itself and is called by
     * ForwardChecking.
     * @param varList - list of unassigned varaibles
     * @param var - variable which is being assigned
     * @param val - value assigned to given variable
     */
    public void BranchLeft(ArrayList<Integer> varList , int var , int val) {

        master.add(val); //assign value to variable and add to main.
        int[][] old_board = board; //store the previous version of the board before pruning.
        if (ReviseFutureArcs(varList, var, val)){
            ForwardChecking(varList); //recursion step
        }
        else {
            board = old_board;// resets domains if revise future arcs is unsuccessful.
            board[var][val]  = 0; //removes the value from the variables domain.
            varList.add(0,var); // returns the variable to the varList.
            assigVar.remove(assigVar.size()-1);
            master.remove(master.size()-1); //removes the variable from the master list.
        }
    }

    /**
     * Handles right branching of algorithm. Recursive because calls the ForwardChecking method in itself and is
     * called by ForwardChecking.
     * @param varList - list of unassigned variables
     * @param var - variable which has been assigned on left branch
     * @param val - value assigned to given variable on left branch
     */
    public void BranchRight(ArrayList<Integer> varList , int var , int val){

        int[][] old_board = board; //saves old version of the board incase needs resetting for failure.
        board[var][val]  = 0; //removes value from variable domain.
        if(arh.SumArray(board[val]) != 0){ //if domain not empty
            if(ReviseFutureArcs(varList , var , val)){
                ForwardChecking(varList); //recursive step.
            }
            else{
                board = old_board; //if revising future arcs fails, this resets the board.
            }
        }
        else{
            board[var][val] = 1; // if results in empty domain then returns the value to the variable domain.
        }
    }

    /**
     * Called in left and right branch for checking the future arcs for unassigned variables after an assignment.
     * @param varList - list of unassigned variables.
     * @param var - variable which has just been assigned.
     * @param val - value which has just been assigned to that value.
     * @return
     */
    public boolean ReviseFutureArcs(ArrayList<Integer> varList , int var, int val) {

        boolean consistent = true; //intialization
        for(int i = 0 ; i < varList.size(); i++){ //loop iterates ot check new assignment against all unassigned variables.
            consistent = revise(varList.get(i) , var , val);
            if(!consistent){
                return false;
            }
        }
        return true;
    }

    /**
     * Revises a specific arc to find supported nodes for given value from binary csp.
     * @param futureVar - from revisefuturearcs, forms one end of the arc.
     * @param var - variable being assigned, forms other end of arc.
     * @param val - value assigned to variable, looking for support on arc.
     * @return
     */
    public boolean revise(int futureVar , int var , int val) {

        BinaryCSPReader bscp = new BinaryCSPReader();
        int[] supportvec = bscp.getBinaryArray(n+"Queens.csp" , var , futureVar , val,n);
        board[futureVar] = arh.MultArray(board[futureVar] , supportvec); //finds intersection of two arrays.
        if (arh.SumArray(board[futureVar])== 0) { //if all filled up
            return false;
        }
        return true;
    }
}
