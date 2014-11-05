/**
 * Created by siriux on 10/30/14.
 */
import java.util.Arrays;

public class Fast {

    private Pattern[] patterns=new Pattern[2];
    private int patternCount=0;
    private  void addToPatter(Pattern p) {
            if (patternCount >= patterns.length)
                resize(patterns, patterns.length*2);
            patterns[patternCount++ ] = p;
    }
    private void resize(Pattern[] arrayOfPatterns, int newSize) {
        Pattern[] temp = new Pattern[newSize];
        for (int i = 0; i < patternCount--; i++) temp[i] = arrayOfPatterns[i];//avoid loitering (Sedgwick's stragy: array=temp)
        arrayOfPatterns = temp;
    }
//
//        public void addToPatter(Point p) {
//            if (++N > pts.length) resize(pts, pts.length * 2);
//            pts[N - 1] = p;
//        }
    private class Pattern {
        private int NP;
        private Point[] pts;
        private double angle;

        public double getAngle() {
            return angle;
        }

        public  Pattern(Point[] patternPts, Point p) {
            NP=patternPts.length+1;
            pts = new Point[NP];
            for (int i=0; i< NP-1; i++){
            pts[i]=patternPts[i];
            }
            pts[NP-1]=p;
        }
        public boolean patternExist(double angleOfP, Point p){
            if (angleOfP!=angle) return false;
            for (int i=0;i<NP;i++)//how to shallow copy the array?
            {
                if (p==pts[i]) return true;
            }
            return false;
        }
    }
    /* gonna try a more brutal way by figuring out all the possible pairs, and then remove permutations.
    private pointWithAngleStack[] connected=new pointWithAngleStack[???];
    private class pointWithAngleStack{
        Point p;
        private int angleCount;
        private double [] angleArray;//non static. for each obj.

        //create a resizable array that resizes when full. link list probably costs more memory and more operations.
        //actually can use linked list to maintain order and save time when comparing  PWS.p
        //anyways, resizable array(i.e. stack) saves time (but not space) when do the compare.
        public void pointWithAngleStack(){
            angleArray=new double[2];
        }
        public void addAngle(double angle){
            if (++angleCount>angleArray.length){
                resize(angleArray, 2*angleArray.length);
            }
            angleArray[angleCount-1]=angle;
        }
        public void resize(double[] array, int newSize){
           double[] temp= new double[newSize];
            for (int i=0; i<newSize; i++){
                temp[i]=array[i];//avoid loitering (Sedgwick's stragy: array=temp)
            }
            array=temp;
        }
    }

    public boolean findPtAngle(Point p, double angle){
        for (int i=0; i<angleCount)//could write iterator...
    }
*/

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates


    //TODO: fix this method.... and others.
    private  void sortPointByRelativeAngle(Point[] aux, int initialPosition){
        Arrays.sort(aux, aux[initialPosition].SLOPE_ORDER);
        for (int i=initialPosition;i<aux.length;i++){
            int j=1;
            int count=0;
            double slopeAgainstP=aux[i].slopeTo(aux[initialPosition]);
            while(i+j<aux.length &&
                    slopeAgainstP==aux[i+j++].slopeTo(aux[initialPosition])){
                count++;
            }
            if (count>2){
                Point[] setOfpts=Arrays.copyOfRange(aux, i, i+count);
                Pattern ptn=new Pattern(setOfpts, aux[initialPosition]);
                this.addToPatter(ptn);
            }
        }
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("require argument for text input");
            return;
        }


        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        java.util.Arrays.sort(points);
        for (int i=0; i<points.length; i++){
            System.out.println(points[i].toString()+"->");
        }

        // shallow copy
        Point[] psAux=new Point[N];
        for (int i=0; i<N; i++){
          psAux[i]=points[i];
        }
        Fast fastSort=new Fast();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (int i=0; i<N; i++){
            fastSort.sortPointByRelativeAngle(psAux, i);
            for (int j=0; j<fastSort.patternCount;j++){
                for (int k=0; k<fastSort.patterns[j].NP;k++){
                    System.out.print(fastSort.patterns[j].pts[k].toString());
                    fastSort.patterns[j].pts[k].draw();
                    if (k!=fastSort.patterns[j].NP-1) {
                        System.out.print(" -> ");
                        fastSort.patterns[j].pts[k].drawTo(fastSort.patterns[j].pts[k+1]);
                    }


                }
                System.out.println();
            }

        }
        //System.exit(999);
        System.out.println("end of Main");
    }

}
//用两点式，不sort，直接find （~N）能够带入的shi'
// n*(n-1) pairs, each takes time ~n  n^3???