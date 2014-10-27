/**
 * Created by siriux on 10/26/14.
 * maybe i should use double linked list. but i'm just using linked list in this implementation
 * update: im gonna use double linked list b/c otherwise I can't implement a robust removeLast()
 */
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;
    private class Node{
        Item item;
        Node previous=null;//trying...  using this or null? prob null for less memory
        Node next=null;
    }
    public Deque()                           // construct an empty deque
    {
        N=0;
    }
    public boolean isEmpty()                 // is the deque empty?
    {return N==0;}
    public int size()                        // return the number of items on the deque
    {return N;}
    public void addFirst(Item item)          // insert the item at the front
    {
        if (item==null) throw new NullPointerException();
        Node oldfirst=first;
        first =new Node();
        first.item=item;
        first.next=oldfirst;
        if (oldfirst!=null) oldfirst.previous=first;
        else last=first;
        N++;
    }
    public void addLast(Item item)           // insert the item at the end
    {
        if (item==null) throw new NullPointerException();
        Node oldlast=last;
        last=new Node();
        last.item=item;
        last.previous=oldlast;
        if (oldlast!=null)oldlast.next=last; //ha! but not very sure... Seems that I can't implement removeLast for multiple calls.
        else first=last;
        N++;
    }
    public Item removeFirst()                // delete and return the item at the front
    {
        if (this.isEmpty()) throw new java.util.NoSuchElementException();
        Item firstItem=first.item;
        first=first.next; //avoid loitering?
        N--;
        return firstItem;
    }
    public Item removeLast()                 // delete and return the item at the end
    {
        if (this.isEmpty()) throw new java.util.NoSuchElementException();
        Item lastItem=last.item;
        last=last.previous;
        N--;
        return lastItem;
    }
    public Iterator<Item> iterator()         // return an iterator over items in order from front to end
    {
        return new DequeIterator();
    }
    private class DequeIterator implements Iterator<Item>
    {
        private Node current;
        public boolean hasNext(){
            return current!=null; //why...?
        }
        public Item next(){
            Item item=current.item;
            current=current.next;
            return item;
        }
        public void remove (){
            throw new UnsupportedOperationException();
        };
    }
    public static void main(String[] args)   // unit testing
    {
        Deque test=new Deque();
        while(!StdIn.isEmpty()){
            String item=StdIn.readString();
            if(item.equals("-F")) System.out.println(test.removeFirst());
            else if(item.equals("-L")) System.out.println(test.removeLast());
            else test.addFirst(item);
            System.out.println("size is "+test.size());
        }
    }
}
