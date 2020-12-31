Have extended BinaryCSPReader with additional methods;
* readBinaryForNodesCSP
* getBinaryArray
* getBinaryArray

My own files:
* MAC.java
* Forward.java
* ArrayHandler.java

To run files;

java <Constraint Solver> <n> <ordering>

where:
<Constraint Solver> = [Forward, MAC]
<n> = integer for n-queens problem
<ordering> = [asc , sdf]

e.g

java MAC 8 sdf

java Forward 6 asc
