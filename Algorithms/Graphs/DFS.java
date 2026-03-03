import java.lang.*;
import java.util.*;

/**
 * Demonstrates a Depth-First Search (DFS) traversal on a generic graph.
 *
 * <p>
 * This implementation follows the classical DFS algorithm as described in
 * CLRS (Introduction to Algorithms), using:
 * <ul>
 *     <li>Vertex colors (WHITE, GRAY, BLACK)</li>
 *     <li>Discovery (start) time</li>
 *     <li>Finish time</li>
 *     <li>Parent pointers to represent the DFS forest</li>
 * </ul>
 *
 * <p>
 * The DFS is implemented recursively and supports disconnected graphs
 * by iterating over all vertices and launching DFS where required.
 */
public class DFS {

    /**
     * Entry point for demonstrating DFS execution.
     *
     * <p>
     * Generates a dummy graph, prints it, runs DFS, and then prints
     * the DFS metadata (timestamps, colors, parents) for each vertex.
     */
    public static void main(String[] args) {
        IGraph<Integer> graph = DummyGraphGenerator.generate();
        System.out.println("graph:\n" + graph.toString());

        DFS.SolutionV1<Integer> dfs = new DFS.SolutionV1<>();
        dfs.dfs(graph);

        System.out.println("graph:\n" + dfs);
    }

    /**
     * Enumeration representing the visitation state of a vertex during DFS.
     *
     * <ul>
     *     <li>WHITE – Vertex has not been discovered yet</li>
     *     <li>GRAY  – Vertex is discovered but not fully explored (in recursion stack)</li>
     *     <li>BLACK – Vertex and all its adjacent vertices are fully explored</li>
     * </ul>
     */
    static enum Color {
        WHITE, GRAY, BLACK
    }

    /**
     * Stores DFS-related metadata for a single vertex.
     *
     * @param <T> Vertex type
     */
    static class VertexProps<T> {

        /** Discovery time of the vertex */
        int start;

        /** Finish time of the vertex */
        int finish;

        /** Parent of the vertex in the DFS tree */
        T parent;

        /** Current DFS color of the vertex */
        Color color;

        /**
         * Constructs a vertex properties object.
         *
         * @param dis    Ignored parameter (legacy / unused)
         * @param parent Parent vertex in DFS tree
         * @param color  Initial color
         */
        public VertexProps(int dis, T parent, Color color) {
            this.start = 0;
            this.finish = 0;
            this.parent = parent;
            this.color = color;
        }

        /**
         * Constructs a default vertex properties object.
         *
         * <p>
         * Vertex starts as WHITE, with no parent, and timestamps unset.
         */
        public VertexProps() {
            this(Integer.MAX_VALUE, null, Color.WHITE);
        }

        /**
         * Returns a readable string representation of DFS metadata
         * associated with this vertex.
         */
        @Override
        public String toString() {
            return "VertexProps{" +
                    "start=" + start +
                    ", finish=" + finish +
                    ", parent=" + parent +
                    ", color=" + color +
                    "}\n";
        }
    }

    /**
     * DFS implementation using recursion.
     *
     * @param <T> Vertex type
     */
    static class SolutionV1<T> {

        /**
         * Global DFS time counter.
         *
         * <p>
         * Incremented on vertex discovery and vertex finish.
         */
        int time = 0;

        /**
         * Maps each vertex to its DFS metadata.
         */
        Map<T, VertexProps> vertexProps = new HashMap<>();

        /**
         * Performs DFS on the given graph.
         *
         * <p>
         * This method:
         * <ol>
         *     <li>Initializes all vertices as WHITE</li>
         *     <li>Iterates over all vertices</li>
         *     <li>Starts DFS for vertices not yet visited</li>
         * </ol>
         *
         * <p>
         * This ensures that disconnected graphs are handled correctly,
         * producing a DFS forest.
         *
         * @param graph Input graph
         */
        void dfs(IGraph<T> graph) {
            vertexProps.clear();
            time = 0;

            for (T vertex : graph.vertices()) {
                vertexProps.put(vertex, new VertexProps<T>());
            }

            for (T vertex : graph.vertices()) {
                dfs(graph, vertex, null);
            }
        }

        /**
         * Recursive DFS visit for a single vertex.
         *
         * <p>
         * Algorithm steps:
         * <ol>
         *     <li>If vertex is not WHITE, return immediately</li>
         *     <li>Mark vertex GRAY and assign discovery time</li>
         *     <li>Recursively visit all adjacent vertices</li>
         *     <li>Mark vertex BLACK and assign finish time</li>
         * </ol>
         *
         * @param graph  Input graph
         * @param vertex Current vertex
         * @param parent Parent vertex in DFS tree
         */
        void dfs(IGraph<T> graph, T vertex, T parent) {
            VertexProps props = vertexProps.get(vertex);

            // Skip already discovered or finished vertices
            if (props.color != Color.WHITE) {
                return;
            }

            // Discover vertex
            props.color = Color.GRAY;
            time++;
            props.start = time;
            props.parent = parent;

            // Explore adjacent vertices
            for (T adj : graph.getAdjacent(vertex)) {
                dfs(graph, adj, vertex);
            }

            // Finish vertex
            time++;
            props.finish = time;
            props.color = Color.BLACK;
        }

        /**
         * Returns the DFS metadata for all vertices.
         */
        public String toString() {
            return vertexProps.toString();
        }
    }
}