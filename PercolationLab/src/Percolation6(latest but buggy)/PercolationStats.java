/**
 * Created by siriux on 10/12/14.
 */
public class PercolationStats {
    private Percolation[] experiments;
    private double[] probability;
    private int times;

    public PercolationStats(int N, int T)     // perform T independent experiments on an N-by-N grid
    {
        if (N <= 0 || T <= 0) {
            throw new IndexOutOfBoundsException("args out of bounds");
        }

        probability = new double[T];
        experiments = new Percolation[T];
        times = T;
        for (int j = 0; j < T; j++) {

            experiments[j] = new Percolation(N);//http://stackoverflow.com/questions/5889034/how-to-initialize-an-array-of-objects-in-java
            do {
                int m = StdRandom.uniform(N) + 1;
                int n = StdRandom.uniform(N) + 1;
//                while(true){
                if (!experiments[j].isOpen(m, n)) {
                    experiments[j].open(m, n);
                    probability[j] += 1 / ((double) N * N); //whoa bro, y u no cast?
                    //break;//use while true break to improve efficiency!
                }
//                }

            }
            while (!experiments[j].percolates());


        }
    }

    public double mean()                      // sample mean of percolation threshold
    {
        double P = 0;
        for (int i = 0; i < probability.length; i++) {
            P += probability[i];
        }
        return P / probability.length;
    }

    public double stddev()                    // sample standard deviation of percolation threshold
    {
        if (times == 1) return Double.NaN;
        double avg = mean();
        double variance = 0;
        for (double p : probability) {
            variance += (Math.pow((p - avg), 2) / (times - 1));
        }
        return Math.sqrt(variance);
    }

    public double confidenceLo()              // low  endpoint of 95% confidence interval
    {
        return mean() - 1.96 * stddev() / Math.sqrt(times);
    }

    public double confidenceHi()              // high endpoint of 95% confidence interval
    {
        return mean() + 1.96 * stddev() / Math.sqrt(times);
    }
//
//
//    private int randomExcept(int[] exclude) //kinda risky... might want to check with the array first?
//    {
////        if(!exclude.contains(random))
////            return random;
////
//    int size=exclude.length;
//    do{
//        int randomNum=StdRandom.uniform(exclude.length);
//        boolean exist=false;
//        for(int i=0; i<exclude.length;i++){
//            if(exclude[i]==randomNum) exist=true;
//        }
//        if(!exist) return randomNum;
//    }
//    while (true);
//    }


    public static void main(String[] args)    // test client (described below)
    {
        if (args.length != 2)
            System.out.println("usage: java PercolationStats <side length of grid> <# of exp to conduct>");
        else {System.out.println(args[0]+args[1]);
            PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
            System.out.println("mean                    = " + ps.mean());
            System.out.println("stddev                  = " + ps.stddev());
            System.out.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());


        }
    }

}
