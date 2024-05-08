package minpq;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Unsorted array (or {@link ArrayList}) implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class UnsortedArrayMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the item-priority pairs in no specific order.
     */
    private final List<PriorityNode<T>> items;

    /**
     * Constructs an empty instance.
     */
    public UnsortedArrayMinPQ() {
        items = new ArrayList<>();
    }

    /*Checks if the list contains the item already, if it does, return an IllegalArgument, if it doesn't, add a new
      PriorityNode with the given item and its priority.
    */
    @Override
    public void add(T item, double priority) {
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        items.add(new PriorityNode<T>(item, priority));
    }

    //Returns true if the list has a Priority node that has the same item, regardless of if the priority is equal.
    @Override
    public boolean contains(T item) {
        return items.contains(new PriorityNode<T>(item, 0.0));
    }

    /* Returns the item of the PriorityNode that has the minimum priority by iterating the entire list for the smallest
       priority.
    */
    @Override
    public T peekMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double min_priority = Integer.MAX_VALUE;
        PriorityNode<T> min = null;
        for(PriorityNode<T> pn : items){
            if(pn.priority() < min_priority) {
                min = pn;
                min_priority = pn.priority();
            }
        }
        return min.item();
    }

    /* Returns the item of the PriorityNode that has the minimum priority by iterating the entire list for the smallest
       priority, and then removes that PriorityNode
    */
    @Override
    public T removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("PQ is empty");
        }
        double min_priority = Double.MAX_VALUE;
        PriorityNode<T> min = null;
        for(PriorityNode<T> pn : items){
            if(pn.priority() < min_priority) {
                min = pn;
                min_priority = pn.priority();
            }
        }
        items.remove(min);
        return min.item();
    }

    /* Changes the priority of a PriorityNode with a given item by removing the PriorityNode of the given item,
       then creating a new PriorityNode and added.
    */
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
        items.remove(new PriorityNode<T>(item, 0.0));
        items.add(new PriorityNode<T>(item, priority));
    }

    //Returns the size of the list.
    @Override
    public int size() {
        return items.size();
    }
}
