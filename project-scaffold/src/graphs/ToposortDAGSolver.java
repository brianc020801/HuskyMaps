package graphs;

import minpq.DoubleMapMinPQ;
import minpq.ExtrinsicMinPQ;

import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        this.edgeTo = new HashMap<>();
        this.distTo = new HashMap<>();
        List<V> result = new ArrayList<>();
        Set<V> visited = new HashSet<>();
        dfsPostOrder(graph, start, visited, result);
        Collections.reverse(result);
        edgeTo.put(start, null);
        distTo.put(start, 0.0);
        for(V vertex : result){
            for(Edge<V> edge : graph.neighbors(vertex)){
                double old_dist = distTo.getOrDefault(edge.to, Double.POSITIVE_INFINITY);
                double new_dist = edge.weight + distTo.get(vertex);
                if(new_dist < old_dist){
                    distTo.put(edge.to, new_dist);
                    edgeTo.put(edge.to, edge);
                }
            }
        }
    }

    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {
        visited.add(start);

        if(!graph.neighbors(start).isEmpty()) {
            for (Edge<V> edge : graph.neighbors(start)) {
                V to = edge.to;
                if (!visited.contains(to)) {
                    dfsPostOrder(graph, to, visited, result);
                }
            }
            result.add(start);
        }
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
}
