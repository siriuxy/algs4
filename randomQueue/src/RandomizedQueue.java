/**
 * Created by siriux on 10/26/14.
 */

import java.util.Iterator;


// below is an attempt at resizable array implementation, which stopped at deque() b/c I found it hard to randomize there.
public class RandomizedQueue<Item> implements Iterable<Item> {
    private int N;
    private int R = 0;
    private Item[] RQ;

    public RandomizedQueue()                 // construct an empty randomized queue
    {
        N = 0;
        RQ = (Item[]) new Object[1];
    }

    public boolean isEmpty()                 // is the queue empty?
    {
        return N == 0;
    }

    public int size()                        // return the number of items on the queue
    {
        return N;
    }

    public void enqueue(Item item)           // add the item
    {
        if (item.equals(null)) throw new NullPointerException();
        if (N < RQ.length) {
            Item[] doubleRQ = (Item[]) new Object[RQ.length * 2];
            for (int i = 0; i < RQ.length; i++) {
                doubleRQ[i] = RQ[i];
            }
            RQ = doubleRQ;
        }
        if (N != 0) R = StdRandom.uniform(N);
        Item oldR = RQ[R];
        RQ[N++] = oldR;
        RQ[R] = item;
    }

    public Item dequeue()                    // delete and return a random item
    {
        if (this.isEmpty()) throw new java.util.NoSuchElementException();
        Item item = RQ[--N];
        RQ[N] = null;
        if (N > 0 && N == RQ.length / 4) resize(RQ.length / 2);
        return item;
    }

    private void resize(int S) {
        Item[] temp = (Item[]) new Object[S];
        int j = 0;
        for (int i = 0; i < RQ.length; i++) {
            if (RQ[j] == null) j++;
            else temp[i] = RQ[++j];
        }
    }

    public Item sample()                     // return (but do not delete) a random item
    {
        if (this.isEmpty()) throw new java.util.NoSuchElementException();
        return RQ[N - 1];
    }

    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
        return new RQIterator();
    }

    private class RQIterator implements Iterator<Item> {
        private int location = 0;
        private int[] locations = new int[N];

        public RQIterator() {
            int j = 0;
            for (int i = 0; i < RQ.length; i++) {
                if (RQ[i] != null) locations[j++] = i;
            }
            StdRandom.shuffle(locations);
        }

        public boolean hasNext() {
            return location < N;
        }

        public Item next() {
            if (!this.hasNext()) throw new java.util.NoSuchElementException();
            return RQ[locations[location++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        ;
    }

    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<String> test = new RandomizedQueue();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            if (item.equals("showAll")) for (String s : test) System.out.println(s);
            else if (item.equals("dequeue")) System.out.println(test.dequeue());
            else if (item.equals("sample")) System.out.println(test.sample());
            else test.enqueue(item);
            System.out.println("size is " + test.size());
        }

    }
}

/** damn! can't implement remove with list... going back to array.
 public class RandomizedQueue<Item> implements Iterable<Item> {
 private Iterator<Item> it;
 private Node first;
 private int N;
 private class Node{
 Item item;
 Node next=null;
 }
 public RandomizedQueue()                 // construct an empty randomized queue
 {
 N=0;
 }
 public boolean isEmpty()                 // is the queue empty?
 {return N==0;}
 public int size()                        // return the number of items on the queue
 {return N;}
 public void enqueue(Item item)           // add the item
 {
 Node oldFirst=first;
 first=new Node();
 first.next=oldFirst;
 }
 public Item dequeue()                    // delete and return a random item
 {

 }
 public Item sample()                     // return (but do not delete) a random item
 public Iterator<Item> iterator()         // return an independent iterator over items in random order
 {
 return new RQIterator();
 }
 private class RQIterator implements Iterator<Item>{
 private Node current;
 public void remove () {}
 public boolean hasNext() {
 return current!=null;
 }
 public Item next(){
 Item item=current.item;
 current=current.next;
 return item;
 }
 }
 public static void main(String[] args)   // unit testing
 }

 */