package minpq;

import java.util.*;

/**
 * Optimized binary heap implementation of the {@link ExtrinsicMinPQ} interface.
 *
 * @param <T> the type of elements in this priority queue.
 * @see ExtrinsicMinPQ
 */
public class OptimizedHeapMinPQ<T> implements ExtrinsicMinPQ<T> {
    /**
     * {@link List} of {@link PriorityNode} objects representing the heap of item-priority pairs.
     */
    private final List<PriorityNode<T>> items;
    /**
     * {@link Map} of each item to its associated index in the {@code items} heap.
     */
    private final Map<T, Integer> itemToIndex;
    /**
     * The number of elements in the heap.
     */
    private int size;

    /**
     * Constructs an empty instance.
     */
    public OptimizedHeapMinPQ() {
        items = new ArrayList<>();
        items.add(null);
        size = 0;
        itemToIndex = new HashMap<>();
    }

    /* Add a new PriorityNode to the list, increase size by 1 and swim the PriorityNode up the heap, then maps the item
       with its respective index.
    */
    @Override
    public void add(T item, double priority){
        if (contains(item)) {
            throw new IllegalArgumentException("Already contains " + item);
        }
        PriorityNode<T> pn_to_add = new PriorityNode<T>(item, priority);
        size +=1 ;
        items.add(pn_to_add);
        swim(size);
        itemToIndex.put(pn_to_add.item(), items.indexOf(pn_to_add));
    }

    //Checks if the map contains a given item.
    @Override
    public boolean contains(T item) {
        return itemToIndex.containsKey(item);
    }

    //Returns the item of the minimum PriorityNode
    @Override
    public T peekMin() {
        if (size == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        return items.get(1).item();
    }

    //Removes the minimum PriorityNode, and then sinks the binary heap to organize and keep the map updated
    @Override
    public T removeMin() {
        if (size == 0) {
            throw new NoSuchElementException("PQ is empty");
        }
        PriorityNode<T> min = items.get(1);
        swap(1, size);
        items.remove(size);
        itemToIndex.remove(min.item());
        size -= 1;
        sink(1);
        return min.item();
    }

    //Changes the priority of a given item to a given priority
    @Override
    public void changePriority(T item, double priority) {
        if (!contains(item)) {
            throw new NoSuchElementException("PQ does not contain " + item);
        }
//        items.remove(new PriorityNode<T>(item, priority));
//        itemToIndex.remove(item);
//        size -= 1;
//        add(item, priority);
        int index = itemToIndex.get(item);
        items.get(index).setPriority(priority);
        swim(index);
        sink(index);
    }

    //Returns the size of the OptimizedHeapMinPQ
    @Override
    public int size() {
        return size;
    }

    // Swim the item at the given index until the heap invariant is satisfied.
    private void swim(int index) {
        //PriorityNode<T> pn = items.get(index);
        while (index > 1) {
            int parentIndex = index / 2;

            // If the item at the parent index is greater, swap the two items.
            if (isGreater(parentIndex, index)) {
                swap(parentIndex, index);
            } else {
                //itemToIndex.put(pn.item(), index);
                return;
            }
            index = parentIndex;
        }
        //itemToIndex.put(pn.item(), index);
    }

    // Returns true if and only if the item at index i is strictly greater
    // than the item at index j. i or j must be valid indices.
    private boolean isGreater(int i, int j) {

        boolean isValidI = 0 < i && i <= size;
        boolean isValidJ = 0 < j && j <= size;
        if (isValidI && isValidJ) {
            // If both i and j are valid, return whether i is greater than j.
            return items.get(i).priority() > items.get(j).priority();
        } else if (isValidI || isValidJ) {
            // Only one of i or j is valid, so return whether i is valid.
            // If i is valid, then it is greater than j.
            // If i is not valid, then it is less than j.
            return isValidI;
        } else {
            throw new IllegalArgumentException("i or j must be a valid index");
        }
    }

    // Swaps the items at the given indices i and j.
    private void swap(int i, int j) {
        PriorityNode<T> temp = items.get(i);
        items.set(i, items.get(j));
        itemToIndex.put(items.get(j).item(), i);
        items.set(j, temp);
        itemToIndex.put(temp.item(), j);
    }

    // Sink the item at the given index until the heap invariant is satisfied.
    private void sink(int index) {
        while (2 * index <= size) {
            int leftIndex = 2 * index;
            int rightIndex = leftIndex + 1;

            // Assign the smaller of the children as the swap candidate.
            int swapCandidate = leftIndex;
            if (rightIndex <= size && isGreater(leftIndex, rightIndex)) {
                swapCandidate = rightIndex;
            }

            // If the item at the current index is greater, swap the two items.
            if (isGreater(index, swapCandidate)) {
                swap(index, swapCandidate);
            } else {
                return;
            }
            index = swapCandidate;
        }
    }

}
