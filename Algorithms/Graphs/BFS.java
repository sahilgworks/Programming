import java.lang.*;
import java.util.*;

/**
 * Demonstrates a Breadth-First Search (BFS) traversal on a graph.
 *
 * <p>This class assumes the existence of:
 * <ul>
 *   <li>{@code IGraph<T>} – an interface providing graph access</li>
 *   <li>{@code DummyGraphGenerator} – a utility to generate a sample graph</li>
 * </ul>
 *
 * <p>The BFS logic itself is implemented inside the {@link Solution} inner class.
 */
public class BFS {

    /**
     * Entry point for the program.
     *
     * <p>Creates a dummy graph, prints its structure,
     * then runs BFS starting from vertex {@code 1} and prints
     * the resulting BFS state (parent, distance, color).
     */
    public static void main(String[] args) {
        IGraph<Integer> graph = DummyGraphGenerator.generate();
        System.out.println("graph:\n" + graph.toString());
        SolutionV3<Integer> bfs = new SolutionV3<>(graph, 1);
        System.out.println("graph:\n" + bfs);
        System.out.println("path to 1:\n" + bfs.pathTo(1));
        System.out.println("path to 2:\n" + bfs.pathTo(2));
        System.out.println("path to 3:\n" + bfs.pathTo(3));
        System.out.println("path to 5:\n" + bfs.pathTo(5));
        System.out.println("path to 17:\n" + bfs.pathTo(17));
        System.out.println("path to 19:\n" + bfs.pathTo(19));
    }

    /**
     * Encapsulates the result and execution of a Breadth-First Search (BFS).
     *
     * <p>This class performs BFS eagerly inside its constructor.
     * Once constructed, it holds the full BFS traversal state:
     * <ul>
     *   <li>Parent relationship (BFS tree)</li>
     *   <li>Shortest distance (in edges) from the source</li>
     *   <li>Color/state of each vertex</li>
     * </ul>
     *
     * @param <T> the vertex type
     */
    static class Solution<T> {

        /**
         * Maps each vertex to its parent in the BFS tree.
         * The source vertex has {@code null} as its parent.
         */
        HashMap<T, T> parent = new HashMap<>();

        /**
         * Stores the shortest distance (number of edges)
         * from the source vertex to each vertex.
         */
        HashMap<T, Integer> distance = new HashMap<>();

        /**
         * Tracks the BFS visitation state of each vertex.
         * Values are {@link #WHITE}, {@link #GRAY}, or {@link #BLACK}.
         */
        HashMap<T, Integer> color = new HashMap<>();

        /**
         * Queue used to process vertices in FIFO order,
         * which is essential for BFS correctness.
         */
        ArrayDeque<T> queue = new ArrayDeque<>();

        /**
         * Vertex has not been discovered yet
         */
        int WHITE = 0;

        /**
         * Vertex has been discovered but not fully explored
         */
        int GRAY = 1;

        /**
         * Vertex and all its adjacent vertices have been explored
         */
        int BLACK = 2;

        /**
         * Source vertex from which BFS starts
         */
        T src;

        /**
         * Constructs and executes BFS on the given graph starting from {@code src}.
         *
         * <p>Algorithm overview:
         * <ol>
         *   <li>Initialize all vertices as WHITE with infinite distance</li>
         *   <li>Mark source as GRAY with distance 0</li>
         *   <li>Repeatedly dequeue a vertex and explore its neighbors</li>
         *   <li>Newly discovered vertices are enqueued and marked GRAY</li>
         *   <li>Fully processed vertices are marked BLACK</li>
         * </ol>
         *
         * @param graph the graph on which BFS is executed
         * @param src   the source vertex
         */
        Solution(IGraph<T> graph, T src) {
            this.src = src;

            // Initialization step
            for (T vertex : graph.vertices()) {
                parent.put(vertex, null);
                distance.put(vertex, Integer.MAX_VALUE);
                color.put(vertex, WHITE);
            }

            // Initialize source vertex
            parent.put(src, null);
            distance.put(src, 0);
            color.put(src, GRAY);
            queue.offerLast(src);

            // BFS main loop
            while (!queue.isEmpty()) {
                T parent = queue.pollFirst();

                // Explore all adjacent vertices
                for (T next : graph.getAdjacent(parent)) {
                    if (color.get(next) == WHITE) {
                        queue.offerLast(next);
                        this.parent.put(next, parent);
                        distance.put(next, distance.get(parent) + 1);
                        color.put(next, GRAY);
                    }
                }

                // Mark vertex as fully processed
                color.put(parent, BLACK);
            }
        }

        /**
         * Returns a string representation of the BFS traversal state.
         *
         * <p>Includes:
         * <ul>
         *   <li>Source vertex</li>
         *   <li>Parent map</li>
         *   <li>Distance map</li>
         *   <li>Color/state map</li>
         * </ul>
         *
         * @return human-readable BFS state
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("BFS state for:");
            sb.append(src);
            sb.append("\n");
            sb.append("parent:\n");
            sb.append(parent.toString());
            sb.append("\ndistance:\n");
            sb.append(distance.toString());
            sb.append("\ncolor:\n");
            sb.append(color.toString());
            return sb.toString();
        }
    }

    /**
     * Alternative BFS implementation that groups per-vertex state
     * (parent, distance, color) into a single object.
     *
     * <p>This version improves cohesion compared to separate maps by
     * storing all BFS-related metadata inside {@link VertexProps}.
     *
     * @param <T> the vertex type
     */
    static class SolutionV2<T> {

        /**
         * Queue used for BFS traversal (FIFO order).
         */
        ArrayDeque<T> queue = new ArrayDeque<>();

        /**
         * Stores BFS-related properties for each vertex.
         */
        HashMap<T, VertexProps> vertexProps = new HashMap<>();

        /**
         * Source vertex from which BFS starts.
         */
        T src;

        /**
         * Vertex has not been discovered
         */
        static int WHITE = 0;

        /**
         * Vertex has been discovered but not fully explored
         */
        static int GRAY = 1;

        /**
         * Vertex and all its neighbors have been fully explored
         */
        static int BLACK = 2;

        /**
         * Holds BFS traversal metadata for a single vertex.
         *
         * <p>This mirrors the classical BFS formulation:
         * <ul>
         *   <li>{@code parent} – predecessor in the BFS tree</li>
         *   <li>{@code distance} – shortest path length from source</li>
         *   <li>{@code color} – visitation state</li>
         * </ul>
         *
         * @param <T> the vertex type
         */
        static class VertexProps<T> {

            /**
             * Parent of this vertex in the BFS tree.
             * {@code null} for the source vertex.
             */
            T parent = null;

            /**
             * Distance from the source vertex.
             * Initialized to {@link Integer#MAX_VALUE} to represent infinity.
             */
            int distance = Integer.MAX_VALUE;

            /**
             * BFS visitation state (WHITE, GRAY, BLACK).
             */
            int color = WHITE;

            /**
             * Constructs a fully initialized {@code VertexProps} instance.
             *
             * @param parent   parent vertex in BFS tree
             * @param distance distance from source
             * @param color    visitation state
             */
            public VertexProps(T parent, int distance, int color) {
                this.parent = parent;
                this.distance = distance;
                this.color = color;
            }

            /**
             * Constructs a default {@code VertexProps} instance
             * with infinite distance and WHITE color.
             */
            public VertexProps() {
            }

            /**
             * Returns a readable representation of vertex state.
             * Infinite distance is printed as {@code INF}.
             */
            @Override
            public String toString() {
                return "VertexProps{" +
                        "parent=" + parent +
                        ", distance=" + (distance == Integer.MAX_VALUE ? "INF" : distance) +
                        ", color=" + color +
                        "}\n";
            }
        }

        /**
         * Constructs and executes BFS on the given graph starting from {@code src}.
         *
         * <p>The BFS algorithm proceeds as follows:
         * <ol>
         *   <li>Initialize all vertices with default {@link VertexProps}</li>
         *   <li>Mark the source as GRAY with distance 0</li>
         *   <li>Process vertices in FIFO order using a queue</li>
         *   <li>Relax edges by discovering WHITE neighbors</li>
         *   <li>Mark fully explored vertices as BLACK</li>
         * </ol>
         *
         * @param graph the graph to traverse
         * @param src   the source vertex
         */
        SolutionV2(IGraph<T> graph, T src) {
            this.src = src;

            // Initialize properties for all vertices
            for (T vertex : graph.vertices()) {
                vertexProps.put(vertex, new VertexProps<T>());
            }

            // Initialize source vertex
            vertexProps.put(src, new VertexProps(null, 0, GRAY));
            queue.offerLast(src);

            // BFS main loop
            while (!queue.isEmpty()) {
                T parent = queue.pollFirst();
                VertexProps parentProps = vertexProps.get(parent);

                for (T next : graph.getAdjacent(parent)) {
                    VertexProps nextProps = vertexProps.get(next);

                    if (nextProps.color == WHITE) {
                        queue.offerLast(next);
                        nextProps.color = GRAY;
                        nextProps.distance = parentProps.distance + 1;
                        nextProps.parent = parent;
                    }
                }

                // Mark vertex as fully explored
                parentProps.color = BLACK;
            }
        }

        /**
         * Returns a string representation of the BFS traversal state.
         *
         * <p>Includes:
         * <ul>
         *   <li>Source vertex</li>
         *   <li>Per-vertex BFS metadata</li>
         * </ul>
         *
         * @return human-readable BFS state
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("BFS state for:");
            sb.append(src);
            sb.append("\n");
            sb.append("vertexProps:\n");
            sb.append(vertexProps.toString());
            return sb.toString();
        }
    }

    /**
     * Breadth-First Search (BFS) implementation that stores
     * all per-vertex traversal state in a single structure.
     *
     * <p>This version:
     * <ul>
     *   <li>Uses {@link Colors} enum for type-safe vertex state</li>
     *   <li>Computes shortest paths in an unweighted graph</li>
     *   <li>Supports path reconstruction from source to any destination</li>
     * </ul>
     *
     * @param <T> the vertex type
     */
    static class SolutionV3<T> {

        /**
         * Queue used to process vertices in FIFO order,
         * which is essential for BFS correctness.
         */
        ArrayDeque<T> queue = new ArrayDeque<>();

        /**
         * Stores BFS-related properties for each vertex.
         */
        HashMap<T, VertexProps<T>> vertexProps = new HashMap<>();

        /**
         * Source vertex from which BFS traversal starts.
         */
        T src;

        /**
         * Enumeration representing BFS visitation states.
         *
         * <ul>
         *   <li>{@code WHITE} – undiscovered</li>
         *   <li>{@code GRAY}  – discovered but not fully explored</li>
         *   <li>{@code BLACK} – fully explored</li>
         * </ul>
         */
        static enum Colors {
            WHITE, GRAY, BLACK
        }

        /**
         * Holds BFS traversal metadata for a single vertex.
         *
         * <p>This mirrors the classical BFS formulation used in
         * standard algorithms literature.
         *
         * @param <T> the vertex type
         */
        static class VertexProps<T> {

            /**
             * Parent of this vertex in the BFS tree.
             * {@code null} for the source vertex.
             */
            T parent;

            /**
             * Distance from the source vertex.
             * Initialized to {@link Integer#MAX_VALUE} to represent infinity.
             */
            int distance;

            /**
             * Current visitation state of the vertex.
             */
            Colors color;

            /**
             * Constructs a fully initialized {@code VertexProps} instance.
             *
             * @param parent   parent vertex in BFS tree
             * @param distance distance from source
             * @param color    visitation state
             */
            public VertexProps(T parent, int distance, Colors color) {
                this.parent = parent;
                this.distance = distance;
                this.color = color;
            }

            /**
             * Constructs a default {@code VertexProps} instance
             * with infinite distance and WHITE color.
             */
            public VertexProps() {
                this(null, Integer.MAX_VALUE, Colors.WHITE);
            }

            /**
             * Returns a readable representation of vertex traversal state.
             * Infinite distance is rendered as {@code INF}.
             */
            @Override
            public String toString() {
                return "VertexProps{" +
                        "parent=" + parent +
                        ", distance=" + (distance == Integer.MAX_VALUE ? "INF" : distance) +
                        ", color=" + color +
                        "}\n";
            }
        }

        /**
         * Constructs and executes BFS on the given graph starting from {@code src}.
         *
         * <p>Algorithm steps:
         * <ol>
         *   <li>Initialize all vertices as WHITE with infinite distance</li>
         *   <li>Mark source vertex as GRAY with distance 0</li>
         *   <li>Process vertices in FIFO order using a queue</li>
         *   <li>Discover undiscovered neighbors and relax edges</li>
         *   <li>Mark fully explored vertices as BLACK</li>
         * </ol>
         *
         * @param graph the graph to traverse
         * @param src   the source vertex
         */
        SolutionV3(IGraph<T> graph, T src) {
            this.src = src;

            // Initialize properties for all vertices
            for (T vertex : graph.vertices()) {
                vertexProps.put(vertex, new VertexProps<T>());
            }

            // Initialize source vertex
            vertexProps.put(src, new VertexProps(null, 0, Colors.GRAY));
            queue.offerLast(src);

            // BFS main traversal loop
            while (!queue.isEmpty()) {
                T parent = queue.pollFirst();
                VertexProps parentProps = vertexProps.get(parent);

                for (T next : graph.getAdjacent(parent)) {
                    VertexProps nextProps = vertexProps.get(next);

                    if (nextProps.color == Colors.WHITE) {
                        queue.offerLast(next);
                        nextProps.color = Colors.GRAY;
                        nextProps.distance = parentProps.distance + 1;
                        nextProps.parent = parent;
                    }
                }

                // Mark vertex as fully explored
                parentProps.color = Colors.BLACK;
            }
        }

        /**
         * Reconstructs the shortest path from the source vertex
         * to the given destination vertex.
         *
         * <p>The path is reconstructed by following parent pointers
         * from the destination back to the source.
         *
         * @param dest destination vertex
         * @return list of vertices representing the path
         */
        List<T> pathTo(T dest) {
            List<T> path = new LinkedList<>();
            T parent = dest;

            path.addFirst(parent);
            parent = vertexProps.get(parent).parent;

            while (parent != null) {
                path.addFirst(parent);
                parent = vertexProps.get(parent).parent;
            }

            return path;
        }

        /**
         * Returns a string representation of the BFS traversal result.
         *
         * <p>Includes:
         * <ul>
         *   <li>Source vertex</li>
         *   <li>Traversal metadata for all vertices</li>
         * </ul>
         *
         * @return human-readable BFS state
         */
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("BFS state for:");
            sb.append(src);
            sb.append("\n");
            sb.append("vertexProps:\n");
            sb.append(vertexProps.toString());
            return sb.toString();
        }
    }

}