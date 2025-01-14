/******************************************************************************
 *  Compilation:  javac OrderedLinkedListMaxPQ.java
 *  Execution:    java OrderedLinkedListMaxPQ
 *
 *  Priority queue implementation with an unordered list.
 *
 ******************************************************************************/

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The {@code OrderedLinkedListMaxPQ} class represents a max priority queue
 * of generic items.
 * It supports the usual <em>insert</em> and <em>deleteMax</em>
 * operations, along with methods for peeking the max item and
 * testing if the queue is empty.
 * <p>
 * This implementation uses a doubly linked list.
 * The <em>insert</em> operation takes linear time in the worst case.
 * The <em>delMax</em>, <em>max</em>, <em>size</em>, and <em>is-empty</em>
 * operations take constant time in the worst case.
 * <p>
 * @author Vlad Beklenyshchev aka vladkinoman
 */
public class OrderedLinkedListMaxPQ<Key extends Comparable<Key>>
        implements Iterable<Key> {
    private int n;     // number of items
    private Node last; // points to the last inserted item

    private class Node {
        Key info;
        Node next;
        Node prev;
    }

    /**
     * Inserts the key into this priority queue.
     * @param key the key to insert
     */
    public void insert(Key key) {
        Node curr = last;
        Node prevCurr = null;
        while (n > 0 && curr != null) {
            if (curr.info.compareTo(key) < 0) {
                Node aux = curr.next;
                curr.next = new Node();
                curr.next.info = key;
                curr.next.prev = curr;
                curr.next.next = aux;
                if (aux != null) aux.prev = curr.next;
                break;
            }
            prevCurr = curr;
            curr = curr.prev;
        }

        if (curr == null) {
            curr = new Node();
            curr.info = key;
            if (prevCurr != null) {
                curr.next = prevCurr;
                prevCurr.prev = curr;
            }
        }
        if (n == 0)            last = curr;
        else if (curr == last) last = curr.next;
        n++;
    }

    /**
     * Returns the maximum in this priority queue.
     * @return the maximum in this priority queue
     * @throws java.util.NoSuchElementException if this priority queue is empty
     */
    public Key max() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue underflow.");
        }
        return last.info;
    }

    /**
     * Removes the maximum in the priority queue.
     * @return the maximum in this priority queue
     * @throws java.util.NoSuchElementException if this priority queue is empty
     */
    public Key delMax() {
        if (isEmpty()) {
            throw new NoSuchElementException("Priority Queue underflow.");
        }
        Key max = last.info;
        last = last.prev;
        last.next = null;
        n--;
        return max;
    }

    /**
     * Returns the number of items in this priority queue.
     * @return the number of items in this priority queue.
     */
    public int size() {
        return n;
    }

    /**
     * Checks whether this pq is empty.
     * @return true if this queue is empty; false otherwise
     */
    public boolean isEmpty() {
        return n == 0;
    }

    private class LinkedListIterator implements Iterator<Key> {
        private Node curr = last;
        public boolean hasNext() {
            return curr != null;
        }
        public Key next() {
            Key key = curr.info;
            curr = curr.prev;
            return key;
        }
    }

    @Override
    public Iterator<Key> iterator() {
        return new LinkedListIterator();
    }

    /**
     * Unit tests the {@code UnorderedArrayMaxPQ} data type.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        OrderedLinkedListMaxPQ<String> pq = new OrderedLinkedListMaxPQ<>();
        pq.insert("to");
        pq.insert("be");
        pq.insert("or");
        pq.insert("not");
        pq.insert("to");
        pq.insert("be");
        pq.insert("thyself");
        pq.delMax();
        pq.insert("who");
        System.out.println("Current state of the priority queue:");
        for (Object key: pq) {
            System.out.print(key + " ");
        }
        System.out.println();
    }
}
