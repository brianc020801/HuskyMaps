package minpq;

public class minpq {
    public static void main(String[] args) {
        OptimizedHeapMinPQ pq = new OptimizedHeapMinPQ();
        pq.add("1", 3.0);
        pq.add("2", 6.0);
        pq.add("3", 5.0);
        pq.add("4", 1.0);
        pq.add("5", 4.0);
        pq.add("6", 2.0);

// Call methods to evaluate behavior.
        //pq.changePriority("3", 0.0);
        //pq.changePriority("1", 7.0);
        while (!pq.isEmpty()) {
            System.out.println(pq.removeMin());
        }
    }
}
