/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int size;
    private Item[] data;

    public RandomizedQueue() {
        data = (Item[]) new Object[10];
        size = 0;
    }

    public void enqueue(Item item) {
        checkItemIsNotNull(item);
        if (size == data.length) {
            resize(size * 2);
        }
        data[size++] = item;
    }

    public Item dequeue() {
        checkIsNotEmpty();
        if (size == data.length / 4) {
            resize(data.length / 2);
        }
        int randIdx = StdRandom.uniform(size);
        Item item = data[randIdx];
        data[randIdx] = data[--size]; // removing this data by overwriting last item
        data[size] = null; // removing duplicate copy of last item
        return item;
    }

    public Item sample() {
        checkIsNotEmpty();
        int randIdx = StdRandom.uniform(size);
        Item item = data[randIdx];
        return item;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    private void resize(int capacity) {
        Item[] newData = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) {
            newData[i] = data[i];
        }
        data = newData;
    }

    private void checkItemIsNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null item cannot be added");
        }
    }

    private void checkIsNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Queue is empty");
        }
    }


    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }


    private class RandomizedQueueIterator implements Iterator<Item> {

        private int pointer;
        private int[] idxArr;

        public RandomizedQueueIterator() {
            pointer = 0;
            idxArr = new int[size];
            for (int i = 0; i < size; i++) {
                idxArr[i] = i;
            }
            StdRandom.shuffle(idxArr);
        }

        public boolean hasNext() {
            return pointer != size;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Queue is empty");
            }
            return data[idxArr[pointer++]];
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {

    }
}
