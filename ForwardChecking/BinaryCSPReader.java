import java.io.* ;
import java.util.* ;

/**
 * A reader tailored for binary extensional CSPs.
 * It is created from a FileReader and a StreamTokenizer
 */
public final class BinaryCSPReader {
  private FileReader inFR ;
  private StreamTokenizer in ;

  /**
   * Main (for testing)
   */
  public static void main(String[] args) {
    if (args.length != 1) {
      System.out.println("Usage: java BinaryCSPReader <file.csp>") ;
      return ;
    }
    BinaryCSPReader reader = new BinaryCSPReader();
    //System.out.println(reader.readBinaryCSP(args[0])) ;
    //System.out.println("TEST..."+reader.readBinaryForNodesCSP(args[0], 1 , 2 , 1) ) ;
  }

  /**
   * File format:
   * <no. vars>
   * NB vars indexed from 0
   * We assume that the domain of all vars is specified in terms of bounds
   * <lb>, <ub> (one per var)
   * Then the list of constraints
   * c(<varno>, <varno>)
   * binary tuples
   * <domain val>, <domain val>
   */
  public BinaryCSP readBinaryCSP(String fn) {
    try {
      inFR = new FileReader(fn) ;
      in = new StreamTokenizer(inFR) ;
      in.ordinaryChar('(') ;
      in.ordinaryChar(')') ;
      in.nextToken() ;                                         // n
      int n = (int)in.nval ;
      int[][] domainBounds = new int[n][2] ;
      for (int i = 0; i < n; i++) {
	      in.nextToken() ;                                  // ith ub
	      domainBounds[i][0] = (int)in.nval ;
		    in.nextToken() ;                                   // ','
		    in.nextToken() ;
	      domainBounds[i][1] = (int)in.nval ;
      }
      ArrayList<BinaryConstraint> constraints = readBinaryConstraints();
      BinaryCSP csp = new BinaryCSP(domainBounds, constraints);
      inFR.close() ;
      return csp;
    }
    catch (FileNotFoundException e) {System.out.println(e);}
    catch (IOException e) {System.out.println(e);}
    return null ;
  }


  private ArrayList<BinaryConstraint> readBinaryConstraints() {
    ArrayList<BinaryConstraint> constraints = new ArrayList<BinaryConstraint>() ;

    try {
      in.nextToken() ;                                  //'c' or EOF
      while(in.ttype != in.TT_EOF) {
	      // scope
	      in.nextToken() ;                                       //'('
		    in.nextToken() ;                                       //var
	      int var1 = (int)in.nval ;
		    in.nextToken() ;                                       //','
		    in.nextToken() ;                                       //var
        int var2 = (int)in.nval ;
		    in.nextToken() ;                                       //')'
        //tuples
		    ArrayList<BinaryTuple> tuples = new ArrayList<BinaryTuple>() ;
        in.nextToken() ;              //1st allowed val of 1st tuple
        while (!"c".equals(in.sval) && (in.ttype != in.TT_EOF)) {
          int val1 = (int)in.nval ;
	        in.nextToken() ;                                   //','
	        in.nextToken() ;                               //2nd val
		      int val2 = (int)in.nval ;
		      tuples.add(new BinaryTuple(val1, val2)) ;
		      in.nextToken() ;      //1st allowed val of next tuple/c/EOF
		    }
        BinaryConstraint c = new BinaryConstraint(var1, var2, tuples) ;
        constraints.add(c) ;
      }

      return constraints ;
    }
    catch (IOException e) {System.out.println(e);}
    return null ;
  }


    /** Additional method, finds all the supported values on node var2 for arc var1 - var2 with var1 = val1.
     * Based on readbinaryCSP and uses getBinaryConstraints.
     * @param fn   - String, name of CSP file e.g 4Queens.csp
     * @param var1 - Arc start variable
     * @param var2 - Arc end variable
     * @param val1 - Value assigned to var 1.
     * @return a list from a file with the possible nodes that can be moved from val1 when comparing
     *         var1 and var2.
     */
    public ArrayList<Integer> readBinaryForNodesCSP(String fn, int var1, int var2 , int val1) {

        try {
            inFR = new FileReader(fn) ;
            in = new StreamTokenizer(inFR) ;
            in.ordinaryChar('(') ;
            in.ordinaryChar(')') ;
            in.nextToken() ;                                         // n
            int n = (int)in.nval ;
            int[][] domainBounds = new int[n][2] ;
            for (int i = 0; i < n; i++) {
                in.nextToken() ;                                  // ith ub
                domainBounds[i][0] = (int)in.nval ;
                in.nextToken() ;                                   // ','
                in.nextToken() ;
                domainBounds[i][1] = (int)in.nval ;
            }
            ArrayList<Integer> constraints = getBinaryConstraints(var1, var2 , val1);
            System.out.println();
            inFR.close();
            return constraints;
        }
        catch (FileNotFoundException e) {
            System.out.println(e);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        return null ;
    }

    /**
     * Additional method, called by readBinaryForNodesCSP and based on readBinaryConstraints. Finds all constraints
     * in csp file and iterates through them to find ones relevant to var1x, var2x, val1x
     * @param var1x - Arc start variable
     * @param var2x - Arc end variable
     * @param val1x - Value assigned to var1x we are checking against.
     * @return
     */
    private ArrayList<Integer> getBinaryArray(int var1x, int var2x , int val1x) {
        ArrayList<BinaryConstraint> constraints = new ArrayList<BinaryConstraint>();
        ArrayList<Integer> arcEnd = new ArrayList<Integer>();
        try {
            in.nextToken() ;                                  //'c' or EOF
            while(in.ttype != in.TT_EOF) {
                // scope
                in.nextToken() ;                                       //'('
                in.nextToken() ;                                       //var
                int var1 = (int)in.nval ;
                in.nextToken() ;                                       //','
                in.nextToken() ;                                       //var
                int var2 = (int)in.nval ;
                in.nextToken() ;                                       //')'
                //tuples
                ArrayList<BinaryTuple> tuples = new ArrayList<BinaryTuple>() ;
                in.nextToken() ;              //1st allowed val of 1st tuple
                while (!"c".equals(in.sval) && (in.ttype != in.TT_EOF)) {
                    int val1 = (int)in.nval ;
                    in.nextToken() ;                                   //','
                    in.nextToken() ;                               //2nd val
                    int val2 = (int)in.nval ;
                    tuples.add(new BinaryTuple(val1, val2)) ;
                    in.nextToken() ;      //1stallowed val of next tuple/c/EOF
                }
                BinaryConstraint c = new BinaryConstraint(var1, var2, tuples) ; //iterates through result
                if(var1 == var1x && var2 == var2x){ // checks correct variable for arc
                for (int i=0 ; i<tuples.size() ; i++){
                    if(tuples.get(i).getVal1() == val1x){ //finds arc with val1 and adds support to list.
                        arcEnd.add(tuples.get(i).getVal2());
                     }
                    }
                return arcEnd;
                }
            }
        }
        catch (IOException e) {System.out.println(e);}
        return null ;
    }



    /** Additional method, uses readBinaryForNodesCSP to return a binary array of supported nodes for arc.
     * @param fn   - String, name of CSP file e.g 4Queens.csp
     * @param var - Arc start variable
     * @param futureVar - Arc end variable
     * @param val - Value assigned to var.
     * @param n - dimension of grid & therefore array.
     * @return binary array e.g [0,0,1]
     */
    public int[] getBinaryArray(String fn , int var , int futureVar , int val, int n){
        if(futureVar < var){ //swap the two over if reserve arc. same result because of symmetry.
            int t = futureVar;
            futureVar = var;
            var = t;
        }
        ArrayList<Integer> support = readBinaryForNodesCSP( fn , var , futureVar , val);
        int[] supportvec = new int[n];
        for (int i = 0 ; i<support.size() ; i++ ){
            supportvec[support.get(i)] = 1; //1 for values supported in domain.
        }
        return supportvec;
    }
}
