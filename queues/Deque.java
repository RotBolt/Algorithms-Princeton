/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item data;
        Node next;
        Node prev;
    }

    private Node head;
    private Node tail;
    private int size;

    public Deque() {
        head = null;
        tail = null;
        size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        checkItemIsNotNull(item);
        Node oldHead = head;
        Node itemNode = new Node();
        itemNode.data = item;
        if (size == 0) {
            head = itemNode;
            tail = itemNode;
        }
        else {
            head = itemNode;
            head.next = oldHead;
            oldHead.prev = head;
        }
        size++;
    }

    public void addLast(Item item) {
        checkItemIsNotNull(item);
        Node itemNode = new Node();
        itemNode.data = item;
        if (size == 0) {
            head = itemNode;
            tail = itemNode;
        }
        else {
            tail.next = itemNode;
            itemNode.prev = tail;
            tail = itemNode;
        }
        size++;
    }

    public Item removeFirst() {
        checkIsNotEmpty();
        Item item = head.data;
        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
            Node nextHead = head.next;
            nextHead.prev = null;
            head.next = null;
            head = nextHead;
        }
        size--;
        return item;
    }

    public Item removeLast() {
        checkIsNotEmpty();
        Item item = tail.data;
        if (size == 1) {
            head = null;
            tail = null;
        }
        else {
            Node prevTail = tail.prev;
            prevTail.next = null;
            tail.prev = null;
            tail = prevTail;
        }
        size--;
        return item;
    }

    private void checkIsNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty");
        }
    }

    private void checkItemIsNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item cannot be added");
        }
    }

    public Iterator<Item> iterator() {
        return new ListIterator();
    }


    private class ListIterator implements Iterator<Item> {

        private Node current = head;

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No next element");
            }
            Item item = current.data;
            current = current.next;
            return item;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}
