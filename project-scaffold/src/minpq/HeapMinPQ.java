package minpq;

import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;

/**
 * {@link PriorityQueue} implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class HeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link PriorityQueue} storing {@link PriorityNode} objects representing each item-priority pair.
     */
    private final PriorityQueue<PriorityNode<T>> pq;

    /**
     * Constructs an empty instance.
     */
    public HeapMinPQ() {
        pq = new PriorityQueue<>(Comparator.comparingDouble(PriorityNode::priority));
    }

    /*Checks if the list contains the item already, if it does, return an IllegalArgument, if it doesn't, add a new
      PriorityNode with the given item and its priority.
    */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        pq.add(new PriorityNode<T>(item, priority));
    }

    //Returns true if the list has a Priority node that has the same item, regardless of if the priority is equal.
    @Override
    public boolean contains(T item) {
        return pq.contains(new PriorityNode<T>(item, 0.0));
    }

    //Since PriorityQueue orders the list, peek() returns the PriorityNode with the smallest priority.
    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return pq.peek().item();
    }

    //Since PriorityQueue orders the list, poll returns and remove the PriorityNode with the smallest priority.
    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        return pq.poll().item();
    }

    /* Changes the priority of a PriorityNode with a given item by removing the PriorityNode of the given item,
       then creating a new PriorityNode and added.
    */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        pq.remove(new PriorityNode<T>(item, 0.0));
        pq.add(new PriorityNode<T>(item, priority));
    }

    //Returns the size of the list.
    @Override
    public int size() {
        return pq.size();
    }
}
