
/**
 * Created by siriux on 10/30/14.
 */
public class Brute {

    public static void main(String[] args){
        if (args.length!=1) {
            System.out.println("require argument for text input");
            return;
        }


        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Point[] points=new Point[N];
        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);

        for (int i=0; i<N; i++){
            for (int j=i+1;j<N;j++){
                for (int k=j+1;k<N;k++){
                    for (int l=k+1;l<N;l++){
                        if (compareInlinePts(points[i],points[j],points[k],points[l])){
                         System.out.println
                                 (
                                 points[i].toString()+" -> "
                                 +points[j].toString()+" -> "
                                 +points[k].toString()+" -> "
                                         +points[l].toString()
                                 );
                            points[i].draw();
                            points[j].draw();
                            points[k].draw();
                            points[l].draw();
                            points[i].drawTo(points[j]);
                            points[i].drawTo(points[k]);
                            points[i].drawTo(points[l]);


                        }
                    }
                }
            }
        }


        System.out.println("new ");
    }

    private static boolean compareInlinePts(Point a, Point b, Point c, Point d){
        double a_s_b=a.slopeTo(b);
        //System.out.println(a_s_b+"<-ASB");
        if(a_s_b==a.slopeTo(c)) {
            if (a_s_b==a.slopeTo(d)) {
                return true;
            }


        }
        return false;//change to the version with comparator
    }
}
