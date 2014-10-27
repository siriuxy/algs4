/**
 * Created by siriux on 10/26/14.
 */
public class Subset {
    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<String> test = new RandomizedQueue();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            test.enqueue(item);
        }
        for (int i = 0; i < Integer.parseInt(args[0]); i++) {
            System.out.println(test.dequeue());
        }
    }
}
