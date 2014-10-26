/**
 * Created by Likai Yan on 10/12/14.
 * For Sedgewick Lab 1, Percolation.
 * For more details, see http://coursera.cs.princeton.edu/algs4/assignments/percolation.html
 */
//since,  WQUUF class conveniently wrapped up the detailed 1D array, I can just play WQUUF's API with my 2D percolation array.
//since there are no pointers in java. might as well use 2d array to store the index of 1d array and access thru! bravo!
public class Percolation {
    private int[][] grids;
    private WeightedQuickUnionUF elements;
    private boolean[] gridsStatus;
    private int sideLength;
    private boolean[][] gridsTopBotConnection;
    private boolean percolateStatus;

    public Percolation(int N)               // create N-by-N grid, with all sites blocked
    {


        sideLength = N;
        if (sideLength <= 0 ) {
            System.out.println(sideLength);
            throw new IndexOutOfBoundsException("args out of bounds");
        }
        grids = new int[sideLength][sideLength];
        gridsTopBotConnection = new boolean[sideLength * sideLength][2];//first for top, 2nd for bot.
        elements = new WeightedQuickUnionUF(sideLength * sideLength + 2);
        percolateStatus = false;
        gridsStatus = new boolean[sideLength * sideLength];
        //grids's value index is the index of the element in the matrix, whereas the extra 2 are the top & bot.
        for (int i = 0; i < sideLength; i++) {
            for (int j = 0; j < sideLength; j++) {
                grids[i][j] = sideLength * i + j;
            }
        }

        for (int j = 0; j < sideLength; j++) {
            //elements.union(grids[0][j], (grids[sideLength - 1][sideLength - 1] + 1));
            gridsTopBotConnection[grids[0][j]][0] = true;
            //elements.union(grids[sideLength - 1][j], (grids[sideLength - 1][sideLength - 1] + 2));
            gridsTopBotConnection[grids[sideLength - 1][j]][1] = true;
        }//connect first row, second row to their respective imaginary points.

    }

    private void updateConnection(int p, int q) {
        gridsTopBotConnection[p][0] = gridsTopBotConnection[p][0] || gridsTopBotConnection[q][0];
        gridsTopBotConnection[p][1] = gridsTopBotConnection[p][1] || gridsTopBotConnection[q][1];
    }

    public void open(int i, int j)          // open site (row i, column j) if it is not open already
    {
        int matrixLocI = i - 1;
        int matrixLocJ = j - 1;
        //throw if it's already open?
        gridsStatus[grids[matrixLocI][matrixLocJ]] = true;
        if (matrixLocI - 1 >= 0 && this.isOpen(i - 1, j)) {
            elements.union(grids[matrixLocI][matrixLocJ], grids[matrixLocI - 1][matrixLocJ]);
            updateConnection(grids[matrixLocI][matrixLocJ], grids[matrixLocI - 1][matrixLocJ]);
        }
        if (matrixLocI + 1 < sideLength && this.isOpen(i + 1, j)) {
            elements.union(grids[matrixLocI][matrixLocJ], grids[matrixLocI + 1][matrixLocJ]);
            updateConnection(grids[matrixLocI][matrixLocJ], grids[matrixLocI + 1][matrixLocJ]);
        }
        if (matrixLocJ - 1 >= 0 && this.isOpen(i, j - 1)) {
            elements.union(grids[matrixLocI][matrixLocJ], grids[matrixLocI][matrixLocJ - 1]);
            updateConnection(grids[matrixLocI][matrixLocJ], grids[matrixLocI][matrixLocJ - 1]);
        }
        if (matrixLocJ + 1 < sideLength && this.isOpen(i, j + 1)) {
            elements.union(grids[matrixLocI][matrixLocJ], grids[matrixLocI][matrixLocJ + 1]);
            updateConnection(grids[matrixLocI][matrixLocJ], grids[matrixLocI][matrixLocJ + 1]);
        }
        int root = elements.find(grids[matrixLocI][matrixLocJ]);
        updateConnection(root, grids[matrixLocI][matrixLocJ]);
        if ((gridsTopBotConnection[root][0]) && (gridsTopBotConnection[root][1]))
            percolateStatus = true;//dude, u can't force the top row to be root
    }

    public boolean isOpen(int i, int j) // is site (row i, column j) open?
    {
        return gridsStatus[grids[i - 1][j - 1]];
    }

    public boolean isFull(int i, int j)     // is site (row i, column j) full?
    {
        return isOpen(i, j) && gridsTopBotConnection[elements.find(grids[i - 1][j - 1])][0];
    }

    public boolean percolates()             // does the system percolate?
    {
        return percolateStatus;
    }
}
//    {
////        grid=new int[(N*N)];//default initialized to all 0
////        for (int i=0;i<N*N;i++){
////            if(i<N){
////                grid[i]=-1;
////            }
////            else if (i>=(N*N-N)){
////                grid[i]=-2;
////            }
////            else grid[i]=i;
////        }//points the first row of elements to an imaginary one, and the last row to another imaginary one.
////        // points every other element to themselves, which implies no connection.
//        sideLength=N;
//        grids=new WeightedQuickUnionUF(sideLength*sideLength+2);//two more imaginary points, explained later
//        gridsStatus=new boolean[sideLength*sideLength];//default initialized to all 0, which means closed.
//        for (int i=0;i<N*N;i++){
//            if(i<=N){
//                grids.union(0,i);
//            }
//            else if (i>(N*N-N)){
//                grids.union(N*N,i);
//             }
//        }//points the first row of elements to an imaginary one, and the last row to another imaginary one.
//    }
//    public void open(int i, int j)          // open site (row i, column j) if it is not open already
//    {
//        int index=j+(i-1)*sideLength;
//        if (!gridsStatus[index]){
//            gridsStatus[index]=true;
//            if ((index-sideLength)>0||this.isOpen((i-1),j)) grids.union(index,(index-sideLength));
//            if ((index+sideLength)<=(sideLength*sideLength)||this.isOpen((i+1),j)) grids.union(index,(index+sideLength));
//            if (index-1>0||this.isOpen(i,(j-1))) grids.union(index,index-1);
//            if (index+1<=(sideLength*sideLength)||this.isOpen(i,(j+1))) grids.union(index,index+1);
//        }
//    }
//    public boolean isOpen(int i, int j)     // is site (row i, column j) open?
//    {
//        int index=j+(i-1)*sideLength;
//        return gridsStatus[index];
//    }
//    public boolean isFull(int i, int j)     // is site (row i, column j) full?
//    {
//        int index=j+(i-1)*sideLength;
//        for (int m=0; m<gridsStatus.length;m++){
//            if (gridsStatus[m]==false) return false;
//        }
//        return true;
//
//    }
//    public boolean percolates()             // does the system percolate?
//    {
//        return grids.connected(0,(sideLength*sideLength+1));
//
//    }
//
//    public static void main(String[] args)   // test client (optional)
//    {
//
//    }
//}
