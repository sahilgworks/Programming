
class DummyGraphGenerator{

    public static IGraph<Integer> generate(){
        IGraph<Integer> graph = new GraphAdjList<Integer>();
        graph.addEdge(1, 3);
        graph.addEdge(1, 5);
        graph.addEdge(1, 7);
        graph.addEdge(3, 5);
        graph.addEdge(5, 7);
        graph.addEdge(5, 7);
        graph.addEdge(5, 9);
        graph.addEdge(3, 11);
        graph.addEdge(7, 13);
        graph.addEdge(13, 15);
        graph.addEdge(15, 17);
        graph.addEdge(17, 19);
        graph.addEdge(5, 19);
        graph.addEdge(2, 4);
        graph.addVertex(6);
        graph.addVertex(8);
        return graph;
    }

}