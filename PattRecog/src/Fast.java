/**
 * Created by siriux on 10/30/14.
 */
public class Fast {
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
        public void findPt
    }
    public static void main(String[] args){

    }

}
//用两点式，不sort，直接find （~N）能够带入的shi'
// n*(n-1) pairs, each takes time ~n  n^3???